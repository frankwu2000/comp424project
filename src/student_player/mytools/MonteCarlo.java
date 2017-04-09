package student_player.mytools;
import java.util.ArrayList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;


/**
 * @author Frank Wu - 260580792
 * This class is for implementation of Monte Carlo search tree algorithm.
 * Monte Carlo search tree algorithm is the one for project.
 * This algorithm is still not good enough.
 * It can beat greedy player in 17/20 games and beat random player 20/20 
 * (by very small chance lose one game)
 *
 */
public class MonteCarlo {
	int player_id;
	Node root;
	long time;
	BohnenspielMove BestMove;
	long TimeLimit;
	
	public MonteCarlo(BohnenspielBoardState board_state ,long time, int player_id,long TimeLimit){
		this.player_id = player_id;
		this.root = new Node(board_state, null, 0, 0 );
		this.time = time;
		this.TimeLimit = TimeLimit;
	}
	
	public BohnenspielMove getBestMove(){
		Run();
		double maxvalue = -1;
		int bestChild = -1;
		for(int i = 0 ; i < root.children.size();i++){
			double tempWinLose = root.getChild(i).win/root.getChild(i).visit;
//			System.out.println("root.getChild(i).win: "+root.getChild(i).win + ", root.getChild(i).visit: "+root.getChild(i).visit);
//			System.out.println("tempWinLose: "+tempWinLose+" at i = "+i+". " + " at move : "+root.getChild(i).getMove().getPit());
			if(tempWinLose >= maxvalue){
				maxvalue = tempWinLose;
				bestChild = i;
			}
		}
		BestMove = root.getChild(bestChild).getMove();
		return BestMove;
	//	return FindBestUCT(root);
		
	//	int bestMove = FindBestUCT(root);
	//	return root.children.get(bestMove).getMove();
	}
	
	
	
	public void Run(){
		//set up running time here ---------------()---
		while(System.currentTimeMillis() < time + TimeLimit){
		//for(int counter = 0 ; counter < maxStep; counter++){
			Node currentNode = root;
			//selection 
			while(!currentNode.IsLeaf() ){
				//get optimal child
				int bestChild = FindBestUCT(currentNode);
				currentNode = currentNode.getChild(bestChild);
			}
			//Random simulation 
			if(currentNode.visit == 0){
				currentNode.win = MyTools.RandomRollOut(currentNode.boardState, player_id);
				currentNode.visit++;
				BackPropogate(currentNode);
			}else{
				AddChildren(currentNode);
				if(currentNode.children.size()<1){
					continue;
				}

				currentNode = currentNode.getChild(0);
				currentNode.win= MyTools.RandomRollOut(currentNode.boardState, player_id);
				currentNode.visit++;
				BackPropogate(currentNode);
//				for(int kk = 0 ; kk < currentNode.children.size();kk++){
//					currentNode.getChild(kk).win= MyTools.RandomRollOut(currentNode.getChild(kk).boardState, player_id);
//					currentNode.getChild(kk).visit++;
//					BackPropogate(currentNode.getChild(kk));
//				}
				/*
				 * for(int kk = 0 ; kk < currentNode.children.size();kk++){
					currentNode.getChild(kk).win= MyTools.RandomRollOut(currentNode.getChild(kk).boardState, player_id);
					currentNode.getChild(kk).visit++;
					BackPropogate(currentNode.getChild(kk));
				}
				*/
			}
		}
	}
	
	
	
	public void AddChildren(Node currentNode){
		ArrayList<BohnenspielMove> moves = currentNode.boardState.getLegalMoves();
		for(int i = 0 ; i < moves.size() ; i++){
			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) currentNode.boardState.clone();
			cloned_board_state.move(moves.get(i));
			Node newChild = new Node(cloned_board_state,moves.get(i),0,0);
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
				if(currentNode.children.get(i).visit == 0){
					return i ;
				}
				double childValue = currentNode.getChild(i).win/currentNode.getChild(i).visit;
				if(childValue>maxQsa){
					maxQsa = childValue;
					bestChild = i;
				}
			}
		}else{
			double minQsa = 100000;
			for(int i = 0 ; i < currentNode.children.size();i++){
				if(currentNode.children.get(i).visit == 0){
					return i ;
				}
				double childValue = currentNode.getChild(i).win/currentNode.getChild(i).visit;
				if(childValue<minQsa){
					minQsa = childValue;
					bestChild = i;
				}
			}
		}
		return bestChild;
	}
	
	public int FindBestUCT(Node currentNode){
		double maxUCB = -1;
		int maxChild = 0;
		for(int i = 0 ; i < currentNode.children.size();i++){
			if(currentNode.children.get(i).visit == 0){
				//if the visit of this action is 0, return it since UCB will be infinite
				return i;
			}else{
				double childUCB = MyTools.UCB(currentNode.getChild(i).win, currentNode.getChild(i).visit, currentNode.visit,Math.sqrt(2));
				if(childUCB > maxUCB){
					maxUCB = childUCB;
					maxChild = i;
				}
			}
		}
		return maxChild;
	}
	
	
	public void BackPropogate(Node node){
		Node current = node.parent;
		while(current!= null){
			current.win += node.win;
			current.visit += node.visit;
			current = current.parent;
		}
		
	}
	
	public void DrawTreeRoot(){
		Node currentNode =root;
		//while(!currentNode.IsLeaf()){
			System.out.println("(win: "+ currentNode.win + ", visit: "+currentNode.visit+")");
			for(int i = 0 ; i<currentNode.children.size();i++){
				System.out.print("(win: "+ currentNode.getChild(i).win + ", visit: "+currentNode.getChild(i).visit+") ");
			}
			System.out.println("");
			System.out.println("BestMove: "+BestMove.getMoveType().name() + "at pit: "+BestMove.getPit());
			System.out.println("");
		//}
	}
	
}
