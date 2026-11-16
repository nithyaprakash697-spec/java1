/**
 * Student - one student record.
 * Fields are private (encapsulation). Setters check the data before saving it.
 */
public class Student {

    private String studentId;
    private String name;
    private int age;
    private double grade;
    private String contact;

    public Student(String studentId, String name, int age, double grade, String contact) {
        setStudentId(studentId);
        setName(name);
        setAge(age);
        setGrade(grade);
        setContact(contact);
    }

    // ---------- Getters ----------
    public String getStudentId() { return studentId; }
    public String getName()      { return name; }
    public int getAge()          { return age; }
    public double getGrade()     { return grade; }
    public String getContact()   { return contact; }

    // ---------- Setters (with validation) ----------
    public void setStudentId(String studentId) {
        if (!ValidationUtils.isValidStudentId(studentId)) {
            throw new IllegalArgumentException("Student ID must be 2-15 letters or digits.");
        }
        this.studentId = studentId.toUpperCase();
    }

    public void setName(String name) {
        if (!ValidationUtils.isValidName(name)) {
            throw new IllegalArgumentException("Name must be 2-50 characters and start with a letter.");
        }
        this.name = name;
    }

    public void setAge(int age) {
        if (!ValidationUtils.isValidAge(age)) {
            throw new IllegalArgumentException("Age must be between "
                    + ValidationUtils.MIN_AGE + " and " + ValidationUtils.MAX_AGE + ".");
        }
        this.age = age;
    }

    public void setGrade(double grade) {
        if (!ValidationUtils.isValidGrade(grade)) {
            throw new IllegalArgumentException("Grade must be between "
                    + ValidationUtils.MIN_GRADE + " and " + ValidationUtils.MAX_GRADE + ".");
        }
        this.grade = grade;
    }

    public void setContact(String contact) {
        if (!ValidationUtils.isValidContact(contact)) {
            throw new IllegalArgumentException("Contact must be a valid email or a phone number (7-15 digits).");
        }
        this.contact = contact;
    }

    // ---------- Display ----------
    public void displayInfo() {
        System.out.println("Student ID : " + studentId);
        System.out.println("Name       : " + name);
        System.out.println("Age        : " + age);
        System.out.printf("Grade      : %.2f%n", grade);
        System.out.println("Contact    : " + contact);
        System.out.println("-".repeat(40));
    }

    // ---------- File format helpers (used for saving and loading) ----------
    public String toCsv() {
        return studentId + "," + name + "," + age + "," + grade + "," + contact;
    }

    public static Student fromCsv(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Expected 5 fields but found " + parts.length);
        }
        try {
            return new Student(parts[0].trim(), parts[1].trim(),
                    Integer.parseInt(parts[2].trim()),
                    Double.parseDouble(parts[3].trim()),
                    parts[4].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age or grade is not a number.");
        }
    }
}
