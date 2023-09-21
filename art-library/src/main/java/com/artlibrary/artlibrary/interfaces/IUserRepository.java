package com.artlibrary.artlibrary.interfaces;

import java.util.Optional;
import com.artlibrary.artlibrary.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
}