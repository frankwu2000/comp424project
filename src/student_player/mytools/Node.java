package student_player.mytools;

import java.util.ArrayList;

import bohnenspiel.*;

/**
 * @author Frank Wu - 260580792
 * 
 * Node class for search tree
 * This class is used for Monte Carlo algorithm.
 * win - Value of taking an action a from states s
 * nsa - Number of times we have taken action a from state s
 * visit - Number of times we have visited state s in simulations
 */
public class Node {
	BohnenspielBoardState boardState;
	ArrayList<Node> children;
	Node parent;
	BohnenspielMove move;//the move the parent take to get to this state.
	
	//monte carlo
	public double win;
	public double visit;
	
	//constructor for node in Monte Carlo Search Tree
	public Node(BohnenspielBoardState boardState, BohnenspielMove move , double win ,double visit){
		this.boardState = boardState;
		this.move = move;
		this.children = new ArrayList<Node>();
		this.win = win;
		this.visit = visit;
	}
	
	public Node(BohnenspielBoardState boardState ){
		this.boardState = boardState;
		this.children = new ArrayList<Node>();
	}
	
	public Node getChild(int index){
		if(children.get(index) != null){
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
	
	public BohnenspielMove getMove(){
		return move;
	}

	
}
