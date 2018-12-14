import java.util.*;
 
 
public class TwoThreeFourTree
{
 
   protected static class Node 
   {
 
     protected ArrayList<Integer> data;
     boolean isLeaf;
 
     /** Reference to the first, second, third, and fourth child */
     protected Node first;
     protected Node second;
     protected Node third;
     protected Node fourth;
 
     // Constructors
     /** Construct a node with given data and no children.
         @param data The data to store in this node
      */
     public Node(ArrayList<Integer> data) 
     {
       this.data = data;
       first = null;
       second = null;
       third = null;
       fourth = null;
     }
 
     // Methods
     /** Return a string representation of the node.
         @return A string representation of the data fields
      */
     public String toString() {
       return data.toString();
     }
   }
 
   //=============================2-3-4=================================//
 
   private Node root;
 
   /**
    * Constructs a 2-3-4 tree.
    */
   public TwoThreeFourTree()
   {
      root=null;
   }
 
   public String search(int n)
   {
      search(root, n);
      return null;
 
   }
 
   public String search(Node node, int n)
   {
      int i=1;
      while(i<=node.data.size() && n>node.data.get(i))
      {
         i=i+1;
      }
      if (i<=node.data.size() && n==node.data.get(i))
      {
         String s = new Integer(node.data.get(i)).toString();
         return s;
      }
      else
         if(node.isLeaf==true)
            return null;
         else
         {
            if(i==1)
               return search(root.first, n);
            else
               if(i==2)
                  return search (root.second, n);
               else
                  if(i==3)
                     return search (root.third, n);
                  else
                     if (i==4)
                        return search (root.fourth, n);
         }
 
      return null;
   }
 
   public void insert(int i)
   {
 
   }
 
}
