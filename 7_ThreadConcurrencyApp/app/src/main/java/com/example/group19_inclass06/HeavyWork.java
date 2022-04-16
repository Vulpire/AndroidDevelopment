package com.example.group19_inclass06;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Random;

    /*
        Assignment: In class #6
        File Name: Group#19_InClass06
        Name: Nicholas Wofford and Sierra Cubero
     */

public class HeavyWork implements Runnable{
	static final String PROGRESS_KEY = "PROGRESS";
	static final int PROGRESS_KEY2 = 0X00;
	public static final long DELAY_MILLI_SECS = 2000;
	int complexity;
	Handler handler;

	public HeavyWork(int i, Handler handler){
		this.complexity = i;
		this.handler = handler;
	}

	public static double getNumber(){
		addSomeDelay(DELAY_MILLI_SECS);
		Random rand = new Random();
		return rand.nextDouble();
	}

	private static void addSomeDelay(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		//getNumber();
		for(int i=0; i<this.complexity; i++){
			Bundle bundle = new Bundle();
			Message message = new Message();
			bundle.putDouble(PROGRESS_KEY,getNumber());
			message.setData(bundle);
			handler.sendMessage(message);

		}
	}
}