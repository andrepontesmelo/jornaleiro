package ngimj.rest;

import ngimj.dto.MercadoriaXml;
import ngimj.dto.MercadoriasXml;
import ngimj.dto.NovosPrecosXml;
import ngimj.service.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mercadorias")
public class MercadoriasRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MercadoriasXml getMercadoria() {
        return new Service().getMercadoriasXml();
    }

    @GET
    @Path("{referencia: \\d+}/novos-preços")
    @Produces(MediaType.APPLICATION_JSON)
    public NovosPrecosXml getNovosPrecos(@PathParam("referencia") String referencia) throws Exception {
        return new Service().getNovosPrecosXml(referencia);
    }

    @PUT
    @Path("{referencia: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putMercadoria(@PathParam("referencia") String referencia,
              MercadoriaXml mercadoria) throws Exception {

        new Service().updateMercadoria(mercadoria);

        return Response.noContent().build();
    }
}