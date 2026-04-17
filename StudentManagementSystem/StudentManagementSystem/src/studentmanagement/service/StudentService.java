package studentmanagement.service;

import studentmanagement.datastructure.MaxHeap;
import studentmanagement.model.Student;

// Service layer keeps validation and heap operations out of the UI code.
public class StudentService {
    private final MaxHeap studentHeap;

    public StudentService(int maxStudents) {
        studentHeap = new MaxHeap(maxStudents);
    }

    public void addStudent(String id, String name, double score) {
        String normalizedId = Student.normalizeId(id);

        if (studentHeap.findById(normalizedId) != null) {
            throw new IllegalArgumentException("Student ID already exists.");
        }

        studentHeap.insert(new Student(normalizedId, name, score));
    }

    public Student findStudentById(String id) {
        Student student = studentHeap.findById(id);
        return student == null ? null : new Student(student);
    }

    public void updateStudent(String id, String newName, double newScore) {
        boolean updated = studentHeap.updateStudent(id, newName, newScore);

        if (!updated) {
            throw new IllegalArgumentException("Student ID was not found.");
        }
    }

    public void deleteStudent(String id) {
        boolean deleted = studentHeap.removeById(id);

        if (!deleted) {
            throw new IllegalArgumentException("Student ID was not found.");
        }
    }

    public Student[] getRanking() {
        return studentHeap.toSortedArrayDescending();
    }

    public boolean isEmpty() {
        return studentHeap.isEmpty();
    }
}
