package com.tasdeeq.tictactoe_game_ui_tester;

import android.app.Activity;
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
	int difficulty = -1;		// human (anything not 1,2,3 is human)
	String playerOneName = "Player 1";	// default name
	String playerTwoName = "Player 2"; 	// defualt name

	final String humanName = "Human";
	final String aiName = "Computer";

	char [][] grid = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};	// empty grid

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_window);

		Bundle gameData = getIntent().getExtras();
		if (gameData != null) {
			difficulty = gameData.getInt("GAME_MODE");
		} 	// Set the image game mode:
		// 1:easy, 2:medium, 3:hard, other:human
		setGameModeImage(difficulty);

		playerOneMove = true;
		updateStatus();

		// instantiate classes based on difficulty
		// TO DO
		// ..


		// The buttons are given arbitrary size in XML file
		// On drawing the grid, we measure the shortest side of the imageView that
		// is showing the grid image. Length of a square button is about one-third of that.
		final ImageView gridImage = (ImageView) findViewById(R.id.gridImage);

		// I have absolutely no clue wtf viewTreeObserver thingamajing is for. All I know is
		// this is the way to go accomplish the above task.
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

	} // end of onCreate

	// Useful in orientation change and other miscellaneous things where onCreate
	// is called again. We want to save the game state.
	// If the reset button is pressed, we only save difficulty
	//										....and perhaps the score.
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("GAME_MODE", difficulty);	// Save game mode info
		if (userWantsReset){
			outState.putBoolean("justDidReset", true);
			return;
		}

		outState.putBoolean("isPlayerOneMove", playerOneMove);
		// Apparently 2d arrays cannot be saved :/
		outState.putCharArray("row0", grid[0]);
		outState.putCharArray("row1", grid[1]);
		outState.putCharArray("row2", grid[2]);
	}


	// Useful in orientation change and other miscellaneous things where onCreate
	// is called again. We want to restore the game state.
	// If the reset button is pressed, we only restore difficulty
	//										....and perhaps the score.
	protected void onRestoreInstanceState(Bundle savedState)
	{
		System.out.println("TAG, onRestoreInstanceState");	// DEBUG CODE
		difficulty = savedState.getInt("GAME_MODE");	// Restore game mode info

		boolean justDidReset = savedState.getBoolean("justDidReset");
		if(justDidReset)
			return;

		playerOneMove = savedState.getBoolean("isPlayerOneMove");
		// Apparently 2d arrays cannot be saved and restored :/
		grid[0] = savedState.getCharArray("row0");
		grid[1] = savedState.getCharArray("row1");
		grid[2] = savedState.getCharArray("row2");

		updateStatus();	// updates whose turn it is.
	}


	// updates the text that says whose turn it is now
	private void updateStatus(){
		playerOneMove = !playerOneMove; // temporary
		turnChange();					// reverses ^that and sets text
	}

	// Abandon the current game and create a new one with same difficulty
	public void onResetClick(View v) {
		userWantsReset = true; 	// default is false
		this.recreate();
	}


	// When one of the 9 buttons is clicked,
	// 		- It places the current player's piece
	//		- Disables the button, it's already been used
	// 		- Initiates AI move for next turn (if applicable)
	// 		- Changes the turn for players.
	public void buttonOnClick(View v) {
		int viewId = v.getId();
		Log.d(TAG, "Button clicked : " + viewId);		// debug code
		v.setBackgroundResource(getImageResource(playerOneMove));	// place the current player's piece
		v.setEnabled(false);


		// Debug code below fills the placeholder 2D array.
		// I'm not quite sure now (Aug 6,2015) if I'll have it in this activity at all
		// This will definitely be in AI classes

		// NEED TO UPDATE GRID ON AI CLASS!!!!!		TO DO \\
	// DEBUG CODE ########################################################################

		// From the button id, find the button row and column.
		// extract 00, 20, 21 etc from button00, button20, button21 etc

		int buttonIndex = getButtonPos( (Button) v );
		int row = buttonIndex / 10 ;
		int col = buttonIndex % 10 ;

		if (playerOneMove)
			grid[row][col] = 'X';
		else
			grid[row][col] = 'O';
	// ##################################################################################

		turnChange();
	}


	// From the button id, find the button row and column.
	// extract 00, 20, 21 etc from button00, button20, button21 etc
	private int getButtonPos(Button button){
		String IdAsString = button.getResources().getResourceName(button.getId());
		int givenButtonNameIndex = IdAsString.lastIndexOf("button");
		// The two numbers are between 5 and 8 (starts from 6, ends before 8)
		String buttonIndexStr = IdAsString.substring(givenButtonNameIndex + 6, givenButtonNameIndex+ 8); // "button" takes 0-5
		int buttonIndex = Integer.parseInt(buttonIndexStr);
		return buttonIndex;
	}


	// Sets the difficulty indicator image based on difficulty
	private void setGameModeImage(final int difficulty){
		final int vsHuman = R.drawable.vs_human ;
		final int vsAIeasy = R.drawable.vs_computer_a ;
		final int vsAImedium = R.drawable.vs_computer_b;
		final int vsAIhard = R.drawable.vs_computer_c ;
		int imageID = vsHuman;

		switch (difficulty){
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


	// Returns image resource id for player 1 or 2.
	private int getImageResource(final boolean playerOneMove){
		final String noughtStr = "@drawable/nought";
		final String crossStr = "@drawable/cross";
		String uri = crossStr; // default , player1 move
		if (!playerOneMove)
			uri = noughtStr;

		int imageResource = getResources().getIdentifier(uri, null, getPackageName());
		return imageResource;
	}


	private void turnChange(){
		playerOneMove = !playerOneMove;		// toggle player turn
		// Set player turn indicator text:
		TextView statusText = (TextView) findViewById(R.id.statusText);
		if(playerOneMove){
			statusText.setText(playerOneName + "\'s turn! ");
		}
		else{
			statusText.setText(playerTwoName + "\'s turn! ");
		}
	}


	private void restoreButtonState(Button button, char piece){
		if (piece != 'X' && piece != 'O') {    // if button is occupied by
						                    // neither player
			button.setEnabled(true);
			return;
		}

		button.setEnabled(false);
		boolean isPlayerOne = (piece == 'X') ;
		button.setBackgroundResource(getImageResource(isPlayerOne));
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
		if (length > 0) {
			// The if condition allows use of this method
			// just to change button states, not sizes
			adjustButtonSize(button00, length, length);
			adjustButtonSize(button01, length, length);
			adjustButtonSize(button02, length, length);
			adjustButtonSize(button10, length, length);
			adjustButtonSize(button11, length, length);
			adjustButtonSize(button12, length, length);
			adjustButtonSize(button20, length, length);
			adjustButtonSize(button21, length, length);
			adjustButtonSize(button22, length, length);
		}

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


	// This method is to be used before initiating AI calculation in separate thread
	// so that user cannot click another button in the meantime.
	private void disableAllButtons(){
		(findViewById(R.id.button00)).setEnabled(false);
		(findViewById(R.id.button01)).setEnabled(false);
		(findViewById(R.id.button02)).setEnabled(false);

		(findViewById(R.id.button10)).setEnabled(false);
		(findViewById(R.id.button11)).setEnabled(false);
		(findViewById(R.id.button12)).setEnabled(false);

		(findViewById(R.id.button20)).setEnabled(false);
		(findViewById(R.id.button21)).setEnabled(false);
		(findViewById(R.id.button22)).setEnabled(false);
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


	private void changeButtonBackground(Button button, int imageID){
		button.setBackgroundResource(imageID);
	}

}
