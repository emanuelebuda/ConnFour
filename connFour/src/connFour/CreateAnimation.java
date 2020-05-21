package connFour;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.*;
	 
public class CreateAnimation extends JFrame {
	private JPanel main = new JPanel();
	private JLabel win = new JLabel("WINS");
	private static final int BLINKING_RATE = 1000;
	private static final long serialVersionUID = 1L;
	
	public void go(String w) {
		Color c;
		if(w.contentEquals("Red")) {
			c = Color.RED;
		}else {
			c = Color.BLUE;
		}
		win.setFont(new Font("Courier", Font.BOLD,80));
		main.add(win);
		t.start();
		main.setBackground(c);
		add(main, BorderLayout.CENTER);
	    setSize(200, 100);
	    setVisible(true);
	}
	
	Thread t = new Thread(new Runnable(){
        private int counter = 0;
        public void run() {
            while (true){
                counter++;
                SwingUtilities.invokeLater(new Runnable(){
                    public void run() {
                        if (counter % 2 == 0){
                            win.setForeground(Color.BLACK);
                            counter = 0;
                        } else {
                            win.setForeground(Color.WHITE);
                        }
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

}

