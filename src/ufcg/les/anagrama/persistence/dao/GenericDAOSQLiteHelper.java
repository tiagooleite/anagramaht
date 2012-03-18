package ufcg.les.anagrama.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAOSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABELA_ANAGRAMAS = "anagramas";
	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_ANAGRAMA = "anagrama";

	private static final String NOME_BD = "anagramaht.db";
	private static final int VERSAO_DB = 1;

	private static final String CREATE_DB = "create table "
			+ TABELA_ANAGRAMAS + "( " + COLUNA_ID
			+ " integer primary key autoincrement, " + COLUNA_ANAGRAMA
			+ " BLOB);";

	public GenericDAOSQLiteHelper(Context context) {
		super(context, NOME_BD, null, VERSAO_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_DB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_ANAGRAMAS);
		onCreate(db);
	}
}