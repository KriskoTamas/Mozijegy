import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyLogger {

    public static void LogWriteOut(String msg) {
        try {
            String str = "Rögzítve: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime()) + ", Üzenet: " + msg + "\n";
            Files.write(Paths.get("log.txt"), str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            
        }
    }
}