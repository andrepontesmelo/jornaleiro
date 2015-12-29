package ngimj.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;

@XmlRootElement
public class MercadoriaXml {
    private Mercadoria mercadoria;

    public Vector<VinculoMercadoriaComponenteCusto> getComponenteCustos() {
        return componenteCustos;
    }

    public void setComponenteCustos(Vector<VinculoMercadoriaComponenteCusto> componenteCustos) {
        this.componenteCustos = componenteCustos;
    }

    private Vector<VinculoMercadoriaComponenteCusto> componenteCustos;


    public Mercadoria getMercadoria() {
        return mercadoria;
    }

    public void setMercadoria(Mercadoria mercadoria) {
        this.mercadoria = mercadoria;
    }

    public MercadoriaXml()
    {
    }

    public MercadoriaXml(String referencia) throws Exception
    {
        mercadoria = ngimj.db.Mercadoria.get(referencia);
        componenteCustos = ngimj.db.VinculoMercadoriaComponenteCusto.get(referencia);
    }
}