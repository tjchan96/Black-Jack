package blackjack;

import java.util.ArrayList;

import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class Dealer extends Player
{
    public Dealer(JFrame cardBoard, ArrayList<Integer> fiftyTwoList)
    {
        super(cardBoard, fiftyTwoList);
        flipACard();
    }

    public boolean play()
    {
        while (score < 17)
        {
            flipACard();
            calculateScore();
        }
        return isBusted();
    }
}
