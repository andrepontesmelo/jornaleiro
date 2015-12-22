package ngimj.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;

@XmlRootElement
public class NovosPrecosXml {

    private NovosPrecos novosPrecos;

    public NovosPrecosXml(String mercadoria) throws SQLException {
        novosPrecos = ngimj.db.NovosPrecos.get(mercadoria);
    }

    public NovosPrecos getNovosPrecos() {
        return novosPrecos;
    }

    public void setNovosPrecos(NovosPrecos novosPrecos) {
        this.novosPrecos = novosPrecos;
    }
}
