package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TTechGetStudent {

    @Test
    public void getStudent() throws JsonProcessingException {
        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        Response rest = RestAssured.given()
                .contentType("application/json")
                .get("student/TTCb3xKA");
        System.out.println(rest.getStatusCode());
        System.out.println(rest.getBody().asString());

        ObjectMapper map = new ObjectMapper();
        JsonNode js = map.readTree(rest.getBody().asString());
        System.out.println(js.get("status"));
        System.out.println(js.get("message"));

        // https://codebeautify.org/jsonviewer >> copy from console and paste in this site
        System.out.println(js.get("data"));
        System.out.println(js.get("data").get("firstName"));
        System.out.println(js.get("data").get("lastName"));
        System.out.println(js.get("data").get("email"));

    }
}



