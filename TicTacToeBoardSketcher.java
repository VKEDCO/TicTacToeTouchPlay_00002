package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

/*
 ************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ***********************************************************
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TicTacToeBoardSketcher extends View {
	final Paint mBackgroundPaint;
	final Paint mForeCirclePaint;
	final Paint mForeCrossPaint;
	final Paint mForeBoardPaint;
	final static short DRAW_DELTA = 25;
	short mTouchedCellNum = -1;
	TicTacToeTouchPlayApp mTTTApp = null;
	int mResultDisplayedForGame = -1;
	private boolean mDrawingEnabled = true;
	final static String DRAW = "DRAW";
	final static String WINS = " WINS";
	
	public TicTacToeBoardSketcher(Context context, AttributeSet atrs) {
		super(context, atrs);
		
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(Color.GREEN);
		
		mForeBoardPaint = new Paint();
		mForeBoardPaint.setColor(Color.BLACK);
		mForeBoardPaint.setAntiAlias(true);
		
		mForeCirclePaint = new Paint();
		mForeCirclePaint.setColor(Color.RED);
		mForeCirclePaint.setAntiAlias(true);
		
		mForeCrossPaint = new Paint();
		mForeCrossPaint.setColor(Color.BLUE);
		mForeCrossPaint.setAntiAlias(true);
	}
	
	// This method redraws the entire canvas
	public void draw(Canvas canvas) {
		if ( mDrawingEnabled ) {
			final int width  = canvas.getWidth();
			final int height = canvas.getHeight();
			// 1. draw background rectangle that covers the entire
			canvas.drawRect(0, 0, width, height, mBackgroundPaint);
			// 2. draw board lines
			drawBoardLines(canvas);
			drawGameStateOnBoard(canvas);
			// 3. force redraw
			invalidate();
		}
	}
	
	public void setTicTacToeApp(TicTacToeTouchPlayApp ttt_app) {
		mTTTApp = ttt_app;
	}
	
	private void drawGameStateOnBoard(Canvas canvas) {
		// get game board state from the TicTacToeGameEngine and
		// draw it on unless the game state is restarted.
		final TicTacToeGameState ttt_gs = mTTTApp.getGameState();
		if ( ttt_gs.isRestarted() ) {
			// if the game state is restarted, draw the background and board lines
			// but do not draw the pieces.
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
			drawBoardLines(canvas);
			return;
		}
		
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
		drawBoardLines(canvas);
		drawBoadState(canvas, ttt_gs);

		if ( TicTacToeGameEngine.isTerminal(ttt_gs) && 
				mResultDisplayedForGame < TicTacToeTouchPlayApp.getGameCount() ) {	
			if ( TicTacToeGameEngine.isDraw(ttt_gs) ) {
				Toast.makeText(mTTTApp, DRAW, Toast.LENGTH_SHORT).show();
			}
			else {
				TicTacToePlayer player = TicTacToeGameEngine.getWinner(ttt_gs);
				Toast.makeText(mTTTApp, player.toString() + WINS, Toast.LENGTH_SHORT).show();
			}
			++mResultDisplayedForGame;
		}
	}
	
	private void drawBoadState(Canvas canvas, TicTacToeGameState ttt_gs) {
		final char[] board_state = ttt_gs.getBoardState();
		for(short i = 0; i < board_state.length; i++)
			drawCell(canvas, board_state[i], i);
	}
	
	private void drawBoardLines(Canvas canvas) {
		drawBorders(canvas);
		drawRows(canvas);
		drawCols(canvas);
	}
	
	private void drawRows(Canvas canvas) {
		final TicTacToeGameBoardGeometry gbg = mTTTApp.getGameBoardGeometry();
		final short tl_x = gbg.getTLX();
		final short tl_y = gbg.getTLY();
		final short cell_dim = gbg.getCellDim();
		final short tr_x = gbg.getTRX();
		final short tr_y = gbg.getTRY();
		canvas.drawLine(tl_x, tl_y + cell_dim, tr_x, tr_y + cell_dim, mForeBoardPaint);
		canvas.drawLine(tl_x, tl_y + 2 * cell_dim, tr_x, tr_y + 2 * cell_dim, mForeBoardPaint);
	}
	
	private void drawCols(Canvas canvas) {
		final TicTacToeGameBoardGeometry gbg = mTTTApp.getGameBoardGeometry();
		final short tl_x = gbg.getTLX();
		final short tl_y = gbg.getTLY();
		final short bl_x = gbg.getBLX();
		final short bl_y = gbg.getBLY();
		final short cell_dim = gbg.getCellDim();
		canvas.drawLine(tl_x + cell_dim, tl_y, bl_x + cell_dim, bl_y, mForeBoardPaint);
		canvas.drawLine(tl_x + 2 * cell_dim, tl_y, bl_x + 2 * cell_dim, bl_y, mForeBoardPaint);	
	}
	
	private void drawBorders(Canvas canvas) {
		final TicTacToeGameBoardGeometry gbg = mTTTApp.getGameBoardGeometry();
		final short tl_x = gbg.getTLX(); 
		final short tl_y = gbg.getTLY();
		final short tr_x = gbg.getTRX(); 
		final short tr_y = gbg.getTRY();
		final short bl_x = gbg.getBLX(); 
		final short bl_y = gbg.getBLY();
		final short br_x = gbg.getBRX(); 
		final short br_y = gbg.getBRY();
		
		// 1. draw line from top left corner to top right corner
		canvas.drawLine(tl_x, tl_y, tr_x, tr_y, mForeBoardPaint);
		// 2. draw line from top right corner to bottom right corner
		canvas.drawLine(tr_x, tr_y, br_x, br_y, mForeBoardPaint);
		// 3. draw line from bottom right corner to bottom left corner 
		canvas.drawLine(br_x, br_y, bl_x, bl_y, mForeBoardPaint);
		// 4. draw line from bottom left corner to top left corner
		canvas.drawLine(bl_x, bl_y, tl_x, tl_y, mForeBoardPaint);
	}
	
	private void drawCell(Canvas canvas, char cell_value, short cell_pos) {
		final TicTacToeGameBoardGeometry gbg = mTTTApp.getGameBoardGeometry();
		short cell_dim = gbg.getCellDim();
		short cell_tlx  = gbg.getCellTLX(cell_pos);
		short cell_tly  = gbg.getCellTLY(cell_pos);
		if ( cell_value == TicTacToeGameEngine.O ) {
			float cx = cell_tlx + cell_dim/2.0f;
			float cy = cell_tly + cell_dim/2.0f;
			//Toast.makeText(getContext(), "cx=" + cx + " cy=" +cy, Toast.LENGTH_LONG).show();
			canvas.drawCircle(cx, cy, cell_dim/2.0f - DRAW_DELTA, mForeCirclePaint);
		}
		else if ( cell_value == TicTacToeGameEngine.X ) {
			canvas.drawLine(cell_tlx + DRAW_DELTA, cell_tly + DRAW_DELTA, 
					cell_tlx + cell_dim - DRAW_DELTA, 
					cell_tly + cell_dim - DRAW_DELTA, 
					mForeCrossPaint);
			canvas.drawLine(cell_tlx + DRAW_DELTA, cell_tly + cell_dim - DRAW_DELTA, 
					cell_tlx + cell_dim - DRAW_DELTA, cell_tly + DRAW_DELTA,
					mForeCrossPaint);
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		// 1. get the x and y of MotionEvent
		final float x = event.getX();
		final float y = event.getY();
		final TicTacToeGameBoardGeometry gbg = mTTTApp.getGameBoardGeometry();
		// 2. find the board cell closest to x and y
		mTouchedCellNum = gbg.getCellByXY(x, y);		
		handleTouchEvent(event);
		return true;
	}
	
	final static String PLAYER_NOT_SELECTED = "Select X/O to play";
	private void handleTouchEvent(MotionEvent event) {
		final TicTacToeGameState ttt_gs = mTTTApp.getGameState();
		int action = event.getAction();
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			if ( mTTTApp.getComputerPlayer() == null ) {
				Toast.makeText(mTTTApp, PLAYER_NOT_SELECTED, Toast.LENGTH_SHORT).show();
				return;
			}
			if ( mTouchedCellNum != -1 ) {
				ttt_gs.play(mTouchedCellNum);
				short cell_num = TicTacToeGameEngine.getNextMinMaxMove(mTTTApp, ttt_gs, mTTTApp.getComputerPlayer());
				if ( cell_num != -1 )
					ttt_gs.play(cell_num);
			}
				
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
	}
	
	final void enableDrawing() { mDrawingEnabled = true; }
	final void disableDrawing() { mDrawingEnabled = true; }
}
