package hust.soict.dsai.test.cart;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.DigitalVideoDisc;

public class CartTest {
    public static void main(String[] args) {
        // Tạo một giỏ hàng mới
        Cart cart = new Cart();

        // Tạo các đĩa DVD mẫu
        DigitalVideoDisc dvd1 = new DigitalVideoDisc("The Lion King", "Animation", "Roger Allers", 87, 19.95f);
        DigitalVideoDisc dvd2 = new DigitalVideoDisc("Star Wars", "Science Fiction", "George Lucas", 124, 24.95f);
        DigitalVideoDisc dvd3 = new DigitalVideoDisc("Aladin", "Animation","",100, 18.99f);
        DigitalVideoDisc dvd4 = new DigitalVideoDisc("The Lion King 2", "Animation", "Darrell Rooney", 78, 15.50f);

        // Thêm các DVD vào giỏ hàng
        cart.addMedia(dvd1);
        cart.addMedia(dvd2);
        cart.addMedia(dvd3);

        // In ra danh sách các mặt hàng trong giỏ
        cart.printCart();
        System.out.println();

        // Tìm kiếm DVD theo ID
        cart.searchById(dvd2.getId());
        cart.searchById(99); // ID không tồn tại
        System.out.println();

        // Tìm kiếm DVD theo tiêu đề
        cart.searchByTitle("Lion");
        cart.searchByTitle("Aladin");
        cart.searchByTitle("Spider-Man"); // Tiêu đề không tồn tại
        System.out.println();

        // Thử xóa một DVD
        cart.removeMedia(dvd1);
        cart.printCart();
        System.out.println();

        // Thử xóa một DVD không tồn tại
        cart.removeMedia(dvd4);
    }
}