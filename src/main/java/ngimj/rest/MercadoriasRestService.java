package ngimj.rest;

import ngimj.dto.MercadoriaXml;
import ngimj.dto.MercadoriasXml;
import ngimj.service.Service;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/mercadorias")
public class MercadoriasRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MercadoriasXml getMercadoria() {
        return new Service().getMercadoriasXml();
    }

    @GET
    @Path("{referencia: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public MercadoriaXml getMercadoria(@PathParam("referencia") String referencia) throws Exception {
        return new Service().getMercadoriaXml(referencia);
    }
}
