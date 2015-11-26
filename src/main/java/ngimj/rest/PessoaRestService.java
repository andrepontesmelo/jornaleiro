package ngimj.rest;

import ngimj.dto.PessoaXml;
import ngimj.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/pessoas")
public class PessoaRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PessoaXml getPessoaResource() {
        Service userService = new Service();
        return userService.getPessoaXml();
    }
}
