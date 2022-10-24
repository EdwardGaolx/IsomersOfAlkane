
/**
 * Write a description of class Compound here.
 *
 * @author (Edward Gao)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Compound
{
    // instance variables
    private ArrayList<Element> elements = new ArrayList<Element>();
    private int[][] arrangement;
    
    private ArrayList<Integer> carbonPosition = new ArrayList<Integer>();
    private ArrayList<Integer> carbonMainChain = new ArrayList<Integer>();
    private ArrayList<Integer> carbonSideChain = new ArrayList<Integer>();
    private ArrayList<Integer> position = new ArrayList<Integer>();
    private ArrayList<String> sideChainBranches = new ArrayList<String>();

    // constructor
    public Compound(ArrayList<Element> elementsIn,int[][] arrIn)
    {
        for(int i=0;i<elementsIn.size();i++)
            elements.add(elementsIn.get(i));
        
        arrangement = arrIn;
    }
    
    public int getNumOfCarbon()
    {
        return carbonPosition.size();
    }
    
    public int getNumOfHydrogen()
    {
        return elements.size() - carbonPosition.size();
    }
    
    public int[][] getArrangement()
    {
        return arrangement;
    }
    
    public ArrayList<Integer> getCarbonPosition()
    {
        return carbonPosition;
    }
    
    public ArrayList<Integer> getCarbonMainChain()
    {
        return carbonMainChain;
    }
    
    public ArrayList<Integer> getCarbonSideChain()
    {
        return carbonSideChain;
    }
    
    //This correspond to the number of symbols need to be print horizontal as graph
    public int getWidthOfGraph()
    {
        return carbonMainChain.size()+ 2;
    }
    
    //This correspond to the number of symbols need to be print vertically as graph
    public int getHeightOfGraph()
    {
        int retValue =3;
        boolean propylCheck =false;
        boolean ethylCheck =false;
        boolean methylCheck =false;
        
        for(int i=0;i<sideChainBranches.size();i++)
        {
    
            if(sideChainBranches.get(i)=="propyl")
                propylCheck=true;
            if(sideChainBranches.get(i)=="ethyl")
                ethylCheck=true;
            if(sideChainBranches.get(i)=="methyl")
                methylCheck=true;
                
        }
        
        if(propylCheck==true)
            retValue+=6;
        if(ethylCheck==true&&propylCheck==false)
            retValue+=4;
        if(methylCheck==true&&ethylCheck==false&&propylCheck==false)
            retValue+=2;
        
        return retValue;
    }
    
    public int getSize()
    {
        return elements.size();
    }
    
    public Element getElement(int intIn)
    {
        return elements.get(intIn);
    }
    
    public ArrayList<Element> getAllElements()
    {
        return elements;
    }
    
    
    //This function is used to find the name of the compound
    public String findName()
    {
        String retValue ="";
        ArrayList<String> elementSpecies = new ArrayList<String>();
        
        //These are the name of main chain which correspond to the nth carbon
        String[] prefixName = {"meth","eth","prop","but","pent","hex","hept","oct","non","dec"};
        
        //This process identify all the elements which are carbon
        for(int i=0;i<elements.size();i++)
        {
            if(elements.get(i).getElementName()=="C")
            {
                carbonPosition.add(i);
            }
            
            for(int j=0;j<elementSpecies.size();j++)
            {
                if(elementSpecies.get(j)==elements.get(j).getElementName())
                    continue;
                else if(elementSpecies.size()==0||j==elementSpecies.size()-1){
                    elementSpecies.add(elements.get(j).getElementName());
                }
            }
            
        }
        
        //This find out the first carbon of the main chain
        int startNode = findStartNode(carbonPosition);
        System.out.println("Start node: " + startNode); 
        
        //This finds all the main chain carbons ( the node which the longest route includes)
        carbonMainChain = findCarbonMainChain(startNode,carbonPosition,carbonMainChain);
        
        //This process adds all the remainning carbon to the side chain list
        for(int i=0;i<carbonPosition.size();i++)
        {
            boolean match = false;
            for(int j=0;j<carbonMainChain.size();j++)
                if(carbonPosition.get(i)==carbonMainChain.get(j))
                    match = true;
            
            if(match==false)
                carbonSideChain.add(carbonPosition.get(i));
        }
        
        for(int i=0;i<carbonPosition.size();i++)
            System.out.println("carbon position: " + carbonPosition.get(i));
            
        for(int i=0;i<carbonMainChain.size();i++)
            System.out.println("carbon main chain position: " + carbonMainChain.get(i));    
            
        for(int i=0;i<carbonSideChain.size();i++)
            System.out.println("carbon side chain position: " + carbonSideChain.get(i));
        
        //This variable correspond to the combined name of all side chains
        String sideChainName = namingSideChain(carbonMainChain,carbonSideChain);    
            
            
        retValue = sideChainName + prefixName[carbonMainChain.size()-1]+ "ane";
        
        return retValue;
    }
    
    //This function is used to find the name of each side chain carbon branches
    public String namingSideChain(ArrayList<Integer> mainChainIn,ArrayList<Integer> sideChainCarbon)
    {
        String retValue ="";
        //These are the name of side chain which contain n carbon
        String[] suffixName = {"methyl","ethyl","propyl","butyl"};
        
        //The reason I made two pair of position and branch name is because I want to work out whether the graph should be interpret from the left hand side or right hand side
        //This is useful when deleting isomorphic graph.
        ArrayList<Integer> tempPos1 = new ArrayList<Integer>();
        ArrayList<String> tempBranch1 = new ArrayList<String>();
        
        ArrayList<Integer> tempPos2 = new ArrayList<Integer>();
        ArrayList<String> tempBranch2 = new ArrayList<String>();
        
        //This finds the side chain carbon which connect directly to the main chain carbon and its position
        for(int i=0;i<sideChainCarbon.size();i++)
        {
            for(int j=0;j<mainChainIn.size();j++)
            {
                if(arrangement[sideChainCarbon.get(i)][mainChainIn.get(j)]==1)
                {
                    int current =i;
                    int num=0;
                    //This finds the number of remaining carbon which follow the side chain carbon as a branch
                    for(int k=0;k<sideChainCarbon.size();k++)
                    {
                        if(arrangement[sideChainCarbon.get(current)][sideChainCarbon.get(k)]==1)
                        {
                            current = k;
                            num++;
                        }
                    }
                    
                    System.out.println("position at " + (j+1));
                    System.out.println("side chain name " + (suffixName[num]));
                    
                    System.out.println("position at " + (mainChainIn.size()-j));
                    System.out.println("side chain name " + (suffixName[num]));
                    
                    //This interpret the position from left
                    tempPos1.add((j+1));
                    tempBranch1.add(suffixName[num]);
                    
                    //This interpret the position from right
                    tempPos2.add((mainChainIn.size()-j));
                    tempBranch2.add(suffixName[num]);
                }
            }
        }
        
        //This process is used to determine should I process the graph from left or right
        //According to the nomenclature rule, the graph should be interpret when the most significant position is at the lowest
        boolean startLeft= true;
        int currentMaxPos =0;
        for(int i=0;i<tempPos1.size();i++)
        {
            if(tempPos1.get(i)>currentMaxPos)
            {
                startLeft=false;
                currentMaxPos = tempPos1.get(i);
            }
            if(tempPos2.get(i)>currentMaxPos)
            {
                startLeft=true;
                currentMaxPos = tempPos2.get(i);
            }
        }
        
        //In case both way have the same position as most significant, I will reject the one with more of such position
        int maxCount1 =0;
        int maxCount2 =0;
        for(int i=0;i<tempPos1.size();i++)
        {
            if(tempPos1.get(i)==currentMaxPos)
            {
                maxCount1++;
            }
            if(tempPos2.get(i)==currentMaxPos)
            {
                maxCount2++;
            }
        }
        
        if(maxCount1>maxCount2)
            startLeft=false;
        else
            startLeft=true;
        
            
        if(startLeft)
        {
            for(int i=0;i<tempPos1.size();i++)
                position.add(tempPos1.get(i));
            for(int i=0;i<tempBranch1.size();i++)
                sideChainBranches.add(tempBranch1.get(i));
        }
        else{
            for(int i=0;i<tempPos2.size();i++)
                position.add(tempPos2.get(i));
            for(int i=0;i<tempBranch2.size();i++)
                sideChainBranches.add(tempBranch2.get(i));
        }
        
        //According to the nomenclature rule, the position of each type of side chain should be in ascending order
        //So I sort the list
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i=0;i<position.size()-1;i++)
            {
                if(position.get(i)>position.get(i+1))
                {
                    int temp = position.get(i);
                    position.set(i,position.get(i+1));
                    position.set(i+1,temp);
                    String tempStr = sideChainBranches.get(i);
                    sideChainBranches.set(i,sideChainBranches.get(i+1));
                    sideChainBranches.set(i+1,tempStr);
                    sorted = false;
                }
                
            }
        }
        
        for(int i=0;i<position.size();i++)
            System.out.println("Sorted position at " + position.get(i));
        
        //As the following method of adding name to the return value is distructive, I made a copy of branches name
        ArrayList<String> tempBranches = new ArrayList<String>();
        for(int i=0;i<sideChainBranches.size();i++)
        {
            tempBranches.add(sideChainBranches.get(i));
        }
        
        int counter=0;
        for(int i=0;i<tempBranches.size();i++)
        {
            String name = tempBranches.get(0);
            //If there are more than one of the same type of side chain, should add the corresponding string to indicate
            String[] countName = {"di","tri","tetra","penta","hexa","hepta","octa"};
            int num=1;
            tempBranches.remove(0);
            retValue += position.get(0);
            position.remove(0);
            
            //Find the following branches which has the same type of side chain, first add the position as it is already in ascending order
            for(int j=counter;j<tempBranches.size();j++)
            {
                if(name==tempBranches.get(j))
                {
                    retValue += ","+ position.get(j);
                    position.remove(j);
                    tempBranches.remove(j);
                    num++;
                    j--;
                }
            }
            if(num>=2)
            {
                retValue+= "-"+countName[num-2]+name+"-";
                i--;
            }
            else{
                //If the branch is unique
                retValue+= "-"+name+"-";
                i--;
            }
        }
            
        
        return retValue;
    }

    //This is a recursive function which find the carbon main chain
    public ArrayList<Integer> findCarbonMainChain(int currentNode, ArrayList<Integer> allCarbonIn,ArrayList<Integer> currentMainChainIn)
    {
        
            ArrayList<Integer> retValue = new ArrayList<Integer>();
            
            ArrayList<Integer> mainChain = new ArrayList<Integer>();
            
            for(int i=0;i<currentMainChainIn.size();i++)
                mainChain.add(currentMainChainIn.get(i));
            
            System.out.println("currentNode = "+allCarbonIn.get(currentNode));
            
            //As each carbon is connected to 4 more carbon, so there are up to 3 choice of next node for the function
            int[] possibleRoute = new int[3];
            
            int num =0;
            
            //Check all the neighbouring carbon of the current node carbon and find the ones which have not been visited
            for(int j=0;j<allCarbonIn.size();j++)
            {
                if(arrangement[allCarbonIn.get(currentNode)][allCarbonIn.get(j)]==1)
                {
                    boolean visitedCheck = false;
                    for(int i=0;i<mainChain.size();i++)
                        if(allCarbonIn.get(j)==mainChain.get(i))
                            visitedCheck = true;
                            
                    if(visitedCheck == false)    
                    {
                            possibleRoute[num] =j;
                            num++;
                    }
                     
                }   
            }
                    
             
            //In the case there are 3 route, find the route with longest distance
            if(num==3)
            {
                mainChain.add(allCarbonIn.get(currentNode));
                
                ArrayList<Integer> temp1 = new ArrayList<Integer>();
                temp1 = mainChain;
                ArrayList<Integer> temp2 = new ArrayList<Integer>();
                temp2 = mainChain;
                ArrayList<Integer> temp3 = new ArrayList<Integer>();
                temp3 = mainChain;
                
                int size1 =findCarbonMainChain(possibleRoute[num-1],allCarbonIn,temp1).size();
                int size2 = findCarbonMainChain(possibleRoute[num-2],allCarbonIn,temp2).size();
                int size3 =findCarbonMainChain(possibleRoute[num-3],allCarbonIn,temp3).size();
                
                if(size1>=size2&&size1>=size3)
                {
                    //currentMainChainIn.add(allCarbonIn.get(currentNode));
                    retValue = findCarbonMainChain(possibleRoute[num-1],allCarbonIn,mainChain);
                    
                }
                if(size2>=size1&&size2>=size3)
                {
                    //currentMainChainIn.add(allCarbonIn.get(currentNode));
                    retValue = findCarbonMainChain(possibleRoute[num-2],allCarbonIn,mainChain);
                    
                }
                if(size3>=size2&&size3>=size1)
                {
                    //currentMainChainIn.add(allCarbonIn.get(currentNode));
                    retValue = findCarbonMainChain(possibleRoute[num-3],allCarbonIn,mainChain);
                    
                }
               
            }
            //In the case there are 2 route, find the route with longest distance
            if(num==2)
            {
                
                mainChain.add(allCarbonIn.get(currentNode));
                
                ArrayList<Integer> temp1 = new ArrayList<Integer>();
                temp1 = mainChain;
                ArrayList<Integer> temp2 = new ArrayList<Integer>();
                temp2 = mainChain;
                
                int size1 =findCarbonMainChain(possibleRoute[num-1],allCarbonIn,temp1).size();
                int size2 = findCarbonMainChain(possibleRoute[num-2],allCarbonIn,temp2).size();
                
                //System.out.println(" size1 = "+size1);
                //System.out.println(" size2 = "+size2);
                if(size1>size2)
                {
                    //currentMainChainIn.add(allCarbonIn.get(currentNode));
                    retValue = findCarbonMainChain(possibleRoute[num-1],allCarbonIn,mainChain);
                    
                }
                //if(findCarbonMainChain(possibleRoute[num-2],allCarbonIn,currentMainChainIn).size()>findCarbonMainChain(possibleRoute[num-1],allCarbonIn,currentMainChainIn).size())
                else
                {
                    //currentMainChainIn.add(allCarbonIn.get(currentNode));
                    retValue = findCarbonMainChain(possibleRoute[num-2],allCarbonIn,mainChain);
                }
            }
            //In the case there are 1 route just follow that route
            if(num==1)
            {
                
                mainChain.add(allCarbonIn.get(currentNode));
                
                retValue = findCarbonMainChain(possibleRoute[num-1],allCarbonIn,mainChain);

                //allCarbonIn.remove(currentNode);
                
            }
            //If there is no more neighbouring node, means it is the end node, so just add the value and return
            if(num==0)
            {
                mainChain.add(allCarbonIn.get(currentNode));
                retValue = mainChain;
            }

        
        
        
        
            return retValue;
    }
    
    
    //This function is used to find the correct first carbon for the main chain
    public int findStartNode( ArrayList<Integer> allCarbonIn)
    {
        int retValue =0;
        
        //Possible node can only be the one with one connection,because it is either end or start
        ArrayList<Integer> possibleNodes = new ArrayList<Integer>();
        for(int i=0;i<allCarbonIn.size();i++)
        {
            int num=0;
            for(int j=0;j<allCarbonIn.size();j++)
                if(arrangement[allCarbonIn.get(i)][allCarbonIn.get(j)]==1)
                    num++;
                    
            if(num==1)
            {
                possibleNodes.add(i);
            }
        }
        
        
        //Find the pair of possible node which has the longest distance between them, and they are the start and end node
        int maxDistance =0;
        for(int i=0;i<possibleNodes.size();i++)
            for(int j=i+1;j<possibleNodes.size();j++)
            {
                int distance =findLengthBetweenNodes(possibleNodes.get(i),possibleNodes.get(j),allCarbonIn);
                //System.out.println("distance is : " + distance);
                //System.out.println("Nodes are : " + possibleNodes.get(i) + " " + possibleNodes.get(j));
                if(distance>maxDistance)
                {
                    maxDistance = distance;
                    //System.out.println("maximum distance : " + maxDistance);
                    //System.out.println("Nodes are : " + possibleNodes.get(i)+ " " + possibleNodes.get(j));
                    retValue =possibleNodes.get(i);
                }
            }
            
        return retValue;
    }
    
    //This function used to find the distance between two nodes
    public int findLengthBetweenNodes(int node1In,int node2In,ArrayList<Integer> allCarbonIn)
    {
        int retValue =0;
        int[] distance = new int[allCarbonIn.size()];
        distance[node1In]=0;
        
        boolean[] visited = new boolean[allCarbonIn.size()];
        for(int i=0;i<visited.length;i++)
            visited[i]=false;
        
        
        ArrayList<Integer> queue = new ArrayList<Integer>();
        
        queue.add(node1In);
        
        visited[node1In]=true;
        
        //Use BFS to fill in the distance table
        while(queue.size()!=0)
        {
            int front = queue.get(0);
            queue.remove(0);
            
            for(int i=0;i<allCarbonIn.size();i++)
            {
                if(visited[i])
                    continue;
                
                if(arrangement[allCarbonIn.get(front)][allCarbonIn.get(i)]==1)
                {
                    distance[i] = distance[front]+1;
                    queue.add(i);
                    visited[i]=true;
                }
            }
        }
            
            
        return distance[node2In];
    }
    
    //This function is used to check whether the compound created is valid
    public boolean validCheck()
    {
        boolean retValue = true;
        for(int i=0;i<elements.size();i++)
        {
            int numOfBonds =0;
            for(int j=0;j<arrangement[i].length;j++)
            {
                if(arrangement[i][j]==1)
                    numOfBonds++;
            }
            //Compare the number of bonds that this element is currently having with the number of bonds it has to have
            if(elements.get(i).findValency()!= numOfBonds)
            {
                retValue = false;
                System.out.println("false");
            }
        }
        
        return retValue;
    }
}
