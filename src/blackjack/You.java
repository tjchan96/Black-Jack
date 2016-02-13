package blackjack;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class You extends Player
{
    private boolean isWaitingForEvent = false;
    private boolean endTurnClicked = false;

    public You(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
        for (int i = 0; i < 2; i++)
        {
            flipACard();
        }
    }

    public boolean play()
    {
        while (isBusted() == false && endTurnClicked == false)
        {
            waitForEvent();
        }
        return isBusted();
    }

    private void waitForEvent()
    {
        synchronized (this)
        {
            isWaitingForEvent = true;
            while (isWaitingForEvent)
            {
                try
                {
                    this.wait();
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(You.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deckWasClicked()
    {
        if (isWaitingForEvent)
        {
            if (!flipACard())
            {
            	endTurnClicked = true;
                synchronized (this)
                {
                    this.notify();
                    isWaitingForEvent = false;
                }
            }
        }
    }

    public void passWasClicked()
    {
        if (isWaitingForEvent)
        {
            endTurnClicked = true;
            synchronized (this)
            {
                this.notify();
                isWaitingForEvent = false;
            }
        }
    }
}
