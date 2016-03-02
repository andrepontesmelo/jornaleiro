package jornaleiro.dto.Elastic;

import com.sun.xml.txw2.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;

@XmlElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class HitsRoot {
    private int total;
    private HashMap<String,Object>[] hits;

    public HitsRoot() {
    }


    public HashMap<String,Object>[] getHits() {
        return hits;
    }

    public void setHits(HashMap<String,Object>[] hits) {
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
