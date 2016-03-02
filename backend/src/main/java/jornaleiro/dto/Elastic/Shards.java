package jornaleiro.dto.Elastic;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Shards {
    private int total;
    private int successful;
    private int failed;

    public Shards() {
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getSuccessful() {
        return successful;
    }
    public void setSuccessful(int successful) {
        this.successful = successful;
    }
    public int getFailed() {
        return failed;
    }
    public void setFailed(int failed) {
        this.failed = failed;
    }

}
