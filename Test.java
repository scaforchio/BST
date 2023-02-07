import java.io.*;
import java.util.ArrayList;

/**
 * Aggiungi qui una descrizione della classe Auto
 *
 * @author (il tuo nome)
 * @version (un numero di versione o una data)
 */
public class Test {
    static int pos=0;
    static ArrayList<Riga> BSTTab = new ArrayList<Riga>();
    public static void main(String[] args) throws Exception{
        BufferedReader comandi=new BufferedReader(new FileReader("comandi.txt"));
        String line;
        String[] comando = new String[2];
        BST a = new BST();
        while ((line = comandi.readLine()) != null) {
            comando=line.split(":");
           // System.out.println(comando[0]+"     "+comando[1]);
            switch(comando[0])
            {
                case "ADD":a.inserisciNodo(comando[1]);
                           break;
                case "BIL":a.bilanciamento();
                           break;
                case "DEL":a.cancellaNodo(comando[1]);
                           break;
                case "VIS":visualizzaAlbero(a.getRadice());
                           break;
                case "TAB":tabella(a);
                           break;

            }
        }


    }

    static void sleep(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void visualizzaAlbero(NodoBT r) {
        FinestraBT a = new FinestraBT(r);
        sleep(2000);
    }

    public static void tabella(BST t) {

        NodoBT r=t.getRadice();
        if (r != null)
            tabella(r);
        FinestraBTTab a=new FinestraBTTab(BSTTab);

    }

    private static void tabella(NodoBT r) {
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
