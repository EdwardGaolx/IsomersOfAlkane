
/**
 * Write a description of class SymbolCanvasMouseListener here.
 *
 * @author (Edward Gao)
 * @version (a version number or a date)
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
 public class CompoundConstructorMouseListener implements MouseListener{ 
    private CompoundConstructor myConstructor;
    Symbol temp1= new Symbol(0,0,0);
    Symbol temp2= new Symbol(0,0,0);
    //This variable record if the left click was clicked before the right click (for the link enabled mode) 
    boolean checkFirst = false;
    public CompoundConstructorMouseListener(CompoundConstructor constructorIn) {      
        this.myConstructor = constructorIn;
        
    } 
    
    public boolean equals(Symbol s1,Symbol s2)
    {
        boolean retValue = false;
        if(s1.centreX == s2.centreX && s1.centreY == s2.centreY)
            retValue = true;
        return retValue;
    }
    
    @Override    
    public void mouseClicked(MouseEvent e) {
        if(!myConstructor.linkEnable){
                //If the program is at normal mode
                //Left click will draw symbol, right click will delete symbol
                if(e.getButton() ==MouseEvent.BUTTON3){
                for(int i=0;i<myConstructor.symbolList.size();i++)
                {
                    //Check if the position of click is within the cycle of symbol,if so delete the symbol
                    if(e.getX()<=myConstructor.symbolList.get(i).centreX+myConstructor.symbolList.get(i).length/2 && e.getX()>=myConstructor.symbolList.get(i).centreX-myConstructor.symbolList.get(i).length/2
                    && e.getY()<=myConstructor.symbolList.get(i).centreY+myConstructor.symbolList.get(i).length/2 && e.getY()>=myConstructor.symbolList.get(i).centreY-myConstructor.symbolList.get(i).length/2)
                        {
                            for(int j=0;j<myConstructor.bondList.size();j++)
                            {
                                if(equals(myConstructor.bondList.get(j).vertice1,myConstructor.symbolList.get(i)) ||equals(myConstructor.bondList.get(j).vertice2,myConstructor.symbolList.get(i)))
                                {
                                    myConstructor.bondList.remove(j);
                                    j--;
                                    
                                }
                            }
                
                            myConstructor.symbolList.remove(i);
                            myConstructor.repaint();
                        }
                }
            }
            else{
                //If left click draw the symbol
                if(myConstructor.cboElementTypes.getSelectedItem() == "Carbon")
                    {
                        myConstructor.addElementSymbol(e.getX(),e.getY(),80,"C");
                    }
                if(myConstructor.cboElementTypes.getSelectedItem() == "Hydrogon")
                    {
                       
                        myConstructor.addElementSymbol(e.getX(),e.getY(),80,"H");
                }
            }
                System.out.println(e.getX() + " " + e.getY()); 
        }
        else{
            //Link enabled mode is on
            if(e.getButton() == MouseEvent.BUTTON1){
                //Left click select the first symbol
                for(int i=0;i<myConstructor.symbolList.size();i++)
                {
                    if(e.getX()<=myConstructor.symbolList.get(i).centreX+myConstructor.symbolList.get(i).length/2 && e.getX()>=myConstructor.symbolList.get(i).centreX-myConstructor.symbolList.get(i).length/2
                    && e.getY()<=myConstructor.symbolList.get(i).centreY+myConstructor.symbolList.get(i).length/2 && e.getY()>=myConstructor.symbolList.get(i).centreY-myConstructor.symbolList.get(i).length/2)
                        {
                            temp1 = myConstructor.symbolList.get(i);
                            temp2 = myConstructor.symbolList.get(i);
                            checkFirst = true;
                        }
                }
            }
            if(e.getButton() == MouseEvent.BUTTON3){
                if(checkFirst)
                    for(int i=0;i<myConstructor.symbolList.size();i++)
                    {
                        //Left click first and then right click select the second symbol
                        if(e.getX()<=myConstructor.symbolList.get(i).centreX+myConstructor.symbolList.get(i).length/2 && e.getX()>=myConstructor.symbolList.get(i).centreX-myConstructor.symbolList.get(i).length/2
                        && e.getY()<=myConstructor.symbolList.get(i).centreY+myConstructor.symbolList.get(i).length/2 && e.getY()>=myConstructor.symbolList.get(i).centreY-myConstructor.symbolList.get(i).length/2)
                        {
                                temp2 = myConstructor.symbolList.get(i);
                                checkFirst = false;
                            }
                    }
                else{
                    for(int i=0;i<myConstructor.symbolList.size();i++)
                    {
                        //Right click select the first symbol
                        if(e.getX()<=myConstructor.symbolList.get(i).centreX+myConstructor.symbolList.get(i).length/2 && e.getX()>=myConstructor.symbolList.get(i).centreX-myConstructor.symbolList.get(i).length/2
                        && e.getY()<=myConstructor.symbolList.get(i).centreY+myConstructor.symbolList.get(i).length/2 && e.getY()>=myConstructor.symbolList.get(i).centreY-myConstructor.symbolList.get(i).length/2)
                        {
                                temp1 = myConstructor.symbolList.get(i);
                                temp2 = myConstructor.symbolList.get(i);
                                checkFirst = false;
                            }
                    }
                }
            }
            //Create a bond using two selected symbols
            myConstructor.addBond(temp1,temp2);
        }
    } 
 
    @Override   
    public void mousePressed(MouseEvent e) {    
           
    } 
 
    @Override
    public void mouseReleased(MouseEvent e) {          
 
    } 
 
    @Override
    public void mouseEntered(MouseEvent e) { 
    } 
 
    @Override
    public void mouseExited(MouseEvent e) {          
 
 
 
    } 
 
}