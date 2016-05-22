package lab09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class CatCommand implements ShellCommand {

	private final CurrentPathProvider provider;

	public CatCommand(CurrentPathProvider provider) {
		this.provider = provider;
	}

	@Override
	public void doCommand(String... commandOptions) throws CommandException {

		if (commandOptions.length == 1) {
			
			Path sourcePath = provider.getCurrentPath().resolve(commandOptions[0]);

			try (BufferedReader fis = new BufferedReader(new FileReader(sourcePath.toFile()))) {
				String line;
				
				while ((line = fis.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				throw new CommandException(e, "Could not cat", "cat", commandOptions);
			}
		}
	}

}
