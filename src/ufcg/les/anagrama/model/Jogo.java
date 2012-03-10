package ufcg.les.anagrama.model;

import ufcg.les.anagrama.enummeration.Nivel;

public class Jogo {
	
	private String nomeJogador;
	private int pontuacao;
	private Nivel nivel;
	
	public Jogo(String nomeJogador, Nivel nivel) {
		this.nomeJogador = nomeJogador;
		this.nivel = nivel;
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
	
	
	

}
