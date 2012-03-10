package ufcg.les.anagrama.model;

import java.util.List;
import java.util.Random;

import ufcg.les.anagrama.enummeration.Nivel;
import ufcg.les.anagrama.persistence.dao.PalavrasDAO;
import ufcg.les.anagrama.util.GeradorStrings;

public class Jogo {
	
	private String nomeJogador;
	private int pontuacao;
	private Nivel nivel = Nivel.NORMAL;
	private PalavrasDAO palavrasDAO;
	private String palavraEmbaralhada;
	private List<String> anagramas;
	
	public Jogo(String nomeJogador) {
		this.nomeJogador = nomeJogador;
		palavrasDAO = new PalavrasDAO();
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	public String getPalavraEmbaralhada() {
		return this.palavraEmbaralhada;
	}
	
	public List<String> getAnagramas() {
		return this.anagramas;
	}
	
	public void carregarNovoAnagrama(){
		this.anagramas = getListaAnagramasAleatoria(palavrasDAO.getPalavrasPorNivel(getNivel()));
		this.palavraEmbaralhada = getPalavraEmbaralhada(this.anagramas);
	}
	
	private String getPalavraEmbaralhada(List<String> anagramas) {
		return GeradorStrings.embaralhaPalavra(anagramas.get(0));
	}
	
	private List<String> getListaAnagramasAleatoria(List<List<String>> palavrasPorNivel) {
		int tamanho = palavrasPorNivel.size();
		Random gerador = new Random();
		int tamanhoAleatorio = gerador.nextInt(tamanho);
		return palavrasPorNivel.get(tamanhoAleatorio);		
	}
}
