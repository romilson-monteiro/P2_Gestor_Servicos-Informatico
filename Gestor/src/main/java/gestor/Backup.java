/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;
import java.io.Serializable;

/**
 *C:\Program Files\Java\jdk-19\lib\src.zip;java.base/
 * @authors 
 * 28891 - Romilson Monteiro
 * 28915 - Ruben Da Luz
 */
public class Backup extends Servico implements Serializable{
    private int capacidade;
    public Backup(String nome, int ID, Estados estado, int capacidade){
        super(nome, ID, estado);
        this.capacidade = capacidade;
    }   
    // *********  Metodos  get/set para capacidade ********* /
    public int getCapacidade(){
         return this.capacidade; 
    }
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }
    
    public void ptintCap(){
        ptint();
        System.out.println("\t\tCapacidade: " +this.capacidade +"GB");
    }
}

