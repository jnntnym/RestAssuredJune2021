package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TTechApiLogInID {

    @Test
    public void loginPostCall() throws JsonProcessingException {
        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        // below 3 lines of code related with only TalentTec Site
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);
        // Main code
        Response rest = RestAssured.given()
                .contentType("application/json")
                .body("{\n" +
                "    \"id\" : \"TTCb3xKA\",\n" +            // login with Id = TTCb3xKA
                "    \"password\" : \"123456\"\n" +
                "}")
                .post("login");                         // login is an end point
        System.out.println(rest.getStatusCode());
        System.out.println(rest.getBody().asString());
        Assert.assertEquals(rest.getStatusCode(),200);

        ObjectMapper map = new ObjectMapper();
        JsonNode js = map.readTree(rest.getBody().asString());
        System.out.println(js.get("success"));
        System.out.println(js.get("status code"));
        System.out.println(js.get("message"));
        System.out.println(js.get("token"));

    }
}
