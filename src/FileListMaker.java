import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {

    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFilename = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("I - Insert an item");
            System.out.println("V - View the list");
            System.out.println("M - Move an item");
            System.out.println("O - Open a list file");
            System.out.println("S - Save the current list");
            System.out.println("C - Clear the list");
            System.out.println("N - Create a new list and save to disk");
            System.out.println("Q - Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A" -> addItem(scanner);
                case "D" -> deleteItem(scanner);
                case "I" -> insertItem(scanner);
                case "V" -> viewList();
                case "M" -> moveItem(scanner);
                case "O" -> openList(scanner);
                case "S" -> saveList(scanner);
                case "C" -> clearList();
                case "N" -> createNewList(scanner);
                case "Q" -> running = quit(scanner);
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter an item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem(Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= list.size()) {
            System.out.println("Invalid index.");
        } else {
            list.remove(index);
            needsToBeSaved = true;
            System.out.println("Item deleted.");
        }
    }

    private static void insertItem(Scanner scanner) {
        System.out.print("Enter the index to insert at: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the item to insert: ");
        String item = scanner.nextLine();
        if (index < 0 || index > list.size()) {
            System.out.println("Invalid index.");
        } else {
            list.add(index, item);
            needsToBeSaved = true;
            System.out.println("Item inserted.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("\nCurrent List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void moveItem(Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the new index: ");
        int toIndex = Integer.parseInt(scanner.nextLine());

        if (fromIndex < 0 || fromIndex >= list.size() || toIndex < 0 || toIndex > list.size()) {
            System.out.println("Invalid indices.");
            return;
        }

        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void openList(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before opening a new file? (Y/N): ");
            String response = scanner.nextLine().toUpperCase();
            if (response.equals("Y")) {
                saveList(scanner);
            } else if (response.equals("N")) {
                // Proceed without saving
                System.out.println("Opening file without saving changes.");
            } else {
                System.out.println("Invalid response. Please enter 'Y' or 'N'.");
                return;
            }
        }

        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();

        try {
            list = FileOperations.loadFile(filename);
            currentFilename = filename;
            needsToBeSaved = false;
            System.out.println("List loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveList(Scanner scanner) {
        if (currentFilename.isEmpty()) {
            System.out.print("Enter a filename to save: ");
            currentFilename = scanner.nextLine() + ".txt";
        }

        try {
            FileOperations.saveFile(list, currentFilename);
            needsToBeSaved = false;
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }

    private static boolean quit(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before quitting? (Y/N): ");
            String response = scanner.nextLine().toUpperCase();
            if (response.equals("Y")) saveList(scanner);
        }
        System.out.println("Goodbye!");
        return false;
    }

    private static void createNewList(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before creating a new list? (Y/N): ");
            String response = scanner.nextLine().toUpperCase();
            if (response.equals("Y")) {
                saveList(scanner);
            } else if (response.equals("N")) {

                list.clear();
                currentFilename = "";
                needsToBeSaved = false;
                System.out.println("New list created.");
            } else {
                System.out.println("Invalid response. Please enter 'Y' or 'N'.");
                return;
            }
        } else {
            list.clear();
            currentFilename = "";
            System.out.println("New list created.");
        }
    }
}
