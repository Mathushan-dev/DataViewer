import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(new JFrame(), "Please select a file to load.", "Click OK to proceed", JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(new Model());
            }
        });
    }
}
