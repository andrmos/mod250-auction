/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soapclient;


import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceRef;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.datatype.XMLGregorianCalendar;
import webservices.Auction;
import webservices.AuctionUser;
import webservices.Bid;
/**
 *
 * @author Eier
 */
import webservices.SoapService_Service;/**
 *
 * @author Eier
 */
public class Main extends JPanel  {
   
    @WebServiceRef(wsdlLocation = "http://localhost:8080/mod250_auction/SoapService?WSDL")
    private static SoapService_Service service;
    static JList  list;

    static DefaultListModel model;

    
    public Main(){
        setLayout(new BoxLayout(this,  BoxLayout.Y_AXIS));        
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);
        JButton placeBid = new JButton("Set bid");
        JTextField field =new JTextField(10);
        JTextArea aucitonInfo= new JTextArea("Choose auction");
        aucitonInfo.setFont(new Font("Serif", Font.ITALIC, 16));
        aucitonInfo.setLineWrap(true);
        aucitonInfo.setWrapStyleWord(true);
        aucitonInfo.setOpaque(false);
        aucitonInfo.setEditable(false);
        aucitonInfo.setBackground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);        
        aucitonInfo.setBorder(border);
        for (int i = 0; i < activeAucitons().size(); i++) {
            model.addElement(activeAucitons().get(i).getId());
        }
        placeBid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setBidWithButton(Double.parseDouble(field.getText()), (long)list.getSelectedValue());                                               
            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    aucitonInfo.setText(getAuctionInformation(list.getSelectedValue()));
                }
            }
        });        
        add(pane);
        add(aucitonInfo);                
        add(field);
        add(placeBid);     
   }
    
    public String getAuctionInformation(Object selectedAuctionID){
        try{
            long tempSelectedAuctionID= (long) selectedAuctionID;
            Auction selectedAuction=getAuctionFromActiveAuctions(tempSelectedAuctionID);
            XMLGregorianCalendar xmlCalendar = selectedAuction.getStartTime();
            Calendar expireDate= xmlCalendar.toGregorianCalendar();
            expireDate.add(Calendar.SECOND, Math.toIntExact(selectedAuction.getDuration()));
            String price="not set yet";
            if(selectedAuction.getBid()!=null){
                price=selectedAuction.getBid().getAmount()+"";
            }
            return "Price: "+price+"|Initial price"
                    + selectedAuction.getInitPrice()+ "|Expires: "+ expireDate.getTime().toString()+"|Description: " + selectedAuction.getProduct().getDescription()
                    +"|Title: " +selectedAuction.getProduct().getProductName();
        }
        
        catch(Exception e){
            //e.printStackTrace();
            return "no id selected";
        }
        
    }
   
    private Auction getAuctionFromActiveAuctions(long id){
        for(Auction e: activeAucitons()){
            if(e.getId().equals(id))
                return e;
        }
        return null;
    }
    
    public static void main(String[] args) throws NamingException, JMSException {
                   jmsHandler();
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
               createAndShowGUI();                  
            }
        });       
        /*
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createConsumer(dest);
            listener = new TextListener();
            consumer.setMessageListener(listener);
            System.out.println("To end program, enter Q or q, then <return>");
                                                                                   
        } catch (JMSRuntimeException e) {
            System.err.println("Exception occurred: " + e.toString());
            System.exit(1);
        }hel
        */
    }

    public static void jmsHandler() throws NamingException, JMSException {
        final  Properties initialContextProperties = new Properties();
        final  String factoryDest = "jms/auctionTopicFactory";
        final  String topicDest = "jms/auctionTopic";
        
        TopicConnectionFactory connectionFactory;
        Topic topic;
        JMSConsumer consumer;
        Destination dest=null;
        final InitialContext ic = new InitialContext(initialContextProperties);
        connectionFactory=(TopicConnectionFactory) ic.lookup(factoryDest);
        topic= (Topic) ic.lookup(topicDest);
        System.out.println(activeAucitons().get(0).getInitPrice());
        TextListener listener=new TextListener();
        dest=(Destination)topic;
        
        TopicConnection tCon = connectionFactory.createTopicConnection();
        TopicSession session = tCon.createTopicSession(
                false, /* not a transacted session */
                Session.AUTO_ACKNOWLEDGE
        );
        TopicSubscriber subscriber = session.createSubscriber(topic);
        subscriber.setMessageListener(listener);
        tCon.start();        
    }

    private static java.util.List<webservices.Auction> activeAucitons() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservices.SoapService port = service.getSoapServicePort();
        return port.activeAucitons();
    }      
    public static void createAndShowGUI(){
    JFrame frame = new JFrame("Auctions");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new Main());
    frame.setSize(260, 200);
    frame.setVisible(true);        
    }        
    
    public static void updateGuiList(){
       model.clear();
       for(int i=0;i<activeAucitons().size();i++){
           model.addElement(activeAucitons().get(i).getId());
       }
    }

    private static void setBidWithButton(double value, long id){         
         System.out.println(setBid(value, id, 3)); 
         updateGuiList();
    }

    private static String setBid(double arg0, long arg1, long arg2) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        System.out.println("setBidss");
        webservices.SoapService port = service.getSoapServicePort();
        return port.setBid(arg0, arg1, arg2);
    }
    
   
}
