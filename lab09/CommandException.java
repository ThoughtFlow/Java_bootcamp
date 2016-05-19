package lab09;

@SuppressWarnings("serial")
public class CommandException extends Exception {

	private final String command;
	private final String[] commandOptions;
	
	public CommandException(String errorMessage, String command, String... commandOptions)
	{
		super(errorMessage + " Command= " + command);
		this.command = command;
		this.commandOptions = commandOptions;
	}

	public CommandException(Exception exception, String errorMessage, String command, String... commandOptions)
	{
		super(errorMessage + " Command= " + command, exception);
		this.command = command;
		this.commandOptions = commandOptions;
	}

	public String getCommand() {
		return command;
	}

	public String[] getCommandOptions() {
		return commandOptions;
	}
}
