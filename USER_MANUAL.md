# User Manual

## Starting the Program
```bash
java -cp out StudentInformationSystem test_data/students.csv
```
On start the program loads the file (if it exists) and shows:

```
Loaded 5 student(s) from test_data/students.csv

=== STUDENT INFORMATION SYSTEM ===
1. Add New Student
2. View All Students
3. Search Student (by ID or Name)
4. Update Student
5. Delete Student
6. Show Statistics
7. Save Data
8. Save and Exit
Enter your choice:
```
Type the number of an option and press Enter. If you type something that is not a number from 1 to 8, the program tells you and asks again.

## Option 1 - Add New Student
You are asked for five details. Each answer is checked; if it is wrong you see a message and are asked again.

| Question | What to type | Example |
|---|---|---|
| Student ID | 2-15 letters/digits, unique | `S006` |
| Name | Letters, starts with a letter | `Anna Lee` |
| Age | Whole number 1-120 | `23` |
| Grade | Number 0-100 | `90.5` |
| Contact | Email or phone number | `anna@email.com` |

Result: `[OK] Student added successfully!`
If the ID already exists you will see `[X] A student with ID S006 already exists.`

## Option 2 - View All Students
Shows every student in a table and the total number.
```
Student ID   Name                   Age    Grade    Contact
--------------------------------------------------------------------------------
S001         John Doe               20     85.50    john@email.com
S002         Jane Smith             21     92.00    jane@email.com
Total students: 2
```

## Option 3 - Search Student
Type a student ID **or** part of a name.
- An exact ID shows the full details of that student.
- Otherwise the program shows every student whose name contains your text (not case-sensitive). For example, typing `jo` finds "John Doe".
- If nothing matches you see `[X] No student found for "text".`

## Option 4 - Update Student
1. Enter the ID of the student.
2. The current details are shown.
3. Choose what to change: 1 Name, 2 Age, 3 Grade, 4 Contact, 5 Cancel.
4. Type the new value (it is validated like when adding).

The Student ID cannot be changed. To change an ID, delete the student and add again.

## Option 5 - Delete Student
1. Enter the ID.
2. The details are shown and you are asked `Are you sure you want to delete this student? (yes/no)`.
3. Only `yes` deletes. Anything else cancels.

## Option 6 - Show Statistics
Shows the total number of students and the average grade.

## Option 7 - Save Data
Writes all students to the data file. Your changes are **not** stored on disk until you save or exit.

## Option 8 - Save and Exit
Saves automatically and closes the program.

## Data File Format
The data is a plain CSV (comma separated) file you can open in Excel or Notepad:
```
studentId,name,age,grade,contact
S001,John Doe,20,85.5,john@email.com
```
If you edit the file by hand, keep the 5 columns, do not use commas inside a value, and follow the validation rules. Wrong lines are skipped with a message when the program starts.

## Messages You May See
| Message | Meaning |
|---|---|
| `No data file found ... Starting with an empty list.` | First run; a file will be created when you save |
| `[X] That is not a whole number.` | You typed letters or a decimal where a whole number is needed |
| `[X] Please enter a number from 1 to 120.` | Value out of range |
| `! Line N skipped: ...` | A line in the data file was wrong and was ignored |
| `[X] Could not save data: ...` | The file cannot be written (permission or path problem) |
