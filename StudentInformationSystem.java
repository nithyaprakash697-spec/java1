import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * StudentInformationSystem - main program with the menu.
 *
 * Run:  java StudentInformationSystem [path-to-data-file]
 * If no file is given, "students.csv" in the current folder is used.
 */
public class StudentInformationSystem {

    private static final StudentManager manager = new StudentManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static String dataFile = "students.csv";

    public static void main(String[] args) {
        if (args.length > 0) {
            dataFile = args[0];
        }
        loadData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = ValidationUtils.readValidInt(scanner, "Enter your choice: ", 1, 8);
            switch (choice) {
                case 1: addStudent();        break;
                case 2: viewAllStudents();   break;
                case 3: searchStudent();     break;
                case 4: updateStudent();     break;
                case 5: deleteStudent();     break;
                case 6: showStatistics();    break;
                case 7: saveData();          break;
                case 8:
                    saveData();
                    System.out.println("\nThank you for using the Student Information System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    // ---------- Menu ----------
    private static void printMenu() {
        System.out.println("\n=== STUDENT INFORMATION SYSTEM ===");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student (by ID or Name)");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Show Statistics");
        System.out.println("7. Save Data");
        System.out.println("8. Save and Exit");
    }

    // ---------- 1. Add ----------
    private static void addStudent() {
        System.out.println("\n=== ADD NEW STUDENT ===");

        String id = ValidationUtils.readValidString(scanner, "Enter Student ID (e.g. S001): ",
                ValidationUtils::isValidStudentId, "ID must be 2-15 letters or digits, no spaces.");
        if (manager.findById(id) != null) {
            System.out.println("[X] A student with ID " + id.toUpperCase() + " already exists.");
            return;
        }

        String name = ValidationUtils.readValidString(scanner, "Enter Name: ",
                ValidationUtils::isValidName, "Name must be 2-50 characters and start with a letter.");
        int age = ValidationUtils.readValidInt(scanner, "Enter Age: ",
                ValidationUtils.MIN_AGE, ValidationUtils.MAX_AGE);
        double grade = ValidationUtils.readValidDouble(scanner, "Enter Grade (0-100): ",
                ValidationUtils.MIN_GRADE, ValidationUtils.MAX_GRADE);
        String contact = ValidationUtils.readValidString(scanner, "Enter Contact (email or phone): ",
                ValidationUtils::isValidContact, "Enter a valid email or a phone number (7-15 digits).");

        manager.addStudent(new Student(id, name, age, grade, contact));
        System.out.println("[OK] Student added successfully!");
    }

    // ---------- 2. View all ----------
    private static void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        List<Student> all = manager.getAllStudents();
        if (all.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        printTable(all);
        System.out.println("Total students: " + all.size());
    }

    private static void printTable(List<Student> list) {
        System.out.printf("%-12s %-22s %-6s %-8s %-28s%n",
                "Student ID", "Name", "Age", "Grade", "Contact");
        System.out.println("-".repeat(80));
        for (Student s : list) {
            System.out.printf("%-12s %-22s %-6d %-8.2f %-28s%n",
                    s.getStudentId(), s.getName(), s.getAge(), s.getGrade(), s.getContact());
        }
    }

    // ---------- 3. Search ----------
    private static void searchStudent() {
        System.out.println("\n=== SEARCH STUDENT ===");
        System.out.print("Enter Student ID or Name: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("[X] Search text cannot be empty.");
            return;
        }

        Student byId = manager.findById(query);
        if (byId != null) {
            System.out.println("\nFound Student:");
            byId.displayInfo();
            return;
        }

        List<Student> byName = manager.searchByName(query);
        if (byName.isEmpty()) {
            System.out.println("[X] No student found for \"" + query + "\".");
        } else {
            System.out.println("\nFound " + byName.size() + " student(s):");
            printTable(byName);
        }
    }

    // ---------- 4. Update ----------
    private static void updateStudent() {
        System.out.println("\n=== UPDATE STUDENT ===");
        System.out.print("Enter Student ID to update: ");
        Student s = manager.findById(scanner.nextLine().trim());
        if (s == null) {
            System.out.println("[X] Student not found.");
            return;
        }

        System.out.println("\nCurrent details:");
        s.displayInfo();
        System.out.println("What do you want to change?");
        System.out.println("1. Name   2. Age   3. Grade   4. Contact   5. Cancel");
        int choice = ValidationUtils.readValidInt(scanner, "Enter your choice: ", 1, 5);

        switch (choice) {
            case 1:
                s.setName(ValidationUtils.readValidString(scanner, "New Name: ",
                        ValidationUtils::isValidName, "Name must be 2-50 characters and start with a letter."));
                break;
            case 2:
                s.setAge(ValidationUtils.readValidInt(scanner, "New Age: ",
                        ValidationUtils.MIN_AGE, ValidationUtils.MAX_AGE));
                break;
            case 3:
                s.setGrade(ValidationUtils.readValidDouble(scanner, "New Grade (0-100): ",
                        ValidationUtils.MIN_GRADE, ValidationUtils.MAX_GRADE));
                break;
            case 4:
                s.setContact(ValidationUtils.readValidString(scanner, "New Contact: ",
                        ValidationUtils::isValidContact, "Enter a valid email or a phone number."));
                break;
            default:
                System.out.println("Update cancelled.");
                return;
        }
        System.out.println("[OK] Student updated successfully!");
    }

    // ---------- 5. Delete ----------
    private static void deleteStudent() {
        System.out.println("\n=== DELETE STUDENT ===");
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();
        Student s = manager.findById(id);
        if (s == null) {
            System.out.println("[X] Student not found.");
            return;
        }
        s.displayInfo();
        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            manager.deleteStudent(id);
            System.out.println("[OK] Student deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ---------- 6. Statistics ----------
    private static void showStatistics() {
        System.out.println("\n=== STATISTICS ===");
        System.out.println("Total students : " + manager.getCount());
        System.out.printf("Average grade  : %.2f%n", manager.getAverageGrade());
    }

    // ---------- File handling ----------
    private static void loadData() {
        File file = new File(dataFile);
        if (!file.exists()) {
            System.out.println("No data file found (" + dataFile + "). Starting with an empty list.");
            return;
        }
        try {
            int count = manager.loadFromFile(dataFile);
            System.out.println("Loaded " + count + " student(s) from " + dataFile);
        } catch (IOException e) {
            System.out.println("[X] Could not read " + dataFile + ": " + e.getMessage());
        }
    }

    private static void saveData() {
        try {
            manager.saveToFile(dataFile);
            System.out.println("[SAVED] Data saved to " + dataFile);
        } catch (IOException e) {
            System.out.println("[X] Could not save data: " + e.getMessage());
        }
    }
}
