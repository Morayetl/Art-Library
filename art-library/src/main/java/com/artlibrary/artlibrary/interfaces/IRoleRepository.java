package com.artlibrary.artlibrary.interfaces;

import java.util.Optional;

import com.artlibrary.artlibrary.enums.ERole;
import com.artlibrary.artlibrary.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}