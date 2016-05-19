package module10_chat_server;

public interface MessageSink<T> {

	public void put(T message);
}
