package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

import android.content.Context;

/*
 *************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com.
 *************************************************************************
 */

class TicTacToeGameEngine {
	
	final static char X = 'x'; 
	final static char O = 'o'; 
	final static char Q = '?'; // cell is empty when set to TicTacToeGameEngine.Q
	
	public static final boolean isDraw(TicTacToeGameState gs) {
		for(char c: gs.getBoardState()) {
			if ( c == TicTacToeGameEngine.Q )
				return false;
		}
		return true;
	}
	
	/*
	 *****************************************
	 * board state cell numbers
	 * 
	 * _____________
	 * | 0 | 1 | 2 |
	 * -------------
	 * | 3 | 4 | 5 |
	 * -------------
	 * | 6 | 7 | 8 |
	 * -------------
	 * 
	 ******************************************
	 */
	
	public static final TicTacToePlayer getWinner(TicTacToeGameState gs) {
		char[] board = gs.getBoardState();
		// 1st row X?
		if ( board[0] == TicTacToeGameEngine.X && 
			 board[1] == TicTacToeGameEngine.X &&
			 board[2] == TicTacToeGameEngine.X )
			return TicTacToePlayer.X;
		// 1 row O?
		else if ( board[0] == TicTacToeGameEngine.O &&
				  board[1] == TicTacToeGameEngine.O &&
				  board[2] == TicTacToeGameEngine.O )
			return TicTacToePlayer.O;
		// 2nd row X?
		else if ( board[3] == TicTacToeGameEngine.X && 
				  board[4] == TicTacToeGameEngine.X &&
				  board[5] == TicTacToeGameEngine.X )
			return TicTacToePlayer.X;
		// 2nd row O?
		else if ( board[3] == TicTacToeGameEngine.O && 
				  board[4] == TicTacToeGameEngine.O &&
				  board[5] == TicTacToeGameEngine.O )
			return TicTacToePlayer.O;
		// 3rd row X?
		else if ( board[6] == TicTacToeGameEngine.X &&
				  board[7] == TicTacToeGameEngine.X &&
				  board[8] == TicTacToeGameEngine.X )
			return TicTacToePlayer.X;
		// 3rd row O?
		else if ( board[6] == TicTacToeGameEngine.O &&
				  board[7] == TicTacToeGameEngine.O &&
				  board[8] == TicTacToeGameEngine.O )
			return TicTacToePlayer.O;
		// 1st column X?
		else if ( board[0] == TicTacToeGameEngine.X &&
				  board[3] == TicTacToeGameEngine.X &&
				  board[6] == TicTacToeGameEngine.X ) 
			return TicTacToePlayer.X;
		// 1st column O?
		else if ( board[0] == TicTacToeGameEngine.O &&
				  board[3] == TicTacToeGameEngine.O &&
				  board[6] == TicTacToeGameEngine.O ) 
			return TicTacToePlayer.O;
		// 2nd column X?
		else if ( board[1] == TicTacToeGameEngine.X &&
				  board[4] == TicTacToeGameEngine.X &&
				  board[7] == TicTacToeGameEngine.X ) 
			return TicTacToePlayer.X;
		// 2nd column O?
		else if ( board[1] == TicTacToeGameEngine.O &&
				  board[4] == TicTacToeGameEngine.O &&
				  board[7] == TicTacToeGameEngine.O ) 
			return TicTacToePlayer.O;
		// 3rd column X?
		else if ( board[2] == TicTacToeGameEngine.X &&
				  board[5] == TicTacToeGameEngine.X &&
				  board[8] == TicTacToeGameEngine.X ) 
			return TicTacToePlayer.X;
		// 3rd column O?
		else if ( board[2] == TicTacToeGameEngine.O &&
				  board[5] == TicTacToeGameEngine.O &&
				  board[8] == TicTacToeGameEngine.O ) 
			return TicTacToePlayer.O;
		// top right to bot left X?
		else if ( board[2] == TicTacToeGameEngine.X &&
				  board[4] == TicTacToeGameEngine.X &&
				  board[6] == TicTacToeGameEngine.X ) 
			return TicTacToePlayer.X;
		// top right to bot left O?
		else if ( board[2] == TicTacToeGameEngine.O &&
				  board[4] == TicTacToeGameEngine.O &&
				  board[6] == TicTacToeGameEngine.O ) 
			return TicTacToePlayer.O;
		// top left to bot right X?
		else if ( board[0] == TicTacToeGameEngine.X &&
				  board[4] == TicTacToeGameEngine.X &&
				  board[8] == TicTacToeGameEngine.X ) 
			return TicTacToePlayer.X;
		// top left to bot right O?
		else if ( board[0] == TicTacToeGameEngine.O &&
				  board[4] == TicTacToeGameEngine.O &&
				  board[8] == TicTacToeGameEngine.O ) 
			return TicTacToePlayer.O;
		else
			return null;
	}
	
	static final boolean isTerminal(TicTacToeGameState gs) {
		return isDraw(gs) || ( getWinner(gs) != null );
	}
	
	static final short getNextMinMaxMove(Context cntxt, TicTacToeGameState gs, TicTacToePlayer computer_player) {
		return TicTacToeContentProviderProxy
					.processBoardMoveUtils(
							TicTacToeContentProviderProxy.getBoardMoveUtilsByBoard(cntxt, gs), 
							gs.getPlayer(),
							computer_player
				);
	}
	
}
