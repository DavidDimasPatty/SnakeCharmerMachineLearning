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
public class Population {
    public ArrayList<Individual> population;//array list untuk setiap individu
    private int maxPopulationSize;//banyaknya ukuran populasi
    private int populationSize=0;//digunakan untuk iterasi
    public double elitismPct;//persentasi elitism
    ArrayList<Item> listOfItems;//array list untuk menyimpan item
    int Board;//banyaknya kotak pada puzzle
    int jumlahRank=0;//variabel untuk menjumlah rank
    Random MyRand;//variabel random dari kelas main
    public String snake;

    public Population(Random MyRand, ArrayList<Item> listOfItems,int Board,int maxPopulationSize, double elitismPct,String snake) {//konsstruktor kelas populasi
        this.MyRand = MyRand;//variabel random dari kelas main
        this.maxPopulationSize = maxPopulationSize;//variabel max population size untuk konstruktor
        this.population = new ArrayList<Individual>();//variabel populasi yg mengandung array list ssebagai konstruktor
        this.elitismPct = elitismPct;//rate elitism
        this.listOfItems = listOfItems;//konstruktor untuk list of items
        this.Board = Board;//banyaknya kotak di box
        for (int i=1;i<=this.maxPopulationSize;i++) {
            this.jumlahRank = this.jumlahRank + i;
        }//buat rank untuk melakukan crossover, populasi dll
        this.snake=snake;
    }

    
      public boolean addIndividual(Individual Indv) {//method untuk menambahkan individu baru
        if (this.populationSize>=this.maxPopulationSize) 
        {
            return false;
        }//jika sudah penuh maka akan mengembalikan false
        this.populationSize++;//populasi size bertambah
        this.population.add(Indv);//jika belum maka akan diisi individu baru
       return true;//return true
    }
      
      
    public Population Elitism() {//method untuk mendapatkan populasi baru dengan elitism
        Population newPop = new Population(this.MyRand, this.listOfItems,this.Board,this.populationSize, this.elitismPct,this.snake);//memanggil kelas populasi 
        int n = (int)(this.elitismPct * this.maxPopulationSize);//persentase elitism dikali dengan size maximal dari populasi
        for (int i=0;i<n;i++) {//untuk setiap populasi baru dengan size n
            boolean res = newPop.addIndividual(this.population.get(i));//tambahkan semua individu baru pada populasi
        }
        return newPop;//mengembalikan populasi baru
    }
    
    public void randomPopulation() {//buat individu untuk populasi
        for (int i=0;i<this.maxPopulationSize;i++) {//membuat individu sebanyak maxpipulationsize
            this.addIndividual(new Individual(this.MyRand,Board,i,listOfItems,this.snake));//memanggil method add individual untuk meambahkan individu baru
        }
    }


    public void hitungFitness() {//menghitung semua fitness pada setiap individu di populasi
        for (int i=0;i<this.populationSize;i++) {//untuk setiap individu pada populasi
             ((Individual)this.population.get(i)).setFitness(listOfItems, Board,this.snake);//set fitness untuk setiap individu
           }
        this.population.sort((idv1,idv2) -> idv1.compareTo(idv2));//sort fitness dari sretiap individu menjadi yg terbesar
       
    }
     //refrensi: kode ko Lionov di teams (General -> Class Material -> Slide Kuliah -> Kode Culun ->new GA) 


    public boolean checkIsi() {//method untuk check populasi sudah penuh apa belum
        return this.maxPopulationSize==this.populationSize;//return true atau false jika sudah penuh apa belum
    }
     //refrensi: kode ko Lionov di teams (General -> Class Material -> Slide Kuliah -> Kode Culun ->new GA) 

    public Individual[] parentSelection() {//select parent dengan cara roulette wheel
        Individual[] parents = new Individual[2]; //set array untuk parent
        int top = this.population.size()+1;//batas population ditambah 1 untuk nambah yg baru
        long totalfitness = 0;//perjumlahan fitness
        for (int i=0;i<this.population.size();i++) {//loop semua fitness ditambahin
           totalfitness = totalfitness + this.population.get(i).fitness;//update sumfitness
        }
        for (int i=0;i<this.population.size();i++) {//loop untuk setiap populasi
            ((Individual)this.population.get(i)).parentProbability = (1.0*this.population.get(i).fitness)/totalfitness;//dimasukan probability terpilih jadi parennya
        }
        for (int n = 0;n<parents.length;n++) {//loop untuk ngisi parent
            int i=-1;//inisialisasi i untuk di while loop
            double prob = this.MyRand.nextDouble();//probabilitas terpilihnya
            double sum = 0.0;//sum untuk penambahan dengan parent probability
            do {
                i++;//index populasinya
                sum = sum + this.population.get(i).parentProbability;//sum terus sampai lebih besar dari prob
            } while(sum<prob);//loop untuk penambahan i
            parents[n] = this.population.get(i);//pilih parent dari i yg terpilih
        }
        return parents;//kembalikan parent
    }

    public Individual getBestIdv() {//dapat individu yang fitnessnya paling bagus
        return this.population.get(0);//return individu
    }


}
