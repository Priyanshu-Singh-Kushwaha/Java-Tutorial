import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUICalculator {
    private JFrame frame;
    private JTextField displayField;
    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";
    private boolean isNewCalculation = true;

    public GUICalculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 350);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        // Create number buttons
        for (int i = 0; i <= 9; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.addActionListener(new NumberListener());
            panel.add(numberButton);
        }

        // Create operator buttons
        JButton addButton = new JButton("+");
        addButton.addActionListener(new OperatorListener("+"));
        panel.add(addButton);

        JButton subtractButton = new JButton("-");
        subtractButton.addActionListener(new OperatorListener("-"));
        panel.add(subtractButton);

        JButton multiplyButton = new JButton("*");
        multiplyButton.addActionListener(new OperatorListener("*"));
        panel.add(multiplyButton);

        JButton divideButton = new JButton("/");
        divideButton.addActionListener(new OperatorListener("/"));
        panel.add(divideButton);

        // Create clear, decimal, and equal buttons
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(e -> {
            currentInput = "";
            operator = "";
            firstOperand = 0;
            displayField.setText("");
            isNewCalculation = true; // reset calculation state
        });
        panel.add(clearButton);

        JButton decimalButton = new JButton(".");
        decimalButton.addActionListener(new NumberListener());
        panel.add(decimalButton);

        JButton equalButton = new JButton("=");
        equalButton.addActionListener(e -> {
            try {
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double result = evaluateExpression();
                    displayField.setText(String.valueOf(result));
                    firstOperand = result; // store the result for future operations
                    currentInput = ""; // reset current input after calculation
                    operator = ""; // reset operator after calculation
                    isNewCalculation = true;
                }
            } catch (ArithmeticException ex) {
                displayField.setText("Error: Division by zero");
            } catch (NumberFormatException ex) {
                displayField.setText("Error: Invalid input");
            }
        });
        panel.add(equalButton);

        // Create a text field for displaying the input and result
        displayField = new JTextField();
        displayField.setEditable(false);
        panel.add(displayField);

        frame.add(panel);
        frame.setVisible(true);
    }

    private double evaluateExpression() {
        double secondOperand = Double.parseDouble(currentInput);

        // Performing the calculation based on the stored operator
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                if (secondOperand == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return firstOperand / secondOperand;
            default:
                return 0;
        }
    }

    private class NumberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isNewCalculation) {
                currentInput = "";  // reset current input for new calculation
                isNewCalculation = false;
            }

            JButton button = (JButton) e.getSource();
            currentInput += button.getText();
            displayField.setText(currentInput);
        }
    }

    private class OperatorListener implements ActionListener {
        private String operator;

        public OperatorListener(String operator) {
            this.operator = operator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!currentInput.isEmpty()) {
                // If there is an existing input, store the first operand and operator
                if (firstOperand == 0 && operator.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);  // store first operand
                } else {
                    // Perform calculation if operator clicked again
                    try {
                        double result = evaluateExpression();
                        displayField.setText(String.valueOf(result));
                        firstOperand = result;  // store result for next calculation
                    } catch (Exception ex) {
                        displayField.setText("Error");
                    }
                }
                currentInput = "";  // reset current input for next number
                GUICalculator.this.operator = this.operator;  // store the operator for use in calculation
            }
        }
    }

    public static void main(String[] args) {
        new GUICalculator();
    }
}
