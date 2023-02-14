import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class FinestraBT extends JFrame implements ActionListener, ComponentListener {
    JTextField JTFNodiDaElaborare;
    JCheckBox JTBTab;
    JCheckBox JCBNum;
    JEditorPane JEPConsole;
    BSTView v;
    BST albero;
    int pos = 0;
    int dist;
    ArrayList<Riga> BSTTab = new ArrayList<Riga>();
    ArrayList<NodoGrafico> ElencoNodi = new ArrayList<NodoGrafico>();
    ArrayList<Arco> ElencoArchi = new ArrayList<Arco>();

    public FinestraBT(BST albero) throws Exception {
        this.albero = albero;
        setSize(new Dimension(1200, 1000));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addComponentListener(this);
        NodoBT root = albero.getRadice();
        if (root != null)
            setTitle("Albero con radice " + root.getInfo().toString());
        else
            setTitle("Albero vuoto");
        v = new BSTView(albero, 600, 40, 50, BSTTab,ElencoNodi,ElencoArchi);
        JScrollPane SP = new JScrollPane(v);
        Container CP = getContentPane();
        CP.setLayout(new BorderLayout());
        CP.add(SP, BorderLayout.CENTER);
        JPanel JPComandi = new JPanel();
        JPComandi.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPComandi.setLayout(new GridBagLayout());
        JLabel JLNodi = new JLabel("Nodes (separated by commas)");
        JTFNodiDaElaborare = new JTextField();
        JTFNodiDaElaborare.setColumns(30);
        JButton JBAdd = new JButton("Add");
        JBAdd.addActionListener(this);
        JButton JBDel = new JButton("Del");
        JBDel.addActionListener(this);
        JButton JBBil = new JButton("Balance");
        JBBil.addActionListener(this);
        JTBTab = new JCheckBox("Table");
        JTBTab.addActionListener(this);
        JCBNum = new JCheckBox("Numeric order");
        JButton JBInorder = new JButton("Inorder");
        JBInorder.addActionListener(this);
        JButton JBPreorder = new JButton("Preorder");
        JBPreorder.addActionListener(this);
        JButton JBPostorder = new JButton("Postorder");
        JBPostorder.addActionListener(this);
        JPComandi.add(JLNodi);
        JPComandi.add(JCBNum);
        JPComandi.add(JTFNodiDaElaborare);
        JPComandi.add(JBAdd);
        JPComandi.add(JBDel);
        JPComandi.add(new JLabel("                 "));
        JPComandi.add(JBBil);
        JPComandi.add(JTBTab);
        JPComandi.add(JBInorder);
        JPComandi.add(JBPreorder);
        JPComandi.add(JBPostorder);
        getRootPane().setDefaultButton(JBAdd);


        JEPConsole = new JEditorPane();
        JEPConsole.setContentType("text/html");
        JEPConsole.setText("<html><br><br><br><br></html>");
        JEPConsole.setEditable(false);
        CP.add(JPComandi, BorderLayout.NORTH);
        CP.add(JEPConsole, BorderLayout.SOUTH);

        setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dist = (int) screenSize.getWidth()/2;


        System.out.println("Dist "+dist);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String comando = actionEvent.getActionCommand();

        switch (comando) {
            case "Add":
                if (!JTFNodiDaElaborare.getText().trim().equals("")) {
                    aggiungiNodi();
                    creaAlberoGrafico(albero.getRadice(),dist,40,50,dist/2);
                }
                break;
            case "Del":
                if (!JTFNodiDaElaborare.getText().trim().equals("")) {
                    eliminaNodi();
                    creaAlberoGrafico(albero.getRadice(),dist,40,50,dist/2);
                }
                break;
            case "Balance":
                bilanciaAlbero();
                break;
            case "Table":
                visualizzaTabella();
                break;
            case "Inorder":
                attraversamento("IN");
                break;
            case "Preorder":
                attraversamento("PRE");
                break;
            case "Postorder":
                attraversamento("POST");
                break;
        }
        JTFNodiDaElaborare.setText("");
        if (albero.getRadice() != null)
            JCBNum.setEnabled(false);
        else
            JCBNum.setEnabled(true);
        JTFNodiDaElaborare.requestFocus();
    }

    private void aggiungiNodi() {

        String nodiDaAggiungere = JTFNodiDaElaborare.getText();
        String[] elencoNodi = nodiDaAggiungere.split(",");

        for (String info : elencoNodi) {
            info = info.trim();
            if (!JCBNum.isSelected())
                albero.inserisciNodo(info);
            else {
                try {
                    albero.inserisciNodo(Double.parseDouble(info));
                } catch (Exception e) {
                    System.out.println("Valore non numerico!");
                }

            }
        }
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();
    }

    private void eliminaNodi() {
        String nodiDaEliminare = JTFNodiDaElaborare.getText();
        String[] elencoNodi = nodiDaEliminare.split(",");
        JTFNodiDaElaborare.setText("");

        for (String info : elencoNodi) {
            info = info.trim();
            if (JCBNum.isSelected())
                albero.cancellaNodo(Double.parseDouble(info));
            else
                albero.cancellaNodo(info);
        }
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();
    }

    private void bilanciaAlbero() {
        albero.bilanciamento();
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();
    }

    private void visualizzaTabella() {
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();
    }

    public void tabella(BST t) {
        BSTTab.clear();
        pos = 0;
        NodoBT r = t.getRadice();
        if (r != null)
            tabella(r);
    }

    private void tabella(NodoBT r) {
        pos++;
        if (r != null) {
            if (r.getSinistra() == null && r.getDestra() == null)
                BSTTab.add(new Riga(pos, r.getInfo().toString(), 0, 0));
            if (r.getSinistra() != null && r.getDestra() == null) {
                BSTTab.add(new Riga(pos, r.getInfo().toString(), pos + 1, 0));
                tabella(r.getSinistra());
            }
            if (r.getSinistra() == null && r.getDestra() != null) {
                BSTTab.add(new Riga(pos, r.getInfo().toString(), 0, (pos + 1 + BST.numeroNodi(r.getSinistra()))));
                tabella(r.getDestra());
            }
            if (r.getSinistra() != null && r.getDestra() != null) {
                BSTTab.add(new Riga(pos, r.getInfo().toString(), pos + 1, (pos + 1 + BST.numeroNodi(r.getSinistra()))));
                tabella(r.getSinistra());
                tabella(r.getDestra());
            }
        }
    }

    public void attraversamento(String ordine) {
        ArrayList<Comparable> lista = null;
        String messaggio = "";
        switch (ordine) {
            case "IN":
                messaggio = "<html><br>INORDER: <b>";
                lista = albero.attraversamentoSimmetrico();
                break;
            case "PRE":
                messaggio = "<html><br>PREORDER: <b>";
                lista = albero.attraversamentoAnticipato();
                break;
            case "POST":
                messaggio = "<html><br>POSTORDER: <b>";
                lista = albero.attraversamentoPosticipato();
                break;
        }
        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1)
                messaggio += lista.get(i).toString() + " , ";
            else
                messaggio += lista.get(i).toString() + "";ArrayList<NodoGrafico> ElencoNodi = new ArrayList<NodoGrafico>();
//    ArrayList<Arco> ElencoArchi = new ArrayList<Arco>();

        }
        messaggio += "<br><br></b></html>";
        JEPConsole.setText(messaggio);
        JEPConsole.setEnabled(true);
    }

    public void svuotaConsole() {
        String messaggio = "<html><br><b>";
        messaggio += "<br><br></b></html>";
        JEPConsole.setText(messaggio);
    }

    private void creaAlberoGrafico(NodoBT node, int x, int y, int size, int dist) {
        System.out.println("X: "+x);
        System.out.println("X: "+y);
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

    public String normalizzaDouble(String a) {
        String pulita = a;
        if (a.length() > 1)
            if (a.substring(a.length() - 2, a.length()).equals(".0"))
                pulita = a.replace(".0", "");
        return pulita;
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        ElencoArchi.clear();
        ElencoNodi.clear();
        NodoBT rad=albero.getRadice();
        int dimx=this.getWidth()/2;
        creaAlberoGrafico(rad, dimx, 40, 50, dimx/2);
        v.ridisegna(false);
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }
}
