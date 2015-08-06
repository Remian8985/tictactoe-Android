package com.tasdeeq.tictactoe_game_ui_tester;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.ViewTreeObserver;
import android.widget.TextView;


public class GameWindow extends Activity {

	final String TAG = "com.tasdeeq.tictactoe_game_ui_tester";
	// CREATE GAME MODE OBJECTS HERE
	// ^classes aren't added to project yet


	// TO DO:
	// on touch and on press for each of 9 buttons		**done**
	// on touch check if the place is empty (if yes return false)
	// ^if false, initiate background processing and place piece
	// Visually place piece		**done**

	// Finally:
	// Make buttons invisible with no text	**done**



	private boolean playerOneMove = true;
	private boolean userWantsReset = false;
	String playerOneName = "Player 1";	// default name
	String playerTwoName = "Player 2"; 	// defualt name

	char [][] grid = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_window);


		playerOneMove = true;
		updateStatus();
		// Set the image game mode:
		// 1:easy, 2:medium, 3:hard, other:human
		setGameModeImage(1);		// 1 is placeholder for now
		// instantiate classes based on ^that
		// TO DO
		// ..


		final ImageView gridImage = (ImageView) findViewById(R.id.gridImage);
		ViewTreeObserver vto = gridImage.getViewTreeObserver();

		// Get critical length of grid image and calculate button lengths from there:
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				gridImage.getViewTreeObserver().removeOnPreDrawListener(this);
				int imageWidth = gridImage.getMeasuredWidth();
				int imageHeight = gridImage.getMeasuredHeight();
				//		Log.d(TAG, "imageView width : " + imageWidth);        // debug code

				int buttonLength;
				if (imageWidth <= imageHeight)            // Portrait . Critical length = width
					buttonLength = getButtonLength(imageWidth);
				else                                     // Landscape. Critical length = height
					buttonLength = getButtonLength(imageHeight);
				adjustButtons(buttonLength);

				return true;
			}
		});

	}

	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save game mode information here
		// ..
		// TO DO

		if (userWantsReset){
			outState.putBoolean("justDidReset", true);
			return;
		}

		outState.putBoolean("isPlayerOneMove", playerOneMove);
		outState.putCharArray("row0", grid[0]);
		outState.putCharArray("row1", grid[1]);
		outState.putCharArray("row2", grid[2]);
	}


	protected void onRestoreInstanceState(Bundle savedState)
	{
		System.out.println("TAG, onRestoreInstanceState");

		// Restore game mode information here
		// ..
		// TO DO

		boolean justDidReset = savedState.getBoolean("justDidReset");
		if(justDidReset)
			return;

		playerOneMove = savedState.getBoolean("isPlayerOneMove");
		grid[0] = savedState.getCharArray("row0");
		grid[1] = savedState.getCharArray("row1");
		grid[2] = savedState.getCharArray("row2");


		updateStatus();

	}

	private void updateStatus(){
		playerOneMove = !playerOneMove; // temporary
		turnChange();					// reverses ^that and sets text
	}

	public void onResetClick(View v) {
		// TO DO:
		// MANAGE THINGS PASSED IN THE ACTIVITY!
		// SAVE GAME MODE
		// CLEAN UP

		userWantsReset = true; 	// default is false
		this.recreate();
	}


	public void buttonOnClick(View v) {
		int viewId = v.getId();
		Log.d(TAG, "Button clicked : " + viewId);		// debug code
		v.setBackgroundResource(getImageResource(playerOneMove));
		v.setEnabled(false);

	// DEBUG CODE ########################################################################
		String IdAsString = v.getResources().getResourceName(viewId);
		int givenButtonNameIndex = IdAsString.lastIndexOf("button");
		String buttonIndexStr = IdAsString.substring(givenButtonNameIndex + 6, givenButtonNameIndex+ 8); // "button" takes 0-5
		int buttonIndex = Integer.parseInt(buttonIndexStr);
		int row = buttonIndex / 10 ;
		int col = buttonIndex % 10;

		if (playerOneMove)
			grid[row][col] = 'X';
		else
			grid[row][col] = 'O';
	// ##################################################################################

		turnChange();
	}

	private void restoreButtonState(Button button, char piece){
		if (piece != 'X' && piece != 'O')	// if button is occupied by
			return; 					// neither player

		button.setEnabled(false);
		boolean isPlayerOne = (piece == 'X') ;
		button.setBackgroundResource(getImageResource(isPlayerOne));
	}




	private void changeButtonBackground(Button button, int imageID){
		button.setBackgroundResource(imageID);
	}

	private void setGameModeImage(int gameMode){
		final int vsHuman = R.drawable.vs_human ;
		final int vsAIeasy = R.drawable.vs_computer_a ;
		final int vsAImedium = R.drawable.vs_computer_b;
		final int vsAIhard = R.drawable.vs_computer_c ;

		int imageID = vsHuman;

		switch (gameMode){
			case 1:
				imageID = vsAIeasy;
				break;
			case 2:
				imageID = vsAImedium;
				break;
			case 3:
				imageID = vsAIhard;
				break;
			default:
				break;
		}

		ImageView gameModeImage = (ImageView) findViewById(R.id.gameModeImage);
		gameModeImage.setImageResource(imageID);
	}


	private int getImageResource(boolean player1Move){
		final String noughtStr = "@drawable/nought";
		final String crossStr = "@drawable/cross";
		String uri = crossStr; // default , player1 move
		if (!player1Move)
			uri = noughtStr;

		int imageResource = getResources().getIdentifier(uri, null, getPackageName());
		return imageResource;
	}



	private void turnChange(){
		playerOneMove = !playerOneMove;
		boolean player1Move = playerOneMove; // making local because i'm paranoid
		TextView statusText = (TextView) findViewById(R.id.statusText);
		if(player1Move){
			statusText.setText(playerOneName + "\'s turn! ");
		}
		else{
			statusText.setText(playerTwoName + "\'s turn! ");
		}
	}



	// Changes all the input buttons to be the right size
	private void adjustButtons(int length) {
		Button button00 = (Button) findViewById(R.id.button00);
		Button button01 = (Button) findViewById(R.id.button01);
		Button button02 = (Button) findViewById(R.id.button02);
		Button button10 = (Button) findViewById(R.id.button10);
		Button button11 = (Button) findViewById(R.id.button11);
		Button button12 = (Button) findViewById(R.id.button12);
		Button button20 = (Button) findViewById(R.id.button20);
		Button button21 = (Button) findViewById(R.id.button21);
		Button button22 = (Button) findViewById(R.id.button22);

		// Adjust button size
		adjustButtonSize(button00, length, length);
		adjustButtonSize(button01, length, length);
		adjustButtonSize(button02, length, length);
		adjustButtonSize(button10, length, length);
		adjustButtonSize(button11, length, length);
		adjustButtonSize(button12, length, length);
		adjustButtonSize(button20, length, length);
		adjustButtonSize(button21, length, length);
		adjustButtonSize(button22, length, length);

		// if there was a saved instance, restore button states
		restoreButtonState(button00 , grid[0][0]);
		restoreButtonState(button01 , grid[0][1]);
		restoreButtonState(button02 , grid[0][2]);
		restoreButtonState(button10 , grid[1][0]);
		restoreButtonState(button11 , grid[1][1]);
		restoreButtonState(button12 , grid[1][2]);
		restoreButtonState(button20 , grid[2][0]);
		restoreButtonState(button21 , grid[2][1]);
		restoreButtonState(button22 , grid[2][2]);
	}


	// ONLY FOR RELATIVE LAYOUT! ...
	private void adjustButtonSize(Button button, int height, int width) {
		RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
		buttonParams.height = height;
		buttonParams.width = width;
		button.setLayoutParams(buttonParams);
	}


	// defined as one-third for now
	private int getButtonLength(int args){
		return args/3;
	}


	private boolean isOccupied(int i, int j){
		// TO DO
		return false; // PLACEHOLDER!
	}

	private void aiPlacePiece(int i, int j){
		Button targetSlotButton = getButton(i, j);
		if (targetSlotButton == null)
			return;

		// Visual updates need to be done onClick
		// Slot information updates need to be done onClick
		targetSlotButton.performClick();
	}


	private Button	getButton (int i, int j){
		int viewID = -1;			 // unassigned
		int	errorVerifier = viewID;
		if (i == 0){
			switch (j){
				case 0:
					viewID = R.id.button00;
					break;
				case 1:
					viewID = R.id.button02;
					break;
				case 2:
					viewID = R.id.button02;
					break;
				default:
					break;
			}
		}
		else if (i == 1){
			switch (j){
				case 0:
					viewID = R.id.button10;
					break;
				case 1:
					viewID = R.id.button12;
					break;
				case 2:
					viewID = R.id.button12;
					break;
				default:
					break;
			}
		}
		else if (i == 2){
			switch (j){
				case 0:
					viewID = R.id.button20;
					break;
				case 1:
					viewID = R.id.button22;
					break;
				case 2:
					viewID = R.id.button22;
					break;
				default:
					break;
			}
		}
		Button button;
		if (viewID == errorVerifier)
			button = null;
		else
			button = (Button) findViewById(viewID);

		return button;
	}


}
