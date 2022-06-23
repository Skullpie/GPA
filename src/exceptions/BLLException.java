package exceptions;

import java.util.ArrayList;
import java.util.List;

public class BLLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> messages = new ArrayList<>();
	
	public BLLException() {
		super();
	}
	
	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	public void addMessage(List<String> messages) {
		this.messages.addAll(messages);
	}
	
	/**
	 * 
	 * @return Messages
	 */
	public List<String> getMessages() {
		return this.messages;
	}
	//TODO redéfinir méthode to string qui parcourerait la liste des messages => Concaténation pour avoir un String en sortie
	
}
