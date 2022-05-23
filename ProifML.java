/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author ID
 */
public class ProifML {
//value=v^(m+1)
  public void start() throws IOException{ //method main
           	Scanner sc = new Scanner(System.in);//inilisasi input
   		int loop = sc.nextInt();//input untuk melakukan seberapa banyak iterasi yg diperlukan
   		Random init = new Random();//inilisasi variabel random untuk melakukan random keseluruhan
                String final_chromosome="Result: ";//string yang nantinya berisi chromosome terbaik
                int temp_res=-999;//patokan fitness yang paling buruk
                long seed_ans=0;//variabel seeds yg nantinya akan diisi dengan seeds random untuk hasil
                int fitness_res=0;//hasil akhir fitness yang nanti akan dibandingkan
                int row=0;//baris dan kolom dari board yg pastinya akar dari board
                int target_res=0;//menyimpan hasil paling optimal
                int rhptemp=0;
                String snake="";
                String best_chromosome="";
                long best_seed=0;
                for (int ct=1;ct<=loop;ct++) {//for sebanyak loop kali
    		long seed = init.nextLong();//inisialisasi seed
	        Random gen = new Random(seed);//seed dimasukan kedalam random agar mempunyai patokan
	    	int board=0, totalGeneration=0, maxPopulationSize=0;// board ukuran puzzle, total generasi dari genetik, maksimum populasi yg dibuat
	    	double crossoverRate=0.0, mutationRate=0.0, elitismPct=0.0;//persenan crossover,mutas,dan elitism
                ArrayList<Item> listOfItems = new ArrayList<Item>();//array list yg berisi kelas item \
                int secVal=0;
	    	try {//handle try jika ada file yg bernama input.txt
	        	sc = new Scanner(new File("input.txt"));//scan file txt untuk input
	        	int maxCapacity = sc.nextInt();//baris yg diperlukan untuk membangun board
                        row=maxCapacity;//variabel row diisi maxcapacity
                        board=maxCapacity*maxCapacity;//banyaknya kotak yg ada dalam puzzle
                        secVal = sc.nextInt();//section value setiap kotak
                        snake=sc.next();//kotak yg hitam memliki weight yg disekitar kotak tersebut harus ada lamp
                        
                        for (int i=0;i<board;i++) {//setiap kotak diisi value,weight, and condition
                             int value=Integer.parseInt(Character.toString(snake.charAt(i)));//semua kotak dianggap tidak mempunyai cahaya saat pertama kali
                             listOfItems.add(new Item(value, 0,0));
                          }
                       
                       
	        } catch (FileNotFoundException e) { e.printStackTrace();}//handle jika tidak ada file yg bernama input.txt keluarkan errorr 
	      
                
                try {//handle try untuk file bernama param.txt
	        	sc = new Scanner(new File("param.txt"));//scan param.txt
	        	totalGeneration = sc.nextInt();//generasi total yg akan dilakukan
	        	maxPopulationSize = sc.nextInt();//banyaknya populasi yg dibutuhkan untuk genetik
	        	crossoverRate = sc.nextDouble();//persenan crossover
	        	mutationRate = sc.nextDouble();//persenan untuk melakukan mutasi
	        	elitismPct = sc.nextDouble();//persenan dari elitism
                        
	        } catch (FileNotFoundException e) { e.printStackTrace();}//handle jika tidak ada file bernama param.txt
		   
                SnakeCharmer hp = new SnakeCharmer(gen,totalGeneration,maxPopulationSize,elitismPct, crossoverRate,mutationRate, listOfItems, board,snake);//memanggil kelas hikari untuk menjalankan algoritma genetik
	            Individual rhp = hp.run();//memanggil kelas run untuk menjalankan algoritma genetik isinya di masukan ke individu
               
                if(rhptemp<rhp.fitness){
                    best_chromosome=rhp.chromosome;
                    rhptemp=rhp.fitness;
                    best_seed=seed;
                }
                final_chromosome=hp.final_res.chromosome;//menyimpan string chromosome yg mempunyai fitness terbesar
                seed_ans=seed;//menyimpan seed yg mempunyai fitness terbesar
                
              
                System.out.printf("\n %2d: Total = %d Seed: %d \n"+rhp.chromosome,ct,rhp.fitness,seed);//print iterasi fittness dan persenan fitness dengan target

                
                }
                
               //************************************************************
                 //BAGIAN OUTPUT TIDAK DIPAKAI DI BAGIAN INI
               //************************************************************                 
                //write output file
                try {  //handle untuk file output txt
                FileWriter myWriter = new FileWriter("output.txt");//scan untuk write file output.txt
                best_chromosome=finalCheck(best_chromosome,row*row);
                myWriter.write(best_chromosome+"\n");//print chromosome yg sudah diganti jika ada spasi maka akan enter,fitness,persentase fitness,cahaya 
                myWriter.write(Integer.toString(rhptemp)+"\n");
                myWriter.write(Long.toString(best_seed)+"\n");
                myWriter.write(snake);
                myWriter.close();//akhiri write dari file output.txt
                } 
                 catch (IOException e) {//jika tidak ada file output.txt
                System.out.println("An error occurred.");//print errot
                e.printStackTrace();
                 }
                
                //masukin ke zip
               //Path myFilePath = Paths.get("output.txt");

                // Path zipFilePath = Paths.get("SnakeCharmer.jar");
                // try( 
                //     FileSystem fs = FileSystems.newFileSystem(zipFilePath)
                //    )
                // {
                //     Path fileInsideZipPath = fs.getPath("/output.txt");
                //     Files.delete(fileInsideZipPath);
                //     Files.copy(myFilePath, fileInsideZipPath);
                // } catch (IOException e) {
                //     // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }
                
          //OPENING JAR
        //  System.out.println();
        // System.out.println("Opening JAR");
        // String[] command = {"java", "-jar","SnakeCharmer.jar"};
        // ProcessBuilder builder = new ProcessBuilder(command);
        // Process p = builder.start();

         
  
  }

  public String finalCheck(String s,int board){
    int row=(int) Math.sqrt(board);
    int [][] snake=new int [row][row];
    int x= Integer.parseInt(Character.toString(s.charAt(0)));
    int y= Integer.parseInt(Character.toString(s.charAt(1)));
    int trim=0;

    for (int i=2;i<s.length();i++){
            try{
                if(Character.toString(s.charAt(i)).equals("U")){
                    
                    y=y-1;
                     if(snake[x][y]==0){
                        snake[x][y]=1;
                    }
                    else{
                        trim=i;
                        break;
                       // return score;
                    }
                }
                }
                catch(ArrayIndexOutOfBoundsException exception){
                    trim=i;
                    break;
                }

                try{
                  if(Character.toString(s.charAt(i)).equals("D")){
                      
                      y=y+1;
                       if(snake[x][y]==0){
                          snake[x][y]=1;
                      }
                      else{
                          trim=i;
                          break;
                         // return score;
                      }
                  }
                  }
                  catch(ArrayIndexOutOfBoundsException exception){
                      trim=i;
                      break;
                  }
                  
                  try{
                    if(Character.toString(s.charAt(i)).equals("L")){
                        
                        x=x-1;
                         if(snake[x][y]==0){
                            snake[x][y]=1;
                        }
                        else{
                            trim=i;
                            break;
                           // return score;
                        }
                    }
                    }
                    catch(ArrayIndexOutOfBoundsException exception){
                        trim=i;
                        break;
                    }

                    try{
                      if(Character.toString(s.charAt(i)).equals("R")){
                          
                          x=x+1;
                           if(snake[x][y]==0){
                              snake[x][y]=1;
                          }
                          else{
                              trim=i;
                              break;
                             // return score;
                          }
                      }
                      }
                      catch(ArrayIndexOutOfBoundsException exception){
                          trim=i;
                          break;
                      }
                
     }
     if(trim!=0){
       s=s.substring(0,trim);
     }
    return s;
  }

                 
}
