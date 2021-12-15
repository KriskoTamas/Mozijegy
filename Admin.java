public class Admin {

    public static void addMovie(){
        System.out.println("\n#### Film hozzáadása ####");
        System.out.print("Film címe: ");
        String title = Main.consoleRead();
        System.out.print("Film korhatár-besorolása: ");
        int rating = Main.consoleReadInt(true);
        System.out.print("Felirat nyelve: ");
        String subtitle = Main.consoleRead();
        System.out.print("Szinkron nyelve: ");
        String dubbing = Main.consoleRead();
        Main.movieList.add(new Movie(MovieList.getMoviesMaxId() + 1, title, rating, subtitle, dubbing));
        MovieList.writeMovies();
        System.out.println("Film hozzáadva.");
    }
    
    public static void editMovie(){
        System.out.println("\n#### Film módosítása ####");
        System.out.println("(Ahol módosítani nem kíván, Enterrel menjen tovább)");
        System.out.print("Módosítani kívánt film azonosítója: ");
        int id = Main.consoleReadInt(true);
        System.out.print("Film címe: ");
        String title = Main.consoleRead();
        System.out.print("Film korhatár-besorolása: ");
        int rating = Main.consoleReadInt(false);
        System.out.print("Felirat nyelve: ");
        String subtitle = Main.consoleRead();
        System.out.print("Szinkron nyelve: ");
        String dubbing = Main.consoleRead();
        Movie original = Main.movieList.get(id);
        Main.movieList.set(MovieList.getMovieIndexById(id), new Movie(original.getId(), title.isEmpty() ? original.getTitle() : title, rating == -1 ? original.getRating() : rating, subtitle.isEmpty() ? original.getSubtitle() : subtitle, dubbing.isEmpty() ? original.getDubbing() : dubbing));
        MovieList.writeMovies();
        System.out.println("A kiválasztott film szerkesztésre került.");
    }

    public static void deleteMovie(){
        System.out.println("\n#### Film törlése ####");
        System.out.print("Törölni kívánt film azonosítója: ");
        int id = Main.consoleReadInt(true);
        Main.movieList.remove(MovieList.getMovieIndexById(id));
        MovieList.writeMovies();
        System.out.println("A kiválasztott film törlésre került.");
    }

    public static void addScreening(){
        System.out.println("\n#### Vetítés létrehozása ####");
        System.out.print("Vetítés teremszáma: ");
        int room = Main.consoleReadInt(true);
        System.out.print("Vetítés ideje: ");
        String screeningTime = Main.consoleRead();
        MovieList.readMovies();
        MovieList.printMovies();
        MovieList.writeMovies();
        System.out.print("Válassza ki a filmet: ");
        int id = Main.consoleReadInt(true);
        Main.screeningList.add(new Screening(ScreeningList.getScreeningsMaxId() + 1, room, screeningTime, MovieList.getMovieById(id)));
        ScreeningList.writeScreenings();
        System.out.println("Vetítés hozzáadva.");
    }

    public static void editScreening(){
        System.out.println("\n#### Vetítés módosítása ####");
        System.out.println("(Ahol módosítani nem kíván, Enterrel menjen tovább)");
        System.out.print("Módosítani kívánt vetítés azonosítója: ");
        int id = Main.consoleReadInt(true);
        Screening original = ScreeningList.getScreeningById(id);
        System.out.print("Vetítés teremszáma: ");
        int room = Main.consoleReadInt(false);
        System.out.print("Vetítés ideje: ");
        String screeningTime = Main.consoleRead();
        MovieList.readMovies();
        MovieList.printMovies();
        MovieList.writeMovies();
        System.out.print("Válassza ki a filmet: ");
        int movieId = Main.consoleReadInt(false);
        Main.screeningList.set(ScreeningList.getScreeningIndexById(id), new Screening(original.getId(), room == -1 ? original.getRoomNumber() : room, screeningTime.isEmpty() ? original.getScreeningTime() : screeningTime, movieId == -1 ? original.getMovie() : MovieList.getMovieById(movieId)));
        ScreeningList.writeScreenings();
        System.out.println("A kiválasztott vetítés szerkesztésre került.");
    }

    public static void deleteScreening(){
        System.out.println("\n#### Vetítés törlése ####");
        System.out.print("Törölni kívánt vetítés azonosítója: ");
        int id = Main.consoleReadInt(true);
        Main.screeningList.remove(ScreeningList.getScreeningById(id));
        ScreeningList.writeScreenings();
        System.out.println("A kiválasztott vetítés törlésre került.");
    }

}
