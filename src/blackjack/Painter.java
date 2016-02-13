package blackjack;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author tj
 */
public class Painter extends Canvas implements MouseListener
{
    private Main main;
    private int mouseX = 0;
    private int mouseY = 0;
    private Image cardCover;

    public Painter(Main mainArg)
    {
        main = mainArg;
        main.getPlayers();
        this.addMouseListener(this);
        cardCover = loadIcon("CardCover_3.jpg");
    }

    @Override
    public void paint(Graphics g)
    {
        int h = this.getHeight();
        int w = this.getWidth();
        int dealerLocationY = 10 * h / 20;

        g.setColor(new Color(75, 75, 75));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(75, 150, 75));
        g.fillRect(this.getWidth() / 3, this.getHeight() / 4, this.getWidth() / 3, this.getHeight() / 2);
        g.fillOval(this.getWidth() / 3 - this.getHeight() / 4, this.getHeight() / 4, this.getHeight() / 2, this.getHeight() / 2);
        g.fillOval(this.getWidth() * 2 / 3 - this.getHeight() / 4, this.getHeight() / 4, this.getHeight() / 2, this.getHeight() / 2);

        for (int i = 0; i < main.getPlayers().size(); i++)
        {
            main.getPlayers().get(i).paint(g, this);
        }

        g.setColor(Color.yellow);
        g.fillRect(w / 2, dealerLocationY, w / 45, h / 20);
        g.drawImage(cardCover, w / 2 - w / 45, dealerLocationY, w / 45, h / 20, this);
    }

    public void mouseClicked(MouseEvent me)
    {
        mouseX = me.getX();
        mouseY = me.getY();
        int h = this.getHeight();
        int w = this.getWidth();
        int dealerLocationY = 10 * h / 20;
        Rectangle deck = new Rectangle(w / 2 - w / 45, dealerLocationY, w / 45, h / 20);
        Rectangle pass = new Rectangle(w / 2, dealerLocationY, w / 45, h / 20);

        if (deck.contains(me.getPoint()))
        {
            main.getPlayers().get(0).deckWasClicked();
            this.repaint();
        }

        if (pass.contains(me.getPoint()))
        {
            main.getPlayers().get(0).passWasClicked();
            this.repaint();
        }
    }

    public Image loadIcon(String cardName)
    {
        Image cardCoverTemp = null;

        URL url;
        url = getClass().getResource("/images/" + cardName);
        try
        {
            cardCoverTemp = ImageIO.read(url);
        } catch (IOException ex)
        {
            System.out.println("error");
        }
        return cardCoverTemp;
    }


    public void mousePressed(MouseEvent me)
    {
    }

    public void mouseReleased(MouseEvent me)
    {
    }

    public void mouseEntered(MouseEvent me)
    {
    }

    public void mouseExited(MouseEvent me)
    {
    }
}
