package edu.byuh.cis.cs203.battleshipendgame2;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

public class MyTimer extends Handler {
	
	private List<TickListener> observers;
	
	public MyTimer() {
		observers = new ArrayList<TickListener>();
		sendMessageDelayed(obtainMessage(0), 100);
	}
	
	public void addListener(TickListener t) {
		observers.add(t);
	}
	
	public void removeListener(TickListener t) {
		observers.remove(t);
	}
	
	@Override
	public void handleMessage(Message msg) {
		sendMessageDelayed(obtainMessage(0), 100);
		for (TickListener t : observers) {
			t.tick();
		}
	}
}
