package com.examples;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class Booking {

    @Test
    public void createNewBooking() {
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking")
                           .contentType(ContentType.JSON)
                           .body("{\n" +
                                 "\"firstname\" : \"Jim\",\n" +
                                 "\"lastname\" : \"Brown\",\n" +
                                 "\"totalprice\" : 111,\n" +
                                 "\"depositpaid\" : true,\n" +
                                 "\"bookingdates\" : {\n" +
                                 "    \"checkin\" : \"2018-01-01\",\n" +
                                 "    \"checkout\" : \"2019-01-01\"\n" +
                                 "},\n" +
                                 "\"additionalneeds\" : \"None\"\n" +
                                 "}")
                   .when().post()
                   .then().statusCode(200);
    }

    @Test
    public void readInformationFromExistingBooking() {
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking")
                           .basePath("1")
                   .when().get()
                   .then().statusCode(200);
    }

    @Test
    public void updateInformationFromExistingBooking() {
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking")
                           .basePath("3")
                           .contentType(ContentType.JSON)
                           .accept("application/json")
                           .auth().preemptive().basic("admin", "password123")
                           .body("{\n" +
                                 "\"firstname\" : \"James\",\n" +
                                 "\"lastname\" : \"Brown\",\n" +
                                 "\"totalprice\" : 111,\n" +
                                 "\"depositpaid\" : true,\n" +
                                 "\"bookingdates\" : {\n" +
                                 "    \"checkin\" : \"2018-01-01\",\n" +
                                 "    \"checkout\" : \"2019-01-01\"\n" +
                                 "},\n" +
                                 "\"additionalneeds\" : \"Breakfast\"\n" +
                                 "}")
                   .when().put()
                   .then().statusCode(200);
    }

    @Test
    public void updatePartiallyInformationFromExistingBooking() {
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking")
                           .basePath("3")
                           .contentType(ContentType.JSON)
                           .accept("application/json")
                           .auth().preemptive().basic("admin", "password123")
                           .body("{\n" +
                                 "\"firstname\" : \"James\",\n" +
                                 "\"lastname\" : \"Brown\"\n" +
                                 "}")
                    .when().patch()
                    .then().statusCode(200);
    }

    @Test
    public void getToken() {
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/auth")
                           .contentType(ContentType.JSON)
                            .body("{\n" +
                                  "\"username\" : \"admin\",\n" +
                                  "\"password\" : \"password123\"\n" +
                                  "}")
                   .when().post()
                   .then().log().all();
    }

    @Test
    public void deleteInformationFromExistingBooking() {
        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking")
                           .basePath("/1")
                           .contentType(ContentType.JSON)
                           .cookie("token", "a5729e81d56afe8")
                   .when().delete()
                   .then().statusCode(201).log().all();
    }
}
