package studentmanagement.model;

// Student is a simple model object with validation around input data.
public class Student {
    // ID should not change after creation, so it is stored as final.
    private final String id;
    private String name;
    private double score;

    public Student(String id, String name, double score) {
        this.id = normalizeId(id);
        this.name = normalizeName(name);
        validateScore(score);
        this.score = score;
    }

    public Student(Student other) {
        // Copy constructor is useful when the UI should not mutate heap data directly.
        this(other.id, other.name, other.score);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = normalizeName(name);
    }

    public void setScore(double score) {
        validateScore(score);
        this.score = score;
    }

    public static String normalizeId(String id) {
        String normalized = requireText(id, "Student ID");
        return normalized.toUpperCase();
    }

    private static String normalizeName(String name) {
        return requireText(name, "Student name");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
        return value.trim();
    }

    private static void validateScore(double score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException("Score must be between 0 and 10.");
        }
    }

    @Override
    public String toString() {
        return String.format("%-10s %-25s %6.2f", id, name, score);
    }
}
