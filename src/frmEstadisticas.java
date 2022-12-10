

import helpers.FileHelper;
import helpers.ProjectHelper;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import vendor.SwingUTP;

public class frmEstadisticas {
  JLabel lblInicio, lblFin, lbltexto, lbltexto2, lblestadistica, lnlFilePath;
  JTextArea jtestadistica;
  JTextField txtDesde, txtHasta;
  JComboBox<String> lstFormatos;
  JButton btnSeleccionar, btnCopiar, btnGuardar, btnGenerar;
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

    jtestadistica = new JTextArea(80, 50);

    lstFormatos = new JComboBox<String>();

    // Set the size of the frame
    frame.setSize(600, 400);
    // Create a new JTable
    
    // Set the model of the table
    DefaultTableModel model = new DefaultTableModel(new String[] { "PARTICIPANTES", "INTERACCCIONES", "MEDIAS" }, 0);
    table.setModel(model);
    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);

    SwingUTP.addControl(10, 1, 180, 25, btnSeleccionar);
    // a lado del boton seleccionar agregamos un label con el nombre del archivo
    SwingUTP.addControl(200, 1, 400, 25, lnlFilePath);

    SwingUTP.addControl(10, 40, 150, 25, lblInicio);
    SwingUTP.addDateField(80, 40, 150, 25, txtDateInit);
    SwingUTP.addControl(250, 40, 150, 25, lblFin);
    SwingUTP.addDateField(320, 40, 150, 25, txtDateFInich);
    SwingUTP.addControl(490, 40, 150, 25, lbltexto);
    
    

    SwingUTP.addControl(10, 80, 100, 20, lblestadistica);

    SwingUTP.addControl(400, 80, 180, 20, btnGenerar);

    //SwingUTP.addControl(10, 135, 600, 380, jtestadistica);
    // agregamos un DataGrid para mostrar los datos
    SwingUTP.addControl(10, 120, 600, 400, scrollPane);
    

    SwingUTP.addControl(10, 530, 150, 25, btnCopiar);
    SwingUTP.addControl(400, 530, 150, 25, btnGuardar);

    SwingUTP.addClickEvent(btnSeleccionar, this::btnSeleccionar_Click);
    SwingUTP.addClickEvent(btnGenerar, this::btnGenerar_Click);
    SwingUTP.addClickEvent(btnCopiar, this::btnCopiar_Click);
    SwingUTP.addClickEvent(btnGuardar, this::btnGuardar_Click);
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
    // obtenemos los datos de la estadistica
    Estadistica.generarEstadistica();
    // mostramos los datos en el textArea
  }

  private void btnCopiar_Click(Object o) {}

  private void btnGuardar_Click(Object o) {}
}
