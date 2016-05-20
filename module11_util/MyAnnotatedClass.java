package module11_util;

@MyAnnotation("A class annotation")
public class MyAnnotatedClass {

	@MyAnnotation("A field annotation")
	private
	int myInt = 0;

	@MyAnnotation("A method annotation")
	public void myMethod() {

	}

	public static void main(String[] args) {

		// Getting class annotations
		MyAnnotation classAnnotation = MyAnnotatedClass.class.getAnnotation(MyAnnotation.class);
		System.out.println(classAnnotation.value());

		// Getting method annotations
		try {
			MyAnnotation methodAnnotation = MyAnnotatedClass.class.getDeclaredMethod("myMethod").getAnnotation(MyAnnotation.class);
			System.out.println(methodAnnotation.value());
		} catch (NoSuchMethodException e) {
			System.err.println("Method does not exist");
		} catch (SecurityException e) {
			System.err.println("Introspection forbidden");
		}

		// Getting field annotations
		try {
			MyAnnotation fieldAnnotation = MyAnnotatedClass.class.getDeclaredField("myInt").getAnnotation(MyAnnotation.class);
			System.out.println(fieldAnnotation.value());
		} catch (NoSuchFieldException e) {
			System.err.println("Field does not exist");
		} catch (SecurityException e) {
			System.err.println("Introspection forbidden");
		}
	}
}
