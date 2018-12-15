package project3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;
import javax.swing.*;
import static project3.Graph.drawRed;

public class StreetMap {
	
	private static Graph g;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Program Start");
		if(args.length < 1){
			System.out.println("error, usage: StreetMap inFile");
			System.exit(1);
		}
		
		try{
			
			Scanner s = new Scanner(new FileInputStream(args[0]));
			
			LinkedList<String> input = new LinkedList<String>();
			
			int numIntersects = 0;
			while(s.hasNextLine()){
				input.add(s.next());
				//The following is faster but assumes all intersections declared before any roads:
				if(numIntersects == 0 && input.peekLast().compareTo("r")==0)
					numIntersects = input.size()/4;
			}
			System.out.println(numIntersects+" intersections found");
			/*for(int i=0; i<input.size(); i+=4)
				if(input.get(i).compareTo("i")==0)
					numIntersects++;*/
			
			g = new Graph(numIntersects);
			for(int i=0; i<numIntersects; i++){
				input.pop();
				g.insert(new Node(input.pop(), Double.parseDouble(input.pop()), Double.parseDouble(input.pop()) ));
			}
			System.out.println("finished building vertex array");
			while(!input.isEmpty()){
				input.pop();
				g.insert(new Edge(input.pop(), input.pop(), input.pop() ));
			}
			System.out.println("finished building edge list");
			System.out.println("Num vertices in graph g: "+g.vertices());
			System.out.println("Num edges in graph g: "+g.edges());
			
			if(args.length == 2){
				if( args[1].compareTo("--show")==0){					
                                        display();
				}
			}else if(args.length > 2 && args.length <7){
                                if( args[1].compareTo("--show")==0 && args[2].compareTo("--directions")==0){
					
                                        //ivalid input corrections to be done
                                        String source = args[3];
                                        String dest = args[4];
                                        
                                        g = g.calculateShortestPathFromSource(g, source,dest);
                           
                                        System.out.printf("Path from %s to %s \n",source,dest);
                                        for(Node n : g.redPath){
                                            
                                            System.out.print(" -> " + n.interId );
                                        }
                                        System.out.print("\n");
                                        drawRed = true;
                                        display();
				}else if(args[1].compareTo("-- directions")==0){
                                    //ivalid input corrections to be done
                                        String source = args[3];
                                        String dest = args[4];
                                        
                                        g = g.calculateShortestPathFromSource(g, source,dest);
                                        
                                        System.out.printf("Path from %s to %s \n",source,dest);
                                        for(Node n : g.redPath){
                                            
                                            System.out.print(" -> " + n.interId );
                                        }
                                        System.out.print("\n");
                                        
                                }
                        }
			
			s.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void display(){
		JFrame frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("Hello World");
		frame.getContentPane().add(label);
		
		
		
		g.drawGraph(frame);
	}
}