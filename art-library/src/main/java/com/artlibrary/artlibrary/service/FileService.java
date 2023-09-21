package com.artlibrary.artlibrary.service;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.artlibrary.artlibrary.interfaces.IDownloadRepository;
import com.artlibrary.artlibrary.interfaces.IMetaDataFields;
import com.artlibrary.artlibrary.interfaces.IViewRepository;
import com.artlibrary.artlibrary.model.Download;
import com.artlibrary.artlibrary.model.View;
import com.artlibrary.artlibrary.requests.UploadRequest;
import com.artlibrary.artlibrary.requests.UploadUpdateRequest;
import com.artlibrary.artlibrary.responses.FileMetaDataResponse;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileService {

  @Autowired
  private GridFsTemplate gridFsTemplate;

  @Autowired
  private GridFsOperations operations;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ProfileService profileService;

  @Autowired
  private IViewRepository viewRepository;
  
  @Autowired
	private IDownloadRepository downloadRepository;

  public String upload(UploadRequest uploadRequest) throws IOException {
    UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    MultipartFile file = uploadRequest.file;
    String type = uploadRequest.file.getContentType();

    List<String> parsedTags = parseTags(uploadRequest.tags);
    BasicDBList tagsToDb = new BasicDBList();

    for (String tag : parsedTags) {
      tagsToDb.add(tag);
    }

    Date date = new Date();
    String extension = file.getContentType().split("/")[1];
    String fileName = String.valueOf(date.getTime()) + "." + extension;

    DBObject metaData = new BasicDBObject();
    metaData.put(IMetaDataFields.type, type);
    metaData.put(IMetaDataFields.title, uploadRequest.title);
    metaData.put(IMetaDataFields.description, uploadRequest.description);
    metaData.put(IMetaDataFields.created_by, principal.getId());
    metaData.put(IMetaDataFields.tags, tagsToDb);
    metaData.put(IMetaDataFields.created, new Date());
    metaData.put(IMetaDataFields.modified, new Date());
    metaData.put(IMetaDataFields.published, true);

    InputStream fileToBeStored = file.getInputStream();
    if (file.getContentType().startsWith("image")) {
      Binary thumbnail = getThumbnail(file);
      InputStream thumbnailInputStream = new ByteArrayInputStream(thumbnail.getData()); 
      InputStream originalImageInputStream = new ByteArrayInputStream(file.getBytes());

      BufferedImage thumbnaiImage = getImage(thumbnailInputStream);
      metaData.put(IMetaDataFields.thumbnail, thumbnail);
      metaData.put(IMetaDataFields.thumbnailHeight, thumbnaiImage.getHeight());
      metaData.put(IMetaDataFields.thumbnailWidth, thumbnaiImage.getWidth());

      BufferedImage originalImage = getImage(originalImageInputStream);
      metaData.put(IMetaDataFields.originalImageHeight, originalImage.getHeight());
      metaData.put(IMetaDataFields.originalImageWidth, originalImage.getWidth());
      metaData.put(IMetaDataFields.type, type);
      fileToBeStored = optimizeImage(file);
    }

    ObjectId id = gridFsTemplate.store(fileToBeStored, fileName,type, metaData);
    updateFileSlug(uploadRequest.title, id.toString());
    return id.toString();
  }

  public static String makeSlug(String input) {
    Pattern NONLATIN = Pattern.compile("[^\\w_-]");
    Pattern SEPARATORS = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
    String noseparators = SEPARATORS.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(noseparators, Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH).replaceAll("-{2,}", "-").replaceAll("^-|-$", "");
  }

  public void addView(String id){
    List<Criteria> criterias = new ArrayList<Criteria>();
    criterias.add(GridFsCriteria.whereMetaData(IMetaDataFields.published).is(true));
    criterias.add(GridFsCriteria.where("_id").is(id));

    Criteria criteria = new Criteria();
    criteria.andOperator(criterias);

    Query query = new Query(criteria);
    GridFSFile file = operations.findOne(query);
    if(file != null){
      View v = new View();
      v.setMedia(id);
      v.setCreated(new Date());
      viewRepository.save(v);
    }
  }

  public void addDownloadCount(String id){
    List<Criteria> criterias = new ArrayList<Criteria>();
    criterias.add(GridFsCriteria.whereMetaData(IMetaDataFields.published).is(true));
    criterias.add(GridFsCriteria.where("_id").is(id));

    Criteria criteria = new Criteria();
    criteria.andOperator(criterias);

    Query query = new Query(criteria);
    GridFSFile file = operations.findOne(query);
    if(file != null){
      Download d = new Download();
      d.setMedia(id);
      d.setCreated(new Date());
      downloadRepository.save(d);
    }
  }

  /**
   * Get files
   * 
   * @param id           file id
   * @param getThumbnail get thumbnail for preview
   * @param owner        get file if its ownes
   * @return
   */
  public ResponseEntity<Object> getFile(String id, boolean getThumbnail, boolean owner) {
    GridFSFile result;
    List<Criteria> criterias = new ArrayList<Criteria>();

    if (owner) {
      UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
          .getPrincipal();
      criterias.add(GridFsCriteria.where(IMetaDataFields.created_by).is(principal.getId()));
    } else {
      // file needs to be published for users to see the file, but owner can see it
      // even when its private
      criterias.add(GridFsCriteria.whereMetaData(IMetaDataFields.published).is(true));
    }

    criterias.add(GridFsCriteria.where("_id").is(id));

    Criteria criteria = new Criteria();
    criteria.andOperator(criterias);

    Query query = new Query(criteria);

    result = operations.findOne(query);

    if (result == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (getThumbnail == true) {
      Binary thumbnail = result.getMetadata().get(IMetaDataFields.thumbnail, Binary.class);
      String mediaType = result.getMetadata().get(IMetaDataFields.type, String.class);

      if (thumbnail != null && mediaType != null) {
        InputStream is = new ByteArrayInputStream(thumbnail.getData());
        InputStreamResource inputStreamResource = new InputStreamResource(is);

        return ResponseEntity.ok().contentLength(thumbnail.getData().length).contentType(MediaType.parseMediaType(mediaType))
            .body(inputStreamResource);
      }
      return ResponseEntity.notFound().build();
    }

    GridFsResource file = operations.getResource(result);
    HttpHeaders responseHeaders = new HttpHeaders();
    try {
      responseHeaders.setContentLength(file.contentLength());
      responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      return new ResponseEntity<>(file, responseHeaders, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public BufferedImage getBufferedImage(InputStream is) throws IOException {
    BufferedImage originalImage = ImageIO.read(is);
    return originalImage;
  }

  public BufferedImage getImage(InputStream is) throws IOException{
    BufferedImage originalImage = ImageIO.read(is);

    is.reset();
    return originalImage;
  }

  public Binary getThumbnail(MultipartFile file) throws IOException {
    int length = 750;

    InputStream is = new ByteArrayInputStream(file.getBytes());
    BufferedImage originalImage = ImageIO.read(is);

    int originalTargetWidth = originalImage.getWidth();
    int originalTargetHeight = originalImage.getHeight();
    int biggerLength = originalTargetWidth > originalTargetHeight ? originalTargetWidth : originalTargetHeight;
    double ratio = (double) length / biggerLength;

    int targetHeight = originalTargetHeight > originalTargetWidth ? length : (int) (ratio * originalTargetHeight);
    int targetWidth = originalTargetWidth > originalTargetHeight ? length : (int) (ratio * originalTargetWidth);

    boolean isBiggerThanMaxHeight = originalTargetWidth >= length || originalTargetHeight >= length;

    int height = isBiggerThanMaxHeight ? targetHeight : originalTargetHeight;
    int width = isBiggerThanMaxHeight ? targetWidth : originalTargetWidth;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    Thumbnails.of(file.getInputStream())
    .size(width, height)
    //.outputQuality(0.9)
    .useExifOrientation(true)
    .toOutputStream(outputStream);
    return new Binary(BsonBinarySubType.BINARY, outputStream.toByteArray());
  }

  public InputStream optimizeImage(MultipartFile file) throws IOException {
    float imageQuality = 0.8f;
    // write into outputstream
    ByteArrayOutputStream outputStream = convertImage(file.getInputStream(), imageQuality, file.getContentType());
    InputStream optimizedImage = new ByteArrayInputStream(outputStream.toByteArray());

    return optimizedImage;
  }

  public ByteArrayOutputStream convertImage(InputStream is, float imageQuality, String mimeType) throws IOException {
    // Get image writers
    Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(mimeType);

    if (!imageWriters.hasNext())
      throw new IllegalStateException("Writers Not Found!!");


    // write into outputstream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    Thumbnails.of(is)
      .scale(1)
      .outputQuality(imageQuality)
      .useOriginalFormat()
      .useExifOrientation(true)
      .toOutputStream(outputStream);

    return outputStream;
  }

  public void updateFileSlug(String title, String id) {
    Document m = new Document();

    String generatedSlug = makeSlug(title);
    String slug = generatedSlug + "-" + id;
    m.append("metadata." + IMetaDataFields.slug, slug);

    Document update = new Document("$set", m);
    UpdateOptions options = new UpdateOptions();
    options.upsert(true);

    Bson filter = Filters.eq("_id", new ObjectId(id));
    mongoTemplate.getCollection("fs.files").updateOne(filter, update, options);
  }

  public void updateMetadata(UploadUpdateRequest uploadUpdateRequest, String id) {
    UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Criteria criteria = new Criteria();
    criteria.andOperator(GridFsCriteria.whereMetaData(IMetaDataFields.created_by).is(principal.getId()),
        GridFsCriteria.where("_id").is(id));
    Query query = new Query(criteria);

    GridFSFile file = operations.findOne(query);

    if (file != null) {
      String description = uploadUpdateRequest.getDescription();
      String title = uploadUpdateRequest.getTitle();
      List<String> tagsList = uploadUpdateRequest.getTags();

      Document m = new Document();

      if (description != null) {
        m.append("metadata." + IMetaDataFields.description, description);
      }

      if (title != null) {
        m.append("metadata." + IMetaDataFields.title, title);
      }


      m.append("metadata." + IMetaDataFields.published, true);
      

      if (tagsList != null) {
        BasicDBList tagsToDb = new BasicDBList();

        for (String tag : tagsList) {
          tagsToDb.add(tag);
        }
        m.append("metadata." + IMetaDataFields.tags, tagsToDb);
      }

      m.append("metadata." + IMetaDataFields.modified, new Date());

      Document update = new Document("$set", m);
      UpdateOptions options = new UpdateOptions();
      options.upsert(true);

      Bson filter = Filters.and(Filters.eq("metadata." + IMetaDataFields.created_by, principal.getId()),
          Filters.eq("_id", new ObjectId(id)));
      mongoTemplate.getCollection("fs.files").updateOne(filter, update, options);

      if (title != null) {
        updateFileSlug(title, id);
      }
    }
  }

  public ResponseEntity<Object> getMetaData(String id) {
    Criteria criteria = new Criteria();
    criteria.orOperator(GridFsCriteria.where("_id").is(id),
    GridFsCriteria.whereMetaData(IMetaDataFields.slug).is(id));
    Query query = new Query(criteria);
    GridFSFile file = operations.findOne(query);

    if(file == null){
      return ResponseEntity.notFound().build();
    }

    FileMetaDataResponse res = mapResponseData(file);
    return ResponseEntity.ok(res);
  }

  public String DateToISODateString(Date date){
    SimpleDateFormat sdf;
    sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getDefault());
    String text = sdf.format(date);
    return text;
  }

  public List<FileMetaDataResponse> findUserFiles() {
    UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<GridFSFile> files = new ArrayList<GridFSFile>();

    List<String> tagQueryList = new ArrayList<>();

    Criteria criteria = new Criteria();
    // get file with search
    if (tagQueryList.size() > 0) {
      criteria.orOperator(GridFsCriteria.whereMetaData("title").regex(principal.getId()),
          GridFsCriteria.whereMetaData("tags").in(tagQueryList));
    }

    Query query = new Query(criteria);
    query.addCriteria(GridFsCriteria.whereMetaData("created_by").is(principal.getId()));

    operations.find(query).into(files);
    List<FileMetaDataResponse> response = new ArrayList<FileMetaDataResponse>();
    for (GridFSFile file : files) {
      response.add(mapResponseData(file));
    }
    return response;
  }

  public FileMetaDataResponse mapResponseData(GridFSFile file) {
    FileMetaDataResponse data = new FileMetaDataResponse();
    // get files id
    String id = file.getId().asObjectId().getValue().toString();
    String slug = file.getMetadata().get(IMetaDataFields.slug, String.class);

    BigDecimal bd = new BigDecimal(file.getLength()/1000000d).setScale(2, RoundingMode.HALF_EVEN);
    double size = bd.doubleValue();
    Integer views = viewRepository.findAllByMedia(id).size();
    if (id != null) {
      data.setId(id);
    }
    
    data.setType(file.getMetadata().get(IMetaDataFields.type).toString());
    data.setTitle(file.getMetadata().get(IMetaDataFields.title).toString());
    data.setDescription(file.getMetadata().get(IMetaDataFields.description).toString());
    data.setCreated_by(file.getMetadata().get(IMetaDataFields.created_by).toString());
    data.setTags(file.getMetadata().getList(IMetaDataFields.tags, String.class));
    data.setCreated(DateToISODateString(file.getMetadata().get(IMetaDataFields.created, Date.class)));
    data.setModified(DateToISODateString(file.getMetadata().get(IMetaDataFields.modified, Date.class)));
    data.setPublished(file.getMetadata().get(IMetaDataFields.published, Boolean.class));
    data.setSrc(profileService.getMediaUrl() + id);
    data.setOriginalImageHeight(file.getMetadata().get(IMetaDataFields.originalImageHeight,Integer.class));
    data.setOriginalImageWidth(file.getMetadata().get(IMetaDataFields.originalImageWidth,Integer.class));
    data.setViews(views);
    data.setSize(size);
    data.setSlug(slug);

    return data;
  }

  /**
   * Delete file owned by user
   * 
   * @param id
   */
  public void deleteUserFiles(String id) {
    UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Criteria criteria = new Criteria();
    criteria.andOperator(GridFsCriteria.whereMetaData(IMetaDataFields.created_by).is(principal.getId()),
        GridFsCriteria.where("_id").is(id));
    Query query = new Query(criteria);
    query.addCriteria(GridFsCriteria.whereMetaData(IMetaDataFields.created_by).is(principal.getId()));

    operations.delete(query);
    viewRepository.deleteByMedia(id);
  }

  /**
   * Converts tags list to List object
   * 
   * @param tags in json
   * @return
   */
  private List<String> parseTags(String tags) {
    List<String> tagList = new ArrayList<>();

    if (tags == null) {
      return tagList;
    }

    JsonParser springParser = JsonParserFactory.getJsonParser();
    tagList = springParser.parseList(tags).stream().map(t -> t.toString()).collect(Collectors.toList());

    return tagList;
  }
}