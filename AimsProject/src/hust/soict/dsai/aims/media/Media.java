package hust.soict.dsai.aims.media;

import java.util.Comparator;
import java.util.Objects;

public abstract class Media {
    protected int id;
    protected String title;
    protected String category;
    protected float cost;

    public Media(int id, String title, String category, float cost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public float getCost() { return cost; }

    public static final Comparator<Media> COMPARE_BY_TITLE_COST = new Comparator<Media>() {
        public int compare(Media m1, Media m2) {
            int titleCompare = m1.getTitle().compareToIgnoreCase(m2.getTitle());
            if (titleCompare != 0) return titleCompare;
            return Float.compare(m2.getCost(), m1.getCost());
        }
    };

    public static final Comparator<Media> COMPARE_BY_COST_TITLE = new Comparator<Media>() {
        public int compare(Media m1, Media m2) {
            int costCompare = Float.compare(m2.getCost(), m1.getCost());
            if (costCompare != 0) return costCompare;
            return m1.getTitle().compareToIgnoreCase(m2.getTitle());
        }
    };

    @Override
    public String toString() {
        return "ID: " + id + " - Title: " + title + " - Category: " + category + " - Cost: " + String.format("%.2f$", cost);
    }

     public boolean isMatch(String title) {
        return this.getTitle().equalsIgnoreCase(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Media)) return false;
        Media other = (Media) o;
        return this.title != null && this.title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title != null ? title.toLowerCase() : null);
    }
}