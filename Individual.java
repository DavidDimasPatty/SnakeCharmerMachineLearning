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
public class Individual  implements Comparable<Individual>{//implement comparable agar bisa membandingkan fitness
    public String chromosome;//variable chromosome untuk menyimpan value dari setiap kotak diboard
    public int fitness;//fitness dari masing-masing individual
    public Random MyRand;//random yang dipanggil dari kelas main
    public double parentProbability;//kemungkinan suatu individual menjadi parent
    public int secValue;

    public Individual(Random MyRand,int board,int i,ArrayList<Item> listOfItems,int secValue) {//konstruktor
        this.MyRand = MyRand;//random dari kelas main
         BoardRule rule=new BoardRule(this.MyRand);//memanggil kelas boardrule untuk membuat chromosome
        this.chromosome=rule.Move(this.MyRand, board, i, listOfItems);//kromosom diisi dengan lampu lampu
        this.fitness = 0;//fitness dari masing masing individual
        this.parentProbability = 0;//kemungkinan suatu individual menjadi parent
        this.secValue=secValue;
    }

    public Individual(Random MyRand, String chromosome) {//konstruktor 
        this.MyRand = MyRand;//random dari kelas main
        this.chromosome = chromosome;//string kromosom masing-masing individual
        this.fitness = 0;//fitness masing-masing individu
        this.parentProbability = 0;//kemungkinan suatu individual menjadi parent
    }

    public int setFitness(ArrayList<Item> listOfItems,int board) {//set fitness masing masing individual
        int row=(int)Math.sqrt(board);//row dan colum dari board
        int headX=Integer.parseInt(Character.toString(this.chromosome.charAt(0)));
        int headY=Integer.parseInt(Character.toString(this.chromosome.charAt(1)));
        BoardRule rule=new BoardRule(this.MyRand);//memanggil kelas boardrule untuk melakukan operasi 
        this.fitness=rule.Score(listOfItems, row-1,this.chromosome, headX, headY, this.secValue);
        //System.out.println(this.fitness);
        return this.fitness;//fitness dari individual di return
        
    }
    
     

    public void doMutation() {//mutasi pilih random kotak dan ditaro lampu atau hilangkan lampu
        int ind1 = MyRand.nextInt(2,this.chromosome.length()-1);//pilih lenght yang di random
        StringBuilder stringChange = new StringBuilder(this.chromosome);//string builder agar bisa ganti karakter pada string
        String [] move= {"U","D","L","R"};
        Character ranchar=move[this.MyRand.nextInt(move.length)].charAt(0);
        stringChange.setCharAt(ind1, ranchar);   //ditambahkan lampu
        this.chromosome=stringChange.toString();//choromosome diganti dengan yg baru
    }

   //refrensi: https://stackoverflow.com/questions/5189168/how-to-crossover-two-strings-1234-abcd-12cd-ab34
    public Individual doCrossover(Individual other) {//method cross over two points
        Individual child1 = new Individual(this.MyRand,"0");//buat individual baru 
        Individual child2 = new Individual(this.MyRand,"0");//buat individual baru 
         int ind1 = this.MyRand.nextInt(this.chromosome.length());//random length potongnya
        int ind2 = this.MyRand.nextInt(this.chromosome.length());//random length potongnya
        while(ind1>ind2){//jika pemotongan 1 lebih besar dari pemotongan 2
            ind1 = this.MyRand.nextInt(this.chromosome.length());//diulang terus sampai ketemu
        }  
        String s1part1 = this.chromosome.substring(0, ind1);//string 1 dibagian menjadi beberapa bagian untuk nanti digabungkan
        String s1part2 = this.chromosome.substring(ind1, ind2);//part 2 pembagian dari string 1
        String s1part3 = this.chromosome.substring(ind2);//part 3 pembagian dari string 1
        String s2part1 = other.chromosome.substring(0, ind1);//sama seperti string 1 string 2 juga dibagi part
        String s2part2 = other.chromosome.substring(ind1, ind2);//string 2 part 2
        String s2part3 = other.chromosome.substring(ind2);//string 2 part 3
        child1.chromosome = s1part1 + s2part2 + s1part3;//setiap part yang sudah dibagi tersebut kemudian digabungkan
        child2.chromosome = s2part1 + s1part2 + s2part3;////setiap part yang sudah dibagi tersebut kemudian digabungkan
        int choose = this.MyRand.nextInt(2);//kemudian dipilih salah satu dengan random
        if (choose==0) return child1;//kembalian yg pertama jika random 0
        else return child2;//selain itu child2
    }

    @Override
    public int compareTo(Individual other) {//membandingkan individual berdasarkan fitness
    	if (this.fitness>other.fitness) return -1;//jika fitness lebih bagus dari pada yg lainya maka -1
        else if (this.fitness<other.fitness) return 1;//jika tidak 1
        else return 0;//kalau sama 0
    }

    @Override
	public String toString() {//method untuk ngeprint individual
		String res = new String(this.chromosome + " " + this.fitness);//buat string yang containts chromosome dan fitness
		return res;//kembali string
	}

 
}
