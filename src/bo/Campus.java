package bo;

import java.io.Serializable;

public class Campus extends Lieu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;

	public Campus(String code) {
		super();
		this.code = code;
	}
	
	public Campus() {
		super();
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
