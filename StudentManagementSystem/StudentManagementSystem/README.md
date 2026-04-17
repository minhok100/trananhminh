# Student Management System

This project rewrites the original code into multiple Java files so it is easier to read, explain, and maintain.

## Project structure

- `src/studentmanagement/Main.java`: console entry point and menu flow.
- `src/studentmanagement/model/Student.java`: student entity and validation.
- `src/studentmanagement/datastructure/MaxHeap.java`: custom max-heap ADT for ranking by score.
- `src/studentmanagement/service/StudentService.java`: business logic for add, edit, search, delete, and ranking.
- `src/studentmanagement/util/InputHelper.java`: safe console input helpers.

## Compile

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java .\src | ForEach-Object FullName)
```

## Run

```powershell
java -cp out studentmanagement.Main
```
