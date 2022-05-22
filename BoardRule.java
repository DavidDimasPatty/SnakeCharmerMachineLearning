/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ID
 */
public class BoardRule {
    public Random MyRand;//random dari main
    
     public BoardRule(Random MyRand) { //konstruktor
        this.MyRand = MyRand;//random
     }
     
     public String Move(Random MyRand,int board,int i,ArrayList<Item> listOfItems){//method untuk menambah lampu
         String S="";//string kosong yg digunakan untuk field chromosome
         int row=(int)Math.sqrt(board);
         
         String [] move= {"U","D","L","R"};
         int x=this.MyRand.nextInt(row-1);
         int y=this.MyRand.nextInt(row-1);
         int tempx=x;
         int tempy=y;
         int [][] snake=new int [row][row];
         snake [tempx][tempy]=1;
         S=S+x+y;
         
         for(int k = 1; k <= board-1; k++)//untuk setiap kotak
	{       
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
//                            if(o==move_1.length-1 && check==false){
//                              board++;
//                             }   
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
                             //    System.out.print("masuk");
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
//                             if(o==move_1.length-1 && check==false){
//                              board++;
//                             }
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
                          //  System.out.print("masuk");
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
//                             if(o==move_1.length-1 && check==false){
//                              board++;
//                             }
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
                           //      System.out.print("masuk");
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
//                             if(o==move_1.length-1 && check==false){
//                              board++;
//                             }
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
             //System.out.println(S.length());
             for(int k=S.length();k<board;k++){
               String [] move_1= {"D","U","L","R"};
               String temps=move_1[this.MyRand.nextInt(move_1.length)];
               S=S+temps;
             }
         }
         return S;//kembalikan chromosome
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
     
       
        public int Score(ArrayList<Item> listOfItems,int row,String chromosome,int headx,int heady,int secVal){//method untuk menambahkan cahaya pada kotak
            int score=0;
            int [][] snake=new int [row][row];
            snake [headx][heady]=listOfItems.get(0).value;
            int headxtemp=headx;
            int headytemp=heady;
            boolean rule=true;
            
            for(int i=2;i<chromosome.length();i++){
                //up
                try{
                if(Character.toString(chromosome.charAt(i)).equals("U")){
                    
                    headxtemp=headxtemp;
                    headytemp=headytemp-1;
                    if(rule==false){
                        score=score-1000;
                        break;
                    }
                    else if(snake[headxtemp][headytemp]==0){
                        snake[headxtemp][headytemp]=listOfItems.get(i-1).value;
                         score=score+100;
                    }
                    else{
                        rule=false;
                        score=score-1000;
                        
                       // return score;
                    }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                        rule=false;
                        score=score-1000;
                 //   return score;
                }
                
                //bot
                try{
                if(Character.toString(chromosome.charAt(i)).equals("D")){
                    headxtemp=headxtemp;
                    headytemp=headytemp+1;
                    if(rule==false){
                        score=score-1000;
                        break;
                    }
                    else if(snake[headxtemp][headytemp]==0){
                        snake[headxtemp][headytemp]=listOfItems.get(i-1).value;
                         score=score+100;
                   }
                    else{
                       rule=false;
                        score=score-1000;
                    //    return score;
                   }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                rule=false;
                        score=score-1000;
                  ///  return score;
                }
                
                //left
                try{
                if(Character.toString(chromosome.charAt(i)).equals("L")){
                    headxtemp=headxtemp-1;
                    headytemp=headytemp;                  
                     if(rule==false){
                        score=score-1000;
                        break;
                     }
                     else if(snake[headxtemp][headytemp]==0){
                        snake[headxtemp][headytemp]=listOfItems.get(i-1).value;
                         score=score+100;
                    }
                    else{
                        rule=false;
                        score=score-1000;
                     //   return score;
                    }
                   
                     }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                    score=score-1000;
                        break;
                    //return score;
                }
                
                //right
                try{
                if(Character.toString(chromosome.charAt(i)).equals("R")){
                    headxtemp=headxtemp+1;
                    headytemp=headytemp;
                     if(rule==false){
                        score=score-1000;
                        break;
                     }
                     else if(snake[headxtemp][headytemp]==0){
                        snake[headxtemp][headytemp]=listOfItems.get(i-1).value;
                        score=score+100;
                    }
                    else{
                     rule=false;
                        score=score-1000;
                     //    return score;
                    }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                   rule=false;
                        score=score-1000;
                   //  return score;
                }
                
            }
            
            score=score+addWeight(listOfItems,snake,chromosome,headx,heady,secVal,row*row);
            
            //System.out.println(score);
            return score;
        }
        
         public int addWeight(ArrayList<Item> listOfItems,int [][] snake,String chromosome,int headx,int heady,int secVal,int size){//method untuk punish lampu yang nabrak
           /*  int score = 0;
            int[] cnt = new int[5];
            int[] dx = {0,1,0,-1};
            int[] dy = {-1,0,1,0};
            for (heady = 0; heady <size; heady++) {
            for (headx = 0; headx < size; headx++) {
                
                try {
                    
                    if (snake[heady][headx] != 0) {
                        int m = 0;
                        for (int dir = 0; dir < dx.length; dir++) {
                            int x2 = headx + dy[dir];
                            int y2 = heady + dx[dir];
                            if ( snake[heady][headx] == snake[y2][x2])
                            m++;
                        }
                        //this.matches[heady][headx] = m;
                        cnt[m] = cnt[m] + 1;
                        score += (int)Math.pow((snake[heady][headx] - 48), (m + 1));
                    }
    
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                
            }
        }
        return score; */
               int row=snake.length-1;
            
             int headxtemp=headx;
             int headytemp=heady;
             int total=0;
            
             for(int i=2;i<chromosome.length();i++){
                int match=0;
                int x=headx;
                int y=heady;
                 
                if(Character.toString(chromosome.charAt(i)).equals("U")){
                   try{
                    if(snake[headxtemp][headytemp-row]==snake[headxtemp][headytemp])//atas
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                        
                    }
                    
                    try{
                    if(snake[headxtemp][headytemp+row]==snake[headxtemp][headytemp])//bawah
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp-1]==snake[headxtemp][headytemp])//kiri
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp+1]==snake[headxtemp][headytemp])//kanan
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    headxtemp=headxtemp;
                    headytemp=headytemp-1; 
                }
                    
                if(Character.toString(chromosome.charAt(i)).equals("D")){
                    try{
                    if(snake[headxtemp][headytemp-row]==snake[headxtemp][headytemp])//atas
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    
                    try{
                    if(snake[headxtemp][headytemp+row]==snake[headxtemp][headytemp])//bawah
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp-1]==snake[headxtemp][headytemp])//kiri
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp+1]==snake[headxtemp][headytemp])//kanan
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    headxtemp=headxtemp;
                    headytemp=headytemp+1;                   
                    
                    }
                if(Character.toString(chromosome.charAt(i)).equals("L")){
                   try{
                    if(snake[headxtemp][headytemp-row]==snake[headxtemp][headytemp])//atas
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    
                    try{
                    if(snake[headxtemp][headytemp+row]==snake[headxtemp][headytemp])//bawah
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp-1]==snake[headxtemp][headytemp])//kiri
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp+1]==snake[headxtemp][headytemp])//kanan
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    headxtemp=headxtemp-1;
                    headytemp=headytemp;                   
                    
                   }
                if(Character.toString(chromosome.charAt(i)).equals("R")){
                    
                    try{
                    if(snake[headxtemp][headytemp-row]==snake[headxtemp][headytemp])//atas
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    
                    try{
                    if(snake[headxtemp][headytemp+row]==snake[headxtemp][headytemp])//bawah
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp-1]==snake[headxtemp][headytemp])//kiri
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    try{
                    if(snake[headxtemp][headytemp+1]==snake[headxtemp][headytemp])//kanan
                        match++;
                    }catch(ArrayIndexOutOfBoundsException exception){
                    
                    }
                    headxtemp=headxtemp+1;
                    headytemp=headytemp;                   
                    
                     
                    }
                
                total=total+(int)Math.pow(snake[x][y], match+1);
                x=headxtemp;
                y=headytemp;

            }
             
             
             return total; 
         }
         
          
        
}
