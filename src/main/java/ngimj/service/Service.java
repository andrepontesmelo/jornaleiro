package ngimj.service;

import ngimj.db.Mercadoria;
import ngimj.dto.ComponentesCustoXml;
import ngimj.dto.MercadoriaXml;
import ngimj.dto.MercadoriasXml;
import ngimj.dto.PessoaXml;

public class Service  {
    public PessoaXml getPessoaXml() {
            return new PessoaXml();
    }

    public MercadoriasXml getMercadoriasXml() { return new MercadoriasXml(); }
    public ComponentesCustoXml getComponentesCustoXml() { return new ComponentesCustoXml(); }

    public MercadoriaXml getMercadoriaXml(String referencia) throws Exception { return new MercadoriaXml(referencia); }

    public void updateMercadoria(MercadoriaXml mercadoria) throws Exception {
        Mercadoria.update(mercadoria);
    }
}
