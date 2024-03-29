import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FinestraBT extends JFrame implements ActionListener, ComponentListener, DocumentListener, KeyListener {
    JTextField JTFNodiDaElaborare;
    JCheckBox JTBTab;
    JCheckBox JCBNum;
    JCheckBox JCDPredecessor;
    JEditorPane JEPConsole;
    JButton JBAdd;
    JButton JBDel;
    JButton JBSearch;
    JButton JBPredecessore;
    JButton JBSuccessore;
    JButton JBPreorder;
    JButton JBPostorder;
    JButton JBInorder;
    JButton JBAddRandom;
    JButton JBReset;
    JButton JBBil;
    String ultimoAttraversato="";
    String nodoDaCercare;
    BSTView v;
    BST albero;
    Timer timerAttraversamento;
    Timer timerRicerca;
    int poslista=-1;
    int pos = 0;
    //   int dist;
    public static ArrayList<Comparable> listaSelezionati= new ArrayList<>();
    ArrayList<Riga> BSTTab = new ArrayList<Riga>();
    ArrayList<NodoGrafico> ElencoNodi = new ArrayList<NodoGrafico>();
    ArrayList<Arco> ElencoArchi = new ArrayList<Arco>();
    ArrayList<Comparable> lista;

    public FinestraBT(BST albero) throws Exception {
        this.albero = albero;
        setSize(new Dimension(1000, 1000));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addComponentListener(this);
        NodoBT root = albero.getRadice();
        setTitle("Binary search tree");
        v = new BSTView(BSTTab,ElencoNodi,ElencoArchi,this);
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
        JLabel JLZoom = new JLabel("Per zoom fare scroll     ");
        JLabel JLNodi = new JLabel("Nodes (separated by commas)");
        JTFNodiDaElaborare = new JTextField();
        JTFNodiDaElaborare.setColumns(30);
        Document JTFDNodiDaElaborare=JTFNodiDaElaborare.getDocument();
        JTFDNodiDaElaborare.addDocumentListener(this);
        JTFNodiDaElaborare.addKeyListener(this);
        JBAdd = new JButton("Add");
        JBAdd.addActionListener(this);
        JBAdd.setEnabled(false);
        JBDel = new JButton("Del");
        JBDel.addActionListener(this);
        JBDel.setEnabled(false);
        JCDPredecessor=new JCheckBox("Del predecessor");
        JCDPredecessor.addActionListener(this);
        JBSearch = new JButton("Search");
        JBSearch.addActionListener(this);
        JBSearch.setEnabled(false);
        JBAddRandom = new JButton("Random tree");
        JBAddRandom.addActionListener(this);
        JBReset = new JButton("Reset");
        JBReset.addActionListener(this);
        JBBil = new JButton("Balance");
        JBBil.addActionListener(this);
        JTBTab = new JCheckBox("Table");
        JTBTab.addActionListener(this);
        JCBNum = new JCheckBox("Numeric order");
        JBInorder = new JButton("Inorder");
        JBInorder.addActionListener(this);
        JBPreorder = new JButton("Preorder");
        JBPreorder.addActionListener(this);
        JBPostorder = new JButton("Postorder");
        JBPostorder.addActionListener(this);
        JBPredecessore = new JButton("Predecessor");
        JBPredecessore.addActionListener(this);
        JBPredecessore.setEnabled(false);

        JPCostruzione.add(JLZoom);
        JBSuccessore = new JButton("Successor");
        JBSuccessore.addActionListener(this);
        JBSuccessore.setEnabled(false);
        JPCostruzione.add(JLNodi);
        JPCostruzione.add(JCBNum);
        JPCostruzione.add(JTFNodiDaElaborare);
        JPCostruzione.add(JBAdd);
        JPCostruzione.add(JBDel);
        JPCostruzione.add(JCDPredecessor);
        JPCostruzione.add(JBSearch);
        JPCostruzione.add(new JLabel("                 "));
        JPCostruzione.add(JBAddRandom);
        JPCostruzione.add(JBReset);
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
        //   dist = (int) screenSize.getWidth()/2;
        timerAttraversamento =new Timer(1000,this);
        timerAttraversamento.setActionCommand("TimeAttr");
        timerRicerca =new Timer(1000,this);
        timerRicerca.setActionCommand("TimeRice");

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String comando = actionEvent.getActionCommand();
        int dimx=0;

        switch (comando) {
            case "Add":
                if (!JTFNodiDaElaborare.getText().trim().equals("")) {
                    aggiungiNodi(JTFNodiDaElaborare.getText());
                    dimx=this.getWidth()/2;
                    creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
                }
                break;
            case "Del":
                eliminaNodi();
                break;
            case "Search":
                nodoDaCercare=JTFNodiDaElaborare.getText();
                ricerca();
                break;
            case "Random tree":
                aggiungiNodi(creaNodiCasuali(1));
                dimx=this.getWidth()/2;
                creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
                break;
            case "Reset":
                albero.setRadice(null);
                listaSelezionati.clear();
                abilitaDisabilitaPulsanti();
                dimx=this.getWidth()/2;
                ElencoArchi.clear();
                ElencoNodi.clear();
                //creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
                v.ridisegna(JTBTab.isSelected());
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
            case "TimeAttr":

                scorriPerAttraversamento();
                break;
            case "TimeRice":

                scorriPerRicerca();
                break;
            //TODO separare timer per attraversamenti da quello per rocerca
        }

        JTFNodiDaElaborare.setText("");
        JCBNum.setEnabled(albero.getRadice() == null);
        JTFNodiDaElaborare.requestFocus();
    }

    private void scorriPerRicerca()
    {
        poslista++;
        if (poslista==lista.size()) {
            timerRicerca.stop();

            for(Comparable n: lista)
                if(!nodoDaCercare.equals(normalizzaDouble(n.toString())  ))
                    segnala(normalizzaDouble(n.toString()),Color.white);
                else
                {
                    segnala(normalizzaDouble(n.toString()),Color.green);
                    if (!JCBNum.isSelected())
                        cambiaListaSelezionati("Add", normalizzaDouble(n.toString()));
                    else
                        cambiaListaSelezionati("Add", Double.parseDouble(normalizzaDouble(n.toString())));
                }
            JBInorder.setEnabled(true);
            JBPostorder.setEnabled(true);
            JBPreorder.setEnabled(true);
            JBAddRandom.setEnabled(true);
            JTBTab.setEnabled(true);
            JBBil.setEnabled(true);
            JBReset.setEnabled(true);
            ultimoAttraversato="";
        }
        else{
            segnala(ultimoAttraversato,Color.yellow);
            segnala(normalizzaDouble(lista.get(poslista).toString()),Color.orange);
            ultimoAttraversato=normalizzaDouble(lista.get(poslista).toString());
        }

    }

    private void scorriPerAttraversamento()
    {
        poslista++;
        if (poslista==lista.size()) {
            timerAttraversamento.stop();
            for(Comparable n: lista)
                segnala(normalizzaDouble(n.toString()),Color.white);
            JBInorder.setEnabled(true);
            JBPostorder.setEnabled(true);
            JBPreorder.setEnabled(true);
            JBAddRandom.setEnabled(true);
            JTBTab.setEnabled(true);
            JBBil.setEnabled(true);
            JBReset.setEnabled(true);
            ultimoAttraversato="";
        }
        else{
            segnala(ultimoAttraversato,Color.yellow);
            segnala(normalizzaDouble(lista.get(poslista).toString()),Color.orange);
            ultimoAttraversato=normalizzaDouble(lista.get(poslista).toString());
        }
    }

    private void aggiungiNodi(String nodiDaAggiungere) {
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
        String[] elencoNodi = nodiDaEliminare.split(",");
        JTFNodiDaElaborare.setText("");
        for (String info : elencoNodi) {
            info = info.trim();
            if (JCBNum.isSelected()) {
                if(JCDPredecessor.isSelected()==true)
                {
                    albero.cancellaNodo(Double.parseDouble(info), true);
                    eliminaNodoGrafico(normalizzaDouble(""+Double.parseDouble(info)));
                }
                albero.cancellaNodo(Double.parseDouble(info));
                eliminaNodoGrafico(normalizzaDouble(""+Double.parseDouble(info)));
            }
            else {
                if(JCDPredecessor.isSelected()==true)
                {
                    albero.cancellaNodo(info,true);
                    eliminaNodoGrafico(info);
                }
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

    public void ricerca()
    {
        poslista=-1;
        lista = new ArrayList<>();
        lista.clear();
        if (JCBNum.isSelected())
            lista=albero.cercaPercorso(Double.parseDouble(JTFNodiDaElaborare.getText()));
        else
            lista=albero.cercaPercorso(JTFNodiDaElaborare.getText());
        String messaggio="SEARCH PATH: ";
        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1)
                messaggio += normalizzaDouble(lista.get(i).toString()) + " , ";
            else
                messaggio += normalizzaDouble(lista.get(i).toString()) + "";
        }
        timerRicerca.start();
        messaggio += "<br><br></b></html>";
        JEPConsole.setText(messaggio);
        JEPConsole.setEnabled(true);
    }

    public void attraversamento(String ordine) {

        if (albero.getRadice()!=null) {
            poslista=-1;
            JBInorder.setEnabled(false);
            JBPostorder.setEnabled(false);
            JBPreorder.setEnabled(false);
            JBAddRandom.setEnabled(false);
            JTBTab.setEnabled(false);
            JBBil.setEnabled(false);
            JBReset.setEnabled(false);

            lista = new ArrayList<>();
            String messaggio = "";
            switch (ordine) {
                case "IN" -> {
                    messaggio = "<html><br>INORDER: <b>";
                    lista = albero.attraversamentoSimmetrico();
                }
                case "PRE" -> {
                    messaggio = "<html><br>PREORDER: <b>";
                    lista = albero.attraversamentoAnticipato();
                }
                case "POST" -> {
                    messaggio = "<html><br>POSTORDER: <b>";
                    lista = albero.attraversamentoPosticipato();
                }
            }
            for (int i = 0; i < lista.size(); i++) {
                if (i < lista.size() - 1)
                    messaggio += normalizzaDouble(lista.get(i).toString()) + " , ";
                else
                    messaggio += normalizzaDouble(lista.get(i).toString()) + "";
            }
            timerAttraversamento.start();
            messaggio += "<br><br></b></html>";
            JEPConsole.setText(messaggio);
            JEPConsole.setEnabled(true);
        }
    }

    //TODO Fare in modo che NodoBT lavori sia con Stringhe che con Double e il Check Box influenzi solo i valori da inserire
    private void successore(){

        NodoBT nodoSelezionato=albero.ricercaNodo(listaSelezionati.get(0));
        NodoBT succ=albero.trovaSuccessore(albero.getRadice(),nodoSelezionato);
        String contenutoSuccessore=normalizzaDouble(succ.getInfo().toString());
        segnala(contenutoSuccessore,Color.red);
    }

    private void predecessore(){
        NodoBT nodoSelezionato=albero.ricercaNodo(listaSelezionati.get(0));
        NodoBT pred=albero.trovaPredecessore(albero.getRadice(),nodoSelezionato);
        String contenutoPredecessore=normalizzaDouble(pred.getInfo().toString());
        segnala(contenutoPredecessore,Color.blue);
    }

    public void segnala(String contNodo, Color c)
    {
        for (NodoGrafico n:ElencoNodi)
        {
            if(n.getContenuto().equals(contNodo))
                n.setColore(c);
        }
        v.repaint();
    }

    public void svuotaConsole() {
        String messaggio = "<html><br><b>";
        messaggio += "<br><br></b></html>";
        JEPConsole.setText(messaggio);
    }

    public void cambiaListaSelezionati(String tipo, Comparable contenutoNodo)
    {
        if (tipo.equals("Add"))
            listaSelezionati.add(contenutoNodo);
        else
            listaSelezionati.remove(contenutoNodo);
        abilitaDisabilitaPulsanti();
        // System.out.println(listaSelezionati.size());
    }

    public void resettaSuccPred()
    {
        for(NodoGrafico n:ElencoNodi)
        {
            if (n.getColore().equals(Color.blue) | n.getColore().equals(Color.red))
                n.setColore(Color.white);
        }
        // System.out.println(listaSelezionati.size());
    }

    public String normalizzaDouble(String a) {
        String pulita = a;
        if (a.length() > 1)
            if (a.startsWith(".0", a.length() - 2))
                pulita = a.replace(".0", "");
        return pulita;
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
                creaNodo(x, y, size / 2, normalizzaDouble(node.getInfo().toString()));
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

    private void creaNodo(int x, int y, int r, String contenuto) {
        int lungContenuto = contenuto.length();
        int larghezza = r;
        if (lungContenuto <= 3) {
            larghezza=r*2;
        } else {
            larghezza=lungContenuto*13;
        }
        ElencoNodi.add(new NodoGrafico(x, y, larghezza, r * 2, Color.white, contenuto));
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
            JBSearch.setEnabled(true);
        }
        else {
            JBAdd.setEnabled(false);
            JBDel.setEnabled(false);
            JBSearch.setEnabled(false);
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

    private void eliminaNodi()
    {
        if (!JTFNodiDaElaborare.getText().trim().equals("")) {
            eliminaNodi(JTFNodiDaElaborare.getText());
        }
        else if(listaSelezionati.size()>0)
        {
            for(Comparable cn:listaSelezionati)
            {
                eliminaNodi(cn.toString());
            }

        }
        int dimx=this.getWidth()/2;
        creaAlberoGrafico(albero.getRadice(),dimx,40,50,dimx/2);
        listaSelezionati.clear();
        abilitaDisabilitaPulsanti();
    }

    String creaNodiCasuali(int numero)
    {
        String elencoNodi="";
        Random r=new Random();
        if (JCBNum.isSelected()) {
            for(int i=1;i<=numero;i++)
            {
                double numeroCasuale= r.nextInt(200);
                elencoNodi+=numeroCasuale+",";
            }
            elencoNodi = elencoNodi.substring(0, elencoNodi.length() - 1);
        }
        else {
            for(int i=1;i<=numero;i++)
            {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 2; j++) {
                    char c = (char)(r.nextInt(26) + 'A');
                    sb.append(c);
                }
                elencoNodi+=sb+",";
            }
            elencoNodi = elencoNodi.substring(0, elencoNodi.length() - 1);

        }
        return elencoNodi;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode()== KeyEvent.VK_DELETE)
        {
            eliminaNodi();
            JTFNodiDaElaborare.setText("");
            JCBNum.setEnabled(albero.getRadice() == null);
            JTFNodiDaElaborare.requestFocus();
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
