package lab09_2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class FindFile {

	/**
	 * Recursively search through the directories to search for the file name.
	 * 
	 * @param parentPath The parent directory.
	 * @param fileToFind The unpathed file to find.
	 * @return The found path of the file or null if not found.
	 * @throws IOException Thrown if any interaction with the files throws an error.
	 */
	private static Path findFirstOccurence(Path parentPath, String fileToFind) throws IOException {
		Path foundPath = null;

		// Can throw IOException
		Iterator<Path> iterator = Files.list(parentPath).iterator();

		while (iterator.hasNext() && foundPath == null) {
			Path nextPath = iterator.next();

			// Search through the directory recursively
			if (nextPath.toFile().isDirectory()) {
				foundPath = findFirstOccurence(nextPath, fileToFind);
			} else {
				// Compare file names without the root
				foundPath = nextPath.getFileName().toString().equals(fileToFind) ? nextPath : null;
			}
		}

		return foundPath;
	}

	public static void main(String[] args)  {
		if (args.length == 2) {
			 Path rootPath = FileSystems.getDefault().getPath(args[0]);
			
			// Can also use this shortcut method
			// Path rootPath = Paths.get(args[0]);

			try {
				Path foundPath = findFirstOccurence(rootPath, args[1]);
				if (foundPath != null) {
					System.out.println(foundPath.toAbsolutePath().normalize());
				}
			} catch (IOException exception) {
				System.err.println("Error occured: " + exception.getMessage());
			}
		} else {
			System.err.println("Usage find rootDir fileToFind");
		}
	}
}
