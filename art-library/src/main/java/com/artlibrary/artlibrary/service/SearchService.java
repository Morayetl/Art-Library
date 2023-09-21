package com.artlibrary.artlibrary.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.artlibrary.artlibrary.interfaces.IMetaDataFields;
import com.artlibrary.artlibrary.responses.SearchResponse;
import com.artlibrary.artlibrary.responses.SearchResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SearchService {


    @Autowired
    private ProfileService profileService;
    @Autowired
    private MongoTemplate mongoTemplate;

    private int pageSize = 30;

    public List<GridFSFile> SearchRandom(List<BsonValue> fileIdList, int sampleSize) {
        MongoCollection<Document> collection = mongoTemplate.getCollection("fs.files");
        List<GridFSFile> results = new ArrayList<GridFSFile>();
        collection.aggregate(
                Arrays.asList(Aggregates.match(Filters.eq("metadata." + IMetaDataFields.published, true)),
                        Aggregates.match(Filters.nin("_id", fileIdList)), Aggregates.sample(sampleSize)),
                GridFSFile.class).into(results);

        return results;
    }

    public ResponseEntity<SearchResponse> SearchAggregate(String searchQuery,int page, boolean userSearch, boolean random) {
        MongoCollection<Document> collection =  mongoTemplate.getCollection("fs.files");
        List<GridFSFile> files = new ArrayList<GridFSFile>();
        List<GridFSFile> allResults = new ArrayList<GridFSFile>();
        List<Bson> aggregatelist = new ArrayList<Bson>();
        String text = searchQuery.trim();

        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (userSearch && !isAuthenticated) {
            return ResponseEntity.status(403).build();
        }

        if(text.length() > 0){
            aggregatelist.add(Aggregates.match(Filters.text(searchQuery)));
        }

        if(!userSearch){
            aggregatelist.add(Aggregates.match(Filters.eq("metadata."+IMetaDataFields.published, true)));
        }

        if (userSearch && isAuthenticated) {
            UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userId = isAuthenticated == true ? principal.getId() : null;
            aggregatelist.add(Aggregates.match(Filters.eq("metadata."+IMetaDataFields.created_by, userId)));
        }

        collection.aggregate(
            aggregatelist
            , GridFSFile.class).into(allResults);
        

        if(!random){
            aggregatelist.add(Aggregates.set(new Field<String>(IMetaDataFields.created, "$metadata." + IMetaDataFields.created)));
            aggregatelist.add(Aggregates.sort(Sorts.descending(IMetaDataFields.created)));
            aggregatelist.add(Aggregates.skip(pageSize * page));
        }else{
            aggregatelist.add(Aggregates.sample(3000));
        }
        
        aggregatelist.add(Aggregates.limit(random ? 3000 : pageSize));
        
        collection.aggregate(
                aggregatelist
                , GridFSFile.class).into(files);

        // find more pictures if results are lower than 10
        if (files.size() > 0 && files.size() < 10 && !userSearch && page < 1) {
            List<BsonValue> fileIdList = new ArrayList<>();
            for (GridFSFile file : files) {
                fileIdList.add(file.getId());
            }

            files.addAll(SearchRandom(fileIdList, pageSize - fileIdList.size()));
        }                
        
        int count = allResults.size();

        List<SearchResult> result = new ArrayList<SearchResult>();
        for (GridFSFile file : files) {
            SearchResult data = new SearchResult();
            // get files id
            String id = file.getId().asObjectId().getValue().toString();
            if (id != null) {
                data.setId(id);
            }

            BigDecimal bd = new BigDecimal(file.getLength() / 1000000d).setScale(2, RoundingMode.HALF_EVEN);
            double size = bd.doubleValue();

            data.setType(file.getMetadata().get(IMetaDataFields.type).toString());
            data.setTitle(file.getMetadata().get(IMetaDataFields.title).toString());
            data.setDescription(file.getMetadata().get(IMetaDataFields.description).toString());
            data.setCreated_by(file.getMetadata().get(IMetaDataFields.created_by).toString());
            data.setTags(file.getMetadata().getList(IMetaDataFields.tags, String.class));
            data.setCreated(file.getMetadata().get(IMetaDataFields.created).toString());
            data.setModified(file.getMetadata().get(IMetaDataFields.modified).toString());
            data.setSlug(file.getMetadata().get(IMetaDataFields.slug, String.class));
            data.setHeight(file.getMetadata().getInteger(IMetaDataFields.thumbnailHeight));
            data.setWidth(file.getMetadata().getInteger(IMetaDataFields.thumbnailWidth));
            data.setSrc(profileService.getMediaUrl() + id);
            data.setSize(size);
            result.add(data);
        }

        SearchResponse response = new SearchResponse(page, count, result, pageSize);
        return ResponseEntity.ok(response);
    }

}