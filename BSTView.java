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
    ArrayList<Riga> BSTTab;
//
    ArrayList<NodoGrafico> ElencoNodi;
    ArrayList<Arco> ElencoArchi;

    JTable table;
    JScrollPane scrollPane;

    public BSTView(BST a, int x, int y, int size, ArrayList<Riga> BSTTab,ArrayList<NodoGrafico> ng,ArrayList<Arco> ar) {
        this.a = a;
        this.root = a.getRadice();
        //  this.x = x;
        this.y = y;
        this.size = size;
        this.dist = x / 2;
        this.BSTTab = BSTTab;
        this.ElencoArchi=ar;
        this.ElencoNodi=ng;
        table = new JTable(new ModelloBSTTab(BSTTab));
        scrollPane = new JScrollPane(table);
        this.add(scrollPane);
      //  creaAlberoGrafico(root, x, y, size, dist);
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
  //          ElencoNodi.clear();
  //          ElencoArchi.clear();
  //          creaAlberoGrafico(root, x, y, size, dist);
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
/*
    private void creaAlberoGrafico(NodoBT node, int x, int y, int size, int dist) {
        if (node != null) {
            creaNodo(x, y, size / 2, normalizzaDouble(node.getInfo().toString()), Color.white);
            if (node.getSinistra() != null) {
                int x1 = x - dist;
                int y1 = y + size * 2;
                ElencoArchi.add(new Arco(x,x1,y+size/2,y1-size/2,Color.black,""));
                creaAlberoGrafico(node.getSinistra(), x1, y1, size, dist / 2);
            }
            if (node.getDestra() != null) {
                int x2 = x + dist;
                int y2 = y + size * 2;
                ElencoArchi.add(new Arco(x,x2,y+size/2,y2-size/2,Color.black,""));
                creaAlberoGrafico(node.getDestra(), x2, y2, size, dist / 2);
            }

        }
    }

    private void creaNodo(int x, int y, int r, String contenuto, Color colore) {
        int lungContenuto = contenuto.length();
        int larghezza = r;
        if (lungContenuto <= 3) {
            larghezza=r*2;

        } else {
            larghezza=lungContenuto*13;

        }

        ElencoNodi.add(new NodoGrafico(x, y, larghezza, r * 2, Color.white, contenuto));

    }
*/
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

    public String normalizzaDouble(String a) {
        String pulita = a;
        if (a.length() > 1)
            if (a.substring(a.length() - 2, a.length()).equals(".0"))
                pulita = a.replace(".0", "");
        return pulita;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int coordX = mouseEvent.getX();
        int coordY = mouseEvent.getY();

        for (NodoGrafico n : ElencoNodi) {
            if ((coordX > n.getX()-n.getLarghezza()/2) & (coordY > n.getY()-n.getAltezza()/2) & (coordX < n.getX()+n.getLarghezza()/2) & (coordY < n.getY()+n.getAltezza()/2    )){
                n.setColore(Color.green);
                disegnaNodi();
                System.out.println("Nodo " + n.getContenuto());
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
