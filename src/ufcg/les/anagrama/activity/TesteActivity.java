package ufcg.les.anagrama.activity;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.util.Constantes;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TesteActivity extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        TextView t = new TextView(getApplicationContext());
	        t.setText(Constantes.MENSAGEM_AJUDA);
	        setContentView(t);
	    }

}
