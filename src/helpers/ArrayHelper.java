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

public static String[][] resize(String[][] users, int i, int c) {
    // creamos un array temporal con el tamaño que le pasamos
    String[][] temp = new String[i][c];
    // recorremos el array
    for (int j = 0; j < users.length; j++) {
      // copiamos los valores del array original al temporal
      // con la varable c podemos indicar cuantas columnas queremos copiar
      for (int k = 0; k < c; k++) {
        temp[j][k] = users[j][k];
      }
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
