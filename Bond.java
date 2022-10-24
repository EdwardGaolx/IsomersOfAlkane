
/**
 * Write a description of class Edge here.
 *
 * @author (Edward Gao)
 * @version (a version number or a date)
 */
import java.awt.*;
public class Bond
{
    // instance variables
    protected Symbol vertice1;
    protected Symbol vertice2;

    
    public Bond(Symbol v1,Symbol v2)
    {
        
        vertice1 = v1;
        vertice2 = v2;
    }

    
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(vertice1.centreX,vertice1.centreY,vertice2.centreX,vertice2.centreY);
    }
}