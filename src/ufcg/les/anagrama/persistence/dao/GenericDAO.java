package ufcg.les.anagrama.persistence.dao;

import java.util.List;

public interface GenericDAO<T> {
	
	public void inserirObjeto(T obj);
	
	public void  deletarObjeto(Long idObj);
	
	public List<T> listarObjetos();
	
	public void atualizarObjeto(Long idObj);

}
