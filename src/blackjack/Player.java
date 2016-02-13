package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public abstract class Player
{
    protected Random randomNumberGenerator;
    protected ArrayList<Integer> hand;
    protected int score = 0;
    private int cardsFlipped = 0;

    protected final JFrame cardBoard;

    public abstract boolean play();

    public Player(JFrame cardB, ArrayList<Integer> fiftyTwoList)
    {
        randomNumberGenerator = new Random();
        hand = new ArrayList<Integer>();
        moveToHand(fiftyTwoList);
        cardBoard = cardB;
    }

    protected void moveToHand(ArrayList<Integer> fiftyTwoList)
    {
        for (int i = 0; i < 5; i++) //takes numbers from fifty two list and puts it into hand
        {
            int fiftyTwoListPointer = randomNumberGenerator.nextInt(fiftyTwoList.size());
            int randomTemp = fiftyTwoList.get(fiftyTwoListPointer);
            fiftyTwoList.remove(fiftyTwoListPointer);
            hand.add(i, randomTemp);
        }
    }

    protected void calculateScore()
    {
        int aces = 0;
        score = 0;
        for (int i = 0; i < cardsFlipped; i++)
        {
            int cardScore = hand.get(i) % 13;
            if (cardScore == 0)
            {
                score += 11;
                aces += 1;
            } else if (cardScore < 10)
            {
                score += cardScore + 1;
            } else if (cardScore < 13)
            {
                score += 10;
            }
        }

        while (score > 21 && aces > 0)
        {
            aces -= 1;
            score -= 10;
        }
    }
    
    public void paint(Graphics g, Painter painter, int x, int y, int w, int h)
    {
    	for (int i = 0; i < 2; i++)
        {
            g.drawImage(painter.loadIcon("CardCover_3.jpg"), w / 3 + x + i * w / 45, y, w / 45, h / 20, null);
        }
    	
        for (int i = 0; i < cardsFlipped; i++)
        {
            g.drawImage(painter.loadIcon("Card" + hand.get(i) + ".jpg"), w / 3 + x + i * w / 45, y, w / 45, h / 20, null);
        }
        
        String scoreStr = score + "";

        if (isBusted())
        {
        	scoreStr += " BUSTED";
        }

        g.setColor(Color.black);
        Font newFont = g.getFont().deriveFont(200);
        g.setFont(newFont);
        g.drawString(scoreStr, w / 3 + x + 1 * w / 45 / 2, y + h / 20 + newFont.getSize());
    }
    
    protected boolean isBusted()
    {
        calculateScore();
        synchronized (cardBoard)
        {
            return score > 21;
        }
    }
    
    protected boolean flipACard()
    {
        if (score < 21 && cardsFlipped < 5)
        {
            cardsFlipped += 1;
            calculateScore();
            return true;
        }
        return false;
    }
}
