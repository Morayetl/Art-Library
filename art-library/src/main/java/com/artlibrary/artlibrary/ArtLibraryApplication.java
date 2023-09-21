package com.artlibrary.artlibrary;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.artlibrary.artlibrary.enums.ERole;
import com.artlibrary.artlibrary.interfaces.IRoleRepository;
import com.artlibrary.artlibrary.model.Role;
import com.artlibrary.artlibrary.requests.UserRequest;
import com.artlibrary.artlibrary.service.UserService;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ArtLibraryApplication implements CommandLineRunner {
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ArtLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Role> rolesInDatabase = roleRepository.findAll();
		
		Boolean metadataIndexFound = false;
		for (Document document : mongoTemplate.getCollection("fs.files").listIndexes()) {
			String indexName = document.get("name").toString();
			if(indexName.contains("metadata")){
				metadataIndexFound = true;
			}	
		}

		if(!metadataIndexFound){
			Bson index = Indexes.compoundIndex(
				Indexes.text("metadata.title"), 
				Indexes.text("metadata.description"),
				Indexes.text("metadata.tags")); 
			IndexOptions options = new IndexOptions();
	
			Document weights = new Document();
			weights.put("metadata.title", 12);
			weights.put("metadata.description", 8);
			weights.put("metadata.tags", 2);
	
			options.weights(weights);
			mongoTemplate.getCollection("fs.files").createIndex(index, options);
		}

		if(rolesInDatabase.isEmpty()){
			// save roles to database
			for (ERole name : ERole.values()) {
				roleRepository.save(new Role(name));
			}
		}

		// create admin if it doenst exist
		UserRequest user = new UserRequest("Test", "Test", "test", "test", "test", "test@test.com");
		if(!userService.userExists(user)){
			Set<ERole> roles = new HashSet<ERole>();
			roles.add(ERole.ROLE_ADMIN);
			userService.register(user, roles);
		}
	}
}
