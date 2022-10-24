
/**
 * Write a description of class Element here.
 *
 * @author (Edward Gao)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.io.*;
public class Element
{
    // instance variables
    private String elementName;
    private int atomicNumber;

    
    public Element(String nameIn,int numberIn)
    {
        elementName = nameIn;
        atomicNumber = numberIn;
    }
    
    public String getElementName()
    {
        return elementName;
    }
    
    public int getAtomicNumber()
    {
        return atomicNumber;
    }
    
    //find valency correspond the number of bonds that the element have to form.
    public int findValency()
    {
        int retValue=0;
        if(atomicNumber==1)
        {
            retValue=1;
        }
        else{
            retValue =(atomicNumber-2)%8;
            
            if(retValue > 4)
            {
                retValue = 8-retValue;
            }
        }
        return retValue;
    }
}
