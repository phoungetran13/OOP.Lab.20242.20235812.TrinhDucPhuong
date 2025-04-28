package hust.soict.dsai.aims.media;

public abstract class Disc extends Media {

    private String director;
    private int length;

    public Disc(int id, String title, String category, float cost, String director, int length) {
        super(id, title, category, cost);
        this.director = director;
        this.length = length;
    }

    public Disc(int id, String title, String category, float cost, String director) {
        super(id, title, category, cost);
        this.director = director;
    }

     public Disc(int id, String title, String category, float cost) {
        super(id, title, category, cost);
    }


    public String getDirector() {
        return director;
    }

    public int getLength() {
        return length;
    }

}