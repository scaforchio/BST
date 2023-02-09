import java.util.ArrayList;

/**
 * Aggiungi qui una descrizione della classe BST
 *
 * @author (il tuo nome)
 * @version (un numero di versione o una data)
 */
public class BST {
    NodoBT Radice;
    ArrayList <Comparable> lista = new ArrayList<Comparable>();

    public BST() {
        Radice = null;
    }

    public BST(NodoBT r) {
        Radice = r;
    }

    public NodoBT getRadice() {
        return Radice;
    }

    public void setRadice(NodoBT n) {
        Radice = n;
    }

    public void inserisciNodo(Comparable o) {
        if (ricercaDato(o)==null) {
            if (Radice == null) {
                Radice = new NodoBT(o);
                return;
            }
            inserisciNodo(new NodoBT(o), Radice);
        }
    }

    private void inserisciNodo(NodoBT n, NodoBT r) {
        if (n.compareTo(r) > 0)
            if (r.getDestra() == null)
                r.setDestra(n);
            else
                inserisciNodo(n, r.getDestra());
        else if (r.getSinistra() == null)
            r.setSinistra(n);
        else
            inserisciNodo(n, r.getSinistra());
    }


    public Comparable ricercaDato(Comparable o) {
        NodoBT dato = new NodoBT(o);
        if (Radice == null)
            return null;
        if (dato.compareTo(Radice) == 0)
            return Radice.getInfo();
        else
            return ricercaDato(dato, Radice);
    }

    private Comparable ricercaDato(NodoBT dato, NodoBT r) {
        if (r == null)
            return null;
        if (dato.compareTo(r) == 0)
            return r.getInfo();
        if (dato.compareTo(r) > 0)
            return ricercaDato(dato, r.getDestra());
        else
            return ricercaDato(dato, r.getSinistra());
    }

    public ArrayList<Comparable> attraversamentoAnticipato() {
        lista.clear();
        if (Radice == null)
            return null;
        else
            attraversamentoAnticipato(Radice);
        return lista;
    }

    private void attraversamentoAnticipato(NodoBT r) {
        if (r != null) {
            lista.add(r.getInfo());
            attraversamentoAnticipato(r.getSinistra());
            attraversamentoAnticipato(r.getDestra());
        } else {
        }
    }

    public ArrayList<Comparable> attraversamentoSimmetrico() {
        lista.clear();
        if (Radice == null)
            return null;
        else
            attraversamentoSimmetrico(Radice);
        return lista;
    }

    private void attraversamentoSimmetrico(NodoBT r) {
        if (r != null) {
            attraversamentoSimmetrico(r.getSinistra());
            lista.add(r.getInfo());
            attraversamentoSimmetrico(r.getDestra());
        } else {
        }
    }


    public static int numeroNodi(NodoBT r) {
        if (r != null)

            return 1 + numeroNodi(r.getSinistra()) + numeroNodi(r.getDestra());

        else
            return 0;
    }

    public ArrayList<Comparable> attraversamentoPosticipato() {
        lista.clear();
        if (Radice == null)
            return null;
        else
            attraversamentoPosticipato(Radice);
        return lista;
    }

    private void attraversamentoPosticipato(NodoBT r) {
        if (r != null) {
            attraversamentoPosticipato(r.getSinistra());
            attraversamentoPosticipato(r.getDestra());
            lista.add(r.getInfo());
        } else {
        }
    }

    public void cancellaNodo(Comparable c) {
        Radice = cancellaNodo(Radice, c);
    }

    private NodoBT cancellaNodo(NodoBT n, Comparable c) {
        if (n == null) {
            return null;
        }

        if (c.compareTo(n.getInfo()) < 0) {
            n.setSinistra(cancellaNodo(n.getSinistra(), c));
        } else if (c.compareTo(n.getInfo()) > 0) {
            n.setDestra(cancellaNodo(n.getDestra(), c));
        } else {
            if (n.getSinistra() == null) {
                return n.getDestra();
            } else if (n.getDestra() == null) {
                return n.getSinistra();
            } else {
                NodoBT successore = trovaSuccessore(n.getDestra());
                n.setInfo(successore.getInfo());
                n.setDestra(cancellaNodo(n.getDestra(), successore.getInfo()));
            }
        }
        return n;
    }

    private NodoBT trovaSuccessore(NodoBT n) {
        while (n.getSinistra() != null) {
            n = n.getSinistra();
        }
        return n;
    }



    public void bilanciamento() {
        BST A2 = new BST();
        this.attraversamentoSimmetrico();
        int metà = (lista.size()) / 2;
        A2.inserisciNodo(lista.get(metà));
        bilanciamento(A2, 0, metà - 1);
        bilanciamento(A2, metà + 1, lista.size() - 1);
        Radice = A2.getRadice();
        A2.setRadice(null);

    }

    private void bilanciamento(BST A2, int a, int b) {
        if (a == b)
            A2.inserisciNodo(lista.get(a));
        if (a < b) {
            int metà = a + ((b - a) / 2);
            A2.inserisciNodo(lista.get(metà));
            bilanciamento(A2, a, metà - 1);
            bilanciamento(A2, metà + 1, b);
        }
    }
}
