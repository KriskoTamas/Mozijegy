import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User {

    public enum Privilege {
        Guest,
        User,
        Manager,
        Admin
    }

    private int id;
    private String name, password;
    private Privilege privilege;
    private Date birthdate;
    protected static ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public User(int id, String name, String password, String birthdate, Privilege privilege){
        this.id = id;
        this.name = name;
        this.password = password;
        try {
            if(!birthdate.equals(""))
                this.birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.privilege = privilege;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public Date getBirthdate(){
        return birthdate;
    }

    public Privilege getPrivilege(){
        return privilege;
    }

    public static ArrayList<Reservation> getReservations(){
        return reservations;
    }

    public static void setReservations(ArrayList<Reservation> list){
        reservations = list;
    }

    public static int getReservationIndexById(int id){
        for(int i = 0; i < reservations.size(); i++)
            if(reservations.get(i).getId() == id)
                return i;
        return -1;
    }

    public static int getReservationsMaxId(){
        if(reservations.size() == 0) return -1;
        int max = reservations.get(0).getId();
        for(int i = 1; i < reservations.size(); i++)
            if(reservations.get(i).getId() > max)
                max = reservations.get(i).getId();
        return max;
    }

    public static void addReservation(){
        System.out.println("\n#### Új foglalás ####");
        MovieList.printMovies();
        System.out.print("- Válasszon egy filmet: ");
        int opt = Main.consoleReadInt(true);
        
        if(MovieList.getMovieById(opt).getRating() > Main.diffInYears(Main.loggedUser.getBirthdate(), Calendar.getInstance().getTime())){
            System.out.println("A felhasználó életkora nem felel meg a korhatár besorolásnak");
            return;
        }
        System.out.println("\n#### Filmhez tartozó vetítések ####");
        ArrayList<Screening> list = ScreeningList.getScreeningsById(opt);
        if(list.size() == 0){
            System.out.println("A filmhez nem tartozik egy vetítés sem.");
            return;
        }
        for(Screening s : ScreeningList.getScreeningsById(opt)){
            System.out.println(s.toString());
        }

        System.out.print("- Válasszon egy vetítést: ");
        opt = Main.consoleReadInt(true);
        Screening screening = ScreeningList.getScreeningById(opt);

        ScreeningList.printSeats(opt);
        System.out.print("- Válasszon ülőhelyeket: ");
        System.out.println("(Ha végzett a bevitellel, írjon be egy #-et)");
        String input = "";
        int seatsAdded = 0;
        ArrayList<String> seats = new ArrayList<String>();
        while(seatsAdded < 1 || !input.equals("#")){
            System.out.print("Ülőhely: ");
            input = Main.consoleReadSeat(opt).toUpperCase();
            if(!input.equals("#"))
                seats.add(input);
            seatsAdded++;
        }
        Reservation r = new Reservation(Main.loggedUser.getId(), getReservationsMaxId() + 1, seats, screening);
        reservations.add(r);
        UserList.writeReservations();
    }

    public static void editReservation(){
        System.out.println("\n#### Foglalás módosítása ####");
        System.out.println("(Ahol módosítani nem kíván, Enterrel menjen tovább)");
        System.out.print("- Válasszon egy foglalást: ");
        int id = Main.consoleReadInt(true);
        Reservation original = reservations.get(getReservationIndexById(id));

        MovieList.printMovies();
        System.out.print("- Válasszon egy filmet: ");
        int movieId = Main.consoleReadInt(false);

        Screening screening = original.getScreening();
        if(movieId != -1){
            System.out.println("\n#### Filmhez tartozó vetítések ####");
            for(Screening s : ScreeningList.getScreeningsById(movieId)){
                System.out.println(s.toString());
            }
    
            System.out.print("- Válasszon egy vetítést: ");
            int screeningId = Main.consoleReadInt(true);
            screening = ScreeningList.getScreeningById(screeningId);
        }

        ScreeningList.printSeats(screening.getId());

        System.out.print("- Válasszon ülőhelyeket: ");
        System.out.println("(Ha végzett a bevitellel, írjon be egy #-et)");
        String input = "";
        int seatsAdded = 0;
        ArrayList<String> seats = new ArrayList<String>();

        while(seatsAdded < 1 || !input.equals("#")){
            System.out.print("Ülőhely: ");
            input = Main.consoleReadSeat(screening.getId()).toUpperCase();
            if(!input.equals("#"))
                seats.add(input);
            seatsAdded++;
        }
        reservations.set(getReservationIndexById(id), new Reservation(original.getUserId(), original.getId(), seats.isEmpty() ? original.getSeats() : seats, screening));
        System.out.println("A foglalás sikeresen elmentve.");
        UserList.writeReservations();
    }

    public static void deleteReservation(){
        System.out.println("\n#### Foglalás törlése ####");
        System.out.print("Törölni kívánt foglalás azonosítója: ");
        int id = Main.consoleReadInt(true);
        reservations.remove(getReservationIndexById(id));
        UserList.writeReservations();
    }

    public static void printReservations(){
        System.out.println("\n#### Felhasználó foglalásai ####");
        int c = 0;
        for(Reservation r : reservations){
            if(Main.loggedUser.getId() == r.getUserId()){
                System.out.println(r.toString());
                c++;
            }
        }
        if(c == 0){
            System.out.println("Nincsenek foglalások");
            return;
        }
    }

    public static boolean checkForAccess(String name, String password){
        for(User u : Main.userList){
            if(u.getName().equals(name) && u.getPassword().equals(Main.md5(password))){
                Main.loggedUser = u;
                return true;
            }
        }
        return false;
    }

    public static void readUsers() {
        Main.userList.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.fileUsers), "UTF-8"));
            String row;
            while((row = br.readLine()) != null){
                if(row.equals(""))
                    continue;
                String[] data = row.split(";");
                Main.userList.add(new User(Integer.parseInt(data[0]), data[1], data[2], data[3], Privilege.valueOf(data[4])));
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
