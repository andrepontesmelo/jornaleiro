package jornaleiro.rest;

import jornaleiro.dto.DocumentXml;
import jornaleiro.dto.Session;
import jornaleiro.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/sessions")
public class SessionRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Session> getSessions() throws Exception {
        return new Service().getSessions();
    }
}