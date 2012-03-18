package ufcg.les.anagrama.persistence.dao;

import java.io.Serializable;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	private static final long serialVersionUID = 948167799996659593L;
	protected SQLiteDatabase bancoDeDados;
	protected GenericDAOSQLiteHelper bdHelper;
//	private Context contexto;

	public GenericDAOImpl(Context contexto) {
		//this.contexto = contexto;
		bdHelper = new GenericDAOSQLiteHelper(contexto);
	}

	public void open() throws SQLException {
		bancoDeDados = bdHelper.getWritableDatabase();
	}

	public void close() {
		bdHelper.close();
	}

	public void limpar() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_PALAVRAS);
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_USUARIOS);
		bdHelper.onCreate(bd);
	}
}
