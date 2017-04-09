package student_player.mytools;
import java.util.Timer;
import java.util.ArrayList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;


public class MonteCarlo {
	int player_id;
	//SearchTree tree;
	Node root;
	long time;
	
	public MonteCarlo(BohnenspielBoardState board_state ,long time, int player_id){
		this.player_id = player_id;
		this.root = new Node(board_state, 0, 0);
	//	this.tree = new SearchTree(root);
		this.time = time;
	}
	
	public int getBestMove(){
		Run();		
		return FindBestUCT(root);
	}
	
	public void Run(){
		while(System.currentTimeMillis() < time+200){
		//for(int counter = 0 ; counter < maxStep; counter++){
			Node currentNode = root;
			while(!currentNode.IsLeaf()){
				//get optimal child
				int bestChild = Selection(currentNode);
				currentNode = currentNode.getChild(bestChild);
			}
			if(currentNode.ns == 0){
				currentNode.qsa = MyTools.RandomRollOut(currentNode.boardState, player_id);
				currentNode.ns++;
				BackPropogate(currentNode);
			}else{
				AddChildren(currentNode);
				if(currentNode.children.size()<1){
					continue;
				}
//				Random random = new Random();
//				currentNode = currentNode.getChild(random.nextInt(currentNode.children.size()));
				currentNode = currentNode.getChild(0);
				currentNode.qsa = MyTools.RandomRollOut(currentNode.boardState, player_id);
				currentNode.ns++;
				BackPropogate(currentNode);
				
				/*
				 * for(int kk = 0 ; kk < currentNode.children.size();kk++){
					currentNode.getChild(kk).qsa= MyTools.RandomRollOut(currentNode.boardState, player_id);
					currentNode.getChild(kk).ns++;
					BackPropogate(currentNode.getChild(kk));
				}*/
			}
		}
	}
	
	
	
	public void AddChildren(Node currentNode){
		ArrayList<BohnenspielMove> moves = currentNode.boardState.getLegalMoves();
		for(int i = 0 ; i < moves.size() ; i++){
			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) currentNode.boardState.clone();
			cloned_board_state.move(moves.get(i));
			Node newChild = new Node(cloned_board_state,0,0);
			newChild.parent = currentNode;
			currentNode.children.add(newChild);
		}
		
	}

	
	
	//select using minmax based on win games/total games
	public int Selection(Node currentNode){
		int bestChild = 0;
		if(currentNode.boardState.getTurnPlayer()==player_id){
			double maxQsa = -1;
			for(int i = 0 ; i < currentNode.children.size();i++){
				if(currentNode.children.get(i).ns == 0){
					return i ;
				}
				double childValue = currentNode.getChild(i).qsa/currentNode.getChild(i).ns;
				if(childValue>maxQsa){
					maxQsa = childValue;
					bestChild = i;
				}
			}
		}else{
			double minQsa = Double.MAX_VALUE;
			for(int i = 0 ; i < currentNode.children.size();i++){
				if(currentNode.children.get(i).ns == 0){
					continue ;
				}
				double childValue = currentNode.getChild(i).qsa/currentNode.getChild(i).ns;
				if(childValue<minQsa){
					minQsa = childValue;
					bestChild = i;
				}
			}
		}
		return bestChild;
	}
	
	public int FindBestUCT(Node currentNode){
		double maxQsa = -1 ;
		int maxChild = 0;
		for(int i = 0 ; i < currentNode.children.size();i++){
			if(currentNode.children.get(i).ns == 0){
				return i;
			}
			//double childQsa = MyTools.UCB(currentNode.getChild(i).qsa/currentNode.getChild(i).ns, currentNode.getChild(i).ns, currentNode.ns);
			double childQsa = MyTools.UCB(currentNode.getChild(i).qsa, currentNode.getChild(i).ns, currentNode.ns);
			if(childQsa > maxQsa){
				maxQsa = childQsa;
				maxChild = i;
			}
		}
		return maxChild;
	}
	
	
	public void BackPropogate(Node node){
		if(node == root){
			return;
		}
		if(node.getParent() == null){
			System.out.println("Error! No parent. ");
			return;
		}
		Node current = node;
		while(current!= root){
			current.parent.qsa += node.qsa;
			current.parent.ns += node.ns;
			current = current.parent;
		}
	}
	
	public void DrawTree(){
		Node currentNode =root;
		//while(!currentNode.IsLeaf()){
			System.out.println("(qsa: "+ currentNode.qsa + ", ns: "+currentNode.ns+")");
			for(int i = 0 ; i<currentNode.children.size();i++){
				System.out.print("(qsa: "+ currentNode.getChild(i).qsa + ", ns: "+currentNode.getChild(i).ns+") ");
			}
		//}
	}
	
}
