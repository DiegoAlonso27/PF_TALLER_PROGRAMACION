import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import vendor.SwingUTP;

public class FormWhats {

  public static void main(String[] args) {
    JButton button = new JButton("Click me!");
    SwingUTP.addControl(button, BorderLayout.SOUTH);

    SwingUTP.addClickEvent(button, (e) -> {
        JOptionPane.showMessageDialog(null, "You clicked the button!");
    });

    SwingUTP.makeWindow("My Window", 400, 300, false);
  }
}
