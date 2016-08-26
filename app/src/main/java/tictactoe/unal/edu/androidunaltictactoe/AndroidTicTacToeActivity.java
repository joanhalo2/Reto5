package tictactoe.unal.edu.androidunaltictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];

    // Various text displayed
    private TextView mInfoTextView;

    private TextView mHumanTextView;
    private TextView mTieTextView;
    private TextView mAndroidTextView;

    private TextView mScoreHumanTextView;
    private TextView mScoreTieTextView;
    private TextView mScoreAndroidTextView;

    private int humanScore, tieScore, androidScore;
    private String menuNuevoJuego, menuResetScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mInfoTextView = (TextView) findViewById(R.id.information);

        mHumanTextView = (TextView) findViewById(R.id.controlHuman);
        mTieTextView = (TextView) findViewById(R.id.controlTie);
        mAndroidTextView = (TextView) findViewById(R.id.controlComputer);


        mScoreHumanTextView = (TextView) findViewById(R.id.scoreHuman);
        mScoreTieTextView = (TextView) findViewById(R.id.scoreTie);
        mScoreAndroidTextView = (TextView) findViewById(R.id.scoreComputer);

        mGame = new TicTacToeGame();

        menuNuevoJuego = getResources().getString(R.string.menu_new_game);
        menuResetScores = getResources().getString(R.string.menu_reset_scores);

        mHumanTextView.setText(R.string.human_score);
        mTieTextView.setText(R.string.tie_score);
        mAndroidTextView.setText(R.string.android_score);
        resetScores();
        startNewGame();
    }

    private void startNewGame() {
        mGame.clearBoard();

        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        // Human goes first
        mInfoTextView.setText(R.string.first_human);

        //reset the initial scores
        //resetScores();

        //set the string values to the scores
        updateScores();
    }

    private void resetScores(){
        //Initial Scores
        humanScore = 0;
        tieScore = 0 ;
        androidScore = 0;
    }

    private void updateScores(){
        mScoreHumanTextView.setText(String.valueOf(humanScore));
        mScoreTieTextView.setText(String.valueOf(tieScore));
        mScoreAndroidTextView.setText(String.valueOf(androidScore));
    }

    private void disableButtons(){
        // Disable all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setEnabled(false);
        }
    }





    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }

                if (winner == 0){
                    mInfoTextView.setText(R.string.turn_human);
                }else{

                    if (winner == 1){
                        mInfoTextView.setText(R.string.result_tie);
                        tieScore++;
                    }
                    else if (winner == 2){
                        mInfoTextView.setText(R.string.result_human_wins);
                        humanScore++;
                    }
                    else if (winner == 3){
                        mInfoTextView.setText(R.string.result_computer_wins);
                        androidScore++;
                    }

                    disableButtons();
                    updateScores();

                }

            }
        }




    }

    private void setMove(char player, int location) {

        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(R.string.menu_new_game);
        menu.add(R.string.menu_reset_scores);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals(menuNuevoJuego)){
            startNewGame();
            return true;
        }

        if(item.getTitle().equals(menuResetScores)){
            resetScores();
            updateScores();
            return true;
        }

        return false;

    }
}
