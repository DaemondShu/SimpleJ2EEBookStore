package business;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by monkey_d_asce on 16-5-1.
 */


@MessageDriven(
        name = "OrderListenerEJB",
        activationConfig =
                {
                        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/myQueue")
                }
)
public class OrderListenerBean implements MessageListener
{
    @EJB(name = "ShopAction")
    ShopAction shopAction;

    public OrderListenerBean()
    {

    }

    @Override
    public void onMessage(Message message)
    {
        try
        {
            if (message instanceof TextMessage)
            {
                String text = ((TextMessage) message).getText();
                String[] temp = text.split("!");
                System.out.println("message: " + text);
                shopAction.buy(temp[1], temp[0]);
            }
        } catch (Exception e)
        {

            e.printStackTrace();
        }
    }
}
