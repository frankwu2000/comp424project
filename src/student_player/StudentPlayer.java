package student_player;

import java.util.ArrayList;

import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import bohnenspiel.BohnenspielPlayer;
import bohnenspiel.BohnenspielMove.MoveType;
import student_player.mytools.MinMax;
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

// Monte Carlo search Tree algorithm
        long currentTime = System.currentTimeMillis();
// Constructor of algorithm class: (board_state, currentTime , playerID, time limit no more than 400 ms)
        MonteCarlo MC = new MonteCarlo(board_state,currentTime, player_id,400);
        BohnenspielMove move = MC.getBestMove();
        MC.DrawTreeRoot();
        
// MiniMax for experiment, not the primary algorithm
    // 	MinMax Mm = new MinMax(board_state,1,currentTime,player_id);
    //  BohnenspielMove move = Mm.MinMax();
        return move;
    }
}