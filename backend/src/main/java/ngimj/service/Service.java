package ngimj.service;

import ngimj.db.Mercadoria;
import ngimj.dto.*;

import java.sql.SQLException;

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

    public NovosPrecosXml getNovosPrecosXml(String referencia) throws SQLException {
        return new NovosPrecosXml(referencia);
    }
}
