package com.artlibrary.artlibrary.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.artlibrary.artlibrary.requests.UploadRequest;
import com.artlibrary.artlibrary.requests.UploadUpdateRequest;
import com.artlibrary.artlibrary.responses.FileMetaDataResponse;
import com.artlibrary.artlibrary.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
	@Autowired
	private FileService fileService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FileMetaDataResponse>> index() {
		List<FileMetaDataResponse> files = fileService.findUserFiles();
		return ResponseEntity.ok(files);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> uploadFile(@Valid @ModelAttribute UploadRequest uploadRequest) throws IOException{
		fileService.upload(uploadRequest);
		return new ResponseEntity<String>("File uploaded succesfully!", HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFile(@PathVariable("id") String id){
		fileService.deleteUserFiles(id);
		return new ResponseEntity<String>("File deleted succesfully!", HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}" ,method = RequestMethod.PUT)
	public ResponseEntity<String> updateMetaData(@Valid @RequestBody UploadUpdateRequest uploadUpdateRequest, @PathVariable("id") String id){
		fileService.updateMetadata(uploadUpdateRequest,id);
		return new ResponseEntity<String>("Information updated succesfully!", HttpStatus.OK);
	}
}