package lab09;

public class Shell {

	public static void main(String... args) {
		if (args.length != 1) {
			System.err.println("Usage: Shell rootDirectory");
		} else {
			LocalShell localShell = new LocalShell();
			localShell.runShell(args[0]);
		}
	}
}
