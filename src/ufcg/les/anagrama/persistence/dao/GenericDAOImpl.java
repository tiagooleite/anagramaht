package ufcg.les.anagrama.persistence.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GenericDAOImpl implements GenericDAO<Palavras> {
	
	private SQLiteDatabase bancoDeDados;
	private GenericDAOSQLiteHelper bdHelper;
	private String[] todosAnagramasColuna = { GenericDAOSQLiteHelper.COLUNA_ID,
			GenericDAOSQLiteHelper.COLUNA_ANAGRAMA };

	public GenericDAOImpl(Context contexto) {
		bdHelper = new GenericDAOSQLiteHelper(contexto);
	}

	public void open() throws SQLException {
		bancoDeDados = bdHelper.getWritableDatabase();
	}

	public void close() {
		bdHelper.close();
	}

	public void inserirListaDeStrings(List<String> obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : obj) {
		    try {
				out.writeUTF(element);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		byte[] bytes = baos.toByteArray();

		ContentValues values = new ContentValues();
		values.put(GenericDAOSQLiteHelper.COLUNA_ANAGRAMA, bytes);
		bancoDeDados.insert(GenericDAOSQLiteHelper.TABELA_ANAGRAMAS, null, values);
	}

	public void deletarObjeto(Long idObj) {
		bancoDeDados.delete(GenericDAOSQLiteHelper.TABELA_ANAGRAMAS, GenericDAOSQLiteHelper.COLUNA_ID
				+ " = " + idObj, null);
	}

	public List<Palavras> listarObjetos() {
		List<Palavras> palavras = new ArrayList<Palavras>();
		
		Cursor cursor = bancoDeDados.query(GenericDAOSQLiteHelper.TABELA_ANAGRAMAS,
				todosAnagramasColuna, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Palavras palavra = cursorParaPalavra(cursor);
			palavras.add(palavra);
			cursor.moveToNext();
		}
		
		cursor.close();
		return palavras;
	}

	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub
	}
	
	private Palavras cursorParaPalavra(Cursor cursor) {
		Palavras palavras = new Palavras();
		
		palavras.setId(cursor.getLong(0));
		
		byte[] bytes = cursor.getBlob(1);
		palavras.setPalavras(getListaDePalavras(bytes));
		
		return palavras;
	}

	private List<String> getListaDePalavras(byte[] bytes) {
		List<String> palavras = new ArrayList<String>();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream in = new DataInputStream(bais);
		
		try {
			while (in.available() > 0) {
			    String element = in.readUTF();
			    palavras.add(element);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return palavras;
	}

	public void inserirObjeto(String obj) {
		// TODO Auto-generated method stub
	}

	public void limpar() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_ANAGRAMAS);
		bdHelper.onCreate(bd);
	}
}
