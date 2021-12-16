import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ScreeningList {
    
    public static Screening getScreeningById(int id){
        Screening screening = null;
        for(Screening s : Main.screeningList){
            if(s.getId() == id)
                return s;
        }
        return screening;
    }

    public static ArrayList<Screening> getScreeningsById(int id){
        ArrayList<Screening> list = new ArrayList<Screening>();
        for(Screening s : Main.screeningList){
            if(s.getMovie().getId() == id)
                list.add(s);
        }
        return list;
    }

    public static ArrayList<String> getSeatsByScreeningId(int id){
        ArrayList<Reservation> reservations = User.getReservations();
        ArrayList<String> seats = new ArrayList<String>();
        for(Reservation r : reservations){
            if(r.getScreening().getId() == id)
                seats = r.getSeats();
        }
        return seats;
    }

    public static int getScreeningIndexById(int id){
        for(int i = 0; i < Main.screeningList.size(); i++)
            if(Main.screeningList.get(i).getId() == id)
                return i;
        return -1;
    }

    public static int getScreeningsMaxId(){
        if(Main.screeningList.size() == 0) return -1;
        int max = Main.screeningList.get(0).getId();
        for(int i = 1; i < Main.screeningList.size(); i++)
            if(Main.screeningList.get(i).getId() > max)
                max = Main.screeningList.get(i).getId();
        return max;
    }

    public static void printSeats(int screeningId){
        ArrayList<String> seats = ScreeningList.getSeatsByScreeningId(screeningId);
        System.out.println();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.fileSeats), "UTF-8"));
            String row;
            while((row = br.readLine()) != null){
                for(String seat : seats){
                    if(row.indexOf(seat) != -1)
                        row = row.replace(seat, "XX");
                }
                System.out.println(row);
            }
            br.close();
        } catch (FileNotFoundException e) {
            MyLogger.LogWriteOut("A fájl nem található (" + Main.fileSeats + ")");
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl olvasásakor (" + Main.fileSeats + ")");
        }
    }

    public static void readScreenings(){
        Main.screeningList.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.fileScreenings), "UTF-8"));
            String row;
            while((row = br.readLine()) != null){
                if(row.equals(""))
                    continue;
                String[] data = row.split(";");
                Main.screeningList.add(new Screening(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], MovieList.getMovieById(Integer.parseInt(data[3]))));
            }
            br.close();
        } catch (FileNotFoundException e) {
            MyLogger.LogWriteOut("A fájl nem található (" + Main.fileScreenings + ")");
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl olvasásakor (" + Main.fileScreenings + ")");
        }
    }

    public static void writeScreenings(){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.fileScreenings), "UTF-8")); 
            for(Screening s : Main.screeningList){
                writer.write(s.getId() + ";" + s.getRoomNumber() + ";" + s.getScreeningTime() + ";" + s.getMovie().getId() + System.lineSeparator());
            }
            writer.close();
            System.out.println("A módosítások rögzítésre kerültek.");
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl írásakor (" + Main.fileScreenings + ")");
            System.out.println("A módosítások rögzítése sikertelen.");
        }
    }

}
