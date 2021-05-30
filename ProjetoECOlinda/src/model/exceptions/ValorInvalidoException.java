package model.exceptions;

public class ValorInvalidoException extends IllegalArgumentException{

	private static final long serialVersionUID = 1L;
	
	public ValorInvalidoException(String msg) {
		super(msg);
	}

}
