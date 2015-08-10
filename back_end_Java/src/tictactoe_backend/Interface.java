/**
 * 	Author: Tasdiq Ameem
 * 	Date created: July 8, 2015
 *	This class will serve to be the interface for 
 *	development and debugging purposes only. This will be text based
 *	and will contain little or no graphics (most likely no graphics). 
 *	The actual logic will be implemented in different classes that
 *	can be easily ported to the Android project. 
 */

package tictactoe_backend;

import java.util.Scanner;

public class Interface {

	static final int SIZE_OF_BOARD = 3;
	static char[][] board = new char[3][3];

	static char playerPiece, aiPiece;

	public static void main(String[] args) {
		char[][] board = new char[SIZE_OF_BOARD][SIZE_OF_BOARD];
		char answer;
		int difficulty =-1;
		boolean twoPlayer = true;
		
		// prompt for singleplayer/2player : 
		System.out.println("Do you want to play with a computer? (Y/N)");
		Scanner in = new Scanner(System.in);	
		answer = in.next().charAt(0);
		if (answer == 'y' || answer == 'Y'){
			twoPlayer = false; 
			System.out.println("Select difficulty level, enter a number: \n1. Random "
					+ "\n2. Reactive \n3. Unbeatable ");
			difficulty = adjustDifficulty(in.nextInt()) ;
		}

		
		if (twoPlayer)
			twoPlayer() ;	// human gets both turns
		else 		
		//	singlePlayer(difficulty) ; // difficulty does not matter for now
			singlePlayerB(difficulty) ; // difficulty does not matter for now

		System.out.println("Done! ");
		in.close(); 
	}
	
	
	
	// Top level thing for single player
	// difficulty level determines how the computer will react. 	
	public static void singlePlayer(int difficulty)
	{
		boolean player1Turn = setPieces(true);	// determines who gets X and who gets O 
											// true for single player, false for 2player
		clearBoard();
		String player1Name = "Player" ;
		String player2Name = "Computer" ; 
		
		PlayerMovesHandler player1 = new PlayerMovesHandler(board, playerPiece);
		ComputerMovesHandler player2 = new ComputerMovesHandler(board, aiPiece);
		
		
		int turnCount = 0;
		while (turnCount < SIZE_OF_BOARD * SIZE_OF_BOARD) {
			if (player1Turn){
				placePlayerPiece(player1);
				if (isPlayerWinner(player1Name , player1.getPiece())){
					return ;
				}
			}
			else{
				player2.placePiece();
				if (isPlayerWinner(player2Name , player2.getPiece())){
					return ;
				}
			}
			printBoard(board);
			player1Turn = !player1Turn;		// toggle
			
			turnCount++;
		}
		printDraw(); // this method should have exited already if someone won
	} // end of singlePlayer(..)
	
	
	
	
	public static void singlePlayerB(int difficulty)
	{
		boolean player1Turn = setPieces(true);	// determines who gets X and who gets O 
											// true for single player, false for 2player
		clearBoard();
		String player1Name = "Player" ;
		String player2Name = "Computer" ; 
		
		Player player1;
		Player player2;
		Player focusedPlayer;	//	Toggle comment here for alternative
		
		player1 = new HumanPlayer(playerPiece);
		player2 = new RandomMoveComputer(aiPiece);
		
		
		int turnCount = 0;
		while (turnCount < SIZE_OF_BOARD * SIZE_OF_BOARD) {
			String playerName;
//			Player focusedPlayer;	//	Toggle comment here for alternative
			if (player1Turn){
				focusedPlayer = player1;
				playerName = player1Name;
			}
			else {
				focusedPlayer = player2;
				playerName = player2Name;
			}
			
			placePlayerPiece(focusedPlayer);
			board = focusedPlayer.getBoard(); 
			if (focusedPlayer.isGameWon()){
				System.out.println(playerName + " wins!");
				printBoard(board); 
				return ;
			}
			
			
			
			
//			if (player1Turn){
//				placePlayerPiece(player1);
//				board = player1.getBoard(); 
//				if (player1.isGameWon()){
//					System.out.println(player1Name + " wins!");
//					printBoard(board); 
//					return ;
//				}
//			}
//			else {
//				placePlayerPiece(player2);
//				board = player2.getBoard(); 
//				if (player2.isGameWon()){
//					System.out.println(player2Name + " wins!");
//					printBoard(board); 
//					return ;
//				}
//			}
			
			printBoard(board); 
			player1Turn = !player1Turn;
			
			
			turnCount++; 
		}
		
		
		printDraw(); // this method should have exited already if someone won
	} // end of singlePlayerB(..)
	
	
	public static void twoPlayer()
	{
		boolean player1Turn = setPieces(false); // false for 2-player, true for single player
		clearBoard();
		String player1Name = "Player 1" ;
		String player2Name = "Player 2" ; 
		
		// the "aiPiece" also belongs to a human player! 
		PlayerMovesHandler player1 = new PlayerMovesHandler(board, playerPiece);
		PlayerMovesHandler player2 = new PlayerMovesHandler(board, aiPiece);
		
		
		int turnCount = 0;
		while (turnCount < SIZE_OF_BOARD * SIZE_OF_BOARD) {
			if (player1Turn){
				placePlayerPiece(player1);
				if (isPlayerWinner(player1Name , player1.getPiece())){
					
					return ;
				}
			}
			else{
				placePlayerPiece(player2);
				if (isPlayerWinner(player2Name , player2.getPiece())){
					return ;
				}
			}
			printBoard(board);
			player1Turn = !player1Turn;		// toggle
			
			turnCount++;
		}
		printDraw(); // this method should have exited already if someone won
	} // end of twoPlayer() 
	
	
	
	// Checks if the given piece has won the game. If yes, prints it out
	// and returns true 
	public static boolean isPlayerWinner(String playerName, char piece){
		if (isGameWon(piece)){
			printBoard(board);
			System.out.println(playerName +  "  WINS!"); 
			return true;
		}
		else 
			return false;
	}
	
	
	// if singleplayer, asks if player wants first turn
	// whoever gets first turn gets X, other gets O (the letter oh)
	// It does not matter for 2-player
	public static boolean setPieces(boolean singlePlayer) {
		if (!singlePlayer){
			playerPiece = 'X';
			aiPiece = 'O';
			return true;
		}
		
		System.out.println("Do you want to go first? (Y/N)");
		Scanner in = new Scanner(System.in);
		char answer = in.next().charAt(0);
		if (answer == 'y' || answer == 'Y') {
			playerPiece = 'X';
			aiPiece = 'O';
			return true;
		} else {
			playerPiece = 'O';
			aiPiece = 'X';
			return false;
		}
	}

	
	// Prints out the game board at current state 
	public static void printBoard(char[][] board) {
		for (int i = 0; i < SIZE_OF_BOARD; ++i) {
			for (int j = 0; j < SIZE_OF_BOARD; ++j) {
				System.out.print('[');
				System.out.print(board[i][j]);
				System.out.print(']');
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	public static void placePlayerPiece(Player player) {
		Scanner in = new Scanner(System.in);
		boolean invalidInput = true;

		while (invalidInput) {
			int row, col; 
			int position = player.aiPlacePiece(); 
			if (position <0){			
				System.out.println("Enter row and column no. where you want to place your mark (Top left is [1,1])");
	
				row = in.nextInt() - 1;
				col = in.nextInt() - 1;
			}
			else {
				row = position /10;
				col = position %10;
			}
			
			try{
				player.placePiece(row, col);
				invalidInput = false;
			}
			catch (Exception e){
				invalidInput = true;
			}
		}
	}

	
	// This method needs to be ported for android without all these prompts
	// Checks need to be done differently for that btw.
	// piece placer for 2-player
	public static void placePlayerPiece(PlayerMovesHandler player) {
		// char piece = player.getPiece();
		Scanner in = new Scanner(System.in);
		boolean invalidInput = true;

		while (invalidInput) {
			System.out.println("Enter row and column no. where you want to place your mark (Top left is [1,1])");

			int row = in.nextInt() - 1;
			int col = in.nextInt() - 1;
			invalidInput = !(player.setPiece(row, col));	// callee returns true if success 
		}
	}

	
	// Clears/resets the board 
	public static void clearBoard() // idk if I will ever use it here
	{
		for (int i = 0; i < SIZE_OF_BOARD; ++i) {
			for (int j = 0; j < SIZE_OF_BOARD; ++j) {
				board[i][j] = ' ';
			}
		}
	}
	
	// Checks if the game is won for the given piece
	public static boolean isGameWon(final char piece){
		if (board[0][0] == piece){								// top-left corner
			if (board[1][1] == piece && board[2][2] == piece)		// diagonal
				return true;
			else if (board[0][1] == piece && board[0][2] == piece)	// going right
				return true;
			else if (board[1][0] == piece && board[2][0] == piece)	// going down
				return true;
		}
		if (board[2][2] == piece){								// bottom-right corner
			if (board[2][1] == piece && board[2][0] == piece)		// going left
				return true;
			else if (board[1][2] == piece && board[0][2] == piece)	// going up
				return true;
		}
		if (piece == board[1][1]){								// middle slot
			if (board[0][2] == piece && board[2][0] == piece)		// the other diagonal
				return true;
			else if (piece == board[1][0] && piece == board[1][2])	// horizontal through and through
				return true;
			else if (piece == board[0][1] && piece == board[2][1])	// vertical through and through
				return true;
		}
		
		return false; 	
	}
	
	
	// Not needed in Android port. Debug method: 
	public static int adjustDifficulty(int rawNum){
		// 1-3 allowed for now: 
		if (rawNum >0 && rawNum <4)
			return rawNum;
		else {
			System.out.println("Invalid level. Setting level to 1.Random ");
			return 1; 
		}			
	}
	
	public static void printDraw() {
		System.out.println("It's a draw! ");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
