import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Aggiungi qui una descrizione della classe FinestraBT
 *
 * @author (il tuo nome)
 * @version (un numero di versione o una data)
 */
public class FinestraBTTab extends JFrame {
    public FinestraBTTab(ArrayList<Riga> TabList) {
        setSize(new Dimension(1200, 1000));
        setTitle("Tabella albero");
        BSTTabView v = new BSTTabView(TabList);
        //v.setPreferredSize(new Dimension(900,2000));
        //JScrollPane SP = new JScrollPane(v);
        //SP.setSize(900,2000);
        //SP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(v);
        setVisible(true);
    }
}
