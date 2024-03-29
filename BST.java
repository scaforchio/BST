import java.util.ArrayList;

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

    public ArrayList<Comparable> cercaPercorso(Comparable obiettivo ) {
        lista.clear();
        cercaPercorso(getRadice(),new NodoBT(obiettivo));
        return lista;
    }

    public void cercaPercorso(NodoBT nodo,NodoBT obiettivo ) {
        if (nodo==null)
            return;
        else if (obiettivo.compareTo(nodo)>0)
        {
            lista.add(nodo.getInfo());
            cercaPercorso(nodo.getDestra(),obiettivo);
        }
        else if (obiettivo.compareTo(nodo)<0)
        {
            lista.add(nodo.getInfo());
            cercaPercorso(nodo.getSinistra(),obiettivo);
        }
        else if (obiettivo.compareTo(nodo)==0)
        {
            lista.add(nodo.getInfo());
            return;

        }

    }


    public NodoBT ricercaNodo(Comparable o) {
        NodoBT dato = new NodoBT(o);
        if (Radice == null)
            return null;
        if (dato.compareTo(Radice) == 0)
            return Radice;
        else
            return ricercaNodo(dato, Radice);
    }

    private NodoBT ricercaNodo(NodoBT dato, NodoBT r) {
        if (r == null)
            return null;
        if (dato.compareTo(r) == 0)
            return r;
        if (dato.compareTo(r) > 0)
            return ricercaNodo(dato, r.getDestra());
        else
            return ricercaNodo(dato, r.getSinistra());
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
                NodoBT successore = trovaMinimo(n.getDestra());
                n.setInfo(successore.getInfo());
                n.setDestra(cancellaNodo(n.getDestra(), successore.getInfo()));
            }
        }
        return n;
    }

    public void cancellaNodo(Comparable c, boolean prec) {
        Radice = cancellaNodo(Radice, c, prec);
    }

    private NodoBT cancellaNodo(NodoBT n, Comparable c, boolean prec) {
        if (n == null) {
            return null;
        }


        if (c.compareTo(n.getInfo()) < 0) {
            n.setSinistra(cancellaNodo(n.getSinistra(), c, true));
        } else if (c.compareTo(n.getInfo()) > 0) {
            n.setDestra(cancellaNodo(n.getDestra(), c,true));
        } else {
            if (n.getSinistra() == null) {
                return n.getDestra();
            } else if (n.getDestra() == null) {
                return n.getSinistra();
            } else {
                NodoBT predecessore = trovaMassimo(n.getSinistra());
                n.setInfo(predecessore.getInfo());
                n.setSinistra(cancellaNodo(n.getSinistra(), predecessore.getInfo(),true));
            }
        }
        return n;
    }

    private NodoBT trovaMinimo(NodoBT n) {
        while (n.getSinistra() != null) {
            n = n.getSinistra();
        }
        return n;
    }

    private NodoBT trovaMassimo(NodoBT n) {
        while (n.getDestra() != null) {
            n = n.getDestra();
        }
        return n;
    }

    public NodoBT trovaSuccessore(NodoBT rad, NodoBT nodo) {
        // Caso base
        if (nodo.getDestra() != null) {
            return trovaMinimo(nodo.getDestra());
        }

        NodoBT succ = null;
        while (rad != null) {
            if (nodo.getInfo().compareTo(rad.getInfo())<0) {
                succ = rad;
                rad = rad.getSinistra();
            } else if (nodo.getInfo().compareTo(rad.getInfo())>0) {
                rad = rad.getDestra();
            } else {
                break;
            }
        }

        return succ;
    }

    public NodoBT trovaPredecessore(NodoBT rad, NodoBT nodo) {
        // Caso base
        if (nodo.getSinistra() != null) {
            return trovaMassimo(nodo.getSinistra());
        }
        NodoBT pred = null;
        while (rad != null) {
            if (nodo.getInfo().compareTo(rad.getInfo())>0) {
                pred = rad;
                rad = rad.getDestra();
            } else if (nodo.getInfo().compareTo(rad.getInfo())<0) {
                rad = rad.getSinistra();
            } else {
                break;
            }
        }
        return pred;
    }


    public void bilanciamento() {
        if(getRadice()!=null) {
            BST A2 = new BST();
            this.attraversamentoSimmetrico();
            int middle = (lista.size()) / 2;
            A2.inserisciNodo(lista.get(middle));
            bilanciamento(A2, 0, middle - 1);
            bilanciamento(A2, middle + 1, lista.size() - 1);
            Radice = A2.getRadice();
            A2.setRadice(null);
        }
    }

    private void bilanciamento(BST A2, int a, int b) {
        if (a == b)
            A2.inserisciNodo(lista.get(a));
        if (a < b) {
            int middle = a + ((b - a) / 2);
            A2.inserisciNodo(lista.get(middle));
            bilanciamento(A2, a, middle - 1);
            bilanciamento(A2, middle + 1, b);
        }
    }
}