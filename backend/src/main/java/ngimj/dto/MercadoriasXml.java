package ngimj.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;
import java.util.Vector;

@XmlRootElement
public class MercadoriasXml {

    private Vector<Mercadoria> mercadorias;
    private Vector<VinculoMercadoriaComponenteCusto> vs;

    public Vector<VinculoMercadoriaComponenteCusto> getVs() {
        return vs;
    }

    public void setVs(Vector<VinculoMercadoriaComponenteCusto> vs) {
        this.vs = vs;
    }

    public MercadoriasXml()
    {
        try {
            this.mercadorias = ngimj.db.Mercadoria.getAll();
            this.vs = new Vector<VinculoMercadoriaComponenteCusto>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Mercadoria> getMercadorias() {
        return mercadorias;
    }

    public void setMercadorias(Vector<Mercadoria> mercadorias) {
        this.mercadorias = mercadorias;
    }
}