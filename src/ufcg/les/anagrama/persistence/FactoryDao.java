package ufcg.les.anagrama.persistence;

import ufcg.les.anagrama.persistence.dao.RankingDAO;

public class FactoryDao {
	
	private static RankingDAO instance;
	
	private FactoryDao() {
	}
	
	public static synchronized RankingDAO getRankingDaoInstance() {
		if (instance == null) {
			instance = new RankingDAO();
		}
		return instance;
	}

}
