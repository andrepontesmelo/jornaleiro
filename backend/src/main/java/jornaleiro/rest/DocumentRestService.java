package jornaleiro.rest;

import jornaleiro.dto.DocumentXml;
import jornaleiro.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/document")
public class DocumentRestService {

    @GET
    @Path("{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentXml getDocument(@PathParam("id") int id) throws Exception {
        return new Service().getDocumentXml(id);
    }
}