package module09_collections_generics;

class Cat extends Animal {
	@Override
	public void speak() {
		System.out.println("meow");
	}

	@Override
	public void identify() {
		System.out.println("I'm a Cat.");
	}
}
