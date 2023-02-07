import javax.swing.*;
import java.awt.*;

/**
 * Aggiungi qui una descrizione della classe FinestraBT
 *
 * @author (il tuo nome)
 * @version (un numero di versione o una data)
 */
public class FinestraBT extends JFrame {
    public FinestraBT(NodoBT root) {
        setSize(new Dimension(1200, 1000));
        setTitle("Albero con radice " + root.getInfo().toString());
        BSTView v = new BSTView(root, 600, 40, 50);
        //v.setPreferredSize(new Dimension(900,2000));
        JScrollPane SP = new JScrollPane(v);
        //SP.setSize(900,2000);
        //SP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(SP);
        setVisible(true);
    }
}
