import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

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
    private JButton batchStartButton;
    private JTextField processedOutputDirectory;
    private JButton processedOutputButton;
    private JLabel processedOutputLabel;
    private JProgressBar progressBar;


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
                // ToDo: Either remove this current directory or add to other Browse buttons
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

                File tempFile = new File("data/cache.txt");
                if (tempFile.exists()) {
                    Cache cache = new Cache("data/cache.txt");

                    imageMod base = new imageMod(basePictureLocation.getText());

                    base.scaleDown(Integer.parseInt(blockSizeTextField.getText()));
                    base.scaleUp(Integer.parseInt(blockSizeTextField.getText()));

                    for (int x = 0; x < base.getScaledWidth(); x++) {
                        for (int y = 0; y < base.getScaledHeight(); y++) {
//                            System.out.println("(R,G,B): " + "(" + base.getColor(x, y).getRed() + "," + base.getColor(x, y).getGreen() + ", " + base.getColor(x, y).getBlue() + ")");
//                            System.out.println(cache.closest(base.getColor(x, y)));
                            base.addImage(cache.getDirectory() + cache.closest(base.getColor(x, y)), x, y, Integer.parseInt(blockSizeTextField.getText()));
                        }
                    }

//                base.writeImg(outputDirectory.getText());
//                outputTextPane.setText("Finished scale down");

                    //base.scaleUp(Integer.parseInt(blockSizeTextField.getText()));
                    base.writeImg(outputDirectory.getText());
                    outputTextPane.setText("Finished");
                } else {
                    outputTextPane.setText("Cache has not been created. Create cache using source image directory.");
                }
            }
        });

        batchStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                progressBar.setValue(0);
                progressBar.setStringPainted(true);
                new Thread(new Runnable() {
                    public void run() {
                        long startTime = System.nanoTime();

                        String fileExtensions[] = {"png", "jpg", "gif"};

                        StringBuffer outputData = new StringBuffer();

                        outputData.append(processedOutputDirectory.getText() + "\\");

                        File folder = new File(directoryTextField.getText() + "/");
                        File[] listOfFiles = folder.listFiles();

                        progressBar.setMaximum(listOfFiles.length);

                        for (int i = 0; i < listOfFiles.length; i++) {

                            final int val = i + 1;
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    progressBar.setValue(val);
                                }
                            });
                            if (Arrays.asList(fileExtensions).contains(getFileExtension(listOfFiles[i]))) {
                                sourceImages source = new sourceImages(listOfFiles[i].getAbsolutePath());
                                // Todo Cache blocksize
                                source.scale(Integer.parseInt(blockSizeTextField.getText()));
                                String name = "test" + i;
                                source.writeImg(processedOutputDirectory.getText(), "/test" + i);
                                outputData.append("\ntest" + i + ".png" + " ");

                                Color color = source.average();
                                outputData.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//                                int rgb[] = source.average();
//                                outputData.append(rgb[0] + " " + rgb[1] + " " + rgb[2] + "\n");
                            }
                        }
                        try {
                            //BufferedWriter bwr = new BufferedWriter(new FileWriter(processedOutputDirectory.getText() + "/cache.txt"));

                            File directory = new File("data/");
                            if (!directory.exists()) {
                                directory.mkdir();
                            }
                            File outputFile = new File("data/cache.txt");
                            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
                            BufferedWriter bwr = new BufferedWriter(fw);
//                            BufferedWriter bwr = new BufferedWriter(new FileWriter("data/cache.txt"));

                            bwr.write(outputData.toString());
                            bwr.flush();
                            bwr.close();
                        } catch (IOException e) {
                            System.out.println("Failed to write cache.");
                        }


//                        Cache cache = new Cache(processedOutputDirectory.getText() + "/cache.txt");
//                        cache.printCache();

                        long endTime = System.nanoTime();
                        outputTextPane.setText("Runtime: " + (double) (endTime - startTime) / 1_000_000_000);


                    }
                }).start();



                /* Non-threaded processing
                long startTime = System.nanoTime();


                String fileExtensions[] = {"png", "jpg", "gif"};

                File folder = new File(directoryTextField.getText() + "/");
                File[] listOfFiles = folder.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                    //System.out.println(getFileExtension(listOfFiles[i]));
                    //outputTextPane.setText("Image: " + (i + 1) + "/" + listOfFiles.length);
                    if (Arrays.asList(fileExtensions).contains(getFileExtension(listOfFiles[i]))) {
                        sourceImages source = new sourceImages(listOfFiles[i].getAbsolutePath());
                        source.scale(32);
                        source.writeImg(processedOutputDirectory.getText().toString(), "test" + i);
                        //source.writeImg(processedOutputDirectory.getText().toString(), listOfFiles[i].getName());

                    }
                }
                //outputTextPane.setText("Finished");


                long endTime = System.nanoTime();
                outputTextPane.setText("Runtime: " + (double) (endTime - startTime) / 1_000_000_000);
                */


                /* Probably won't use or change as it runs out of memory
                long startTime = System.nanoTime();

                sourceImages source = new sourceImages(directoryTextField.getText());
                outputTextPane.setText("Images loaded.");
                source.scale(32);
                outputTextPane.setText("Images scaled");
                source.writeImg(processedOutputDirectory.getText().toString(), "test");
                outputTextPane.setText("Finished");

                long endTime = System.nanoTime();
                outputTextPane.setText("Runtime: " + (double) (endTime - startTime) / 1_000_000_000);
                */


            }
        });
        processedOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    processedOutputDirectory.setText(fc.getSelectedFile().toString());
                }
            }
        });

        directoryTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (directoryTextField.getText().length() == 0 || processedOutputDirectory.getText().length() == 0) {
                    batchStartButton.setEnabled(false);
                } else {
                    batchStartButton.setEnabled(true);
                }
            }
        });

        processedOutputDirectory.getDocument().addDocumentListener(new DocumentListener() {
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
                if (directoryTextField.getText().length() == 0 || processedOutputDirectory.getText().length() == 0) {
                    batchStartButton.setEnabled(false);
                } else {
                    batchStartButton.setEnabled(true);
                }
            }
        });
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else return "";
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
        baseWindow.setLayout(new GridLayoutManager(5, 1, new Insets(15, 10, 15, 10), -1, -1));
        Directories = new JPanel();
        Directories.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        baseWindow.add(Directories, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        baseFileLabel = new JLabel();
        baseFileLabel.setText("Base Image");
        Directories.add(baseFileLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        basePictureLocation = new JTextField();
        Directories.add(basePictureLocation, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(700, 30), null, 0, false));
        baseFileButton = new JButton();
        baseFileButton.setText("Browse");
        Directories.add(baseFileButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputLabel = new JLabel();
        outputLabel.setText("Output");
        Directories.add(outputLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputDirectory = new JTextField();
        Directories.add(outputDirectory, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(700, 30), null, 0, false));
        outputButton = new JButton();
        outputButton.setText("Browse");
        Directories.add(outputButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockSizeLabel = new JLabel();
        blockSizeLabel.setText("Block Size");
        Directories.add(blockSizeLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        blockSizeTextField = new JTextField();
        blockSizeTextField.setText("16");
        Directories.add(blockSizeTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 25), new Dimension(50, -1), 0, false));
        startButton = new JButton();
        startButton.setEnabled(false);
        startButton.setHideActionText(false);
        startButton.setText("Start");
        Directories.add(startButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        Directories.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 10), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        baseWindow.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        directoryLabel = new JLabel();
        directoryLabel.setHorizontalAlignment(0);
        directoryLabel.setHorizontalTextPosition(0);
        directoryLabel.setText("<html>Source Folder<br/>(Sampled Images)</html>");
        directoryLabel.setVerticalAlignment(0);
        directoryLabel.setVerticalTextPosition(0);
        panel1.add(directoryLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        directoryTextField = new JTextField();
        directoryTextField.setEditable(true);
        directoryTextField.setToolTipText("");
        panel1.add(directoryTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(700, 30), null, 0, false));
        directoryBrowseButton = new JButton();
        directoryBrowseButton.setText("Browse");
        panel1.add(directoryBrowseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processedOutputLabel = new JLabel();
        processedOutputLabel.setText("Processed Output");
        panel1.add(processedOutputLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processedOutputDirectory = new JTextField();
        panel1.add(processedOutputDirectory, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        batchStartButton = new JButton();
        batchStartButton.setEnabled(false);
        batchStartButton.setText("Start");
        panel1.add(batchStartButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processedOutputButton = new JButton();
        processedOutputButton.setText("Browse");
        panel1.add(processedOutputButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outputTextPane = new JTextPane();
        baseWindow.add(outputTextPane, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        baseWindow.add(separator1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        progressBar = new JProgressBar();
        progressBar.setStringPainted(false);
        baseWindow.add(progressBar, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return baseWindow;
    }

}
