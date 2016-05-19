package lab10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

public class CatCommand implements ShellCommand {

	private final CurrentPathProvider provider;
	private final BufferedWriter writer;

	public CatCommand(CurrentPathProvider provider, OutputStream writer) {
		this.provider = provider;
		this.writer = new BufferedWriter(new OutputStreamWriter(writer));
	}

	@Override
	public void doCommand(String... commandOptions) throws CommandException {

		if (commandOptions.length == 1) {
			
			Path sourcePath = provider.getCurrentPath().resolve(commandOptions[0]);

			try (BufferedReader fis = new BufferedReader(new InputStreamReader(
					new FileInputStream(sourcePath.toFile())))) {
				String line;
				
				while ((line = fis.readLine()) != null) {
					writer.write(line);
					writer.write("\n");
				}
				writer.flush();
			} catch (IOException e) {
				throw new CommandException(e, "Could not cat", "cat", commandOptions);
			}
		}
	}

}
