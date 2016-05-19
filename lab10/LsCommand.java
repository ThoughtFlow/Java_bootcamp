package lab10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class LsCommand implements ShellCommand {

	private final CurrentPathProvider provider;
	private final BufferedWriter writer;

	/**
	 * 
	 * @param provider
	 */
	public LsCommand(CurrentPathProvider provider, OutputStream writer) {
		this.provider = provider;
		this.writer = new BufferedWriter(new OutputStreamWriter(writer));
	}

	/**
	 * @inheritDoc
	 * 
	 * Does list files.
	 */
	@Override
	public void doCommand(String... commandOptions) throws CommandException {
		Path path = provider.getCurrentPath();

		try {
			Iterator<Path> iterator = Files.list(path).iterator();
			while (iterator.hasNext()) {
				writer.write(iterator.next().getFileName().toString());
				writer.write("\n");
			}
			writer.flush();
		} catch (IOException e) {
			throw new CommandException(e, e.getMessage(), "ls", commandOptions);
		}
	}

}
