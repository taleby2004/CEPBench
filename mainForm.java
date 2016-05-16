import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.lang.String;
/**
 * Created by mohammadreza on 5/14/2016.
 */
public class mainForm extends JFrame{
    private JPanel main;
    private JButton closeButton;
    private JButton openButton;
    private JButton saveButton;
    private JButton addEventButton;
    private JList machineCount_list;
    private JList list2;
    private JButton addMachineButton;
    private JList list3;
    private JCheckBox offlineCheckBox;
    private JTextField machineCount_textField;
    private JButton addRuleButton;
    private JComboBox comboBox1;
    private JButton setFilesPathButton;
    private JComboBox comboBox2;
    private AddRuleDialog addRuleDialog;
    private AddEventDialog addEventDialog;
    private AddMachineDialog addMachineDialog;


    public class Config{
        public int eventTypeCount ;
        public int attributeCount ;
        public String attributeDistributionFunction ;
        public int machineCount ;
        public String mode ; //Offline or Online

    }


    public mainForm(){
        addRuleButton.setActionCommand("showAddRule");
        offlineCheckBox.setActionCommand("changeMode");
        saveButton.setActionCommand("saveConfig");
        addMachineButton.setActionCommand("showAddMachine");
        addEventButton.setActionCommand("showAddEvent");
        addRuleButton.addComponentListener(new ComponentAdapter() {
        });
        addRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command =  e.getActionCommand();

                if (command == "showAddRule"){
                    addRuleDialog.main(null);
                }

            }
        });
        addMachineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command =  e.getActionCommand();
                if (command == "showAddMachine"){
                    addMachineDialog.main(null);
                }
            }
        });
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        offlineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command =  e.getActionCommand();

                if (command == "changeMode" && offlineCheckBox.isSelected()){
                    machineCount_list.setEnabled(false);
                    addMachineButton.setEnabled(false);
                    machineCount_textField.setEnabled(true);
                    machineCount_list.setEnabled(false);
                }
                else if (command == "changeMode" && !offlineCheckBox.isSelected())
                {
                    machineCount_list.setEnabled(true);
                    addMachineButton.setEnabled(true);
                    machineCount_textField.setEnabled(false);
                    machineCount_list.setEnabled(true);
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(mainForm.this);
//                if (rVal == JFileChooser.APPROVE_OPTION) {
//                    filename.setText(c.getSelectedFile().getName());
//                    dir.setText(c.getCurrentDirectory().toString());
//                }
//                if (rVal == JFileChooser.CANCEL_OPTION) {
//                    filename.setText("You pressed cancel");
//                    dir.setText("");
//                }
            }
        });
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command =  e.getActionCommand();
                if (command == "showAddEvent")
                {
                    addEventDialog.main(null);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainForm");
        frame.setContentPane(new mainForm().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
