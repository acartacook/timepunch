package ui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class loginGUI extends JFrame
{
	/**
	 * 
	 */
	
	private JLabel loginL;
	private JTextField loginText;
	private JButton LoginB, exitB;
	
	private JButton ClockInB, ClockInCB, ClockOutB;
	
	public String password;
	
	//Button handlers:
	private LoginButtonHandler loginHandler;
	private ExitButtonHandler ebHandler;
	
	//Clock In Button handlers:
	private ClockInHandler ciHandler;
	private ClockInCBHandler callbackHandler;
	private ClockOutHandler coHandler;

		
	public void closeWindow(){
		System.exit(0);
	}
	
	public void createLoginPopup()
	{
		//Login Labels
		loginL = new JLabel("Enter Your Login: ", SwingConstants.CENTER);
		loginText = new JTextField(10);

		
		
		//SPecify handlers for each button and add (register) ActionListeners to each button.
		LoginB = new JButton("Login");
		loginHandler = new LoginButtonHandler();
		LoginB.addActionListener(loginHandler);
		
		
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		
		//Create window itself
		setTitle("Turtle Time Punch");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(4, 2));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(loginL);
		pane.add(loginText);
		pane.add(LoginB);
		pane.add(exitB);
		
		//Launch the actual window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class LoginButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			password = (loginText.getText()); //We use the getText & setText methods to manipulate the data entered into those fields.
			ValdostaConsole.accept(password, "");
		}
	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	

	public void createClockInPopup()
	{
		//Login Labels

		loginL.setText("Welcome to Turtle Time Punch!");
		LoginB.setVisible(false);
		loginText.setVisible(false);
		exitB.setVisible(false);
		
		//SPecify handlers for each button and add (register) ActionListeners to each button.
		ClockInB = new JButton("Clock In (Normal)");
		ciHandler = new ClockInHandler();
		ClockInB.addActionListener(ciHandler);
		
		ClockInCB = new JButton("Clock In (Callback)");
		callbackHandler = new ClockInCBHandler();
		ClockInCB.addActionListener(callbackHandler);
		
		ClockOutB = new JButton("Clock Out");
		coHandler = new ClockOutHandler();
		ClockOutB.addActionListener(coHandler);
		
		
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		
		//Create window itself
		setTitle("Turtle Time Punch");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(10, 1, 10, 10));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(ClockInB);
		pane.add(ClockInCB);
		pane.add(ClockOutB);
		exitB.setVisible(true);
		pane.add(exitB);
		
		
		//Launch the actual window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	private class ClockInHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			ValdostaConsole.accept("#IN", "");
		}
	}
	
	private class ClockInCBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			ValdostaConsole.accept("#IN" , "CB");
		}
	}
	private class ClockOutHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			ValdostaConsole.accept("#OUT", "");
		}
	}

	
}