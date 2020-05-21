package connFour;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class ConnectFour extends JFrame implements ActionListener {
		  private JLabel lblPlayer = new JLabel("Player: ");
		  private JLabel lblCurrentPlayer = new JLabel("Blue");
		  private JPanel pnlMenu = new JPanel();
		  private JPanel pnlSlots = new JPanel();
		  private JButton btnNewGame = new JButton("Nuevo Juego (2 players)");
		  private JButton btnNewGame2 = new JButton("Nuevo Juego (Enemy: AI)");
		  private JButton btnNewGame3 = new JButton("Nuevo Juego (Enemy: DUMB)");

		  private Slot[][] slots = new Slot[7][6];

		  private boolean winnerExists = false;
		  private int currentPlayer = 1;
		  private boolean AI;
		  private int mod;

		  public ConnectFour(boolean AI, int x) {
		    super("Conecta4 - Grupo Erasmus");
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    currentPlayer = (int) (Math.random()*2) + 1;

		    this.AI = AI;
		    this.mod = x;

		    btnNewGame.addActionListener(this);
		    btnNewGame2.addActionListener(this);
		    btnNewGame3.addActionListener(this);
		    switch(currentPlayer) {
		      case 1:
		        lblCurrentPlayer.setForeground(Color.blue);
		        lblCurrentPlayer.setText("Blue");
		        break;
		      case 2:
		        lblCurrentPlayer.setForeground(Color.red);
		        lblCurrentPlayer.setText("Red");
		        break;
		    }
		    pnlMenu.add(btnNewGame);
		    pnlMenu.add(btnNewGame2);
		    pnlMenu.add(btnNewGame3);
		    pnlMenu.add(lblPlayer);
		    pnlMenu.add(lblCurrentPlayer);

		    pnlSlots.setLayout(new GridLayout(6, 7));

		    for (int j = 0; j < 6; j++) {
		      for (int i = 0; i < 7; i++) {
		        slots[i][j] = new Slot(i, j);
		        slots[i][j].addActionListener(this);
		        pnlSlots.add(slots[i][j]);
		      }
		    }

		    add(pnlMenu, BorderLayout.NORTH);
		    add(pnlSlots, BorderLayout.CENTER);
		    setSize(750, 750);
		    setVisible(true);

		    if (currentPlayer == 2 && AI && x == 0) insertTo(minimax());
		    if (currentPlayer == 2 && AI && x == 1) { 
		    	System.out.println("70");
		    	dumbInsertTo(minimax());
		    }
		    getContentPane().setSize(800,800);
		  }

		  public void actionPerformed(ActionEvent ae) {
		    if (ae.getSource() == btnNewGame) {
		      if (JOptionPane.showConfirmDialog(this, "Estás Confirmation de que quieres cerrar?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
		        dispose();
		        new ConnectFour(false, 0);
		        return;
		      }
		    }
		    
		    if (ae.getSource() == btnNewGame2) {
		      if (JOptionPane.showConfirmDialog(this, "Estás Confirmation de que quieres cerrar?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
		        dispose();
		        new ConnectFour(true, 0);
		        return;
		      }
		    }else if (!winnerExists && ae.getSource() != btnNewGame3 && ae.getSource() != btnNewGame) {
		      Slot slot = (Slot)ae.getSource();
		      insertTo(slot);
		    }
		    
		    if (ae.getSource() == btnNewGame3) {
			  if (JOptionPane.showConfirmDialog(this, "Estás Confirmation de que quieres cerrar?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
				  dispose();
			      new ConnectFour(true, 1);
			      return;
			  }
		    }
		  }

		  @SuppressWarnings("deprecation")
		void insertTo(Slot slot) {
		    if (slot.getPiece() != Piece.None) {
		      return;
		    }

		    int i = slot.getI();
		    int j = slot.getJ();

		    while(j < slots[0].length-1 && slots[i][j+1].getPiece() == Piece.None) {
		      j++;
		    }

		    switch(currentPlayer) {
		      case 1:
		        slots[i][j].setPiece(Piece.Blue);
		        break;
		      case 2:
		        slots[i][j].setPiece(Piece.Red);
		        break;
		    }

		    currentPlayer = (currentPlayer % 2) + 1;

		    if (thereIsAWinner()) {
		      lblPlayer.setText("Winner: ");
		      CreateAnimation anim = new CreateAnimation();
		      //anim.setSize(250, 200);
			  anim.show();
		      anim.go(lblCurrentPlayer.getText());
		      winnerExists = true;
		    } else {
		      switch(currentPlayer) {
		        case 1:
		          lblCurrentPlayer.setForeground(Color.blue);
		          lblCurrentPlayer.setText("Blue");
		          break;
		        case 2:
		          lblCurrentPlayer.setForeground(Color.red);
		          lblCurrentPlayer.setText("Red");
		          break;
		      }

		      if (currentPlayer == 2 && AI && this.mod == 0) {
		        insertTo(minimax());
		      }else if(currentPlayer == 2 && AI && this.mod == 1) {
		    	  System.out.println("151");
		    	  dumbInsertTo(minimax());
		      }
		    }
		  }
		  
		  void dumbInsertTo(Slot slot) {
			  System.out.println("dumb");
			  if (slot.getPiece() != Piece.None) {
			      return;
			    }

			    int i = slot.getI();
			    int j = slot.getJ();

			    while(j < slots[0].length-1 && slots[i][j+1].getPiece() == Piece.None) {
			      j++;
			    }

			    switch(currentPlayer) {
			      case 1:
			        slots[i][j].setPiece(Piece.Blue);
			        break;
			      case 2:
			        slots[i][j].setPiece(Piece.Red);
			        break;
			    }

			    currentPlayer = (currentPlayer % 2) + 1;

			    if (thereIsAWinner()) {
			      lblPlayer.setText("Winner: ");
			      CreateAnimation anim = new CreateAnimation();
			      //anim.setSize(250, 200);
				  anim.show();
			      anim.go(lblCurrentPlayer.getText());
			      winnerExists = true;
			    } else {
			      switch(currentPlayer) {
			        case 1:
			          lblCurrentPlayer.setForeground(Color.blue);
			          lblCurrentPlayer.setText("Blue");
			          break;
			        case 2:
			          lblCurrentPlayer.setForeground(Color.red);
			          lblCurrentPlayer.setText("Red");
			          break;
			      }

			      if (currentPlayer == 2 && AI && this.mod == 0) {
			        insertTo(minimax());
			      }else if(currentPlayer == 2 && AI && this.mod == 1) {
			    	  System.out.println("151");
			    	  dumbInsertTo(minimax());
			      }
			    }
		  }

		  public boolean thereIsAWinner() {
		    for (int j = 0; j < 6; j++) {
		      for (int i = 0; i < 7; i++) {
		        if (slots[i][j].getPiece() != Piece.None && connectsToFour(i, j)) {
		          return true;
		        }
		      }
		    }
		    return false;
		  }

		  public boolean connectsToFour(int i, int j) {
		    if (lineOfFour(i, j, -1, -1)) return true;
		    if (lineOfFour(i, j, -1, 0)) return true;
		    if (lineOfFour(i, j, -1, 1)) return true;
		    if (lineOfFour(i, j, 0, -1)) return true;
		    if (lineOfFour(i, j, 0, 1)) return true;
		    if (lineOfFour(i, j, 1, -1)) return true;
		    if (lineOfFour(i, j, 1, 0)) return true;
		    if (lineOfFour(i, j, 1, 1)) return true;
		    return false;
		  }

		  public boolean lineOfFour(int x, int y, int i, int j) {
		    Piece color = slots[x][y].getPiece();

		    for (int k = 1; k < 4; k++) {
		      if (x+i*k < 0 || y+j*k < 0 || x+i*k >= slots.length || y+j*k >= slots[0].length) {
		        return false;
		      }
		      if (slots[x+i*k][y+j*k].getPiece() != color) {
		        return false;
		      }
		    }
		    return true;
		  }

		  public Slot minimax() {
		    Tree tree = new Tree(slots, 0);
		    return slots[tree.getX()][0];
		  }
}
