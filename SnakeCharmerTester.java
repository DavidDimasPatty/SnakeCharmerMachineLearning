import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.topcoder.marathon.*;

public class SnakeCharmerTester extends MarathonVis {
    //Ranges
    private static final int minSize = 7, maxSize = 49;
    private static final int minNumValues = 2, maxNumValues = 8;
    private static final int minValue = 2;

    //Inputs
    private int size;
    private int numValues;
    private String snake;

    //Constants 
    private static final int[] dx = {0,1,0,-1};
    private static final int[] dy = {-1,0,1,0};
    private static final char empty = '.';

    //Painting
    private Color[] colors;

    //State Control
    private int[][] matches;
    private char[][] grid;

    //Output
    private String solution;

    private String Score = "0";
    private int headx;
    private int heady;
    private String seed_ml="0";

    protected void generate() {
        size = randomInt(0, (maxSize - minSize) / 2) * 2 + minSize;
        numValues = randomInt(minNumValues, maxNumValues);

        //Special cases for seeds 1 and 2
        if (seed == 1) {
            size = minSize;
            numValues = 3;
        } else if (seed == 2) {
            size = maxSize;
            numValues = maxNumValues;
        }

        //User defined parameters
        if (parameters.isDefined("N")) {
            int[] range = parameters.getIntRange("N");
            int min = Math.min(maxSize, Math.max(minSize, range[0]));
            int max = Math.min(maxSize, Math.max(minSize, range[1]));
            if (min % 2 == 1 && max % 2 == 1 && max >= min) size = randomInt(0, (max - min) / 2) * 2 + min;
        }
        if (parameters.isDefined("V")) numValues = randomInt(parameters.getIntRange("V"), minNumValues, maxNumValues);

        //generate snake
        StringBuilder sb = new StringBuilder(size * size);
        for (int i = 0; i < size * size; i++) {
            sb.append((char) ('0' + randomInt(minValue, minValue + numValues - 1)));
        }
        //snake = sb.toString();

        if (debug) {
            System.out.println("Grid Size N = " + size);
            System.out.println("   Values V = " + numValues);
            System.out.println("      Snake = " + snake);
        }
    }

    protected boolean isMaximize() {
        return true;
    }

    protected double run() throws Exception {
        init();
        this.solution = callSolution();
        if (this.solution == null) {
            if (!isReadActive()) {
                return getErrorScore();
            }
            return fatalError();
        }
        int x = Integer.parseInt(Character.toString(this.solution.charAt(0)));
        int y = Integer.parseInt(Character.toString(this.solution.charAt(1)));
        this.headx = x;
        this.heady = y;
        this.solution = this.solution.substring(2, this.solution.length());
        this.grid[y][x] = this.snake.charAt(this.solution.length());
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
                return fatalError("Unknown direction " + c + " at move " + (i + 1));
            }
            if (!inGrid(y, x)) return fatalError("You stepped out of grid bounds at move " + (i + 1));
            if (this.grid[y][x] != '.') return fatalError("You've hit yourself at move " + (i + 1)); 
            this.grid[y][x] = this.snake.charAt(this.solution.length() - i );
        }
        if (this.debug) {
            System.out.println("Your moves: " + this.solution);
            for (i = 0; i < this.size; i++) {
                for (int k = 0; k < this.size; k++) {
                    System.out.print(this.grid[i][k]);
                    System.out.println();
                }
            }
        }
        int score = 0;
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
        if (hasVis()) {
            addInfo("Score", Integer.valueOf(score));
            addInfo("Seed", String.valueOf(this.seed_ml));
            addInfo("Time", String.valueOf(getRunTime()) + " ms");
            for (int c = 0; c < this.colors.length; c++) {
                addInfo(this.colors[c], Integer.valueOf(cnt[c]));
                update();
            }
        }
        return score;
    }

    protected void paintContent(Graphics2D g) {
        g.setStroke(new BasicStroke(0.005F, 1, 1));
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if (this.grid[y][x] == '.') {
                    g.setColor(Color.lightGray);
                }
                else {
                    g.setColor(this.colors[this.matches[y][x]]);
                }
                g.fillRect(x, y, 1, 1);
                g.setColor(Color.gray);
                g.drawRect(x, y, 1, 1);
            }
        }
        if (this.solution != null) {
            int x = headx;
            int j = heady;
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(0.05F, 1, 1));
            g.drawRect(x, j, 1, 1);
            GeneralPath gp = new GeneralPath();
            gp.moveTo(x + 0.5D, j + 0.5D);
            for (int i = 0; i < this.solution.length(); i++) {
                char c = this.solution.charAt(i);
                if (c == 'L') {
                    x--;
                } else if (c == 'R') {
                    x++;
                } else if (c == 'U') {
                    j--;
                } else if (c == 'D') {
                    j++;
                }
                gp.lineTo(x + 0.5D, j + 0.5D);
            } 
            g.drawRect(x, j, 1, 1);
            Color c0 = new Color(0, 0, 0, 80);
            g.setColor(c0);
            g.setStroke(new BasicStroke(0.4F, 1, 1));
            g.draw(gp);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(0.03F, 1, 1));
            g.draw(gp);
            Color c1 = new Color(0, 0, 0, 40);
            for (j = 0; j < this.size; j++) {
                for (x = 0; x < this.size; x++) {
                    if (this.grid[j][x] != '.') {
                        Ellipse2D e = new Ellipse2D.Double(x + 0.2D, j + 0.2D, 0.6D, 0.6D);
                        g.setColor(this.colors[this.matches[j][x]]);
                        g.fill(e);
                        g.setColor(c1);
                        g.fill(e);
                        g.setColor(c0);
                        g.draw(e);
                    }
                }
            }
            g.setColor(Color.black);
            adjustFont(g, "SansSerif", 0, "0", new Rectangle2D.Double(0.0D, 0.0D, 0.7D, 0.7D));
            for (j = 0; j < this.size; j++) {
                for (x = 0; x < this.size; x++) {
                    if (this.grid[j][x] != '.') {
                        Rectangle2D rc = new Rectangle2D.Double(x, j, 1.0D, 1.0D);
                        drawString(g, String.valueOf(this.grid[j][x]), rc);
                    }
                }
            }
        }
    }

    private boolean inGrid(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private void init() {
        grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], empty);
        }
        matches = new int[size][size];

        if (hasVis()) {
            colors = new Color[] {Color.white,Color.green,Color.cyan,Color.orange,Color.red};

            setInfoMaxDimension(15, 15);
            setContentRect(0, 0, size, size);
            setDefaultSize(30);
            addInfo("Seed", Long.parseLong(this.seed_ml));
            addInfoBreak();
            addInfo("Size N", size);
            addInfo("Values V", numValues);
            addInfoBreak();
            addInfo("Score",this.Score);
            addInfoBreak();
            addInfo("Time", "-");
            addInfoBreak();
            addInfo("Count");
            for (int c = 0; c < colors.length; c++) {
                addInfo(colors[c], 0);
            }
            update();
        }
    }

    private String callSolution() throws Exception {
        Scanner sc = new Scanner(System.in); // inilisasi input
        sc = new Scanner(new File("output.txt")); // scan file txt untuk input
        String ret = sc.next();
        this.Score = sc.next();
        this.seed_ml=sc.next();
        this.snake=sc.next();
        System.out.println(this.Score);
        sc.close();
        return new String(ret);
    }

    public static void main(String[] args) throws IOException {
        ProifML proifML = new ProifML();
        proifML.start();
        new MarathonController().run(args);
    }
}