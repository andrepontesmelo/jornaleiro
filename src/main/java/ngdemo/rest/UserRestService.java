package ngdemo.rest;

import ngdemo.dto.PesquisaPessoa;
import ngdemo.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/users")
public class UserRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PesquisaPessoa getDefaultUserInJSON() {
        UserService userService = new UserService();
        return userService.getDefaultUser();
    }
}
