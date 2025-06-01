package hust.soict.dsai.aims.screen.customer.controller;

import java.io.IOException;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewStoreController {

    private Store store;
    private Cart cart;

    @FXML
    private GridPane gridPane;
    
    // Constructor để nhận đối tượng Store và Cart từ bên ngoài
    // Điều này cần thiết để Controller có dữ liệu để hiển thị và thao tác
    public ViewStoreController(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
    }

    // Phương thức initialize() sẽ được JavaFX tự động gọi sau khi file FXML được tải xong. [cite: 125, 126]
    // Đây là nơi lý tưởng để điền dữ liệu vào giao diện.
    @FXML
    public void initialize() {
        final String ITEM_FXML_FILE_PATH = "/hust/soict/dsai/aims/screen/customer/view/Item.fxml";
        int column = 0;
        int row = 1;
        
        // Lặp qua tất cả các item trong store để hiển thị chúng lên gridPane
        for (int i = 0; i < store.getItemsInStore().size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(ITEM_FXML_FILE_PATH));
                
                // Mỗi item sẽ có một controller riêng (ItemController)
                ItemController itemController = new ItemController(cart); // Truyền cart vào ItemController
                fxmlLoader.setController(itemController);

                AnchorPane anchorPane = new AnchorPane();
                anchorPane = fxmlLoader.load();
                
                // Truyền dữ liệu media vào cho ItemController
                itemController.setData(store.getItemsInStore().get(i));

                // Thêm item vào gridPane
                if (column == 3) {
                    column = 0;
                    row++;
                }
                
                gridPane.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(20, 10, 10, 10));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức này được gọi khi nút "View Cart" được nhấn. [cite: 186, 187]
    @FXML
    void btnViewCartPressed(ActionEvent event) {
        try {
            final String CART_FXML_FILE_PATH = "/hust/soict/dsai/aims/screen/customer/view/Cart.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CART_FXML_FILE_PATH));
            
            // Tạo CartController và truyền cả store và cart vào [cite: 189]
            fxmlLoader.setController(new CartController(store, cart));
            
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cart");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}