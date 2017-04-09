package student_player.mytools;

import java.util.ArrayList;

import bohnenspiel.*;

/**
 * @author Frank Wu
 * Node class for search tree
 * qsa - Value of taking an action a from states s
 * nsa - Number of times we have taken action a from state s
 * ns - Number of times we have visited state s in simulations
 */
public class Node {
	BohnenspielBoardState boardState;
	ArrayList<Node> children;
	Node parent;
	
	public int qsa;
	public int ns;
	
	public Node(BohnenspielBoardState boardState , int qsa ,int ns){
		this.boardState = boardState;
		this.children = new ArrayList<Node>();
		this.qsa = qsa;
		this.ns = ns;
	}
	
	public Node(BohnenspielBoardState boardState ){
		this.boardState = boardState;
		this.children = new ArrayList<Node>();
	}
	
	public Node getChild(int index){
		if(children.size()>index && children.get(index) != null){
			return children.get(index);
		}else{
			System.out.println("child is null!");
			return null;
		}
	}
	
	public void setChild(ArrayList<Node> children){
		this.children = children;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public boolean IsLeaf(){
		if(children.size() == 0){
			return true;
		}
		return false;
	}
	
	
}
