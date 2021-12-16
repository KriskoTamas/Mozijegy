import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MovieList {
    
    public static void printMovies(){
        System.out.println("\n#### Elérhető filmek ####");
        for(Movie m : Main.movieList){
            System.out.println(m.toString());
        }
    }

    public static Movie getMovieById(int id){
        Movie movie = null;
        for(Movie m : Main.movieList){
            if(m.getId() == id)
                return m;
        }
        return movie;
    }

    public static int getMovieIndexById(int id){
        for(int i = 0; i < Main.movieList.size(); i++)
            if(Main.movieList.get(i).getId() == id)
                return i;
        return -1;
    }

    public static int getMoviesMaxId(){
        if(Main.movieList.size() == 0) return -1;
        int max = Main.movieList.get(0).getId();
        for(int i = 1; i < Main.movieList.size(); i++)
            if(Main.movieList.get(i).getId() > max)
                max = Main.movieList.get(i).getId();
        return max;
    }

    public static void readMovies(){
        Main.movieList.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.fileMovies), "UTF-8"));
            String row;
            while((row = br.readLine()) != null){
                if(row.equals(""))
                    continue;
                String[] data = row.split(";");
                Main.movieList.add(new Movie(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], data[4]));
            }
            br.close();
        } catch (FileNotFoundException e) {
            MyLogger.LogWriteOut("A fájl nem található (" + Main.fileMovies + ")");
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl olvasásakor (" + Main.fileMovies + ")");
        }
    }

    public static void writeMovies(){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Main.fileMovies), "UTF-8")); 
            for(Movie m : Main.movieList){
                writer.write(m.getId() + ";" + m.getTitle() + ";" + m.getRating() + ";" + m.getSubtitle() + ";" + m.getDubbing() + System.lineSeparator());
            }
            writer.close();
            System.out.println("A módosítások rögzítésre kerültek.");
        } catch (IOException e) {
            MyLogger.LogWriteOut("Hiba a fájl írásakor (" + Main.fileMovies + ")");
            System.out.println("A módosítások rögzítése sikertelen.");
        }
    }

}
