import java.io.*;
/**
 * Aggiungi qui una descrizione della classe Auto
 *
 * @author (il tuo nome)
 * @version (un numero di versione o una data)
 */
public class Test {
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

}
