package exceptions;

import java.util.ArrayList;
import java.util.List;

public class ExceptionsContainer {
	private List<String> errorMessages;

	public ExceptionsContainer() {
		this.errorMessages = new ArrayList<>();
	}

	public void addMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}
	
	public void addMessage(List<String> errorMessages) {
		this.errorMessages.addAll(errorMessages);
	}
	
	public List<String> getErrorMessages(){
		return this.errorMessages;
	}
	
	public boolean hasErrors() {
		return this.errorMessages.size() != 0;
	}

}
