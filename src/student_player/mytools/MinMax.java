package student_player.mytools;

import java.util.ArrayList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;

/**
 * @author Frank Wu - 260580792
 * This class is for implementation of Minimax algorithm.
 * Minimax algorithm is just for experiment not the algorithm for my project.
 *
 */
public class MinMax {
	int player_id;
	Node root;
	long time;
	BohnenspielMove BestMove;
	int maxDepth;
	
	public MinMax(BohnenspielBoardState board_state ,int maxDepth,long time, int player_id){
		this.player_id = player_id;
		this.maxDepth = maxDepth;
		this.root = new Node(board_state,null,0);
		this.time = time;
	}
	
	//Too much cost - not using it with maxDepth more than 2
	public void BuildTree(Node node){
		if(node.boardState.getTurnNumber()-root.boardState.getTurnNumber()>maxDepth){
			return;
		}else{
			AddChildren(node);
			for(Node child : node.children){
				BuildTree(child);
			}
		}
		
	}
	
	public BohnenspielMove MinimaxDecision(){
		BuildTree(root);
		double BestValue = -1;
		int BestNode = -1;
		for(int i=0;i<root.children.size();i++){
			double value = MinimaxValue(root.getChild(i));
			if(value>BestValue){
				BestValue = value;
				BestNode = i;
			}
		}
		return root.getChild(BestNode).getMove();
	//	return root.getChild(0).getMove();
		
	}
	
	private double MinimaxValue(Node node){
		if(node.IsLeaf()){
			return node.value;
		}
		for(Node child : node.children){
			child.value = MinimaxValue(child);
		}
		//max player
		if(node.boardState.getTurnPlayer() == player_id){
			int maxChild = GetMaxValueChild(node);
			return node.getChild(maxChild).value;
		}
		//min player
		else{
			int minChild = GetMinValueChild(node);
			return node.getChild(minChild).value;
		}
	}
	
	public int GetMaxValueChild(Node node){
		double maxValue = -1;
		int maxChild = -1;
		for(int i = 0 ; i < node.children.size() ;i++){
			if(node.getChild(i).value>maxValue){
				maxChild = i;
				maxValue = node.getChild(i).value;
			}
		}
		return maxChild;
	}
	
	public int GetMinValueChild(Node node){
		double minValue = 10000000;
		int minChild = -1;
		for(int i = 0 ; i < node.children.size() ;i++){
			if(node.getChild(i).value<minValue){
				minChild = i;
				minValue = node.getChild(i).value;
			}
		}
		return minChild;
	}
	
	
	public void AddChildren(Node currentNode){
		ArrayList<BohnenspielMove> moves = currentNode.boardState.getLegalMoves();
		for(int i = 0 ; i < moves.size() ; i++){
			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) currentNode.boardState.clone();
			cloned_board_state.move(moves.get(i));
			Node newChild;
			if(cloned_board_state.getTurnNumber()-root.boardState.getTurnNumber() == maxDepth){
				newChild = new Node(cloned_board_state,moves.get(i),cloned_board_state.getScore(player_id));
			}else{
				newChild = new Node(cloned_board_state,moves.get(i),0);
			}
			
			newChild.parent = currentNode;
			currentNode.children.add(newChild);
		}
		
	}
	
	
//	//second version of MinMax
//	public BohnenspielMove MinMax2(){
//		double bestScore = -9999;
//		BohnenspielMove bestMove =  root.boardState.getLegalMoves().get(0);
//		for(BohnenspielMove move1 : root.boardState.getLegalMoves()){
//			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) root.boardState.clone();
//	        cloned_board_state.move(move1);
//		    double score= minPlay(cloned_board_state);
//		    if(score > bestScore){
//		    	bestMove = move1;
//		    	bestScore = score;
//		    }
//		}
//		return bestMove;
//	}
//	
//	double minPlay(BohnenspielBoardState boardState){
//		
//		if((boardState.getTurnNumber()-root.boardState.getTurnNumber()>maxDepth) || boardState.gameOver()){
//			return boardState.getScore(player_id);
//		}
//			ArrayList<BohnenspielMove> moves = boardState.getLegalMoves();
//			double bestScore = 99999;
//			for(BohnenspielMove move : moves){
//				BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) boardState.clone();
//			    double score = maxPlay(cloned_board_state);
//			    if(score < bestScore){
//			    	BestMove = move;
//			    	bestScore = score;
//			    }
//			}
//			return bestScore;
//	}
//	
//	double maxPlay(BohnenspielBoardState boardState){
//		if((boardState.getTurnNumber()-root.boardState.getTurnNumber()>maxDepth) || boardState.gameOver()){
//			return boardState.getScore(player_id);
//		}
//			ArrayList<BohnenspielMove> moves = boardState.getLegalMoves();
//			double bestScore = -99999;
//			for(BohnenspielMove move : moves){
//				BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) boardState.clone();
//			    double score = minPlay(cloned_board_state);
//			    if(score > bestScore){
//			    	BestMove = move;
//			    	bestScore = score;
//			    }
//			}
//			return bestScore;
//	}
		
	
}
