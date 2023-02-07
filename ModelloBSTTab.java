import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class ModelloBSTTab extends AbstractTableModel {
    private ArrayList<Riga> righe;

    ModelloBSTTab(ArrayList<Riga> righe) {
        this.righe = righe;
    }

    @Override
    public int getRowCount() {
        return righe.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Pos.";
            case 1:
                return "Contenuto";
            case 2:
                return "Figlio Sx";
            default:
                return "Figlio Dx";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Riga riga = righe.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return riga.getPosizione();
            case 1:
                return riga.getValore();
            case 2:
                return riga.getPosSx();
            default:
                return riga.getPosDx();
        }
    }
}
