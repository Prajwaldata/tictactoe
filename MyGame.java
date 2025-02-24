package TicTacToe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyGame extends JFrame implements ActionListener
{
	//In this Class we will create all th GUI
	JLabel heading ;
	JLabel clock;
	Font font = new Font("",Font.BOLD,40);
	JPanel mainPanel;
	JButton[] btn = new JButton[9];
	
	
//	game instance Variable
	
	int gameChances[]= {2,2,2,2,2,2,2,2,2};
	int activePlayer = 0;
	
	
	//Winning Position Array 
	
	int winner = 2;
	boolean gameOver = false;
	int wps[][]=
			{
					{0, 1, 2},
					{3, 4, 5},
					{6, 7 ,8},
					{0, 3, 6},
					{2, 5, 8},
					{1, 4, 7},
					{0, 4, 8},
					{2, 4, 6}
			};
	MyGame()
	{
		setTitle("TicTacToe");
		setSize(700,700);
		
		setLocation(20,20);
		
		ImageIcon icon = new ImageIcon("res/TicTacToe/TicTacToeLogo.jpg");
		setIconImage(icon.getImage());
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createGUI();
		setVisible(true);
	}
	private void createGUI() 
	{
		this.getContentPane().setBackground(Color.decode("#2196f3"));
		this.setLayout(new BorderLayout());
		heading = new JLabel("Tic Tac Toe");
		heading.setFont(font);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setForeground(Color.WHITE);
		heading.setIcon(new ImageIcon("res/TicTacToe/TicTacToeLogo.jpg"));
		this.add(heading,BorderLayout.NORTH);
		heading.setHorizontalTextPosition(SwingConstants.CENTER);
		heading.setVerticalTextPosition(SwingConstants.TOP);
		
		
		//Creating the Object of the jLabel For the lcock
		clock = new JLabel("Clock");
		clock.setHorizontalAlignment(SwingConstants.CENTER);
		clock.setFont(font);
		clock.setForeground(Color.WHITE);
		this.add(clock,BorderLayout.SOUTH);
		
		
		// Creating the thred for the Clock
		Thread t = new Thread() 
		{
			public void run(){
				while(true)
				{
					try {
						String datetime = new Date().toLocaleString();
//						System.out.println(datetime);
						clock.setText(datetime);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("Caught Exception");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();  // Don't forget to start the thread!
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,3));
		for(int i = 1 ; i <= 9 ; i ++) 
		{
			JButton bttn = new JButton();
//			bttn.setIcon(new ImageIcon("res/TicTacToe/TicTacToeCross.png"));
			
			bttn.setFont(font);
			mainPanel.add(bttn);
			bttn.setBackground(Color.white);
			btn[i-1] = bttn;
			
			bttn.addActionListener(this);
			bttn.setName(String.valueOf(i-1));
		}
		this.add(mainPanel, BorderLayout.CENTER);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("clicked");
		JButton cbtn = (JButton) e.getSource();
		String namestr = cbtn.getName();
		System.out.println(namestr);
		if(gameOver)
		{
			JOptionPane.showMessageDialog(this,"Game Over" );
			return;
		}
		int name = Integer.parseInt(namestr.trim());
		
		if(gameChances[name] == 2)
		{
			if(activePlayer != 1)
			{
				cbtn.setIcon(new ImageIcon("res/TicTacToe/TicTacToeCircle.png"));
				gameChances[name] = activePlayer;
				activePlayer = 1;
			}
			else
			{
				cbtn.setIcon(new ImageIcon("res/TicTacToe/TicTacToeCross.png"));
				gameChances[name] = activePlayer;
				activePlayer = 0;
			}
			
//			Find the Winner
			
			for(int [] temp :wps)
			{
				    if ((gameChances[temp[0]] == gameChances[temp[1]]) &&
				        (gameChances[temp[1]] == gameChances[temp[2]]) && 
				        (gameChances[temp[2]] != 2)) 
				    { 
				        winner = gameChances[temp[0]];
				        gameOver = true;
				        JOptionPane.showMessageDialog(null, "Player " + winner + " Has Won the Game");
				        // Additional logic to handle the winner
				        // You might want to break out of the loop if a winner is found
				        
				        int i = JOptionPane.showConfirmDialog(this,  "Do You Want to Play More");
				        if(i == 0)
				        {
				        	this.setVisible(false);
				        	new MyGame();
				        }
				        else if(i == 2)
				        {
				        	System.exit(0);
				        }
				        else
				        {}
				        break;
				    }
				    
			}
			int c = 0;
			for( int x : gameChances)
			{
				if(x == 2)
				{
					c++;break;
				}
				
			}
			if(c ==0 &!gameOver )
			{
				JOptionPane.showMessageDialog(null,"Its Draw");
				int i = JOptionPane.showConfirmDialog(this, "Play More?");
				if(i == 1)
				{
					System.exit(0);
				}
				else if(i == 0) 
				{
					this.setVisible(false);
					new MyGame();
				}
				else {}
				gameOver = true;
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Position Already ocuupied");
		}
	}
	
}
