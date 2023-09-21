package com.artlibrary.artlibrary.interfaces;

import java.util.List;

import com.artlibrary.artlibrary.model.Download;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IDownloadRepository extends MongoRepository<Download, String> {
    List<Download> findAllByMedia(String media);
    void deleteByMedia(String media);
}