class Riga
{
    int posizione;
    String valore;
    int posSx;
    int posDx;

    public Riga(int posizione, String valore, int posSx, int posDx) {
        this.posizione = posizione;
        this.valore = valore;
        this.posSx = posSx;
        this.posDx = posDx;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public int getPosSx() {
        return posSx;
    }

    public void setPosSx(int posSx) {
        this.posSx = posSx;
    }

    public int getPosDx() {
        return posDx;
    }

    public void setPosDx(int posDx) {
        this.posDx = posDx;
    }
}