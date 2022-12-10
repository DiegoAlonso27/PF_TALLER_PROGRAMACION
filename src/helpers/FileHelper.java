package helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

  public static String[] readFile(String url) {
    try (
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(url), StandardCharsets.UTF_8)
      )
    ) {
      // Inicializar una lista de líneas vacía
      java.util.List<String> lines = new java.util.ArrayList<>();

      // Leer cada línea del archivo
      String line;
      while ((line = reader.readLine()) != null) {
        // Agregar la línea leída a la lista de líneas
        lines.add(line);
      }
      // Convertir la lista de líneas a un arreglo y devolverlo
      return lines.toArray(new String[0]);
    } catch (IOException ex) {
      // Si ocurre un error, imprimir el mensaje de error y devolver null
      System.out.println(ex.getMessage());
      return null;
    }
  }

  public static boolean exists(String path) {
    return Files.exists(Paths.get(path));
  }

  public static String joinPath(String path) {
    return Paths.get("").toAbsolutePath().toString() + path;
  }
}
