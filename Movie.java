public class Movie {

    private int id, rating;
    private String title, subtitle, dubbing;

    public Movie(int id, String title, int rating, String subtitle, String dubbing){
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.subtitle = subtitle;
        this.dubbing = dubbing;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public int getRating(){
        return rating;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public String getDubbing(){
        return dubbing;
    }

    public String toString(){
        return "#: " + id + ", Cím: " + title + ", besorolás: " + rating + ", felirat: " + (subtitle.isEmpty() ? "nincs" : subtitle) + ", szinkron: " + dubbing;
    }

}
