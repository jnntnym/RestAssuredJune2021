package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TTechApiSignUp {

    @Test
    public void signUpPostCall() throws JsonProcessingException {
        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        // below 3 lines of code related with only TalentTec Site
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);
        // Main code
        Response rest = RestAssured.given()         // Local variable rest is  type
                .contentType("application/json")    // Manually written
                .body("{\n" +                       // Copy from talent tech POST API
                        "    \"firstName\" : \"Jhon\",\n" +
                        "    \"lastName\" : \"Doe\",\n" +
                        "    \"email\"     : \"jhon.doe204@gmail.com\",\n" +
                        "    \"password\"  : \"123456\",\n" +
                        "    \"confirmPassword\"  : \"123456\",\n" +
                        "    \"dob\"       : {\n" +
                        "        \"year\"      : 2013,\n" +
                        "        \"month\"     : 12,\n" +
                        "        \"day\"       : 31\n" +
                        "    },\n" +
                        "    \"gender\"    : \"male\",\n" +
                        "    \"agree\"     : true\n" +
                        "}")
                .post("signup");                // end point

        System.out.println(rest.getStatusCode());  // sout =  System.out.println(); // out put = 201
        System.out.println(rest.getBody().asString()); // Output as = {"success":true,"message":"Registration Success","id":"TTCl3qHU"}

        ObjectMapper map= new ObjectMapper();       //  map help to read specific item of the body from console
        JsonNode js = map.readTree(rest.getBody().asString());  // readTree > add throw exception ; JsonNode js help to get data
        System.out.println(js.get("success"));      // Output as = true {"success":true,"message":"Registration Success","id":"TTCl3qHU"}
        System.out.println(js.get("message"));      // Output as = "Registration Success"
        System.out.println(js.get("id"));           // Output as = "TTCl3qHU"
    }
}
