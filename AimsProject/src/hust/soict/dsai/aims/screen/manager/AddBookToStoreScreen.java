package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.store.Store;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AddBookToStoreScreen extends AddItemToStoreScreen {
    private JTextField tfAuthors;

    public AddBookToStoreScreen(Store store, StoreManagerScreen parentScreen) {
        super(store, parentScreen, "Book");
        setVisible(true);
    }

    @Override
    protected JPanel createSpecificInputFields() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Book Specific Details"));
        
        panel.add(new JLabel("Authors (comma-separated):"));
        tfAuthors = new JTextField();
        panel.add(tfAuthors);
        
        return panel;
    }

    @Override
    protected boolean addItemAction() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String title = tfTitle.getText();
            String category = tfCategory.getText();
            float cost = Float.parseFloat(tfCost.getText());

            if (title.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Category cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (cost < 0) {
                 JOptionPane.showMessageDialog(this, "Cost must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return false;
            }
            // Kiểm tra ID trùng lặp
            if (store.getItemsInStore().stream().anyMatch(m -> m.getId() == id)) {
                JOptionPane.showMessageDialog(this, "Media with ID " + id + " already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }


            // Lấy thông tin riêng của Book
            String authorsStr = tfAuthors.getText();
            List<String> authorsList = Arrays.stream(authorsStr.split(","))
                                             .map(String::trim)
                                             .filter(s -> !s.isEmpty())
                                             .collect(Collectors.toList());
            if (authorsList.isEmpty()){
                JOptionPane.showMessageDialog(this, "Book must have at least one author.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Book newBook = new Book(id, title, category, cost, authorsList);
            store.addMedia(newBook);
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for ID or Cost.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding Book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}