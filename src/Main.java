import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;

class Student {
    int rollNo;
    String name;
    double marks;

    Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks);
        System.out.println("--------------------");
    }
}

public class Main {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static String fileName = "students.txt";

    static void loadStudents() {
        try {
            Path path = Path.of(fileName);

            if (!Files.exists(path)) return;

            for (String line : Files.readAllLines(path)) {
                String[] data = line.split("\t", -1);

                if (data.length == 3) {
                    students.add(new Student(
                            Integer.parseInt(data[0]),
                            data[1],
                            Double.parseDouble(data[2])
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading student data.");
        }
    }

    static void saveStudents() {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(fileName))) {

            for (Student s : students) {
                writer.write(s.rollNo + "\t" + s.name + "\t" + s.marks);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }

    public static void main(String[] args) {

        loadStudents();

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Roll Number: ");
                    int roll = sc.nextInt();
                    sc.nextLine();

                    boolean exists = false;

                    for (Student s : students) {
                        if (s.rollNo == roll) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        System.out.println("Roll Number already exists!");
                        break;
                    }

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    students.add(new Student(roll, name, marks));
                    saveStudents();

                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No students found!");
                    }

                    for (Student s : students) {
                        s.display();
                    }
                    break;

                case 3:
                    System.out.print("Enter Roll Number: ");
                    int search = sc.nextInt();

                    boolean found = false;

                    for (Student s : students) {
                        if (s.rollNo == search) {
                            s.display();
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Student not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Roll Number: ");
                    int delete = sc.nextInt();

                    boolean removed = students.removeIf(
                            s -> s.rollNo == delete
                    );

                    if (removed) {
                        saveStudents();
                        System.out.println("Student deleted!");
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;

                case 5:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}