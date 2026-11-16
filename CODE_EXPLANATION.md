# Code Explanation

## 1. Architecture
The program is split into four classes. Each class has one clear job.

```
 StudentInformationSystem  (menu + user interaction)
        |            \
        v             v
  StudentManager    ValidationUtils  (checks every input)
        |                 ^
        v                 |
     Student  ------------+   (one record; setters call ValidationUtils)
```

| File | Role |
|---|---|
| `Student.java` | Holds the data of one student. Private fields, public getters and validated setters. Converts to and from a CSV line |
| `ValidationUtils.java` | Static helper methods: `isValidAge`, `isValidGrade`, `isValidName`, `isValidContact`, `isValidStudentId` and safe readers `readValidInt`, `readValidDouble`, `readValidString` |
| `StudentManager.java` | Keeps the `ArrayList<Student>` and does add, find, search, delete, average, save and load. It never prints menus or reads the keyboard |
| `StudentInformationSystem.java` | `main` method, the menu loop and all user messages |

Keeping the logic (`StudentManager`) apart from the screen (`StudentInformationSystem`) makes the code easier to read, test and change.

## 2. Data Structures
- **`ArrayList<Student>`** - stores all students in the order they were added. It grows automatically and gives fast access by position.
- **`Pattern` (regular expressions)** - describe the allowed shape of an ID, name, email and phone number.
- **CSV text file** - simple storage format: one student per line, five values separated by commas.

## 3. Algorithms
| Task | Method | Time |
|---|---|---|
| Find by ID | Linear search: check each student, compare IDs ignoring case | O(n) |
| Search by name | Linear scan, lower-case both texts and use `contains` | O(n) |
| Add | Check the ID is not used (linear search), then `ArrayList.add` | O(n) |
| Delete | Find the student, then `ArrayList.remove` | O(n) |
| Average grade | Add all grades, divide by the count (0 if the list is empty) | O(n) |

For a classroom-size list, linear search is fast and easy to understand. A `HashMap<String, Student>` would give faster ID lookups for very large data.

## 4. Key Programming Ideas Used
**Encapsulation** - fields in `Student` are `private`. The only way to change them is through setters, and every setter checks the value and throws an `IllegalArgumentException` for bad data. A `Student` object can therefore never hold invalid data.

**Input loops** - `ValidationUtils.readValidInt` keeps asking until the answer is valid:
```java
while (true) {
    System.out.print(prompt);
    String input = sc.nextLine().trim();
    try {
        int value = Integer.parseInt(input);
        if (value >= min && value <= max) return value;
        System.out.println("  [X] Please enter a number from " + min + " to " + max + ".");
    } catch (NumberFormatException e) {
        System.out.println("  [X] That is not a whole number. Please try again.");
    }
}
```
Every input is read with `nextLine()` and converted by hand. This avoids the well-known `nextInt()` / `nextLine()` newline bug and stops the program crashing when a user types letters.

**Exception handling**
- `NumberFormatException` is caught when text cannot become a number.
- `IllegalArgumentException` signals invalid student data (setters, `fromCsv`).
- `IOException` is caught around file reading and writing and turned into a friendly message.
- `try-with-resources` closes files automatically.

**Method references** - `ValidationUtils::isValidName` is passed to `readValidString` as the rule to apply, so one reader method works for IDs, names and contacts.

**Menu loop** - `while (running)` shows the menu, reads a choice and uses a `switch` to call the matching method. Choosing 8 sets `running = false`.

**Formatted output** - `System.out.printf("%-22s %-6d %-8.2f", ...)` pads columns so the table lines up. `%-22s` means a left-aligned text of width 22, `%-6d` a whole number, `%-8.2f` a number with 2 decimals.

## 5. How Saving and Loading Work
1. **Start**: `loadData()` checks whether the file exists, then `StudentManager.loadFromFile` reads it line by line. The header line is skipped. Each line goes through `Student.fromCsv`, which uses the same validation as the setters. A wrong or duplicate line is skipped and reported with its line number; the rest still load.
2. **Save**: `saveToFile` writes the header and then `student.toCsv()` for each student.
3. **Exit**: option 8 saves automatically.

## 6. Design Decisions
- Student IDs are stored in capital letters so `s001` and `S001` are the same student.
- The ID cannot be updated, because it identifies the record.
- Delete asks for confirmation to prevent accidents.
- The program works only with plain text symbols (`[OK]`, `[X]`) so it displays correctly on every terminal.
- Data validation lives in one place (`ValidationUtils`), so the same rule is used when typing, updating and loading from a file.

## 7. Possible Improvements
- Sort students by name or grade.
- Store data in a database (SQLite) or JSON.
- Add a graphical interface (JavaFX or Swing).
- Add unit tests with JUnit.
