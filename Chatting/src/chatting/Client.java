
package chatting;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class Client  implements ActionListener {
    
    JTextField text;
    JPanel top;
    static JPanel bottom;
    static Box vertical=Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f=new JFrame();
    Client(){
        f.setSize(415,700);
        f.setLocation(800,20);
        f.setLayout(null);
        
        top=new JPanel();
        top.setBounds(0,0,400,60);
        top.setBackground(Color.decode("#66A5AD"));
        f.add(top);
        top.setLayout(null);
        f.getContentPane().setBackground(Color.WHITE);
        
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,15,25,25);
        top.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                f.setVisible(false);
            }
    });
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/images.jpeg"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,7,50,50);
        top.add(profile);
        
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i8=i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel call=new JLabel(i9);
        call.setBounds(280,7,50,50);
        top.add(call);
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i11=i10.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel video=new JLabel(i12);
        video.setBounds(220,7,50,50);
        top.add(video);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/more.png"));
        Image i14=i13.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel more=new JLabel(i15);
        more.setBackground(Color.WHITE);
        more.setBounds(340,18,30,30);
        top.add(more);
        
        JLabel name=new JLabel("RAMA");
        name.setFont(new Font("sans_serif",Font.BOLD,18));
        name.setBounds(100,7,100,30);
        name.setForeground(Color.WHITE);
        top.add(name);
        
        JLabel status=new JLabel("ACTIVE NOW");
        status.setFont(new Font("sans_serif",Font.BOLD,12));
        status.setBounds(100,40,100,20);
        status.setForeground(Color.WHITE);
        top.add(status);
        
        bottom=new JPanel();
        bottom.setBounds(0,60,400,550);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        f.add(bottom);
        
        text=new JTextField();
        text.setBounds(5,620,250,30);
        text.setFont(new Font("sans_serif",Font.PLAIN,16));
        f.add(text);
        
        JButton send=new JButton("SEND");
        send.setFont(new Font("sans_serif",Font.BOLD,16));
        send.setBackground(Color.decode("#66A5AD"));
        send.setBounds(265,620,95,30);
        send.addActionListener(this);
        f.add(send);
        f.setVisible(true);
        
    }

  
    public static void main(String[] args) {
       new Client();

       try{
           Socket s=new Socket("127.0.0.1",6001);
           DataInputStream din=new DataInputStream(s.getInputStream());
           dout=new DataOutputStream(s.getOutputStream());
           while(true){
                   bottom.setLayout(new BorderLayout());
                   String msg=din.readUTF();
                   JPanel panel=formatLabel(msg);
                   
                   JPanel left= new JPanel(new BorderLayout());
                   left.add(panel,BorderLayout.LINE_START);
                   vertical.add(left);
                   vertical.add(Box.createVerticalStrut(15));
                   bottom.add(vertical,BorderLayout.PAGE_START);
                   
                   f.validate();
               }
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg=text.getText();
        
        JPanel p2=formatLabel( msg);
     
        bottom.add(p2);
        
        bottom.setLayout(new BorderLayout());
        
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        bottom.add(vertical,BorderLayout.PAGE_START);
        try{
            dout.writeUTF(msg);
        }catch(Exception ae){
            ae.printStackTrace();
        }
        f.repaint();
        f.invalidate();
        f.validate();
        text.setText("");//set textfield null after sending
        
    }
    public static JPanel formatLabel(String msg){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel out=new JLabel("<html><p style=\"width:150 px\">"+msg+"</p></html>");
        out.setBorder(new EmptyBorder(15,15,14,15));
        out.setOpaque(true);
        out.setFont(new Font("tahoma",Font.PLAIN,16));
        out.setBackground(new Color(220, 248, 198));
       
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat simple=new SimpleDateFormat("HH:mm");
        panel.add(out);
        JLabel time=new JLabel();
        time.setText(simple.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    
}
