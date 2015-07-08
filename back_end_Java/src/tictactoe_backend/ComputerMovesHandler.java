package tictactoe_backend;

public class ComputerMovesHandler {
	

	
	private final int SIZE_OF_BOARD = 3; 
	private int difficulty = -1; // unassigned
	private char[][] board = new char[3][3]; 
	private char aiPiece;
	
	// Constructor: 
	public ComputerMovesHandler(char[][] board_, char piece_, int difficulty_)
	{
		board = board_; 
		aiPiece = piece_;
		difficulty = difficulty_ ; 
		clearBoard();		
	}
	
	public void  doYourThing() 
	{
		// TO DO 
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
