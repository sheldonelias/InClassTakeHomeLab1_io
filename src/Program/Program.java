package Program;

//Package classes
import Controller.AttendanceMonitor;
import Controller.ClassesLoader;
import Model.ClassCS;
import Model.Student;
import View.AttendanceReporter;

//I/O classes
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

//Date and Time classes
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Program
{
    //STATIC VARIABLES

    //Model members
    private static ClassCS[] classCS;
    private static int classCount;

    //Logic members
    static boolean isRunning = true;

    //Date and Time members
    static DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' HH:mm:ss");
    static String dateTimeString;

    public static void main(String[] args)
    {
        while(isRunning)
        {
            System.out.println("Welcome to the Student Attendance Manager.");
            System.out.println("Choose (C)reate Rolls, (P)rint Rolls, (E)xport Rolls, " +
                    "(S)ave Classes, (L)oad Classes, (T)ake Attendance, E(x)it");

            Scanner scnr = new Scanner(System.in);
            String menuChoice = scnr.next();

            //Menu choice interface system
            switch(menuChoice)
            {
                case "c":
                case "C": System.out.println("You selected Create Roles -----");
                    System.out.println("Your roles will be saved as Role_<ClassName>_<Date>.txt");
                    System.out.println("Your class will be saved automatically as Classes.txt");
                    enterClassCount();
                    initClasses();
                    //Creating role also saves class
                    try
                    {
                        saveClasses();
                    }catch (IOException excpt)
                    {
                        System.out.println("Cannot write to output file. " + excpt.getMessage());
                    }
                    break;
                case "p":
                case "P": System.out.println("You selected Print Roles -----");
                    printAllRoles();

                    //-- TEST ZONE --
                    //The arg delivers an array of students
                    //System.out.println( classCS[0].getClasses()[0].getFirstName()   );

                    //-- CODE TO USE --

                    break;
                case "e":
                case "E": System.out.println("You selected Export Roles -----");
                    System.out.println("Your roles will be saved as Role_<ClassName>_<Date>.txt");
                    //Iterates through all loaded classes and exports to file all students for each class
                    for(ClassCS classToReport: classCS)
                    {
                        //TWO WAYS TO DO THIS
                        //1: Create instance members.
                        // Instantiate and create objects that can be destroyed later
                        //Call instance methods.
                        //Instantiate Attendance report object
                        /*
                        AttendanceReport report = new AttendanceReport();

                        report.exportRoles(classToReport.getStudents(), classToReport.getClassName(),
                                classToReport.getClassNumber());

                        //Object destroyed, now eligible for garbage collection
                        report = new AttendanceReport();
                         */
                        //2: Call static methods that do same as #1 above, provided that no
                        //persistent data is needed later.
                        //Iterates through all loaded classes and exports to file all students for each class.
                        for(ClassCS classToExport: classCS)
                        {
                            AttendanceReporter.exportRoles(classToExport);
                        }
                    }
                    break;
                case "s":
                case "S": System.out.println("You selected Save Classes.");
                    System.out.println("Your class will be saved automatically as Classes.txt");
                    try
                    {
                        saveClasses();
                    }catch (IOException excpt)
                    {
                        System.out.println("Cannot write to output file. " + excpt.getMessage());
                    }
                    break;
                case "l":
                case "L": System.out.println("You selected Load Classes.");
                    //Calls loadClasses() assumes user has already selected Create Roles>enterClassCount()
                    //Assumes a file called Classes.txt has been created by the program.
                    try
                    {
                        classCS = ClassesLoader.loadClass();
                    }catch(IOException excpt)
                    {
                        System.out.println("The Classes.txt file is missing. " + excpt.getMessage());
                        Program.main(new String[0]);
                    }
                    System.out.println("Your courses are loaded.");
                    break;
                case "t":
                case "T": System.out.println("You selected Take Attendance.");
                    AttendanceMonitor.selectClass();
                    break;
                case "x":
                case "X": System.out.println("You selected Exit.");
                    System.exit(0);
                    isRunning = false;
                    break;
                default: System.out.println("Menu choice invalid. Try again.");
                    Program.main(new String[0]);
            }
        }
    }


    public static void initClasses()
    {
        classCS = new ClassCS[classCount];
        Scanner scnr = new Scanner(System.in);

        for(int i = 0; i < classCS.length; ++i)
        {
            System.out.print("Enter class name: ");
            String className = scnr.nextLine();
            System.out.print("Enter class number: ");
            String classNumber = scnr.nextLine();
            classCS[i] = new ClassCS(className, classNumber, false);
        }
    }

    public static void enterClassCount()
    {

        Scanner scnr = new Scanner(System.in);

        while(true)
        {
            try
            {
                System.out.print("Enter number of classes: ");
                classCount = scnr.nextInt();
                break;

            }catch(InputMismatchException exception)
            {
                System.out.println("You entered an invalid value: " + exception.getMessage());
                System.out.println("Try again.");

                //Resets scnr
                scnr.reset();
                scnr.next();
            }
        }
    }

    public static int getClassCount()
    {
        return classCount;
    }

    public static void saveClasses() throws IOException
    {
        // Exception check if there is path availalbe to build a file.
        //Remember, many paths are to mounted network drives
        //TODO:
        // A. Write a try/catch block that
        // 1. Declares/initializes a string with pathname
        // 2. Declares/initializes a FileOutputStream
        // 3. Declares/initializes a PrintWriter
        // B. Write an enhanced for-loop that iterates through each class array and
        // 1. Captures declares/initializes a LocalDateTime object
        // 2. Declares/initializes in class scope a DateTimeFormatter object
        //    with the static method DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' HH:mm:ss")
        // 3. In the try, initializes the DateTimeFormatter object with the instance
        //    method format(LocalDateTime.now()
        // 4. Declares and initializes a local scoped String with:
        //    "Class Name: " + classToSave.getClassName() + " " + "Class Number: " +
        //                    classToSave.getClassNumber();
        // 5. Print the string declared in #4 above to the output file using the PrintWriter object.
        // 6. Declare and initialize a Student array, and initialize with classToSave.getStudents();
        // 7. Write a for loop that stores it local scoped string:
        //    "Student Name " + students[i].getStudentID() +
        //                            " Last: " + students[i].getLastName() +
        //                            " First: " + students[i].getFirstName();
        // 8. Print the string from #7 above to the output file using the PrintWriter object.
        // 9. Print a blank line to the output file using the PrintWriter object.
        // 10. Flush and close the PrintWriter object
        // 11. Complete the catch part of the try/catch so that at the end of it, the system exits with error -1
        try
        {
            //Use file output class and instantiate with file name and path as arg
            String outputFile = String.format("src/Classes.txt");
            FileOutputStream fis = new FileOutputStream(outputFile);
            //Instantiate a printwriter
            PrintWriter pw = new PrintWriter(fis);

        for(ClassCS classToSave: classCS) {
            //Capture current date and time.
            LocalDateTime now = LocalDateTime.now();

            //Set date at instantiation. We will learn to get a real date
            dateTimeString = dtf1.format(LocalDateTime.now());

            String line = "Class Name: " + classToSave.getClassName() + " " + "Class Number: " +
                    classToSave.getClassNumber();

            //DEBUG: Check print to console that className is working
            System.out.println(line);

            //Write to file first line of className and date
            pw.println(line);

            //Loop through student list to print every student in array
            Student[] students = classToSave.getStudents();
            for (int i = 0; i < students.length; ++i) {
                if (students[i] != null) {
                    line = "Student Name " + students[i].getStudentID() +
                            " Last: " + students[i].getLastName() +
                            " First: " + students[i].getFirstName();

                    //DEBUG: Check print to console that student info is working
                    //System.out.println(line);

                    pw.println(line);
                }
            }
            pw.println();
            //DEBUG: Check print to console that student info is working
            //System.out.println();
        }

        //Must flush and close, kind of like airplane toilets all PrintWriter objects
        pw.flush();
        pw.close();

        }catch(IOException excpt)
        {
            //In case of rare bad news that file could not be created.
            System.out.println("Output file not found. " + excpt);
            System.exit(-1);
        }
    }

    public static ClassCS[] getClasses()
    {
        return classCS;
    }

    public static void printAllRoles() throws NullPointerException
    {
        //Set date at instantiation. We will learn to get a real date
        dateTimeString = dtf1.format(LocalDateTime.now());

        for(int i = 0; i < classCS.length; ++i )
        {
            try {
                System.out.println(classCS[i].getClassNumber() + ": " + classCS[i].getClassName() +
                        " " + dateTimeString);

                for (Student student : classCS[i].getStudents())
                {
                    if (student != null)
                    {
                       System.out.println(
                               student.getStudentID() + " " + student.getFirstName() + " " +
                               student.getLastName()
                                );
                    }
                }
                System.out.println("- - -");
            } catch(NullPointerException excpt)
            {
                System.out.println("Found a null Student in the Student[]." + excpt);
            }
        }
    }
}
