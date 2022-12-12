package helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

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
    // validamos si el path ya tiene el .csv al final o no
    if (path.endsWith(".csv")) {
      path = path.substring(0, path.length() - 4);
    }
    // validamos si el usuario canceló la acción
    if (path.equals("")) {
      return;
    }
    // si el usuario no canceló la acción, guardamos el archivo
    try (
      // crear un objeto FileWriter
      FileWriter writer = new FileWriter(path + ".csv");
    ) {
      // salto de columna 
      // ponemos el encabezado PARTICIPANTES, INTERACCIONES, MEDIAS en el archivo
      writer.write("\"PARTICIPANTES\";\"INTERACCIONES\";\"MEDIAS\" \n");
      // recorremos el array statistics
      for (int i = 0; i < statistics.length; i++) {
        for (int j = 0; j < statistics[i].length; j++) {
          // validamos si es null el valor de la celda
          if (statistics[i][j] != null) {
            // escribimos el valor de la celda en el archivo
            writer.write(statistics[i][j]);
            // si no es la última columna, agregamos una coma
            if (j < 2) {
              writer.write(";");
            }
          }
        }
        writer.write("\n");
      }
      // mostramos un mensaje de éxito
      JOptionPane.showMessageDialog(
        null,
        "El archivo CSV se ha guardado con éxito",
        "Éxito",
        JOptionPane.INFORMATION_MESSAGE
      );

    } catch (IOException ex) {
      // si ocurre un error, imprimir el mensaje de error
      System.out.println(ex.getMessage());
    }
  }

  public static void exportTXT(String[][] statistics) {
    // TODO: exportar el array statistics a un archivo TXT
    // hacemos que el usuario elija donde guardar el archivo
    String path = ProjectHelper.choosePath(
      "Seleccione la ruta donde desea guardar el archivo TXT",
      "txt"
    );
    // validamos si el path ya tiene el .txt al final o no
    if (path.endsWith(".txt")) {
      path = path.substring(0, path.length() - 4);
    }
    // validamos si el usuario canceló la acción
    if (path.equals("")) {
      return;
    }
    // si el usuario no canceló la acción, guardamos el archivo
    try (
      // crear un objeto FileWriter
      FileWriter writer = new FileWriter(path + ".txt");
    ) {
      // hacemos una tabla con caracteres ASCII donde cada celda tiene un ancho de 20 caracteres y un alto de 5 caracteres para poder imprimir el array statistics
      // separador de celdas
      String separator = String.format("%62s", " ").replace(" ", "-");
      // titulo de la tabla
      writer.write(separator + "\n");
      String title = String.format("%20s", "PARTICIPANTES");
      title += String.format("%20s", "INTERACCIONES");
      title += String.format("%20s", "MEDIAS");
      // escribimos el titulo de la tabla en el archivo
      writer.write(title + "\n");
      // escribimos el separador de celdas en el archivo
      writer.write(separator + "\n");
      // recorremos el array statistics
      for (int i = 0; i < statistics.length; i++) {
        for (int j = 0; j < statistics[i].length; j++) {
          // validamos si es null el valor de la celda
          if (statistics[i][j] != null) {
            // ponemos el los valores del array statistics a la misma altura que el titulo de la tabla
            String value = String.format("%20s", statistics[i][j]);
            // escribimos el valor de la celda en el archivo
            writer.write(value);
            // si no es la última columna, agregamos un separador de celdas
            /* if (j < 2) {
              writer.write(separator);
            } */
          }
        }
        writer.write("\n");
        // si no es la última fila, agregamos un separador de celdas
        if (i < statistics.length - 1) {
          // validamos si no es la última fila
          if (statistics[i][0] != null) {
            // escribimos el separador de celdas en el archivo
            writer.write(separator + "\n");
          }
        }
      }

      // mensaje de éxito con JOptionPane
      ProjectHelper.showSuccessMessage(
        "El archivo TXT se ha exportado con éxito"
      );

    } catch (IOException ex) {
      // si ocurre un error, imprimir el mensaje de error
      System.out.println(ex.getMessage());
    }
  }

  public static void exportHTML(String[][] statistics) {
    // TODO: exportar el array statistics a un archivo a una tabla HTML
    // hacemos que el usuario elija donde guardar el archivo
    String path = ProjectHelper.choosePath(
      "Seleccione la ruta donde desea guardar el archivo HTML",
      "html"
    );
    // validamos si el path ya tiene el .html al final o no
    if (path.endsWith(".html")) {
      path = path.substring(0, path.length() - 5);
    }
    // validamos si el usuario canceló la acción
    if (path.equals("")) {
      return;
    }
    // si el usuario no canceló la acción, guardamos el archivo
    try (
      // crear un objeto FileWriter
      FileWriter writer = new FileWriter(path + ".html");
    ) {
      // escribimos el encabezado del archivo HTML
      writer.write("<!DOCTYPE html>\r \n");
      writer.write("<html>\r \n");
      writer.write("<head>\r \n");
      writer.write("<title>Estadísticas</title>\r \n");
      writer.write("</head>\r \n");
      writer.write("<body>\r \n");
      writer.write("<table>\r \n");
      // escribimos el titulo de la tabla en el archivo
      writer.write("<tr>\r \n");
      writer.write("<th>Participantes</th>\r \n");
      writer.write("<th>Interacciones</th>\r \n");
      writer.write("<th>Medias</th>\r \n");
      writer.write("</tr>\r \n");
      // recorremos el array statistics
      for (int i = 0; i < statistics.length; i++) {
        // validamos si es null el valor de la celda
        if (statistics[i][0] != null) {
          // escribimos la etiqueta de apertura de fila
          writer.write("<tr>\r \n");
          // recorremos las columnas
          for (int j = 0; j < statistics[i].length; j++) {
            // validamos si es null el valor de la celda
            if (statistics[i][j] != null) {
              // escribimos la etiqueta de apertura de celda
              writer.write("<td>");
              // escribimos el valor de la celda en el archivo
              writer.write(statistics[i][j]);
              // escribimos la etiqueta de cierre de celda
              writer.write("</td>\r \n");
            }
          }
          // escribimos la etiqueta de cierre de fila
          writer.write("</tr>\r \n");
        }
      }
      // escribimos el cierre de la tabla
      writer.write("</table>\r \n");
      // escribimos el cierre del archivo HTML
      writer.write("</body>\r \n");
      writer.write("</html>\r \n");

      //mensaje de confirmación
      JOptionPane.showMessageDialog(
        null,
        "El archivo se ha guardado correctamente",
        "Archivo guardado",
        JOptionPane.INFORMATION_MESSAGE
      );
    } catch (IOException ex) {
      // si ocurre un error, imprimir el mensaje de error
      System.out.println(ex.getMessage());
    }
  }

  public static void exportXLSX(String[][] statistics) {
    // TODO: exportar el array statistics a un archivo a una tabla excel
    // hacemos que el usuario elija donde guardar el archivo
    String path = ProjectHelper.choosePath(
      "Seleccione la ruta donde desea guardar el archivo XLSX",
      "xlsx"
    );
    // validamos si el path ya tiene el .xlsx al final o no
    if (path.endsWith(".xlsx")) {
      path = path.substring(0, path.length() - 5);
    }
    // validamos si el usuario canceló la acción
    if (path.equals("")) {
      return;
    }
    // si el usuario no canceló la acción, guardamos el archivo
    try (
      // crear un objeto FileWriter
      FileWriter writer = new FileWriter(path + ".xlsx");
    ) {
      // escribimos el encabezado del archivo
    } 
    catch (IOException ex) {
      // si ocurre un error, imprimir el mensaje de error
      System.out.println(ex.getMessage());
    }

  }

public static void exportJSON(String[][] statistics) {
  // TODO: exportar el array statistics a un archivo a una tabla JSON
  // hacemos que el usuario elija donde guardar el archivo
  String path = ProjectHelper.choosePath(
    "Seleccione la ruta donde desea guardar el archivo JSON",
    "json"
  );
  // validamos si el path ya tiene el .json al final o no
  if (path.endsWith(".json")) {
    path = path.substring(0, path.length() - 5);
  }
  // validamos si el usuario canceló la acción
  if (path.equals("")) {
    return;
  }
  // si el usuario no canceló la acción, guardamos el archivo
  try (
    // crear un objeto FileWriter
    FileWriter writer = new FileWriter(path + ".json");
  ) {
    // escribimos el encabezado del archivo
  } 
  catch (IOException ex) {
    // si ocurre un error, imprimir el mensaje de error
    System.out.println(ex.getMessage());
  }
}
}
