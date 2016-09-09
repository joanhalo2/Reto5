package tictactoe.unal.edu.androidunaltictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    private int selected;//-1,0,1,2
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        selected = -1;
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

    private void resetScores() {
        //Initial Scores
        humanScore = 0;
        tieScore = 0;
        androidScore = 0;
    }

    private void updateScores() {
        mScoreHumanTextView.setText(String.valueOf(humanScore));
        mScoreTieTextView.setText(String.valueOf(tieScore));
        mScoreAndroidTextView.setText(String.valueOf(androidScore));
    }

    private void disableButtons() {
        // Disable all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setEnabled(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AndroidTicTacToe Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://tictactoe.unal.edu.androidunaltictactoe/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AndroidTicTacToe Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://tictactoe.unal.edu.androidunaltictactoe/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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

                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_human);
                } else {

                    if (winner == 1) {
                        mInfoTextView.setText(R.string.result_tie);
                        tieScore++;
                    } else if (winner == 2) {
                        mInfoTextView.setText(R.string.result_human_wins);
                        humanScore++;
                    } else if (winner == 3) {
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

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*
        if(item.getTitle().equals(menuNuevoJuego)){
            startNewGame();
            return true;
        }

        if(item.getTitle().equals(menuResetScores)){
            resetScores();
            updateScores();
            return true;
        }
        */

        switch (item.getItemId()) {

            case R.id.new_game:

                startNewGame();

                return true;

            case R.id.ai_difficulty:

                showDialog(DIALOG_DIFFICULTY_ID);

                return true;
            case R.id.reset:
                resetScores();
                updateScores();
                return true;

            case R.id.quit:

                showDialog(DIALOG_QUIT_ID);

                return true;

        }

        return false;

    }


    @Override

    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        switch (id) {

            case DIALOG_DIFFICULTY_ID:

                builder.setTitle(R.string.difficulty_choose);

                final CharSequence[] levels = {

                        getResources().getString(R.string.difficulty_easy),

                        getResources().getString(R.string.difficulty_harder),

                        getResources().getString(R.string.difficulty_expert)};


                        // TODO: Set selected, an integer (0 to n-1), for the Difficulty dialog.
                        // selected is the radio button that should be selected.
                        if(selected==-1){
                            selected = 2;//Default difficulty is Expert
                        }

                        builder.setSingleChoiceItems(levels, selected,

                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int item) {

                                        dialog.dismiss(); // Close dialog

                                        // TODO: Set the diff level of mGame based on which item was selected.
                                        selected = item;
                                        switch (item){
                                            case 0:
                                                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                                                break;
                                            case 1:
                                                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
                                                break;
                                            case 2:
                                                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
                                                break;
                                        }

                                        // Display the selected difficulty level
                                        Toast.makeText(getApplicationContext(), levels[item],Toast.LENGTH_SHORT).show();

                                    }

                                });

                        dialog = builder.create();
                break;
            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                AndroidTicTacToeActivity.this.finish();

                            }

                        })

                        .setNegativeButton(R.string.no, null);

                dialog = builder.create();
                break;
        }

        return dialog;
    }
}
