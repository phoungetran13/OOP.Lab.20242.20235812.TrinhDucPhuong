package hust.soict.dsai.aims.screen.manager;

import javax.swing.*;

import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MediaStore extends JPanel {
    private Media media;
    private StoreManagerScreen parentScreen;

    public MediaStore(Media media, StoreManagerScreen parentScreen) {
        this.media = media;
        this.parentScreen = parentScreen;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(media.getTitle());
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 15));
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel cost = new JLabel(String.format("%.2f $", media.getCost()));
        cost.setAlignmentX(CENTER_ALIGNMENT);

        JLabel mediaDetails = new JLabel();
        mediaDetails.setAlignmentX(CENTER_ALIGNMENT);
        if (media instanceof DigitalVideoDisc) {
            DigitalVideoDisc dvd = (DigitalVideoDisc) media;
            mediaDetails.setText("DVD - Director: " + dvd.getDirector() + " - Length: " + dvd.getLength() + " mins");
        } else if (media instanceof CompactDisc) {
            CompactDisc cd = (CompactDisc) media;
            mediaDetails.setText("CD - Artist: " + cd.getArtist() + " - Tracks: " + cd.getTracks().size());
        } else {
             mediaDetails.setText("Category: " + media.getCategory());
        }


        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        if (media instanceof Playable) {
            JButton playButton = new JButton("Play");
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog playDialog = new JDialog(parentScreen, "Playing: " + media.getTitle(), true);
                    playDialog.setSize(400, 200);
                    playDialog.setLocationRelativeTo(parentScreen);

                    JTextArea playContent = new JTextArea();
                    playContent.setEditable(false);
                    playContent.setLineWrap(true);
                    playContent.setWrapStyleWord(true);

                    StringBuilder playInfo = new StringBuilder();
                    playInfo.append("Playing: ").append(media.getTitle()).append("\n");
                    if (media instanceof DigitalVideoDisc) {
                        DigitalVideoDisc dvd = (DigitalVideoDisc) media;
                        playInfo.append("Type: DVD\n");
                        playInfo.append("Director: ").append(dvd.getDirector()).append("\n");
                        playInfo.append("Length: ").append(dvd.getLength()).append(" minutes\n");
                    } else if (media instanceof CompactDisc) {
                        CompactDisc cd = (CompactDisc) media;
                        playInfo.append("Type: CD\n");
                        playInfo.append("Artist: ").append(cd.getArtist()).append("\n");
                        playInfo.append("Total Length: ").append(cd.getLength()).append(" seconds\n");
                        playInfo.append("Tracks:\n");
                        if (cd.getTracks().isEmpty()) {
                            playInfo.append("  (No tracks available)\n");
                        } else {
                            for (Object trackObj : cd.getTracks()) {
                                if (trackObj instanceof hust.soict.dsai.aims.media.Track) {
                                    hust.soict.dsai.aims.media.Track track = (hust.soict.dsai.aims.media.Track) trackObj;
                                    playInfo.append("  - ").append(track.getTitle()).append(" (").append(track.getLength()).append("s)\n");
                                }
                            }
                        }
                    }

                    playContent.setText(playInfo.toString());
                    playDialog.add(new JScrollPane(playContent));
                    playDialog.setVisible(true);
                }
            });
            container.add(playButton);
        }

        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(mediaDetails); // Thêm dòng chi tiết
        this.add(cost);
        this.add(Box.createVerticalStrut(5)); // Khoảng cách nhỏ trước container nút
        this.add(container);
        this.add(Box.createVerticalGlue());


        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true)); // Bo tròn góc
        this.setPreferredSize(new Dimension(200, 150)); // Kích thước cố định cho mỗi cell
        this.setMaximumSize(new Dimension(220, 180)); // Kích thước tối đa
    }
}