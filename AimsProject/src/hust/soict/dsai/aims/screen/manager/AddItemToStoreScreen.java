package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AddItemToStoreScreen extends JFrame {
    protected Store store;
    protected StoreManagerScreen parentScreen;

    protected JTextField tfId, tfTitle, tfCategory, tfCost;

    public AddItemToStoreScreen(Store store, StoreManagerScreen parentScreen, String itemType) {
        this.store = store;
        this.parentScreen = parentScreen;

        setTitle("Add " + itemType + " to Store");
        setSize(500, 400);
        setLocationRelativeTo(parentScreen);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                parentScreen.setVisible(true);
                parentScreen.refreshCenterPanel();
            }
        });


        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(10, 10));

        setJMenuBar(createMenuBarShared());

        JPanel commonInputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        commonInputPanel.setBorder(BorderFactory.createTitledBorder("Common Media Details"));
        
        commonInputPanel.add(new JLabel("ID:"));
        tfId = new JTextField();
        commonInputPanel.add(tfId);

        commonInputPanel.add(new JLabel("Title:"));
        tfTitle = new JTextField();
        commonInputPanel.add(tfTitle);

        commonInputPanel.add(new JLabel("Category:"));
        tfCategory = new JTextField();
        commonInputPanel.add(tfCategory);

        commonInputPanel.add(new JLabel("Cost:"));
        tfCost = new JTextField();
        commonInputPanel.add(tfCost);
        
        cp.add(commonInputPanel, BorderLayout.NORTH);

        JPanel specificInputPanel = createSpecificInputFields();
        if (specificInputPanel != null) {
            cp.add(specificInputPanel, BorderLayout.CENTER);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Add " + itemType);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addItemAction()) {
                    JOptionPane.showMessageDialog(AddItemToStoreScreen.this,
                            itemType + " added successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); 
                }
            }
        });
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        ((JPanel)cp).setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }

    private JMenuBar createMenuBarShared() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOptions = new JMenu("Options");
        JMenuItem viewStoreItem = new JMenuItem("View Store");
        viewStoreItem.addActionListener(e -> {
            parentScreen.setVisible(true);
            parentScreen.refreshCenterPanel();
            this.dispose();
        });
        menuOptions.add(viewStoreItem);
        menuBar.add(menuOptions);
        return menuBar;
    }

    protected abstract JPanel createSpecificInputFields();

    protected abstract boolean addItemAction();
}