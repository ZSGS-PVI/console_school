package schoolbookpannel.student;

import java.sql.SQLException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import schoolbookpannel.AddStudent;
import schoolbookpannel.SchoolBookPannel;
import schoolbookpannel.dto.Student;
import schoolbookpannel.repository.SchoolBookPannelRepository;

public class AddStudentViewModel {
	
	   private List<Student> studentList = new ArrayList<>();

	private AddStudent addStudent;
	private SchoolBookPannelRepository repository;

	public AddStudentViewModel(AddStudent addStudent) {
		this.addStudent = addStudent;
	}


  public AddStudentViewModel(SchoolBookPannelRepository repository) {
      this.repository = repository;
  }

	
	public boolean loginCount() {
		SchoolBookPannelRepository.getInstanse().loginChech();
		return true;
	}
	public boolean login(Student student) {
		SchoolBookPannelRepository.getInstanse().loginAdmin(student);
		return true;
	}
	

//    public List<Student> list1() {
//        List<Student> students = SchoolBookPannelRepository.getInstanse().getStudents();
//        if (students != null) {
//            this.studentList = students;
//        }
//        return this.studentList;
//    }

public boolean addAdmin(Student student) throws SQLException {
	boolean signupAdmin = SchoolBookPannelRepository.getInstanse().signupAdmin(student);
	//this.addStudent.onSuccess();
		return signupAdmin;
	}




public void signup(Student student) {
    try {
        if (addAdmin(student)) {
           addStudent.notifications("Sign up successful!");
        } else {
        	 addStudent.notifications("Sign up failed. Please try again.");
        }
    } catch (SQLException e) {
        System.out.println("Error during signup: " + e.getMessage());
        e.printStackTrace(); // Print stack trace for detailed error info
    }
}

//public List<Student> list() {
//    return repository.getStudents();
//}




// 1.add student details

	public AddStudentViewModel(SchoolBookPannel schoolBookPannel) {
		// TODO Auto-generated constructor stub
	}

	public void validate(Student student) {

		SchoolBookPannelRepository.getInstanse().insertStudent(student);
		

	}

// 2.view student details

	public void viewUsers(int rollNo) {
	    List<Student> students = SchoolBookPannelRepository.getInstanse().viewStudentById(rollNo);

	    if (students.isEmpty()) {
	        addStudent.notifications("No students found.");
	    } else {
	        addStudent.notifications("List of students:");
	        for (Student student : students) {
	            addStudent.studentlist(student);
	        }
	    }
	}

	public List<Student> list() {
		List<Student> students = SchoolBookPannelRepository.getInstanse().getStudents();
		return students;
	}

//3.Update the student details
	public void update(int idToUpdate) {

		List<Student> students = SchoolBookPannelRepository.getInstanse().getStudents();

		}

// 4.Delete the student Details
	public void delete(int idTODelete) {
		try {
			SchoolBookPannelRepository.getInstanse().deleteStudent(idTODelete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	// student name validation
	public void isValidName(String name) {

		if (name.matches("^[a-zA-Z]+$")) {
		}

		else {
			addStudent.notifications("only characters are allowed!");

			addStudent.addStudentInfo();
		}

	}

	// student DOB validation
	public void isValidDateOfBirth(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		try {
			Date date = dateFormat.parse(dateStr);

			Date currentDate = new Date();

			if (!date.after(currentDate) && date.before(currentDate)) {

			}
		} catch (ParseException e) {
			// ParseException will be thrown if the date format is invalid
			addStudent.notifications("Invalid date of birth!");
		
			addStudent.addStudentInfo();

		}
	}

	// student Mobile number validation

	public void isValidMobileNo(String number) {

		if (number != null && !number.isEmpty()) {

			if (number.matches("^[0-9]{10}$")) {

			} else {
				addStudent.notifications("Invalid mobile number! Only 10 digits are allowed.");
				
				addStudent.addStudentInfo();
			}
		} else {
			addStudent.notifications("Mobile number cannot be empty or null!");
			addStudent.addStudentInfo();
		}
	}

	// student Email validation
	public void isValidEmail(String name) {

		if (name != null && !name.isEmpty()) {

			if (name.matches(
					"^[A-Za-z0-9-\\+]+(\\.[A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				
			}

			else {
				addStudent.notifications("Invalid Email Id!");
				addStudent.addStudentInfo();
			}

		} else {
			addStudent.notifications("Email Id cannot be empty or null!");
			addStudent.addStudentInfo();
		}

	}
	
	public boolean isValidPassword(String password) {
	    

	    if (password.length() < 8) {
	        addStudent.notifications("Password must be at least 8 characters long!");
	        return false;
	    }

	    if (!password.matches(".*\\d.*")) {
	        addStudent.notifications("Password must contain at least one digit!");
	        return false;
	    }

	    if (!password.matches(".*[a-z].*")) {
	        addStudent.notifications("Password must contain at least one lowercase letter!");
	        return false;
	    }

	    if (!password.matches(".*[A-Z].*")) {
	        addStudent.notifications("Password must contain at least one uppercase letter!");
	        return false;
	    }

	    
	    return true;
	}




//
//	public void viewStudentById(int studentId) {
//		// TODO Auto-generated method stub
//		
//	}




}
