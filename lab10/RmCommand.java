package lab10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RmCommand implements ShellCommand {

	private final CurrentPathProvider provider;
	
	public RmCommand(CdCommand provider) {
		this.provider = provider;
	}
	
	@Override
	public void doCommand(String... commandOptions) throws CommandException {
		if (commandOptions.length == 1) {
			Path sourcePath = provider.getCurrentPath().resolve(commandOptions[0]);

			try {
				Files.delete(sourcePath);
			} catch (IOException e) {
				throw new CommandException(e, e.getMessage(), "rm", commandOptions);
			}
		} else {
			throw new CommandException("No file to delete specified", "rm", commandOptions);
		}
	}

}
