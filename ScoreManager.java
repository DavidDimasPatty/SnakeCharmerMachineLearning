import java.util.*;
public class ScoreManager {
    private int size;
    private String snake;

    //Constants 
    private static final int[] dx = {0,1,0,-1};
    private static final int[] dy = {-1,0,1,0};
    private static final char empty = '.';

    //State Control
    private int[][] matches;
    private char[][] grid;

    //Output
    private String solution;


    //SCORE
    private int score = 0;

    public ScoreManager(String ret, String snake) {
        this.solution = ret;
        this.snake = snake;
        this.size = (int)Math.sqrt(snake.length());
    }

    public int calculate() {
        init();
        int x = Integer.parseInt(Character.toString(this.solution.charAt(0)));
        int y = Integer.parseInt(Character.toString(this.solution.charAt(1)));
        this.solution = this.solution.substring(2, this.solution.length());
        this.grid[y][x] = this.snake.charAt(0);
        int i;
        for (i = 0; i < this.solution.length(); i++) {
            char c = this.solution.charAt(i);
            if (c == 'L') {
                x--;
            } else if (c == 'R') {
                x++;
            } else if (c == 'U') {
                y--;
            } else if (c == 'D') {
                y++;
            } else {
                break;
            }
            if (!inGrid(y, x)) return score;
            if (this.grid[y][x] != '.') return score; 
            this.grid[y][x] = this.snake.charAt(i+1);
            //System.out.println(this.snake.charAt(this.snake.length() - (i+2) ));
        }
        int[] cnt = new int[5];
        for (y = 0; y < this.size; y++) {
            for (x = 0; x < this.size; x++) {
                if (this.grid[y][x] != '.') {
                    int m = 0;
                    for (int dir = 0; dir < dx.length; dir++) {
                        int x2 = x + dy[dir];
                        int y2 = y + dx[dir];
                        if (inGrid(y2, x2) && this.grid[y][x] == this.grid[y2][x2])
                        m++;
                    }
                    this.matches[y][x] = m;
                    cnt[m] = cnt[m] + 1;
                    score += (int)Math.pow((this.grid[y][x] - 48), (m + 1));
                }
             
            }
        }
        return score;
    }

    

    public boolean inGrid(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public void init() {
        grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], empty);
        }
        matches = new int[size][size];
    }
}