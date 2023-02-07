
public class NodoBT {
    Comparable info;

    NodoBT sinistra;
    NodoBT destra;

    public NodoBT() {
    }

    public NodoBT(Comparable i) {
        info = i;
    }

    public Comparable getInfo() {
        return info;
    }

    public void setInfo(Comparable i) {
        info = i;
    }

    public NodoBT getSinistra() {
        return sinistra;
    }

    public NodoBT getDestra() {
        return destra;
    }

    public void setSinistra(NodoBT s) {
        sinistra = s;
    }

    public void setDestra(NodoBT d) {
        destra = d;
    }

    public int compareTo(NodoBT n) {
        return getInfo().compareTo(n.getInfo());
    }

}
