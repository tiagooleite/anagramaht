package ufcg.les.anagrama.activity;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.enummeration.Nivel;
import android.app.Activity;
import android.os.Bundle;

public class OpcoesActivity extends Activity {
	
	private Nivel nivel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_opcoes);
    }

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

}
