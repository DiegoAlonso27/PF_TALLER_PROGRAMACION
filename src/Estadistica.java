import javax.swing.table.DefaultTableModel;

import helpers.ArrayHelper;
import helpers.FileHelper;
import helpers.ProjectHelper;

public class Estadistica {
  // Array bidimensional para guardar los usuarios y un id para cada uno
  private static String[][] users = new String[100][2];
  // Array para guardar los mensajes y un id para cada uno y el id del usuario que lo envió
  private static String[][] messages = new String[10][4]; // id, user_id, message, date
  public static String lastUser;
  public static String lastDate;
  public static String filePath;

  public static void main(String[] args) {
    // llamamos al metoddo mostrar de frmEstadisticas
    // Crea una instancia de la clase Estadisticas
    frmEstadisticas estadisticas = new frmEstadisticas();
    // Llama al método mostrar de la instancia de Estadisticas
    estadisticas.mostrar();
  }

  // metodo para extraer los datos del archivo
  public static String[][] extraerDatosUser(String path) {
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
            users = ArrayHelper.resize(users, users.length + 1, 2);
          }
          // añadimos el usuario al array con su id
          users[id][0] = user;
          users[id][1] = String.valueOf(id + 1);
          id++;
        }
      }
    }

    // retornamos el array de usuarios
    return users;
  }

  // metodo para extraer los datos del archivo
  public static String[][] extraerDatosMessage(String path) {
    String[] text = FileHelper.readFile(path);
    // Recorrer el array de texto otra vez para guardar los mensajes y la fecha en el array messages, le asignamos un id a cada mensaje y el id del usuario que lo envió
    int id = 0;
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
          messages = ArrayHelper.resize(messages, messages.length + 1, 4);
        }
        // guardamos el mensaje en el array messages
        messages[id][0] = String.valueOf(id + 1);
        // buscamos el id del usuario que envió el mensaje, validamos que el usuario exista
        if (ArrayHelper.findId(users, user) == null) {
          System.out.println("El usuario " + user + " no existe");
        } else {
          messages[id][1] = ArrayHelper.findId(users, user);
          // guardamos el usuario que envió el mensaje
          lastUser = user;
          messages[id][2] = message;
          messages[id][3] = dateFormat;
          // guardamos la fecha del mensaje
          lastDate = dateFormat;
          id++;
        }
      } else { // si no detecta ningun usuario en la linea lo guarda como mensaje del ultimo usuario
        // validamos que tengamos un usario y una fecha para guardar el mensaje
        if (lastUser == null || lastDate == null) {
          System.out.println("No se puede guardar el mensaje: " + message);
          continue;
        }
        // si el array está lleno aumentamos su tamaño
        if (id == messages.length) {
          messages = ArrayHelper.resize(messages, messages.length + 1, 4);
        }
        // imprimimos el mensaje que no tiene usuario
        //System.out.println("Mensaje sin usuario: " + message);
        // guardamos el mensaje en el array messages
        messages[id][0] = String.valueOf(id + 1);
        // buscamos el id del usuario que envió el mensaje, validamos que el usuario exista
        if (ArrayHelper.findId(users, lastUser) == null) {
          System.out.println("El usuario " + lastUser + " no existe");
        } else {
          messages[id][1] = ArrayHelper.findId(users, lastUser);
          // guardamos el usuario que envió el mensaje
          messages[id][2] = message;
          messages[id][3] = lastDate;
          id++;
        }
      }
    }

    // retornamos el array de mensajes
    return messages;
  }

  // metodo para extraer las estadisticas
  public static void generarEstadistica() {
    // llamamos al metodo para extraer los datos del archivo
    String[][] users = extraerDatosUser(filePath);
    String[][] messages = extraerDatosMessage(filePath);

    DefaultTableModel model = new DefaultTableModel(new String[] { "PARTICIPANTES", "INTERACCCIONES", "MEDIAS" }, 0);
    // Recorrer el array de usuarios
    for (String[] user : users) {
      // si el usuario no está vacío
      if (user[0] != null) {
        // con el id del usuario contamos cuantos mensajes tiene
        int count = ArrayHelper.countMessages(messages, user[1]);
        // con el id del usuario sacamos solo los mensajes que ha enviado y los guardamos en el array messagesUser
        String[][] messagesUser = ArrayHelper.findMessages(messages, user[1]);
        // sacamos la media de los mensajes del usuario
        int media = ArrayHelper.mediaMessages(messagesUser);
        // mostramos el usuario en la columna PARTICIPANTES y el numero de mensajes en la columna INTERACCIONES y la media en la columna MEDIAS
        model.addRow(new Object[] { user[0], count, media });
      }

      frmEstadisticas.table.setModel(model);
    }
  }
}
