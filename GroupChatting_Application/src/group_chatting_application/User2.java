package group_chatting_application;


import javax.swing.*; // java extended pakage 
import javax.swing.border.*;
import java.awt.*;
// import java.awt.Desktop.Action;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar; 

public class User2  implements ActionListener, Runnable{
	JTextField text1 ;
	JPanel a1;
	static	Box vertical = Box.createVerticalBox();//to make messages one after one.
	//   action listener has an unimplemented abstract method so we have to override it.
	static JFrame f = new JFrame();
	static DataOutputStream dout ;

	BufferedReader reader;
	BufferedWriter writer;
	String name = "user2";
	User2(){
		
		f.setLayout(null);// we will be creating our own layout.
//		when ever setLayot is null we need to pass the coordinates using setBounds/
		
		
		JPanel p1=new JPanel();//to set pannel 
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0, 0, 450, 70);
		p1.setLayout(null);
		f.add(p1); //setting on the frame
		
		// here we are loading the image.
		ImageIcon i= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		// We can not pass i2 directly into JLabel so we need a 3rd object.
		ImageIcon i3= new ImageIcon(i2);//converting to image icon now we can pass it again.
		// 
		JLabel back = new JLabel(i3);
		// (from left, from right, image width, image heigh)
		back.setBounds(5,20,25,25);
		// to set on p1 pannel
		p1.add(back);
//  	 to get an action on clicking at back icon
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae){
				System.exit(0);
 }		
	});
	// here we are loading the profile.
	ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/dp.png"));
	Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	ImageIcon i6= new ImageIcon(i5);
	JLabel profile = new JLabel(i6);
	profile.setBounds(40,10,50,50);
	p1.add(profile);
	
	// here we are adding video tag
	ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
	Image i8 = i7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	ImageIcon i9= new ImageIcon(i8);
	JLabel video = new JLabel(i9);
	video.setBounds(300,20,35,30);
	p1.add(video);

	// here we are adding phone image
	ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
	Image i11 = i10.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	ImageIcon i12= new ImageIcon(i11);
	JLabel phone = new JLabel(i12);
	phone.setBounds(350,20,35,30);
	p1.add(phone);

	//here we are adding 3dots
	ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
	Image i14 = i13.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	ImageIcon i15= new ImageIcon(i14);
	JLabel morevert = new JLabel(i15);
	morevert.setBounds(400,20,35,30);
	p1.add(morevert); 
		
//  to write on the frame we use JLabel
    JLabel name = new JLabel("User1");
	name.setBounds(110, 15, 100, 20);
	name.setForeground(Color.WHITE);
	name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
	p1.add(name);

	// to insert the status
	JLabel status = new JLabel("User1, User2, User3 ");
	status.setBounds(110, 15, 160, 60);
	status.setForeground(Color.WHITE);
	status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
	p1.add(status);


//  Adding writing pannel
	 a1 = new JPanel();
	a1.setBounds(5,75,440,570);
	a1.setBackground(Color.white);
	f.add(a1);
// Adding the textfield.
    text1  = new JTextField();
	text1.setBounds(5,655,310,40);
	text1.setFont(new Font("SAN_SERIF",Font.BOLD,14));
	f.add(text1); 
//  Adding send Button
    JButton send = new JButton("Send");
	send.setBounds(320,655, 123, 40);
	send.setBackground(new Color(7,94,84));
	send.setForeground(Color.WHITE);
	send.setFont(new Font("SAN_SERIF",Font.BOLD,14));
	send.addActionListener(this);

	f.add(send);

		// size of main frame
	f.setSize(450,700);//setting size of frame by java swing
	f.setLocation(490,50);
	f.setUndecorated(true);// to erase the default pannel
	f.getContentPane().setBackground(Color.WHITE); //java awt
//		to make it visible
	f.setVisible(true);//should be in last

	try{
        Socket socket = new Socket("localhost",2003);
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	// Overriding
	public void actionPerformed(ActionEvent ae){
		try {

       String out="<html><p>" + name + "</p><p>" + text1.getText() + "</p></html>";
	   JPanel p2 = formatLabel(out);
       a1.setLayout(new BorderLayout());
	   JPanel right = new JPanel(new BorderLayout());
	   right.setBackground(Color.white);
	   right.add(p2,BorderLayout.LINE_END);
	   vertical.add(right);
	   vertical.add(Box.createVerticalStrut(15));
	   a1.add(vertical, BorderLayout.PAGE_START);
       
try{
 writer.write(out);
 writer.write("\r\n");
 writer.flush();
}catch(Exception e){
	e.printStackTrace();
}

	   text1.setText("");
	   
	   f.repaint();
	   f.invalidate();
	   f.validate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static JPanel formatLabel(String out){
     JPanel panel = new JPanel();
     panel.setBackground(Color.WHITE);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel output = new JLabel(out);
    output.setFont(new Font("Tahoma",Font.PLAIN,16));
	output.setBackground(new Color(37,211,102));
	output.setOpaque(true);
	output.setBorder(new EmptyBorder(15,15,15,50));
	panel.add(output);

	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("hh: mm");

	JLabel time = new JLabel();
	time.setText(sdf.format(cal.getTime()));
	panel.add(time);
	 return panel;
	}
	public void run(){
        try{
              String mssg ="";
              while(true) {
			  mssg = reader.readLine();
			  if(mssg.contains(name)) {
				  continue;
			  }
              
			  JPanel pannel = formatLabel(mssg);

			  JPanel left = new JPanel(new BorderLayout());
			  left.setBackground(Color.WHITE);
			  left.add(pannel, BorderLayout.LINE_START);
			  vertical.add(left);

			  a1.add(vertical, BorderLayout.PAGE_START);

			  f.repaint();
			  f.invalidate();
			  f.validate();

              }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {             
		  User2 two = new User2();
		  Thread t1 = new Thread(two);
		  t1.start();
		}	
	}