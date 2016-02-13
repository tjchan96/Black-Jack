package blackjack;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class AIPlayer extends Player
{
    private Random randomNumber;
    private int randomInt;

    public AIPlayer(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
        randomNumber = new Random();
        randomInt = 15 + randomNumber.nextInt(5);
    }

    public boolean play()
    {
        while (score < randomInt)
        {
            flipACard();
            calculateScore();
        }
        return isBusted();
    }
}
