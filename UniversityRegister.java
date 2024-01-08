import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniversityRegister {
    private final List<Student> students;

    public UniversityRegister(List<Student> students) {
        this.students = List.copyOf(students);
    }

    //base student list
    public UniversityRegister() {
        this(List.of(
                new Student(1001, "John", "Doe", CourseType.CS, "Java Programming"),
                new Student(1002, "Alice", "Smith", CourseType.CSG, "Data Structures"),
                new Student(1003, "Emma", "Johnson", CourseType.SE, "Algorithms"),
                new Student(1004, "Michael", "Williams", CourseType.CS, "Web Development")
        ));
    }

    //add student function
    public UniversityRegister addStudent(Student student) {
        List<Student> updatedStudents = Stream.concat(
                students.stream(),
                Stream.of(student)
        ).collect(Collectors.toList());
        return new UniversityRegister(updatedStudents);
    }

    //remove student function
    public UniversityRegister removeStudent(int idNumber) {
        Predicate<Student> byId = student -> student.idNumber() == idNumber;
        List<Student> updatedStudents = students.stream()
                .filter(byId.negate())
                .collect(Collectors.toList());
        return new UniversityRegister(updatedStudents);
    }
    //function for searching students
    public List<Student> searchStudents(Predicate<Student> predicate) {
        return students.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    //function to print student details
    public void printStudents(List<Student> studentList) {
        studentList.forEach(student -> System.out.println(
                "ID: " + student.idNumber() +
                        ", Name: " + student.firstName() +
                        ", Last Name: " + student.lastName() +
                        ", Course Type: " + student.courseType() +
                        ", Module: " + student.module()));
    }

     public List<Student> getAllStudents() {
        return List.copyOf(students);
    }
    
    //function used to search students by first letter of name
   /* public List<Student> searchStudentsByFirstLetter(String letter) {
        Predicate<Student> predicate = student -> student.firstName().startsWith(letter);
        return students.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    } */
    
    //replacement function using recursion
    public List<Student> searchStudentsByFirstLetter(char letter) {
        return recursiveFunction(students, letter);
    }

    private List<Student> recursiveFunction(List<Student> students, char letter) {
        if (students.isEmpty()) {
            return List.of(); //base case
        }

        Student currentStudent = students.get(0);
        List<Student> remainingStudents = students.subList(1, students.size());

        List<Student> studentsWithFirstLetter = recursiveFunction(remainingStudents, letter);
        return currentStudent.firstName().charAt(0) == letter ?
                Stream.concat(Stream.of(currentStudent), studentsWithFirstLetter.stream()).collect(Collectors.toList()) :
                studentsWithFirstLetter;
    }
    
    //main
    public static void main(String[] args) {
        UniversityRegister register = new UniversityRegister();

        //scanner for user input
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        //switch statements to decide what is achieved with program, allows users to use the program
        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a student");
            System.out.println("2. Remove a student");
            System.out.println("3. Query students");
            System.out.println("4. View all students");
            System.out.println("5. Query students by first letter of name");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter student details:");
                    System.out.print("ID number: ");
                    int idNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("Choose Course Type:");
                    System.out.println("1. CS");
                    System.out.println("2. CSG");
                    System.out.println("3. SE");
                    int courseChoice = scanner.nextInt();
                    CourseType courseType;
                    switch (courseChoice) {
                        case 1:
                            courseType = CourseType.CS;
                            break;
                        case 2:
                            courseType = CourseType.CSG;
                            break;
                        case 3:
                            courseType = CourseType.SE;
                            break;
                        default:
                            System.out.println("Invalid course type!");
                            continue;
                    }
                    scanner.nextLine();
                    System.out.print("Module: ");
                    String module = scanner.nextLine();

                    register = register.addStudent(new Student(idNumber, firstName, lastName, courseType, module));
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    System.out.print("Enter ID number of the student to remove: ");
                    int idToRemove = scanner.nextInt();
                    scanner.nextLine();

                    register = register.removeStudent(idToRemove);
                    System.out.println("Student removed successfully!");
                    break;

                case 3:
                    System.out.println("Choose query option:");
                    System.out.println("1. By name");
                    System.out.println("2. By ID number");
                    System.out.println("3. By course");
                    System.out.println("4. By module");
                    int queryOption = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter query: ");
                    String query = scanner.nextLine();

                    Predicate<Student> predicate;
                    switch (queryOption) {
                        case 1:
                            predicate = student -> student.firstName().equalsIgnoreCase(query) || student.lastName().equalsIgnoreCase(query);
                            break;
                        case 2:
                            int id = Integer.parseInt(query);
                            predicate = student -> student.idNumber() == id;
                            break;
                        case 3:
                            predicate = student -> student.courseType().toString().equalsIgnoreCase(query.toUpperCase());
                            break;
                        case 4:
                            predicate = student -> student.module().equalsIgnoreCase(query);
                            break;
                        default:
                            System.out.println("Invalid query option!");
                            continue;
                    }

                    List<Student> queriedStudents = register.searchStudents(predicate);
                    register.printStudents(queriedStudents);
                    break;

                case 4:
                    List<Student> allStudents = register.getAllStudents();
                    register.printStudents(allStudents);
                    break;

                    /*case 5:
                    System.out.print("Enter the first letter of the name to query: ");
                    String firstLetter = scanner.nextLine().substring(0, 1).toUpperCase();
                    List<Student> studentsByFirstLetter = register.searchStudentsByFirstLetter(firstLetter);
                    register.printStudents(studentsByFirstLetter);
                    break;*/

                case 5:
                    System.out.print("Enter the first letter of the name: ");
                    char firstLetter = scanner.nextLine().charAt(0);

                    List<Student> studentsWithFirstLetter = register.searchStudentsByFirstLetter(firstLetter);
                    register.printStudents(studentsWithFirstLetter);
                    break;

                case 6:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice! Enter a valid option.");
                    break;
            }
        }
        scanner.close();
    }
}