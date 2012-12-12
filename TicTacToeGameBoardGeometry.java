package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

/*
 ***********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ***********************************************************
 */

class TicTacToeGameBoardGeometry {
	
	// top left X of the board drawn on Canvas
	private short mTLX = 0;
	// top left Y of the board drawn on Canvas
	private short mTLY = 0;
	// bottom right X of the board drawn on Canvas
	private short mBRX = 0;
	// bottom right Y of the board drawn on Canvas
	private short mBRY = 0;
	// board cell dim (cells are squares)
	private short mCellDim = 0;
	final static short NUM_CELLS = 3;
	
	TicTacToeGameBoardGeometry(short tlx, short tly, short cell_dim) {
		mTLX = tlx; 
		mTLY = tly;
		mCellDim = cell_dim; 
		mBRX = tlx;
		mBRX += NUM_CELLS * mCellDim; 
		mBRY = tly;
		mBRY += NUM_CELLS * mCellDim;
	}
	
	final short getTLX() {
		return mTLX;
	}
	
	final short getTLY() {
		return mTLY;
	}
	
	final short getTRX() {
		short trx = mTLX;
		trx += NUM_CELLS * mCellDim;
		return trx;
	}
	
	final short getTRY() {
		return mTLY;
	}
	
	final short getBLX() {
		return mTLX;
	}
	
	final short getBLY() {
		short bly = mTLY;
		bly += NUM_CELLS * mCellDim;
		return bly;
	}
	
	final short getBRX() {
		return mBRX;
	}
	
	final short getBRY() {
		return mBRY;
	}
	
	final short getCellDim() {
		return mCellDim;
	}
	
	final void setCellDim(short cell_dim) {
		mCellDim = cell_dim;
		mBRX = computeBRX(mTLX);
		mBRY = computeBRY(mTLY);
	}
	
	final void setTLX(short tlx) {
		mTLX = tlx;
		mBRX = computeBRX(tlx);
	}
	
	final void setTLY(short tly) {
		mTLY = tly;
		mBRY = computeBRY(tly);
	}
	
	final private short computeBRX(short tlx) {
		short brx = tlx;
		brx += NUM_CELLS * mCellDim;
		return brx;
	}
	
	final private short computeBRY(short tly) {
		short bry = tly;
		bry += NUM_CELLS * mCellDim;
		return bry;
	}
	
	final short getCellTLX(short cell_num) {
		switch ( cell_num ) {
		// 1st row
		case 0: return mTLX;
		case 1: return (short)(mTLX + mCellDim);
		case 2: return (short)(mTLX + 2 * mCellDim);
		// 2nd row
		case 3: return mTLX;
		case 4: return (short)(mTLX + mCellDim);
		case 5: return (short)(mTLX + 2 * mCellDim);
		// 3rd row
		case 6: return mTLX;
		case 7: return (short)(mTLX + mCellDim);
		case 8: return (short)(mTLX + 2 * mCellDim);
		default: return -1;
		}
	}
	
	final short getCellTLY(short cell_num) {
		switch ( cell_num ) {
		// 1st row
		case 0: return mTLY;
		case 1: return mTLY;
		case 2: return mTLY;
		// 2nd row
		case 3: return (short)(mTLY + mCellDim);
		case 4: return (short)(mTLY + mCellDim);
		case 5: return (short)(mTLY + mCellDim);
		// 3rd row
		case 6: return (short)(mTLY + 2 * mCellDim);
		case 7: return (short)(mTLY + 2 * mCellDim);
		case 8: return (short)(mTLY + 2 * mCellDim);
		default: return -1;
		}
	}
	
	final short getCellTLXByX(float x) {
		short in_x = (short) x;
		short tl_x, tr_y;
		for(short i = 0; i < 9; i++) {
			tl_x = getCellTLX(i);
			tr_y = (short)(tl_x + mCellDim);
			if ( in_x > tl_x && in_x < tr_y )
				return getCellTLX(i);
		}
		return -1;
	}
	
	final short getCellTLXByY(float y) {
		short in_y = (short) y;
		short tl_y, bl_y;
		for(short i = 0; i < 9; i++) {
			tl_y = getCellTLY(i);
			bl_y = (short)(tl_y + mCellDim);
			if ( in_y > tl_y && in_y < bl_y )
				return getCellTLY(i);
		}
		return -1;
	}
	
	final short getCellByXY(float x, float y) {
		short tl_x, tl_y, bl_y, tr_x;
	
		for(short i = 0; i < 9; i++) {
			tl_x = getCellTLX(i);
			tr_x = (short)(tl_x + mCellDim);
			tl_y = getCellTLY(i);
			bl_y = (short)(tl_y + mCellDim);
			if ( x > tl_x && x < tr_x && y > tl_y && y < bl_y ) {
				return i;
			}
		}
		return -1;
	}
	
	
	
}
