/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;

/**
 *
 * @author ID
 */
public class Target {
     public ArrayList<Item> population;//
     int Board;//banyak nya kotak di board

    public Target(ArrayList<Item> listOfItems,int Board,int secVal) {//konstruktor
	this.population = new ArrayList<Item>();
        this.Board = Board;
    }
    
    public int max_Light(ArrayList<Item> listOfItems,int row,int secVal){//menghitung jumlah cahaya maksimum
        int light=0;//variabel untuk menyimpan cahaya yang nyala
        
                for(int k=0;k<(row*row);k++){//menghitung banyaknya cahaya di lampu sebanyak kotak di board
             if(listOfItems.get(k).condition!=-1){//jika bukan kotak hitam pasti kena cahaya
              
                light++;//cahaya di tambah
            }
         }
        return light;//kembalikan banyaknya cahaya
    }
}
