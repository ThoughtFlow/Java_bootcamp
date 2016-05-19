package lab16;

import java.lang.reflect.Method;

public class AnnotationAnalyzer {

	public static void printAnnotation(Object object) {
		Method[] methods = object.getClass().getMethods();
		for (Method nextMethod : methods) {
			Transactional t = nextMethod.getAnnotation(Transactional.class);
			if (t != null) {
			   System.out.println(nextMethod.getName() + ": " + t.value() + " " + t.commitType());
			}
		}
	}
	
	public static void main(String[] args) {
		printAnnotation(new EmployeeDataAccessor());
		printAnnotation(new CustomerDataAccessor());
		printAnnotation(new OrderDataAccessor());
	}
	
}
