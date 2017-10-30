
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame 
{
   private JTextField enterField;
   private JTextArea displayArea;
   private JButton sendButton;
   private JScrollPane scroll;
   private DatagramSocket socket; 
   private DatagramPacket receivePacket;
   private DatagramPacket sendPacket;
   int port;
   InetAddress IPaddress;
  
   public Server()
   {
      super("Server");
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
	  
	  sendButton.addActionListener(new ActionListener() 
      { 
            public void actionPerformed( ActionEvent event )
            {
               try 
               {
                  
                  String message = enterField.getText();
                  displayArea.append( "\nMe:"+message);
                  byte data[] = message.getBytes(); 
                  sendPacket = new DatagramPacket(data,data.length,IPaddress,port);
                  socket.send(sendPacket);
                  enterField.setText("");				  
				  if(message.equals("Quit"))
				  {
					  socket.close();
					  System.exit(0);
				  }
               }
               catch ( IOException ioException ) 
               {
                  ioException.printStackTrace();
               } 
            } 
      }); 
      
      setSize(400,400); 
      setVisible(true);
	  
	  
      try 
      {
          socket = new DatagramSocket( 5000 );
		  displayArea.append("\nServer ready.Waiting for client..\n");
		  displayArea.append("Enter 'Quit' to exit\n");
      } 
      catch ( SocketException socketException ) 
      {
          socketException.printStackTrace();
          System.exit( 1 );
      }
   } 

 
   public void waitForPackets()
   {
      while(true) 
      {
         try 
         {
            byte data[] = new byte[ 1000 ]; 
            receivePacket = new DatagramPacket( data, data.length );
            socket.receive( receivePacket ); 
			String rec=new String(receivePacket.getData(),0,receivePacket.getLength());
			IPaddress=receivePacket.getAddress();
			port=receivePacket.getPort();//saving address and port number for sending back to client
            displayArea.append( "\nClient:"+rec);
			if(rec.equals("Quit"))
			{
				socket.close();
				System.exit(0);
			}
         } 
         catch(IOException ioException)
         {
             ioException.printStackTrace();
         } 
      } 
   } 


  
   
} 


 class ServerTest
 {
	 public static void main(String args[])
     {
          Server application = new Server();
          application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          application.waitForPackets(); 
     } 
}