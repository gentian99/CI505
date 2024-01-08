//student record with datafields regarding student details
public record Student(int idNumber, String firstName, String lastName, CourseType courseType, String module) {}

//enum used to create constants
//courses students can be enrolled in
enum CourseType {
    CS,
    CSG,
    SE
}

