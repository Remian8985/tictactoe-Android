package tictactoe_backend;

import java.util.Arrays;
import java.util.Random;

public class RandomMoveComputer extends Player {
	
	private int[] allowedPositions = {00, 01, 02, 10, 11, 12, 20, 21, 22};
	private int allowedSize ;
	
	

	public RandomMoveComputer(char[][] board, char piece) {
		super(board, piece);
		
		allowedSize = SIZE_OF_BOARD * SIZE_OF_BOARD;
		allowedSize = countEmpty(); // allows for creation of new player 
									// in the middle of the game 
	}
	
	public RandomMoveComputer(char piece) {
		super( piece);
		
		allowedSize = SIZE_OF_BOARD * SIZE_OF_BOARD;
		allowedSize = countEmpty(); // allows for creation of new player 
									// in the middle of the game 
	}
	
	
	

	@Override
	public int aiPlacePiece() {
		if (allowedSize <=0)
			return -1 ; 	// error 
		
		countEmpty();
		Random rand = new Random();
		int  i = rand.nextInt(allowedSize); 
		int position = allowedPositions[i];
		swapEmptyWithNonempty(i);
		
		return position;
	}
	
	
	private int countEmpty(){
		int count = 0 ;
		for (int i =0; i < SIZE_OF_BOARD; ++i ){
			for (int j =0; j < SIZE_OF_BOARD; ++j ){
				if (BLANK == board[i][j] )
					count++; 
				else {
					int index = Arrays.binarySearch(allowedPositions, 0, allowedSize, 10*i +j ) ;
					
					if (index >=0 )	// otherwise not found in the range
						swapEmptyWithNonempty(index); 
				}
			}
		}
		return count ;
	}
	
	
	private void swapEmptyWithNonempty(int i){
		int temp = allowedPositions[i]; 
		allowedPositions[i] = allowedPositions[allowedSize -1]; 
		allowedPositions[allowedSize -1] = temp; 
		allowedSize--; 
	}
	
	
	
	

}
