/**
 * Write a description of class GUI here.
 *
 * @author (Edward Gao)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;
public class CompoundConstructor extends JFrame implements ActionListener
{
    //These are variables for the GUI and storing symbols and bonds
    JPanel pane = new JPanel();
    
    JLabel instructions = new JLabel("Please draw the compound");
    
    JLabel nameOfCompound = new JLabel("Name of Compound");
    JLabel numberOfIsomers = new JLabel("Number of Isomers left: 0");
    
    JButton addBtn = new JButton(" Add Symbol ");
    JButton clearBtn = new JButton("Clear screen");
    JButton completeBtn = new JButton("Complete");
    JButton nextOneBtn = new JButton("Show next one");
    JButton lastOneBtn = new JButton("Show previous one");
    
    JButton isomerBtn = new JButton("generate isomer");
    JButton validCheckBtn = new JButton("Valid Check");
    
    ArrayList<Compound> isomers = new ArrayList<Compound>();
    int currentIsomer=0;
    
    ArrayList<ElementSymbol> symbolList = new ArrayList<ElementSymbol>();
    ArrayList<ElementSymbol> sideSymbolList = new ArrayList<ElementSymbol>();
    ArrayList<ElementSymbol> hydrogenSymbolList = new ArrayList<ElementSymbol>();
    ArrayList<Bond> bondList = new ArrayList<Bond>();
    
    protected boolean linkEnable;
    
    String[] elementTypes= {"Carbon","Hydrogon"};
    JComboBox cboElementTypes = new JComboBox<String>(elementTypes);
    
    
    JButton linkBtn = new JButton("Bond Enable");
    JButton linkDisableBtn = new JButton("Bond Disable");
    
    Compound compound;
    
    public CompoundConstructor()
    {
        //Creation of GUI
        setSize(1500,1000);
        
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        pane.setLayout(layout);
        
        c.gridx =0;
        c.gridy =0;
        c.ipady =15;
        c.ipadx =15;
        c.insets = new Insets(20, 1000, 20, 10);
        c.gridwidth = 2;
        
        c.fill = GridBagConstraints.BOTH;
        
        Font font  =new Font("Verdana", Font.BOLD, 20);
        Font font2  =new Font("Verdana", Font.BOLD, 15);
        
        
        
        instructions.setFont(font);
        pane.add(instructions,c);
        
        c.gridy=1;
        
        nameOfCompound.setFont(font);
        pane.add(nameOfCompound,c);
        
        c.gridy =2;
        c.insets = new Insets(20, 1200, 20, 10);
        
        
        c.gridwidth = 1;
        
        
        
        
        numberOfIsomers.setFont(font2);
        pane.add(numberOfIsomers,c);
        
        
        
        c.gridy =3;
        
        
        
        cboElementTypes.setFont(font2);
        pane.add(cboElementTypes,c);
        
        c.gridy =4;
        
        linkBtn.addActionListener(this);
        pane.add(linkBtn,c);
        
        
        c.gridy =5;
        linkDisableBtn.addActionListener(this);
        linkDisableBtn.setEnabled(false);
        
        pane.add(linkDisableBtn,c);
        
        c.gridy =6;
        validCheckBtn.addActionListener(this);
        
        pane.add(validCheckBtn,c);
        
        c.gridy =7;
        completeBtn.addActionListener(this);
        
        pane.add(completeBtn,c);
        completeBtn.setVisible(false);
        
        c.gridy =8;
        isomerBtn.addActionListener(this);
        isomerBtn.setEnabled(false);
        
        pane.add(isomerBtn,c);
        
        c.gridy =9;
        
        nextOneBtn.addActionListener(this);
        nextOneBtn.setEnabled(false);
        
        pane.add(nextOneBtn,c);
        
        c.gridy =10;
        
        lastOneBtn.addActionListener(this);
        lastOneBtn.setEnabled(false);
        
        pane.add(lastOneBtn,c);
        
        
        c.gridy=11;
        clearBtn.addActionListener(this);
        
        pane.add(clearBtn,c);
        
        addMouseListener(new CompoundConstructorMouseListener(this));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(pane);
        setVisible(true);
    }
    
    //Corresponding actions when buttons were clicked
    public void actionPerformed(ActionEvent e)
    {
        //Clear screen button reset the program
        if(e.getSource()== clearBtn){
            clearScreen();
            validCheckBtn.setVisible(true);
            completeBtn.setVisible(false);
            nameOfCompound.setText("Name of Compound");
            numberOfIsomers.setText("Number of isomers left: 0" );
            instructions.setText("Please draw the compound");
            currentIsomer=-1;
            nextOneBtn.setEnabled(false);
            lastOneBtn.setEnabled(false);
            isomerBtn.setEnabled(false);
            linkDisableBtn.setEnabled(false);
            linkBtn.setEnabled(true);
            validCheckBtn.setEnabled(true);
            repaint();
        }
        //Link enable button allow user to link two symbols by clicking mouse
        if(e.getSource()== linkBtn){
            linkEnable = true;
            linkDisableBtn.setEnabled(true);
            linkBtn.setEnabled(false);
        }
        //Link disable button disabled the linking mode, and go back to original function which is draw symbol by clicking mouse
        if(e.getSource()== linkDisableBtn){
            linkEnable = false;
            linkBtn.setEnabled(true);
            linkDisableBtn.setEnabled(false);
        }
        //Valid check button will allow user to go to next operation if compound drawing is valid
        if(e.getSource()== validCheckBtn){
            
            try{
                constructCompound();
                linkEnable = false;
                if(compound.validCheck())
                {
                    String name =compound.findName();
                    System.out.println(name);
                    nameOfCompound.setText(name);
                    isomerBtn.setEnabled(true);
                    validCheckBtn.setEnabled(false);
                    instructions.setText("Compound is valid, now you can find isomers");
                }
                else{
                    instructions.setText("Compound is not valid !");
                    System.out.println("false");
                }
            }
            
            catch (Exception ee){
                System.out.println("Error input");
                instructions.setText("Compound is not valid !");

            }
            
            
        }
        //This button generate isomers
        if(e.getSource()== isomerBtn){
            try{
                constructCompound();
                String name =compound.findName();
                System.out.println(name);
                nameOfCompound.setText(name);
                clearScreen();
                printCompound(compound);
                Isomers iso = new Isomers(compound);
                isomers = iso.findIsomers();
                nextOneBtn.setEnabled(true);
                isomerBtn.setEnabled(false);
                validCheckBtn.setEnabled(true);
                for(int i=0;i<isomers.size();i++)
                    if(isomers.get(i).validCheck()==false)
                    {
                        isomers.remove(i);
                        i--;
                    }
                instructions.setText("Total number of isomers: " + isomers.size());
                numberOfIsomers.setText("Number of isomers left: " + (isomers.size()-1));
            }
            catch (Exception ee){
                    System.out.println("Error input");
                    instructions.setText("Can not find isomers");
    
            }
        }
        //This button allow user to see the displayed formula of next isomer
        if(e.getSource()==nextOneBtn){
            
            if(currentIsomer<isomers.size()-1)
            {
                currentIsomer++;
                String name =isomers.get(currentIsomer).findName();
                System.out.println(name);
                nameOfCompound.setText(name);
                clearScreen();
                printCompound(isomers.get(currentIsomer));
                numberOfIsomers.setText("Number of isomers left: " + (isomers.size()-currentIsomer-1));
                lastOneBtn.setEnabled(true);
                if(currentIsomer==isomers.size()-1)
                {
                    instructions.setText("Use clear button to restart");
                    numberOfIsomers.setText("Finish printing all isomers");
                    nextOneBtn.setEnabled(false);
                }
            }
        }
        //This button allow user to see the displayed formula of previous isomer
        if(e.getSource()==lastOneBtn){
            
            if(currentIsomer>0)
            {
                Isomers iso = new Isomers(compound);
                isomers = iso.findIsomers();
                currentIsomer--;
                String name =isomers.get(currentIsomer).findName();
                System.out.println(name);
                nameOfCompound.setText(name);
                clearScreen();
                printCompound(isomers.get(currentIsomer));
                numberOfIsomers.setText("Number of isomers left: " + (isomers.size()-currentIsomer-1));
                nextOneBtn.setEnabled(true);
                if(currentIsomer==0)
                    lastOneBtn.setEnabled(false);
            }
            
        }
    }
    
    public void clearScreen(){
        ArrayList<ElementSymbol> tempList = new ArrayList<ElementSymbol>();
        symbolList = tempList;
        ArrayList<ElementSymbol> tempList2 = new ArrayList<ElementSymbol>();
        sideSymbolList = tempList2;
        ArrayList<ElementSymbol> tempList3 = new ArrayList<ElementSymbol>();
        hydrogenSymbolList = tempList3;
            
        ArrayList<Bond> tempBondList = new ArrayList<Bond>();
        bondList = tempBondList;
    }
    
    public void addElementSymbol(int x, int y, int length, String letter) { 
        symbolList.add(new ElementSymbol(x,y,length,letter));  
        repaint();
    }
    
    public void addBond(Symbol v1,Symbol v2){
        bondList.add(new Bond(v1,v2));
        repaint();
    }
    
    //This procedure use the information from user's drawing to create compound object.
    public void constructCompound(){
        ArrayList<Element> elements = new ArrayList<Element>();
        int[][] arrangement = new int[symbolList.size()][symbolList.size()];
        
        //Create elements using symbols
        for(int i=0;i<symbolList.size();i++)
        {
            if(symbolList.get(i).getSymbolLetter()=="C")
                elements.add(new Element(symbolList.get(i).getSymbolLetter(),6));
            if(symbolList.get(i).getSymbolLetter()=="H")
                elements.add(new Element(symbolList.get(i).getSymbolLetter(),1));
            
        }
        
        //Modify the arrangement adjacency matrix to represent the information of bonds
        for(int i=0;i<bondList.size();i++)
        {
            int symbol1=0;
            int symbol2=0;
            for(int j=0;j<symbolList.size();j++)
            {
                if(symbolList.get(j).equals(bondList.get(i).vertice1))
                    symbol1 = j;
            }
            
            for(int k=0;k<symbolList.size();k++)
            {
                if(symbolList.get(k).equals(bondList.get(i).vertice2))
                    symbol2 = k;
            }
            
            arrangement[symbol1][symbol2]=1;
            arrangement[symbol2][symbol1]=1;
           
        }
        
        for(int i=0;i<arrangement.length;i++)
            arrangement[i][i] =0;
            
        compound = new Compound(elements,arrangement);
    }
    
    //This procedure draw the displayed formula of chosen compound
    public void printCompound(Compound compoundIn){
        int rowsOfSymbol = compoundIn.getHeightOfGraph();
        int yDistanceBetweenSymbols = 1000/(rowsOfSymbol+2);
        int columnsOfSymbol = compoundIn.getWidthOfGraph();
        int xDistanceBetweenSymbols = 1500/(columnsOfSymbol+2);
        
        //This variable check if the corresponding element has been printed
        boolean[] checkPrinted = new boolean[compoundIn.getAllElements().size()];
        for(int i=0;i<checkPrinted.length;i++)
            checkPrinted[i] = false;
        
        //This variable check if the main chain carbon has empty positiion at top righ bot or left
        //This variable when printing hydrogen connects to the main chain carbon
        boolean[][] topRightBotLeftMC = new boolean[compoundIn.getCarbonMainChain().size()][4];
        for(int i=0;i<topRightBotLeftMC.length;i++)
            for(int j=0;j<topRightBotLeftMC[i].length;j++)
                topRightBotLeftMC[i][j]=false;
        
        //This variable check if the side chain carbon has empty positiion at top righ bot or left
        //This variable when printing hydrogen connects to the side chain carbon
        boolean[][] topRightBotLeftSC = new boolean[compoundIn.getCarbonSideChain().size()][4];
        for(int i=0;i<topRightBotLeftSC.length;i++)
            for(int j=0;j<topRightBotLeftSC[i].length;j++)
                topRightBotLeftSC[i][j]=false;
        
        //Convert main chain carbons to symbols
        for(int i=0;i<compoundIn.getCarbonMainChain().size();i++)
        {
            int x =xDistanceBetweenSymbols*(i+2);
            int y = 1000/2;
            ElementSymbol tempElement = new ElementSymbol(x,y,80,compoundIn.getElement(compoundIn.getCarbonMainChain().get(i)).getElementName());
            symbolList.add(tempElement);
            checkPrinted[compoundIn.getCarbonMainChain().get(i)] =true;
        }
        
        for(int i=0;i<compoundIn.getCarbonMainChain().size()-1;i++)
        {
            addBond(symbolList.get(i),symbolList.get(i+1));
            topRightBotLeftMC[i][1]=true;
            topRightBotLeftMC[i+1][3]=true;
        }
        
        ArrayList<Integer> orderOfAdding = new ArrayList<Integer>();
        //These two variables determine whether next carbon side chain shoud be draw above or below the main chain
        //This avoid two side chain overlap each other in the most case
        int top =0;
        int bot =1;
        //Convert side chain carbons to symbols
        for(int i=0;i<compoundIn.getCarbonSideChain().size();i++)
        {
            
            for(int j=0;j<compoundIn.getCarbonMainChain().size();j++)
            {
                int x=0;
                int y=0;
                if(compoundIn.getArrangement()[compoundIn.getCarbonSideChain().get(i)][compoundIn.getCarbonMainChain().get(j)]==1)
                {
                    x = symbolList.get(j).centreX;
                    y = symbolList.get(j).centreY + bot*2*yDistanceBetweenSymbols - top*2*yDistanceBetweenSymbols;
                    ElementSymbol tempElement = new ElementSymbol(x,y,80,compoundIn.getElement(compoundIn.getCarbonSideChain().get(i)).getElementName());
                    sideSymbolList.add(tempElement);
                    checkPrinted[compoundIn.getCarbonSideChain().get(i)] =true;
                    
                    orderOfAdding.add(compoundIn.getCarbonSideChain().get(i));
                    addBond(tempElement,symbolList.get(j));
                    
                    topRightBotLeftSC[i][2*top]=true;
                    topRightBotLeftMC[j][2*bot]=true;
                    
                    for(int k=0;k<compoundIn.getCarbonSideChain().size();k++)
                    {
                        if(compoundIn.getArrangement()[compoundIn.getCarbonSideChain().get(i)][compoundIn.getCarbonSideChain().get(k)]==1)
                        {
                            y += bot*yDistanceBetweenSymbols-top*yDistanceBetweenSymbols;
                            ElementSymbol tempElement2 = new ElementSymbol(x,y,80,compoundIn.getElement(compoundIn.getCarbonSideChain().get(k)).getElementName());
                            sideSymbolList.add(tempElement2);
                            checkPrinted[compoundIn.getCarbonSideChain().get(k)] =true;
                            
                            orderOfAdding.add(compoundIn.getCarbonSideChain().get(k));
                            addBond(tempElement,tempElement2);
                            
                            topRightBotLeftSC[i][2*bot]=true;
                            topRightBotLeftSC[k][2*top]=true;
                        }
                    }
                    int temp = top;
                    top = bot;
                    bot = temp;
                }
            }
        }
        
        
        //Convert hydrogen carbons to symbols according the the empty position represent by topRightBotLeft arrays
        for(int i=0;i<compoundIn.getAllElements().size();i++)
        {
            if(checkPrinted[i]==false)
            {
                for(int j=0;j<compoundIn.getCarbonMainChain().size();j++)
                {
                    if(compoundIn.getArrangement()[i][compoundIn.getCarbonMainChain().get(j)]==1)
                    {
                        int x = symbolList.get(j).centreX;
                        int y = symbolList.get(j).centreY;
                        for(int a=0;a<topRightBotLeftMC[j].length;a++)
                        {
                            if(topRightBotLeftMC[j][a]==false)
                            {
                                if(a==0)
                                    y-=yDistanceBetweenSymbols;
                                if(a==1)
                                    x+=xDistanceBetweenSymbols;
                                if(a==2)
                                    y+=yDistanceBetweenSymbols;
                                if(a==3)
                                    x-=xDistanceBetweenSymbols;
                                topRightBotLeftMC[j][a]=true;
                                break;
                            }
                        }
                        ElementSymbol tempElement3 = new ElementSymbol(x,y,80,compoundIn.getElement(i).getElementName());
                        hydrogenSymbolList.add(tempElement3);
                        checkPrinted[i]=true;
                        addBond(symbolList.get(j),tempElement3);
                            
                    }
                }
                
                for(int k=0;k<compoundIn.getCarbonSideChain().size();k++)
                {
                    if(compoundIn.getArrangement()[i][compoundIn.getCarbonSideChain().get(k)]==1)
                    {
                        System.out.println("not match yet");
                        //int pos =0;
                        int xCor=0;
                        int yCor=0;
                        for(int b=0;b<orderOfAdding.size();b++)
                        {
                            
                            if(orderOfAdding.get(b)==compoundIn.getCarbonSideChain().get(k))
                            {
                                //pos =b;
                                System.out.println("matched");
                                xCor=sideSymbolList.get(b).centreX;
                                System.out.println(xCor);
                                yCor=sideSymbolList.get(b).centreY;
                                System.out.println(yCor);
                                for(int c=0;c<topRightBotLeftSC[k].length;c++)
                                {
                                    if(topRightBotLeftSC[k][c]==false)
                                    {
                                        if(c==0)
                                            yCor-=yDistanceBetweenSymbols;
                                        if(c==1)
                                            xCor+=xDistanceBetweenSymbols;
                                        if(c==2)
                                            yCor+=yDistanceBetweenSymbols;
                                        if(c==3)
                                            xCor-=xDistanceBetweenSymbols;
                                        topRightBotLeftSC[k][c]=true;
                                        break;
                                    }
                                }
                                
                                System.out.println(xCor);
                                System.out.println(yCor);
                                ElementSymbol tempElement4 = new ElementSymbol(xCor,yCor,80,compoundIn.getElement(i).getElementName());
                                hydrogenSymbolList.add(tempElement4);
                                checkPrinted[i]=true;
                                addBond(sideSymbolList.get(k),tempElement4);
                                System.out.println("added");
                            }
                        }
                    }
                }
            }
        }
                
    }
    
    //This procedure paint the GUI
    public void paint(Graphics g){
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        
        for(int i=0;i<symbolList.size();i++){
            symbolList.get(i).paint(g);
        }
        
        
        for(int i=0;i<sideSymbolList.size();i++){
            sideSymbolList.get(i).paint(g);
        }
        
        
        for(int i=0;i<hydrogenSymbolList.size();i++){
            hydrogenSymbolList.get(i).paint(g);
        }
        
        for(int i=0;i<bondList.size();i++){
            bondList.get(i).paint(g);
        }
        
        instructions.repaint();
        numberOfIsomers.repaint();
        nextOneBtn.repaint();
        lastOneBtn.repaint();
        nameOfCompound.repaint();
        cboElementTypes.repaint();
        linkBtn.repaint();
        clearBtn.repaint();
        linkDisableBtn.repaint();
        validCheckBtn.repaint();
        completeBtn.repaint();
        isomerBtn.repaint();
        
    }
}