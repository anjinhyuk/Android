package edu.byuh.cis.cs203.bw4;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private GameView gv;
	//private ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//iv = new ImageView(this);
		gv = new GameView(this);
		setContentView(gv);
	}


}
