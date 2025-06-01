package hust.soict.dsai.aims.media;

import java.util.Objects;

import hust.soict.dsai.aims.exception.PlayerException;

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
    public void play() throws PlayerException {
        if (this.getLength() > 0) {
           System.out.println("Playing track: " + this.getTitle() + " - Length: " + this.getLength() + "s");
        } else {
            // Ném ra ngoại lệ nếu thời lượng không hợp lệ
            throw new PlayerException("ERROR: Track length is non-positive for track: " + this.getTitle());
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