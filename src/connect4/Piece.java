package connect4;
import java.awt.*;

public class Piece {
    private Color color;
    Piece(Color _color)
    {
        color = _color;
    }
    Color getColor()
    {
        return (color);
    }
    void setColor(Color _color)
    {
        color = _color;
    }    
}
