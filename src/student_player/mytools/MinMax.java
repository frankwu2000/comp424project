package student_player.mytools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	int opponent_id;
	BohnenspielBoardState root ;
	long time;
	BohnenspielMove BestMove;
	int maxDepth;
	
	public MinMax(BohnenspielBoardState board_state ,int maxDepth,long time, int player_id){
		this.player_id = player_id;
		BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) root.clone();
	    cloned_board_state.move(cloned_board_state.getLegalMoves().get(0));
	    this.opponent_id = cloned_board_state.getTurnPlayer();
	   
	    this.maxDepth = maxDepth;
		this.root = board_state;
		this.time = time;
	}
	
	
	
	public BohnenspielMove MinimaxDecision(){
		return root.getLegalMoves().get(0);
	}
	
	private int MinimaxValue(BohnenspielBoardState state){
		if(state.getTurnNumber()-root.getTurnNumber()>=maxDepth||state.gameOver()){
			return state.getScore(player_id)-state.getScore(opponent_id);
		}
//		ArrayList<BohnenspielBoardState> successors = GetSuccessor(state);
//		Map<BohnenspielBoardState,Integer> values = new HashMap();
//		for(BohnenspielBoardState successor : successors){
//			int value = MinimaxValue(successor);
//			values.put(successor, value);
//		}
		//max player
		if(state.getTurnPlayer() == player_id){
			return GetMaxValueChild(state);
		}
		//min player
		else{
			return GetMinValueChild(state);
		}
	}
	
	public int GetMaxValueChild(BohnenspielBoardState state){
		int bestscore=-9999;
		ArrayList<BohnenspielMove> moves = state.getLegalMoves();
		for(int i = 0 ; i <moves.size() ;i++){
			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) state.clone();
		    BohnenspielMove move1 = moves.get(i);
		    cloned_board_state.move(move1);
		    //score = our_player - opponent player
		    int score=cloned_board_state.getScore(player_id) - cloned_board_state.getScore(opponent_id);
		    if (score>bestscore)
		    {
		        bestscore=score;
	        }
		}
		return bestscore;
	}
	
	public int GetMinValueChild(BohnenspielBoardState state){
		int bestscore = 10000000;
		ArrayList<BohnenspielMove> moves = state.getLegalMoves();
		for(int i = 0 ; i <moves.size() ;i++){
			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) state.clone();
		    BohnenspielMove move1 = moves.get(i);
		    cloned_board_state.move(move1);
	        //score = our_player - opponent player
	        int score=cloned_board_state.getScore(player_id) - cloned_board_state.getScore(opponent_id);
	        if (score<bestscore)
	        {
		        bestscore=score;
	        }
		}
		return bestscore;
	}
	
	public ArrayList<BohnenspielBoardState> GetSuccessor(BohnenspielBoardState state){
		ArrayList<BohnenspielBoardState> successors = new ArrayList<BohnenspielBoardState>();
		ArrayList<BohnenspielMove> moves = state.getLegalMoves();
		for(int i = 0 ; i <moves.size() ;i++){
			BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) state.clone();
		    BohnenspielMove move1 = moves.get(i);
		    cloned_board_state.move(move1);
		    successors.add(cloned_board_state);
		}
		return successors;
	}
	
	

		
	
}
