/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;
import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 *
 * @authors 
 * 28891 - Romilson Monteiro
 * 28915 - Ruben Da Luz
 */
public class Pedidos implements Serializable {
    int ID;
   JSONObject descricao = new JSONObject();
   
   public void print() {
        System.out.println("ID: " + ID);
        System.out.println("Descrição: " + descricao);
   }

   public Pedidos(int ID, JSONObject descricao){
        this.ID = ID;
        this.descricao = descricao;
    }

 // *********  Metudos para ID ********* /
    public int getID(){
         return this.ID; 
    }
    public void setID(int ID){
        this.ID = ID;
    }
    // *********  Metudos para ID ********* /
    public JSONObject getDescricao(){
         return this.descricao; 
    }
    public void setID(JSONObject descricao){
        this.descricao = descricao;
    }
}