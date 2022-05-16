import java.awt.*;
import java.awt.geom.*;
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
        snake = sb.toString();

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
        solution = callSolution();
        if (solution == null) {
            if (!isReadActive()) return getErrorScore();
            return fatalError();
        }

        int x = size / 2;
        int y = size / 2;
        grid[y][x] = snake.charAt(solution.length());
        for (int i = 0; i < solution.length(); i++) {
            char c = solution.charAt(i);
            if (c == 'L') x--;
            else if (c == 'R') x++;
            else if (c == 'U') y--;
            else if (c == 'D') y++;
            else return fatalError("Unknown direction " + c + " at move " + (i + 1));
            if (!inGrid(y, x)) return fatalError("You stepped out of grid bounds at move " + (i + 1));
            if (grid[y][x] != empty) return fatalError("You've hit yourself at move " + (i + 1));
            grid[y][x] = snake.charAt(solution.length() - (i + 1));
        }

        if (debug) {
            System.out.println("Your moves: " + solution);
            for (int i = 0; i < size; i++) {
                for (int k = 0; k < size; k++) {
                    System.out.print(grid[i][k]);
                }
                System.out.println();
            }
        }

        //compute the raw score
        int score = 0;
        int[] cnt = new int[5];
        for (y = 0; y < size; y++) {
            for (x = 0; x < size; x++) {
                if (grid[y][x] == empty) continue;
                int m = 0;
                for (int dir = 0; dir < dx.length; dir++) {
                    int x2 = x + dy[dir];
                    int y2 = y + dx[dir];
                    if (inGrid(y2, x2) && grid[y][x] == grid[y2][x2]) m++;
                }
                matches[y][x] = m;
                cnt[m]++;
                score += (int) Math.pow(grid[y][x] - '0', m + 1);
            }
        }

        if (hasVis()) {
            addInfo("Score", score);
            addInfo("Time", getRunTime() + " ms");
            for (int c = 0; c < colors.length; c++) {
                addInfo(colors[c], cnt[c]);
            }
            update();
        }
        return score;
    }

    protected void paintContent(Graphics2D g) {
        //Matches
        g.setStroke(new BasicStroke(0.005f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (grid[y][x] == empty) g.setColor(Color.lightGray);
                else g.setColor(colors[matches[y][x]]);
                g.fillRect(x, y, 1, 1);
                g.setColor(Color.gray);
                g.drawRect(x, y, 1, 1);
            }
        }

        if (solution != null) {
            int x = size / 2;
            int y = size / 2;
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(0.05f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawRect(x, y, 1, 1); //Highlight starting number

            GeneralPath gp = new GeneralPath();
            gp.moveTo(x + 0.5, y + 0.5);
            for (int i = 0; i < solution.length(); i++) {
                char c = solution.charAt(i);
                if (c == 'L') x--;
                else if (c == 'R') x++;
                else if (c == 'U') y--;
                else if (c == 'D') y++;
                gp.lineTo(x + 0.5, y + 0.5);
            }
            g.drawRect(x, y, 1, 1); //Highlight ending number

            //Path
            Color c0 = new Color(0, 0, 0, 80);
            g.setColor(c0);
            g.setStroke(new BasicStroke(0.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(gp);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(0.03f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(gp);

            //Numbers
            Color c1 = new Color(0, 0, 0, 40);
            for (y = 0; y < size; y++) {
                for (x = 0; x < size; x++) {
                    if (grid[y][x] == empty) continue;
                    Ellipse2D e = new Ellipse2D.Double(x + 0.2, y + 0.2, 0.6, 0.6);
                    g.setColor(colors[matches[y][x]]);
                    g.fill(e);
                    g.setColor(c1);
                    g.fill(e);
                    g.setColor(c0);
                    g.draw(e);
                }
            }
            g.setColor(Color.black);
            adjustFont(g, Font.SANS_SERIF, Font.PLAIN, "0", new Rectangle2D.Double(0, 0, 0.7, 0.7));
            for (y = 0; y < size; y++) {
                for (x = 0; x < size; x++) {
                    if (grid[y][x] == empty) continue;
                    Rectangle2D rc = new Rectangle2D.Double(x, y, 1, 1);
                    drawString(g, String.valueOf(grid[y][x]), rc);
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

            addInfo("Seed", seed);
            addInfoBreak();
            addInfo("Size N", size);
            addInfo("Values V", numValues);
            addInfoBreak();
            addInfo("Score", "-");
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
        writeLine(size);
        writeLine(numValues);
        writeLine(snake);
        flush();
        if (!isReadActive()) return null;

        startTime();
        int n = readLineToInt(-1);
        if (n < 0) {
            setErrorMessage("Invalid number of moves: " + getLastLineRead());
            return null;
        }
        char[] ret = new char[n];
        for (int i = 0; i < ret.length; i++) {
            String s = readLine();
            if (s.length() != 1) {
                setErrorMessage("Invalid return in line " + (i + 1) + " : " + getLastLineRead());
                return null;
            }
            ret[i] = s.charAt(0);
        }
        stopTime();
        return new String(ret);
    }

    public static void main(String[] args) {
        new MarathonController().run(args);
    }
}