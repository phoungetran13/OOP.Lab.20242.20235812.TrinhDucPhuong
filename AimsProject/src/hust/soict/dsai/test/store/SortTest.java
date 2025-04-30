package hust.soict.dsai.test.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;

public class SortTest {
    public static void main(String[] args) {
        List<Media> mediaList = new ArrayList<>();
        mediaList.add(new DigitalVideoDisc(1, "The Lion King", "Animation", 19.95f, "Roger Allers", 87));
        mediaList.add(new CompactDisc(4, "Abbey Road", "Rock", 12.50f, "The Beatles"));
        mediaList.add(new DigitalVideoDisc(2, "Star Wars", "Science Fiction", 24.95f, "George Lucas", 124));
        mediaList.add(new Book(5, "The Lion King", "Storybook", 15.50f, List.of("Disney"))); // Cùng title, giá khác
        mediaList.add(new DigitalVideoDisc(3, "Aladdin", "Animation", 18.99f, "Guy Ritchie", 90));

        System.out.println("--- Danh sách gốc ---");
        mediaList.forEach(System.out::println);

        // Sắp xếp theo Tiêu đề rồi Giá (cao->thấp)
        Collections.sort(mediaList, Media.COMPARE_BY_TITLE_COST);

        System.out.println("\n--- Sắp xếp theo Title -> Cost (desc) ---");
        mediaList.forEach(System.out::println);

        // Sắp xếp theo Giá (cao->thấp) rồi Tiêu đề
        Collections.sort(mediaList, Media.COMPARE_BY_COST_TITLE);

        System.out.println("\n--- Sắp xếp theo Cost (desc) -> Title ---");
        mediaList.forEach(System.out::println);
    }
}