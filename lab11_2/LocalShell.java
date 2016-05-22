package lab11_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LocalShell {

	private final Map<String, ShellCommand> commands = new HashMap<>();
	private final CdCommand cdCommand;
	
	public LocalShell(String rootDir) {
		cdCommand = new CdCommand(rootDir);
		commands.put("cd", cdCommand);
		commands.put("ls", new LsCommand(cdCommand));
		commands.put("cp", new CpCommand(cdCommand));
		commands.put("rm", new RmCommand(cdCommand));
		commands.put("cat", new CatCommand(cdCommand));
		commands.put("pwd", new PwdCommand(cdCommand));
	}
	
	public void runShell() {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String commandLine;
			System.out.print(cdCommand.getCurrentPath() +">> ");

			while ((commandLine = reader.readLine()) != null && !"quit".equals(commandLine)) {
				try {
					String[] commandLineArray = commandLine.split(" ");
					String command = commandLineArray[0];
					String[] commandOptions = new String[commandLineArray.length - 1];
					System.arraycopy(commandLineArray, 1, commandOptions, 0, commandLineArray.length - 1);

					ShellCommand shellCommand = commands.get(command);
					if (shellCommand != null) {
						shellCommand.doCommand(commandOptions);
					}
					else {
						System.out.println("Unsupported command: " + command);
					}
				} catch (CommandException e) {
					System.out.println("Error occurred: " + e.getMessage());
				}
				System.out.print(cdCommand.getCurrentPath() +">> ");
			}
		} catch (IOException e) {
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("You have quit the shell");
	}

}
