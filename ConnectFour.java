import java.util.*;
//David Guzman Valente
// TA: Niyati Trivedi
// Due Date
// P0: Project Name
// ConnectFour
// This class implements the AbstractStrategyGame and uses it to recreate the board game
// connect four as a video game with a small twist allowing the user to remove tokens from
// the bottom row 
public class ConnectFour implements AbstractStrategyGame {
    private char[][] board;
    private boolean player1; //if player 1 is true then it is player one's turn, if false then player two

    // Behavior: creates a new board for the connect four game
    // Exceptions: N/A
    // Return: N/A
    // Parameters: N/A
    public ConnectFour() {
        this.board = new char[][] {{'-', '-', '-','-','-','-','-'},
                                   {'-', '-', '-','-','-','-','-'},
                                   {'-', '-', '-','-','-','-','-'},
                                   {'-', '-', '-','-','-','-','-'},
                                   {'-', '-', '-','-','-','-','-'},
                                   {'-', '-', '-','-','-','-','-'},};
        player1 = true;
    }

    // Behavior: returns a string of the game board and what player needs to make a move
    // Exceptions: N/A
    // Return: returns a string that shows the game board and say's who's turn it is to remove 
    // a token or add a new token
    // Parameters: N/A
    public String toString() {
        String result = "";
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                result += board[i][j] + " ";
            }

            result += "\n";
        }
        String player = "";
        if(player1) {
            player = "Player 1's";
        } else {
            player = "Player 2's";
        }
        return result;
    }

    // Behavior: returns a list of instructions for how to play the game
    // Exceptions: N/A
    // Return: returns a list of instructions for how to play the game connect four
    // Parameters: N/A
    public String instructions() {
        return "Player 1 is X and begins the game. Player's alternate every turn, and \n" 
                + "will choose a column between 1 representing the farthest left column,\n"
                + "and 7 representing the farthest right column.  If the player who\n"
                + "selected the column has a token in the bottom row they will choose to\n"
                + "either remove that token from the bottom row or add a new token on top of\n"
                + "the column selected. Once a player gets four tokens either in a row or\n"
                + "a row or in a column they win.";

    }

    // Behavior: returns whether the game is over or not
    // Exceptions: N/A
    // Return: returns whether the game is over or not
    // Parameters: N/A
    public boolean isGameOver() {
        return getWinner() >= 0;
    }

    // Behavior: looks at the game board and determines if there is a winner.
    // if there is a winner it will return the number corresponding to the player that won.
    // Exceptions: N/A
    // Return: returns an integer corresponding to the player that won. If no player has won
    // it will return a -1.
    // Parameters: N/A
    public int getWinner() {
        for(int i = 0; i < board.length; i++) {
            String row = "";
            for(int j = 0; j < board[i].length; j++) {
                row += board[i][j];
                if(row.contains("XXXX")) {
                    return 1;
                } else if(row.contains("OOOO")) {
                    return 2;
                }
            }
        }

        for(int i = 0; i < board[0].length; i++) {
            String col = "";
            for(int j = 0; j < board.length; j++) {
                col += board[j][i];
                if(col.contains("XXXX")) {
                    return 1;
                } else if (col.contains("OOOO")) {
                    return 2;
                }
            }
        }

        return -1;
    }

    // Behavior: if the game isn't over it will return a number 
    // representing the player that will be able to make the next move
    // Exceptions: N/A
    // Return: returns the player that will make the next move. If the game is over
    // it will return -1
    // Parameters:
    public int getNextPlayer() {
        if(isGameOver()) {
            return -1;
        }

        return player1 ? 1 : 2;
    }

    // Behavior: It will prompt the user what column they would like to place their token
    // if it's player 1 the token will appear as an X and if not  the token will be an O.
    // If the column chosen is within the boundaries it will check and see if the bottom row
    // has a token. If it does and the token is associated to that player it will ask whether they
    // would like to remove that token or add another token to the column and adjust the game
    // board accordingly.
    // Exceptions: throws an IllegalArgumentException if the player inputs a column number outside
    // the range
    // Return: N/A
    // Parameters: accepts user input that is taken from the console
    public void makeMove(Scanner input) {
        System.out.print("Player " + this.getNextPlayer() + " What Column? ");
        //the -1 is so that it is placed in the correct column since their range is (1,7)
        int col = input.nextInt() - 1; 
        if(col < 0 || col > 6) {
            throw new IllegalArgumentException();
        }

        char token = 'O';
        if(this.getNextPlayer() == 1) {
            token = 'X';
        }
        if(board[5][col] == '-') {
            board[5][col] = token;
        } else if(board[5][col] == token) {
            System.out.print("Would you like to (r)emove or (a)dd a token to this column? ");
            String action = input.next();
            if(action.equals("r")) {
                removeToken(col);
            } else {
                int index = addToken(col);
                board[index][col] = token;
            }
        } else {
            int index = addToken(col);
            board[index][col] = token;
        }


        player1 = !player1;
    }

    // Behavior:This method will go through a column in the game board and remove the token
    // that is at the lowest position, shifting all the tokens above it down
    // Exceptions: N/A
    // Return: N/A
    // Parameters: Accepts a number representing the column in the game board that will be
    // looked at.
    private void removeToken(int col) {
        for(int i = board.length - 1; i > 0; i--) {
            this.board[i][col] = this.board[i - 1][col];
        }
        this.board[0][col] = '-';
    }

    // Behavior: This method will go through a column in the game board and determine what
    // row the token should be placed
    // Exceptions: N/A
    // Return: returns a number representing the row index of where the token will need to go
    // Parameters: Takes in a number representing the column in the game board that will be
    // looked at.
    private int addToken(int col) {
        for(int i = board.length - 1; i >= 0; i--) {
            if(board[i][col] == '-') {
                return i;
            }
        }
        return -1;
    }
}

