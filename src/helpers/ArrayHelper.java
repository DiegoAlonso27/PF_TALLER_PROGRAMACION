package helpers;
public class ArrayHelper {

  public static boolean inArray(String[][] users, String user) {
    try {
      // verificamos que el array no esté vacío
      if (users.length > 0) {
        // recorremos el array
        for (String[] u : users) {
          // si el usuario ya está en el array, devolvemos true
          // verificamos que el usuario no sea null
            if (u[0] != null && u[0].equals(user)) {
                return true;
            }
        }
        // si el usuario no está en el array, devolvemos false
        return false;
      }
      // si el array está vacío, devolvemos false
      return false;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

public static String[][] resize(String[][] users, int i) {
    // creamos un array temporal con el tamaño que le pasamos
    String[][] temp = new String[i][2];
    // recorremos el array
    for (int j = 0; j < users.length; j++) {
      // copiamos los valores del array original al temporal
      temp[j][0] = users[j][0];
      temp[j][1] = users[j][1];
    }
    // devolvemos el array temporal
    return temp;
}

public static String findId(String[][] users, String user) {
    // recorremos el array
    for (String[] u : users) {
      // si el usuario ya está en el array, devolvemos su id
      if (u[0].equals(user)) {
        return u[1];
      }
    }
    // si el usuario no está en el array, devolvemos null
    return null;
}
}
