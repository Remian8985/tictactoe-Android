package com.tasdeeq.tictactoe_game_ui_tester;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
	String playerOneName = "Player 1";	// default name
	String playerTwoName = "Player 2"; 	// defualt name

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_window);


		playerOneMove = true;

		final ImageView gridImage = (ImageView) findViewById(R.id.gridImage);
		ViewTreeObserver vto = gridImage.getViewTreeObserver();

		// Get critical length of grid image and calculate button lengths from there:
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				gridImage.getViewTreeObserver().removeOnPreDrawListener(this);
				int imageWidth = gridImage.getMeasuredWidth();
				Log.d(TAG, "imageView width : " + imageWidth);		// debug code

				int buttonLength = getButtonLength(imageWidth) ;
				resizeButtons(buttonLength);

				return true;
			}
		});

	}



	public void buttonOnClick(View v) {
		int viewId = v.getId();

		Log.d(TAG, "Button clicked : " + viewId);		// debug code


		placeImage((Button) v);
		turnChange();


	}



	private void changeButtonBackground(Button button, int imageID){
		button.setBackgroundResource(imageID);
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



	private  void placeImage(Button button){
		button.setBackgroundResource(getImageResource(playerOneMove));
		button.setEnabled(false);
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
	private void resizeButtons(int length) {
		Button button00 = (Button) findViewById(R.id.button00);
		Button button01 = (Button) findViewById(R.id.button01);
		Button button02 = (Button) findViewById(R.id.button02);
		Button button10 = (Button) findViewById(R.id.button10);
		Button button11 = (Button) findViewById(R.id.button11);
		Button button12 = (Button) findViewById(R.id.button12);
		Button button20 = (Button) findViewById(R.id.button20);
		Button button21 = (Button) findViewById(R.id.button21);
		Button button22 = (Button) findViewById(R.id.button22);

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
