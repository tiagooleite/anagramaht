package ufcg.les.anagrama.exceptions;

public class PalavraJaEncontradaException extends RuntimeException {
	private final static String MENSAGEM = "Essa palavra ja foi encontrada: ";
	
	private static final long serialVersionUID = -869826511138338983L;
	
	public PalavraJaEncontradaException(String palavra){
		super(MENSAGEM + palavra);
	}

}
