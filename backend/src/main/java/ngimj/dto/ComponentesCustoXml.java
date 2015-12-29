package ngimj.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;
import java.util.Vector;

@XmlRootElement
public class ComponentesCustoXml {

    private Vector<ComponenteCusto> componentes;

    public Vector<ComponenteCusto> getComponentes() {
        return componentes;
    }
    public void setComponentes(Vector<ComponenteCusto> componentes) {
        this.componentes = componentes;
    }

    public ComponentesCustoXml()
    {
        try {
            this.componentes = ngimj.db.ComponenteCusto.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}