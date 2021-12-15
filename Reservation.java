import java.util.ArrayList;

public class Reservation {
    private int userId, id;
    private ArrayList<String> seats;
    private Screening screening;

    public Reservation(int userId, int id, ArrayList<String> seats, Screening screening){
        this.userId = userId;
        this.id = id;
        this.seats = seats;
        this.screening = screening;
    }

    public int getUserId(){
        return userId;
    }

    public int getId(){
        return id;
    }

    public ArrayList<String> getSeats(){
        return seats;
    }

    public Screening getScreening(){
        return screening;
    }

    public String toString(){
        String str = "#: " + id + ", Ülőhelyek: [";
        for(int i = 0; i < seats.size(); i++){
            str += seats.get(i);
            if(i < seats.size() - 1)
                str += ", ";
        }
        str += "], vetítés:\n\t" + screening.toString();
        return str;
    }
}
