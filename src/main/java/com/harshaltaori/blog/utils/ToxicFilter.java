package com.harshaltaori.blog.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.harshaltaori.blog.configs.AppConstants;


public class ToxicFilter {
	
	public static boolean isToxicComment(String comment) {
	    return AppConstants.OFFENSIVE_WORDS.stream()
	        .anyMatch(word -> comment.toLowerCase().contains(word));
	}
	
	
	//We can use the prespective api from the google cloud as well it is free
	//Below is the code for the same
	
//	public static boolean isToxic(String comment) {
//	    // Calling the Perspective API or similar
//	    String apiKey = "YOUR_API_KEY";
//	    RestTemplate restTemplate = new RestTemplate();
//	    String url = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + apiKey;
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_JSON);
//
//	    String body = "{"
//	        + "\"comment\": {\"text\": \"" + comment + "\"},"
//	        + "\"languages\": [\"en\"],"
//	        + "\"requestedAttributes\": {\"TOXICITY\": {}}"
//	        + "}";
//
//	    HttpEntity<String> request = new HttpEntity<>(body, headers);
//	    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//
//	    // Processing the response to determine if the comment is toxic
//	    return response.getBody().contains("\"value\": 0.8"); // Example threshold
//	}
	
	
	
}
