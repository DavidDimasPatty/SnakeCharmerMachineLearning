/*     */ import com.topcoder.marathon.MarathonController;
/*     */ import com.topcoder.marathon.MarathonVis;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
/*     */ import java.util.Arrays;
import java.util.Scanner;
/*     */ 
/*     */ public class SnakeCharmerTester extends MarathonVis {
/*     */   private static final int minSize = 7;
/*     */   
/*     */   private static final int maxSize = 49;
/*     */   
/*     */   private static final int minNumValues = 2;
/*     */   
/*     */   private static final int maxNumValues = 8;
/*     */   
/*     */   private static final int minValue = 2;
/*     */   
/*     */   private int size;
/*     */   
/*     */   private int numValues;
/*     */   
/*     */   private String snake;
/*     */   
/*  19 */   private static final int[] dx = new int[] { 0, 1, -1 };
/*     */   
/*  20 */   private static final int[] dy = new int[] { -1, 1 };
/*     */   
/*     */   private static final char empty = '.';
/*     */   
/*     */   private Color[] colors;
/*     */   
/*     */   private int[][] matches;
/*     */   
/*     */   private char[][] grid;
/*     */   
/*     */   private String solution;
            private String Score="0";
            
            private int headx;
            private int heady;
/*     */   
/*     */   protected void generate() {
/*  34 */     this.size = randomInt(0, 21) * 2 + 7;
/*  35 */     this.numValues = randomInt(2, 8);
/*  38 */     if (this.seed == 1L) {
/*  39 */       this.size = 7;
/*  40 */       this.numValues = 3;
/*  41 */     } else if (this.seed == 2L) {
/*  42 */       this.size = 49;
/*  43 */       this.numValues = 8;
/*     */     } 
/*  47 */     if (this.parameters.isDefined("N")) {
/*  48 */       int[] range = this.parameters.getIntRange("N");
/*  49 */       int min = Math.min(49, Math.max(7, range[0]));
/*  50 */       int max = Math.min(49, Math.max(7, range[1]));
/*  51 */       if (min % 2 == 1 && max % 2 == 1 && max >= min)
/*  51 */         this.size = randomInt(0, (max - min) / 2) * 2 + min; 
/*     */     } 
/*  53 */     if (this.parameters.isDefined("V"))
/*  53 */       this.numValues = randomInt(this.parameters.getIntRange("V"), 2, 8); 
/*  56 */     StringBuilder sb = new StringBuilder(this.size * this.size);
/*  57 */     for (int i = 0; i < this.size * this.size; i++)
/*  58 */       sb.append((char)(48 + randomInt(2, 2 + this.numValues - 1))); 
/*  60 */     this.snake = sb.toString();
/*  62 */     if (this.debug) {
/*  63 */       System.out.println("Grid Size N = " + this.size);
/*  64 */       System.out.println("   Values V = " + this.numValues);
/*  65 */       System.out.println("      Snake = " + this.snake);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isMaximize() {
/*  70 */     return true;
/*     */   }
/*     */   
/*     */   protected double run() throws Exception {
/*  74 */     init();
/*  75 */     this.solution = callSolution();
/*  76 */     if (this.solution == null) {
/*  77 */       if (!isReadActive())
/*  77 */         return getErrorScore(); 
/*  78 */       return fatalError();
/*     */     } 
/*  81 */     int x = Integer.parseInt(Character.toString(this.solution.charAt(0)));
/*  82 */     int y = Integer.parseInt(Character.toString(this.solution.charAt(1)));
               this.headx=x;
               this.heady=y;
              this.solution=this.solution.substring(2, this.solution.length());
/*  83 */     this.grid[y][x] = this.snake.charAt(this.solution.length());
/*     */     int i;
/*  84 */     for (i = 0; i < this.solution.length(); i++) {
/*  85 */       char c = this.solution.charAt(i);
/*  86 */       if (c == 'L') {
/*  86 */         x--;
/*  87 */       } else if (c == 'R') {
/*  87 */         x++;
/*  88 */       } else if (c == 'U') {
/*  88 */         y--;
/*  89 */       } else if (c == 'D') {
/*  89 */         y++;
/*     */       } else {
/*  90 */         return fatalError("Unknown direction " + c + " at move " + (i + 1));
/*     */       } 
/*  91 */       if (!inGrid(y, x))
/*  91 */         return fatalError("You stepped out of grid bounds at move " + (i + 1)); 
/*  92 */       if (this.grid[y][x] != '.')
/*  92 */         return fatalError("You've hit yourself at move " + (i + 1)); 
/*  93 */       this.grid[y][x] = this.snake.charAt(this.solution.length() - i );
/*     */     } 
/*  96 */     if (this.debug) {
/*  97 */       System.out.println("Your moves: " + this.solution);
/*  98 */       for (i = 0; i < this.size; i++) {
/*  99 */         for (int k = 0; k < this.size; k++)
/* 100 */           System.out.print(this.grid[i][k]); 
/* 102 */         System.out.println();
/*     */       } 
/*     */     } 
             //ngitung score
/* 107 */     int score = 0;
/* 108 */     int[] cnt = new int[5];
/* 109 */     for (y = 0; y < this.size; y++) {
/* 110 */       for (x = 0; x < this.size; x++) {
/* 111 */         if (this.grid[y][x] != '.') {
/* 112 */           int m = 0;
/* 113 */           for (int dir = 0; dir < dx.length; dir++) {
/* 114 */             int x2 = x + dy[dir];
/* 115 */             int y2 = y + dx[dir];
/* 116 */             if (inGrid(y2, x2) && this.grid[y][x] == this.grid[y2][x2])
/* 116 */               m++; 
/*     */           } 
/* 118 */           this.matches[y][x] = m;
/* 119 */           cnt[m] = cnt[m] + 1;
/* 120 */           score += (int)Math.pow((this.grid[y][x] - 48), (m + 1));
/*     */         } 
/*     */       } 
/*     */     } 
/* 124 */     if (hasVis()) {
/* 125 */       addInfo("Score", Integer.valueOf(score));
/* 126 */       addInfo("Time", String.valueOf(getRunTime()) + " ms");
/* 127 */       for (int c = 0; c < this.colors.length; c++)
/* 128 */         addInfo(this.colors[c], Integer.valueOf(cnt[c])); 
/* 130 */       update();
/*     */     } 
/* 132 */     return score;
/*     */   }
/*     */   
/*     */   protected void paintContent(Graphics2D g) {
/* 137 */     g.setStroke(new BasicStroke(0.005F, 1, 1));
/* 138 */     for (int y = 0; y < this.size; y++) {
/* 139 */       for (int x = 0; x < this.size; x++) {
/* 140 */         if (this.grid[y][x] == '.') {
/* 140 */           g.setColor(Color.lightGray);
/*     */         } else {
/* 141 */           g.setColor(this.colors[this.matches[y][x]]);
/*     */         } 
/* 142 */         g.fillRect(x, y, 1, 1);
/* 143 */         g.setColor(Color.gray);
/* 144 */         g.drawRect(x, y, 1, 1);
/*     */       } 
/*     */     } 
/* 148 */     if (this.solution != null) {
/* 149 */       int x = headx;
/*  82 */       int j = heady;
/* 151 */       g.setColor(Color.black);
/* 152 */       g.setStroke(new BasicStroke(0.05F, 1, 1));
/* 153 */       g.drawRect(x, j, 1, 1);
/* 155 */       GeneralPath gp = new GeneralPath();
/* 156 */       gp.moveTo(x + 0.5D, j + 0.5D);
/* 157 */       for (int i = 0; i < this.solution.length(); i++) {
/* 158 */         char c = this.solution.charAt(i);
/* 159 */         if (c == 'L') {
/* 159 */           x--;
/* 160 */         } else if (c == 'R') {
/* 160 */           x++;
/* 161 */         } else if (c == 'U') {
/* 161 */           j--;
/* 162 */         } else if (c == 'D') {
/* 162 */           j++;
/*     */         } 
/* 163 */         gp.lineTo(x + 0.5D, j + 0.5D);
/*     */       } 
/* 165 */       g.drawRect(x, j, 1, 1);
/* 168 */       Color c0 = new Color(0, 0, 0, 80);
/* 169 */       g.setColor(c0);
/* 170 */       g.setStroke(new BasicStroke(0.4F, 1, 1));
/* 171 */       g.draw(gp);
/* 172 */       g.setColor(Color.black);
/* 173 */       g.setStroke(new BasicStroke(0.03F, 1, 1));
/* 174 */       g.draw(gp);
/* 177 */       Color c1 = new Color(0, 0, 0, 40);
/* 178 */       for (j = 0; j < this.size; j++) {
/* 179 */         for (x = 0; x < this.size; x++) {
/* 180 */           if (this.grid[j][x] != '.') {
/* 181 */             Ellipse2D e = new Ellipse2D.Double(x + 0.2D, j + 0.2D, 0.6D, 0.6D);
/* 182 */             g.setColor(this.colors[this.matches[j][x]]);
/* 183 */             g.fill(e);
/* 184 */             g.setColor(c1);
/* 185 */             g.fill(e);
/* 186 */             g.setColor(c0);
/* 187 */             g.draw(e);
/*     */           } 
/*     */         } 
/*     */       } 
/* 190 */       g.setColor(Color.black);
/* 191 */       adjustFont(g, "SansSerif", 0, "0", new Rectangle2D.Double(0.0D, 0.0D, 0.7D, 0.7D));
/* 192 */       for (j = 0; j < this.size; j++) {
/* 193 */         for (x = 0; x < this.size; x++) {
/* 194 */           if (this.grid[j][x] != '.') {
/* 195 */             Rectangle2D rc = new Rectangle2D.Double(x, j, 1.0D, 1.0D);
/* 196 */             drawString(g, String.valueOf(this.grid[j][x]), rc);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean inGrid(int x, int y) {
/* 203 */     return (x >= 0 && x < this.size && y >= 0 && y < this.size);
/*     */   }
/*     */   
/*     */   private void init() {
/* 207 */     this.grid = new char[this.size][this.size];
/* 208 */     for (int i = 0; i < this.size; i++)
/* 209 */       Arrays.fill(this.grid[i], '.'); 
/* 211 */     this.matches = new int[this.size][this.size];
/* 213 */     if (hasVis()) {
/* 214 */       this.colors = new Color[] { Color.white, Color.green, Color.cyan, Color.orange, Color.red };
/* 216 */       setInfoMaxDimension(15, 15);
/* 217 */       setContentRect(0.0D, 0.0D, this.size, this.size);
/* 218 */       setDefaultSize(30);
/* 220 */       addInfo("Seed TES", Long.valueOf(this.seed));
/* 221 */       addInfoBreak();
/* 222 */       addInfo("Size of Board", Integer.valueOf(this.size));
/* 223 */       addInfo("Values V", Integer.valueOf(this.numValues));
/* 224 */       addInfoBreak();
/* 225 */       addInfo("Score", Integer.valueOf(Integer.parseInt(this.Score)));
/* 226 */       addInfoBreak();
/* 227 */       addInfo("Time", "-");
/* 228 */       addInfoBreak();
/* 229 */       addInfo("Count");
/* 230 */       for (int c = 0; c < this.colors.length; c++)
/* 231 */         addInfo(this.colors[c], Integer.valueOf(0)); 
/* 233 */       update();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String callSolution() throws Exception {
/* 238 */   Scanner sc = new Scanner(System.in);//inilisasi input  
            sc = new Scanner(new File("output.txt"));//scan file txt untuk input
            String ret= sc.next();
            this.Score=sc.next();
            System.out.println(this.Score);
            return new String(ret);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws IOException {
    ProifML proifML = new ProifML();
    proifML.start();
/* 264 */     (new MarathonController()).run(args);
/*     */   }
/*     */ }


/* Location:              C:\Users\Intel\Documents\SnakeCharmer\!\SnakeCharmerTester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */