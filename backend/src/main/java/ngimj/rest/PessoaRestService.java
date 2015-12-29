package ngimj.rest;

import ngimj.dto.PessoaXml;
import ngimj.service.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;


@Path("/pessoas")
public class PessoaRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PessoaXml getPessoaResource() {
        Service userService = new Service();
        return userService.getPessoaXml();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTodo(JAXBElement<PessoaXml> todo) {
        PessoaXml c = todo.getValue();
        return Response.noContent().build();
    }

}
