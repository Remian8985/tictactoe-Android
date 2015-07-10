package tictactoe_backend;
/*	
 * 	Author: Tasdiq Ameem
 * 	Date created : July 8, 2015
 * 	This class handles computer player's moves.
 * 	At the moment, I intend to make subclasses of this 
 * 	for different difficulty modes. 
 * 	But I forgot the rules of inheritance LOL. 
 * 	May just use methods and switch statements 
 */

import java.util.Random;

public class ComputerMovesHandler {
	

	
	private final int SIZE_OF_BOARD = 3; 
	private char[][] board = new char[3][3]; 
	private char aiPiece, playerPiece;
	
	// Constructor: 
	public ComputerMovesHandler(char[][] board_, char piece_)
	{
		board = board_; 
		aiPiece = piece_;
		clearBoard();	
		if (aiPiece == 'x' || aiPiece == 'X')
			playerPiece = 'O';
		else 
			playerPiece = 'X';
	}
	
	public void  doYourThing() 
	{
		// TO DO 
	}
	
	public char getOpponentPiece(){
		return playerPiece; 
	}
	
	public char[][] getBoard(){
		return board;
	}
	
	public char getPiece(){
		return aiPiece;
	}
	
	public void setBoard(char[][] board_)
	{
		board = board_;
	}
	
	
	// Redundancies need to removed....
	public boolean setPiece(int row, int col){
		if (row >= SIZE_OF_BOARD || col >= SIZE_OF_BOARD || row < 0 || col < 0) {
	//		System.out.println("Invalid move, out of bounds.");
			return false;
		}
		if (board[row][col] == ' ') {
			board[row][col] = aiPiece;
			return true;
		} else {
	//		System.out.println("Invalid move, that location is not empty.");
			return false;
		}
	}
	
	
	// set a slot to empty, useful for calculating victory or fork
	public void clearPiece(int row, int col){
		board[row][col] = ' '; 
	}
	
	
	
	
	public void clearBoard()	// idk if I will ever use it here 
	{
		for (int i =0; i < SIZE_OF_BOARD; ++i )
		{
			for (int j =0 ; j < SIZE_OF_BOARD; ++j) 
			{
				board [i][j] = ' ' ;
			}
		}
	}
	
	
	// this is a duplicate from the main class. Trust me, I need to duplicate
	public boolean isGameWon(char piece)
	{
		if (board[0][0] == piece){
			if (board[1][1] == piece && board[2][2] == piece)
				return true;
			else if (board[0][1] == piece && board[0][2] == piece)
				return true;
			else if (board[1][0] == piece && board[2][0] == piece)
				return true;
		}
		if (board[2][2] == piece){
			if (board[2][1] == piece && board[2][0] == piece)
				return true;
			else if (board[1][2] == piece && board[0][2] == piece)
				return true;
		}
		if (board[0][2] == piece && board[1][1] == piece && board[2][0] == piece){
			return true;
		}
		
		return false;
	}
	
	
	// This is the default, places piece on random grid. 
	// Subclasses of this class will have smarter algorithm. 
	public void placePiece() 
	{
		Random rand = new Random();
		int  randNum = rand.nextInt(9) +1 ;
		int row = extractRow(randNum); 
		int col = extractCol(randNum); 
		placePieceHelper(row, col) ;
	}
	
	
	// Brute force (idk if this is called brute force, this
	// is just really unefficient) recursion to find an 
	// acceptable place selected randomly 
	private boolean placePieceHelper(int row, int col){
		if (board[row][col] == ' '){
			board[row][col] = aiPiece; 
			return true;
		}
		Random rand = new Random();
		int  randNum = rand.nextInt(9) +1 ;
		row = extractRow(randNum); 
		col = extractCol(randNum); 
		return placePieceHelper(row, col) ;
	}
	
	
//	[1][2][3]	Because I'm too lazy to think of
//	[4][5][6]	a better way to randomize both 
//	[7][8][9]	rows and columns at the same time
	private int extractRow(int combined) {
		return (combined - extractCol(combined)) /3 ;
	}
	
	private int extractCol(int combined) {
		return (combined -1) % 3 ;
	}
	
	private int getCombined(int row, int col) {
		return row*3 + col + 1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
