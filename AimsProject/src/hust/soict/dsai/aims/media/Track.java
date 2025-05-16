package hust.soict.dsai.aims.media;

import java.util.Objects;

public class Track implements Playable {
    private String title;
    private int length;

    public Track(String title, int length) {
        this.title = title;
        this.length = length;
    }

    public String getTitle() { return title; }
    public int getLength() { return length; }

    @Override
    public void play() {
        if (this.length > 0) {
           System.out.println("Playing track: " + this.getTitle() + " - Length: " + this.getLength() + "s"); // Thêm "s" cho rõ
        } else {
            System.out.println("Cannot play track: " + this.getTitle() + " - Length is zero or invalid.");
        }
    }

    @Override
    public String toString() {
        return getTitle() + " (" + getLength() + "s)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Track other = (Track) obj;
        return length == other.length && Objects.equals(title, other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, length);
    }
}