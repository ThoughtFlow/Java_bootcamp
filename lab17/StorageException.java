package lab17;

@SuppressWarnings("serial")
public class StorageException extends Exception{

	public StorageException(String errorMessage) {
		super("Storage exception: " + errorMessage);
	}
}
