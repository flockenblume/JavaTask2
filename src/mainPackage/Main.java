package mainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class MainFrame extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 320;

    private final JTextField textFieldX;
    private final JTextField textFieldY;
    private final JTextField textFieldZ;
    private final JTextField textFieldResult;

    private final ButtonGroup radioButtonsFormula = new ButtonGroup();

    private final Box hboxFormulaType = Box.createHorizontalBox();
    private final Box hboxMemoryType = Box.createHorizontalBox();

    private int formulaId = 1;
    private int activeMemoryId = 1;

    private Double mem1 = 0.0;
    private Double mem2 = 0.0;
    private Double mem3 = 0.0;

    public Double calculate1(Double x, Double y, Double z) throws IllegalArgumentException {
        if (z <= 0)
            throw new IllegalArgumentException("Область определения Формулы 1: z > 0.");
        if ((y * y + Math.pow(Math.E, Math.cos(x)) + Math.sin(y)) == 0)
            throw new IllegalArgumentException("Область определения Формулы 1: знаменатель не должен быть равен нулю.");

        return (Math.pow(Math.log(z) + Math.sin(Math.PI * z * z), 1.0 / 4)) /
                (Math.pow(y * y + Math.pow(Math.E, Math.cos(x)) + Math.sin(y), Math.sin(x)));
    }

    public Double calculate2(Double x, Double y, Double z) throws IllegalArgumentException {
        if (y <= 0)
            throw new IllegalArgumentException("Область определения Формулы 2: y > 0.");
        if ((1 + Math.pow(y, 3)) <= 0)
            throw new IllegalArgumentException("Область определения Формулы 2: 1 + y^3 > 0.");

        return Math.sqrt(y) * (3 * Math.pow(z, x) / Math.sqrt(1 + Math.pow(y, 3)));
    }

    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(_ -> MainFrame.this.formulaId = formulaId);
        radioButtonsFormula.add(button);
        hboxFormulaType.add(button);
    }

    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();

        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtonsFormula.setSelected(
                radioButtonsFormula.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(30));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalStrut(30));
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());

        JLabel labelForResult = new JLabel("Результат:");
        textFieldResult = new JTextField("0", 10);
        textFieldResult.setMaximumSize(textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelForResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        ButtonGroup radioButtonsMemory = new ButtonGroup();
        hboxMemoryType.add(Box.createHorizontalGlue());
        addMemoryRadioButton("Переменная 1", 1, radioButtonsMemory);
        addMemoryRadioButton("Переменная 2", 2, radioButtonsMemory);
        addMemoryRadioButton("Переменная 3", 3, radioButtonsMemory);
        radioButtonsMemory.setSelected(
                radioButtonsMemory.getElements().nextElement().getModel(), true);
        hboxMemoryType.add(Box.createHorizontalGlue());
        hboxMemoryType.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

        JButton buttonCalc = getjButton();
        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(_ -> {
            textFieldX.setText("0");
            textFieldY.setText("0");
            textFieldZ.setText("0");
            textFieldResult.setText("0");
        });
        JButton buttonMC = getMemoryClearButton();
        JButton buttonMPlus = getMemoryAddButton();

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(10));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalStrut(10));
        hboxButtons.add(buttonMC);
        hboxButtons.add(Box.createHorizontalStrut(10));
        hboxButtons.add(buttonMPlus);
        hboxButtons.add(Box.createHorizontalStrut(10));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(hboxMemoryType);
        contentBox.add(hboxButtons);
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    private JButton getjButton() {
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(_ -> {
            try {
                Double x = Double.parseDouble(textFieldX.getText());
                Double y = Double.parseDouble(textFieldY.getText());
                Double z = Double.parseDouble(textFieldZ.getText());
                Double result;
                if (formulaId == 1)
                    result = calculate1(x, y, z);
                else
                    result = calculate2(x, y, z);

                textFieldResult.setText(result.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                        JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        ex.getMessage(), "Ошибка области определения",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        return buttonCalc;
    }

    private void addMemoryRadioButton(String buttonName, final int memoryId, ButtonGroup group) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(_ -> MainFrame.this.activeMemoryId = memoryId);
        group.add(button);
        hboxMemoryType.add(button);
    }

    private JButton getMemoryClearButton() {
        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(_ -> {
            if (activeMemoryId == 1) mem1 = 0.0;
            else if (activeMemoryId == 2) mem2 = 0.0;
            else mem3 = 0.0;
            textFieldResult.setText("0");
        });
        return buttonMC;
    }

    private JButton getMemoryAddButton() {
        JButton buttonMPlus = new JButton("M+");
        buttonMPlus.addActionListener(_ -> {
            try {
                double result = Double.parseDouble(textFieldResult.getText());
                if (activeMemoryId == 1) mem1 += result;
                else if (activeMemoryId == 2) mem2 += result;
                else mem3 += result;
                textFieldResult.setText(activeMemoryId == 1 ? mem1.toString() :
                        activeMemoryId == 2 ? mem2.toString() : mem3.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате числа", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        return buttonMPlus;
    }

    public static void main() {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}