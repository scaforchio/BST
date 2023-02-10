import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FinestraBT extends JFrame implements ActionListener {
    JTextField JTFNodiDaElaborare;
    JCheckBox JTBTab;
    JCheckBox JCBNum;
    JEditorPane JLConsole;
    BSTView v;
    BST albero;
    int pos=0;
    ArrayList<Riga> BSTTab = new ArrayList<Riga>();
    public FinestraBT(BST albero) throws Exception{
        this.albero=albero;
        setSize(new Dimension(1200, 1000));
        NodoBT root=albero.getRadice();
        if (root!=null)
            setTitle("Albero con radice " + root.getInfo().toString());
        else
            setTitle("Albero vuoto");
        v = new BSTView(albero, 600, 40, 50, BSTTab);
        JScrollPane SP = new JScrollPane(v);
        Container CP=getContentPane();
        CP.setLayout(new BorderLayout());
        CP.add(SP,BorderLayout.CENTER);
        JPanel JPConsole=new JPanel();
        JPConsole.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPConsole.setLayout(new GridBagLayout());
        JLabel JLNodi=new JLabel("Nodes (separated by commas)");
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

       // JCBNum.addActionListener(this);


        JButton JBInorder = new JButton("Inorder");
        JBInorder.addActionListener(this);
        JButton JBPreorder = new JButton("Preorder");
        JBPreorder.addActionListener(this);
        JButton JBPostorder = new JButton("Postorder");
        JBPostorder.addActionListener(this);
        JPConsole.add(JLNodi);
        JPConsole.add(JTFNodiDaElaborare);
        JPConsole.add(JCBNum);
        JPConsole.add(JBAdd);
        JPConsole.add(JBDel);
        JPConsole.add(new JLabel("     "));
        JPConsole.add(JBBil);
        JPConsole.add(JTBTab);
        JPConsole.add(JBInorder);
        JPConsole.add(JBPreorder);
        JPConsole.add(JBPostorder);
        //JLConsole=new JLabel("<html><br><br><br><br></html>");

        JLConsole=new JEditorPane();
        JLConsole.setContentType("text/html");
        JLConsole.setText("<html><br><br><br><br></html>");
        JLConsole.setEditable(false);
        CP.add(JPConsole, BorderLayout.NORTH);
        CP.add(JLConsole,BorderLayout.SOUTH);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String comando=actionEvent.getActionCommand();

        switch (comando)
        {
            case "Add": if (!JTFNodiDaElaborare.getText().trim().equals("")) aggiungiNodi(); break;
            case "Del": if (!JTFNodiDaElaborare.getText().trim().equals("")) eliminaNodi(); break;
            case "Balance": bilanciaAlbero(); break;
            case "Table": visualizzaTabella(); break;
            case "Inorder": attraversamento("IN"); break;
            case "Preorder": attraversamento("PRE"); break;
            case "Postorder": attraversamento("POST"); break;
        }
        JTFNodiDaElaborare.setText("");
        if (albero.getRadice()!=null)
            JCBNum.setEnabled(false);
        else
            JCBNum.setEnabled(true);
    }

    private void aggiungiNodi()
    {

        String nodiDaAggiungere=JTFNodiDaElaborare.getText();
        String[] elencoNodi=nodiDaAggiungere.split(",");

        for (String info:elencoNodi) {
            info=info.trim();
            if (!JCBNum.isSelected())
                albero.inserisciNodo(info);
            else {
                try{
                    albero.inserisciNodo(Double.parseDouble(info));
                }
                catch(Exception e)
                {
                    System.out.println("Valore non numerico!");
                }

            }
        }
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();

    }
    private void eliminaNodi()
    {
        String nodiDaEliminare=JTFNodiDaElaborare.getText();
        String[] elencoNodi=nodiDaEliminare.split(",");
        JTFNodiDaElaborare.setText("");

        for (String info:elencoNodi) {
            info=info.trim();
            if (JCBNum.isSelected())
               albero.cancellaNodo(Double.parseDouble(info));
            else
               albero.cancellaNodo(info);
        }
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();

    }
    private void bilanciaAlbero()
    {
          albero.bilanciamento();
          tabella(albero);
          v.ridisegna(JTBTab.isSelected());
          svuotaConsole();

    }
    private void visualizzaTabella()
    {
        tabella(albero);
        v.ridisegna(JTBTab.isSelected());
        svuotaConsole();

    }
    public void tabella(BST t) {
        BSTTab.clear();
        pos=0;
        NodoBT r=t.getRadice();
        if (r != null)
            tabella(r);
        
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
    public void attraversamento(String ordine){
        ArrayList <Comparable> lista=null;
        String messaggio="";
        switch(ordine)
        {
            case "IN":messaggio="<html><br>INORDER: <b>";
                lista=albero.attraversamentoSimmetrico();
                break;
            case "PRE":messaggio="<html><br>PREORDER: <b>";
                lista=albero.attraversamentoAnticipato();
                break;
            case "POST":messaggio="<html><br>POSTORDER: <b>";
                lista=albero.attraversamentoPosticipato();
                break;
        }


        for(int i=0;i<lista.size();i++)
        {
            if (i<lista.size()-1)
                messaggio+=lista.get(i).toString()+" , ";
            else
                messaggio+=lista.get(i).toString()+"";


        }
        messaggio+="<br><br></b></html>";
        JLConsole.setText(messaggio);
        JLConsole.setEnabled(true);

    }

    public void svuotaConsole()
    {
        String messaggio="<html><br><b>";

        messaggio+="<br><br></b></html>";
        JLConsole.setText(messaggio);

    }



}
