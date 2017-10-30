import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame 
{
   private JTextField enterField; 
   private JTextArea displayArea;
   private JScrollPane scroll;
   private DatagramSocket socket; 
   private JButton sendButton;

   
   public Client()
   {
      super("Client");

      setLayout(null);
	  displayArea = new JTextArea(); 
	  displayArea.setEditable(false);
	  
	  scroll = new JScrollPane(displayArea);
	  scroll.setBounds(20, 20, 345, 260);
	  add(scroll);
	  
	  enterField=new JTextField();
	  enterField.setBounds(20, 300, 240, 30);
	  add(enterField);
	  
	  sendButton=new JButton("SEND");
	  sendButton.setBounds(275, 300, 90, 30);
	  add(sendButton);
	  
      setSize(400,400); 
      setVisible(true);
	  
      sendButton.addActionListener(new ActionListener() 
      { 
            public void actionPerformed(ActionEvent event)
            {
               try 
               {
                  
                  String message = enterField.getText();
                  displayArea.append( "\nMe:"+message);
                  byte data[] = message.getBytes(); 
                  DatagramPacket sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),5000);
                  socket.send(sendPacket); 
				  enterField.setText("");
				  if(message.equals("Quit"))
				  {
					  socket.close();
					  System.exit(0);
				  }
               }
               catch (IOException ioException) 
               {
                  ioException.printStackTrace();
               } 
            } 
      }); 

      try 
      {
           socket = new DatagramSocket();
		   displayArea.append("\nClient ready\n");
		   displayArea.append("Enter 'Quit' to exit\n");
      } 
      catch ( SocketException socketException ) 
      {
         socketException.printStackTrace();
         System.exit(1);
      } 
   } 

   public void waitForPackets()
   {
      while(true) 
      {
         try 
         {
            byte data[] = new byte[ 1000 ]; 
            DatagramPacket receivePacket=new DatagramPacket(data,data.length);
            socket.receive(receivePacket);
			String rec=new String(receivePacket.getData(),0,receivePacket.getLength());
            displayArea.append("\nServer:"+rec);
			if(rec.equals("Quit"))
			{   
		        socket.close();
				System.exit(0);
			}
         } 
         catch (IOException exception) 
         {
            exception.printStackTrace();
         } 
      } 
   } 

 
}  


class ClientTest
{
    public static void main(String[] args)
    {
        Client application = new Client(); 
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.waitForPackets();
    } 
}
