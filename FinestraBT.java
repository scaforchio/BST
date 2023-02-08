import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Aggiungi qui una descrizione della classe FinestraBT
 *
 * @author (il tuo nome)
 * @version (un numero di versione o una data)
 */
public class FinestraBT extends JFrame implements ActionListener {

    JTextField JTFNodiDaAggiungere;
    JToggleButton JTBTab;
    BSTView v;
    BST albero;
    int pos=0;
    ArrayList<Riga> BSTTab = new ArrayList<Riga>();
    public FinestraBT(BST albero) {
        this.albero=albero;
        setSize(new Dimension(1200, 1000));
        NodoBT root=albero.getRadice();
        if (root!=null)
            setTitle("Albero con radice " + root.getInfo().toString());
        else
            setTitle("Albero vuoto");
    //    if (JTBTab.isSelected()) {
    //        tabella(albero);
    //        v = new BSTView(albero, 600, 40, 50, "tab",BSTTab);
    //    }
    //    else
            v = new BSTView(albero, 600, 40, 50,"tree",BSTTab);

        JScrollPane SP = new JScrollPane(v);
        Container CP=getContentPane();
        CP.setLayout(new BorderLayout());
        CP.add(SP,BorderLayout.CENTER);
        JPanel JPConsole=new JPanel();
        JPConsole.setLayout(new FlowLayout(FlowLayout.CENTER));
        JTFNodiDaAggiungere = new JTextField();
        JTFNodiDaAggiungere.setColumns(50);
        JButton JBAdd = new JButton("Add");
        JBAdd.addActionListener(this);
        JButton JBDel = new JButton("Del");
        JBDel.addActionListener(this);
        JButton JBBil = new JButton("Balance");
        JBBil.addActionListener(this);
        JTBTab = new JToggleButton("Table");
        JTBTab.addActionListener(this);
        JPConsole.add(JTFNodiDaAggiungere);
        JPConsole.add(JBAdd);

        JPConsole.add(JBDel);
        JPConsole.add(JBBil);
        JPConsole.add(JTBTab);
        CP.add(JPConsole, BorderLayout.NORTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String comando=actionEvent.getActionCommand();
        switch (comando)
        {
            case "Add": aggiungiNodi();
                        break;
            case "Del": eliminaNodi(); break;
            case "Balance": bilanciaAlbero(); break;
            case "Table": visualizzaTabella(); break;
        }
    }

    private void aggiungiNodi()
    {
        String nodiDaAggiungere=JTFNodiDaAggiungere.getText();
        String[] elencoNodi=nodiDaAggiungere.split(",");
        System.out.println("Qui");
        for (String info:elencoNodi) {
            info=info.trim();
            albero.inserisciNodo(info);
        }
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        // v.repaint();

    }
    private void eliminaNodi()
    {
        String nodiDaEliminare=JTFNodiDaAggiungere.getText();
        String[] elencoNodi=nodiDaEliminare.split(",");
        System.out.println("Qui");
        for (String info:elencoNodi) {
            info=info.trim();
            albero.cancellaNodo(info);
        }
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        // v.repaint();

    }
    private void bilanciaAlbero()
    {
          albero.bilanciamento();
          tabella(albero);
          v.ridisegna(JTBTab.isSelected());
         // v.repaint();

    }
    private void visualizzaTabella()
    {

        tabella(albero);
        v.ridisegna(JTBTab.isSelected());

    }
    public void tabella(BST t) {
        BSTTab.clear();
        pos=0;
        NodoBT r=t.getRadice();
        if (r != null)
            tabella(r);
        //FinestraBTTab a=new FinestraBTTab(BSTTab);
    }

    private void tabella(NodoBT r) {
        pos++;
        if (r != null) {
            if (r.getSinistra() == null && r.getDestra() == null)
                BSTTab.add(new Riga(pos,r.getInfo().toString(),0,0));
            if (r.getSinistra() != null && r.getDestra() == null) {
                BSTTab.add(new Riga(pos,r.getInfo().toString(),pos+1,0));
                tabella(r.getSinistra());
            }
            if (r.getSinistra() == null && r.getDestra() != null) {
                BSTTab.add(new Riga(pos,r.getInfo().toString(),0,(pos + 1 + BST.numeroNodi(r.getSinistra()))));

                tabella(r.getDestra());
            }
            if (r.getSinistra() != null && r.getDestra() != null) {
                BSTTab.add(new Riga(pos,r.getInfo().toString(),pos+1,(pos + 1 + BST.numeroNodi(r.getSinistra()))));
                tabella(r.getSinistra());
                tabella(r.getDestra());
            }
        }
    }

}
