package ufcg.les.anagrama.persistence.dao;

import java.util.ArrayList;
import java.util.List;

public class GenericDAOImpl<T> implements GenericDAO<T> {
	
	private List<T> objetos = new ArrayList<T>(); 

	public void inserirObjeto(T obj) {
		objetos.add(obj);
		// TODO Auto-generated method stub
		
	}

	public void deletarObjeto(Long idObj) {
		// TODO Auto-generated method stub
		
	}

	public List<T> listarObjetos() {
		return objetos;
	}

	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub
	}

}
