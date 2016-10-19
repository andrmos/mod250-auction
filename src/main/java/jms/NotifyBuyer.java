package jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "jms/auctionQueue"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class NotifyBuyer implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;
    static final Logger logger = Logger.getLogger("SimpleMessageBean");

    public NotifyBuyer() {
    }

    @Override
    public void onMessage(Message inMessage) {

        try {
            if (inMessage instanceof TextMessage) {
                
                /*  
                ---- START EMAIL to customer X ----
               Dear X,
               Congratulations!  You have won in bidding for product Y.
               You can access the product using the following link:
               URL=<LINK>
               ---- END EMAIL to customer X ----
                */
                
                String productName = "";
                String username = "";
                String auctionId = "1";
                String link = "";
                String msg = inMessage.getBody(String.class);
                String[] info = msg.split(":");
                if(info.length == 3){
                    productName = info[0];
                    username = info[1];
                    auctionId = info[2];
                }
                
                try{
                    link = "https://localhost:8181/mod250_auction/GeneralViews/auctionDetail.xhtml?auctionId=" + auctionId;
                }catch(Exception e){
                    
                }
                    
                StringBuilder sb = new StringBuilder();
                sb.append("---- START EMAIL to customer ");
                sb.append(username);
                sb.append(" ----\n");
                
                sb.append("Dear ");
                sb.append(username);
                sb.append(",\n");
                
                sb.append("Congratulations!  You have won in bidding for product ");
                sb.append(productName + ".\n");
                
                sb.append("You can access the product using the following link:\n");
                sb.append("URL=");
                sb.append(link + "\n");
                
                sb.append("---- END EMAIL to customer ");
                sb.append(username);
                sb.append(" ----");
                System.out.println(sb.toString());
            } else {
                logger.log(Level.WARNING,
                        "Message of wrong type: {0}",
                        inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "SimpleMessageBean.onMessage: JMSException: {0}",
                    e.toString());
            mdc.setRollbackOnly();
        }
    }
}