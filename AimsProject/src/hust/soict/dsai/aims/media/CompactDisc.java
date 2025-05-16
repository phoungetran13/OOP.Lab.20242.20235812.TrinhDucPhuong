package hust.soict.dsai.aims.media;

import java.util.ArrayList;
import java.util.List;
import hust.soict.dsai.aims.media.Track; 
import java.util.Objects;

public class CompactDisc extends Disc implements Playable {
    private String artist;
    private List<Track> tracks = new ArrayList<>();

    public CompactDisc(int id, String title, String category, float cost, String artist) {

         super(id, title, category, cost); 
         this.artist = artist;
    }

    public String getArtist() { return artist; }
    public List<Track> getTracks() { return tracks; }


     public void addTrack(Track track) {
        if (track != null && !tracks.contains(track)) {
            tracks.add(track);
        } else {
            System.out.println("Track \"" + (track != null ? track.getTitle() : "null") + "\" is already in the CD or invalid.");
        }
    }

    public void removeTrack(Track track) {
        if (track != null && tracks.contains(track)) {
            tracks.remove(track);
        } else {
            System.out.println("Track \"" + (track != null ? track.getTitle() : "null") + "\" is not in the CD.");
        }
    }

    @Override
    public int getLength() {
        int totalLength = 0;
        for (Track track : tracks) {
            totalLength += track.getLength();
        }
        return totalLength;
    }

    @Override
    public void play() {
        if (!tracks.isEmpty()) {
             System.out.println("Playing CD: " + this.getTitle() + " by " + this.artist);
             System.out.println("Total length: " + this.getLength() + "s");
             System.out.println("--- Tracks ---");
             for (Track track : tracks) {
                 track.play();
             }
             System.out.println("--------------");
        } else {
             System.out.println("Cannot play CD: " + this.getTitle() + " - No tracks available.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CD - ")
          .append("ID: ").append(getId())
          .append(" - Title: ").append(getTitle())
          .append(" - Category: ").append(getCategory())
          .append(" - Artist: ").append(getArtist())
          .append(" - (").append(getLength()).append("s)")
          .append(": ").append(String.format("%.2f$", getCost()));

        if (tracks != null && !tracks.isEmpty()) {
            sb.append("\nTracks:");
            for (Track track : tracks) {
                sb.append("\n\t").append(track.toString());
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false; 
        if (!(o instanceof CompactDisc)) return false; 
        CompactDisc other = (CompactDisc) o;
        return Objects.equals(artist, other.artist);
    }

     @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), artist);
    }

}