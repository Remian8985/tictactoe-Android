package tictactoe_backend;
/*	Date created : July 8, 2015
 * 	This class handles human player's moves.
 * 	This can be instantiated twice in 2 player games 
 */

public class PlayerMovesHandler {
	
	private final int SIZE_OF_BOARD = 3; 
	private char[][] board = new char[3][3]; 
	private char playerPiece;
	
	// Constructor: 
	public PlayerMovesHandler(char[][] board_, char piece_)
	{
		board = board_; 
		playerPiece = piece_;
		clearBoard();		
	}
	
	
	public char[][] getBoard(){
		return board;
	}
	
	public char getPiece(){
		return playerPiece;
	}
	
	public void setBoard(char[][] board_)
	{
		board = board_;
	}
	
	public boolean setPiece(int row, int col){
		if (row >= SIZE_OF_BOARD || col >= SIZE_OF_BOARD || row < 0 || col < 0) {
			System.out.println("Invalid move, out of bounds.");
			return false;
		}
		if (board[row][col] == ' ') {
			board[row][col] = playerPiece;
			return true;
		} else {
			System.out.println("Invalid move, that location is not empty.");
			return false;
		}

	}
	
	private void clearBoard()	// idk if I will ever use it here 
	{
		for (int i =0; i < SIZE_OF_BOARD; ++i )
		{
			for (int j =0 ; j < SIZE_OF_BOARD; ++j) 
			{
				board [i][j] = ' ' ;
			}
		}
	}
	
	
	
	
	

}
