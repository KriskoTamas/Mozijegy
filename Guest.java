public class Guest {
    
    public static void registerUser(){
        System.out.println();
        System.out.println("#### Regisztráció ####");
        System.out.print("Felhasználónév: ");
        String name = Main.consoleRead();
        System.out.print("Jelszó: ");
        String password = Main.md5(Main.consoleRead());
        System.out.print("Születési dátum (év-hónap-nap): ");
        String date = Main.consoleRead();
        User newUser = new User(UserList.getUsersMaxId() + 1, name, password, date, User.Privilege.User);
        Main.userList.add(newUser);
        UserList.writeUser(newUser);
        System.out.println("Sikeres regisztráció.\n");
    }
    
}
