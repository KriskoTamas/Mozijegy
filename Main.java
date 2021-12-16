import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Main
 */
public class Main {

    public static final String fileUsers = "users.txt";
    public static final String fileMovies = "movies.txt";
    public static final String fileScreenings = "screenings.txt";
    public static final String fileReservations = "reservations.txt";
    public static final String fileSeats = "seats.txt";
    public static User loggedUser;
    public static ArrayList<User> userList = new ArrayList<User>();
    public static ArrayList<Movie> movieList = new ArrayList<Movie>();
    public static ArrayList<Screening> screeningList = new ArrayList<Screening>();
    public static Scanner sc = new Scanner(System.in, "CP852");

    public static void main(String[] args) {
        
        User.readUsers();

        showInitialMenu();

        if(loggedUser != null){
            System.out.println("Sikeres bejelentkezés!");
            MovieList.readMovies();
            ScreeningList.readScreenings();
            UserList.readReservations();
            printMenu();
        }
        else{
            MyLogger.LogWriteOut("Sikertelen bejelentkezés...");
            System.out.println("Sikertelen bejelentkezés...");
            boolean logged = false;
            while(!logged){
                logged = showLogin();
            }
            System.out.println("Sikeres bejelentkezés!");
            MovieList.readMovies();
            ScreeningList.readScreenings();
            UserList.readReservations();
            printMenu();
        }
        sc.close();

    }

    public static void showInitialMenu(){
        System.out.println("#### Válasszon menüpontot ####");
        System.out.println("1. Belépés vendégként");
        System.out.println("2. Bejelentkezés");
        System.out.println("3. Regisztráció");
        System.out.print("Menüpont: ");
        int opt = consoleReadInt(true, 1, 3);
        switch(opt){
            case 1:
                loggedUser = new User(0, "Vendeg", "", "", User.Privilege.Guest);
                printMenu();
            break;
            case 2:
                showLogin();
            break;
            case 3:
                Guest.registerUser();
                showLogin();
            break;
        }
    }

    public static boolean showLogin(){
        System.out.println();
        System.out.println("#### Jelentkezzen be ####");
        System.out.print("Felhasználónév: ");
        String name = consoleRead();
        System.out.print("Jelszó: ");
        String password = consoleRead();
        return User.checkForAccess(name, password);
    }

    public static void printMenu(){
        if(loggedUser.getPrivilege() == User.Privilege.Admin){
            System.out.println();
            System.out.println("1. Filmek listázása");
            System.out.println("2. Film létrehozása");
            System.out.println("3. Film módosítása");
            System.out.println("4. Film törlése");
            System.out.println("5. Vetítés létrehozása");
            System.out.println("6. Vetítés módosítása");
            System.out.println("7. Vetítés törlése");
            System.out.println("* - Kilépés");
            System.out.print("Menüpont: ");
            try {
                int opt = consoleReadInt(true, 1, 7);
                switch(opt){
                    case 1:
                        MovieList.readMovies();
                        MovieList.printMovies();
                        printMenu();
                    break;
                    case 2:
                        Admin.addMovie();
                        printMenu();
                    break;
                    case 3:
                        Admin.editMovie();
                        printMenu();
                    break;
                    case 4:
                        Admin.deleteMovie();
                        printMenu();
                    break;
                    case 5:
                        Admin.addScreening();
                        printMenu();
                    break;
                    case 6:
                        Admin.editScreening();
                        printMenu();
                    break;
                    case 7:
                        Admin.deleteScreening();
                        printMenu();
                    break;
                }
            } catch (Exception e) {
                MyLogger.LogWriteOut("Érvénytelen menüpont megadása");
            }
        }
        else if(loggedUser.getPrivilege() == User.Privilege.Manager){
            System.out.println();
            System.out.println("1. Filmek listázása");
            System.out.println("2. Felhasználó foglalásainak megtekintése");
            System.out.println("* - Kilépés");
            System.out.print("Menüpont: ");
            try {
                int opt = consoleReadInt(true, 1, 2);
                switch(opt){
                    case 1:
                        MovieList.readMovies();
                        MovieList.printMovies();
                        printMenu();
                    break;
                    case 2:
                        System.out.print("Felhasználónév: ");
                        Manager.getReservations();
                        printMenu();
                    break;
                }
            } catch (Exception e) {
                MyLogger.LogWriteOut("Érvénytelen menüpont megadása");
            }
        }
        else if(loggedUser.getPrivilege() == User.Privilege.User){
            System.out.println();
            System.out.println("1. Filmek listázása");
            System.out.println("2. Foglalások listázása");
            System.out.println("3. Új foglalás");
            System.out.println("4. Foglalás módosítása");
            System.out.println("5. Foglalás törlése");
            System.out.println("* - Kilépés");
            System.out.print("Menüpont: ");
            try {
                int opt = consoleReadInt(true, 1, 5);
                switch(opt){
                    case 1:
                        MovieList.readMovies();
                        MovieList.printMovies();
                        printMenu();
                    break;
                    case 2:
                        User.printReservations();
                        printMenu();
                    break;
                    case 3:
                        User.addReservation();
                        printMenu();
                    break;
                    case 4:
                        User.editReservation();
                        printMenu();
                    break;
                    case 5: 
                        User.deleteReservation();
                    printMenu();
                break;
                }
            } catch (Exception e) {
                MyLogger.LogWriteOut("Érvénytelen menüpont megadása");
            }
        }
        else{
            System.out.println();
            System.out.println("1. Filmek listázása");
            System.out.println("* - Kilépés");
            System.out.print("Menüpont: ");
            try {
                int opt = consoleReadInt(true, 1, 1);
                switch(opt){
                    case 1:
                    MovieList.readMovies();
                    MovieList.printMovies();
                    printMenu();
                    break;
                }
            } catch(Exception e){
                MyLogger.LogWriteOut("Érvénytelen menüpont megadása");
            }
        }
    }

    public static int diffInYears(Date date1, Date date2){
        long timeDiff = date2.getTime() - date1.getTime();
        long yearsDiff = (timeDiff / (1000l * 60 * 60 * 24 * 365));
        return (int) yearsDiff;
    }

    public static int consoleReadInt(boolean strict, int... bounds){
        int num = -1;
        try{
            String str = sc.nextLine();
            if(!strict && str.isEmpty())
                return -1;
            if(str.equals("*")) System.exit(0);
            num = Integer.parseInt(str);
            if(bounds.length > 0 && (num < bounds[0] || num > bounds[1]))
                throw new Exception();
        }
        catch(Exception e){
            System.out.print("Próbálja meg újra: ");
            return consoleReadInt(strict, bounds);
        }
        return num;
    }

    public static String consoleRead(){
        String str = sc.nextLine();
        if(str.equals("*")) System.exit(0);
        return str;
    }

    public static String consoleReadSeat(int screeningId){
        List<String> seats = ScreeningList.getSeatsByScreeningId(screeningId);
        String str = sc.nextLine().toUpperCase();
        if(str.equals("*")) System.exit(0);
        if(str.equals("#")) return "#";
        try{
            if(str.length() != 2)
                throw new Exception();
            int c1 = Integer.parseInt(str.charAt(0) + "");
            char c2 = str.charAt(1);
            if( (c1 == 1 && !(c2 >= 'A' && c2 <= 'L')) ||
                (c1 == 2) && !(c2 >= 'A' && c2 <= 'N') ||
                (c1 >= 3 && c1 <= 5) && !(c2 >= 'A' && c2 <= 'P') ||
                (c1 >= 6 && c1 <= 9) && !(c2 >= 'A' && c2 <= 'R') ||
                (seats.contains(str)) )
                    throw new Exception();
        }
        catch(Exception e){
            System.out.print("Próbálja meg újra: ");
            return consoleReadSeat(screeningId);
        }
        return str;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String md5(String text)
    {   String hashtext = null;
        try {
            String plaintext = text;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashtext = bigInt.toString(16);
            while(hashtext.length() < 32 ){
                hashtext = "0" + hashtext;   
            }
        } catch (Exception e1){
            
        }
        return hashtext;     
    }

}