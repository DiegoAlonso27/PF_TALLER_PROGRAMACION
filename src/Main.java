import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helpers.ArrayHelper;
import helpers.FileHelper;
import helpers.ProjectHelper;

public class Main {
  // Array bidimensional para guardar los usuarios y un id para cada uno
  private static String[][] users = new String[100][2];
  // Array para guardar los mensajes y un id para cada uno y el id del usuario que lo envió
  private static String[][] messages = new String[100][4]; // id, user_id, message, date

  public static void main(String[] args) {
    String path = "/src/assets/extracto_chat.txt";
    // vemos en que directorio estamos y lo concatenamos con el path del archivo
    path = FileHelper.joinPath(path);
    // verificamos si el archivo existe
    if (!FileHelper.exists(path)) {
      System.out.println("El archivo no existe");
      return;
    }
    String[] text = FileHelper.readFile(path);

    // Recorrer el array de texto
    int id = 0;
    for (String line : text) {
      // sacamos todos los usuarios y los guardamos en el array users y le asignamos un id
      String user = ProjectHelper.findUser(line);
      // si el usuario no está vacío lo guardamos en el array teniendo en cuenta que no se repita
      if (!user.isEmpty()) {
        // si el usuario no está en el array lo agregamos
        if (!ArrayHelper.inArray(users, user)) {
          // si el array está lleno aumentamos su tamaño
          if (id == users.length) {
            users = ArrayHelper.resize(users, users.length + 1);
          }
          // añadimos el usuario al array con su id
          users[id][0] = user;
          users[id][1] = String.valueOf(id + 1);
          id++;
        }
      }
    }
    // imprimimos la lista de usuarios con su id
    for (String[] user : users) {
      // si el usuario no es null lo imprimimos
      if (user[0] != null) {
        System.out.println(user[0] + " " + user[1]);
      }
    }
    // Recorrer el array de texto otra vez para guardar los mensajes y la fecha en el array messages, le asignamos un id a cada mensaje y el id del usuario que lo envió
    id = 0;
    for (String line : text) {
      // sacamos la fecha y el usuario de cada línea
      String date = ProjectHelper.findDateAtBeginning(line);
      // convertimos la fecha a formato estándar de java
      String dateFormat = ProjectHelper.convertDate(date);
      String user = ProjectHelper.findUser(line);
      String message = line
        .replace(date, "")
        .replace(new String(user), "")
        .replace("- :", "");
      // si la fecha no está vacía y el usuario no está vacío
      if (!date.isEmpty() && !user.isEmpty()) {
        // si el array está lleno aumentamos su tamaño
        if (id == messages.length) {
          messages = ArrayHelper.resize(messages, messages.length + 1);
        }
        // guardamos el mensaje en el array messages
        messages[id][0] = String.valueOf(id + 1);
        messages[id][1] = ArrayHelper.findId(users, user);
        messages[id][2] = message;
        messages[id][3] = dateFormat;
        id++;
      }
    }
    // imprimimos la lista de mensajes con su id, el id del usuario que lo envió, el mensaje y la fecha
    for (String[] message : messages) {
      // si el mensaje no es null lo imprimimos
      if (message[0] != null) {
        System.out.println(
          message[0] + " " + message[1] + " " + message[2] + " " + message[3]
        );
      }
    }
    // imprimir cada línea del archivo
    /* for (String line : text) {
      // guardar la fecha en una variable
      String date = findDateAtBeginning(line);
      // guardar el usuario en una variable
      String user = findUser(line);
      // todo lo que no sea fecha o usuario lo guardamos en una variable
      String message = line
        .replace(date, "")
        .replace(new String(user), "")
        .replace("- :", "");
      // si la fecha no está vacía la imprimimos
      if (!date.isEmpty()) {
        System.out.println(date + " " + new String(user) + ":");
        System.out.println(message);
      } else {
        // si la fecha esta vacia imprimimos la linea
        System.out.println("No se encontró fecha: " + line);
      }
    } */
  }
}
