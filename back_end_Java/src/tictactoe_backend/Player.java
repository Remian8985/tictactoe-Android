package tictactoe_backend;

public abstract class Player {
	
	public static final char BLANK = ' ';
	public static final char CROSS = 'X'; 
	public static final char NOUGHT= 'O';
	public static final int SIZE_OF_BOARD = 3; 
	
	protected static char[][] board = new char[3][3]; 	// CONSIDER MAKING THIS STATIC AND PROTECTED
	
	private char playerPiece;
	
	
	// Constructor: 
	public Player(char[][] board, char piece)
	{
		this.board = board; 
		this.playerPiece = piece;
	//	clearBoard();			// this messes things up if the board is static variable
	}
	
	public Player(char piece)
	{
		clearBoard();
		this.playerPiece = piece;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	// Abstract methods: 
	public abstract int aiPlacePiece();

	//////////////////////////////////////////////////////////////////////////////
	// Public methods: 
	
	public void placePiece(int row, int col) throws Exception{
		
		checkRowColValidity(row, col);
		board[row][col] = playerPiece;
	}
	
	public void setPiece(final char piece){
		playerPiece = piece;
	}
	
	public char[][] getBoard(){
		return board;
	}
	
	public char getPiece(){
		return playerPiece;
	}
	
	public void setBoard(char[][] board){
		this.board = board;
	}
	
	
	// Chekcs if the game is won for the given piece 
	// Worst case: 7 comparisons and return false
	//		...could be optimized by using variables
	public boolean isGameWon(final char piece){
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
	
	
	// Checks if the game is won for this player
	public boolean isGameWon(){
		return isGameWon(playerPiece); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Protected methods: 
	
	protected boolean isRowColValid(int row, int col){
		return (row >=0 && row <3 && col >=0 && col <3);
	}
	
	
	protected boolean isOccupied(int row, int col) { //throws Exception{
	//	checkRowColValidity(row,col); 
		return board[row][col] != BLANK;
	}
	
	
	protected void clearBoard()	// idk if I will ever use it here 
	{
		for (int i =0; i < SIZE_OF_BOARD; ++i )
		{
			for (int j =0 ; j < SIZE_OF_BOARD; ++j) 
			{
				board [i][j] = BLANK ;
			}
		}
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Private methods: 
	
	private void checkRowColValidity(int row, int col) throws Exception{
		if (!isRowColValid(row,col) || isOccupied(row,col)){
			System.out.println("placePiece(row,col) at Player.java");
			throw new Exception("Invalid row, col");
		}
	}

}
