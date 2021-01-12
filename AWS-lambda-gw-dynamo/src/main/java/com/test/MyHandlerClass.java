package com.test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
// Handler Class for the AWS Lambda function
public class MyHandlerClass implements RequestHandler<Request,Object> {

	@Override
	public Object handleRequest(Request request, Context context) {
		System.out.println("Request object = " + request);
		System.out.println("Context object = " + context);
		AmazonDynamoDB adb = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(adb);
		Student student = null;
		switch (request.getHttpMethod()) {
			
			case "GET" :
				student = mapper.load(Student.class,request.getId());
				if (student == null) {
					throw new ResourceNotFoundException("Resource Not Found "+request.getId());
				}
				return student;
			case "POST" :
				student = request.getStudent();
				mapper.save(student);
				return student;
			case "default" :
				break;
				
		}
		
		return null;
	}
	
	

}
