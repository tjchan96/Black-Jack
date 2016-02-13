package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class You extends GenericPlayer
{
    private boolean isWaitingForEvent = false;
    private boolean endTurnClicked = false;
    private Image cardCover2;

    public You(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
        for (int i = 0; i < 2; i++)
        {
            flipACard();
        }
    }

    private boolean isBusted()
    {
        if (detectBust() == GenericPlayer.BUSTED)
        {
            return true;
        } else
        {
            return false;
        }
    }

    private boolean hasWon()
    {
        checkFiveCardWin();
        if (fiveCardWin == true)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public int play()
    {
        while (isBusted() == false && endTurnClicked == false && hasWon() == false)
        {
            waitForEvent();
        }
        return detectBust();
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

    @Override
    public void deckWasClicked()
    {
        if (isWaitingForEvent)
        {
            flipACard();
        }
    }

    @Override
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

    @Override
    public void paint(Graphics g, Painter canvas)
    {
        int h = canvas.getHeight();
        int w = canvas.getWidth();

        int youLocationX = 9 * w / 45;
        int youLocationY = h * 13 / 20;

        for (int i = 0; i < this.getLastFlippedCard(); i++)
        {
            g.drawImage(loadIcon(hand.get(i)), w / 3 + youLocationX + i * w / 45, youLocationY, w / 45, h / 20, null);

        }
            if (isBusted() == true)
            {
                g.setColor(Color.black);
                Font newFont = g.getFont().deriveFont(200);
                g.setFont(newFont);
                g.drawString("BUSTED", w / 3 + youLocationX + 0 * w / 45, youLocationY + h / 20);
            }
    }

    @Override
    protected int detectBust()
    {
        synchronized (this)
        {
            if (score > 21)
            {
                return GenericPlayer.BUSTED;
            }
        }
        return NOT_BUSTED;
    }
}
