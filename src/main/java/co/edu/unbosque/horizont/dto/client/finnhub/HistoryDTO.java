package co.edu.unbosque.horizont.dto.client.finnhub;

import java.util.Collections;
import java.util.List;

/**
 * Payload con timestamps y precios de cierre.
 */
public class HistoryDTO {
    private String status;
    private List<Long> t;
    private List<Double> c;
    private List<Double> o;
    private List<Double> h;
    private List<Double> l;
    private List<Long> v;

    public HistoryDTO() {
        this.t = Collections.emptyList();
        this.c = Collections.emptyList();
    }

    public HistoryDTO(List<Long> t, List<Double> c) {
        this.t = t;
        this.c = c;
    }

    public static HistoryDTO empty() {
        return new HistoryDTO(Collections.emptyList(), Collections.emptyList());
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Double> getO() {
        return o;
    }

    public void setO(List<Double> o) {
        this.o = o;
    }

    public List<Double> getH() {
        return h;
    }

    public void setH(List<Double> h) {
        this.h = h;
    }

    public List<Double> getL() {
        return l;
    }

    public void setL(List<Double> l) {
        this.l = l;
    }

    public List<Long> getV() {
        return v;
    }

    public void setV(List<Long> v) {
        this.v = v;
    }

    public List<Long> getT() { return t; }
    public void setT(List<Long> t) { this.t = t; }

    public List<Double> getC() { return c; }
    public void setC(List<Double> c) { this.c = c; }

    public String getStatus() {
        return status;
    }

}