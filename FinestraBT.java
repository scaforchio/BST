import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class FinestraBT extends JFrame implements ActionListener, ComponentListener, DocumentListener {
    JTextField JTFNodiDaElaborare;
    JCheckBox JTBTab;
    JCheckBox JCBNum;
    JEditorPane JEPConsole;
    JButton JBAdd;
    JButton JBDel;
    JButton JBPredecessore;
    JButton JBSuccessore;
    BSTView v;
    BST albero;
    int pos = 0;
    int dist;
    public static ArrayList<String> listaSelezionati=new ArrayList<String>();

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
        v = new BSTView(albero, 600, 40, 50, BSTTab,ElencoNodi,ElencoArchi,this);
        JScrollPane SP = new JScrollPane(v);
        Container CP = getContentPane();
        CP.setLayout(new BorderLayout());
        CP.add(SP, BorderLayout.CENTER);
        JPanel JPComandi = new JPanel();
        JPanel JPCostruzione = new JPanel();
        JPanel JPEsercizi = new JPanel();
        JPCostruzione.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPEsercizi.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPComandi.setLayout(new GridLayout(2,1));
        JPComandi.add(JPCostruzione);
        JPComandi.add(JPEsercizi);
        //JPComandi.setLayout(new GridBagLayout());
        JLabel JLNodi = new JLabel("Nodes (separated by commas)");
        JTFNodiDaElaborare = new JTextField();
        JTFNodiDaElaborare.setColumns(30);
        Document JTFDNodiDaElaborare=JTFNodiDaElaborare.getDocument();
        JTFDNodiDaElaborare.addDocumentListener(this);
        JBAdd = new JButton("Add");
        JBAdd.addActionListener(this);
        JBAdd.setEnabled(false);
        JBDel = new JButton("Del");
        JBDel.addActionListener(this);
        JBDel.setEnabled(false);
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
        JBPredecessore = new JButton("Predecessor");
        JBPredecessore.addActionListener(this);
        JBPredecessore.setEnabled(false);

        JBSuccessore = new JButton("Successor");
        JBSuccessore.addActionListener(this);
        JBSuccessore.setEnabled(false);
        JPCostruzione.add(JLNodi);
        JPCostruzione.add(JCBNum);
        JPCostruzione.add(JTFNodiDaElaborare);
        JPCostruzione.add(JBAdd);
        JPCostruzione.add(JBDel);
       // JPCostruzione.add(new JLabel("                 "));
        JPEsercizi.add(JBBil);
        JPEsercizi.add(JTBTab);
        JPEsercizi.add(JBInorder);
        JPEsercizi.add(JBPreorder);
        JPEsercizi.add(JBPostorder);
        JPEsercizi.add(JBSuccessore);
        JPEsercizi.add(JBPredecessore);
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

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String comando = actionEvent.getActionCommand();
        //System.out.println(comando);
        switch (comando) {
            case "Add":
                if (!JTFNodiDaElaborare.getText().trim().equals("")) {
                    aggiungiNodi(JTFNodiDaElaborare.getText());
                    int dimx=this.getWidth()/2;
                    creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
                }
                break;
            case "Del":
                if (!JTFNodiDaElaborare.getText().trim().equals("")) {
                    eliminaNodi(JTFNodiDaElaborare.getText());
                }
                else if(listaSelezionati.size()>0)
                {
                    for(String cn:listaSelezionati)
                    {
                        eliminaNodi(cn);
                    }

                }
                int dimx=this.getWidth()/2;
                creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
                listaSelezionati.clear();
                abilitaDisabilitaPulsanti();
                break;
            case "Balance":
                bilanciaAlbero();
                ElencoArchi.clear();
                dimx=this.getWidth()/2;
                creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
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
            case "Predecessor":
                predecessore();
                break;
            case "Successor":
                successore();
                break;
        }

        JTFNodiDaElaborare.setText("");
        if (albero.getRadice() != null)
            JCBNum.setEnabled(false);
        else
            JCBNum.setEnabled(true);
        JTFNodiDaElaborare.requestFocus();
    }

    private void aggiungiNodi(String nodiDaAggiungere) {

      //  String nodiDaAggiungere = JTFNodiDaElaborare.getText();
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

    private void eliminaNodi(String nodiDaEliminare) {
      //  String nodiDaEliminare = JTFNodiDaElaborare.getText();
        String[] elencoNodi = nodiDaEliminare.split(",");
        JTFNodiDaElaborare.setText("");

        for (String info : elencoNodi) {
            info = info.trim();
            if (JCBNum.isSelected()) {
                albero.cancellaNodo(Double.parseDouble(info));
                eliminaNodoGrafico(normalizzaDouble(""+Double.parseDouble(info)));
            }
            else {
                albero.cancellaNodo(info);
                eliminaNodoGrafico(info);
            }


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
        ArrayList<Comparable> lista = new ArrayList<Comparable>();
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
                messaggio += lista.get(i).toString() + "";
            ArrayList<NodoGrafico> ElencoNodi = new ArrayList<NodoGrafico>();
        }
        messaggio += "<br><br></b></html>";
        JEPConsole.setText(messaggio);
        JEPConsole.setEnabled(true);
    }
    private void successore(){
       NodoBT nodoSelezionato=albero.ricercaNodo(listaSelezionati.get(0));
       NodoBT succ=albero.trovaSuccessore(albero.getRadice(),nodoSelezionato);
       String contenutoSuccessore=succ.getInfo().toString();
       segnalaInRosso(contenutoSuccessore);
       String messaggio="<br>SUCC: <b>"+succ.getInfo()+"</b><br><br>";
       JEPConsole.setText(messaggio);
       JEPConsole.setEnabled(true);

    }
    private void predecessore(){
        NodoBT nodoSelezionato=albero.ricercaNodo(listaSelezionati.get(0));
        NodoBT pred=albero.trovaPredecessore(albero.getRadice(),nodoSelezionato);
        String contenutoPredecessore=pred.getInfo().toString();
        segnalaInRosso(contenutoPredecessore);
        String messaggio="<br>PRED: <b>"+pred.getInfo()+"</b><br><br>";
        JEPConsole.setText(messaggio);
        JEPConsole.setEnabled(true);
    }
    public void segnalaInRosso(String contNodo)
    {
        for (NodoGrafico n:ElencoNodi)
        {
            if(n.getContenuto().equals(contNodo))
                n.setColore(Color.red);
        }
        v.repaint();
    }
    public void svuotaConsole() {
        String messaggio = "<html><br><b>";
        messaggio += "<br><br></b></html>";
        JEPConsole.setText(messaggio);
    }

    public void cambiaListaSelezionati(String tipo, String contenutoNodo)
    {
        if (tipo.equals("Add"))
            listaSelezionati.add(contenutoNodo);
        else
            listaSelezionati.remove(contenutoNodo);
        abilitaDisabilitaPulsanti();
       // System.out.println(listaSelezionati.size());
    }
    private void eliminaNodoGrafico(String info)
    {
        for(int i=0;i<ElencoNodi.size();i++)
        {
            if (info.equals(ElencoNodi.get(i).getContenuto()))
            {
                ElencoNodi.remove(i);
                ElencoArchi.clear();
                break;
            }
        }
    }
    private void creaAlberoGrafico(NodoBT node, int x, int y, int size, int dist) {

        if (node != null) {
            NodoGrafico n= cercaNodoGrafico(normalizzaDouble(node.getInfo().toString()));
            if (n==null)
                creaNodo(x, y, size / 2, normalizzaDouble(node.getInfo().toString()), Color.white);
            else {
                n.setX(x);
                n.setY(y);
            }
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

    private NodoGrafico cercaNodoGrafico(String a)
    {
        for (NodoGrafico n : ElencoNodi)
        {
            if (n.getContenuto().equals(a))
                return n;
        }
        return null;
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
      //  ElencoNodi.clear();
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

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        abilitaDisabilitaPulsanti();
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        abilitaDisabilitaPulsanti();
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        abilitaDisabilitaPulsanti();
    }
    private void abilitaDisabilitaPulsanti()
    {
        if (JTFNodiDaElaborare.getText().length()>0)
        {
            JBAdd.setEnabled(true);
            JBDel.setEnabled(true);
        }
        else {
            JBAdd.setEnabled(false);
            JBDel.setEnabled(false);
        }
        if (listaSelezionati.size()>0)
            JBDel.setEnabled(true);
        if (listaSelezionati.size()==1) {
            JBPredecessore.setEnabled(true);
            JBSuccessore.setEnabled(true);
        }
        else {
            JBPredecessore.setEnabled(false);
            JBSuccessore.setEnabled(false);
        }
    }
}

