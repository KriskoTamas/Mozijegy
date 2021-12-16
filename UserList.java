import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class UserList {

    public static int getUsersMaxId(){
        if(Main.userList.size() == 0) return -1;
        int max = Main.userList.get(0).getId();
        for(int i = 1; i < Main.userList.size(); i++)
            if(Main.userList.get(i).getId() > max)
                max = Main.userList.get(i).getId();
        return max;
    }

    public static void readReservations(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.fileReservations), "UTF-8"));
            String row;
            ArrayList<Reservation> temp = new ArrayList<Reservation>();
            while((row = br.readLine()) != null){
                if(row.equals(""))
                    continue;
                String[] data = row.split(";");
                String[] seats = data[2].split(",");
                temp.add(new Reservation(Integer.parseInt(data[0]), Integer.parseInt(data[1]), new ArrayList<String>(Arrays.asList(seats)), ScreeningList.getScreeningById(Integer.parseInt(data[3]))));
            }
            User.setReservations(temp);
            br.close();
        } catch (FileNotFoundException e) {
            MyLogger.LogWriteOut("A fájl nem található (" + Main.fileReservations + ")");
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl olvasásakor (" + Main.fileReservations + ")");
        }
    }

    public static void writeReservations(){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.fileReservations), "UTF-8"));
            for(Reservation r : User.getReservations()){
                writer.write(r.getUserId() + ";" + r.getId() + ";");
                for(int i = 0; i < r.getSeats().size(); i++){
                    writer.write(r.getSeats().get(i));
                    if(i < r.getSeats().size() - 1)
                        writer.write(",");
                }
                writer.write(";" + r.getScreening().getId() + System.lineSeparator());
            }
            writer.close();
            System.out.println("A módosítások rögzítésre kerültek.");
        } catch (IOException e) {
            System.out.println("A módosítások rögzítése sikertelen.");
            MyLogger.LogWriteOut("Hiba a fájl írásakor (" + Main.fileReservations + ")");
        }
    }

    public static void writeUser(User user){
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(Main.fileUsers, true)));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            writer.append(user.getId() + ";" + user.getName() + ";" + user.getPassword() + ";" + dateFormat.format(user.getBirthdate()) + ";" + user.getPrivilege() + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl írásakor (" + Main.fileUsers + ")");
        }
    }

}
