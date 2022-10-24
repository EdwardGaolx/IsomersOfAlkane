                                                           
/**
 * 在这里给出对类 carbonChain 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import java.util.ArrayList;
public class carbonChain
{
    // instance variables
    private ArrayList<Element> carbonChain = new ArrayList<Element>();
    private int[][] carbonArrangement;
    
    private int mainChainLength;
    private ArrayList<Integer> carbonMainChain = new ArrayList<Integer>();
    private ArrayList<Integer> sideChainLength = new ArrayList<Integer>();
    private ArrayList<Integer> sideChainPosition = new ArrayList<Integer>();
    
    
    public carbonChain(ArrayList<Element> carbonIn, int[][] arrIn)
    {
        for(int i=0;i<carbonIn.size();i++)
            carbonChain.add(carbonIn.get(i));
        
        carbonArrangement = arrIn;
        findMainChain();
        findSideChain();
    }
    
    
    
    public int getNumOfCarbon()
    {
        return carbonChain.size();
    }
    
    public ArrayList<Element> getCarbon()
    {
        return carbonChain;
    }
    
    // This function checks if the carbon already has 4 bonds
    public boolean checkConnectionFull(int carbonPosition)
    {
        boolean retValue = false;
        int numberOfConnection =0;
        for(int i=0;i<carbonArrangement[carbonPosition].length;i++)
            if(carbonArrangement[carbonPosition][i]==1)
                numberOfConnection++;
        if(numberOfConnection==4)
            retValue = true;
        
        return retValue;
    }
    
    public int[][] getArrangement()
    {
        return carbonArrangement;
    }
    
    //Function used to add one more carbon element to the graph
    public void extendCarbonChain(Element newCarbon, int connectedPosition)
    {
        carbonChain.add(newCarbon);
        carbonArrangement[connectedPosition][carbonChain.size()-1]=1;
        carbonArrangement[carbonChain.size()-1][connectedPosition]=1;
    }
    
    
    //Function used to find the carbon main chain
    public void findMainChain()
    {
        ArrayList<Integer> possibleNodes = new ArrayList<Integer>();
        for(int i=0;i<carbonChain.size();i++)
        {
            int num=0;
            for(int j=0;j<carbonChain.size();j++)
                if(carbonArrangement[i][j]==1)
                    num++;
                    
            if(num==1)
            {
                possibleNodes.add(i);
            }
        }
        
        int maxDistance =0;
        int node1 =-1;
        int node2 =-1;
        for(int i=0;i<possibleNodes.size();i++)
            for(int j=i+1;j<possibleNodes.size();j++)
            {
                
                int distance =findCurrentMainChain(possibleNodes.get(i),possibleNodes.get(j)).size();
                
                if(distance>maxDistance)
                {
                    maxDistance = distance;
                    
                    node1 =possibleNodes.get(i);
                    node2 =possibleNodes.get(j);
                }
            }
        carbonMainChain = findCurrentMainChain(node1,node2);
            
    }
    
    //Function used to find the side chain position and length of each side chain
    public void findSideChain()
    {
        ArrayList<Integer> sideCarbons = new ArrayList<Integer>();
        
        ArrayList<Integer> tempPos1 = new ArrayList<Integer>();
        ArrayList<Integer> tempBranch1 = new ArrayList<Integer>();
        
        ArrayList<Integer> tempPos2 = new ArrayList<Integer>();
        ArrayList<Integer> tempBranch2 = new ArrayList<Integer>();
        
        for(int i=0;i<carbonChain.size();i++)
        {
            boolean isSideChain= true;
            for(int j=0;j<carbonMainChain.size();j++)
            {
                if(i==carbonMainChain.get(j))
                    isSideChain =false;
            }
            if(isSideChain)
                sideCarbons.add(i);
        }
        
        for(int i=0;i<sideCarbons.size();i++)
        {
            for(int j=0;j<carbonMainChain.size();j++)
            {
                if(carbonArrangement[sideCarbons.get(i)][carbonMainChain.get(j)]==1)
                {
                    int current =i;
                    int num=0;
                    for(int k=0;k<sideCarbons.size();k++)
                    {
                        if(carbonArrangement[sideCarbons.get(current)][sideCarbons.get(k)]==1)
                        {
                            current = k;
                            num++;
                        }
                    }
                    
                    tempPos1.add((j+1));
                    tempBranch1.add(num+1);
                    
                    tempPos2.add((carbonMainChain.size()-j));
                    tempBranch2.add(num+1);
                }
            }
        }
        
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
        {
            startLeft=false;
            
        }
        else
        {
            startLeft=true;
            
        }
        
        if(startLeft)
        {
            for(int i=0;i<tempPos1.size();i++)
                sideChainPosition.add(tempPos1.get(i));
            for(int i=0;i<tempBranch1.size();i++)
                sideChainLength.add(tempBranch1.get(i));
        }
        else{
            for(int i=0;i<tempPos2.size();i++)
                sideChainPosition.add(tempPos2.get(i));
            for(int i=0;i<tempBranch2.size();i++)
                sideChainLength.add(tempBranch2.get(i));
        }
        
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i=0;i<sideChainPosition.size()-1;i++)
            {
                if(sideChainPosition.get(i)>sideChainPosition.get(i+1))
                {
                    if(sideChainLength.get(i)>=sideChainLength.get(i+1))
                    {
                        int temp = sideChainPosition.get(i);
                        sideChainPosition.set(i,sideChainPosition.get(i+1));
                        sideChainPosition.set(i+1,temp);
                        int tempLength = sideChainLength.get(i);
                        sideChainLength.set(i,sideChainLength.get(i+1));
                        sideChainLength.set(i+1,tempLength);
                        sorted = false;
                        continue;
                    }
                }
                if(sideChainPosition.get(i)==sideChainPosition.get(i+1))
                {
                    if(sideChainLength.get(i)>sideChainLength.get(i+1))
                    {
                        int temp = sideChainPosition.get(i);
                        sideChainPosition.set(i,sideChainPosition.get(i+1));
                        sideChainPosition.set(i+1,temp);
                        int tempLength = sideChainLength.get(i);
                        sideChainLength.set(i,sideChainLength.get(i+1));
                        sideChainLength.set(i+1,tempLength);
                        sorted = false;
                        continue;
                    }
                }
            }
        }
    }
    
    //Function used find carbon main chain using the idea of prim's algorithm which has lower time complexity compare to the recursive algorithm that I designed for compound class
    public ArrayList<Integer> findCurrentMainChain(int node1In,int node2In)
    {
        //Create two table, one for the distance, one for the previous node
        int[] distance = new int[carbonChain.size()];
        distance[node1In]=0;
        int[] previousNode = new int[carbonChain.size()];
        previousNode[node1In] = node1In;
        
        ArrayList<Integer> retValue = new ArrayList<Integer>();
        
        boolean[] visited = new boolean[carbonChain.size()];
        for(int i=0;i<visited.length;i++)
            visited[i]=false;
        
        
        ArrayList<Integer> queue = new ArrayList<Integer>();
        
        queue.add(node1In);
        
        visited[node1In]=true;
        
        //Fill in two tables using BFS
        while(queue.size()!=0)
        {
            int front = queue.get(0);
            queue.remove(0);
            
            for(int i=0;i<carbonChain.size();i++)
            {
                if(visited[i])
                    continue;
                
                if(carbonArrangement[front][i]==1)
                {
                    distance[i] = distance[front]+1;
                    queue.add(i);
                    previousNode[i]=front;
                    visited[i]=true;
                }
            }
        }
        
        //Adding the distance from previous node until go back to the first node
        int track = node2In;
        retValue.add(track);
        while(track != node1In)
        {
            track = previousNode[track];
            retValue.add(track);
        }
            
        return retValue;
    }
    
    //Use the information about the length of carbon main chain and position and length of carbon side chain to generate a hash value
    //This hash value is used for deleting isomorphic graph
    //If two graph has the same length of main chain, the same side chain position and length then they are identical, so shoud not be counted twice when finding isomers
    public String getHashValue()
    {
        String retValue ="";
        
        retValue += carbonMainChain.size();
        for(int i=0;i<sideChainLength.size();i++)
        {
            
            retValue += sideChainLength.get(i);
            retValue += sideChainPosition.get(i);
        }
        return retValue;
    }
}
