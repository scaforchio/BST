import java.awt.*;

public class Arco {
    int xStart;
    int xEnd;
    int yStart;
    int yEnd;
    Color colore;
    String label;

    public Arco(int xStart, int xEnd, int yStart, int yEnd, Color colore,String label) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.colore = colore;
        this.label=label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Color getColore() {
        return colore;
    }

    public void setColore(Color colore) {
        this.colore = colore;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }
}
