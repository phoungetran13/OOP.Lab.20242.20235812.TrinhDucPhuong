package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.store.Store;

import javax.swing.*;
import java.awt.*;

public class AddDigitalVideoDiscToStoreScreen extends AddItemToStoreScreen {
    private JTextField tfDirector, tfLength;

    public AddDigitalVideoDiscToStoreScreen(Store store, StoreManagerScreen parentScreen) {
        super(store, parentScreen, "DVD");
        setVisible(true);
    }

    @Override
    protected JPanel createSpecificInputFields() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("DVD Specific Details"));
        
        panel.add(new JLabel("Director:"));
        tfDirector = new JTextField();
        panel.add(tfDirector);

        panel.add(new JLabel("Length (minutes):"));
        tfLength = new JTextField();
        panel.add(tfLength);
        
        return panel;
    }

    @Override
    protected boolean addItemAction() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String title = tfTitle.getText();
            String category = tfCategory.getText();
            float cost = Float.parseFloat(tfCost.getText());
            String director = tfDirector.getText();
            int length = Integer.parseInt(tfLength.getText());

             if (title.isEmpty() || category.isEmpty() || director.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title, Category, and Director cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (cost < 0 || length <= 0) {
                 JOptionPane.showMessageDialog(this, "Cost must be non-negative. Length must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return false;
            }
            if (store.getItemsInStore().stream().anyMatch(m -> m.getId() == id)) {
                JOptionPane.showMessageDialog(this, "Media with ID " + id + " already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            DigitalVideoDisc newDVD = new DigitalVideoDisc(id, title, category, cost, director, length);
            store.addMedia(newDVD);
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for ID, Cost, or Length.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding DVD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}