package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TTechApiDobYear {

    @Test
    public void dobApi() throws JsonProcessingException {
        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);
        // Main code
        Response rest = RestAssured.given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"firstName\" : \"Jhon\",\n" +
                        "    \"lastName\" : \"Doe\",\n" +
                        "    \"email\"     : \"jhdoe05@gmail.com\",\n" +
                        "    \"password\"  : \"123456\",\n" +
                        "    \"confirmPassword\"  : \"123456\",\n" +
                        "    \"dob\"       : {\n" +
                        "        \"year\"      : 2020,\n" +     //A limit between 1935 and 2013.
                        "        \"month\"     : 12,\n" +
                        "        \"day\"       : 31\n" +
                        "    },\n" +
                        "    \"gender\"    : \"male\",\n" +
                        "    \"agree\"     : true\n" +
                        "}")
                .post("signup");
        System.out.println(rest.getStatusCode());
        System.out.println(rest.getBody().asString());
        Assert.assertEquals(rest.getStatusCode(),200);

        ObjectMapper map = new ObjectMapper();
        JsonNode js = map.readTree(rest.getBody().asString());
        System.out.println(js.get("success"));
        System.out.println(js.get("message"));
        System.out.println(js.get("id"));
        System.out.println(js.get("error"));










    }
}
