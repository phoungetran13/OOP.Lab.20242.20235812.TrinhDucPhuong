package hust.soict.dsai.aims.screen.customer.controller;

import java.util.ResourceBundle;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import hust.soict.dsai.aims.exception.DuplicateMediaException;
import hust.soict.dsai.aims.exception.LimitExceededException;
import javafx.fxml.Initializable;
import java.net.URL;
import hust.soict.dsai.aims.exception.PlayerException;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Track;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ItemController implements Initializable {

    private Media media;
    private Cart cart; // Giả sử bạn có một đối tượng Cart được truyền vào
    // Thêm phương thức này vào
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Căn giữa tất cả các phần tử con trong HBox
        hboxButton.setAlignment(Pos.CENTER);
        // Đặt khoảng cách 10px giữa các phần tử con
        hboxButton.setSpacing(10); 
    }
    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblTitle;
    
    @FXML
    private HBox hboxButton;


    public ItemController(Cart cart) {
        this.cart = cart;
    }


    public void setData(Media media) {
        this.media = media;
        lblTitle.setText(media.getTitle());
        lblCost.setText(media.getCost() + " $");
        
        // Cập nhật trạng thái của nút Play
        if (media instanceof Playable) {
            btnPlay.setVisible(true);
        } else {
            btnPlay.setVisible(false);
            // Thay đổi lề của nút AddToCart để nó căn giữa khi nút Play bị ẩn
            HBox.setMargin(btnAddToCart, new Insets(0, 0, 0, 60));
        }
    }

@FXML
void btnAddToCartClicked(ActionEvent event) {
    try {
        cart.addMedia(media);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("'" + media.getTitle() + "' has been added to the cart.");
        alert.showAndWait();
    } catch (LimitExceededException | DuplicateMediaException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Add to Cart Warning");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage()); // Hiển thị thông báo lỗi từ exception
        alert.showAndWait();
    }
}

    @FXML
    void btnPlayClicked(ActionEvent event) {
        try {
            // Gọi phương thức play() để xử lý logic (có thể vẫn in ra console hoặc không)
            if (media instanceof Playable) {
                ((Playable) media).play();
            } else {
                // Không phải là media có thể play, không làm gì cả
                return;
            }

            // Tạo và hiển thị một Dialog mới với thông tin chi tiết
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Now Playing");

            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setHeaderText("Playing: " + media.getTitle());
            dialogPane.getButtonTypes().add(ButtonType.OK);

            // Tạo layout để hiển thị thông tin bên trong Dialog
            VBox content = new VBox(10);
            content.setPadding(new Insets(20));
            content.getChildren().add(new Label("Category: " + media.getCategory()));
            content.getChildren().add(new Label("Cost: " + media.getCost() + " $"));


            if (media instanceof DigitalVideoDisc dvd) {
                content.getChildren().add(new Label("Director: " + dvd.getDirector()));
                content.getChildren().add(new Label("Length: " + dvd.getLength() + " mins"));
            } else if (media instanceof CompactDisc cd) {
                content.getChildren().add(new Label("Artist: " + cd.getArtist()));
                content.getChildren().add(new Label("Total Length: " + cd.getLength() + "s"));

                // Hiển thị danh sách các track nếu có
                if (!cd.getTracks().isEmpty()) {
                    TextArea tracksArea = new TextArea();
                    StringBuilder sb = new StringBuilder();
                    for (Track track : cd.getTracks()) {
                        sb.append("- ").append(track.getTitle()).append(" (").append(track.getLength()).append("s)\n");
                    }
                    tracksArea.setText(sb.toString());
                    tracksArea.setEditable(false);
                    tracksArea.setWrapText(true);
                    tracksArea.setPrefHeight(100);

                    content.getChildren().add(new Label("Tracks:"));
                    content.getChildren().add(tracksArea);
                }
            }

            dialogPane.setContent(content);
            dialog.showAndWait();

        } catch (PlayerException e) {
            // Hiển thị Alert nếu có lỗi xảy ra từ phương thức play()
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Playback Error");
            alert.setHeaderText("Cannot play this media!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}