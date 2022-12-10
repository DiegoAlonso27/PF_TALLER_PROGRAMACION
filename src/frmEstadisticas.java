

import helpers.FileHelper;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import vendor.SwingUTP;

public class frmEstadisticas {
  JLabel lblInicio, lblFin, lbltexto, lbltexto2, lblestadistica, lnlFilePath;
  JTextArea jtestadistica;
  JTextField txtDesde, txtHasta;
  JComboBox<String> lstFormatos;
  JButton btnSeleccionar, btnCopiar, btnGuardar, btnGenerar;

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

    SwingUTP.addControl(10, 1, 180, 25, btnSeleccionar);
    // a lado del boton seleccionar agregamos un label con el nombre del archivo
    SwingUTP.addControl(200, 1, 400, 25, lnlFilePath);

    SwingUTP.addControl(10, 35, 150, 25, lblInicio);
    SwingUTP.addControl(10, 70, 150, 25, lblFin);

    SwingUTP.addControl(155, 35, 200, 25, txtDesde);
    SwingUTP.addControl(155, 70, 200, 25, txtHasta);

    SwingUTP.addControl(380, 35, 150, 25, lbltexto);
    SwingUTP.addControl(380, 70, 150, 25, lbltexto2);

    SwingUTP.addControl(10, 110, 100, 20, lblestadistica);

    SwingUTP.addControl(400, 110, 180, 20, btnGenerar);

    SwingUTP.addControl(10, 135, 600, 380, jtestadistica);

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
      lnlFilePath.setText("Archivo: " + fichero.getName());
    } 
  }

  private void btnGenerar_Click(Object o) {}

  private void btnCopiar_Click(Object o) {}

  private void btnGuardar_Click(Object o) {}
}
