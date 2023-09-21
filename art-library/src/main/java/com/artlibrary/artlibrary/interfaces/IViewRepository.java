package com.artlibrary.artlibrary.interfaces;

import java.util.List;
import com.artlibrary.artlibrary.model.View;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IViewRepository extends MongoRepository<View, String> {
    List<View> findAllByMedia(String media);
    void deleteByMedia(String media);
}