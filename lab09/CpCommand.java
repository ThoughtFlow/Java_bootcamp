package lab09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CpCommand implements ShellCommand {

	private final CurrentPathProvider provider;

	public CpCommand(CurrentPathProvider provider) {
		this.provider = provider;
	}

	@Override
	public void doCommand(String... commandOptions) throws CommandException {
		if (commandOptions.length == 2) {
			Path sourcePath = provider.getCurrentPath().resolve(
					commandOptions[0]);
			Path destinationPath = provider.getCurrentPath().resolve(
					commandOptions[1]);

			try {
				Files.copy(sourcePath, destinationPath,
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new CommandException(e, e.getMessage(), "cp", commandOptions);
			}
		} else {
			throw new CommandException("No ource and/or destination files", "cp", commandOptions);
		}
	}

}
