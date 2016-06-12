package ChineseCheckers;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Label extends JLabel implements MouseListener, MouseMotionListener 
{
	private static final long serialVersionUID = 1L;

	private final int tlX, tlY, r, c;
	private final String zone;
	
	public Label(int topLeftX, int topLeftY, int row, int col, String zone
			, ImageIcon icon)
	{
		tlX = topLeftX;
		tlY = topLeftY;
		r = row;
		c = col;
		this.zone = zone;
		
		setIcon(icon);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{	Main.mouseDragged(this, e);		}

	@Override
	public void mouseMoved(MouseEvent e) 
	{}

	@Override
	public void mouseClicked(MouseEvent e) 
	{}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		String thisColor = ((ImageIcon) getIcon()).getDescription();
		if( thisColor.compareTo( Main.getWhoseTurn() ) == 0)
		{
			Main.mousePressed(this, e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		String thisColor = ((ImageIcon) Main.getPieceBeingMoved().getIcon())
				.getDescription();
		if( thisColor.compareTo( Main.getWhoseTurn() ) == 0)
		{	Main.mouseReleased(this, e);	}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{	Main.setCurrentMousePosition(this);		}

	@Override
	public void mouseExited(MouseEvent e) 
	{	Main.setCurrentMousePosition(null);		}
	
	public int getTLX()
	{	return tlX;		}
	
	public int getTLY()
	{	return tlY;		}
	
	public int getR()
	{	return r;	}
	
	public int getC()
	{	return c;	}
	
	public String getZone()
	{	return zone;	}
}