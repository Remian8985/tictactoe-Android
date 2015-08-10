package tictactoe_backend;

public class HumanPlayer extends Player {

	public HumanPlayer(char[][] board, char piece) {
		super(board, piece);
		// TODO Auto-generated constructor stub
	}
	
	public HumanPlayer(char piece){
		super(piece); 
	}
	

	@Override
	public int aiPlacePiece() {
		// For human player, there is no AI calculation 
		// so...
		return -1 ;		// no position to give, this will be ignored
	}
	
	// In fact, no need to do anything at all. 
	// Caller will just use placePiece(..) and getBoard() methods
}
