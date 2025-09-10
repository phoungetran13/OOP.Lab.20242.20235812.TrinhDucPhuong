package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.Track;
import hust.soict.dsai.aims.store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCompactDiscToStoreScreen extends AddItemToStoreScreen {
    private JTextField tfArtist;
    private DefaultListModel<Track> trackListModel;
    private JList<Track> trackJList;


    public AddCompactDiscToStoreScreen(Store store, StoreManagerScreen parentScreen) {
        super(store, parentScreen, "CD");
        setSize(600, 550);
        setVisible(true);
    }

    @Override
    protected JPanel createSpecificInputFields() {
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.setBorder(BorderFactory.createTitledBorder("CD Specific Details"));

        JPanel artistPanel = new JPanel(new GridLayout(0,2,5,5));
        artistPanel.add(new JLabel("Artist:"));
        tfArtist = new JTextField();
        artistPanel.add(tfArtist);
        panel.add(artistPanel, BorderLayout.NORTH);

        JPanel trackInputPanel = new JPanel(new FlowLayout());
        trackInputPanel.setBorder(BorderFactory.createTitledBorder("Add Tracks"));
        JTextField tfTrackTitle = new JTextField(15);
        JTextField tfTrackLength = new JTextField(5);
        JButton btnAddTrack = new JButton("Add Track");

        trackInputPanel.add(new JLabel("Title:"));
        trackInputPanel.add(tfTrackTitle);
        trackInputPanel.add(new JLabel("Length(s):"));
        trackInputPanel.add(tfTrackLength);
        trackInputPanel.add(btnAddTrack);
        
        panel.add(trackInputPanel, BorderLayout.CENTER);

        trackListModel = new DefaultListModel<>();
        trackJList = new JList<>(trackListModel);
        JScrollPane scrollPane = new JScrollPane(trackJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Tracks"));
        scrollPane.setPreferredSize(new Dimension(200, 100));

        panel.add(scrollPane, BorderLayout.SOUTH);

        btnAddTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = tfTrackTitle.getText().trim();
                String lengthStr = tfTrackLength.getText().trim();
                if (title.isEmpty() || lengthStr.isEmpty()) {
                    JOptionPane.showMessageDialog(AddCompactDiscToStoreScreen.this,
                            "Track title and length cannot be empty.", "Track Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int length = Integer.parseInt(lengthStr);
                    if (length <= 0) {
                        JOptionPane.showMessageDialog(AddCompactDiscToStoreScreen.this,
                            "Track length must be positive.", "Track Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    trackListModel.addElement(new Track(title, length));
                    tfTrackTitle.setText("");
                    tfTrackLength.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddCompactDiscToStoreScreen.this,
                            "Invalid format for track length.", "Track Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panel;
    }

    @Override
    protected boolean addItemAction() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String title = tfTitle.getText();
            String category = tfCategory.getText();
            float cost = Float.parseFloat(tfCost.getText());
            String artist = tfArtist.getText();
            
            if (title.isEmpty() || category.isEmpty() || artist.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title, Category, and Artist cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (cost < 0) {
                 JOptionPane.showMessageDialog(this, "Cost must be non-negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return false;
            }
             if (store.getItemsInStore().stream().anyMatch(m -> m.getId() == id)) {
                JOptionPane.showMessageDialog(this, "Media with ID " + id + " already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }


            CompactDisc newCD = new CompactDisc(id, title, category, cost, artist);
            for (int i = 0; i < trackListModel.getSize(); i++) {
                newCD.addTrack(trackListModel.getElementAt(i));
            }

            store.addMedia(newCD);
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for ID or Cost.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding CD: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}