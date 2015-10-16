
package boardgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class Piece {
    Graphics2D g;  
    private Color myColor;
    Piece(Color _col){
        myColor = _col;
    }
    public Color getColor()
    {
        return(myColor);
    }
    public void setColor(Color _col)
    {
        myColor = _col;
    }
}
