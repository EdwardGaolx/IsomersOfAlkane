
/**
 * Write a description of class ElementSymbol here.
 *
 * @author (Edward Gao)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.io.*;
public class ElementSymbol extends Symbol
{
    // instance variables
    private String symbolLetter;
    private Font font  =new Font("Verdana", Font.BOLD, 30);
    
    
    public ElementSymbol(int xIn,int yIn,int lengthIn, String letterIn)
    {
        super(xIn,yIn,lengthIn);
        symbolLetter = letterIn;
        
    }

    public String getSymbolLetter()
    {
        return symbolLetter;
    }
    
    public String getLetter()
    {
        return symbolLetter;
    }
    
    //This function checks two symbol are identical or not
    public boolean equals(ElementSymbol symbolIn)
    {
        boolean retValue = false;
        if(symbolIn.getLetter() == symbolLetter&&symbolIn.centreX == centreX&&symbolIn.centreY == centreY&& symbolIn.length == length)
            retValue = true;
        
        return retValue;
        
    }
    
    public void paint(Graphics g)
    {
        
        g.setColor(Color.BLACK);
        g.drawOval(centreX-length/2, centreY-length/2, length, length);
        g.setFont(font);
        g.drawString(symbolLetter,centreX-length/8,centreY+length/8);
        
    }
}
