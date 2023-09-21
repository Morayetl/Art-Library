package com.artlibrary.artlibrary.controller;

import com.artlibrary.artlibrary.responses.CaptchaSiteKeyResponse;
import com.artlibrary.artlibrary.service.CaptchaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
	@Autowired
	CaptchaService captchaService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<CaptchaSiteKeyResponse> get(){
		return captchaService.getSiteKey();
	}
}