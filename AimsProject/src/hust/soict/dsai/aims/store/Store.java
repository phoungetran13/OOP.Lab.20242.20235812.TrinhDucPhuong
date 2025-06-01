package hust.soict.dsai.aims.store;

import java.util.ArrayList;
import hust.soict.dsai.aims.media.Media;
import java.util.List;
import hust.soict.dsai.aims.exception.DuplicateMediaException;
import hust.soict.dsai.aims.exception.LimitExceededException;

public class Store {

    private static final int MAX_ITEMS = 100;
    private ArrayList<Media> itemsInStore = new ArrayList<Media>();

    public void addMedia(Media media) throws LimitExceededException, DuplicateMediaException {
    if (media == null) {
        // Có thể ném một IllegalArgumentException ở đây nếu muốn
        System.out.println("Error: Cannot add null media.");
        return;
    }
    if (itemsInStore.size() >= MAX_ITEMS) {
        throw new LimitExceededException("ERROR: The store is full. Cannot add more media.");
    }
    if (itemsInStore.contains(media)) {
        throw new DuplicateMediaException("ERROR: Media '" + media.getTitle() + "' already exists in the store.");
    }
    itemsInStore.add(media);
    System.out.println("Successfully added '" + media.getTitle() + "' to the store.");
}

    public boolean removeMedia(Media media) {
        if (media != null) {
             boolean removed = itemsInStore.remove(media);
             return removed;
        }
        return false;
    }

    public ArrayList<Media> getItemsInStore() {
        return itemsInStore;
    }

    public void displayStore() {
        System.out.println("***********************STORE***********************");
        System.out.println("Items in the store:");
        if (itemsInStore.isEmpty()) {
            System.out.println("The store is empty.");
        } else {
            for (int i = 0; i < itemsInStore.size(); i++) {
                Media media = itemsInStore.get(i);
                System.out.println((i + 1) + ". " + media.toString());
            }
        }
        System.out.println("**************************************************");
    }

    public List<Media> searchByTitle(String title) {
        List<Media> results = new ArrayList<>();
        for (Media media : itemsInStore) {
            if (media.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(media);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No media found with title containing \"" + title + "\" in the store.");
        } else {
            System.out.println("Search results for title \"" + title + "\" in the store:");
            for (Media media : results) {
                System.out.println("-> " + media.toString());
            }
        }
        return results;
    }
}
