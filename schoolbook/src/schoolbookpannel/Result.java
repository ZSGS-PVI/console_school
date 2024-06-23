package schoolbookpannel;

import schoolbookpannel.dto.Student;

public interface Result {

	public void addStudentInfo();
	public void  signup(Student student);
	public void viewStudent() ;
	public void updateStudent() ;
	public void deleteStudent();
	public void onSuccess() ;
	public void notifications(String string);
	public void showError(String errorMessage);
	public void studentlist(Student student);
	
}
