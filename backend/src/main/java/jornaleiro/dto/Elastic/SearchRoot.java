package jornaleiro.dto.Elastic;

import com.sun.xml.txw2.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRoot {
    private int took;
    private boolean timed_out;
    private Shards _shards;
    private HitsRoot hits;
    private double elapsedTime;

    public SearchRoot() {
    }

    public int getTook() {
        return took;
    }
    public void setTook(int took) {
        this.took = took;
    }
    public boolean isTimed_out() {
        return timed_out;
    }
    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }
    public Shards get_shards() {
        return _shards;
    }
    public void set_shards(Shards _shards) {
        this._shards = _shards;
    }
    public HitsRoot getHits() { return hits; }
    public void setHits(HitsRoot hits) {
        this.hits = hits;
    }
    public double getElapsedTime() { return elapsedTime; }
    public void setElapsedTime(double elapsedTime) { this.elapsedTime = elapsedTime; }
}
