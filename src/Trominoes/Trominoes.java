package Trominoes;

import java.awt.Color;

import ecs100.UI;

/**
 * Tiles board of size 2^nx2^n with one square removed with trominoes, where n >= 1
 * Using a divide and conquer algorithm
 * @author James Sutton
 *
 */
public class Trominoes {
	
	public static int ts = 10; // tile size
	public static int height;
	public static int n;
	public static int boardSize;
	
	public static void main(String[] args){
		UI.println("Tromino D&C tiling algorithm");
		UI.println("2^n x 2^n board, one missing square\n");
		
		//Ask user for n value
		UI.println("Please Enter:");
		int n = UI.askInt("n (>=1): ");
		while(n < 1) {
			UI.println("please enter a valid number");
			n = UI.askInt("n (>=1): ");
		}
		boardSize = (int) Math.pow(2,n);
		
		//Ask user for missing square x coord
		UI.println("Missing square coords:");
		int x = UI.askInt("x (0-"+(boardSize-1)+") : ");
		while(x < 0 || x >= boardSize) {
			UI.println("please enter a valid number");
			x = UI.askInt("x (0-"+(boardSize-1)+") : ");
		}
		
		//Ask user for missing square y coord
		int y = UI.askInt("y (0-"+(boardSize-1)+") : ");
		while(y < 0 || y >= boardSize) {
			UI.println("please enter a valid number");
			y = UI.askInt("y (0-"+(boardSize-1)+") : ");
		}
		
		UI.println("\nTrominoes:");
		
		//command line arguments
		//n value
		//n = Integer.parseInt(args[0]);
		//boardSize = (int) Math.pow(2,n);
		
		//position of blank square
		//int x = Integer.parseInt(args[1]);
		//int y = Integer.parseInt(args[2]);
		
		int[] blankSquare = {x,y};
		
		//bottom left coordinates of the lower left quadrant of board
		int[] bl_LL = {0,0};
		
		height = UI.getCanvasHeight();

		tile(boardSize,blankSquare,bl_LL);

		//draw board outline
		UI.drawRect(0, height-(boardSize*ts), boardSize*ts, boardSize*ts);
		//fill in missing square
		UI.fillRect(x*ts, (height-ts)-y*ts, ts, ts);
	}

	private static void tile(int boardSize, int[] blankSquare, int[] bl_LL) {
		if(boardSize == 2){
			tileWithT(blankSquare);
			return;
		}
		subBoards(boardSize, blankSquare, bl_LL);
	}

	private static void tileWithT(int[] blankSquare) {
		
		if((blankSquare[0] % 2) == 0 && (blankSquare[1] % 2) == 0){ // both even UR tromino
			UI.println((blankSquare[0]+1)+" "+(blankSquare[1]+1)+" UR");
			drawUR((blankSquare[0]+1),(blankSquare[1]+1));
		}
		
		else if((blankSquare[0] % 2) == 0 && !((blankSquare[1] % 2) == 0)){ // x even y odd LR tromino
			UI.println((blankSquare[0]+1)+" "+blankSquare[1]+" LR");
			drawLR((blankSquare[0]+1),blankSquare[1]);
		}
		
		else if(!((blankSquare[0] % 2) == 0) && (blankSquare[1] % 2) == 0){ // x odd y even UL tromino
			UI.println(blankSquare[0]+" "+(blankSquare[1]+1)+" UL");
			drawUL(blankSquare[0],(blankSquare[1]+1));
		}
		
		else if(!((blankSquare[0] % 2) == 0 && (blankSquare[1] % 2) == 0)){ // both odd LL tromino
			UI.println(blankSquare[0]+" "+blankSquare[1]+" LL");
			drawLL(blankSquare[0],blankSquare[1]);
		}
	}

	/**
	 * 
	 * @param boardSize
	 * @param blankSquare
	 * @param bl_LL
	 */
	private static void subBoards(int boardSize, int[] blankSquare, int[] bl_LL) {
		//divide board into four quadrants
		int newBoardSize = boardSize/2;
		int[] bl_LR = {bl_LL[0]+newBoardSize, bl_LL[1]};//bottom left of LR quadrant
		int[] bl_UL = {bl_LL[0], bl_LL[1]+newBoardSize};//bottom left of UL quadrant
		int[] bl_UR = {bl_LL[0]+newBoardSize, bl_LL[1]+newBoardSize};//bottom left of UR quadrant
		
		//missing square in Lower Left Quadrant
		if(blankSquare[0] < bl_LL[0]+newBoardSize && blankSquare[1] < bl_LL[1]+newBoardSize){
			//draw tromino at centre of sub-board opposite to blank square
			UI.println(newBoardSize+" "+newBoardSize+" UR");
			drawUR(bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize);
			
			int[] m_LL = blankSquare; //coords of current missing square in lower left quadrant
			int[] m_UL = {bl_LL[0]+newBoardSize-1,bl_LL[1]+newBoardSize}; //coords of new missing square in upper left quadrant
			int[] m_UR = {bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize}; //coords of new missing square in upper right quadrant
			int[] m_LR = {bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize-1}; //coords of new missing square in lower right quadrant
			
			//tile new quadrants
			tile(newBoardSize, m_LL, bl_LL);
			tile(newBoardSize, m_UL, bl_UL);
			tile(newBoardSize, m_UR, bl_UR);
			tile(newBoardSize, m_LR, bl_LR);
		}
		
		//missing square in Upper Left Quadrant
		else if(blankSquare[0] < bl_LL[0]+newBoardSize && blankSquare[1] >= bl_LL[1]+newBoardSize){
			//draw tromino at centre of sub-board opposite to blank square
			UI.println(newBoardSize+" "+newBoardSize+" LR");
			drawLR(bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize);
			
			int[] m_UL = blankSquare; //coords of current missing square in upper left quadrant
			int[] m_LL = {bl_LL[0]+newBoardSize-1,bl_LL[1]+newBoardSize-1}; //coords of new missing square in lower left quadrant 
			int[] m_UR = {bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize}; //coords of new missing square in upper right quadrant
			int[] m_LR = {bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize-1}; //coords of new missing square in lower right quadrant
			
			//tile new quadrants
			tile(newBoardSize, m_UL, bl_UL);
			tile(newBoardSize, m_LL, bl_LL);
			tile(newBoardSize, m_UR, bl_UR);
			tile(newBoardSize, m_LR, bl_LR);
		}
		
		//missing square in Lower Right Quadrant
		else if(blankSquare[0] >= bl_LL[0]+newBoardSize && blankSquare[1] < bl_LL[1]+newBoardSize){
			//draw tromino at centre of sub-board opposite to blank square
			UI.println(newBoardSize+" "+newBoardSize+" UL");
			drawUL(bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize);
			
			int[] m_LR = blankSquare; //coords of current missing square in lower right quadrant
			int[] m_LL = {bl_LL[0]+newBoardSize-1,bl_LL[1]+newBoardSize-1}; //coords of new missing square in lower left quadrant 
			int[] m_UR = {bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize}; //coords of new missing square in upper right quadrant
			int[] m_UL = {bl_LL[0]+newBoardSize-1,bl_LL[1]+newBoardSize}; //coords of new missing square in upper left quadrant
			
			//tile new quadrants
			tile(newBoardSize, m_UL, bl_UL);
			tile(newBoardSize, m_LL, bl_LL);
			tile(newBoardSize, m_UR, bl_UR);
			tile(newBoardSize, m_LR, bl_LR);
		}
		
		//missing square in Upper Right Quadrant
		else if(blankSquare[0] >= bl_LL[0]+newBoardSize && blankSquare[1] >= bl_LL[1]+newBoardSize){
			//draw tromino at centre of sub-board opposite to blank square
			UI.println(newBoardSize+" "+newBoardSize+" LL");
			drawLL(bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize);
			
			int[] m_UR = blankSquare; //coords of current missing square in upper right quadrant
			int[] m_LL = {bl_LL[0]+newBoardSize-1,bl_LL[1]+newBoardSize-1}; //coords of new missing square in lower left quadrant 
			int[] m_LR = {bl_LL[0]+newBoardSize,bl_LL[1]+newBoardSize-1}; //coords of new missing square in lower right quadrant
			int[] m_UL = {bl_LL[0]+newBoardSize-1,bl_LL[1]+newBoardSize}; //coords of new missing square in upper left quadrant
			
			//tile new quadrants
			tile(newBoardSize, m_UR, bl_UR);
			tile(newBoardSize, m_LL, bl_LL);
			tile(newBoardSize, m_LR, bl_LR);
			tile(newBoardSize, m_UL, bl_UL);
		}
	}

	/**
	 * Draw Lower Left Tromino 
	 *  _
	 * | |_
	 * |___|
	 * @param x
	 * @param y
	 */
	private static void drawLL(int x, int y) {
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts-ts, (height-ts)-y*ts, x*ts-ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts+ts, (height+ts)-y*ts, x*ts+ts, (height)-y*ts);
		UI.drawLine(x*ts+ts, (height)-y*ts, x*ts, (height)-y*ts);
	}

	/**
	 * Draw Upper Left Tromino
	 *  ___
	 * |  _|
	 * |_|
	 * @param x
	 * @param y
	 */
	private static void drawUL(int x, int y) {
		UI.drawLine(x*ts+ts, (height)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts+ts, (height)-y*ts, x*ts+ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts-ts, (height-ts)-y*ts, x*ts-ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts, (height+ts)-y*ts);
		UI.drawLine(x*ts, (height+ts)-y*ts, x*ts, (height)-y*ts);
	}

	/**
	 * Draw Lower Right Tromino
	 *    _
	 *  _| |
	 * |___|
	 * @param x
	 * @param y
	 */
	private static void drawLR(int x, int y) {
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts+ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts-ts, (height)-y*ts);
		UI.drawLine(x*ts-ts, (height)-y*ts, x*ts, (height)-y*ts);
	}
	
	/**
	 * Draw Upper Right Tromino
	 *  ___
	 * |_  |
	 *   |_|
	 * @param x
	 * @param y
	 */
	private static void drawUR(int x, int y) {
		UI.drawLine(x*ts-ts, (height)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts-ts, (height)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts+ts, (height+ts)-y*ts, x*ts, (height+ts)-y*ts);
		UI.drawLine(x*ts, (height+ts)-y*ts, x*ts, (height)-y*ts);
	}
}
