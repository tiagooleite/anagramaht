package ufcg.les.anagrama.exceptions;

public class AnagramaNaoExistenteException extends RuntimeException {
	private final static String MENSAGEM = "Essa palavra nao eh um anagrama: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7544954230200385910L;
	
	public AnagramaNaoExistenteException(String palavra){
		super(MENSAGEM + palavra);
	}

}
