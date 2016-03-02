package jornaleiro.rest;

import jornaleiro.dto.Elastic.SearchRoot;
import jornaleiro.service.ElasticSearch;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/search")
public class SearchRestService {

    @GET
    @Path("{query: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    public SearchRoot getResult(@PathParam("query") String query) throws Exception {
        long start = System.nanoTime();
        SearchRoot result = ElasticSearch.getInstance().search(query.replace('_', ' '));
        double elapsedTime = (double) (System.nanoTime() - start) / 1000000000.0;
        result.setElapsedTime(elapsedTime);

        return result;
    }
}