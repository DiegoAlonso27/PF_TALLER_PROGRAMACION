package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHelper {

  public static boolean isValidDate(String text) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      sdf.setLenient(false);
      sdf.parse(text);
      return true;
    } catch (ParseException ex) {
      return false;
    }
  }
}
