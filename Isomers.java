
/**
 * 在这里给出对类 Isomers 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import java.util.ArrayList;
public class Isomers
{
    // instance variables
    private int numOfCarbon;
    private int numOfHydrogen;
    private ArrayList<carbonChain> incompleteIsomers = new ArrayList<carbonChain>();
    private ArrayList<carbonChain> uniqueChains = new ArrayList<carbonChain>();
    
    
    public Isomers(Compound compoundIn)
    {
        numOfCarbon = compoundIn.getNumOfCarbon();
        numOfHydrogen = compoundIn.getNumOfHydrogen();
    }

    //This function finds all the isomers of the same molecular formula
    public ArrayList<Compound> findIsomers()
    {
        ArrayList<Compound> retValue = new ArrayList<Compound>();
        
        // Isomer only exist when the carbon content is greater than 3
        if(numOfCarbon<=3)
            System.out.println("no isomer");
        else{
            isomerismCountInRepetition();
            removeRepetition();
        }    
        System.out.println("Number of all compound count in repeated: " +incompleteIsomers.size());
        System.out.println("Number of isomers: " +uniqueChains.size());
        
        //The unique carbon chains after removing all the duplicates
        //This process adds hydrogens to them and let them become complete compound
        for(int i=0;i<uniqueChains.size();i++)
        {
            ArrayList<Element> elements = new ArrayList<Element>();
            int[][] Arrangement = new int[numOfCarbon+numOfHydrogen][numOfCarbon+numOfHydrogen];
            for(int a=0;a<uniqueChains.get(i).getCarbon().size();a++)
                elements.add(uniqueChains.get(i).getCarbon().get(a));
            for(int j=0;j<uniqueChains.get(i).getArrangement().length;j++)
                for(int k=0;k<uniqueChains.get(i).getArrangement()[j].length;k++)
                    Arrangement[j][k] = uniqueChains.get(i).getArrangement()[j][k];
            
            for(int e=0;e<numOfCarbon;e++)
            {
                int numberOfBonds =0;
                for(int j=0;j<Arrangement[e].length;j++)
                    numberOfBonds += Arrangement[e][j];
                
                //Continue adding hydrogen until the carbon has 4 bonds
                while(numberOfBonds<4)
                {
                    elements.add(new Element("H",1));
                    Arrangement[e][elements.size()-1] =1;
                    Arrangement[elements.size()-1][e] =1;
                    numberOfBonds++;
                }
                //System.out.println("new number of bonds " +  numberOfBonds); 
            }
            
            retValue.add(new Compound(elements,Arrangement));
        }
        
        
        return retValue;
    }
    
    //Using hashed value generated from each incomplete carbon chain, and remove duplicates
    public void removeRepetition()
    {
        String[] hashValues = new String[incompleteIsomers.size()];
        boolean[] checkRepeated = new boolean[incompleteIsomers.size()];
        for(int i=0;i<checkRepeated.length;i++)
            checkRepeated[i] = false;
            
        for(int i=0;i<incompleteIsomers.size();i++)
            hashValues[i] = incompleteIsomers.get(i).getHashValue();
            
            
        for(int i=0;i<hashValues.length;i++)
            for(int j=i+1;j<hashValues.length;j++)
                //if(compareHashValue(hashValues[i],hashValues[j]))
                if(hashValues[i].equals(hashValues[j]))
                    checkRepeated[j] = true;
        
        for(int i=0;i<hashValues.length;i++)
            if(!checkNumOfC(hashValues[i]))
                checkRepeated[i]=true;
                
        
        for(int i=0;i<checkRepeated.length;i++)
            if(checkRepeated[i]==false)
                uniqueChains.add(incompleteIsomers.get(i));
                
        for(int i=0;i<uniqueChains.size();i++)
        
            System.out.println(uniqueChains.get(i).getHashValue());

    }
    
    public boolean checkNumOfC(String hashIn)
    {
        boolean retValue = true;
        
        int numOfC = Integer.parseInt(String.valueOf(hashIn.charAt(0)));
            
        for(int i=1;i<hashIn.length();i+=2)
            numOfC += Integer.parseInt(String.valueOf(hashIn.charAt(i)));
        
        System.out.println("number of carbon in hash Value "+ numOfC);
        if(numOfC!=numOfCarbon)
            retValue = false;
        
        return retValue;
    }
    
    
    //This function finds all the structure that can be formed for a carbon chain with n carbon
    public void isomerismCountInRepetition()
    {
        ArrayList<Element> baseChain = new ArrayList<Element>();
        int[][] baseArrangement = new int[numOfCarbon][numOfCarbon];
        for(int i=0;i<2;i++)
        {
            baseArrangement[i][i+1]=1;
            baseArrangement[i+1][i]=1;
        }
        
        for(int i=0;i<3;i++)
            baseChain.add(new Element("C",6));
        
        incompleteIsomers.add(new carbonChain(baseChain,baseArrangement));
        
        //Iteratively create new incomplete structure by adding one more carbon into the carbon chain
        for(int i=3;i<numOfCarbon;i++)
        {
            
            int currentNumOfIsomers = incompleteIsomers.size();
            // Record the number of incomplete structure with one less carbon
            // For each these structure add one more carbon in every possible way,try to add to each carbon that has not have 4 bonds yet
            // These creates alot of repetitions which need to be removed later
            while(currentNumOfIsomers>0){
                for(int k=1;k<incompleteIsomers.get(0).getNumOfCarbon();k++)
                {
                    if(incompleteIsomers.get(0).checkConnectionFull(k))
                    {
                        continue;
                    }
                    else{
                        ArrayList<Element> tempChain = new ArrayList<Element>();
                        int[][] tempArrangement = new int[numOfCarbon][numOfCarbon];
                        
                        for(int a=0;a<incompleteIsomers.get(0).getCarbon().size();a++)
                            tempChain.add(incompleteIsomers.get(0).getCarbon().get(a));
                            
                        for(int b=0;b<incompleteIsomers.get(0).getArrangement().length;b++)
                            for(int c=0;c<incompleteIsomers.get(0).getArrangement()[b].length;c++)
                                tempArrangement[b][c] = incompleteIsomers.get(0).getArrangement()[b][c];
                                
                        tempChain.add(new Element("C",6));
                        tempArrangement[k][tempChain.size()-1]=1;
                        tempArrangement[tempChain.size()-1][k]=1;
                        
                        incompleteIsomers.add(new carbonChain(tempChain,tempArrangement));
                    }
                }
                //After find new cases generated by each case, delete the case that made these new structures(the one with one less carbon)
                incompleteIsomers.remove(0);
                currentNumOfIsomers--;
            }
        }
    }
}
