package helpers;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProjectHelper {
  private static Pattern dateTimePattern1 = Pattern.compile(
    "\\d{1,2}/\\d{1,2}/\\d{2,4} \\d{1,2}:\\d{1,2} (a\\. m\\.|p\\. m\\.)"
  );

  private static Pattern dateTimePattern2 = Pattern.compile(
    "(\\d{1,2}/\\d{1,2}/\\d{2,4}, \\d{2}:\\d{2,4} |\\d{1,2}/\\d{1,2}/\\d{2,4} \\d{2}:\\d{2,4})"
  );
  private static Pattern usPattern = Pattern.compile("- ([^:]{0,30}):");

  // Crear una función que busque una fecha en el inicio de un texto
  public static String findDateAtBeginning(String text) {
    // Crear un objeto Matcher que se ajuste al patrón en el texto dado
    Matcher dateMatcher = dateTimePattern1.matcher(text);
    Matcher dateMatcher2 = dateTimePattern2.matcher(text);
    // Intentar encontrar la primera coincidencia del patrón en el texto
    if (dateMatcher.find()) {
      // Si se encuentra una coincidencia, devolver el texto que coincide con el patrón
      return dateMatcher.group();
    } else if (dateMatcher2.find()) {
      return dateMatcher2.group();
    } else {
      // Si no se encuentra una coincidencia, devolver una cadena vacía
      return "";
    }
  }

  public static String findUser(String text) {
    // Crear un objeto Matcher que se ajuste al patrón en el texto dado
    Matcher userMatcher = usPattern.matcher(text);
    // Intentar encontrar la primera coincidencia del patrón en el texto dado
    if (userMatcher.find()) {
      // Si se encuentra una coincidencia, obtener el usuario que coincide con el patrón
      return userMatcher.group(1);
    }
    // Si no se encuentra una coincidencia, devolver un arreglo vacío
    return "";
  }

  // Función que convierte los formatos de fechas de WhatsApp a formato dd/mm/aaaa hh:mm:ss
  public static String convertDate(String dateTime) {
    // Crear un objeto Matcher que se ajuste al patrón en el texto dado
    Matcher dateMatcher = dateTimePattern1.matcher(dateTime);
    Matcher dateMatcher2 = dateTimePattern2.matcher(dateTime);
    // Intentar encontrar la primera coincidencia del patrón en el texto
    if (dateMatcher.find()) {
      try {
        // Crea un patrón a partir de la expresión regular
        Pattern pattern = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{2,4})");
        // Crea un objeto Matcher que se ajuste al patrón en el texto dado
        Matcher matcher = pattern.matcher(dateTime);
        // Intentar encontrar la primera coincidencia del patrón en el texto
        if (matcher.find()) {
          // Si se encuentra una coincidencia, obtener los grupos que coinciden con el patrón
          String day = matcher.group(1);
          String month = matcher.group(2);
          String year = matcher.group(3);

          // si el dia solo tiene un digito, agregarle un 0 a la izquierda
          if (day.length() == 1) {
            day = "0" + day;
          }

          // si el mes solo tiene un digito, agregarle un 0 a la izquierda
          if (month.length() == 1) {
            month = "0" + month;
          }

          // si el año solo tiene dos digitos, agregarle un 20 a la izquierda
          if (year.length() == 2) {
            year = "20" + year;
          }

          // guardar la fecha en formato dd/mm/aaaa
          String date = day + "/" + month + "/" + year;

          // retornar la fecha en formato dd/mm/aaaa
          return date;
        } else {
          // Si no se encuentra una coincidencia, devolver una cadena vacía
          return "";
        }
      } catch (Exception e) {
        // TODO: handle exception
        return "Error al convertir la fecha: " + e.getMessage();
      }
    } else if (dateMatcher2.find()) {
      try {
        // Crea un patrón a partir de la expresión regular
        Pattern pattern = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{2,4})");
        // Crea un objeto Matcher que se ajuste al patrón en el texto dado
        Matcher matcher = pattern.matcher(dateTime);
        // Intentar encontrar la primera coincidencia del patrón en el texto
        if (matcher.find()) {
          // Si se encuentra una coincidencia, obtener los grupos que coinciden con el patrón
          String day = matcher.group(1);
          String month = matcher.group(2);
          String year = matcher.group(3);

          // si el dia solo tiene un digito, agregarle un 0 a la izquierda
          if (day.length() == 1) {
            day = "0" + day;
          }

          // si el mes solo tiene un digito, agregarle un 0 a la izquierda
          if (month.length() == 1) {
            month = "0" + month;
          }

          // si el año tiene 2 dígitos, agregarle 2000
          if (year.length() == 2) {
            year = "20" + year;
          }

          // guardar la fecha en formato dd/mm/aaaa
          String date = day + "/" + month + "/" + year;

          // retornar la fecha en formato dd/mm/aaaa
          return date;
        } else {
          // Si no se encuentra una coincidencia, devolver una cadena vacía
          return "";
        }
      } catch (Exception e) {
        return "Error al convertir la fecha: " + e.getMessage();
      }
    } else {
      // Si no se encuentra una coincidencia, devolver una cadena vacía
      return "";
    }
  }

  public static String choosePath(String descString, String extString) {
    // Crear un objeto JFileChooser
    JFileChooser fileChooser = new JFileChooser();
    // Establecer el directorio actual
    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    // Establecer el filtro de archivos
    FileNameExtensionFilter filter = new FileNameExtensionFilter(descString, extString);
    fileChooser.setFileFilter(filter);
    // Mostrar el cuadro de diálogo para seleccionar el archivo
    int result = fileChooser.showOpenDialog(null);
    // Si el usuario selecciona un archivo
    if (result == JFileChooser.APPROVE_OPTION) {
      // Obtener el archivo seleccionado
      File selectedFile = fileChooser.getSelectedFile();
      // Obtener la ruta del archivo seleccionado
      String path = selectedFile.getAbsolutePath();
      // Retornar la ruta del archivo seleccionado
      return path;
    } else {
      // Si el usuario no selecciona un archivo, retornar una cadena vacía
      return "";
    }
  }

  public static void showSuccessMessage(String string) {
  }
}
