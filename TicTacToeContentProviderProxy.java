package org.vkedco.mobappdev.tic_tac_toe_touch_play_00002;

/*
 ************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ***********************************************************
 */

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class TicTacToeContentProviderProxy {
	
	static final String NEWLINE          = "\n";
	static final String RECORD_SEPARATOR = "***********";
	static final String FORWARD_SLASH    = "/";
	static final String EQLS			 = "=";
	static final String COLON			 = ":";
	static final String SPACE			 = " ";
	static final String LOGTAG = TicTacToeContentProviderProxy.class.getSimpleName() + "_LOG";
	
	// constants for board_state table column names
	static final String BOARD_STATE_TBL_ID_COL_NAME        = "ID";
	static final String BOARD_STATE_TBL_BOARD_COL_NAME     = "Board";
	static final String BOARD_STATE_TBL_PLAYER_COL_NAME    = "Player";
	static final String BOARD_STATE_TBL_MOVEUTILS_COL_NAME = "MoveUtils";

	static final String[] BOARD_STATE_TBL_COLUMN_NAMES = 
	{ 
		BOARD_STATE_TBL_ID_COL_NAME, 
		BOARD_STATE_TBL_BOARD_COL_NAME, 
		BOARD_STATE_TBL_PLAYER_COL_NAME, 
		BOARD_STATE_TBL_MOVEUTILS_COL_NAME,
	};
	
	// constants for board_state table column numbers
	static final int BOARD_STATE_TBL_ID_COL_NUM        = 0;
	static final int BOARD_STATE_TBL_BOARD_COL_NUM	   = 1;
	static final int BOARD_STATE_TBL_PLAYER_COL_NUM    = 2;
	static final int BOARD_STATE_TBL_MOVEUTILS_COL_NUM = 3;
	
	final static Cursor getBoardMoveUtilsByBoard(Context cntxt, TicTacToeGameState gs) {
		String board_state = new String(gs.getBoardState());
	    	Uri uri = Uri.parse(cntxt.getResources().getString(R.string.ttt_board_query_uri) + board_state);
	    	Log.d(LOGTAG, "uri=" + uri.toString());
	    	Cursor rslt = cntxt.getContentResolver().query(uri, 
	    			BOARD_STATE_TBL_COLUMN_NAMES, 
	    			null, 
	    			null, 
	    			null
	    			);
	    	return rslt;
	 }
	
    final static short processBoardMoveUtils(Cursor rslt, TicTacToePlayer ttt_player, TicTacToePlayer computer_player) {
		if ( rslt == null ) {
			Log.d(LOGTAG, "Cursor is null");
			return -1;
		}
		
		Log.d(LOGTAG, "ttt_player=" + ttt_player.getName());
		if ( rslt.getCount() != 0 ) {
			rslt.moveToFirst();
			int id = 0;
			String board, player, moveUtils;
			board = ""; player = ""; moveUtils = "";
			while ( rslt.isAfterLast() == false ) {
				id = rslt.getInt(rslt.getColumnIndex(BOARD_STATE_TBL_ID_COL_NAME));
				board = rslt.getString(rslt.getColumnIndex(BOARD_STATE_TBL_BOARD_COL_NAME));
				player = rslt.getString(rslt.getColumnIndex(BOARD_STATE_TBL_PLAYER_COL_NAME));
				moveUtils = rslt.getString(rslt.getColumnIndex(BOARD_STATE_TBL_MOVEUTILS_COL_NAME));
				//Log.d(LOGTAG, "content provider id=" + id);
				//Log.d(LOGTAG, "content provider board=" + board);
				//Log.d(LOGTAG, "content provider player=" + player);
				//Log.d(LOGTAG, "content provider moveUtils=" + moveUtils);
				rslt.moveToNext();
			}
			rslt.close();
			if ( player.charAt(0) == ttt_player.getName() ) {
				short cell = getMinMaxUtilMove(moveUtils, computer_player);
				//Log.d(LOGTAG, "next cell = " + cell);
				return cell;
			}
			else {
				//Log.d(LOGTAG, "next cell = -1");
				return -1;
			}
		}
		else {
			//Log.d(LOGTAG, "zero entries in cursor");
			return -1;
		}
	}
    
    final static private short getMinMaxUtilMove(String move_utils, TicTacToePlayer computer_player) {
    	String [] splits = move_utils.trim().split(SPACE);
    	
    	if ( splits.length == 1 ) {
    		if ( splits[0].contains(COLON) ) {
    			return Short.parseShort(splits[0].substring(0, splits[0].indexOf(COLON)));
    		}
    		else {
    			// this is a terminal position
    			return -1;
    		}
    	}
    	else {
    		short util = (short)-2;
    		short cell_num = (short) -1;
    		for(String move_util: splits) {
    			//Log.d(LOGTAG, "move_util=" + move_util);
    			final int pos = move_util.indexOf(COLON); 
    			//Log.d(LOGTAG, "pos(:)=" + pos);
    			final short temp_cell = Short.parseShort(move_util.substring(0, pos));
    			final short temp_util = Short.parseShort(move_util.substring(pos+1));
    			//Log.d(LOGTAG, "temp_cell, temp_util=" + temp_cell + ", " + temp_util);
    			if ( computer_player == TicTacToePlayer.X ) {
    				if ( temp_util > util ) {
    					util = temp_util;
    					cell_num = temp_cell;
    				}
    			}
    			else if ( computer_player == TicTacToePlayer.O ) {
    				if ( util == -2 ) {
    					util = temp_util;
    					cell_num = temp_cell;
    				}
    				else if ( temp_util < util ) {
    					util = temp_util;
    					cell_num = temp_cell;
    				}
    			}
    			//Log.d(LOGTAG, "util, cell_num=" + util + ", " + cell_num);
    		}
    		return cell_num;
    	}
    }
    
}


