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
         String [] move= {"U","B","L","R"};
         int x=this.MyRand.nextInt(row-1);
         int y=this.MyRand.nextInt(row-1);
         S=S+x+y;
         
         for(int k = 1; k <= board-1; k++)//untuk setiap kotak
	{
                S=S+move[this.MyRand.nextInt(move.length)];
        }
         return S;//kembalikan chromosome
     }
     
       
        public int Score(ArrayList<Item> listOfItems,int row,String chromosome,int headx,int heady,int secVal){//method untuk menambahkan cahaya pada kotak
            int score=0;
            int [][] snake=new int [row][row];
            snake [headx][heady]=listOfItems.get(0).value;
            int headxtemp=headx;
            int headytemp=heady;
            
            for(int i=2;i<chromosome.length();i++){
                //up
                try{
                if(Character.toString(chromosome.charAt(i)).equals("U")){
                    
                    headxtemp=headxtemp;
                    headytemp=headytemp-row;
                    if(snake[headxtemp][headytemp-row]==0){
                        snake[headxtemp][headytemp-row]=listOfItems.get(i-1).value;
                    }
                    else{
                        score=score-10000;
                    }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                    score=score-10000;
                }
                
                //bot
                try{
                if(Character.toString(chromosome.charAt(i)).equals("B")){
                    headxtemp=headxtemp;
                    headytemp=headytemp+row;
                   if(snake[headxtemp][headytemp-row]==0){
                        snake[headxtemp][headytemp-row]=listOfItems.get(i-1).value;
                   }
                    else{
                        score=score-10000;
                    }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                    score=score-10000;
                }
                
                //left
                try{
                if(Character.toString(chromosome.charAt(i)).equals("L")){
                    headxtemp=headxtemp-1;
                    headytemp=headytemp;                  
                    if(snake[headxtemp][headytemp-row]==0){
                        snake[headxtemp][headytemp-row]=listOfItems.get(i-1).value;
                    }
                    else{
                        score=score-10000;
                    }
                   
                     }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                    score=score-10000;
                }
                
                //right
                try{
                if(Character.toString(chromosome.charAt(i)).equals("R")){
                    headxtemp=headxtemp+1;
                    headytemp=headytemp;
                    if(snake[headxtemp][headytemp-row]==0){
                        snake[headxtemp][headytemp-row]=listOfItems.get(i-1).value;
                    }
                    else{
                         score=score-10000;
                    }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                    score=score-10000;
                }
                
            }
            
            score=addWeight(listOfItems,snake,chromosome,headx,heady,secVal);
            
            //System.out.println(score);
            return score;
        }
        
         public int addWeight(ArrayList<Item> listOfItems,int [][] snake,String chromosome,int headx,int heady,int secVal){//method untuk punish lampu yang nabrak
             int row=snake.length-1;
            
             int headxtemp=headx;
             int headytemp=heady;
             int total=0;
            
             for(int i=2;i<chromosome.length();i++){
                int match=0;
                 
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
                }
                    
                if(Character.toString(chromosome.charAt(i)).equals("B")){
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
                
                total=total+(int)Math.pow(secVal, match+1);
            }
             
             
             return total;
         }
         
          
        
}
