package reportartifacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import studentmanagement.model.Student;
import studentmanagement.service.StudentService;

public class ReportBenchmark {
    private static final int[] DATA_SIZES = {10, 100, 500, 1000};
    private static final int WARMUP_ROUNDS = 20;
    private static final int MEASURE_ROUNDS = 60;

    public static void main(String[] args) {
        System.out.println("Benchmark results for Student Management and Ranking System");
        System.out.println("Average time is measured in milliseconds.");
        System.out.println();
        System.out.println("n,add_one_student_ms,show_ranking_heap_ms,show_ranking_arraylist_sort_ms,search_by_id_ms");

        for (int size : DATA_SIZES) {
            runWarmup(size);

            double addTime = averageMillis(() -> benchmarkAdd(size));
            double heapRankingTime = averageMillis(() -> benchmarkHeapRanking(size));
            double listSortTime = averageMillis(() -> benchmarkArrayListSort(size));
            double searchTime = averageMillis(() -> benchmarkSearch(size));

            System.out.printf(
                    "%d,%.4f,%.4f,%.4f,%.4f%n",
                    size,
                    addTime,
                    heapRankingTime,
                    listSortTime,
                    searchTime
            );
        }
    }

    private static void runWarmup(int size) {
        for (int i = 0; i < WARMUP_ROUNDS; i++) {
            benchmarkAdd(size);
            benchmarkHeapRanking(size);
            benchmarkArrayListSort(size);
            benchmarkSearch(size);
        }
    }

    private static double averageMillis(Runnable task) {
        long totalNanos = 0;

        for (int i = 0; i < MEASURE_ROUNDS; i++) {
            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();
            totalNanos += (end - start);
        }

        return totalNanos / 1_000_000.0 / MEASURE_ROUNDS;
    }

    private static void benchmarkAdd(int size) {
        StudentService service = new StudentService(size + 5);

        for (int i = 0; i < size; i++) {
            service.addStudent(idAt(i), nameAt(i), scoreAt(i));
        }

        service.addStudent(idAt(size), nameAt(size), scoreAt(size));
    }

    private static void benchmarkHeapRanking(int size) {
        StudentService service = new StudentService(size + 1);

        for (int i = 0; i < size; i++) {
            service.addStudent(idAt(i), nameAt(i), scoreAt(i));
        }

        service.getRanking();
    }

    private static void benchmarkArrayListSort(int size) {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            students.add(new Student(idAt(i), nameAt(i), scoreAt(i)));
        }

        Collections.sort(
                students,
                Comparator.comparingDouble(Student::getScore).reversed()
        );
    }

    private static void benchmarkSearch(int size) {
        StudentService service = new StudentService(size + 1);

        for (int i = 0; i < size; i++) {
            service.addStudent(idAt(i), nameAt(i), scoreAt(i));
        }

        service.findStudentById(idAt(size - 1));
    }

    private static String idAt(int index) {
        return String.format("S%04d", index + 1);
    }

    private static String nameAt(int index) {
        return "Student " + (index + 1);
    }

    private static double scoreAt(int index) {
        Random random = new Random(2026L + index * 17L);
        return Math.round((4 + random.nextDouble() * 6) * 100.0) / 100.0;
    }
}
