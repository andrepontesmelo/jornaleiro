package ngdemo.rest;

import ngdemo.dto.Mercadoria;
import ngdemo.dto.MercadoriaResource;
import ngdemo.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Vector;


@Path("/mercadorias")
public class MercadoriaRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MercadoriaResource getMercadoriaResource() {
        UserService userService = new UserService();
        return userService.getMercadoriaResource();
    }
}
