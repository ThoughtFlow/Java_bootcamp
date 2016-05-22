package module07_io;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriterStreamTest {

	private static void printWithStream(Path filePath) throws IOException {
		try (PrintStream ps = new PrintStream(new FileOutputStream(filePath.toFile()))) {
				ps.println("Hey there!");
				ps.println("This is a test with print stream");
			}
	}
	
	private static void printWithWriter(Path filePath) throws IOException {
		try (PrintWriter pw = new PrintWriter(new FileWriter(filePath.toFile()))){
				pw.println("Hey there!");
				pw.println("This is a test with print stream");
			}
	}
	
	private static Path createFile(String fileToCreate) throws IOException {
		Path path = Paths.get(fileToCreate);
		Files.deleteIfExists(path);
		
		return Files.createFile(path);
	}
	
	public static void main(String[] args) {

		if (args.length == 2) {
			try {
				printWithStream(createFile(args[0]));
				printWithWriter(createFile(args[1]));
			}
			catch (IOException exception) {
				System.err.println("Error: " + exception);
				exception.printStackTrace();
			}
		}
		else {
			System.err.println("Usage: WriterStreamTest pathToStreamFile pathToWriterFile");
		}
	}
}
