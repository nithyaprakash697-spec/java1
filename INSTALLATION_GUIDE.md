# Installation Guide

## 1. Install the JDK
You need a JDK (not only a JRE), version 11 or newer.

**Windows**
1. Download a JDK (for example Eclipse Temurin or Oracle JDK 17/21).
2. Run the installer and keep the default options.
3. Open Command Prompt and type `javac -version`. You should see a version number.

**macOS**
1. Install with Homebrew: `brew install openjdk@17`, or use the installer from the Temurin website.
2. Check with `javac -version`.

**Linux (Ubuntu / Debian)**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
javac -version
```

If `javac` is "not found" after installing, add the JDK `bin` folder to your `PATH` environment variable.

## 2. Get the Project
```bash
git clone <your-repository-url>
cd student-information-system
```
Or download the ZIP from GitHub and extract it.

## 3. Compile and Run (Command Line)
```bash
javac -d out src/*.java
java -cp out StudentInformationSystem test_data/students.csv
```
- `javac -d out src/*.java` compiles all four files into the `out` folder.
- `java -cp out StudentInformationSystem` runs the program. The last part is the data file to load and save.

On Windows PowerShell, if `src/*.java` is not accepted, use:
```powershell
javac -d out (Get-ChildItem src\*.java).FullName
```

## 4. Run in an IDE
**IntelliJ IDEA**
1. File > Open > select the `student-information-system` folder.
2. Right-click the `src` folder > Mark Directory as > Sources Root.
3. Open `StudentInformationSystem.java` and click the green Run arrow.
4. To use the sample data: Run > Edit Configurations > Program arguments: `test_data/students.csv`.

**Eclipse**
1. File > New > Java Project, uncheck "Use default location", and choose the project folder.
2. Make sure `src` is the source folder.
3. Right-click `StudentInformationSystem.java` > Run As > Java Application.

**VS Code**
1. Install the "Extension Pack for Java".
2. Open the folder, open `StudentInformationSystem.java`, and click Run above `main`.

## 5. Common Problems
| Problem | Fix |
|---|---|
| `'javac' is not recognized` | JDK is not installed or not in PATH. Reinstall and restart the terminal |
| `Could not find or load main class` | Run from the project folder and use `-cp out`. Compile first |
| `class X is public, should be declared in a file named X.java` | Do not rename the files |
| Strange characters in output | Use a terminal that supports UTF-8 (the program uses only plain text symbols) |
| Data not saved | Use menu option 7 or 8. Check you have write permission in the folder |
