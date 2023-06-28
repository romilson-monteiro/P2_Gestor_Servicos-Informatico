/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;
import java.io.Serializable;
/**
 *
 * @authors 
 * 28891 - Romilson Monteiro
 * 28915 - Ruben Da Luz
 */

public class Impressao extends Servico implements Serializable{ 
    private String impressora;
    public Impressao(String nome, int ID, Estados estado, String impressora){
        super(nome, ID, estado);
        this.impressora = impressora;
    }   
    // *********  Metodos  get/set para impressora ********* /
    public String getImpressora(){
         return this.impressora; 
    }
    public void setImpressora(String impressora){
        this.impressora = impressora;
    }
    public void ptintImp(){
        ptint();
        System.out.println("\t\tImpressora: " +this.impressora);
    }
    
}
