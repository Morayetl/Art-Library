package com.artlibrary.artlibrary.controller;

import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.artlibrary.artlibrary.responses.SearchResponse;
import com.artlibrary.artlibrary.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	@Autowired
	SearchService searchService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<SearchResponse> Search(
		@RequestParam @Size(max = 300) Optional<String> query,
		@RequestParam Optional<String> sortBy,
		@RequestParam @Min(0) Optional<Integer> page,
		@RequestParam Optional<Boolean> random
		) {
		return this.searchService.SearchAggregate(query.orElse(""), page.orElse(0), false, random.orElse(false));
	}

	@RequestMapping(value="/user" ,method = RequestMethod.GET)
	public ResponseEntity<SearchResponse> SearchUser(
		@RequestParam @Size(max = 300) Optional<String> query,
		@RequestParam Optional<String> sortBy,
		@RequestParam @Min(0) Optional<Integer> page
		) {
		return this.searchService.SearchAggregate(query.orElse(""), page.orElse(0), true, false);
	}
}