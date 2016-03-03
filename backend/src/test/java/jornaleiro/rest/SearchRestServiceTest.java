package jornaleiro.rest;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.when;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class SearchRestServiceTest {

    @Test
    public void testSearchShouldFindSomething() throws Exception {
        get("/jornaleiro/rest/search/maria").then().assertThat().body(containsString("maria"));
    }

    @Test
    public void testSearchShouldBeFast() {
        when().get("/jornaleiro/rest/search/augusto_souza").then().time(lessThan(2L), SECONDS);
    }

    @Test
    public void testSearchShouldHighlightQuery() throws Exception {
        get("/jornaleiro/rest/search/adalberto").then().assertThat().body(containsString("<em>adalberto</em>"));
    }
}