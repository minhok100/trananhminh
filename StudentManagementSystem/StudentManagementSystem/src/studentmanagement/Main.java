package studentmanagement;

import java.util.Scanner;

import studentmanagement.model.Student;
import studentmanagement.service.StudentService;
import studentmanagement.util.InputHelper;

// Main coordinates the console menu and delegates logic to the service layer.
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            printHeader();

            int maxStudents = InputHelper.readIntInRange(
                    scanner,
                    "Enter the maximum number of students to manage: ",
                    1,
                    1000
            );

            StudentService studentService = new StudentService(maxStudents);
            boolean running = true;

            // The loop keeps the program interactive until the user chooses to exit.
            while (running) {
                printMenu();
                int choice = InputHelper.readIntInRange(scanner, "Choose an option (1-6): ", 1, 6);

                switch (choice) {
                    case 1:
                        handleAddStudent(scanner, studentService);
                        break;
                    case 2:
                        handleShowRanking(studentService);
                        break;
                    case 3:
                        handleSearchStudent(scanner, studentService);
                        break;
                    case 4:
                        handleEditStudent(scanner, studentService);
                        break;
                    case 5:
                        handleDeleteStudent(scanner, studentService);
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }

            System.out.println("Program closed.");
        }
    }

    private static void printHeader() {
        System.out.println("==============================================");
        System.out.println("   STUDENT MANAGEMENT AND RANKING SYSTEM");
        System.out.println("==============================================");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add student");
        System.out.println("2. Show ranking table");
        System.out.println("3. Search student by ID");
        System.out.println("4. Edit student");
        System.out.println("5. Delete student");
        System.out.println("6. Exit");
    }

    private static void handleAddStudent(Scanner scanner, StudentService studentService) {
        try {
            String id = InputHelper.readRequiredText(scanner, "Enter student ID: ");
            String name = InputHelper.readRequiredText(scanner, "Enter student name: ");
            double score = InputHelper.readDoubleInRange(scanner, "Enter score (0-10): ", 0, 10);

            studentService.addStudent(id, name, score);
            System.out.println("Student added successfully.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void handleShowRanking(StudentService studentService) {
        if (studentService.isEmpty()) {
            System.out.println("No students have been added yet.");
            return;
        }

        Student[] ranking = studentService.getRanking();

        System.out.println();
        System.out.println("RANKING TABLE");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-6s %-10s %-25s %-8s%n", "Rank", "ID", "Name", "Score");
        System.out.println("--------------------------------------------------------");

        for (int i = 0; i < ranking.length; i++) {
            Student student = ranking[i];
            System.out.printf(
                    "%-6d %-10s %-25s %-8.2f%n",
                    i + 1,
                    student.getId(),
                    student.getName(),
                    student.getScore()
            );
        }
    }

    private static void handleSearchStudent(Scanner scanner, StudentService studentService) {
        String id = InputHelper.readRequiredText(scanner, "Enter student ID to search: ");
        Student student = studentService.findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student found:");
        System.out.println(student);
    }

    private static void handleEditStudent(Scanner scanner, StudentService studentService) {
        try {
            String id = InputHelper.readRequiredText(scanner, "Enter student ID to edit: ");
            String newName = InputHelper.readRequiredText(scanner, "Enter new name: ");
            double newScore = InputHelper.readDoubleInRange(scanner, "Enter new score (0-10): ", 0, 10);

            studentService.updateStudent(id, newName, newScore);
            System.out.println("Student updated successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void handleDeleteStudent(Scanner scanner, StudentService studentService) {
        try {
            String id = InputHelper.readRequiredText(scanner, "Enter student ID to delete: ");
            studentService.deleteStudent(id);
            System.out.println("Student deleted successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}
