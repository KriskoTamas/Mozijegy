import java.util.ArrayList;

public class Manager {
    
    public static void getReservations(){
        String name = Main.consoleRead();
        int id = -1;
        for(User u : Main.userList){
            if(name.equals(u.getName()))
                id = u.getId();
        }
        // System.out.println("id: " + id);
        if(id == -1){
            System.out.println("A megadott felhasználó nem létezik.");
        }
        else{
            UserList.readReservations();
            ArrayList<Reservation> reservations = User.getReservations();
            // System.out.println("size: " + reservations.size());
            System.out.println("Felhasználó foglalásai: ");
            for(Reservation r : reservations){
                if(r.getUserId() == id){
                    System.out.println(r.toString());
                }
            }
        }
    }

}
