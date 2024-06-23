package schoolbookpannel.repository;

import java.io.FileWriter;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import schoolbookpannel.AddStudent;
import schoolbookpannel.dto.Student;

public class SchoolBookPannelRepository {
	
	

	private AddStudent addStudent;

	public void AddStudentViewModel(AddStudent addStudent) {
		this.addStudent = addStudent;
	}

	private static SchoolBookPannelRepository repository;
	 private static final String url = "jdbc:mysql://localhost:3306/schoolManagementconsole";
	    private static final String username = "root";
	    private static final String password = "new_password";
		private List<Student> studentList;

	

	private SchoolBookPannelRepository() {
		 try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	}

//	public SchoolBookPannelRepository(AddStudent addStudent2) {
//		// TODO Auto-generated constructor stub
//	}

	public static SchoolBookPannelRepository getInstanse() {
		if (repository == null) {
			repository = new SchoolBookPannelRepository();
		}
		return repository;
	}
	
	public boolean loginChech() {
	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	        String query = "SELECT COUNT(*) FROM AdminSignup"; 
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	             ResultSet resultSet = preparedStatement.executeQuery()) {

	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count > 0; // If count is greater than 0, admin exists
	            }
	        }
	    }catch(SQLException e) {
			System.out.println("SQL Error: "+e.getMessage());
			System.out.println("SQL Error: "+e.getSQLState());
			System.out.println("SQL Error: "+e.getErrorCode());
			 e.printStackTrace();
		        return false;
		}
	    return false; // Default return if any exception occurs or admin doesn't exist
	}
	

	
	
	
	public boolean signupAdmin(Student student)  {
	    //boolean isSuccess = false;
		try(Connection connection=DriverManager.getConnection(url,username,password)){
			String addadmin="INSERT INTO AdminSignup(id,name,email,password) VALUES(?,?,?,?)";
			try(PreparedStatement preparedStstement=connection.prepareStatement(addadmin)){
				 preparedStstement.setInt(1, student.getId());
				 preparedStstement.setString(2, student.getName());
				 preparedStstement.setString(3, student.getEmail());
				 preparedStstement.setString(4, student.getPassword());
				
					
					int rowsAffected= preparedStstement.executeUpdate();
					if(rowsAffected>0) {
					
	
						 return true;
						
					}
			}
		}
		catch(SQLException e) {

			 e.printStackTrace();
			 return false;
		}
		 return false;
	}
	
	public boolean loginAdmin(Student student) {
		
		try (Connection connection=DriverManager.getConnection(url,username,password)){
			String query="SELECT * FROM AdminSignup WHERE  email=? AND password=?";
			try(PreparedStatement preparedStatement=connection.prepareCall(query)){
				preparedStatement.setString(1,student.getEmail());
				preparedStatement.setString(2,student.getPassword());
				ResultSet resultSet=preparedStatement.executeQuery();
				return resultSet.next();
				
			}
		}
		catch(SQLException e) {
			addStudent.notifications("SQL Error: "+e.getMessage());
			
			 e.printStackTrace();
		        return false;
		}
	}
	public boolean studentExists(int rollNo) {
	    String query = "SELECT COUNT(*) FROM Students WHERE rollno = ?";
	    try (Connection connection = DriverManager.getConnection(url, username, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, rollNo);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error checking if student exists: " + e.getMessage());
	    }
	    return false;
	}
	
	
	public List<Student> viewStudentById(int id) {
	    List<Student> studentList = new ArrayList<>();

	    String query = "SELECT * FROM Students WHERE rollno = ?";
	    try (Connection connection = DriverManager.getConnection(url, username, password);
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, id);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {  // Changed from if to while to handle potential multiple students
	            Student student = new Student(
	                resultSet.getInt("rollno"),
	                resultSet.getString("name"),
	                resultSet.getString("DOB"),
	                resultSet.getString("mobileno"),
	                resultSet.getString("email")
	            );
	            studentList.add(student);  // Add the student to the list
	        }
	    } catch (SQLException e) {
	        System.out.println("invalid input!");
	    }

	    return studentList;
	}


	// 1.add
	public void insertStudent(Student Student) {
		
		 if (studentExists(Student.getRollNo())) {
		        System.out.println("Student with roll number " + Student.getRollNo() + " already exists.");
		        return; // Or handle this case as needed
		    }

		try(Connection connection=DriverManager.getConnection(url,username,password)){
			String insertQuery="INSERT INTO Students(rollno,name,DOB,mobileno,email) VALUES(?,?,?,?,?)";
			try(PreparedStatement preparedStatement=connection.prepareStatement(insertQuery)){
				preparedStatement.setInt(1,Student.getRollNo());
				preparedStatement.setString(2,Student.getName());
				preparedStatement.setString(3,Student.getDOB());
				preparedStatement.setString(4,Student.getMobileno());
				preparedStatement.setString(5,Student.getEmail());
				
				int rowsAffected=preparedStatement.executeUpdate();
				if(rowsAffected>0) {
					//addStudent.notifications("student inserted successfully");
					System.out.println("student inserted successfully...");
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		//studentList.add(Student);
	
		//json();
		
	}

//	 2.view
	
	
	 public Student showStudentInfo(int studentId) throws SQLException {
	        String query = "SELECT * FROM students";
	        try (Connection connection = DriverManager.getConnection(url, username, password);
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, studentId);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                return new Student(
	                    resultSet.getInt("rollNo"),
	                    resultSet.getString("name"),
	                    resultSet.getString("DOB"),
	                    resultSet.getString("mobileno"),
	                    resultSet.getString("email")
	                );
	            } else {
	                return null;
	            }
	        }
	    }

	 public void updateStudent(Student student) {
	        if (student == null) {
	            System.out.println("Student object is null. Cannot update.");
	            return;
	        }

	        String updateQuery = "UPDATE Students SET name = ?, DOB = ?, mobileno = ?, email = ? WHERE rollNo = ?";
	        try (Connection connection = DriverManager.getConnection(url, username, password);
	             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
	            
	            preparedStatement.setString(1, student.getName());
	            preparedStatement.setString(2, student.getDOB());
	            preparedStatement.setString(3, student.getMobileno());
	            preparedStatement.setString(4, student.getEmail());
	            preparedStatement.setInt(5, student.getRollNo());

	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Update successful.");
	            } else {
	                System.out.println("No student found with Roll No: " + student.getRollNo());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


		public void setStudents(List<Student> updatedStudents) {
			this.studentList = updatedStudents;

		}
		public void deleteStudent(int rollNo) throws SQLException {
			try(Connection connection=DriverManager.getConnection(url,username,password)){
				String deleteQuery="DELETE FROM Students WHERE rollNo=?";
				try(PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery)){
					preparedStatement.setInt(1, rollNo);
					int rowsAffected=preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						//addStudent.notifications("Student deleted successfully");
						System.out.println("Student deleted successfully");
					}
					else {
						//addStudent.notifications("No student found with Roll No: "+rollNo);
						System.out.println("Student deleted not successfully");
					}
				}
			}
			catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
//
//		public List<Student> getStudents() {
//			// TODO Auto-generated method stub
		
//			return null;
//		}

		
		
		public List<Student> getStudents() {
			 List<Student> studentList = new ArrayList<Student>();
			 try(Connection connection=DriverManager.getConnection(url,username,password)){
				 String selectQuery="SELECT * FROM Students";
				 try(Statement statement=connection.createStatement();
						 ResultSet resultSet=statement.executeQuery(selectQuery)){
					 while(resultSet.next()) {
						 Student student=new Student(
							 resultSet.getInt("rollNo"),
								 resultSet.getString("name"),
								 resultSet.getString("DOB"),
								 resultSet.getString("mobileno"),
								 resultSet.getString("email")
								 );
//						 System.out.println(student.getName());
//							System.out.println(student.getRollNo());
//							System.out.println(student.getDOB());
//							System.out.println(student.getMobileno());
//							System.out.println(student.getEmail());
						 studentList.add(student);
					 }
				 }
			 }
			 catch(SQLException e) {
				 e.printStackTrace();
			 }
			
			return studentList;
		}


	
		 		}

		

