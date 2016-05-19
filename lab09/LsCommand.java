package lab09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class LsCommand implements ShellCommand {

	private final CurrentPathProvider provider;

	/**
	 * 
	 * @param provider
	 */
	public LsCommand(CurrentPathProvider provider) {
		this.provider = provider;
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
				System.out.println(iterator.next().getFileName());
			}
		} catch (IOException e) {
			throw new CommandException(e, e.getMessage(), "ls", commandOptions);
		}
	}

}
