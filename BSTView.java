import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BSTView extends JPanel {
    private final int y;
    private final int size;
    private int dist;
    private NodoBT root;
    private final BST a;
    private Boolean tabella=false;
    ArrayList<Riga> BSTTab;
    JTable table;
    JScrollPane scrollPane;
    public BSTView(BST a, int x, int y, int size, ArrayList<Riga> BSTTab) {
        this.a=a;
        this.root = a.getRadice();
        //  this.x = x;
        this.y = y;
        this.size = size;
        this.dist = x / 2;
        this.BSTTab=BSTTab;
        table = new JTable(new ModelloBSTTab(BSTTab));
        scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!tabella) {
            scrollPane.setVisible(false);
            this.root = a.getRadice();
            int x = this.getWidth() / 2;
            dist = x / 2;
            g.setFont(new Font("Courier", Font.BOLD, 20));
            drawTree(g, root, x, y, size, dist);
        }
        else
        {
            scrollPane.setVisible(true);
            this.add(scrollPane);
        }
    }

    public void ridisegna(boolean tabella)
    {
        this.tabella=tabella;
        repaint();
    }

    private void drawTree(Graphics g, NodoBT node, int x, int y, int size, int dist) {

        ((Graphics2D) g).setStroke(new BasicStroke(3.0f));
        if (node!=null) {
           // drawNodo(g, x, y, size / 2, node.getInfo().toString());
            drawNodo(g, x, y, size / 2, normalizzaDouble(node.getInfo().toString()));
            if (node.getSinistra() != null) {
                int x1 = x - dist;
                int y1 = y + size * 2;
                g.drawLine(x, y + size / 2, x1, y1 - size / 2);
                drawTree(g, node.getSinistra(), x1, y1, size, dist / 2);
            }
            if (node.getDestra() != null) {
                int x2 = x + dist;
                int y2 = y + size * 2;
                g.drawLine(x, y + size / 2, x2, y2 - size / 2);
                drawTree(g, node.getDestra(), x2, y2, size, dist / 2);
            }
            if (node.getSinistra() != null) {
                drawTree(g, node.getSinistra(), x - dist, y + size * 2, size, dist / 2);
            }
            if (node.getDestra() != null) {
                drawTree(g, node.getDestra(), x + dist, y + size * 2, size, dist / 2);
            }
        }
     //   if (getHeight()<y+size*2)
     //   setPreferredSize(new Dimension(getWidth()-20,y+size*2));

    }

    private void drawNodo(Graphics g, int x, int y, int r, String contenuto) {
        int lungContenuto = contenuto.length();
        if (lungContenuto <= 3) {
            g.drawOval(x - r, y - r, r * 2, r * 2);
        } else {
            g.drawOval(x - (lungContenuto * 13) / 2, y - r, (lungContenuto * 13), r * 2);
        }
        g.drawString(contenuto, x - (lungContenuto * 13) / 2 + 2, y + 8);
      //  if (getHeight()<y+size*2)
      //      setPreferredSize(new Dimension(getWidth()-20,y+size*2));

    }
    public String normalizzaDouble(String a)
    {
        String pulita=a;
        if (a.length()>1)
        if (a.substring(a.length()-2,a.length()).equals(".0"))
            pulita=a.replace(".0","");
        return pulita;
    }
}
