package hust.soict.dsai.aims.cart;

import hust.soict.dsai.aims.media.Media;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private ArrayList<Media> itemsOrdered = new ArrayList<Media>();
    public static final int MAX_NUMBERS_ORDERED = 20;


    public boolean addMedia(Media media) {
        if (media != null && itemsOrdered.size() < MAX_NUMBERS_ORDERED && !itemsOrdered.contains(media)) {
            itemsOrdered.add(media);

            return true;
        } else if (itemsOrdered.contains(media)) {

             return false;
        } else if (itemsOrdered.size() >= MAX_NUMBERS_ORDERED) {

             return false;
        } else {

            return false;
        }
    }

    public boolean removeMedia(Media media) {
        if (media != null && itemsOrdered.remove(media)) {

            return true;
        } else {

            return false;
        }
    }

    public float totalCost() {
        float total = 0;
        for (Media media : itemsOrdered) {
            total += media.getCost();
        }
        return total;
    }

    public void printCart() {
        System.out.println("***********************CART***********************");
        System.out.println("Ordered Items:");
        if (itemsOrdered.isEmpty()) {
            System.out.println("The cart is empty.");
        } else {
            for (int i = 0; i < itemsOrdered.size(); i++) {
                Media media = itemsOrdered.get(i);
                System.out.println((i + 1) + ". " + media.toString());
            }
            System.out.println("Total cost: " + String.format("%.2f", totalCost()) + " $"); // Định dạng tiền tệ
        }
        System.out.println("***************************************************");
    }

    public Media searchById(int id) {
        for (Media media : itemsOrdered) {
            if (media.getId() == id) {
                return media;
            }
        }
        return null;
    }

    public List<Media> searchByTitle(String title) {
        List<Media> results = new ArrayList<>();
        String lowerCaseTitle = title.toLowerCase(); // Tìm kiếm không phân biệt hoa thường
        for (Media media : itemsOrdered) {
            // Dùng contains để tìm gần đúng
            if (media.getTitle().toLowerCase().contains(lowerCaseTitle)) {
                results.add(media);
            }
        }
        return results;
    }

    public void sortByTitle() {
        if (itemsOrdered.isEmpty()) return; // Không sort nếu rỗng
        Collections.sort(itemsOrdered, Media.COMPARE_BY_TITLE_COST);
    }

    public void sortByCost() {
        if (itemsOrdered.isEmpty()) return; // Không sort nếu rỗng
        Collections.sort(itemsOrdered, Media.COMPARE_BY_COST_TITLE);
    }

    public ArrayList<Media> getItemsOrdered() {
        return itemsOrdered; 
    }

    public void placeOrder() {
        itemsOrdered.clear();
    }
}