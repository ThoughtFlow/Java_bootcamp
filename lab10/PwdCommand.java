package lab10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PwdCommand implements ShellCommand {

	private final CurrentPathProvider currentPathProvider;
	private final BufferedWriter writer;

	public PwdCommand(CurrentPathProvider provider, OutputStream writer) {
		super();
		this.currentPathProvider = provider;
		this.writer = new BufferedWriter(new OutputStreamWriter(writer));		
	}

	@Override
	public void doCommand(String... commandOptions) throws CommandException {
		try {
			writer.write(currentPathProvider.getCurrentPath().toAbsolutePath().toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
