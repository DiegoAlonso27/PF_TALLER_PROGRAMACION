package helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
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

  public static void exportCSV(String[][] statistics) {
    // TODO: exportar el array statistics a un archivo CSV
    // hacemos que el usuario elija donde guardar el archivo
    String path = ProjectHelper.choosePath(
      "Seleccione la ruta donde desea guardar el archivo CSV",
      "csv"
    );
    // validamos si el usuario canceló la acción
    if (path.equals("")) {
      return;
    }
    // si el usuario no canceló la acción, guardamos el archivo
    try (
      // crear un objeto FileWriter
      FileWriter writer = new FileWriter(path + ".csv");
    ) {
      // recorrer el array statistics
      for (int i = 0; i < statistics.length; i++) { // recorremos las filas
        // validamos si es la primera fila
        if (i == 0) {
          // escribimos el valor de la celda en el archivo
          writer.write("PARTICIPANTES,INTERACCIONES,MEDIAS\n");
        } else {
          // para las demás filas ponemos los valores del array statistics respectivamente en las columnas
          // validamos que no sea null el valor de la celda
            // recorremos las columnas
            for (int j = 0; j < statistics[i+1].length; j++) {
              // verificamos si es null el valor de la celda
              if (statistics[i+1][j] != null) {
              // escribimos el valor de la celda en el archivo
              writer.write(statistics[i][j]);
              // si no es la última columna, agregamos un delimitador de celdas (una coma)
              if (j < statistics[i].length - 1) {
                writer.write(",");
              }
            }
            // agregamos un delimitador de filas (un salto de línea)
            writer.write("\n");
          }
        }
      }
      // mostramos un mensaje de éxito con JOptionPane
      ProjectHelper.showSuccessMessage("El archivo CSV se ha exportado con éxito");
    } catch (IOException ex) {
      // si ocurre un error, imprimir el mensaje de error
      System.out.println(ex.getMessage());
    }
  }
}
