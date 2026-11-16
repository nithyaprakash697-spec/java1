import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentManager - the "business logic" class.
 * It stores students in an ArrayList and handles add, find, update, delete, save and load.
 * It never talks to the user - that is the job of StudentInformationSystem.
 */
public class StudentManager {

    private final ArrayList<Student> students = new ArrayList<>();

    // ---------- Create ----------
    /** Adds a student. Returns false if the ID already exists. */
    public boolean addStudent(Student student) {
        if (findById(student.getStudentId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    // ---------- Read ----------
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);   // copy, so outside code cannot break our list
    }

    public int getCount() {
        return students.size();
    }

    /** Linear search by exact ID (not case-sensitive). Returns null if not found. */
    public Student findById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /** Finds every student whose name contains the text (not case-sensitive). */
    public List<Student> searchByName(String text) {
        List<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                results.add(s);
            }
        }
        return results;
    }

    // ---------- Delete ----------
    public boolean deleteStudent(String id) {
        Student found = findById(id);
        if (found == null) {
            return false;
        }
        students.remove(found);
        return true;
    }

    // ---------- Statistics ----------
    public double getAverageGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (Student s : students) {
            total += s.getGrade();
        }
        return total / students.size();
    }

    // ---------- File persistence ----------
    public void saveToFile(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("studentId,name,age,grade,contact");
            writer.newLine();
            for (Student s : students) {
                writer.write(s.toCsv());
                writer.newLine();
            }
        }
    }

    /**
     * Loads students from a CSV file.
     * Bad lines are skipped (and reported) so one mistake does not stop the whole load.
     * Returns the number of students loaded.
     */
    public int loadFromFile(String path) throws IOException {
        int loaded = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 && line.startsWith("studentId")) {
                    continue;                       // skip header row
                }
                if (line.trim().isEmpty()) {
                    continue;                       // skip blank lines
                }
                try {
                    if (addStudent(Student.fromCsv(line))) {
                        loaded++;
                    } else {
                        System.out.println("  ! Line " + lineNumber + " skipped: duplicate ID.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("  ! Line " + lineNumber + " skipped: " + e.getMessage());
                }
            }
        }
        return loaded;
    }
}
