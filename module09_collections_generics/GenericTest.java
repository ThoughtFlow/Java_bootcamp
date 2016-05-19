package module09_collections_generics;

public class GenericTest {

	@SuppressWarnings("unused")
	public static void main(String... args) {

		// The idea behind generics is to eliminate type surprises at runtime. 
		// You can prove at compilation that your program will not encounter class cast exceptions.
		// Static typing is great but there is a price to pay. Generics are non-intuitive.
		// Furthermore, sometimes, you don't care what the specific type is because you are only interested in processing the generic class without interacting with its parameterized type.
		// And this is where the fun begins!
		
		
	    // While this works (regular polymorphism we all know and love)
		{
		   B typeB = new B();
		   A typeA = typeB;
		}
		
		// The same thinking does not work with parameterized types (because by default, parameterized types are not covariant)
		{
		   G<B> gOfB = new G<>();
//		   G<A> gOfA = gOfB; //fails compilation
		}
		
		// To achieved the same thing, we need to make them covariant with bounded wildcards
		{
		   G<? extends B> gOfExtendsB = new G<B>();
		   G<? extends A> gOfExtendsA = gOfExtendsB;
		   
		   // or this to be unbounded
		   G<?> gOfWildcard = gOfExtendsB;
		   
		   // Or this
		   G<?> gOfWildcard2 = gOfExtendsA;
		   
		   // But not this
//			G<? extends B> gOfExtendsB = new G<? extends B>() // cannot instantiate a wildcard type
		}

		// But covariant parameterized types limit what we can do 
		{
			// One set of rules for extends
			G<? extends B> gOfExtendsB = new G<B>();
//			gOfExtendsB.setT(new B()); // Can't use any method that expects a concrete type because then no guarantees could be made about the parameterized type
			B typeB = gOfExtendsB.get(); // But this is OK because we know for sure is that it's AT LEAST a B.
			Object typeO = gOfExtendsB.get(); // Upcasting works as expected.
			
			// Another set of rules for super
			G<? super B> gOfSuperB = new G<B>();
			gOfSuperB.setT(new B()); // Only the super type (B) works because we're sure that it can substitute for any type for G.
//			gOfSuperB.setT(new A()); // This won't work because it can't substitute for B
			typeO = gOfSuperB.get(); // But can only be assigned to Object
		}
	}
	
	private static class A {
		
	}
	
	private static class B extends A {
		
	}
	
	private static class G<T> {
		private T t;
		
		public void setT(T t) {
			this.t = t;
		}
		
		public T get() {
			return this.t;
		}
	}
}
