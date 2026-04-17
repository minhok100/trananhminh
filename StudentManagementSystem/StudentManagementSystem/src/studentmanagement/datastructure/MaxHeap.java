package studentmanagement.datastructure;

import studentmanagement.model.Student;

// MaxHeap keeps the student with the highest score at the root.
public class MaxHeap {
    private final Student[] heap;
    private int size;

    public MaxHeap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }

        this.heap = new Student[capacity];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Student student) {
        if (size == heap.length) {
            throw new IllegalStateException("The student list is full.");
        }

        // Insert at the end first, then move the node upward if needed.
        heap[size] = student;
        heapifyUp(size);
        size++;
    }

    public Student findById(String id) {
        int index = findIndexById(id);
        return index >= 0 ? heap[index] : null;
    }

    public boolean updateStudent(String id, String newName, double newScore) {
        int index = findIndexById(id);
        if (index < 0) {
            return false;
        }

        double oldScore = heap[index].getScore();
        heap[index].setName(newName);
        heap[index].setScore(newScore);

        // Rebalance only in the direction affected by the new score.
        if (newScore > oldScore) {
            heapifyUp(index);
        } else if (newScore < oldScore) {
            heapifyDown(index);
        }

        return true;
    }

    public boolean removeById(String id) {
        int index = findIndexById(id);
        if (index < 0) {
            return false;
        }

        size--;
        heap[index] = heap[size];
        heap[size] = null;

        if (index < size) {
            rebalanceFrom(index);
        }

        return true;
    }

    public Student extractMax() {
        if (isEmpty()) {
            return null;
        }

        // Remove the root, move the last element to the top, then fix the heap.
        Student maxStudent = heap[0];
        size--;
        heap[0] = heap[size];
        heap[size] = null;

        if (!isEmpty()) {
            heapifyDown(0);
        }

        return maxStudent;
    }

    public Student[] toSortedArrayDescending() {
        // Work on a copy so the original heap stays unchanged after ranking output.
        MaxHeap copiedHeap = copy();
        Student[] sortedStudents = new Student[size];

        for (int i = 0; i < sortedStudents.length; i++) {
            sortedStudents[i] = copiedHeap.extractMax();
        }

        return sortedStudents;
    }

    private MaxHeap copy() {
        MaxHeap copiedHeap = new MaxHeap(heap.length);
        copiedHeap.size = size;

        for (int i = 0; i < size; i++) {
            copiedHeap.heap[i] = new Student(heap[i]);
        }

        return copiedHeap;
    }

    private int findIndexById(String id) {
        String normalizedId = Student.normalizeId(id);

        for (int i = 0; i < size; i++) {
            if (heap[i].getId().equals(normalizedId)) {
                return i;
            }
        }

        return -1;
    }

    private void rebalanceFrom(int index) {
        int parentIndex = (index - 1) / 2;

        if (index > 0 && heap[index].getScore() > heap[parentIndex].getScore()) {
            heapifyUp(index);
        } else {
            heapifyDown(index);
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            if (heap[index].getScore() <= heap[parentIndex].getScore()) {
                break;
            }

            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int largestIndex = index;

            if (leftChild < size && heap[leftChild].getScore() > heap[largestIndex].getScore()) {
                largestIndex = leftChild;
            }

            if (rightChild < size && heap[rightChild].getScore() > heap[largestIndex].getScore()) {
                largestIndex = rightChild;
            }

            if (largestIndex == index) {
                break;
            }

            swap(index, largestIndex);
            index = largestIndex;
        }
    }

    private void swap(int firstIndex, int secondIndex) {
        Student temp = heap[firstIndex];
        heap[firstIndex] = heap[secondIndex];
        heap[secondIndex] = temp;
    }
}
