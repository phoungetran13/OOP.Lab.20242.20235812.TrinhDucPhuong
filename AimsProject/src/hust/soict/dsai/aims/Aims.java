package hust.soict.dsai.aims;

import hust.soict.dsai.aims.cart.Cart;
import hust.soict.dsai.aims.media.*;
import hust.soict.dsai.aims.store.Store;
import hust.soict.dsai.aims.media.Track;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Aims {

    private static Store store = new Store();
    private static Cart cart = new Cart();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextMediaId = 7;

    public static void main(String[] args) {

        try {
            DigitalVideoDisc dvd1 = new DigitalVideoDisc(1, "The Lion King", "Animation", 19.95f, "Roger Allers", 87);
            DigitalVideoDisc dvd2 = new DigitalVideoDisc(2, "Star Wars", "Science Fiction", 24.95f, "George Lucas", 124);
            DigitalVideoDisc dvd3 = new DigitalVideoDisc(3, "Aladdin", "Animation", 18.99f, "Guy Ritchie", 90);
            CompactDisc cd1 = new CompactDisc(4, "Abbey Road", "Rock", 12.50f, "The Beatles");
            cd1.addTrack(new Track("Come Together", 4));
            cd1.addTrack(new Track("Something", 3));
            Book book1 = new Book(5, "The Lord of the Rings", "Fantasy", 22.50f, List.of("J.R.R. Tolkien"));
            Book book2 = new Book(6, "Dune", "Science Fiction", 20.80f, List.of("Frank Herbert"));

            store.addMedia(dvd1);
            store.addMedia(dvd2);
            store.addMedia(dvd3);
            store.addMedia(cd1);
            store.addMedia(book1);
            store.addMedia(book2);
        } catch (Exception e) {
             System.err.println("Error adding sample media: " + e.getMessage());
        }


        int choice;
        do {
            showMenu();
            choice = getUserChoice(0, 3);

            switch (choice) {
                case 1:
                    viewStore();
                    break;
                case 2:
                    updateStore();
                    break;
                case 3:
                    viewCart();
                    break;
                case 0:
                    System.out.println("END");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    // --- CÁC HÀM HIỂN THỊ MENU ---
    public static void showMenu() {
        System.out.println("\nAIMS - Main Menu: ");
        System.out.println("--------------------------------");
        System.out.println("1. View store");
        System.out.println("2. Update store");
        System.out.println("3. See current cart");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
        System.out.print("Please choose a number (0-3): ");
    }

    public static void storeMenu() {
        System.out.println("\nOptions - Store Menu: ");
        System.out.println("--------------------------------");
        System.out.println("1. See a media’s details");
        System.out.println("2. Add a media to cart");
        System.out.println("3. Play a media");
        System.out.println("4. See current cart");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.print("Please choose a number (0-4): ");
    }

     public static void mediaDetailsMenu(boolean isPlayable) {
        System.out.println("\nOptions - Media Details: ");
        System.out.println("--------------------------------");
        System.out.println("1. Add to cart");
        if (isPlayable) {
             System.out.println("2. Play");
        }
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.print("Please choose a number (0 or 1" + (isPlayable ? " or 2" : "") + "): ");
    }

    public static void cartMenu() {
        System.out.println("\nOptions - Cart Menu: ");
        System.out.println("--------------------------------");
        System.out.println("1. Filter media in cart");
        System.out.println("2. Sort media in cart");
        System.out.println("3. Remove media from cart");
        System.out.println("4. Play a media");
        System.out.println("5. Place order");
        System.out.println("0. Back");
        System.out.println("--------------------------------");
        System.out.print("Please choose a number (0-5): ");
    }


    // --- CÁC HÀM XỬ LÝ NGHIỆP VỤ ---

    private static void viewStore() {
        store.displayStore(); // Giả sử hàm trong Store là printStore()
        int choice;
        do {
            storeMenu();
            choice = getUserChoice(0, 4);

            switch (choice) {
                case 1:
                    seeMediaDetails();
                    break;
                case 2:
                    addMediaToCartFromStore();
                    break;
                case 3:
                    playMediaFromStore();
                    break;
                case 4:
                    viewCart(); // Gọi hàm viewCart của Aims
                    store.displayStore(); // In lại store sau khi quay lại từ cart
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
            }
        } while (choice != 0);
    }

    private static void seeMediaDetails() {
        System.out.print("Enter the title of the media to see details: ");
        String title = scanner.nextLine();
        List<Media> foundList = store.searchByTitle(title);
        Media foundMedia = null;

        if (!foundList.isEmpty()) {
            foundMedia = foundList.get(0); // Lấy phần tử đầu tiên
            if (foundList.size() > 1) {
                // Xử lý trường hợp tìm thấy nhiều kết quả
                System.out.println("Warning: Found " + foundList.size() + " items matching '" + title + "'. Showing details for the first one: " + foundMedia.getTitle());
            }

            // --- Chỉ thực hiện khi foundMedia không null ---
            System.out.println("--- Media Details ---");
            System.out.println(foundMedia.toString());
            System.out.println("---------------------");

            boolean isPlayable = foundMedia instanceof Playable;
            int choice;
            do {
                mediaDetailsMenu(isPlayable);
                int maxChoice = isPlayable ? 2 : 1;
                choice = getUserChoice(0, maxChoice);

                switch (choice) {
                    case 1:
                        if (cart.addMedia(foundMedia)) {
                           System.out.println("Added '" + foundMedia.getTitle() + "' to cart.");
                        } else {
                            System.out.println("Could not add '" + foundMedia.getTitle() + "' to cart (maybe full or already exists).");
                        }
                        return; // Quay lại storeMenu
                    case 2:
                        if (isPlayable) {
                            ((Playable) foundMedia).play();
                        }
                        break;
                    case 0:
                        System.out.println("Returning to store menu...");
                        break;
                }
            } while (choice != 0);
            // --- Hết phần xử lý khi foundMedia không null ---

        } else {
            System.out.println("--> Media with title containing '" + title + "' not found in the store.");
        }
    }


    private static void addMediaToCartFromStore() {
        store.displayStore();
        System.out.print("Enter the title of the media to add to cart: ");
        String title = scanner.nextLine();

        List<Media> foundList = store.searchByTitle(title);
        Media mediaToAdd = null;

        if (!foundList.isEmpty()) {
            mediaToAdd = foundList.get(0); // Lấy cái đầu tiên
            if (foundList.size() > 1) {
                 // Xử lý trường hợp tìm thấy nhiều kết quả
                System.out.println("Warning: Found " + foundList.size() + " items matching '" + title + "'. Adding the first one: " + mediaToAdd.getTitle());
            }
        }

        if (mediaToAdd != null) {
            if (cart.addMedia(mediaToAdd)) {
                 System.out.println("Successfully added '" + mediaToAdd.getTitle() + "' to cart.");
                 // Đếm DVD nếu thêm thành công
                 if (mediaToAdd instanceof DigitalVideoDisc) {
                    int dvdCount = 0;
                    for(Media m : cart.getItemsOrdered()){
                        if(m instanceof DigitalVideoDisc){
                            dvdCount++;
                        }
                    }
                    System.out.println("Number of DVDs currently in cart: " + dvdCount);
                 }
                 System.out.println("Current total items in cart: " + cart.getItemsOrdered().size());
            } else {
                 System.out.println("Failed to add '" + mediaToAdd.getTitle() + "' to cart (maybe full or already exists).");
            }
        } else {
            System.out.println("Media with title containing '" + title + "' not found in the store.");
        }
    }

    private static void playMediaFromStore() {
         store.displayStore();
         System.out.print("Enter the title of the media to play: ");
         String title = scanner.nextLine();

         List<Media> foundList = store.searchByTitle(title);
         Media mediaToPlay = null;

         if (!foundList.isEmpty()) {
             mediaToPlay = foundList.get(0); // Lấy cái đầu tiên
             if (foundList.size() > 1) {
                  // Xử lý trường hợp tìm thấy nhiều kết quả
                 System.out.println("Warning: Found " + foundList.size() + " items matching '" + title + "'. Playing the first one: " + mediaToPlay.getTitle());
             }
         }

        if (mediaToPlay != null) {
            if (mediaToPlay instanceof Playable) {
                ((Playable) mediaToPlay).play();
            } else {
                System.out.println("This media type (" + mediaToPlay.getClass().getSimpleName() + ") cannot be played.");
            }
        } else {
            System.out.println("Media with title containing '" + title + "' not found in the store.");
        }
    }

    private static void updateStore() {
        System.out.println("\n--- Update Store ---");
        System.out.println("1. Add Media");
        System.out.println("2. Remove Media");
        System.out.println("0. Back");
        System.out.print("Choose option (0-2): ");
        int choice = getUserChoice(0, 2);

        switch (choice) {
            case 1:
                addMediaStore(); // Gọi hàm xử lý thêm
                break;
            case 2:
                removeMediaStore(); // Gọi hàm xử lý xóa
                break;
            case 0:
                System.out.println("Returning to main menu...");
                break;
        }
    }

    private static void addMediaStore() {
        System.out.println("\n--- Add New Media to Store ---");
        System.out.println("Select the type of media to add:");
        System.out.println("1. Book");
        System.out.println("2. Compact Disc (CD)");
        System.out.println("3. Digital Video Disc (DVD)");
        System.out.println("0. Cancel");
        System.out.print("Choose type (0-3): ");
        int typeChoice = getUserChoice(0, 3);

        if (typeChoice == 0) {
            System.out.println("Add media cancelled.");
            return; // Quay lại menu updateStore
        }

        System.out.println("\n--- Enter Common Media Details ---");
        String title = getNonEmptyStringInput("Enter Title: ");

        // Kiểm tra title trùng lặp trong store
        if (!store.searchByTitle(title).isEmpty()) {
            System.out.println("Error: A media with a similar title already exists in the store. Cannot add duplicate.");
            return;
        }

        String category = getNonEmptyStringInput("Enter Category: ");
        float cost = getValidatedFloatInput("Enter Cost (e.g., 19.95, must be >= 0): ");
        if (cost < 0) {
             System.out.println("Invalid cost entered. Add media cancelled.");
             return;
        }

        int id = nextMediaId++; // Lấy ID tiếp theo

        Media newMedia = null;

        try {
            switch (typeChoice) {
                case 1: // Thêm Book
                    System.out.println("\n--- Enter Book Details ---");
                    List<String> authors = new ArrayList<>();
                    String authorInput;
                    System.out.println("Enter authors (one per line, type 'done' when finished):");
                    while (true) {
                        System.out.print("Author name: ");
                        authorInput = scanner.nextLine().trim();
                        if (authorInput.equalsIgnoreCase("done")) {
                            if (authors.isEmpty()) { // Phải có ít nhất 1 tác giả
                                System.out.println("Error: Book must have at least one author. Please add an author.");
                            } else {
                                break;
                            }
                        } else if (!authorInput.isBlank()) {
                            authors.add(authorInput);
                        } else {
                            System.out.println("Author name cannot be empty. Please try again or type 'done'.");
                        }
                    }
                    newMedia = new Book(id, title, category, cost, authors);
                    break;

                case 2: // Thêm CD
                    System.out.println("\n--- Enter CD Details ---");
                    String artist = getNonEmptyStringInput("Enter Artist: ");
                    CompactDisc newCD = new CompactDisc(id, title, category, cost, artist);

                    System.out.println("\n--- Add Tracks to CD (optional, type 'done' for title when finished) ---");
                    while (true) {
                        String trackTitle = getNonEmptyStringInput("Track Title (or 'done'): ");
                        if (trackTitle.equalsIgnoreCase("done")) {
                            break;
                        }
                        int trackLength = getValidatedIntInput("Track '" + trackTitle + "' Length (seconds, must be >= 0): ");
                        if (trackLength < 0) {
                             System.out.println("Invalid length (must be >= 0). Track not added.");
                             continue;
                        }
                        newCD.addTrack(new Track(trackTitle, trackLength));
                        System.out.println("Track '" + trackTitle + "' added.");
                    }
                    newMedia = newCD;
                    break;

                case 3: // Thêm DVD
                    System.out.println("\n--- Enter DVD Details ---");
                    String director = getNonEmptyStringInput("Enter Director: ");
                    int length = getValidatedIntInput("Enter Length (minutes, must be > 0): ");
                    if (length <= 0) { // Độ dài phải > 0
                         System.out.println("Invalid length (must be > 0). Cannot add DVD.");
                         return;
                    }
                    newMedia = new DigitalVideoDisc(id, title, category, cost, director, length);
                    break;
            }

            // Thêm Media vào Store
            if (newMedia != null) {
                if (store.addMedia(newMedia)) {
                    System.out.println("\n--- Media added successfully to store! ---");
                    store.displayStore(); // Hiển thị lại store
                } else {
                    System.out.println("\n--- Failed to add media to the store. ---");
                    nextMediaId--; // Hoàn lại ID nếu không thêm thành công
                }
            }

        } catch (Exception e) {
            System.err.println("\nAn error occurred while creating or adding media: " + e.getMessage());
            nextMediaId--; // Hoàn lại ID nếu có lỗi
        }
    }


    private static void removeMediaStore() {
         store.displayStore();
         if (store.getItemsInStore().isEmpty()) {
              System.out.println("Store is empty. Nothing to remove.");
              return;
         }
         System.out.print("Enter title of media to remove from store: ");
         String removeTitle = scanner.nextLine();

         List<Media> storeListToRemove = store.searchByTitle(removeTitle);
         Media mediaToRemoveFromStore = null;

         // Xử lý tìm thấy nhiều/một/không có kết quả
         if (storeListToRemove.isEmpty()) {
             System.out.println("Media containing title '" + removeTitle + "' not found in store.");
             return;
         } else if (storeListToRemove.size() == 1) {
             mediaToRemoveFromStore = storeListToRemove.get(0);
         } else {
             System.out.println("Found multiple media items matching '" + removeTitle + "'. Please choose which one to remove:");
             for (int i = 0; i < storeListToRemove.size(); i++) {
                 System.out.println((i + 1) + ". " + storeListToRemove.get(i).toString());
             }
             System.out.print("Enter the number (1-" + storeListToRemove.size() + ") or 0 to cancel: ");
             int removeChoice = getUserChoice(0, storeListToRemove.size());
             if (removeChoice == 0) {
                 System.out.println("Remove cancelled.");
                 return;
             }
             mediaToRemoveFromStore = storeListToRemove.get(removeChoice - 1);
         }

         // Tiến hành xóa khỏi store và cart
         if (mediaToRemoveFromStore != null) {
             // Giả sử Store.removeMedia trả về boolean
             if (store.removeMedia(mediaToRemoveFromStore)) {
                 System.out.println("Successfully removed '" + mediaToRemoveFromStore.getTitle() + "' from the store.");
                 // Tự động tìm và xóa khỏi giỏ hàng
                 List<Media> cartListToRemove = cart.searchByTitle(mediaToRemoveFromStore.getTitle());
                 List<Media> itemsActuallyRemovedFromCart = new ArrayList<>();
                 for (Media itemInCart : cartListToRemove) {
                     if (itemInCart.equals(mediaToRemoveFromStore)) {
                         // Giả sử Cart.removeMedia trả về boolean
                        if (cart.removeMedia(itemInCart)) {
                            itemsActuallyRemovedFromCart.add(itemInCart);
                        }
                     }
                 }
                 if (!itemsActuallyRemovedFromCart.isEmpty()) {
                     System.out.println("Also removed the following matching item(s) from the cart:");
                     for (Media removedItem : itemsActuallyRemovedFromCart) {
                         System.out.println("- " + removedItem.getTitle());
                     }
                 }
             } else {
                 System.out.println("Failed to remove '" + mediaToRemoveFromStore.getTitle() + "' from the store (maybe it was already removed?).");
             }
         }
    }

    private static void viewCart() {
        cart.printCart();
        int choice;
        do {
            cartMenu(); // Hiển thị menu giỏ hàng (của Aims)
            choice = getUserChoice(0, 5);

            switch (choice) {
                case 1:
                    filterCart();
                    break;
                case 2:
                    sortCart();
                    break;
                case 3:
                    removeMediaFromCart();
                    break;
                case 4:
                    playMediaFromCart();
                    break;
                case 5:
                    if(cart.getItemsOrdered().isEmpty()){
                         System.out.println("Cart is empty. Cannot place order.");
                    } else {
                         cart.placeOrder(); // Cart chỉ clear list
                         System.out.println("Order placed successfully! Cart is now empty.");
                    }
                    return; // Quay về main menu
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
            }
             // In lại giỏ hàng sau các hành động
             if (choice >= 1 && choice <= 4) {
                 cart.printCart();
             }
        } while (choice != 0);
    }

     private static void filterCart() {
          System.out.println("\n--- Filter Options ---");
          System.out.println("1. Filter by ID");
          System.out.println("2. Filter by Title");
          System.out.println("0. Back");
          System.out.print("Choose option (0-2): ");
          int filterChoice = getUserChoice(0, 2);
          switch (filterChoice) {
              case 1:
                  System.out.print("Enter ID to filter: ");
                  int id = -1;
                  try {
                      id = Integer.parseInt(scanner.nextLine());
                      Media foundById = cart.searchById(id);
                      System.out.println("\n--- Filter Result (ID: " + id + ") ---");
                      if (foundById != null) {
                          System.out.println(foundById.toString());
                      } else {
                          System.out.println("No media found with ID: " + id);
                      }
                       System.out.println("-----------------------------");
                  } catch (NumberFormatException e) {
                      System.out.println("Invalid ID format.");
                  }
                  break;
              case 2:
                  System.out.print("Enter Title keyword to filter: ");
                  String titleKeyword = scanner.nextLine();
                  List<Media> foundByTitle = cart.searchByTitle(titleKeyword);
                  System.out.println("\n--- Filter Result (Title contains: '" + titleKeyword + "') ---");
                  if (!foundByTitle.isEmpty()) {
                      for (int i = 0; i < foundByTitle.size(); i++) {
                           System.out.println((i + 1) + ". " + foundByTitle.get(i).toString());
                      }
                  } else {
                      System.out.println("No media found with title containing: '" + titleKeyword + "'");
                  }
                  System.out.println("-------------------------------------------");
                  break;
              case 0:
                 break; // Quay lại cart menu
          }
     }

     private static void sortCart() {
          System.out.println("\n--- Sort Options ---");
          System.out.println("1. Sort by Title (then Cost descending)");
          System.out.println("2. Sort by Cost (descending, then Title)");
          System.out.println("0. Back");
          System.out.print("Choose option (0-2): ");
          int sortChoice = getUserChoice(0, 2);
          switch (sortChoice) {
              case 1:
                  cart.sortByTitle();
                  System.out.println("Cart has been sorted by Title -> Cost Desc.");
                  break;
              case 2:
                  cart.sortByCost();
                   System.out.println("Cart has been sorted by Cost Desc -> Title.");
                  break;
               case 0:
                 break; // Quay lại cart menu
          }
     }

     private static void removeMediaFromCart() {
         cart.printCart();
         ArrayList<Media> items = cart.getItemsOrdered();

         // Kiểm tra null và rỗng
         if (items == null || items.isEmpty()) {
             System.out.println("Cart is empty. Nothing to remove.");
             return;
         }

         System.out.print("Enter the number of the media to remove (1-" + items.size() + "), or 0 to cancel: ");
         int removeChoice = getUserChoice(0, items.size());

         if (removeChoice > 0) {
             // Lấy media theo index (lựa chọn - 1)
             Media mediaToRemove = items.get(removeChoice - 1);
             if (cart.removeMedia(mediaToRemove)) {
                 System.out.println("Successfully removed '" + mediaToRemove.getTitle() + "' from the cart.");
             } else {
                 System.out.println("Could not remove '" + mediaToRemove.getTitle() + "'. Please try again.");
             }
         } else {
             System.out.println("Removal cancelled.");
         }
     }

    private static void playMediaFromCart() {
         cart.printCart();
         ArrayList<Media> items = cart.getItemsOrdered();

         // Kiểm tra null và rỗng
         if (items == null || items.isEmpty()) {
             System.out.println("Cart is empty. Nothing to play.");
             return;
         }

         System.out.print("Enter the number of the media to play (1-" + items.size() + "), or 0 to cancel: ");
         int playChoice = getUserChoice(0, items.size());

         if (playChoice > 0) {
            Media mediaToPlay = items.get(playChoice - 1); // Lấy media theo index

            if (mediaToPlay instanceof Playable) {
                System.out.println("\n--- Playing from Cart ---");
                ((Playable) mediaToPlay).play();
                System.out.println("-------------------------\n");
            } else {
                System.out.println("This media type (" + mediaToPlay.getClass().getSimpleName() + ") cannot be played.");
            }
         } else {
              System.out.println("Play cancelled.");
         }
    }


    // --- HÀM HỖ TRỢ NHẬP LIỆU ---
    private static int getUserChoice(int min, int max) {
        int choice = -1;
        while (true) {
            try {
                 choice = Integer.parseInt(scanner.nextLine()); // Đọc cả dòng rồi parse
                 if (choice >= min && choice <= max) {
                     return choice;
                 } else {
                     System.out.print("Invalid choice. Please enter a number between " + min + " and " + max + ": ");
                 }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static String getNonEmptyStringInput(String prompt) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isBlank()) {
                return input;
            } else {
                System.out.println("Input cannot be empty or just whitespace. Please try again.");
            }
        }
    }

    private static float getValidatedFloatInput(String prompt) {
        float value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Float.parseFloat(scanner.nextLine());
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Value must be non-negative (>= 0).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (e.g., 19.95).");
            }
        }
    }

    private static int getValidatedIntInput(String prompt) {
        int value;
         while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

} 