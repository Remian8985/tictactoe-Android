/**
 * @author Ameem
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
		boolean player1Turn = setPieces();
		clearBoard();

		PlayerMovesHandler player1 = new PlayerMovesHandler(board, playerPiece);
		PlayerMovesHandler player2 = new PlayerMovesHandler(board, aiPiece);
		int turnCount = 0;
		printBoard(board);
		while (turnCount < SIZE_OF_BOARD * SIZE_OF_BOARD) {
			if (player1Turn)
				placePlayerPiece(player1);
			else
				placePlayerPiece(player2);

			player1Turn = !player1Turn;
			printBoard(board);
			turnCount++;

		}

		System.out.println("Hello World!");
	}

	public static boolean setPieces() {
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

	// This method needs to be ported for android without all these prompts
	// Checks need to be done differently for that btw.
	public static void placePlayerPiece(PlayerMovesHandler player) {
		char piece = player.getPiece();

		Scanner in = new Scanner(System.in);
		boolean invalidInput = true;

		while (invalidInput) {
			System.out.println("Enter row and column no. where you want to place your mark (Top left is [1,1])");

			int row = in.nextInt();
			int col = in.nextInt();
			if (row > SIZE_OF_BOARD || col > SIZE_OF_BOARD || row < 1 || col < 1) {
				System.out.println("Invalid move, out of bounds.");
			}
			if (board[row][col] == ' ') {
				board[row][col] = piece; // Possible redundancy here
				player.setPiece(row, col); // ^^^^^^^^^^^^^^^^^^^^^^^^
				invalidInput = false;
			} else {
				System.out.println("Invalid move, that location is not empty.");
			}
		}
	}

	public static void clearBoard() // idk if I will ever use it here
	{
		for (int i = 0; i < SIZE_OF_BOARD; ++i) {
			for (int j = 0; j < SIZE_OF_BOARD; ++j) {
				board[i][j] = ' ';
			}
		}
	}

}
