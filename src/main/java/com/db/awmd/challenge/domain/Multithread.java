package com.db.awmd.challenge.domain;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Multithread extends Thread{
	public void run() 
	{ 
		try
		{ 
			// Displaying the thread that is running 
			System.out.println ("Thread " + 
					Thread.currentThread().getId() + 
					" is running"); 

			  RestTemplate restTemplate= new RestTemplate();
			
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      TransactionDetails swetaTransaction=new TransactionDetails("sweta", "narendra", new BigDecimal(100));
		
		      
		      HttpEntity<TransactionDetails> entity = new HttpEntity<TransactionDetails>(swetaTransaction,headers);
		       restTemplate.exchange(
		         "http://localhost:18080/v1/accounts/amount/transfer", HttpMethod.POST, entity, String.class).getBody();
			  
				/*
				 * 
				 * TransactionDetails nTransaction=new TransactionDetails( "narendra","sweta",
				 * new BigDecimal(10));
				 * 
				 * 
				 * HttpEntity<TransactionDetails> entity2 = new
				 * HttpEntity<TransactionDetails>(nTransaction,headers); restTemplate.exchange(
				 * "http://localhost:18080/v1/accounts/amount/transfer", HttpMethod.POST,
				 * entity2, String.class).getBody()
				 */;
				  
		} 
		catch (Exception e) 
		{ 
			// Throwing an exception 
			System.out.println ("Exception is caught"); 
		} 
	} 
} 


