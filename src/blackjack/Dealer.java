package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class Dealer extends GenericPlayer
{
    private ArrayList<JButton> dealerCard;

    public Dealer(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
        dealerCard = new ArrayList<JButton>();
        flipACard();
    }

    public int play()
    {
        while (score < 17)
        {
            flipACard();
            calculateScore();
        }
        return detectBust();
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

    @Override
    public void paint(Graphics g, Painter canvas)
    {
        int h = canvas.getHeight();
        int w = canvas.getWidth();

        int dealerLocationX = w / 3;
        int dealerLocationY = 10 * h / 20;

        for (int i = 0; i < this.getLastFlippedCard(); i++)
        {
            g.drawImage(loadIcon(hand.get(i)), w / 3 + dealerLocationX + i * w / 45, dealerLocationY, w / 45, h / 20, null);
        }
        if (isBusted() == true)
            {
                g.setColor(Color.black);
                Font newFont = g.getFont().deriveFont(200);
                g.setFont(newFont);
                g.drawString("BUSTED", w / 3 + dealerLocationX + 0 * w / 45, dealerLocationY + h / 20);
            }
    }
}
