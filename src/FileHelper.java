import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static String readFile(String path) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return text;

    }

    public static boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    public static String joinPath(String path) {
        return Paths.get("").toAbsolutePath().toString() + path;
    }

}
