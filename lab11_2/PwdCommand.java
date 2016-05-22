package lab11_2;

public class PwdCommand implements ShellCommand {

	private final CurrentPathProvider currentPathProvider;

	public PwdCommand(CurrentPathProvider provider) {
		super();
		this.currentPathProvider = provider;
	}

	@Override
	public void doCommand(String... commandOptions) throws CommandException {
		System.out.println(currentPathProvider.getCurrentPath().toAbsolutePath());
	}
	
	
}
