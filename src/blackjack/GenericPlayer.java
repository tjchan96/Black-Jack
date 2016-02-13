package blackjack;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class GenericPlayer
{
    protected Random randomNumberGenerator;
    protected ArrayList<Integer> hand;
    protected int score = 0;
    protected boolean fiveCardWin = false;
    private int lastFlippedCard = 0;

    protected boolean capableOfFlippingCard = false;
    final protected JFrame cardBoard;
    static public int BUSTED = 1;
    static public int NOT_BUSTED = 2;

    abstract public int play();

    public GenericPlayer(JFrame cardB, ArrayList<Integer> fiftyTwoList)
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

    protected int detectBust()
    {
        calculateScore();
        synchronized (cardBoard)
        {
            if (score > 21)
            {
                return GenericPlayer.BUSTED;
            }
            return GenericPlayer.NOT_BUSTED;
        }
    }

    public void paint(Graphics g, Painter canvas)
    {
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
            detectBust();
            capableOfFlippingCard = true;
        }
        capableOfFlippingCard = false;
    }

    public void deckWasClicked()
    {
    }

    public void passWasClicked()
    {
    }

    protected Image loadIcon(Integer cardValue) //Needs to be fixed without copy
    {
        URL url;
        url = getClass().getResource("/images/Card" + cardValue + ".jpg");
        Image cardFace;
        try
        {
            cardFace = ImageIO.read(url);
            return cardFace;
        } catch (IOException ex)
        {
            System.out.println("error");
            return null;
        }
    }

    public int getLastFlippedCard()
    {
        return lastFlippedCard;
    }
}
