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
  public static void extraerDatos(String path) {
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
          messages = ArrayHelper.resize(messages, messages.length + 1, 4);
        }
        // guardamos el mensaje en el array messages
        messages[id][0] = String.valueOf(id + 1);
        // buscamos el id del usuario que envió el mensaje, validamos que el usuario exista
        if (ArrayHelper.findId(users, user) == null) {
          System.out.println("El usuario " + user + " no existe");
          return;
        }
        messages[id][1] = ArrayHelper.findId(users, user);
        // guardamos el usuario que envió el mensaje
        lastUser = user;
        messages[id][2] = message;
        messages[id][3] = dateFormat;
        // guardamos la fecha del mensaje
        lastDate = dateFormat;
        id++;
      } else { // si no detecta ningun usuario en la linea lo guarda como mensaje del ultimo usuario
        // si el array está lleno aumentamos su tamaño
        if (id == messages.length) {
          messages = ArrayHelper.resize(messages, messages.length + 1, 4);
        }
        // guardamos el mensaje en el array messages
        messages[id][0] = String.valueOf(id + 1);
        // buscamos el id del usuario que envió el mensaje, validamos que el usuario exista
        if (ArrayHelper.findId(users, lastUser) == null) {
          System.out.println("El usuario " + lastUser + " no existe");
          return;
        }
        messages[id][1] = ArrayHelper.findId(users, lastUser);
        // guardamos el usuario que envió el mensaje
        messages[id][2] = message;
        messages[id][3] = lastDate;
        id++;

      }
    }
  }
}
