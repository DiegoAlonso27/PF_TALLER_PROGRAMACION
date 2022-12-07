import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*
         * Agarramos el archivo de txt que esta en assets/chat-whatsapp.txt exportado de WhatsApp y lo imprimos en consola
         */
        // path del archivo
        String path = "/src/assets/chat-whatsapp.txt";
        // unimos el path con la ubicacion del proyecto para que funcione en cualquier computadora
        path = FileHelper.joinPath(path);
        // imprimimos el path
        System.out.println(path);
        // validamos que el archivo exista
        if (!FileHelper.exists(path)) {
            System.out.println("El archivo no existe");
            return;
        }
        String text = FileHelper.readFile(path);
        // cada linea del archivo es un elemento del array
        String[] lines = text.split("\r \n");
        // imprimimos el array
        //System.out.println(Arrays.toString(lines));
        // recorremos el array y en cada elemento el string que tiene dentro cada espacio lo reemplazamos por un guion bajo
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replace(" ", "_");
        }
        // imprimimos el array
        System.out.println(Arrays.toString(lines));
        //System.out.println(text);

    }
}