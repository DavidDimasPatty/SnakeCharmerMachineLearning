import java.util.ArrayList;
import java.util.Random;

public class BoardRule {
    public Random MyRand; // random dari main
    
    public BoardRule(Random MyRand) { // konstruktor
        this.MyRand = MyRand; // random
    }
    
    public String Move(Random MyRand,int board,int i,ArrayList<Item> listOfItems){ // method untuk menambah lampu
        String S=""; // string kosong yg digunakan untuk field chromosome
        int row=(int)Math.sqrt(board);
        
        String [] move= {"U","D","L","R"};
        int x=this.MyRand.nextInt(row-1);
        int y=this.MyRand.nextInt(row-1);
        int tempx=x;
        int tempy=y;
        int [][] snake=new int [row][row];
        snake [tempx][tempy]=1;
        S=S+x+y;
        
        for(int k = 1; k <= board-1; k++) { // untuk setiap kotak
            String ran=move[this.MyRand.nextInt(move.length)];
            boolean check=false; 
            if(ran.equals("U")){
                if(tempy-1>row ||tempy-1<0||snake[tempx][tempy-1]==1 ){
                    String [] move_1= {"D","L","R"};
                    for(int o=0;o<move_1.length;o++){
                        String temps=move_1[o];
                        check=changeVariable(tempx,tempy,snake,temps,row);
                        if(check!=false){
                            // System.out.print("masuk");
                            if(temps.equals("L")){
                                tempx=tempx-1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("R")){
                                tempx=tempx+1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("D")){
                                tempy=tempy+1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            break;
                        }
                        // if(o==move_1.length-1 && check==false){
                        //     board++;
                        // }
                    }
                }
                else{
                    S=S+ran;
                    tempy=tempy-1;
                    snake[tempx][tempy]=1;
                }
            }
            else if(ran.equals("D")){
                if( tempy+1>=row ||tempy+1<0 || snake[tempx][tempy+1]==1 ){
                    String [] move_1= {"U","L","R"};
                    for(int o=0;o<move_1.length;o++){
                        String temps=move_1[o];
                        check=changeVariable(tempx,tempy,snake,temps,row);
                        if(check!=false){
                            // System.out.print("masuk");
                            if(temps.equals("L")){
                                tempx=tempx-1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("R")){
                                tempx=tempx+1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("U")){
                                tempy=tempy-1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            break;
                        }
                        // if(o==move_1.length-1 && check==false){
                        //     board++;
                        // }
                    }
                }
                else{
                    S=S+ran;
                    tempy=tempy+1;
                    snake[tempx][tempy]=1;
                }
            }
            else if(ran.equals("L")){
                if(tempx-1>=row ||tempx-1<0 ||snake[tempx-1][tempy]==1 ){
                    String [] move_1= {"D","U","R"};
                    for(int o=0;o<move_1.length;o++){
                        String temps=move_1[o];
                        check=changeVariable(tempx,tempy,snake,temps,row);
                        if(check!=false){
                            // System.out.print("masuk");
                            if(temps.equals("U")){
                                tempy=tempy-1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("R")){
                                tempx=tempx+1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("D")){
                                tempy=tempy+1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            break;
                        }
                        // if(o==move_1.length-1 && check==false){
                        //     board++;
                        // }
                    }
                }
                else{
                    S=S+ran;
                    tempx=tempx-1;
                    snake[tempx][tempy]=1;
                }
            }
            else if(ran.equals("R")){
                if(tempx+1>=row ||tempx+1<0 ||snake[tempx+1][tempy]==1){
                    String [] move_1= {"U","L","D"};
                    for(int o=0;o<move_1.length;o++){
                        String temps=move_1[o];
                        check=changeVariable(tempx,tempy,snake,temps,row);
                        if(check!=false){
                            // System.out.print("masuk");
                            if(temps.equals("L")){
                                tempx=tempx-1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("U")){
                                tempy=tempy-1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            else if(temps.equals("D")){
                                tempy=tempy+1;
                                snake[tempx][tempy]=1;
                                S=S+temps;
                            }
                            break;
                        }
                        // if(o==move_1.length-1 && check==false){
                        //     board++;
                        // }
                    }
                }
                else{
                    S=S+ran;
                    tempx=tempx+1;
                    snake[tempx][tempy]=1;
                }
            }
        }
        if(S.length()<board){
            // System.out.println(S.length());
            for(int k=S.length();k<board;k++){
                String [] move_1= {"D","U","L","R"};
                String temps=move_1[this.MyRand.nextInt(move_1.length)];
                S=S+temps;
            }
        }
        return S; // kembalikan chromosome
    }
    
    public boolean changeVariable(int x,int y,int [][]arr,String s,int row){
        boolean res=true;
        if(s.equals("U")){
            y=y-1;
            try{
                if(y>row ||y<0||arr[x][y]==1){
                    return false;
                }
                if (arr[x][y]==0){
                    return res;
                }
            }
            catch(ArrayIndexOutOfBoundsException exception){
                return false;
            }
        }
        if(s.equals("D")){
            y=y+1;
            try{
                if(y>=row ||y<0||arr[x][y]==1){
                    return false;
                }
                if (arr[x][y]==0){
                    return res;
                }
            }
            catch(ArrayIndexOutOfBoundsException exception){
                return false;
            }
        }
        if(s.equals("L")){
            x=x-1;
            try{
                if(x>row ||x<0||arr[x][y]==1){
                    return false;
                }
                if (arr[x][y]==0){
                    return res;
                }
            }
            catch(ArrayIndexOutOfBoundsException exception){
                return false;
            }
        }
        if(s.equals("R")){
            x=x+1;
            try{
                if(x>=row ||x<0||arr[x][y]==1){
                    return false;
                }
                if (arr[x][y]==0){
                    return res;
                }
            }
            catch(ArrayIndexOutOfBoundsException exception){
                return false;
            }
        }
        return res;
    }
    
    public int Score(String chromosome,String snake){ // method untuk menambahkan cahaya pada kotak
        ScoreManager sm = new ScoreManager(chromosome, snake);
        int score = sm.calculate();
        
        return score;
    }
}