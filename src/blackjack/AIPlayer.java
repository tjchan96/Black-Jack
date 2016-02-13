package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class AIPlayer extends GenericPlayer
{
    private ArrayList<JButton> dealerCard;
    private Random randomNumber;
    private int randomInt;

    public AIPlayer(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
        dealerCard = new ArrayList<JButton>();
        randomNumber = new Random();
        randomInt = 15 + randomNumber.nextInt(5);
    }

    public int play()
    {
        while (score < randomInt)
        {
            flipACard();
            calculateScore();
        }
        return detectBust();
    }

    protected boolean isBusted()
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
        int player2LocationX = -5 * w / 45;
        int dealerLocationY = 10 * h / 20;
        int youLocationX = 9 * w / 45;
        int youLocationY = h * 13 / 20;
        int player1LocationX = 1 * w / 45;
        int player3LocationY = h * 7 / 20 - h / 20;

        Point pointHolder = new Point(player2LocationX, dealerLocationY);


        for (int i = 0; i < this.getLastFlippedCard(); i++)
        {
            g.drawImage(loadIcon(hand.get(i)), w / 3 + pointHolder.x + i * w / 45, pointHolder.y, w / 45, h / 20, null);
        }

        if (isBusted() == true)
        {
            g.setColor(Color.black);
            Font newFont = g.getFont().deriveFont(200);
            g.setFont(newFont);
            g.drawString("BUSTED", w / 3 + pointHolder.x + 0 * w / 45, pointHolder.y + h / 20);
        }
    }
}
