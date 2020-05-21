package connFour;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

class Slot extends JButton {
	  private int i;
	  private int j;
	  private Piece piece = Piece.None;

	  public Slot(int i, int j) {
	    this.i = i;
	    this.j = j;
	    setOpaque(true);
	    setBorder(new LineBorder(Color.BLACK));
	    setBorderPainted(true);
	    setColor();
	  }

	  public int getI() {
	    return i;
	  }

	  public int getJ() {
	    return j;
	  }

	  public Piece getPiece() {
	    return piece;
	  }

	  public void setPiece(Piece piece) {
	    this.piece = piece;
	    setColor();
	  }

	  public void setColor() {
	    switch(piece) {
	      case Red:
	        setBackground(Color.red);
	        break;
	      case Blue:
	        setBackground(Color.blue);
	        break;
	      case None:
	        setBackground(Color.white);
	        break;
	    }
	  }
	}