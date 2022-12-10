package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectHelper {
  private static Pattern dateTimePattern1 = Pattern.compile(
    "\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (a\\. m\\.|p\\. m\\.)"
  );

  private static Pattern dateTimePattern2 = Pattern.compile(
    "\\d{1,2}/\\d{1,2}/\\d{2,4}, \\d{2}:\\d{2}"
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

  public static String convertDate(String date) {
    // creamos un objeto de tipo SimpleDateFormat para convertir la fecha
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    // validamos con que formato viene la fecha
    Matcher dateMatcher = dateTimePattern1.matcher(date);
    Matcher dateMatcher2 = dateTimePattern2.matcher(date);
    // si la fecha viene con el formato dateTimePattern1 la convertimos a SimpleDateFormat
    if (dateMatcher.find()) {
        // sacamos los datos de la fecha
        String day = dateMatcher.group(1);
        String month = dateMatcher.group(2);
        String year = dateMatcher.group(3);
        String hour = dateMatcher.group(4);
        String minute = dateMatcher.group(5);
        // sacamos si es a. m o p. m
        String ampm = dateMatcher.group(6);
        // si es p. m le sumamos 12 a la hora
        if (ampm.equals("p. m")) {
          hour = String.valueOf(Integer.parseInt(hour) + 12);
        }
        // creamos la fecha con el formato de SimpleDateFormat
        date = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":00";
        // convertimos la fecha a Date
        try {
          Date date1 = formatter.parse(date);
          // convertimos la fecha a String
          date = formatter.format(date1);
        } catch (ParseException e) {
          e.printStackTrace();
        }
    } else if (dateMatcher2.find()) {
        // si la fecha viene con el formato dateTimePattern2 la convertimos a SimpleDateFormat
        // sacamos los datos de la fecha
        String day = dateMatcher2.group(1);
        String month = dateMatcher2.group(2);
        String year = dateMatcher2.group(3);
        String hour = dateMatcher2.group(4);
        String minute = dateMatcher2.group(5);
        // al año le sumamos 2000
        year = String.valueOf(Integer.parseInt(year) + 2000);
        // creamos la fecha con el formato de SimpleDateFormat
        date = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":00";
        // convertimos la fecha a Date
        try {
          Date date1 = formatter.parse(date);
          // convertimos la fecha a String
          date = formatter.format(date1);
        } catch (ParseException e) {
          e.printStackTrace();
        }

    }
    return date;
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
}
