package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class AIPlayerOne extends AIPlayer
{
    public AIPlayerOne(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
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

        Point pointHolder = new Point(player1LocationX, youLocationY);


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