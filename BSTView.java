import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BSTView extends JPanel implements MouseListener {
    private final int y;
    private final int size;
    private int dist;
    private NodoBT root;
    private final BST a;
    private Boolean tabella = false;
    private Graphics g;
    private FinestraBT fbt;
    ArrayList<Riga> BSTTab;
    ArrayList<NodoGrafico> ElencoNodi;
    ArrayList<Arco> ElencoArchi;
    JTable table;
    JScrollPane scrollPane;
    public BSTView(BST a, int x, int y, int size, ArrayList<Riga> BSTTab,ArrayList<NodoGrafico> ng,ArrayList<Arco> ar,FinestraBT fbt) {
        this.a = a;
        this.root = a.getRadice();
        this.y = y;
        this.size = size;
        this.dist = x / 2;
        this.BSTTab = BSTTab;
        this.ElencoArchi=ar;
        this.ElencoNodi=ng;
        this.fbt=fbt;
        table = new JTable(new ModelloBSTTab(BSTTab));
        scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        addMouseListener(this);
        setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g=g;
        if (!tabella) {
            scrollPane.setVisible(false);
            this.root = a.getRadice();
            int x = this.getWidth() / 2;
            dist = x / 2;
            g.setFont(new Font("Courier", Font.BOLD, 20));
            disegnaNodi();
            disegnaArchi();
        } else {
            scrollPane.setVisible(true);
            this.add(scrollPane);
        }
    }

    public void ridisegna(boolean tabella) {
        this.tabella = tabella;
        repaint();
    }

    public void disegnaNodi() {
        ((Graphics2D) g).setStroke(new BasicStroke(3.0f));
        for (NodoGrafico n : ElencoNodi) {
            int lungContenuto = n.getContenuto().length();
            if (n.getColore()!=Color.white) {
                g.setColor(n.getColore());
                g.fillOval(n.getX() - n.getLarghezza() / 2, n.getY() - n.getAltezza() / 2, n.getLarghezza(), n.getAltezza());
            }
            g.setColor(Color.black);
            g.drawOval(n.getX()-n.getLarghezza()/2, n.getY()-n.getAltezza()/2, n.getLarghezza(), n.getAltezza());
            g.drawString(n.getContenuto(), n.getX() - (lungContenuto * 13) / 2 + 2, n.getY() + 8);
        }
    }
    public void disegnaArchi() {
        ((Graphics2D) g).setStroke(new BasicStroke(3.0f));
        for (Arco a : ElencoArchi) {
            g.setColor(a.getColore());
            g.drawLine(a.getxStart(),a.getyStart(),a.getxEnd(),a.getyEnd());
            g.drawString(a.getLabel(), (a.getxStart()+a.getxEnd())/2+10,(a.getyStart()+a.getyEnd())/2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int coordX = mouseEvent.getX();
        int coordY = mouseEvent.getY();
        for (NodoGrafico n : ElencoNodi) {
            if ((coordX > n.getX()-n.getLarghezza()/2) & (coordY > n.getY()-n.getAltezza()/2) & (coordX < n.getX()+n.getLarghezza()/2) & (coordY < n.getY()+n.getAltezza()/2    )){
                if (n.getColore().equals(Color.white)) {
                    n.setColore(Color.green);
                 //   FinestraBT.listaSelezionati.add(n.getContenuto());
                    fbt.cambiaListaSelezionati("Add",n.getContenuto());
                    repaint();
                }
                else {
                    n.setColore(Color.white);
                 //   FinestraBT.listaSelezionati.remove(n.getContenuto());
                    fbt.cambiaListaSelezionati("Del",n.getContenuto());
                    repaint();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
