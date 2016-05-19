package lab09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalShell {
	
	public void runShell(String rootDir) {

		CdCommand cdCommand = new CdCommand(rootDir);
		LsCommand lsCommand = new LsCommand(cdCommand);
		CpCommand cpCommand = new CpCommand(cdCommand);
		RmCommand rmCommand = new RmCommand(cdCommand);
		CatCommand catCommand = new CatCommand(cdCommand);
		PwdCommand pwdCommand = new PwdCommand(cdCommand);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			String commandLine;
			System.out.print(cdCommand.getCurrentPath() +">> ");

			while ((commandLine = reader.readLine()) != null && !"quit".equals(commandLine)) {
				try {
					String[] commandLineArray = commandLine.split(" ");
					String command = commandLineArray[0];
					String[] commandOptions = new String[commandLineArray.length - 1];
					System.arraycopy(commandLineArray, 1, commandOptions, 0, commandLineArray.length - 1);

					switch (command) {
					case "cd":
						cdCommand.doCommand(commandOptions);
						break;
					case "ls":
						lsCommand.doCommand(commandOptions);
						break;
					case "cp":
						cpCommand.doCommand(commandOptions);
						break;
					case "rm":
						rmCommand.doCommand(commandOptions);
						break;
					case "cat":
						catCommand.doCommand(commandOptions);
						break;			
					case "pwd":
						pwdCommand.doCommand(commandOptions);
						break;									
					default:
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
