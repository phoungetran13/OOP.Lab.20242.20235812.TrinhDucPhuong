// In file: GUIProject/src/hust/soict/dsai/swing/NumberGrid.java
package hust.soict.dsai.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGrid extends JFrame {
    private JButton[] btnNumbers = new JButton[10];
    private JButton btnDelete, btnReset;
    private JTextField tfDisplay;

    public NumberGrid() {
        tfDisplay = new JTextField();
        tfDisplay.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tfDisplay.setEditable(false); // Thường thì display của calculator không cho sửa trực tiếp

        JPanel panelButtons = new JPanel(new GridLayout(4, 3, 5, 5)); // 4 hàng, 3 cột, khoảng cách 5px
        addButtons(panelButtons);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(5, 5)); // Khoảng cách giữa các component
        cp.add(tfDisplay, BorderLayout.NORTH);
        cp.add(panelButtons, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Number Grid");
        setSize(250, 300); // Điều chỉnh kích thước cho phù hợp
        setLocationRelativeTo(null); // Căn giữa màn hình
        setVisible(true);
    }

    void addButtons(JPanel panelButtons) {
        ButtonListener btnListener = new ButtonListener();

        // Thêm nút số 1-9
        for (int i = 1; i <= 9; i++) {
            btnNumbers[i] = new JButton("" + i);
            btnNumbers[i].addActionListener(btnListener);
            panelButtons.add(btnNumbers[i]);
        }

        // Thêm nút DEL, 0, C
        btnDelete = new JButton("DEL");
        btnDelete.addActionListener(btnListener);
        panelButtons.add(btnDelete);

        btnNumbers[0] = new JButton("0");
        btnNumbers[0].addActionListener(btnListener);
        panelButtons.add(btnNumbers[0]);

        btnReset = new JButton("C");
        btnReset.addActionListener(btnListener);
        panelButtons.add(btnReset);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand();
            String currentText = tfDisplay.getText();

            if (button.charAt(0) >= '0' && button.charAt(0) <= '9') {
                tfDisplay.setText(currentText + button);
            } else if (button.equals("DEL")) {
                if (currentText.length() > 0) {
                    tfDisplay.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (button.equals("C")) { // Giả sử nút reset là "C"
                tfDisplay.setText("");
            }
        }
    }

    public static void main(String[] args) {
        // Chạy thử NumberGrid
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGrid();
            }
        });
    }
}
