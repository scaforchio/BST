import javax.swing.*;
import java.awt.*;

public class BSTView extends JPanel {

    private int x;
    private final int y;
    private final int size;
    private int dist;
    private final NodoBT root;
    public BSTView(NodoBT root, int x, int y, int size) {
        this.root = root;
        //  this.x = x;
        this.y = y;
        this.size = size;
        this.dist = x / 2;

        setBackground(Color.white);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.x = this.getWidth() / 2;
        dist = x / 2;
        g.setFont(new Font("Courier", Font.BOLD, 20));
        drawTree(g, root, x, y, size, dist);
    }

    private void drawTree(Graphics g, NodoBT node, int x, int y, int size, int dist) {

        ((Graphics2D) g).setStroke(new BasicStroke(3.0f));
        drawNodo(g, x, y, size / 2, node.getInfo().toString());

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
      //  if (getHeight()<y+size*2) setPreferredSize(new Dimension(getWidth()-20,y+size*2));

    }

    private void drawNodo(Graphics g, int x, int y, int r, String contenuto) {
        int lungContenuto = contenuto.length();
        if (lungContenuto <= 3) {
            g.drawOval(x - r, y - r, r * 2, r * 2);

        } else {
            g.drawOval(x - (lungContenuto * 13) / 2, y - r, (lungContenuto * 13), r * 2);

        }
        g.drawString(contenuto, x - (lungContenuto * 13) / 2 + 2, y + 8);

    }

}
