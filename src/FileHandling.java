import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class FileHandling {

    Game mane;

    // Create a file chooser that opens up in user.dir = the current working
    // directory
    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));

    /**
     * Constructor for the FileHandling class.
     *
     * @param mane The game instance to be managed.
     */
    public FileHandling(Game mane) {
        this.mane = mane;
    }

    /**
     * This method is used to save the game state to a file.
     * It opens a file chooser dialog, lets the user select a file, and writes the
     * game state to the file.
     */
    public void saveIt() {
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        chooser.setDialogTitle("Save As");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int response = chooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            // auto add .txt extension to the file name
            String fileName = chooser.getSelectedFile().getAbsolutePath() + ".txt";

            // Write matrix data to the file
            JButton[][] but = mane.getButtons();
            System.out.println("##################");
            System.out.println(but.length);
            System.out.println("##################");
            writeMatrixToFile(but, fileName);

            System.out.println("Matrix data has been written to " + fileName);
        } else {
            System.out.println("Saving canceled. Game on!");
        }
    }

    /**
     * This method is used to write the game matrix to a file.
     *
     * @param matrix   The game matrix to write to the file.
     * @param filePath The path of the file to write to.
     */
    private void writeMatrixToFile(JButton[][] matrix, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Write the size of the matrix, target value & moves to the file
            fileWriter.write(mane.getnBackup() + System.lineSeparator());
            fileWriter.write(mane.getTargetValueBackup() + System.lineSeparator());
            fileWriter.write(mane.getNumberOfMovesTillTheEndBackup() + System.lineSeparator());

            // Iterate through the matrix and write each element to the file
            for (int i = 0; i < matrix.length; i++) {
                JButton[] size = matrix[i];
                for (int j = 0; j < size.length; j++) {
                    JButton value = size[j];
                    fileWriter.write(value.getText() + " "); // Assuming space-separated values
                }
                fileWriter.write(System.lineSeparator()); // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }

    /**
     * This method is used to load a game state from a file.
     * It opens a file chooser dialog, lets the user select a file, and reads the
     * game state from the file.
     */
    public void loadIt() {
        // disable the "All files" option
        chooser.setAcceptAllFileFilterUsed(false);
        // choose between text files only
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        chooser.setDialogTitle("Select a file to open");
        // choose only files
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int response = chooser.showOpenDialog(null); // select file to open

        if (response == JFileChooser.APPROVE_OPTION) {
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("You chose to open this file: " + filePath);

            // Check if the file is empty? If so, show an error message and return
            File file = new File(filePath);
            if (file.length() == 0) {
                JOptionPane.showMessageDialog(null, "File is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int size;
            try {
                Scanner s = new Scanner(new File(filePath));

                // get the size of the matrix
                size = s.nextInt();
                if (size < 10 || size > 20) {
                    System.out.println("Invalid matrix dimensions in the file: " + filePath);
                    JOptionPane.showMessageDialog(null, "Invalid matrix dimensions!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                loadMatrixFromFile(filePath, size); // load the matrix from the file

                // close the scanner
                s.close();

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("File selection canceled.");
        }
    }

    /**
     * This method is used to load the game state from a file.
     *
     * @param filePath The path of the file to read from.
     * @param size     The size of the game matrix.
     */
    private void loadMatrixFromFile(String filePath, int size) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i = 0;

            boolean holds = true;

            loop: while ((line = reader.readLine()) != null && i < mane.getButtons().length + 3) {
                String[] values = line.split("\\s+");

                // skip the first 3 lines
                if (i < 3) {
                    i++;
                    continue;
                }

                for (int j = 0; j < size; j++) {
                    // if the value is not between 1 and 9, show an error message and return
                    if (Integer.parseInt(values[j]) < 1 || Integer.parseInt(values[j]) > 9) {
                        holds = false;
                        break loop;
                    }
                }

                i++;
            }

            if (holds) {

                Scanner sc = new Scanner(new File(filePath));
                String line2;

                int size1 = Integer.parseInt(sc.nextLine());
                int targetValue1 = Integer.parseInt(sc.nextLine());
                int moves1 = Integer.parseInt(sc.nextLine());

                Game m2 = mane;
                mane = new Game(size1, targetValue1, moves1);
                m2.dispose();

                // sc.nextLine();
                // sc.nextLine();

                for (int j = 0; j < mane.getButtons().length; j++) {

                    line2 = sc.nextLine();
                    String[] value = line2.split("\\s+");

                    for (int k = 0; k < size; k++) {
                        mane.getButtons()[j][k].setText(value[k]);
                    }

                    i++;
                }

                sc.close();

                // using a custom icon
                ImageIcon customIcon = new ImageIcon("src/Icons/ImportS.png");
                JOptionPane.showMessageDialog(null, "Game data has been loaded successfully!", "Custom Icon",
                        JOptionPane.PLAIN_MESSAGE, customIcon);

                System.out.println("Matrix data has been loaded from " + filePath);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid matrix values!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real application
        }
    }
}