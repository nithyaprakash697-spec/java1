# Student Information System

A menu-driven Java console application that stores, displays, searches, updates and deletes student records, with input validation and file persistence.

This is the **Advanced Version** of the project: it includes add, view, search, update and delete, full data validation, error handling, and saving to / loading from a file.

## Project Goals
- Practise Java basics: variables, data types, operators, conditionals, loops and methods.
- Use a `Student` class with private fields and validated setters (encapsulation).
- Take user input safely with the `Scanner` class.
- Store records in an `ArrayList` and keep them between runs using a CSV file.
- Show output in clean, aligned tables.

## Features
| Feature | Description |
|---|---|
| Add student | Asks for ID, name, age, grade, contact and checks each answer |
| View all | Prints every student in an aligned table |
| Search | By student ID (exact) or by name (partial, not case-sensitive) |
| Update | Change name, age, grade or contact of an existing student |
| Delete | Removes a student after a yes/no confirmation |
| Statistics | Shows total students and average grade |
| Data validation | Age 1-120, grade 0-100, valid ID, name and contact |
| Error handling | Wrong input never crashes the program; it asks again |
| Persistence | Loads on start, saves on option 7 or on exit (`students.csv`) |

## Requirements
- Java Development Kit (JDK) 11 or newer
- A terminal (Command Prompt, PowerShell, Terminal) or any IDE (IntelliJ, Eclipse, VS Code)

## Quick Start
```bash
# 1. Go into the project folder
cd student-information-system

# 2. Compile (output goes to a folder named "out")
javac -d out src/*.java

# 3. Run with the sample data
java -cp out StudentInformationSystem test_data/students.csv
```
Run without a file name to start with an empty list (data is saved to `students.csv` in the current folder):
```bash
java -cp out StudentInformationSystem
```
See [docs/INSTALLATION_GUIDE.md](docs/INSTALLATION_GUIDE.md) for detailed steps for Windows, macOS and Linux.

## Project Structure
```
student-information-system/
├── README.md                      Project overview and setup (this file)
├── .gitignore
├── src/
│   ├── Student.java               Student class (data + validation)
│   ├── StudentInformationSystem.java   Main program with the menu
│   ├── StudentManager.java        Business logic: add, find, update, delete, save, load
│   └── ValidationUtils.java       Input validation methods
├── docs/
│   ├── INSTALLATION_GUIDE.md      Step-by-step setup
│   ├── USER_MANUAL.md             How to use every menu option
│   ├── CODE_EXPLANATION.md        Architecture, data structures, algorithms
│   ├── TESTING.md                 Test cases and results
│   ├── sample_output.txt          Real program output (full session)
│   ├── validation_output.txt      Real output showing invalid input being rejected
│   ├── invalid_file_output.txt    Real output showing bad file lines being skipped
│   └── screenshots/               Add your own screenshots here
└── test_data/
    ├── students.csv               Valid sample students
    └── invalid_students.csv       Deliberately wrong records for testing
```

## Documentation
- [Installation Guide](docs/INSTALLATION_GUIDE.md)
- [User Manual](docs/USER_MANUAL.md)
- [Code Explanation](docs/CODE_EXPLANATION.md)
- [Testing Evidence](docs/TESTING.md)

## Validation Rules
| Field | Rule |
|---|---|
| Student ID | 2-15 letters or digits, no spaces, must be unique (saved in capitals) |
| Name | 2-50 characters, starts with a letter; letters, spaces, `.`, `'`, `-` allowed |
| Age | Whole number from 1 to 120 |
| Grade | Number from 0 to 100 |
| Contact | A valid email address or a phone number (7-15 digits, optional leading `+`) |

## Author
Your Name - Your Course / Batch - Date
