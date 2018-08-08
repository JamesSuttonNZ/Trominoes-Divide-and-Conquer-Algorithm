package Trominoes;

import java.awt.Color;

import ecs100.UI;

public class Trominoes {
	
	public static int ts = 20; // tile size
	public static int height;
	public static int nStart;
	
	public static void main(String[] args){
		//test
		UI.println("Trominoes:");
		
		//size of board
		nStart = Integer.parseInt(args[0]);
		//position of blank square
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		int[] l = {x,y};
		int[] botLeft = {0,0};
		height = UI.getCanvasHeight();

		tile(nStart,l,botLeft);

		UI.drawRect(0, height-(nStart*ts), nStart*ts, nStart*ts);
		UI.fillRect(x*ts, (height-ts)-y*ts, ts, ts);
	}

	private static void tile(int n, int[] l, int[] botLeft) {
		if(n == 2){
			tileWithT(l);
			return;
		}
		subBoards(n, l, botLeft);
	}

	private static void tileWithT(int[] l) {
		if((l[0] % 2) == 0 && (l[1] % 2) == 0){ // both even UR trom
			UI.println((l[0]+1)+" "+(l[1]+1)+" UR");
			drawUR((l[0]+1),(l[1]+1));
		}
		else if((l[0] % 2) == 0 && !((l[1] % 2) == 0)){ // x even y odd LR trom
			UI.println((l[0]+1)+" "+l[1]+" LR");
			drawLR((l[0]+1),l[1]);
		}
		else if(!((l[0] % 2) == 0) && (l[1] % 2) == 0){ // x odd y even UL trom
			UI.println(l[0]+" "+(l[1]+1)+" UL");
			drawUL(l[0],(l[1]+1));
		}
		else if(!((l[0] % 2) == 0 && (l[1] % 2) == 0)){ // both odd LL trom
			UI.println(l[0]+" "+l[1]+" LL");
			drawLL(l[0],l[1]);
		}
	}

	/*
	 * sorry for spaghetti code realised the way I had implemented it didn't hold up for larger than 4x4 and the fix was a bit messy
	 */
	private static void subBoards(int n, int[] l, int[] botLeft) {
		int n2 = n/2;
		int[] bl1 = {botLeft[0]+n2, botLeft[1]};
		int[] bl2 = {botLeft[0], botLeft[1]+n2};
		int[] bl3 = {botLeft[0]+n2, botLeft[1]+n2};
		if(l[0] < botLeft[0]+n2 && l[1] < botLeft[1]+n2){ // missing square LL
			UI.println(n2+" "+n2+" UR");
			drawUR(botLeft[0]+n2,botLeft[1]+n2);
			int[] m1 = l;
			int[] m2 = {botLeft[0]+n2-1,botLeft[1]+n2};
			int[] m3 = {botLeft[0]+n2,botLeft[1]+n2};
			int[] m4 = {botLeft[0]+n2,botLeft[1]+n2-1};
			tile(n2, m1, botLeft);
			tile(n2, m2, bl2);
			tile(n2, m3, bl3);
			tile(n2, m4, bl1);
		}
		else if(l[0] < botLeft[0]+n2 && l[1] >= botLeft[1]+n2){ // missing square UL
			UI.println(n2+" "+n2+" LR");
			drawLR(botLeft[0]+n2,botLeft[1]+n2);
			int[] m1 = l;
			int[] m2 = {botLeft[0]+n2-1,botLeft[1]+n2-1};
			int[] m3 = {botLeft[0]+n2,botLeft[1]+n2};
			int[] m4 = {botLeft[0]+n2,botLeft[1]+n2-1};
			tile(n2, m1, bl2);
			tile(n2, m2, botLeft);
			tile(n2, m3, bl3);
			tile(n2, m4, bl1);
		}
		else if(l[0] >= botLeft[0]+n2 && l[1] < botLeft[1]+n2){ // missing square LR
			UI.println(n2+" "+n2+" UL");
			drawUL(botLeft[0]+n2,botLeft[1]+n2);
			int[] m1 = l;
			int[] m2 = {botLeft[0]+n2-1,botLeft[1]+n2-1};
			int[] m3 = {botLeft[0]+n2,botLeft[1]+n2};
			int[] m4 = {botLeft[0]+n2-1,botLeft[1]+n2};
			tile(n2, m1, bl2);
			tile(n2, m2, botLeft);
			tile(n2, m3, bl3);
			tile(n2, m4, bl1);
		}
		else if(l[0] >= botLeft[0]+n2 && l[1] >= botLeft[1]+n2){ //missing square UR
			UI.println(n2+" "+n2+" LL");
			drawLL(botLeft[0]+n2,botLeft[1]+n2);
			int[] m1 = l;
			int[] m2 = {botLeft[0]+n2-1,botLeft[1]+n2-1};
			int[] m3 = {botLeft[0]+n2,botLeft[1]+n2-1};
			int[] m4 = {botLeft[0]+n2-1,botLeft[1]+n2};
			tile(n2, m1, bl3);
			tile(n2, m2, botLeft);
			tile(n2, m3, bl2);
			tile(n2, m4, bl1);
		}
	}

	private static void drawLL(int x, int y) {
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts-ts, (height-ts)-y*ts, x*ts-ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts+ts, (height+ts)-y*ts, x*ts+ts, (height)-y*ts);
		UI.drawLine(x*ts+ts, (height)-y*ts, x*ts, (height)-y*ts);
	}

	private static void drawUL(int x, int y) {
		UI.drawLine(x*ts+ts, (height)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts+ts, (height)-y*ts, x*ts+ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts-ts, (height-ts)-y*ts, x*ts-ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts, (height+ts)-y*ts);
		UI.drawLine(x*ts, (height+ts)-y*ts, x*ts, (height)-y*ts);
	}

	private static void drawLR(int x, int y) {
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts, (height-ts)-y*ts, x*ts+ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts-ts, (height+ts)-y*ts, x*ts-ts, (height)-y*ts);
		UI.drawLine(x*ts-ts, (height)-y*ts, x*ts, (height)-y*ts);
	}

	private static void drawUR(int x, int y) {
		UI.drawLine(x*ts-ts, (height)-y*ts, x*ts, (height)-y*ts);
		UI.drawLine(x*ts-ts, (height)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts-ts, (height-ts)-y*ts);
		UI.drawLine(x*ts+ts, (height-ts)-y*ts, x*ts+ts, (height+ts)-y*ts);
		UI.drawLine(x*ts+ts, (height+ts)-y*ts, x*ts, (height+ts)-y*ts);
		UI.drawLine(x*ts, (height+ts)-y*ts, x*ts, (height)-y*ts);
	}
}
