package lab10;

public interface ShellCommand {
	
	/**
	 * This is where you implement your own command.
	 * 
	 * @param commandOptions This is for your command options
	 * @throws CommandException Thrown when the command fails for any reason.
	 */
	public void doCommand(String... commandOptions) throws CommandException; 
}
