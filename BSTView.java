import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class BSTView extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener{
    Boolean tabella = false;
    final FinestraBT fbt;
    ArrayList<Riga> BSTTab;
    ArrayList<NodoGrafico> ElencoNodi;
    ArrayList<Arco> ElencoArchi;
    JTable table;
    JScrollPane scrollPane;
    double zoomFactor = 1;
    double prevZoomFactor = 1;
    boolean zoomer;
    boolean dragger;
    boolean released;
    double xOffset = 0;
    double yOffset = 0;
    int xDiff;
    int yDiff;
    Point startPoint;

    public BSTView(ArrayList<Riga> BSTTab, ArrayList<NodoGrafico> ng, ArrayList<Arco> ar, FinestraBT fbt) {
        this.BSTTab = BSTTab;
        this.ElencoArchi = ar;
        this.ElencoNodi = ng;
        this.fbt = fbt;
        table = new JTable(new ModelloBSTTab(BSTTab));
        scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        addMouseListener(this);
        setBackground(Color.white);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g2.transform(at);
            zoomer = false;
        }

        if (dragger) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFactor, zoomFactor);
            g2.transform(at);

            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragger = false;
            }
        }

        if (!tabella) {
            scrollPane.setVisible(false);

            int x = this.getWidth() / 2;
            g.setFont(new Font("Courier", Font.BOLD, 20));
            disegnaNodi(g2);
            disegnaArchi(g2);
        } else {
            scrollPane.setVisible(true);
            this.add(scrollPane);
        }
    }

    public void ridisegna(boolean tabella) {
        this.tabella = tabella;
        repaint();
    }

    public void disegnaNodi(Graphics2D g2) {
        g2.setStroke(new BasicStroke(3.0f));
        for (NodoGrafico n : ElencoNodi) {
            int lungContenuto = n.getContenuto().length();
            if (n.getColore() != Color.white) {
                g2.setColor(n.getColore());
                g2.fillOval(n.getX() - n.getLarghezza() / 2, n.getY() - n.getAltezza() / 2, n.getLarghezza(), n.getAltezza());
            }
            g2.setColor(Color.black);
            g2.drawOval(n.getX() - n.getLarghezza() / 2, n.getY() - n.getAltezza() / 2, n.getLarghezza(), n.getAltezza());
            g2.drawString(n.getContenuto(), n.getX() - (lungContenuto * 13) / 2 + 2, n.getY() + 8);
        }
    }

    public void disegnaArchi(Graphics2D g2) {
        g2.setStroke(new BasicStroke(3.0f));
        for (Arco a : ElencoArchi) {
            g2.setColor(a.getColore());
            g2.drawLine(a.getxStart(), a.getyStart(), a.getxEnd(), a.getyEnd());
            g2.drawString(a.getLabel(), (a.getxStart() + a.getxEnd()) / 2 + 10, (a.getyStart() + a.getyEnd()) / 2);
        }
    }

    public void zoomIn() {
        zoomFactor *= 1.1;
        repaint();
    }

    // Metodo per lo zoom out
    public void zoomOut() {
        zoomFactor /= 1.1;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (fbt.JTBTab.isEnabled()) {
            int coordX = mouseEvent.getX();
            int coordY = mouseEvent.getY();
            for (NodoGrafico n : ElencoNodi) {
                if ((coordX > n.getX() - n.getLarghezza() / 2) & (coordY > n.getY() - n.getAltezza() / 2) & (coordX < n.getX() + n.getLarghezza() / 2) & (coordY < n.getY() + n.getAltezza() / 2)) {
                    if (!n.getColore().equals(Color.green)) {
                        n.setColore(Color.green);

                        if (fbt.JCBNum.isSelected())
                            fbt.cambiaListaSelezionati("Add", Double.parseDouble(n.getContenuto()));
                        else
                            fbt.cambiaListaSelezionati("Add", n.getContenuto());
                        fbt.resettaSuccPred();
                        repaint();
                    } else {
                        n.setColore(Color.white);

                        if (fbt.JCBNum.isSelected())
                            fbt.cambiaListaSelezionati("Del", Double.parseDouble(n.getContenuto()));
                        else
                            fbt.cambiaListaSelezionati("Del", n.getContenuto());
                        repaint();
                        fbt.resettaSuccPred();
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        released = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        dragger = true;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        zoomer = true;

        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }

        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }
}
