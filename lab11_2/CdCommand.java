package lab11_2;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class CdCommand implements ShellCommand, CurrentPathProvider {

	private Path currentPath;
	
	public CdCommand(String root) {
		currentPath = FileSystems.getDefault().getPath(root);
	}

	@Override
	public void doCommand(String... commandOptions) throws CommandException {
		if (commandOptions.length > 0)
		{
			Path newPath = currentPath.resolve(commandOptions[0]).normalize();
			if (newPath.toFile().isDirectory()) {
				currentPath = newPath;
			}
			else {
				throw new CommandException("No such directory: " + commandOptions[0], "cd", commandOptions);
			}
		}
		else
		{
			throw new CommandException("No directory specified", "cd", commandOptions);
		}
	}
	
	@Override
	public Path getCurrentPath()
	{
		return currentPath;
	}

}