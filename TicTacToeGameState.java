package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

/*
 *************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com.
 *************************************************************************
 */

public class TicTacToeGameState {
	
	protected char[] mBoardState = null;
	protected TicTacToePlayer mPlayer = null;
	
	TicTacToeGameState(TicTacToePlayer player) {
		mPlayer = player;
		mBoardState = new char[] {
				TicTacToeGameEngine.Q, TicTacToeGameEngine.Q, TicTacToeGameEngine.Q,
				TicTacToeGameEngine.Q, TicTacToeGameEngine.Q, TicTacToeGameEngine.Q,
				TicTacToeGameEngine.Q, TicTacToeGameEngine.Q, TicTacToeGameEngine.Q
		};
	}
	
	TicTacToeGameState(char[] board_state, TicTacToePlayer player) {
		mBoardState = board_state;
		mPlayer     = player;
	}
	
	final void setBoardCell(short cell_number, char cell_value) {
		if ( cell_number >= 0 && cell_number < 9 &&
			(cell_value == TicTacToeGameEngine.X || cell_value == TicTacToeGameEngine.O ||
					cell_value == TicTacToeGameEngine.Q ) ) {
			mBoardState[cell_number] = cell_value;
		}
	}
	
	final void setBoardState(char[] board) {
		if ( board.length == 8 ) {
			for(char c: board) {
				if ( !( c == TicTacToeGameEngine.X || c == TicTacToeGameEngine.O || 
					    c == TicTacToeGameEngine.Q ) )
					return;
			}
			mBoardState = board;
		}
	}
	
	final void setPlayer(TicTacToePlayer player) {
		mPlayer = player;
	}
	
	final void switchPlayer() {
		if ( mPlayer != null ) {
			if ( mPlayer == TicTacToePlayer.X )
				mPlayer = TicTacToePlayer.O;
			else if ( mPlayer == TicTacToePlayer.O )
				mPlayer = TicTacToePlayer.X;
			else
				mPlayer = null;
		}
	}
	
	final char[] getBoardState() {
		return mBoardState;
	}
	
	final TicTacToePlayer getPlayer() {
		return mPlayer;
	}
	
	final void play(short cell_number) {
		if ( mPlayer == TicTacToePlayer.O ) {
			setBoardCell(cell_number, TicTacToeGameEngine.O);
			switchPlayer();
		}
		else if ( mPlayer == TicTacToePlayer.X ) {
			setBoardCell(cell_number, TicTacToeGameEngine.X);
			switchPlayer();
		}
	}
	
	final void restartGameState() {
		for(short i = 0; i < mBoardState.length; i++) {
			setBoardCell(i, TicTacToeGameEngine.Q);
		}
		mPlayer = TicTacToePlayer.X;
	}
	
	final boolean isRestarted() {
		if ( mPlayer != TicTacToePlayer.X ) return false;
		for(char c: mBoardState) {
			if ( c != TicTacToeGameEngine.Q )
				return false;
		}
		return true;
	} 
}
