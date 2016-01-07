package jornaleiro.rest;

import jornaleiro.dto.ResultadoBuscaXml;
import jornaleiro.service.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/busca")
public class BuscaRestService {

    @GET
    @Path("{query: \\w+}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultadoBuscaXml getMercadoria(@PathParam("query") String query) throws Exception {
        return new Service().obtemResultadoBuscaXml(query.replace('_', ' '));
    }
}