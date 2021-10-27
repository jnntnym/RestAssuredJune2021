package org.example;

import com.github.javafaker.Faker;
import java.time.LocalDateTime;

public class BaseUtil {

        public Faker faker = new Faker();
        public int rand=faker.number().numberBetween(100,999);

        public String firstName=faker.name().firstName();
        public String lastName=faker.name().lastName();
        public String email="Test1"+rand+"@gmail.com";   // Test1 201 @gmail.com
        public String password="Test99"+rand;
        public String updatePassword="Update11"+rand;
        public int year= LocalDateTime.now().minusMonths(120).getYear();        // (2021-(10*12) yrs back to reach 2013
        public int month=LocalDateTime.now().getMonthValue();
        public int day=LocalDateTime.now().getDayOfMonth();

        public String signUpBody(String userEmail, String userPassword){
            String body="{\n" +
                    "    \"firstName\" : \""+firstName+"\",\n" +
                    "    \"lastName\" : \""+lastName+"\",\n" +
                    "    \"email\"     : \""+userEmail+"\",\n" +
                    "    \"password\"  : \""+userPassword+"\",\n" +
                    "    \"confirmPassword\"  : \""+userPassword+"\",\n" +
                    "    \"dob\"       : {\n" +
                    "        \"year\"      : "+year+",\n" +
                    "        \"month\"     : "+month+",\n" +
                    "        \"day\"       : "+day+"\n" +
                    "    },\n" +
                    "    \"gender\"    : \"male\",\n" +
                    "    \"agree\"     : true\n" +
                    "}";
            return body;

        }
}
