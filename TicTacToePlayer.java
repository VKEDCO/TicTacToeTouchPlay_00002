package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

/*
 ***********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ***********************************************************
 */

enum TicTacToePlayer {
	X('X'), O('O');
	char mName;
	TicTacToePlayer(char c) {
		mName = c;
	}
	public char getName() {
		return mName;
	}
}
