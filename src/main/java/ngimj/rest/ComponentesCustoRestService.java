package ngimj.rest;

import ngimj.dto.ComponentesCustoXml;
import ngimj.dto.MercadoriaXml;
import ngimj.dto.MercadoriasXml;
import ngimj.dto.VinculoMercadoriaComponenteCusto;
import ngimj.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Vector;


@Path("/componentesdecusto")
public class ComponentesCustoRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ComponentesCustoXml getComponentes() {
        return new Service().getComponentesCustoXml();
    }
}
