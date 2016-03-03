package jornaleiro.rest;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

public class DocumentRestServiceTest {

    @Test
    public void testShouldGetDocument() throws Exception {
        get("/jornaleiro/rest/document/1155111").then().body("id", equalTo(1155111));
    }
}