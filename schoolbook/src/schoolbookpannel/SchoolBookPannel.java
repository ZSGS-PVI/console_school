package schoolbookpannel;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import schoolbookpannel.dto.Student;
import schoolbookpannel.repository.SchoolBookPannelRepository;
import schoolbookpannel.student.AddStudentViewModel;

public class SchoolBookPannel {
    private AddStudentViewModel addStudentViewModel;
    private Scanner in;
    private AddStudent addStudent;
    private SchoolBookPannelRepository repository;

    public SchoolBookPannel() {
        addStudent = new AddStudent();
        addStudentViewModel = new AddStudentViewModel(this);
        in = new Scanner(System.in);
    }
    
//    public SchoolBookPannel() {
//        addStudent = new AddStudent(repository); // Pass repository instance here
//        addStudentViewModel = new AddStudentViewModel(repository); // Pass repository instance here
//        in = new Scanner(System.in);
//    }
  

    public static void main(String[] args) throws SQLException {
        SchoolBookPannel schoolbookpannel = new SchoolBookPannel();
       // SchoolBookPannelRepository repository = new SchoolBookPannelRepository(schoolbookpannel.addStudent);
        schoolbookpannel.init();
        
    }

    public void init() throws SQLException {
        while (true) {
            System.out.println("--------WELCOME TO SCHOOL MANAGEMENT SYSTEM--------");
            System.out.println("1. Admin\n2. Student\n3. Exit");
            System.out.print("Enter the option: ");

            int choice = getUserInput();
            if (choice == -1) continue;

            switch (choice) {
                case 1:
                    handleAdminOptions();
                    break;
                case 2:
                    studentfun();
                    break;
                case 3:
                    System.out.println("Goodbye! Have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private int getUserInput() {
        try {
            return in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            in.next(); 
            return -1; 
        }
    }

    private void handleAdminOptions() throws SQLException {
    	while(true) {
        System.out.println("1. Login\n2. Sign Up");
        System.out.print("Enter the option: ");
        int adminChoice = getUserInput();
        if (adminChoice == -1) return;

        switch (adminChoice) {
        case 1:
            if (addStudent.login()) {
                System.out.println("Login successful:");
                adminfun();
                
            } else {
                System.out.println("Login failed. Exiting...");
            }
            break;
        case 2:
          
            if (addStudent.signUp()) {
                System.out.println("Login successful!");
                adminfun();
                
            } else {
                System.out.println("Login failed. Exiting...");
            }
            break;
        default:
            System.out.println("Invalid choice.");
    }
}
    }
	

    private void adminfun() throws SQLException {
        while (true) {
            System.out.println("1. Add Student\n2. View Student\n3. Update Student\n4. Delete Student\n5. Exit");
            System.out.print("Enter the option: ");

            int option = getUserInput();
            if (option == -1) continue;
            
            

            switch (option) {
                case 1:
                    addStudent.addStudentInfo();
                    break;
                case 2:
                    addStudent.viewStudent();
                    break;
                case 3:
                    addStudent.updateStudent();
                    break;
                case 4:
                    addStudent.deleteStudent();
                    break;
                case 5:
                   
                    init();
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void studentfun() throws SQLException {

        
        addStudent.viewStudent();
        init();
    }
}
