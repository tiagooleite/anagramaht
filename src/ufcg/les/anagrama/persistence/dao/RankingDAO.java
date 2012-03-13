package ufcg.les.anagrama.persistence.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;

public class RankingDAO implements Serializable {

	private static final long serialVersionUID = -7056832528147460984L;
	
	private List<Usuario> listUsuario;
	private static final int TAMANHO_DO_RANKING = 5; 
	private Activity activity;
	
	public RankingDAO() {
		listUsuario = new ArrayList<Usuario>(TAMANHO_DO_RANKING);
		carregaRanking();
	}
	
	public RankingDAO(Activity activity) {
		this();
		this.activity = activity;
	}

	public void carregaRanking() {
		listUsuario	= carregaRankingDefault(); // TODO Resgatar do banco de dados
		Collections.sort(listUsuario);
	}
	
	public List<Usuario> getRanking() {
		return listUsuario;
	}
	
	public boolean addUsuario(Usuario usuario) {
		listUsuario.add(usuario);
		Collections.sort(listUsuario);
		truncarRanking();
		
		return listUsuario.contains(usuario);
	}
	
	private void truncarRanking() {
		if(listUsuario.size() > TAMANHO_DO_RANKING) {
			listUsuario.remove(listUsuario.size()-1);
		}
	}
	
	public String toString() {
		return listUsuario.toString();
	}
	
	@Deprecated
	public List<Usuario> carregaRankingDefault() {
		List<Usuario> listaUsuariosDefault = new ArrayList<Usuario>(TAMANHO_DO_RANKING);
		for (int i = 0; i < TAMANHO_DO_RANKING; i++) {
			listaUsuariosDefault.add(new Usuario("Default0"+i, i * 10));
		}
		
		return listaUsuariosDefault;
	}
}