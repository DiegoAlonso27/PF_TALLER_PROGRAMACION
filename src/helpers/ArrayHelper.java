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

  public static int countMessages(String[][] messages, String string) {
    // creamos una variable para contar los mensajes
    int count = 0;
    // recorremos el array
    for (String[] m : messages) {
      // comprobamos que el id del usuario sea igual al id del mensaje y que el mensaje no sea null
      if (m[1] != null && m[1].equals(string)) {
        count++;
      }
    }
    // devolvemos el número de mensajes
    return count;
  }

  public static String[][] findMessages(String[][] messages, String idUser) {
    // metodo para buscar los mensajes de un usuario en especifico y devolverlos en un array de 2 dimensiones con los mensajes
    // creamos un array temporal
    String[][] temp = new String[0][0];
    // recorremos el array
    for (String[] m : messages) {
      // comprobamos que el id del usuario sea igual al id del mensaje
      if (m[1] != null && m[1].equals(idUser)) {
        // si el array temporal está vacío, creamos un array temporal con el tamaño del array de mensajes
        if (temp.length == 0) {
          temp = new String[messages.length][3];
        }
        // recorremos el array temporal
        for (int i = 0; i < temp.length; i++) {
          // si la posición del array temporal está vacía, copiamos el mensaje
          if (temp[i][0] == null) {
            temp[i][0] = m[0];
            temp[i][1] = m[1];
            temp[i][2] = m[2];
            break;
          }
        }
      }
    }
    // devolvemos el array temporal
    return temp;
  }

  public static int mediaMessages(String[][] messagesUser) {
    // inicializar el contador de mensajes multimedia
    int multimediaMessages = 0;
    // recorrer la lista de mensajes
    for (int i = 0; i < messagesUser.length; i++) {
      // validar que el mensaje no sea null
      if (messagesUser[i][2] == null) {
        continue;
      }
      // si el mensaje contiene "<Multimedia omitido>" o "<Media omitted>", sumar 1 al contador
      if (
        messagesUser[i][2].contains("<Multimedia omitido>") ||
        messagesUser[i][2].contains("<Media omitted>")
      ) {
        multimediaMessages++;
      }
    }
    // retornar el contador de mensajes multimedia
    return multimediaMessages;
  }

public static String[][] filterMessages(String[][] messages, String dateStart, String dateEnd) {
  // metodo para filtrar los mensajes por fecha
  // creamos un array temporal
  String[][] temp = new String[0][0];
  // recorremos el array
  for (String[] m : messages) {
    // comprobamos que la fecha del mensaje sea mayor o igual a la fecha de inicio y menor o igual a la fecha de fin
    if (m[0].compareTo(dateStart) >= 0 && m[0].compareTo(dateEnd) <= 0) {
      // si el array temporal está vacío, creamos un array temporal con el tamaño del array de mensajes
      if (temp.length == 0) {
        temp = new String[messages.length][4];
      }
      // recorremos el array temporal y vamos copiando los mensajes que cumplan con la condición
      for (int i = 0; i < temp.length; i++) {
        if (m[2] != null && temp[i][0] == null) { // si la posición del array temporal está vacía, copiamos el mensaje
          temp[i][0] = m[0];
          temp[i][1] = m[1];
          temp[i][2] = m[2];
          temp[i][3] = m[3];
          break;
        }
      }
    }
  }
  // devolvemos el array temporal
  return temp;
}
}
