import javax.swing.*; // Import Swing components
import java.awt.event.*; // Import Event classes

public class SimpleGUI {
  public static void main(String[] args) {
    // 1. Create the frame (main window)
    JFrame frame = new JFrame("My First GUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 200);

    // 2. Create a button
    JButton button = new JButton("Click Me!");

    // 3. Add action listener (functionality)
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "Hello, welcome to Java GUI!");
      }
    });

    // 4. Add the button to the frame and make it visible
    frame.getContentPane().add(button);
    frame.setVisible(true);
  }
}
