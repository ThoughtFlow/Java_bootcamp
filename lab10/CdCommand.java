package lab10;

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
			currentPath= currentPath.resolve(commandOptions[0]).normalize();
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
