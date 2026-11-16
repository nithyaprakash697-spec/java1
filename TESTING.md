# Testing Evidence

Testing was done by running the program with the files in `test_data/` and typing the inputs listed below. The real program output is saved in the `docs/` folder:
- `sample_output.txt` - a full session (view, add, search, update, statistics, delete, save)
- `validation_output.txt` - invalid input being rejected
- `invalid_file_output.txt` - wrong lines in a data file being skipped

## Test Data
| File | Purpose |
|---|---|
| `test_data/students.csv` | 5 valid students: S001 John Doe, S002 Jane Smith, S003 Bob Wilson, S004 Priya Sharma, S005 Carlos Mendez |
| `test_data/invalid_students.csv` | 1 valid line and 6 wrong lines (duplicate ID, negative age, grade 150, bad contact, missing field, text age) |

## Test Cases
| # | Feature | Input | Expected result | Result |
|---|---|---|---|---|
| 1 | Load valid file | Start with `students.csv` | `Loaded 5 student(s)` | Pass |
| 2 | Load invalid file | Start with `invalid_students.csv` | Only 1 student loaded; 6 lines skipped with reasons | Pass |
| 3 | Missing file | Start with a file name that does not exist | Message and empty list, no crash | Pass |
| 4 | View all | Menu 2 | Aligned table, total count | Pass |
| 5 | View when empty | Menu 2 with no students | `No students found!` | Pass |
| 6 | Add valid student | S006, Anna Lee, 23, 90.5, anna@email.com | `[OK] Student added successfully!` | Pass |
| 7 | Duplicate ID | Add S001 again | Rejected, message shown | Pass |
| 8 | Invalid ID | `x` (only 1 character) | Error message, asked again | Pass |
| 9 | Invalid name | Single space, `A`, or empty | Error message, asked again | Pass |
| 10 | Negative age | `-3` | `Please enter a number from 1 to 120.` | Pass |
| 11 | Age as text | `abc` | `That is not a whole number.` | Pass |
| 12 | Grade too high | `200` | Range error, asked again | Pass |
| 13 | Grade out of range (low) | `-1` | Range error, asked again | Pass |
| 14 | Invalid contact | `bad` | Error, asked again | Pass |
| 15 | Phone as contact | `+919876543210` | Accepted | Pass |
| 16 | Search by ID | `S001` | Full details of John Doe | Pass |
| 17 | Search ID, other case | `s001` | Same student found | Pass |
| 18 | Search by name | `anna` | Table with Anna Lee | Pass |
| 19 | Search not found | `zzz` | `No student found` | Pass |
| 20 | Search empty text | Press Enter | `Search text cannot be empty.` | Pass |
| 21 | Update grade | S001, option 3, `95` | `Student updated`; table shows 95.00 | Pass |
| 22 | Update unknown ID | `S999` | `Student not found.` | Pass |
| 23 | Update cancel | Option 5 | `Update cancelled.` | Pass |
| 24 | Delete confirmed | S003, `yes` | Student removed from list | Pass |
| 25 | Delete cancelled | S003, `no` | `Delete cancelled.` | Pass |
| 26 | Statistics | Menu 6 | Correct count and average (e.g. 6 students, 85.21) | Pass |
| 27 | Save and reload | Add, exit, restart | Changes still present | Pass |
| 28 | Menu choice invalid | `9` or `abc` | Error, menu asked again | Pass |

## Example: Validation Test (from `validation_output.txt`)
```
Enter Name:   [X] Name must be 2-50 characters and start with a letter.
Enter Age:   [X] Please enter a number from 1 to 120.
Enter Age:   [X] That is not a whole number. Please try again.
Enter Grade (0-100):   [X] Please enter a number from 0.0 to 100.0.
Enter Contact (email or phone):   [X] Enter a valid email or a phone number (7-15 digits).
[OK] Student added successfully!
```

## Example: Invalid File Test (from `invalid_file_output.txt`)
```
  ! Line 3 skipped: duplicate ID.
  ! Line 4 skipped: Age must be between 1 and 120.
  ! Line 5 skipped: Grade must be between 0.0 and 100.0.
  ! Line 6 skipped: Contact must be a valid email or a phone number (7-15 digits).
  ! Line 7 skipped: Expected 5 fields but found 3
  ! Line 8 skipped: Age or grade is not a number.
Loaded 1 student(s) from test_data/invalid_students.csv
```

## How to Repeat the Tests
```bash
javac -d out src/*.java
java -cp out StudentInformationSystem test_data/students.csv
java -cp out StudentInformationSystem test_data/invalid_students.csv
```
Note: the program saves on exit, so use a copy of `students.csv` if you want to keep the original sample data unchanged.
