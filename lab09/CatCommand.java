package lab09;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

			try (BufferedReader fis = new BufferedReader(new InputStreamReader(
					new FileInputStream(sourcePath.toFile())))) {
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
