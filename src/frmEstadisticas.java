import helpers.FileHelper;
import helpers.ProjectHelper;
import helpers.DateHelper;
import java.io.File;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import vendor.SwingUTP;

public class frmEstadisticas {
  JLabel lblInicio, lblFin, lbltexto, lbltexto2, lblestadistica, lnlFilePath;
  JTextArea jtestadistica;
  JTextField txtDesde, txtHasta;
  JComboBox<String> lstFormatos;
  JButton btnSeleccionar, btnCopiar, btnGuardar, btnGenerar, cleanDate;
  JButton btnCSV, btnTXT, btnHTML, btnXLSX, btnJSON;
  JFrame frame = new JFrame();
  JFormattedTextField txtDateInit = new JFormattedTextField();
  JFormattedTextField txtDateFInich = new JFormattedTextField();
  static JTable table = new JTable();

  public void mostrar() {
    prepararControles();
    SwingUTP.runWindow("PROYECTO FINAL", 700, 650);
  }

  public void prepararControles() {
    lblInicio = new JLabel("DESDE: ");
    lblFin = new JLabel("HASTA: ");
    lbltexto = new JLabel("dd/mm/aaaa");
    lbltexto2 = new JLabel("dd/mm/aaaa");
    lblestadistica = new JLabel("Estadisticas");
    lnlFilePath = new JLabel("Archivo:");

    txtDesde = new JTextField(18);
    txtHasta = new JTextField(18);

    btnSeleccionar = new JButton("Seleccionar archivo ...");
    btnGenerar = new JButton("GENERAR DATOS");
    btnCopiar = new JButton("COPIAR");
    btnGuardar = new JButton("GUARDAR");
    cleanDate = new JButton("Limpiar fecha");
    btnCSV = new JButton("CSV");
    btnTXT = new JButton("TXT");
    btnHTML = new JButton("HTML");
    btnXLSX = new JButton("XLSX");
    btnJSON = new JButton("JSON");
    

    jtestadistica = new JTextArea(80, 50);

    lstFormatos = new JComboBox<String>();

    // Set the size of the frame
    frame.setSize(600, 400);
    // Create a new JTable

    // Set the model of the table
    DefaultTableModel model = new DefaultTableModel(
      new String[] { "PARTICIPANTES", "INTERACCCIONES", "MEDIAS" },
      0
    );
    table.setModel(model);
    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);

    SwingUTP.addControl(10, 1, 180, 25, btnSeleccionar);
    // a lado del boton seleccionar agregamos un label con el nombre del archivo
    SwingUTP.addControl(200, 1, 400, 25, lnlFilePath);

    SwingUTP.addControl(10, 35, 150, 25, lblInicio);
    SwingUTP.addDateField(60, 35, 120, 25, txtDateInit);
    SwingUTP.addControl(190, 35, 150, 25, lblFin);
    SwingUTP.addDateField(240, 35, 120, 25, txtDateFInich);
    SwingUTP.addControl(380, 35, 150, 25, lbltexto);
    SwingUTP.addControl(490, 35, 150, 25, cleanDate);

    SwingUTP.addControl(10, 80, 100, 20, lblestadistica);

    SwingUTP.addControl(400, 80, 180, 20, btnGenerar);

    //SwingUTP.addControl(10, 135, 600, 380, jtestadistica);
    // agregamos un DataGrid para mostrar los datos
    SwingUTP.addControl(10, 120, 650, 400, scrollPane);

    /* SwingUTP.addControl(10, 530, 150, 25, btnCopiar);
    SwingUTP.addControl(400, 530, 150, 25, btnGuardar); */
    // ponemos los botones para exportar a diferentes formatos en una sola linea
    SwingUTP.addControl(10, 530, 120, 25, btnCSV);
    SwingUTP.addControl(140, 530, 120, 25, btnTXT);
    SwingUTP.addControl(270, 530, 120, 25, btnHTML);
    SwingUTP.addControl(400, 530, 120, 25, btnXLSX);
    SwingUTP.addControl(530, 530, 120, 25, btnJSON);
    

    SwingUTP.addClickEvent(btnSeleccionar, this::btnSeleccionar_Click);
    SwingUTP.addClickEvent(btnGenerar, this::btnGenerar_Click);
    SwingUTP.addClickEvent(btnCopiar, this::btnCopiar_Click);
    SwingUTP.addClickEvent(btnGuardar, this::btnGuardar_Click);
    SwingUTP.addClickEvent(btnCSV, this::btnCSV_Click);
    SwingUTP.addClickEvent(btnTXT, this::btnTXT_Click);
    SwingUTP.addClickEvent(btnHTML, this::btnHTML_Click);
    SwingUTP.addClickEvent(btnXLSX, this::btnXLSX_Click);
    SwingUTP.addClickEvent(btnJSON, this::btnJSON_Click);
    SwingUTP.addClickEvent(cleanDate, this::cleanDate_Click);
  }

  private void btnSeleccionar_Click(Object o) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(
      new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt")
    );

    int seleccion = fileChooser.showOpenDialog(null);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
      File fichero = fileChooser.getSelectedFile();
      String path = fichero.getAbsolutePath();
      // verificamos si el archivo existe
      if (!FileHelper.exists(path)) {
        // con JOptionPane mostramos un mensaje de error
        JOptionPane.showMessageDialog(
          null,
          "El archivo no existe",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
        return;
      }
      // mandamos el path al valor de filePaht
      Estadistica.filePath = path;
      // mostramos el nombre del archivo en el label
      lnlFilePath.setText("Archivo: " + path);
    }
  }

  private void btnGenerar_Click(Object o) {
    // verificamos si el archivo existe
    if (!FileHelper.exists(Estadistica.filePath)) {
      // con JOptionPane mostramos un mensaje de error
      JOptionPane.showMessageDialog(
        null,
        "El archivo no existe",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    // validamos si las fechas estan vacias o no - si no estn vacias validar si son correctas si estan vacias las dejamos vacias
    if (txtDateInit.getText().isEmpty() || txtDateFInich.getText().isEmpty()) {
      txtDateInit.setText("");
      txtDateFInich.setText("");
    } else {
      // validamos si las fechas son correctas
      if (!DateHelper.isValidDate(txtDateInit.getText())) {
        JOptionPane.showMessageDialog(
          null,
          "La fecha inicial no es correcta",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
        return;
      }
      if (!DateHelper.isValidDate(txtDateFInich.getText())) {
        JOptionPane.showMessageDialog(
          null,
          "La fecha final no es correcta",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
        return;
      }
    }
    // obtenemos los datos de la estadistica, mandamos las fechas
    Estadistica.generarEstadistica( txtDateInit.getText(), txtDateFInich.getText() );
    // mostramos los datos en el textArea
  }

  private void btnCopiar_Click(Object o) {}

  private void btnGuardar_Click(Object o) {}

  private void btnCSV_Click(Object object1) {
    // exportamos a CSV los datos de la estadistica en el array statistics
    // validamos si el array esta vacio
    if (Estadistica.statistics.length == 0) {
      JOptionPane.showMessageDialog(
        null,
        "No hay datos para exportar",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    // si hay datos llamamos al metodo exportCSV
    FileHelper.exportCSV(Estadistica.statistics);
  }

  private void btnTXT_Click(Object object1) {
    // exportamos a TXT los datos de la estadistica en el array statistics
    // validamos si el array esta vacio
    if (Estadistica.statistics.length == 0) {
      JOptionPane.showMessageDialog(
        null,
        "No hay datos para exportar",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    // si hay datos llamamos al metodo exportTXT
    FileHelper.exportTXT(Estadistica.statistics);
  }

  private void btnHTML_Click(Object object1) {
    // exportamos a HTML los datos de la estadistica en el array statistics
    // validamos si el array esta vacio
    if (Estadistica.statistics.length == 0) {
      JOptionPane.showMessageDialog(
        null,
        "No hay datos para exportar",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    // si hay datos llamamos al metodo exportHTML
    FileHelper.exportHTML(Estadistica.statistics);
  }

  private void btnXLSX_Click(Object object1) {
    // exportamos a XLSX los datos de la estadistica en el array statistics
    // validamos si el array esta vacio
    if (Estadistica.statistics.length == 0) {
      JOptionPane.showMessageDialog(
        null,
        "No hay datos para exportar",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    // si hay datos llamamos al metodo exportXLSX
    FileHelper.exportXLSX(Estadistica.statistics);
  }

  private void btnJSON_Click(Object object1) {
    // exportamos a JSON los datos de la estadistica en el array statistics
    // validamos si el array esta vacio
    if (Estadistica.statistics.length == 0) {
      JOptionPane.showMessageDialog(
        null,
        "No hay datos para exportar",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    // si hay datos llamamos al metodo exportJSON
    FileHelper.exportJSON(Estadistica.statistics);
  }

  private void cleanDate_Click(Object o) {
    txtDateInit.setText("");
    txtDateFInich.setText("");
  }
}
