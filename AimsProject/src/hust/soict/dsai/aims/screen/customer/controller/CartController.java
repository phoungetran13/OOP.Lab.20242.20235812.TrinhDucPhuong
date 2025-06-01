package hust.soict.dsai.aims.screen.customer.controller;

import java.io.IOException;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.exception.PlayerException;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.Playable;
import hust.soict.dsai.aims.store.Store;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;
import hust.soict.dsai.aims.media.Track;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class CartController {

    private Store store;
    private Cart cart;
    private FilteredList<Media> filteredData;

    @FXML
    private TableView<Media> tblMedia;
    @FXML
    private TableColumn<Media, String> colMediaCategory;
    @FXML
    private TableColumn<Media, Float> colMediaCost;
    @FXML
    private TableColumn<Media, Integer> colMediaId;
    @FXML
    private TableColumn<Media, String> colMediaTitle;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnRemove;
    @FXML
    private Label costLabel;
    @FXML
    private ToggleGroup filterCategory;
    @FXML
    private RadioButton radioBtnFilterId;
    @FXML
    private RadioButton radioBtnFilterTitle;
    @FXML
    private TextField tfFilter;

    public CartController(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
    }

    @FXML
    public void initialize() {
        colMediaId.setCellValueFactory(new PropertyValueFactory<Media, Integer>("id"));
        colMediaTitle.setCellValueFactory(new PropertyValueFactory<Media, String>("title"));
        colMediaCategory.setCellValueFactory(new PropertyValueFactory<Media, String>("category"));
        colMediaCost.setCellValueFactory(new PropertyValueFactory<Media, Float>("cost"));
        tblMedia.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Khởi tạo FilteredList từ danh sách ObservableList của cart
        filteredData = new FilteredList<>(cart.getItemsOrdered(), p -> true);
        tblMedia.setItems(filteredData);

        btnPlay.setVisible(false);
        btnRemove.setVisible(false);

        tblMedia.getSelectionModel().selectedItemProperty().addListener(
            (_, _, newValue) -> {
                if(newValue != null) {
                    updateButtonBar(newValue);
                }
            }
        );
        
        tfFilter.textProperty().addListener((_, _, newValue) -> {
            showFilteredMedia(newValue);
        });
        
        // Thêm listener cho RadioButtons để cập nhật bộ lọc khi lựa chọn thay đổi
        filterCategory.selectedToggleProperty().addListener((_, _, _) -> {
            showFilteredMedia(tfFilter.getText());
        });

        updateTotalCost();
    }
    
    void updateButtonBar(Media media) {
        btnRemove.setVisible(true);
        if (media instanceof Playable) {
            btnPlay.setVisible(true);
        } else {
            btnPlay.setVisible(false);
        }
    }
    
    void updateTotalCost() {
        costLabel.setText(String.format("%.2f $", cart.totalCost()));
    }

    @FXML
    void btnRemovePressed(ActionEvent event) {
        Media media = tblMedia.getSelectionModel().getSelectedItem();
        if (media != null) {
            cart.removeMedia(media);
            updateTotalCost();
        }
    }

    @FXML
    void btnPlayPressed(ActionEvent event) {
        // Lấy media đang được người dùng chọn trong TableView
        Media media = tblMedia.getSelectionModel().getSelectedItem();

        // Kiểm tra xem có media nào được chọn không
        if (media == null) {
            return; // Không làm gì cả nếu không có gì được chọn
        }

        try {
            // Bây giờ 'media' là đối tượng được chọn, không còn là null nữa
            if (media instanceof Playable) {
                ((Playable) media).play();
            } else {
                return;
            }

            // Tạo và hiển thị một Dialog mới với thông tin chi tiết
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Now Playing");

            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setHeaderText("Playing: " + media.getTitle());
            dialogPane.getButtonTypes().add(ButtonType.OK);

            // ... (phần còn lại của code hiển thị Dialog giữ nguyên) ...
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Playback Error");
            alert.setHeaderText("Cannot play this media!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void btnPlaceOrderPressed(ActionEvent event) {
        if(cart.getItemsOrdered().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Order Warning");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty. Cannot place order.");
            alert.showAndWait();
            return;
        }

        cart.placeOrder();
        updateTotalCost();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Your order has been placed successfully!");
        alert.showAndWait();
    }
    
    @FXML
    void btnViewStorePressed(ActionEvent event) {
        try {
            final String STORE_FXML_FILE_PATH = "/hust/soict/dsai/aims/screen/customer/view/Store.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
            fxmlLoader.setController(new ViewStoreController(store, cart));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void showFilteredMedia(String filterText) {
        filteredData.setPredicate(media -> {
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filterText.toLowerCase();
            if (radioBtnFilterId.isSelected()) {
                return String.valueOf(media.getId()).contains(lowerCaseFilter);
            } else if (radioBtnFilterTitle.isSelected()) {
                return media.getTitle().toLowerCase().contains(lowerCaseFilter);
            }
            return false;
        });
    }
}