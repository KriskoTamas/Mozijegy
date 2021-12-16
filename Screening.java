public class Screening {
    private int id, roomNumber;
    private String screeningTime;
    private Movie movie;

    public Screening(int id, int roomNumber, String screeningTime, Movie movie){
        this.id = id;
        this.roomNumber = roomNumber;
        this.screeningTime = screeningTime;
        this.movie = movie;
    }

    public int getId(){
        return id;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public Movie getMovie(){
        return movie;
    }

    public String getScreeningTime(){
        return screeningTime;
    }

    public String toString(){
        return "#: " + id + ", Teremszám: " + roomNumber + ", vetítés ideje: " + screeningTime + ", film:\n\t" + movie.toString();
    }
}
