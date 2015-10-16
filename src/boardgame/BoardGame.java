// BoradGame
package boardgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class BoardGame extends JFrame implements Runnable  {
    
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + 800;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + 800;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;
    
    final int numRows = 8;
    final int numColumns = 8;
    Piece board[][];
    
    int turnnum = 1;
    
    int currentr = 0;
    int currentc = 0;
    
    int whowon = 0;
    
    static BoardGame frame1;
    public static void main(String[] args) {
        frame1 = new BoardGame();
        frame1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }
    public BoardGame() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                
                if(whowon > 0)
                   return;
                
                if (e.BUTTON1 == e.getButton()) {
                    int xpos = e.getX();
                    int yepc = 0;
                    int yepr = numRows-1;

                    for(int col = 0; col<numColumns+1;col++)
                    {
                        if(xpos-getX(0) < (col+1)*getWidth2()/numColumns && xpos-getX(0) > col*getWidth2()/numColumns)
                        {
                            yepc = col;
                            for(yepr = numRows-1; yepr>=0; yepr-- )
                            {
                                if(board[yepc][yepr] == null)
                                {
                                    if(turnnum % 2 == 1)
                                    board[yepc][yepr] = new Piece(Color.RED);
                                    if(turnnum % 2 == 0)
                                    board[yepc][yepr] = new Piece(Color.BLACK);
                                    turnnum++;
                                    currentc = yepc;
                                    currentr = yepr;
                                    break;
                                }
                            }
                        }
                    
                    }
                }
               
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_RIGHT == e.getKeyCode())
                {

                }
                if (e.VK_LEFT == e.getKeyCode())
                {

                }
                if (e.VK_UP == e.getKeyCode())
                {

                }
                if (e.VK_DOWN == e.getKeyCode())
                {

                }
                if (e.VK_SPACE == e.getKeyCode())
                    reset();
                    
                
                repaint();
            }
        });
        init();
        start();
    }




    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

//fill background
        g.setColor(Color.MAGENTA);

        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.WHITE);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.black);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
  
//horizontal lines
//        for (int zi=1;zi<numRows;zi++)
//        { 
//            g.setColor(Color.BLACK);
//            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
//            getX(getWidth2()) ,getY(0)+zi*getHeight2()/(numRows) );
//        }
//vertical lines
        for (int zi=1;zi<numColumns;zi++)
        { 
            g.setColor(Color.BLACK);
            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
            getX(0)+zi*getWidth2()/(numColumns),getY(getHeight2()));
        }
        
        for (int xax=0;xax<numColumns;xax++)
        { 
           for (int yax=0;yax<numRows;yax++)
           { 
               if(board[xax][yax] != null)
               {
                   g.setColor(board[xax][yax].getColor());
                   g.fillOval(getX(0)+xax*getWidth2()/numColumns,
                    getY(0)+yax*getHeight2()/numRows,
                    getWidth2()/numColumns,
                    getHeight2()/numRows);
               }
           }
        }
        
          if (whowon == 1)
        {
            g.setColor(Color.GREEN); 
            g.setFont(new Font("Goudy Stout",Font.PLAIN,40));
            g.drawString("Player 1 Wins!", getX(getWidth2()/7),getYNormal(getHeight2() /2)); 
        }
          if (whowon == 2)
        {
            g.setColor(Color.GREEN); 
            g.setFont(new Font("Goudy Stout",Font.PLAIN,40));
            g.drawString("Player 2 Wins!", getX(getWidth2()/7),getYNormal(getHeight2() /2)); 
        }
        
        gOld.drawImage(image, 0, 0, null);
    }


////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.02;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        whowon = 0;
        board = new Piece[numRows][numColumns];
        
       // board[1][0] = new Piece(Color.RED);
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }

            reset();
        }
        if(whowon > 0)
            return;
        //horizontal wins
        for(int currow = numRows-1; currow>=0; currow--)
        {
            int redcount = 0;
            int blackcount = 0;
            for(int curcol = 0; curcol < numColumns; curcol++)
            {
                if(board[curcol][currow] != null)
                {
                    if(board[curcol][currow].getColor() == Color.red)
                    {
                        redcount++;
                        blackcount = 0;
                    }
                    else if(board[curcol][currow].getColor() == Color.black)
                    {
                        blackcount++;
                        redcount = 0;
                    }
                }
                else if(board[curcol][currow] == null)
                {
                    redcount = 0;
                    blackcount = 0;
                }
                if(blackcount >= 4)
                    whowon = 2;
                if(redcount >= 4)
                    whowon = 1;
            }
        }
        //vertical wins
        for(int curcol = numColumns-1; curcol>=0; curcol--)
        {
            int redcount = 0;
            int blackcount = 0;
            for(int currow = 0; currow < numRows; currow++)
            {
                if(board[curcol][currow] != null)
                {
                    if(board[curcol][currow].getColor() == Color.red)
                    {
                        redcount++;
                        blackcount = 0;
                    }
                    else if(board[curcol][currow].getColor() == Color.black)
                    {
                        blackcount++;
                        redcount = 0;
                    }
                }
                else if(board[curcol][currow] == null)
                {
                    redcount = 0;
                    blackcount = 0;
                }
                if(blackcount >= 4)
                    whowon = 2;
                if(redcount >= 4)
                    whowon = 1;
            }
        }
       //diagonal wins
       
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}

