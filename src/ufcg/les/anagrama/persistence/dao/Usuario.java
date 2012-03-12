package ufcg.les.anagrama.persistence.dao;

public class Usuario implements Comparable<Usuario> {
	private String nome;
	private int pontuacao;
	private long tempo;
	private static final long TEMPO_DEFAULT = 0;
	
	public Usuario(String nome, int pontuacao, long tempo) {
		setNome(nome);
		setPontucao(pontuacao);
		setTempo(tempo);
	}
	
	public Usuario(String nome, int pontuacao) {
		this(nome,pontuacao,TEMPO_DEFAULT);
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setPontucao(int pontuacao) {
		this.pontuacao = pontuacao; 
	}
	
	public int getPontucao() {
		return this.pontuacao;
	}
	
	public void setTempo(long tempo) {
		this.tempo = tempo;
	}
	
	public long getTempo() {
		return this.tempo;
	}

	public int compareTo(Usuario usuario) {
		if(usuario.getPontucao() == this.getPontucao()) {
			return (int) (this.getTempo() - usuario.getTempo());
		} 
		
		return this.getPontucao() - usuario.getPontucao(); 
	}
	
	@Override
	public String toString(){
		return "Nome: " + this.nome + "-> Pontucao: "+ this.pontuacao;
	}
}