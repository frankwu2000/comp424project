package student_player;

import java.util.ArrayList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import bohnenspiel.BohnenspielPlayer;
import bohnenspiel.BohnenspielMove.MoveType;
import student_player.mytools.MonteCarlo;
import student_player.mytools.MyTools;

/** A Hus player submitted by a student. */
public class StudentPlayer extends BohnenspielPlayer {

    /** You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else. */
    public StudentPlayer() { super("260580792"); }

    /** This is the primary method that you need to implement.
     * The ``board_state`` object contains the current state of the game,
     * which your agent can use to make decisions. See the class
bohnenspiel.RandomPlayer
     * for another example agent. */
    public BohnenspielMove chooseMove(BohnenspielBoardState board_state)
    {
        // Get the contents of the pits so we can use it to make decisions.
        int[][] pits = board_state.getPits();

        // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
        int[] my_pits = pits[player_id];
        int[] op_pits = pits[opponent_id];

        // Get the legal moves for the current board state.
        ArrayList<BohnenspielMove> moves = board_state.getLegalMoves();
        BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) board_state.clone(); 

      //  BohnenspielMove move = moves.get(0);
        
        // search Tree algorithm
        long currentTime = System.currentTimeMillis();
        MonteCarlo MC = new MonteCarlo(board_state,currentTime, player_id);
        BohnenspielMove move = moves.get(MC.getBestMove());
       // MC.DrawTree();
        
/*		test algorith - delete after complete all        
        int bestScore = -1;
        BohnenspielMove bestMove = moves.get(0);
        for(int i = 0 ; i< moves.size();i++){
        	BohnenspielBoardState cloned_board_state = (BohnenspielBoardState) board_state.clone();
	        BohnenspielMove move1 = moves.get(i);
	        cloned_board_state.move(move1);
	        int score = MyTools.RandomRollOut(cloned_board_state, player_id);
	        if (score > bestScore){
		        bestMove=move1;
		        bestScore=score;
	        }
        }
        
        // But since this is a placeholder algorithm, we won't act on that information.
        return bestMove; 
 */
        return move;
    }
}