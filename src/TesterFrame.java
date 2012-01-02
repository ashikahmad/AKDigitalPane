import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ashkit.digital.AKDigitalPane;
import javax.swing.border.BevelBorder;


/**
 * This code is free for any purpose of use. Just Keep this comment block as it is
 * and It will be nice if you give me credit, where you used this (not mandatory.)
 * 
 * @author Ashik uddin Ahmad
 * email: ashikcu@gmail.com
 * 
 * other contributors:
 * 1. name: enhancement-description
 * 2. 
 */
@SuppressWarnings("serial")
public class TesterFrame extends JFrame implements ActionListener, Runnable {

	private AKDigitalPane p;
	private JButton btnReset;
	private JButton btnStart;
	private Thread _thread;
	private boolean isRunning;
	private int mm;
	private int ss;
	private int hh;
	private int xx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TesterFrame frame = new TesterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TesterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		p = new AKDigitalPane(15, 2.0f);
		p.setItalic(-10);
		p.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		p.setText("press to start");
		p.setFontColor(Color.BLACK.brighter());
		p.setBackground(Color.DARK_GRAY.brighter());
		
		contentPane.add(p, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnReset = new JButton("Reset"); 
		btnReset.addActionListener(this);
		panel.add(btnReset);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		panel.add(btnStart);
		
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getSource() == btnStart){
			
			if (!isRunning) {
				startThread();
			} else {
				stopThread();
			}
		} else if(ev.getSource() == btnReset){
			hh = 0;
			mm = 0;
			ss = 0;
			xx = 0;
			refreshDisplay();
			
			if(isRunning){
				stopThread();
			}
		}
	}

	private void startThread() {
		_thread = new Thread(this);
		btnStart.setText("Stop");
		isRunning = true;
		_thread.start();
	}

	private void stopThread() {
		isRunning = false;
		btnStart.setText("Start");
	}

	private void refreshDisplay() {
		String s = hh + ":";
		s = s + ((mm > 9)? "":"0") + mm + ":";
		s = s + ((ss > 9)? "":"0") + ss + ":";
		s = s + ((xx > 9)? "":"0") + xx;
		p.setText(s);
	}

	@Override
	public void run() {
		while (isRunning) {
			xx++;
			if (xx >= 100) {
				xx -= 100;
				ss++;
				if (ss >= 60) {
					ss -= 60;
					mm++;
					if (mm >= 60) {
						mm -= 60;
						hh++;
					}
				}
			}
			refreshDisplay();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
