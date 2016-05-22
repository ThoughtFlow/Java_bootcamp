package lab11_2;

public class Shell {

	public static void main(String... args) {
		if (args.length != 1) {
			System.err.println("Usage: Shell rootDirectory");
		} else {
			LocalShell localShell = new LocalShell(args[0]);
			localShell.runShell();
		}
	}
}
