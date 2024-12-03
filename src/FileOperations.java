import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class FileOperations {


    public static ArrayList<String> loadFile(String filename) throws IOException {
        Path filePath = Path.of(filename);


        System.out.println("Loading file from: " + filePath.toAbsolutePath());

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found at: " + filePath.toAbsolutePath());
        }

        return new ArrayList<>(Files.readAllLines(filePath));
    }

    // Save a list to the file
    public static void saveFile(ArrayList<String> list, String filename) throws IOException {
        Path filePath = Path.of(filename);


        System.out.println("Saving file to: " + filePath.toAbsolutePath());


        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }


        Files.write(filePath, list);
    }
}
