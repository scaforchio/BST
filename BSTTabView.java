import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BSTTabView extends JPanel {

    ArrayList<Riga> BSTTab;
    public BSTTabView(ArrayList<Riga> BSTTab) {
        this.BSTTab = BSTTab;

        JTable table = new JTable(new ModelloBSTTab(BSTTab));
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);



    }

}
