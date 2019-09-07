import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class mainWindow {
    private JPanel baseWindow;
    private JTextField basePictureLocation;
    private JButton baseFileButton;
    private JLabel baseFileLabel;
    private JButton directoryBrowseButton;
    private JTextField directoryTextField;
    private JLabel directoryLabel;
    private JTextField outputDirectory;
    private JButton outputButton;
    private JLabel outputLabel;
    private JPanel Directories;
    private JButton startButton;
    private JTextField blockSizeTextField;
    private JLabel blockSizeLabel;
    private JTextPane outputTextPane;


    public mainWindow() {
        baseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    basePictureLocation.setText(fc.getSelectedFile().toString());
                }
            }
        });

        directoryBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    directoryTextField.setText(fc.getSelectedFile().toString());
                }
            }
        });

        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

                //fc.addChoosableFileFilter(new FileNameExtensionFilter("Image", "png"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image (*.png)", "png");
                fc.addChoosableFileFilter(filter);
                fc.setFileFilter(filter);


                int result = fc.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    //outputDirectory.setText(fc.getSelectedFile().toString() + ".png");
                    outputDirectory.setText(fc.getSelectedFile().toString());
                }

                //startButton.setEnabled(true);
            }
        });

        basePictureLocation.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change();
            }

            public void change() {
                if (basePictureLocation.getText().length() == 0 || outputDirectory.getText().length() == 0) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
        });

        outputDirectory.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change();
            }

            public void change() {
                if (basePictureLocation.getText().length() == 0 || outputDirectory.getText().length() == 0) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageMod base = new imageMod(basePictureLocation.getText());
                base.scaleDown(Integer.parseInt(blockSizeTextField.getText()));
                base.writeImg(outputDirectory.getText());

                outputTextPane.setText("Finished scale down");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainWindow");
        frame.setResizable(false);
        frame.setContentPane(new mainWindow().baseWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        /*
        String sourceDir = "images/base/test3.png";
        String outputDir = "images/processed/";
        imageMod base = new imageMod(sourceDir);
        base.scaleDown(10);
        base.writeImg(outputDir + "output");
        base.scaleUp(10);
        //base.writeImg("output2");
        base.writeImg(outputDir + "output2");

        sourceImages source = new sourceImages(sourceDir);
        source.scale(32);
        //source.writeImg("output3");
        source.writeImg(outputDir + "output3");
        */
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        baseWindow = new JPanel();
        baseWindow.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(15, 10, 15, 10), -1, -1));
        Directories = new JPanel();
        Directories.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        baseWindow.add(Directories, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        baseFileLabel = new JLabel();
        baseFileLabel.setText("Base Image");
        Directories.add(baseFileLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        basePictureLocation = new JTextField();
        Directories.add(basePictureLocation, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(700, 30), null, 0, false));
        baseFileButton = new JButton();
        baseFileButton.setText("Browse");
        Directories.add(baseFileButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        directoryLabel = new JLabel();
        directoryLabel.setHorizontalAlignment(0);
        directoryLabel.setHorizontalTextPosition(0);
        directoryLabel.setText("<html>Source Folder<br/>(Sampled Images)</html>");
        directoryLabel.setVerticalAlignment(0);
        directoryLabel.setVerticalTextPosition(0);
        Directories.add(directoryLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        directoryTextField = new JTextField();
        directoryTextField.setEditable(true);
        directoryTextField.setToolTipText("");
        Directories.add(directoryTextField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(700, 30), null, 0, false));
        directoryBrowseButton = new JButton();
        directoryBrowseButton.setText("Browse");
        Directories.add(directoryBrowseButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputLabel = new JLabel();
        outputLabel.setText("Output");
        Directories.add(outputLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputDirectory = new JTextField();
        Directories.add(outputDirectory, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(700, 30), null, 0, false));
        outputButton = new JButton();
        outputButton.setText("Browse");
        Directories.add(outputButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        baseWindow.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setEnabled(false);
        startButton.setHideActionText(false);
        startButton.setText("Start");
        panel1.add(startButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockSizeLabel = new JLabel();
        blockSizeLabel.setText("Block Size");
        panel1.add(blockSizeLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockSizeTextField = new JTextField();
        blockSizeTextField.setText("16");
        panel1.add(blockSizeTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 25), new Dimension(50, -1), 0, false));
        outputTextPane = new JTextPane();
        baseWindow.add(outputTextPane, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return baseWindow;
    }

}
