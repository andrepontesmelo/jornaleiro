package jornaleiro.rest;

import jornaleiro.dto.ResultadoDocumentoXml;
import jornaleiro.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/documento")
public class DocumentoRestService {

    @GET
    @Path("{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultadoDocumentoXml getDocumento(@PathParam("id") int id) throws Exception {
        return new Service().obtemDocumentoXml(id);
    }
}