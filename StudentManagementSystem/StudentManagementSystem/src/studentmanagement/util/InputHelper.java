package studentmanagement.util;

import java.util.Scanner;

public final class InputHelper {
    private InputHelper() {
    }

    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            // Repeat until the user enters a valid menu choice or number.
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.println("Please enter a number from " + min + " to " + max + ".");
                    continue;
                }

                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static double readDoubleInRange(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            // Centralized validation keeps input handling consistent across the app.
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (value < min || value > max) {
                    System.out.println("Please enter a value from " + min + " to " + max + ".");
                    continue;
                }

                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Invalid value. Please try again.");
            }
        }
    }

    public static String readRequiredText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field must not be empty.");
        }
    }
}
