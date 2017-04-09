package student_player.mytools;

import java.util.ArrayList;
import java.util.Random;

import bohnenspiel.*;

public class MyTools {

    public static double getSomething(){
        return Math.random();
    }
    
   
    /**
     * UCB1 formula for Monte Carlo tree search 
     * @param qsa - Value of taking an action a from states s
     * @param nsa - Number of times we have taken action a from state s
     * @param ns - Number of times we have visited state s in simulations
     * @return - Upper bound on the value of taking action a in state s
     */
    public static double UCB(double reward, int nsa, int ns){
    	//use c = sqrt(2) for scalling factor
    	double result = reward;
    	result += Math.sqrt(2*Math.log(ns)/nsa);
    	return result;
    }
    
    public static int RandomRollOut(BohnenspielBoardState board_state,int player_id){
    	BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) board_state.clone();
    	
    	while(!cloned_board_state.gameOver()){
    		Random rand = new Random();
    		ArrayList<BohnenspielMove> moves = cloned_board_state.getLegalMoves();
            BohnenspielMove move = moves.get(rand.nextInt(moves.size()));
            cloned_board_state.move(move);
    	}
    	if(player_id == cloned_board_state.getWinner()){
    		return 1;
    	}else{
    		return 0;
    	}
    }
}
