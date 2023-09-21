package com.artlibrary.artlibrary.service;

import com.artlibrary.artlibrary.responses.CaptchaResponse;
import com.artlibrary.artlibrary.responses.CaptchaSiteKeyResponse;
import com.artlibrary.artlibrary.settings.CaptchaSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CaptchaService {
	
	@Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CaptchaSettings captchaSettings;
 
	public boolean isValidCaptcha(String captcha) {
		String url= "https://www.google.com/recaptcha/api/siteverify";
		String params="?secret="+ captchaSettings.getSecret()+"&response="+captcha;
		String completeUrl=url+params;
		try{
			CaptchaResponse resp= restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
			return resp.isSuccess();
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public ResponseEntity<CaptchaSiteKeyResponse> getSiteKey(){
		return ResponseEntity.ok(new CaptchaSiteKeyResponse(captchaSettings.getSite()));
	}
}