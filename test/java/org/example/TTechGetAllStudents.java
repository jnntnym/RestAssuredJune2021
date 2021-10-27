package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TTechGetAllStudents {

    @Test
    public void getStudent() throws JsonProcessingException {
        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false);
        Response rest = RestAssured.given()
                .contentType("application/json")
                .get("students");   // end point
        System.out.println(rest.getStatusCode());
        System.out.println(rest.getBody().asString());

        ObjectMapper map = new ObjectMapper();
        JsonNode js = map.readTree(rest.getBody().asString());
   //   System.out.println(js.get("status"));
   //   System.out.println(js.get("message"));
   //   System.out.println(js.get("data"));  // https://codebeautify.org/jsonviewer >> copy from console and paste in this site

        // Conditional information:  finding email when firstname= Jannat and lastname= Nayeem both are true
        JsonNode data = js.get("data");
        for (int i=0; i< data.size(); i++){
            String fName = data.get(i).get("firstName").toString().replaceAll("\"","");
            String lName = data.get(i).get("lastName").toString().replaceAll("\"","");
            if (fName.equalsIgnoreCase("jannat") && lName.equalsIgnoreCase("Nayeem")){
                System.out.println(data.get(i).get("email"));
                break;
            }

        }
    }
}



