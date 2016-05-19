package lab10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LocalShell {
	
	public void runShell(String rootDir, InputStream input, OutputStream output) {

		CdCommand cdCommand = new CdCommand(rootDir);
		LsCommand lsCommand = new LsCommand(cdCommand, output);
		CpCommand cpCommand = new CpCommand(cdCommand);
		RmCommand rmCommand = new RmCommand(cdCommand);
		CatCommand catCommand = new CatCommand(cdCommand, output);
		PwdCommand pwdCommand = new PwdCommand(cdCommand, output);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));)
		 {
			String commandLine;
			writer.write(cdCommand.getCurrentPath() +">> ");
			writer.flush();

			while ((commandLine = reader.readLine()) != null && !"quit".equals(commandLine)) {
				try {
					String[] commandLineArray = commandLine.split(" ");
					String command = commandLineArray[0];
					String[] commandOptions = new String[commandLineArray.length - 1];
					System.arraycopy(commandLineArray, 1, commandOptions, 0, commandLineArray.length - 1);
					
					System.out.println(commandLine);

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
						writer.write("Unsupported command: " + command);
					}
				} catch (CommandException e) {
					writer.write("Error occurred: " + e.getMessage());
				}
				writer.write("\n");
				writer.write(cdCommand.getCurrentPath() +">> ");
				writer.flush();
			}
		} catch (IOException e) {
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("You have quit the shell");
	}
}
