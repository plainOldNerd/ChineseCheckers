package ChineseCheckers;

import java.util.ArrayList;
import java.util.Random;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Main 
{	
	private static int i, j;	//  loop counters
	
	private static JFrame f = new JFrame();
	private static JPanel p = new JPanel();
	private static JLayeredPane lp = new JLayeredPane();
	
	public static final String images = "images/CC/";
	public static final String[] colors = {"blue","green","orange","purple",
			"red","yellow"};
	public static final String clear = "none";
	public static final ImageIcon blue = new ImageIcon(images + "blue.png", "blue"),
			green = new ImageIcon(images + "green.png", "green"),
			orange = new ImageIcon(images + "orange.png", "orange"),
			purple = new ImageIcon(images + "purple.png", "purple"),
			red = new ImageIcon(images + "red.png", "red"),
			yellow = new ImageIcon(images + "yellow.png", "yellow"),
			none = new ImageIcon(images + "none.png", "none"),
			smiley = new ImageIcon(images + "smiley.png", "smiley");
	
	private static final double[] zeroZeroth = {26, 141},
			IVector = {(double) 10/12, (double) 521/12}, 
			JVector = {(double) 437/12, (double) -251/12};
	
	@SuppressWarnings("unchecked")
	private static ArrayList<Label>[] rows = new ArrayList[17];
	
	private static int numPlayers, whoseTurn;
	private static int[] twoP = {0, 3}, threeP = {0, 2, 4}, fourP = {0, 1, 3, 4},
			sixP = {0, 1, 2, 3, 4, 5},
			piecesHome = {0, 0, 0, 0, 0, 0};
	private static ArrayList<String> players = new ArrayList<String>(), 
			winners = new ArrayList<String>();
	private static JLabel whoseTurnLabel = new JLabel(), 
			pieceBeingMoved = new JLabel(none);
	private static ArrayList<Label> possibleMoves = new ArrayList<Label>();
	private static Label currentMousePosition;
	
	private static int xIconOffset, yIconOffset;
	
	public static void main(String[] args) 
	{
		numPlayers = Integer.parseInt(args[0]);
		
		int[] playerInts;
		switch (numPlayers)
		{
			case 2:
			{
				playerInts = twoP;
				break;
			}
			case 3:
			{
				playerInts = threeP;
				break;
			}
			case 4:
			{
				playerInts = fourP;
				break;
			}
			case 6:
			{
				playerInts = sixP;
				break;
			}
			default:
			{	return;		}
		}
		
		for(i=0; i<playerInts.length; ++i)
		{	players.add(players.size(), colors[playerInts[i]]);		}
		
		whoseTurn = new Random().nextInt(6);
		while( !players.contains(colors[whoseTurn]) )
		{	whoseTurn = (whoseTurn+1)%6;	}
		
		lp.setPreferredSize(new Dimension(684,684));
		
		JLabel board = new JLabel();
		
		board.setIcon(new ImageIcon(images + "board.png"));
		board.setBounds(0,0,682,682);
		lp.add(board, new Integer(0));

		for(i=0; i<17; ++i)
		{
			rows[i] = new ArrayList<Label>();
		}
		
		//  set all the null Labels
		for(i=0; i<=3; ++i)
		{
			for(j=0; j<4; ++j)
				rows[i].add(j,null);
		}
		
		for(i=5; i<=7; ++i)
		{
			for(j=0; j<=i-5; ++j)
				rows[i].add(j,null);
		}
		
		for(i=8; i<=12; ++i)
		{
			for(j=0; j<=3; ++j)
				rows[i].add(j,null);
		}
		
		for(i=13; i<=16; ++i)
		{
			for(j=0; j<=i-5; ++j)
				rows[i].add(j,null);
		}
		
		//  set the blue pieces
		for(i=0; i<=3; ++i)
		{
			for(j=4; j<=i+4; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"blue", blue));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the yellow pieces
		for(i=4; i<=7; ++i)
		{
			ImageIcon icon;
			if(numPlayers == 3)
				icon = none;
			else
				icon = yellow;
				
			for(j=i-4; j<=3; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"yellow", icon));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the top half of the "none"s
		for(i=4; i<=8; ++i)
		{
			for(j=4; j<=i+4; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"none", none));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the green pieces
		for(i=4; i<=7; ++i)
		{
			ImageIcon icon;
			if(numPlayers == 3)
				icon = none;
			else
				icon = green;
			
			for(j=i+5; j<=12; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"green", icon));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the red pieces
		for(i=9; i<=12; ++i)
		{
			for(j=4; j<=i-5; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"red", red));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the bottom half of the "none"s
		for(i=9; i<=12; ++i)
		{
			for(j=i-4; j<=12; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"none", none));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the orange pieces
		for(i=9; i<=12; ++i)
		{
			for(j=13; j<=i+4; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"orange", orange));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		//  set the purple pieces
		for(i=13; i<=16; ++i)
		{
			ImageIcon icon;
			if(numPlayers == 3)
				icon = none;
			else
				icon = purple;
			
			for(j=i-4; j<=12; ++j)
			{
				rows[i].add(j, new Label(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]), i, j,
						"purple", icon));
				rows[i].get(j).setBounds(
						(int) (zeroZeroth[0]+i*IVector[0]+j*JVector[0]),
						(int) (zeroZeroth[1]+i*IVector[1]+j*JVector[1]),35,35);
				lp.add(rows[i].get(j), new Integer(1));
			}
		}
		
		whoseTurnLabel.setText("<html> <big>" + colors[whoseTurn] + "'s turn </big> </html>");
		whoseTurnLabel.setBounds(520,10,1000,50);
		lp.add(whoseTurnLabel, new Integer(1));
		
		p.add(lp);
		f.add(p);
		f.setLocation(0, 0);
		f.setSize(700,720);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void setCurrentMousePosition( Label l )
	{	currentMousePosition = l;	}
	
	public static String getWhoseTurn()
	{	return colors[whoseTurn];	}
	
	public static JLabel getPieceBeingMoved()
	{	return pieceBeingMoved;		}
	
	/*  when a piece is clicked upon, the game automatically calculates where 
	 *  that piece can move to, and sets the piece ready for drag and drop
	 */
	public static void mousePressed(Label l, MouseEvent e)
	{
		xIconOffset = l.getTLX() - e.getX();
		yIconOffset = l.getTLY() - e.getY();
		ImageIcon colorToMove = (ImageIcon) l.getIcon();
		l.setIcon(none);
		pieceBeingMoved = new JLabel();
		pieceBeingMoved.setIcon(colorToMove);
		pieceBeingMoved.setBounds(e.getX() + xIconOffset, e.getY() + yIconOffset,
				35,35);
		lp.add(pieceBeingMoved, new Integer(1));
		lp.moveToFront(pieceBeingMoved);
		
		possibleMoves = new ArrayList<Label>();
		possibleMoves.add(0, l);
		
		int r = l.getR(), c = l.getC();
		
		//  calculate which of the immediately adjacent places can be moved to
		for(i=-1; i<=1; ++i)
		{
			for(j=-1; j<=1; ++j)
			{
				if( i!=-j && r+i>=0 && r+i<=16 && c+j>=0 )
				if( c+j<rows[r+i].size() )
				{
					Label currentLabel = (Label) rows[r+i].get(c+j);
					
					if( currentLabel != null )
					{
						if( ( currentLabel.getZone().compareTo( "none" ) == 0 ||
								currentLabel.getZone()
								.compareTo( colors[whoseTurn] ) == 0 ||
								currentLabel.getZone()
								.compareTo( colors[(whoseTurn+3)%6] ) == 0 ) && 
								((ImageIcon) currentLabel.getIcon())
								.equals(Main.none) )
						{
							possibleMoves.add(possibleMoves.size(),currentLabel);
						}
					}
				}
			}
		}
		
		//  calculate, recursively, which places can be jumped to 
		canJumpTo(l);
		
		possibleMoves.remove(l);
		for(i=0; i<possibleMoves.size(); ++i)
		{	possibleMoves.get(i).setIcon(smiley);	}
	}
	
	//  recursively calculate which places can be jumped to from the clicked place
	//  other players' (that are not the opponent) bases may not be entered
	private static void canJumpTo(Label l)
	{
		int r = l.getR(), c = l.getC();
		
		for(int i=-1; i<=1; ++i)
		{
			for(int j=-1; j<=1; ++j)
			{
				if( i!=-j && r+2*i>=0 && r+2*i<=16 && c+2*j>=0 )
				{
					if( c+2*j<rows[r+2*i].size() )
					{
						Label adjacentLabel = rows[r+i].get(c+j),
								twoOver = rows[r+2*i].get(c+2*j);
						if( adjacentLabel!=null && twoOver!=null && 
								!possibleMoves.contains(twoOver) )
						{
							if( !adjacentLabel.getIcon().equals(none) && 
									twoOver.getIcon().equals(none) &&
									(twoOver.getZone().compareTo(colors[whoseTurn]) == 0 ||
									twoOver.getZone().compareTo(colors[(whoseTurn+3)%6]) == 0 ||
									twoOver.getZone().compareTo("none") == 0 ) )
							{
								possibleMoves.add(possibleMoves.size(),twoOver);
								canJumpTo(twoOver);
							}
						}
					}
				}
			}
		}
	}
	
	public static void mouseDragged(Label l, MouseEvent e)
	{	pieceBeingMoved.setLocation(e.getX() + xIconOffset, e.getY() + yIconOffset);	}
	
	/*  mouseDragged events are delivered constantly to the JLabel where the 
	 *  mouse was clicked.  Hence, Label l refers to the JLabel where the piece
	 *  was dragged from, not to 
	 */
	/*  reset all of the possible move icons.  check if the current player has 
	 *  won, and if so add them to the winners list, remove them from the players
	 *  list, and allow the rest of the players to continue play.
	 */
	public static void mouseReleased(Label l, MouseEvent e)
	{
		for(Label wasPossible : possibleMoves)
		{	wasPossible.setIcon(none);	}
		
		if( possibleMoves.contains(currentMousePosition) )
		{
			currentMousePosition.setIcon(pieceBeingMoved.getIcon());
			
			if( l.getZone().compareTo(colors[(whoseTurn+3)%6]) != 0 &&
					currentMousePosition.getZone()
					.compareTo(colors[(whoseTurn+3)%6]) == 0 )
			{
				++piecesHome[whoseTurn];
				
				if( piecesHome[whoseTurn] == 10 )
				{
					winners.add(winners.size(), colors[whoseTurn]);
					players.remove(colors[whoseTurn]);

					if( players.size() == 1 )
					{
						winners.add(winners.size(), players.get(0));
						players.remove(players.get(0));

						--whoseTurn;
						
						for(i=0; i<=16; ++i)
						{
							for(j=0; j<rows[i].size(); ++j)
							{
								Label currentLabel = rows[i].get(j);
								
								if( currentLabel != null )
								{
									currentLabel.removeMouseListener(currentLabel);
									currentLabel.removeMouseMotionListener(currentLabel);
								}
							}
						}
					}
				}
			}
			if( l.getZone().compareTo(colors[(whoseTurn+3)%6]) == 0 &&
					currentMousePosition.getZone()
					.compareTo(colors[(whoseTurn+3)%6]) != 0 )
			{	--piecesHome[whoseTurn];	}
			
			whoseTurn = (whoseTurn + 1) % 6;
			while( players.size()>0 && !players.contains(colors[whoseTurn]) )
			{	whoseTurn = (whoseTurn+1)%6;	}
			whoseTurnLabel.setText( "<html> <big>" + colors[whoseTurn] + "'s turn "
					+ "</big> </html>" );
		}
		else
		{	l.setIcon(pieceBeingMoved.getIcon());	}
		
		possibleMoves = new ArrayList<Label>();
		currentMousePosition = null;
		pieceBeingMoved.setIcon(null);
		//  this avoids complications with mouseReleased in Label
		pieceBeingMoved = new JLabel();
		pieceBeingMoved.setIcon(none);
	}
}