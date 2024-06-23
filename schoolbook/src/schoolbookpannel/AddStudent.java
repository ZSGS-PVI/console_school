package schoolbookpannel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import schoolbookpannel.dto.Student;
import schoolbookpannel.repository.SchoolBookPannelRepository;
import schoolbookpannel.student.AddStudentViewModel;

public class AddStudent {
	
	private AddStudentViewModel addStudentviewmodel;
	private SchoolBookPannel schoolBookPannel;
	private Scanner in = new Scanner(System.in);
	//private student student;
	private SchoolBookPannelRepository repository;

	
    public AddStudent() { // Modify constructor
        this.repository = repository; // Initialize repository
        addStudentviewmodel = new AddStudentViewModel(repository);
    	addStudentviewmodel=new AddStudentViewModel(this);// Pass repository here
    }
 

	


	public void addStudentInfo() {
	
		int rollNo = 0;
		String name;
		String DOB;
		String mobileno;
		String email;

		while (true) {
			System.out.println("Enter the rollNo  (type '0' to stop adding names):");
			rollNo = in.nextInt();

			if (rollNo == 0) {
				break;
			}

			System.out.println("Enter  student name:");
			name = in.next();
			addStudentviewmodel.isValidName(name);

			System.out.println("Enter student DOB Like (\"yyyy-MM-dd\"): ");
			DOB = in.next();
			addStudentviewmodel.isValidDateOfBirth(DOB);
			System.out.println("Enter  student mobileno: ");
			mobileno = in.next();
			addStudentviewmodel.isValidMobileNo(mobileno);

			System.out.println("Enter student email  ");
			email = in.next();
			addStudentviewmodel.isValidEmail(email);

			Student student = new Student(rollNo, name, DOB, mobileno, email);
			addStudentviewmodel.validate(student);

		}
	}

	public boolean login() throws SQLException {
	    System.out.print("Enter Email: ");
	    String email = in.next();
	    addStudentviewmodel.isValidEmail(email);


	    System.out.print("Enter Password: ");
	    String password = in.next();
	    if (!addStudentviewmodel.isValidPassword(password)) {
	        System.out.println("Invalid password format.");
	        return false;
	    }

	    Student student = new Student(email, password);
	    return addStudentviewmodel.login(student);
	}

	public boolean signUp() throws SQLException {
	    System.out.print("Enter the id: ");
	    int id = in.nextInt();
	    System.out.print("Enter name: ");
	    String name = in.next();
	    addStudentviewmodel.isValidName(name);


	    System.out.print("Enter Email: ");
	    String email = in.next();
	   addStudentviewmodel.isValidEmail(email);
	    System.out.print("Enter Password: ");
	    String password = in.next();
	    if (!addStudentviewmodel.isValidPassword(password)) {
	        System.out.println("Invalid password format.");
	        return false;
	    }

	    Student student = new Student(id, name, email, password);
	    addStudentviewmodel.signup(student);
		return false;
	}




	public void viewStudent() {
		System.out.println("Enter the student ID:");
    int studentId=in.nextInt();
		
		addStudentviewmodel.viewUsers(studentId);
	}

	public void updateStudent() {
	    System.out.println("Enter the Id to update:");
	    int idToUpdate = in.nextInt();
	    List<Student> list = addStudentviewmodel.list();
	    
	    if (list == null || list.isEmpty()) {
            System.out.println("Student list is empty or not initialized.");
            return;
        }


	    boolean found = false;
	    for (Student student : list) {
	        if (student.getRollNo() == idToUpdate) {
	            found = true;

	            System.out.println(" 1.Name\n 2.DOB \n 3.Mobileno\n 4.Email\n 5.Back");
	            
	            int choice = in.nextInt();
	            in.nextLine(); // Consume newline character

	            switch (choice) {
	                case 1:
	                    System.out.println("Enter new name: ");
	                    String newName = in.nextLine();
	                    student.setName(newName);
	                    SchoolBookPannelRepository.getInstanse().updateStudent(student);
	                    break;
	                case 2:
	                    System.out.println("Enter new DOB: ");
	                    String newDOB = in.nextLine();
	                    student.setDOB(newDOB);
	                    SchoolBookPannelRepository.getInstanse().updateStudent(student);
	                    break;
	                case 3:
	                    System.out.println("Enter new MobileNo: ");
	                    String newMobileNo = in.nextLine();
	                    student.setMobileno(newMobileNo);
	                    SchoolBookPannelRepository.getInstanse().updateStudent(student);
	                    break;
	                case 4:
	                    System.out.println("Enter new email: ");
	                    String newEmail = in.nextLine();
	                    student.setEmail(newEmail);
	                    SchoolBookPannelRepository.getInstanse().updateStudent(student);
	                    break;
	                default:
	                    System.out.println("Invalid choice.");
	                    break;
	            }
	        }
	    }

	    if (!found) {
	        System.out.println("Student not found with ID: " + idToUpdate);
	    }

	    addStudentviewmodel.update(idToUpdate);
	}
	public void deleteStudent() {

		System.out.println("Enter the Id to delete:");
		int idTODelete = in.nextInt();
		

		addStudentviewmodel.delete(idTODelete);
	}

	public void onSuccess() {
		System.out.println("Inserted Successfully");

	}
	public void notifications(String string) {
		System.out.println(string);
	}

	public void showError(String errorMessage) {
		System.out.println(errorMessage);
	}

	public void studentlist(Student student) {
		System.out.println("---------------------------");
		System.out.println("ID: " + student.getRollNo());
		System.out.println("Name: " + student.getName());
		System.out.println("DOB: " + student.getDOB());
		System.out.println("Mobile No: " + student.getMobileno());
		System.out.println("Email: " + student.getEmail());
		System.out.println("---------------------------");
	}
	





	

}
