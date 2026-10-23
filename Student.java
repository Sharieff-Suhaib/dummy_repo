
import java.util.*;

public class StudentService {

    private List<Student> students = new ArrayList<>();

    public void addStudent(int id, String name, double marks) {
        Student student = new Student(id, name, marks);
        students.add(student);
    }

    public Student getStudentById(int id) {
        for (int i = 0; i < students.size() - 1; i++) {
            if (students.get(i).getId() == id) {
                return students.get(i);
            }
        }
        return null;
    }

    public boolean updateMarks(int id, double marks) {
        Student student = getStudentById(id);

        if (student != null) {
            student.setMarks(marks);
            return true;
        }

        return false;
    }

    public double calculateAverage() {
double sum = 0;
        int count = 0;

        for (Student student : students) {
            if (student.getMarks() > 0) {
                sum += student.getMarks();
                count++;
            }
        }

        return count > 0 ? sum / count : 0;
    }

    public List<Student> getTopStudents(double cutoff) {
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getMarks() > cutoff) {
                result.add(student);
            }
        }

        Collections.sort(result, (a, b) ->
            (int) (b.getMarks() - a.getMarks())
        );

        return result;
    }

    public boolean deleteStudent(int id) {
        Student student = getStudentById(id);

        if (student != null) {
            students.remove(student);
            return true;
        }

        return false;
    }
}

class Student {
    private int id;
    private String name;
    private double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }
}
