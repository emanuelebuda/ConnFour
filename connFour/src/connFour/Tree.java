package connFour;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Tree {
	  private int value;
	  private Slot[][] slots;
	  private ArrayList<Integer> bestMoves;
	  private Slot prev = null;
	  private int depth;
	  public static int MAX_DEPTH = 6;
	  
	public Tree(Slot[][] slots, int depth) {
	    this.slots = slots;
	    this.bestMoves = new ArrayList<Integer>();
	    this.depth = depth;
	    this.value = getValue();

	    if (depth < MAX_DEPTH && this.value < 100 && this.value > -100 ) {
	      ArrayList<Integer> possibilities = new ArrayList<Integer>();

	      for (int i = 0; i < 7; i++) {
	        if (slots[i][0].getPiece() == Piece.None) {
	          possibilities.add(i);
	        }
	      }

	      for (int i = 0; i < possibilities.size(); i++) {
	        insertTo(slots[possibilities.get(i)][0]);
	        Tree child = new Tree(slots, depth+1);
	        prev.setPiece(Piece.None);

	        if (i == 0) {
	          bestMoves.add(possibilities.get(i));
	          value = child.value;
	        } else if (depth % 2 == 0) {
	          if (value < child.value) {
	            bestMoves.clear();
	            bestMoves.add(possibilities.get(i));
	            this.value = child.value;
	          } else if (value == child.value) {
	            bestMoves.add(possibilities.get(i));
	          }
	        } else if (depth % 2 == 1) {
	          if (value > child.value) {
	            bestMoves.clear();
	            bestMoves.add(possibilities.get(i));
	            this.value = child.value;
	          } else if (value == child.value) {
	            bestMoves.add(possibilities.get(i));
	          }
	        }
	      }
	    }
	    else {
	      this.value = getValue();
	    }
	  }
	  private void printSlots() {
		    for (int j = 0; j < 6; j++) {
		      for (int i = 0; i < 7; i++) {
		        switch(slots[i][j].getPiece()) {
		          case Blue: System.out.print("B"); break;
		          case Red: System.out.print("R"); break;
		          default: System.out.print("-"); break;
		        }
		      }
		      System.out.println();
		    }
		  }
	  private void insertTo(Slot slot) {
		    if (slot.getPiece() != Piece.None)
		      return;

		    int i = slot.getI();
		    int j = slot.getJ();

		    while(j < slots[0].length-1 && slots[i][j+1].getPiece() == Piece.None)
		      j++;

		    if (depth % 2 == 0)
		      slots[i][j].setPiece(Piece.Red);
		    else
		      slots[i][j].setPiece(Piece.Blue);
		    prev = slots[i][j];
		  }

		  public int getX() {
		    int random = (int)(Math.random() * 100) % bestMoves.size();
		    return bestMoves.get(random);
		  }

		  public int getValue() {
		    int value = 0;
		    for (int j = 0; j < 6; j++) {
		      for (int i = 0; i < 7; i++) {
		        if (slots[i][j].getPiece() != Piece.None) {
		          if (slots[i][j].getPiece() == Piece.Red) {
		            value += possibleConnections(i, j) * (MAX_DEPTH - this.depth);
		          } else {
		            value -= possibleConnections(i, j) * (MAX_DEPTH - this.depth);
		          }
		        }
		      }
		    }
		    return value;
		  }

		  private int possibleConnections(int i, int j) {
		    int value = 0;
		    value += lineOfFour(i, j, -1, -1);
		    value += lineOfFour(i, j, -1, 0);
		    value += lineOfFour(i, j, -1, 1);
		    value += lineOfFour(i, j, 0, -1);
		    value += lineOfFour(i, j, 0, 1);
		    value += lineOfFour(i, j, 1, -1);
		    value += lineOfFour(i, j, 1, 0);
		    value += lineOfFour(i, j, 1, 1);

		    return value;
		  }

		  private int lineOfFour(int x, int y, int i, int j) {
		    int value = 1;
		    Piece color = slots[x][y].getPiece();

		    for (int k = 1; k < 4; k++) {
		      if (x+i*k < 0 || y+j*k < 0 || x+i*k >= slots.length || y+j*k >= slots[0].length) {
		        return 0;
		      }
		      if (slots[x+i*k][y+j*k].getPiece() == color) {
		        value++;
		      } else if (slots[x+i*k][y+j*k].getPiece() != Piece.None) {
		        return 0;
		      } else {
		        for (int l = y+j*k; l >= 0; l--) {
		          if (slots[x+i*k][l].getPiece() == Piece.None) {
		            value--;
		          }
		        }
		      }
		    }

		    if (value == 4) return 100;
		    if (value < 0) return 0;
		    return value;
		  }
		}
