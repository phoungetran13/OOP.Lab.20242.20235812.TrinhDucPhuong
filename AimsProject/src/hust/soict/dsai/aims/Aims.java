package hust.soict.dsai.aims;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.*;
import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.screen.customer.controller.ViewStoreController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aims extends Application {

    private static Store store;
    private static Cart cart;

    @Override
    public void start(Stage primaryStage) throws Exception {
        store = new Store();
        cart = new Cart();

        DigitalVideoDisc dvd1 = new DigitalVideoDisc(1, "The Lion King", "Animation", 19.95f, "Roger Allers", 87);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc(2, "Star Wars", "Science Fiction", 24.95f, "George Lucas", 124);
        Book book1 = new Book(3, "The Lord of the Rings", "Fantasy", 22.50f, "J.R.R. Tolkien");

        DigitalVideoDisc dvd3 = new DigitalVideoDisc(4, "Inception", "Sci-Fi", 21.55f, "Christopher Nolan", 148);
        DigitalVideoDisc dvd4 = new DigitalVideoDisc(5, "The Matrix", "Sci-Fi", 18.75f, "Wachowskis", 136);

        Book book2 = new Book(6, "A Game of Thrones", "Fantasy", 25.99f, "George R.R. Martin");
        Book book3 = new Book(7, "Dune", "Sci-Fi", 15.50f, "Frank Herbert");

        CompactDisc cd1 = new CompactDisc(8, "Thriller", "Pop", 10.99f, "Michael Jackson");
        cd1.addTrack(new Track("Wanna Be Startin' Somethin'", 362));
        cd1.addTrack(new Track("Thriller", 357));
        cd1.addTrack(new Track("Beat It", 258));

        CompactDisc cd2 = new CompactDisc(9, "Back in Black", "Rock", 12.99f, "AC/DC");
        cd2.addTrack(new Track("Hells Bells", 312));
        cd2.addTrack(new Track("Back in Black", 255));

        store.addMedia(dvd1);
        store.addMedia(dvd2);
        store.addMedia(book1);
        store.addMedia(dvd3);
        store.addMedia(dvd4);
        store.addMedia(book2);
        store.addMedia(book3);
        store.addMedia(cd1);
        store.addMedia(cd2);
        
        final String STORE_FXML_FILE_PATH = "/hust/soict/dsai/aims/screen/customer/view/Store.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
        
        fxmlLoader.setController(new ViewStoreController(store, cart));
        
        Parent root = fxmlLoader.load();
        
        primaryStage.setTitle("AIMS");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}