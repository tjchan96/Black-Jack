package blackjack;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author tj
 */
public class Main
{
    private Random randomNumberGenerator;
    private ArrayList<Integer> fiftyTwoList;
    final private JFrame cardBoard;
//    private ImageIcon cardCover;
    private ArrayList<GenericPlayer> players;
    private Painter canvasJr;
    static public final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    static public final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args)
    {
        while (true)
        {
            Main main = new Main();
            main.play();
        }

    }

    public Main()
    {
//        cardCover = new ImageIcon(loadIcon("CardCover_3.jpg"));
        cardBoard = new JFrame("Memory");
        cardBoard.setSize(screenWidth, screenHeight);
        cardBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardBoard.setTitle("Black Jack");
        canvasJr = new Painter(this);
        cardBoard.add(canvasJr);

        randomNumberGenerator = new Random();
        fiftyTwoList = new ArrayList<Integer>();

        createFiftyTwoList();
        knuth();

        players = new ArrayList<GenericPlayer>();

        players.add(new You(cardBoard, fiftyTwoList));
        players.add(new AIPlayerOne(cardBoard, fiftyTwoList));
        players.add(new AIPlayerTwo(cardBoard, fiftyTwoList));
        players.add(new AIPlayerThree(cardBoard, fiftyTwoList));
        players.add(new AIPlayerFour(cardBoard, fiftyTwoList));
        players.add(new Dealer(cardBoard, fiftyTwoList));

        cardBoard.setVisible(true);
    }

    private void knuth() // Shuffle
    {
        for (int i = 0; i < 10; i++)
        {
            int index = 1 + i + randomNumberGenerator.nextInt(19 - i);
            int firstSwap = fiftyTwoList.get(i);
            int secondSwap = fiftyTwoList.get(index);
            fiftyTwoList.set(index, firstSwap);
            fiftyTwoList.set(i, secondSwap);
        }
    }

    private void createFiftyTwoList() //makes list of all 52 cards
    {
        for (int i = 0; i < 52; i++) 
        {
            fiftyTwoList.add(i);
        }
    }

    private void play()
    {
        for (int i = 0; i < players.size(); i++)
        {
            players.get(i).play();
        }

        int highestScore = 0;
        ArrayList<Integer> winners = null;

        winners = new ArrayList<Integer>();

        for (int i = 0; i < players.size(); i++)
        {
//            System.out.println(players.get(i).score + "      " + i);
            if (players.get(i).score > highestScore && players.get(i).score < 22)
            {
                highestScore = players.get(i).score;
                winners.clear();
                winners.add(i);
            } else if (players.get(i).score < highestScore)
            {
            } else if (players.get(i).score == highestScore)
            {
                winners.add(i);
            }
        }


        System.out.println(highestScore + "      " + winners.toString());

        try
        {
            Thread.sleep(8000);
        } catch (InterruptedException ex)
        {
            System.out.println("hiccup");
        }
    }
    
    public ArrayList<GenericPlayer> getPlayers()
    {
        return players;
    }
}
