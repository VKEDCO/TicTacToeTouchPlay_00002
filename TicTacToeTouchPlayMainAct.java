package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

/*
 *************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com.
 *************************************************************************
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class TicTacToeTouchPlayMainAct extends Activity {
	
	TicTacToeTouchPlayApp mTTTApp = null;
	TicTacToeBoardSketcher mTouchImageSketcher = null;
	final static String LOGTAG = TicTacToeTouchPlayMainAct.class.getSimpleName() + "_LOGTAG";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_cp_main_act);
        setTitle(R.string.title_activity_tic_tac_toe_cp_main);
        mTouchImageSketcher = (TicTacToeBoardSketcher) this.findViewById(R.id.sketcher);
        mTTTApp = (TicTacToeTouchPlayApp) this.getApplication();
        mTouchImageSketcher.setTicTacToeApp(mTTTApp);
        mTTTApp.setComputerPlayer(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tic_tac_toe_touch_play_menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
		case R.id.item_play_x:
			mTTTApp.restartGameState();
			mTTTApp.setComputerPlayer(TicTacToePlayer.O);
			mTouchImageSketcher.setTicTacToeApp(mTTTApp);
			Log.d(LOGTAG, "human player plays X");
			return true;
		case R.id.item_play_o:
			mTTTApp.restartGameState();
			mTTTApp.setComputerPlayer(TicTacToePlayer.X);
			short cell = TicTacToeGameEngine.getNextMinMaxMove(mTTTApp, mTTTApp.getGameState(), mTTTApp.getComputerPlayer());
			mTouchImageSketcher.setTicTacToeApp(mTTTApp);
			if ( cell != -1 ) mTTTApp.getGameState().play(cell);
			Log.d(LOGTAG, "human player plays O");
			return true;
		case R.id.item_clear_board: 
			mTTTApp.restartGameState();
			mTouchImageSketcher.setTicTacToeApp(mTTTApp);
			mTTTApp.setComputerPlayer(null);
			return true;
		default: return true; 
		}
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		if ( mTouchImageSketcher != null ) {
			mTouchImageSketcher.disableDrawing();
			mTouchImageSketcher = null;
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if ( mTouchImageSketcher != null ) {
			mTouchImageSketcher.disableDrawing();
			mTouchImageSketcher = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if ( mTouchImageSketcher != null ) {
			mTouchImageSketcher.disableDrawing();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if ( mTouchImageSketcher != null ) {
			mTouchImageSketcher.enableDrawing();
		}
		else {
			mTouchImageSketcher = (TicTacToeBoardSketcher) this.findViewById(R.id.sketcher);
			mTouchImageSketcher.enableDrawing();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if ( mTouchImageSketcher != null ) {
			mTouchImageSketcher.disableDrawing();
		}
	}
}
