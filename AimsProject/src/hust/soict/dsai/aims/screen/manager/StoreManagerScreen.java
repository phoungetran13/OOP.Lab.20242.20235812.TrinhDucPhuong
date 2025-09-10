package hust.soict.dsai.aims.screen.manager;

import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StoreManagerScreen extends JFrame {
    private Store store;
    private JPanel centerPanel;

    public StoreManagerScreen(Store store) {
        this.store = store;

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(createNorth(), BorderLayout.NORTH);
        
        centerPanel = createCenter();
        cp.add(centerPanel, BorderLayout.CENTER);

        setTitle("Store Manager - AIMS");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    JPanel createNorth() {
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(createMenuBar());
        north.add(createHeader());
        return north;
    }

    JMenuBar createMenuBar() {
        JMenu menu = new JMenu("Options");

        JMenuItem viewStoreItem = new JMenuItem("View store");
        viewStoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCenterPanel();
            }
        });
        menu.add(viewStoreItem);

        JMenu smUpdateStore = new JMenu("Update Store");
        
        JMenuItem addBookItem = new JMenuItem("Add Book");
        addBookItem.addActionListener(e -> openAddItemScreen("Book"));
        smUpdateStore.add(addBookItem);

        JMenuItem addCDItem = new JMenuItem("Add CD");
        addCDItem.addActionListener(e -> openAddItemScreen("CD"));
        smUpdateStore.add(addCDItem);

        JMenuItem addDVDItem = new JMenuItem("Add DVD");
        addDVDItem.addActionListener(e -> openAddItemScreen("DVD"));
        smUpdateStore.add(addDVDItem);

        menu.add(smUpdateStore);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuBar.add(menu);
        return menuBar;
    }
    
    private void openAddItemScreen(String type) {
        this.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            if ("Book".equals(type)) {
                new AddBookToStoreScreen(store, this);
            } else if ("CD".equals(type)) {
                new AddCompactDiscToStoreScreen(store, this);
            } else if ("DVD".equals(type)) {
                new AddDigitalVideoDiscToStoreScreen(store, this);
            }
        });
    }


    JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBackground(new Color(200, 220, 255));

        JLabel title = new JLabel("AIMS");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(new Color(0, 102, 204));

        header.add(Box.createRigidArea(new Dimension(10, 10)));
        header.add(title);
        header.add(Box.createHorizontalGlue());
        header.add(Box.createRigidArea(new Dimension(10, 10)));
        
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Padding

        return header;
    }

    JPanel createCenter() {
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(0, 3, 10, 10)); 
        center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        ArrayList<Media> mediaInStore = store.getItemsInStore();
        if (mediaInStore.isEmpty()) {
             center.add(new JLabel("The store is currently empty.", SwingConstants.CENTER));
        } else {
            for (Media media : mediaInStore) {
                MediaStore cell = new MediaStore(media, this);
                center.add(cell);
            }
        }
        return center;
    }

    public void refreshCenterPanel() {
        Container cp = getContentPane();
        if (centerPanel != null) {
            cp.remove(centerPanel);
        }
        centerPanel = createCenter();
        cp.add(centerPanel, BorderLayout.CENTER);
        cp.revalidate();
        cp.repaint();
    }


    public static void main(String[] args) {
        Store sampleStore = new Store();
        // Thêm dữ liệu mẫu từ lớp Aims.java của bạn
        try {
            sampleStore.addMedia(new DigitalVideoDisc(1, "The Lion King", "Animation", 19.95f, "Roger Allers", 87));
            sampleStore.addMedia(new DigitalVideoDisc(2, "Star Wars", "Science Fiction", 24.95f, "George Lucas", 124));
            sampleStore.addMedia(new Book(5, "The Lord of the Rings", "Fantasy", 22.50f, List.of("J.R.R. Tolkien")));
            CompactDisc cd1 = new CompactDisc(4, "Abbey Road", "Rock", 12.50f, "The Beatles");
            cd1.addTrack(new hust.soict.dsai.aims.media.Track("Come Together", 259)); // Sửa: thêm new Track
            cd1.addTrack(new hust.soict.dsai.aims.media.Track("Something", 182));     // Sửa: thêm new Track
            sampleStore.addMedia(cd1);

        } catch (Exception e) {
             System.err.println("Error adding sample media: " + e.getMessage());
        }

        // Chạy giao diện trên Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StoreManagerScreen(sampleStore);
            }
        });
    }
}