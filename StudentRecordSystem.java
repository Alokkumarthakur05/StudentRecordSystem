import java.io.*;
import java.util.Scanner;

public class StudentRecordSystem {

    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Record Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewAllStudents();
                case 3 -> searchStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static void addStudent(Scanner sc) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("Enter Student ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();  // consume newline
            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            String record = id + "," + name + "," + age + "," + course;
            writer.write(record);
            writer.newLine();
            System.out.println("Student record added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static void viewAllStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- All Student Records ---");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    System.out.println("ID: " + data[0] + ", Name: " + data[1] + ", Age: " + data[2] + ", Course: " + data[3]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No records found. File does not exist.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static void searchStudent(Scanner sc) {
        System.out.print("Enter Student ID to search: ");
        String searchId = sc.nextLine();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4 && data[0].equals(searchId)) {
                    System.out.println("Record Found: ID: " + data[0] + ", Name: " + data[1] + ", Age: " + data[2] + ", Course: " + data[3]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with ID " + searchId + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static void deleteStudent(Scanner sc) {
        System.out.print("Enter Student ID to delete: ");
        String deleteId = sc.nextLine();
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4 && data[0].equals(deleteId)) {
                    deleted = true;
                    continue; // skip writing this line
                }
                writer.write(line);
                writer.newLine();
            }

            if (deleted) {
                System.out.println("Student with ID " + deleteId + " deleted.");
            } else {
                System.out.println("Student with ID " + deleteId + " not found.");
            }

        } catch (IOException e) {
            System.out.println("Error processing file.");
        }

        // Replace original file with temp file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }
}





