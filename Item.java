/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ID
 */
public class Item {
     public int value;//nilai kotak
    public int weight;//match horizontal atas bawah
    public int condition;//udah dilaluin apa belum

    public Item(int value, int weight, int condition) {//konstrukttor untuk kelas item
        this.value = value;//untuk mengisi value 
        this.weight = weight;//untuk mengisi weight
        this.condition = condition; //untuk mengisi kondisi
    }
}
