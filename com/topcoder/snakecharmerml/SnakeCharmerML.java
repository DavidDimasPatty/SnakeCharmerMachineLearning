/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ID
 */
public class SnakeCharmerML {
//value=v^(m+1)
  public static void main(String[]args){ //method main
           	Scanner sc = new Scanner(System.in);//inilisasi input
   		int loop = sc.nextInt();//input untuk melakukan seberapa banyak iterasi yg diperlukan
   		Random init = new Random();//inilisasi variabel random untuk melakukan random keseluruhan
                String final_chromosome="Result: ";//string yang nantinya berisi chromosome terbaik
                int temp_res=-999;//patokan fitness yang paling buruk
                long seed_ans=0;//variabel seeds yg nantinya akan diisi dengan seeds random untuk hasil
                int fitness_res=0;//hasil akhir fitness yang nanti akan dibandingkan
                int row=0;//baris dan kolom dari board yg pastinya akar dari board
                int target_res=0;//menyimpan hasil paling optimal
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
                        String snake=sc.next();//kotak yg hitam memliki weight yg disekitar kotak tersebut harus ada lamp
                        
                        for (int i=0;i<board;i++) {//setiap kotak diisi value,weight, and condition
                             int value=Integer.parseInt(Character.toString(snake.charAt(i)));//semua kotak dianggap tidak mempunyai cahaya saat pertama kali
                             listOfItems.add(new Item(value, 0,0));
                          }
                        Target target=new Target(listOfItems,board,secVal);//memanggil kelas target untuk menghitung maksimal cahaya yg diperlukan untuk menghitung fitness
                        target_res=target.max_Light(listOfItems, maxCapacity,secVal);//memanggil kelas target untuk mendapatkan hasil dari method max_light
                        
                        //System.out.println(listOfItems.size());
                       
	        } catch (FileNotFoundException e) { e.printStackTrace();}//handle jika tidak ada file yg bernama input.txt keluarkan errorr 
	      
                
                try {//handle try untuk file bernama param.txt
	        	sc = new Scanner(new File("param.txt"));//scan param.txt
	        	totalGeneration = sc.nextInt();//generasi total yg akan dilakukan
	        	maxPopulationSize = sc.nextInt();//banyaknya populasi yg dibutuhkan untuk genetik
	        	crossoverRate = sc.nextDouble();//persenan crossover
	        	mutationRate = sc.nextDouble();//persenan untuk melakukan mutasi
	        	elitismPct = sc.nextDouble();//persenan dari elitism
                        
	        } catch (FileNotFoundException e) { e.printStackTrace();}//handle jika tidak ada file bernama param.txt
		   
                SnakeCharmer hp = new SnakeCharmer(gen,totalGeneration,maxPopulationSize,elitismPct, crossoverRate,mutationRate, listOfItems, board,secVal);//memanggil kelas hikari untuk menjalankan algoritma genetik
	        Individual rhp = hp.run();//memanggil kelas run untuk menjalankan algoritma genetik isinya di masukan ke individu
               
                
                final_chromosome=hp.final_res.chromosome;//menyimpan string chromosome yg mempunyai fitness terbesar
                seed_ans=seed;//menyimpan seed yg mempunyai fitness terbesar
                
              
                System.out.printf("\n %2d: Total = %d Seed: %d \n"+rhp.chromosome,ct,rhp.fitness,seed);//print iterasi fittness dan persenan fitness dengan target

                }
               //************************************************************
                 //BAGIAN OUTPUT TIDAK DIPAKAI DI BAGIAN INI
               //************************************************************                 
//                StringBuilder sb = new StringBuilder(final_chromosome);//string builder untuk menyambungkan string dengan karakter baru
//                int leng=final_chromosome.length();//variabel untuk menyimpan panjang kromosome
//                for(int i=0;i<leng;i+=row+1){//iterasi yg dilakukan untuk merapihkan string dengan spasi
//                      sb.insert(i, " ");//setiap akhir dari baris ditambahkan spasi
//                        leng=leng+1;//leng bertambah dengan adanya karakter baru
//                   }
//                final_chromosome=sb.toString();//ubah format string builder ke string
//
//                try {  //handle untuk file output txt
//                FileWriter myWriter = new FileWriter("output.txt");//scan untuk write file output.txt
//                myWriter.write("result : \n"+ final_chromosome.replaceAll("\\s+","\n")+"\n"+"\n"+"Fitness : "+(1.0*fitness_res/target_res)+"\n"+"Total cahaya maksimal : "+target_res+"\n"+"Jumlah Cahaya : "+1.0*fitness_res+"\n"+"Seed : "+seed_ans);//print chromosome yg sudah diganti jika ada spasi maka akan enter,fitness,persentase fitness,cahaya 
//                myWriter.close();//akhiri write dari file output.txt
//                } 
//                 catch (IOException e) {//jika tidak ada file output.txt
//                System.out.println("An error occurred.");//print errot
//                e.printStackTrace();
//                }         
}
}
