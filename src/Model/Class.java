package Model;

public interface Class
{
    String className = "Unnamed";

    String classNumber = "000";

    Student[] students = new Student[0];

    public abstract Student[] getStudents();

    public void setStudents(Student[] students);

    public String getClassName();

    public String getClassNumber();
}
