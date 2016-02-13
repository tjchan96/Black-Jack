package blackjack;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author tj
 */
public class Painter extends Canvas implements MouseListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -698416571512385181L;
	private Main main;
    private Image cardCover;
    private Point[] locationsByPlayerID = new Point[6];
    private BufferedImage offscreen = null;

    public Painter(Main mainArg)
    {
        main = mainArg;
        main.getPlayers();
        this.addMouseListener(this);
        cardCover = loadIcon("CardCover_3.jpg");
        for (int i = 0; i < main.getPlayers().size(); i++)
        {
        	locationsByPlayerID[i] = new Point();
        }
    }
    
    @Override
    public void update(Graphics g)
    {
        Graphics offgc;
        Rectangle box = g.getClipBounds();

        offscreen = new BufferedImage(box.width, box.height, BufferedImage.TYPE_INT_RGB);
        offgc = offscreen.getGraphics();
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, box.width, box.height);
        offgc.setColor(getForeground());
        offgc.translate(-box.x, -box.y);
        paint(offgc);
        g.drawImage(offscreen, box.x, box.y, this);
    }

    @Override
    public void paint(Graphics g)
    {
        int h = getHeight();
        int w = getWidth();
        
        int player2LocationX = -5 * w / 45;
        int dealerLocationY = 10 * h / 20;
        int youLocationX = 9 * w / 45;
        int youLocationY = h * 13 / 20;
        int player1LocationX = 1 * w / 45;
        int player3LocationY = h * 7 / 20 - h / 20;
        int dealerLocationX = w / 3;
        
        locationsByPlayerID[0].x = youLocationX;
        locationsByPlayerID[0].y = youLocationY;
        locationsByPlayerID[1].x = player1LocationX;
        locationsByPlayerID[1].y = youLocationY;
        locationsByPlayerID[2].x = player2LocationX;
        locationsByPlayerID[2].y = dealerLocationY;
        locationsByPlayerID[3].x = player1LocationX;
        locationsByPlayerID[3].y = player3LocationY;
        locationsByPlayerID[4].x = youLocationX;
        locationsByPlayerID[4].y = player3LocationY;
        locationsByPlayerID[5].x = dealerLocationX;
        locationsByPlayerID[5].y = dealerLocationY;

        g.setColor(new Color(75, 75, 75));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(75, 150, 75));
        g.fillRect(this.getWidth() / 3, this.getHeight() / 4, this.getWidth() / 3, this.getHeight() / 2);
        g.fillOval(this.getWidth() / 3 - this.getHeight() / 4, this.getHeight() / 4, this.getHeight() / 2, this.getHeight() / 2);
        g.fillOval(this.getWidth() * 2 / 3 - this.getHeight() / 4, this.getHeight() / 4, this.getHeight() / 2, this.getHeight() / 2);

        for (int i = 0; i < main.getPlayers().size(); i++)
        {
            main.getPlayers().get(i).paint(g, this, locationsByPlayerID[i].x, locationsByPlayerID[i].y, w, h);
        }

        g.setColor(Color.yellow);
        g.fillRect(w / 2, dealerLocationY, w / 45, h / 20);
        g.drawImage(cardCover, w / 2 - w / 45, dealerLocationY, w / 45, h / 20, this);
    }

    public void mouseClicked(MouseEvent me)
    {
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
