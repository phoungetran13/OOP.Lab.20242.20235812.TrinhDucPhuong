package hust.soict.dsai.test.store;

import java.util.ArrayList;
import java.util.List;

import hust.soict.dsai.aims.media.Book;
import hust.soict.dsai.aims.media.Media;
import hust.soict.dsai.aims.media.CompactDisc;
import hust.soict.dsai.aims.media.DigitalVideoDisc;



public class MediaTest {

    public static void main(String[] args) {
        List<Media> mediaList = new ArrayList<Media>();

        mediaList.add(new Book(1, "The Lord of the Rings", "Fantasy", 19.99f, List.of("J.R.R. Tolkien")));
        mediaList.add(new Book(4, "Dune", "Sci-Fi", 22.30f, List.of("Frank Herbert")));
        mediaList.add(new CompactDisc(2, "Abbey Road", "Rock", 12.50f, "The Beatles"));
        mediaList.add(new DigitalVideoDisc(3, "Inception", "Sci-Fi", 15.00f, "Christopher Nolan", 148));

        System.out.println("--- Demo Polymorphism with toString() ---");
        for (Media mediaItem : mediaList) {
            System.out.println(mediaItem.toString());

        }
        System.out.println("-----------------------------------------");

    }
}