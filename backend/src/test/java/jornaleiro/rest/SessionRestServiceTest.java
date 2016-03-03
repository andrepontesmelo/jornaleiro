package jornaleiro.rest;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

public class SessionRestServiceTest {

    @Test
    public void testShouldGetSessions() throws Exception {
        get("/jornaleiro/rest/sessions/").
            then().
            statusCode(200).
            body("title[0]", equalTo("Noticiário")).
            body("id[0]", equalTo(1));
}
}