package talentTekAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import org.example.BaseUtil;
import org.testng.annotations.Test;

public class ApiFramework01 {           // Run from this line
    String email;
    String password;
    String id ;
    String tkn;

    // Generate Student Unique ID and store in variable n > POST Call
    @Test
    public void signUP() throws JsonProcessingException {
        BaseUtil util = new BaseUtil();
        RestAssured.baseURI = "http://qa.taltektc.com/api/";

        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        email = util.email;
        password = util.password;

        Response rest = RestAssured.given()
                .contentType("application/json")
                .body(util.signUpBody(
                        email,
                        password))
                .post("signup");    // POST Call

        System.out.println(rest.getStatusCode());
        System.out.println(rest.getBody().asString());

        ObjectMapper map = new ObjectMapper();
        JsonNode js = map.readTree(rest.getBody().asString());
        System.out.println(js.get("success"));
        //Student Id Provided below
        id = js.get("id").toString().replaceAll("\"",""); // Store ID
        System.out.println("Id :"+id);
    }

    // Use email/ ID and password for LOGIN POST Call and get TOKEN
    @Test (dependsOnMethods = "signUP")
    public void logInPost() throws JsonProcessingException {

        RestAssured.baseURI = "http://qa.taltektc.com/api/";

        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        Response res = RestAssured.given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"email\" : \""+email+"\",\n" +        // you can login with Id also instead of email
                        "    \"password\" : \""+password+"\"\n" +
                        "}")
                .post("login");     // POST call
        System.out.println(res.getStatusCode());
        System.out.println(res.getBody().asString());

        ObjectMapper map= new ObjectMapper();
        JsonNode js = map.readTree(res.getBody().asString());

        System.out.println(js.get("success"));
        System.out.println(js.get("message"));
        // After successfully login it will provide a Bearer Token
        tkn = js.get("token").toString().replaceAll("\"","");
        System.out.println("Token: "+tkn);
    }
    // Used Token to update student information >  PUT call ??
    @Test (dependsOnMethods = "logInPost")
    public void putUpdateInfo() throws JsonProcessingException {

        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer" + tkn)        // Token used here
                .body("{\n" +
                        "    \"firstName\" : \"Jhon\",\n" +
                        "    \"lastName\" : \"Doe\",\n" +
                        "    \"email\"     : \"jony12@gmail.com\",\n" +
                        "    \"dob\"       : {\n" +
                        "        \"year\"      : 2013,\n" +
                        "        \"month\"     : 11,\n" +
                        "        \"day\"       : 31\n" +
                        "    },\n" +
                        "    \"gender\"    : \"Female\",\n" +
                        "    \"agree\"     : true\n" +
                        "}\n")
                .put("student/"+ id);      // PUT Call

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());

        ObjectMapper map = new ObjectMapper();
        JsonNode body = map.readTree(response.getBody().asString());
        System.out.println(body.get("data"));
    }
    // Used previous Student ID (n) to get one student information > GET Call
    @Test (dependsOnMethods = "signUP")
    public void getStudentInfo() throws JsonProcessingException {

        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        Response response = RestAssured.given()
                .contentType("application/json")
                .get("student/"+ id);        // GET Call

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());

        ObjectMapper map = new ObjectMapper();
        JsonNode body = map.readTree(response.getBody().asString());
        System.out.println(body.get("data"));
    }

    @Test
    public void getAllStudentInfo() throws JsonProcessingException {

        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        Response response = RestAssured.given()
                .contentType("application/json")
                .get("students");
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString()); // The recently added user will show at the end
        ObjectMapper map = new ObjectMapper();
        JsonNode body = map.readTree(response.getBody().asString());
        JsonNode data = body.get("data");
    }

    @Test (dependsOnMethods = "signUP")
    public void changePassWord() throws JsonProcessingException {

        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"password\"  : \"1234567\",\n" +  //old password:  in password variable
                        "    \"confirmPassword\"  : \"1234567\"\n" +
                        "}")
                .patch("updatePassword/"+id);                 // Change PassWord

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());

        ObjectMapper map = new ObjectMapper();
        JsonNode body = map.readTree(response.getBody().asString());
        System.out.println(body.get("message"));
    }

    @Test (dependsOnMethods = "logInPost")
    public void deleteStudent() throws JsonProcessingException{

        RestAssured.baseURI = "http://qa.taltektc.com/api/";
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);

        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer" + tkn)        // Token used here
                .delete("student/"+id);                            // id used here

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());

        ObjectMapper map= new ObjectMapper();
        JsonNode body = map.readTree(response.getBody().asString());
        System.out.println(body.get("message"));


    }

}
