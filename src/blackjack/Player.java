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
    protected boolean fiveCardWin = false;
    private int lastFlippedCard = 0;

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
        for (int i = 0; i < lastFlippedCard; i++)
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
        checkFiveCardWin();
    }

    protected boolean isBusted()
    {
        calculateScore();
        synchronized (cardBoard)
        {
            return score > 21;
        }
    }
    
    public void paint(Graphics g, Painter painter, int x, int y, int w, int h)
    {
        for (int i = 0; i < this.getLastFlippedCard(); i++)
        {
            g.drawImage(painter.loadIcon("Card" + hand.get(i) + ".jpg"), w / 3 + x + i * w / 45, y, w / 45, h / 20, null);
        }

        if (isBusted())
        {
            g.setColor(Color.black);
            Font newFont = g.getFont().deriveFont(200);
            g.setFont(newFont);
            g.drawString("BUSTED", w / 3 + x + 1 * w / 45 / 2, y + h / 20 + newFont.getSize());
        }
    }

    protected void checkFiveCardWin()
    {
        synchronized (cardBoard)
        {
            if (lastFlippedCard == 5 && score < 22)
            {
                fiveCardWin = true;
//                JOptionPane.showMessageDialog(cardBoard, "You Win");
//                cardBoard.notify();
//                cardBoard.setVisible(false);
            } else
            {
                fiveCardWin = false;
            }
        }
    }

    protected void flipACard()
    {
        if (score < 21 && lastFlippedCard < 5)
        {
            lastFlippedCard += 1;
            calculateScore();
        }
    }

    public void deckWasClicked()
    {
    }

    public void passWasClicked()
    {
    }

    public int getLastFlippedCard()
    {
        return lastFlippedCard;
    }
}
