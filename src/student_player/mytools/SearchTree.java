package student_player.mytools;

import java.util.ArrayList;

public class SearchTree {
	ArrayList<Node> nodes = new ArrayList<Node>(); 
	Node root;
	
	public SearchTree(Node root){
		this.root = root;
	}
	
	public void AddNode(Node n){
		nodes.add(n);
	}
	
	public Node GetNode(int index){
		return nodes.get(index);
	}
}
