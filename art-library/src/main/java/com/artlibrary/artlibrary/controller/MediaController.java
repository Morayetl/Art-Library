package com.artlibrary.artlibrary.controller;

import java.time.Duration;

import javax.servlet.http.HttpServletRequest;

import com.artlibrary.artlibrary.service.CaptchaService;
import com.artlibrary.artlibrary.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RestController
@RequestMapping("/api/media")
public class MediaController {
	@Autowired
	private FileService fileService;

	@Autowired
	private CaptchaService captchaService;

	private final Bucket downloadBucket;
	private final Bucket imageBucket;
	private final Bucket viewBucket;
	private final Bucket downloadCountBucket;

	public MediaController() {
		Bandwidth limit = Bandwidth.classic(30, Refill.greedy(30, Duration.ofMinutes(5)));
		this.downloadBucket = Bucket4j.builder().addLimit(limit).build();
		Bandwidth imageLimit = Bandwidth.classic(200, Refill.greedy(100, Duration.ofSeconds(1)));
		this.imageBucket = Bucket4j.builder().addLimit(imageLimit).build();
		Bandwidth viewLimit = Bandwidth.classic(1, Refill.greedy(1, Duration.ofHours(1)));
		this.viewBucket = Bucket4j.builder().addLimit(viewLimit).build();
		Bandwidth downloadCountLimit = Bandwidth.classic(1, Refill.greedy(1, Duration.ofHours(1)));
		this.downloadCountBucket = Bucket4j.builder().addLimit(downloadCountLimit).build();
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> index(@PathVariable("id") String id, HttpServletRequest request) {
		String response = request.getParameter("g-recaptcha-response");
		Boolean isValid = captchaService.isValidCaptcha(response);

		if (!isValid) {
			return ResponseEntity.badRequest().build();
		}

		if (downloadBucket.tryConsume(1)) {
			if (downloadCountBucket.tryConsume(1)) {
				fileService.addDownloadCount(id);
			}
			return fileService.getFile(id, false, false);
		}

		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}

	@RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getThumbnail(@PathVariable("id") String id) {
		if (imageBucket.tryConsume(1)) {
			return fileService.getFile(id, true, false);
		}
		
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}

	@RequestMapping(value = "/meta/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getMetaData(@PathVariable("id") String id) {
		if (viewBucket.tryConsume(1)) {
			fileService.addView(id);
		}
		return fileService.getMetaData(id);
	}
}