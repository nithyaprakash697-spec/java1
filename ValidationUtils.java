import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * ValidationUtils - helper methods that check user input.
 * Every check lives here so the rest of the program stays clean.
 */
public final class ValidationUtils {

    public static final int MIN_AGE = 1;
    public static final int MAX_AGE = 120;
    public static final double MIN_GRADE = 0.0;
    public static final double MAX_GRADE = 100.0;

    // Student ID: 2 to 15 letters or digits (example: S001)
    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9]{2,15}$");
    // Name: starts with a letter, then letters, spaces, dots, apostrophes or hyphens
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z .'-]{1,49}$");
    // Contact: an email address or a phone number
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    private ValidationUtils() { }   // no objects needed, only static methods

    // ---------- Simple checks ----------

    public static boolean isValidAge(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    public static boolean isValidGrade(double grade) {
        return grade >= MIN_GRADE && grade <= MAX_GRADE;
    }

    public static boolean isValidStudentId(String id) {
        return id != null && ID_PATTERN.matcher(id).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidContact(String contact) {
        return contact != null
                && (EMAIL_PATTERN.matcher(contact).matches()
                    || PHONE_PATTERN.matcher(contact).matches());
    }

    // ---------- Safe input readers (they keep asking until the answer is valid) ----------

    public static String readValidString(Scanner sc, String prompt,
                                         Predicate<String> rule, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (rule.test(input)) {
                return input;
            }
            System.out.println("  [X] " + errorMessage);
        }
    }

    public static int readValidInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("  [X] Please enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [X] That is not a whole number. Please try again.");
            }
        }
    }

    public static double readValidDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("  [X] Please enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [X] That is not a valid number. Please try again.");
            }
        }
    }
}
