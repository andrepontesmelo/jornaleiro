package jornaleiro.rest;

import jornaleiro.dto.SearchResultXml;
import jornaleiro.service.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/search")
public class SearchRestService {

    @GET
    @Path("{query: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultXml getResult(@PathParam("query") String query) throws Exception {
        return new Service().getSearchResultXml(query.replace('_', ' '));
    }
}