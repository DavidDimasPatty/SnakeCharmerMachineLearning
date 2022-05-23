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
public class SnakeCharmer {
      Random MyRand;//variabel random yg dipanggil dari kelas main
    public int maxPopulationSize;//size dari populasi yg nantinya diambibl dari kelas main
    public double elitismPct;//elitism untuk mengambil individu terbaik
    public double crossoverRate;//persenan untuk melakukan crossover
    public double mutationRate;//persenan untuk melakukan mutasi
    public int totalGeneration;//generasi total yg akan dilakukan periterasi
    public ArrayList<Item> listOfItems;//list dari item untuk dilempar ke kelas population
    public Individual final_res;//individual terbaik
    int Board;//jumlah kotak dari board
    public String snake;

    public SnakeCharmer(Random MyRand, int totalGeneration, int maxPopulationSize, double elitismPct,
        double crossoverRate, double mutationRate, ArrayList<Item> listOfItems, int Board,String snake) {//konstruktor kelas hikari
        this.MyRand = MyRand;//random yg diambil dari kelas main
        this.totalGeneration = totalGeneration;//generasi total yg diambil dari kelas main
        this.maxPopulationSize = maxPopulationSize;//populasi maksimal yg dilakukan pada setiap iterasi
        this.elitismPct = elitismPct;//elitism rate dari kelas main
        this.crossoverRate = crossoverRate;//cross over rate dari kelas main
        this.mutationRate = mutationRate;//mutasi rate dari kelas main
        this.listOfItems = listOfItems;//list yg nantinya akan digunakan ke kelas population
        this.Board = Board;//jumlah kotak dari board
        this.snake=snake;
    }

    public Individual run() {//method untuk melakukan algoritma genetik
        int gen = 1;//variabel generasi untuk melakukan loop
        Population currentPop = new Population(MyRand,this.listOfItems, this.Board, this.maxPopulationSize, this.elitismPct,this.snake);//memanggil kelas populasi
        currentPop.randomPopulation();//membuat populasi secara random
        currentPop.hitungFitness();//menghitung fitness setiap individu pada populasi
          while (stop(gen)==0) {//memanggil method terminate untuk memberhentikan program jika tidak ada jawaban yg menyerupai total maksimal cahaya
            Population newPop = currentPop.Elitism();//mendapatkan populasi baru dengan beberapa populasi yg bagus
            while (newPop.checkIsi()==false) {//jika populasi sudah terisi
               Individual[] parents = currentPop.parentSelection();//menyimpan variabel individual ke dalam array
                if (this.MyRand.nextDouble()<=this.crossoverRate) {//melakukan crossover jika persentasi random lebih kecil dari crossover rate
                     Individual child = parents[0].doCrossover(parents[1]);//melakukan cross over pada parents yg memiliki fitness paling bagus
                    if (this.MyRand.nextDouble()<=this.mutationRate) {//melakukan mutasi jika persentasi random lebih kecil mutasi rate
                        child.doMutation();//melakukan mutasi pada setiap child
                    }
                    newPop.addIndividual(child);//menambahkan child ke populasi
                }
            }
            gen++;//generasi ditambah untuk iterasi
            currentPop = newPop;//populasi diganti dengan populasi yg baru
            currentPop.hitungFitness();//menghitung semua fitness dari individu di populasi
          }
        this.final_res=currentPop.getBestIdv();//mendapatkan individu yg terbaik
        return currentPop.getBestIdv();//mengembalikan individu yg bagus
    }

    public int stop(int gen){//method untuk memberhentikan iterasi pada method run
        if (gen >= this.totalGeneration){
         return 1;
        }//jika generasi sudah sama dengan total generasi maka akan berhenti
        else
        {
            return 0;
        }//jika generasi masih lebih kecil maka return false
    }
}
