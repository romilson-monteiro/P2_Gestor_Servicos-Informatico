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
public class Utilizador implements Serializable {
   private String nome, nif,email,password,departamento,cargo;
   private int numCC; 
   private Tipos tipo;
   public enum Tipos {
        ADIMINISTRADOR,
        CONSUMIDOR
   }
   public void print(){
       System.out.println("\n# "+ this.nome +"\t[ "+ this.tipo +" ]");
       System.out.println("\tNo. CC       : " + this.numCC);
       System.out.println("\tNif          : " + this.nif);
       System.out.println("\tDepartamento : " + this.departamento);
       System.out.println("\tCargo        : " + this.cargo);
       System.out.println("\tEmail        : " + this.email );

   }

   public Utilizador(String nome,String nif, String email, String password, Tipos tipo, String departamento, String cargo, int numCC){
       this.nome = nome;
       this.nif = nif;
       this.email = email;
       this.numCC = numCC;
       this.tipo = tipo;
       this.password = password;
       this.cargo = cargo;
       this.departamento = departamento;
       
   }
   
    // *********  Metodos  get/set para nome ********* /
    public String getNome(){
         return this.nome; 
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    // *********  Metodos  get/set para email ********* /
    public String getEmail(){
         return this.email; 
    }
    public void setEmail(String email){
        this.email = email;
    }
    // *********  Metodos  get/set para Password ********* /
    public String getPassword(){
         return this.password; 
    }
    public void setPassword(String password){
        this.password = password;
    }
    //  *********  Metodos  get/set numero CC ********* /
    public int getCC(){
         return this.numCC; 
    }
    public void setCC(int numCC){
        this.numCC = numCC;
    }
    
    // *********  Metodos  get/set para tipo ********* /
    public Tipos getTipo(){
         return this.tipo; 
    }
    public void setTipo(Tipos tipo){
        this.tipo = tipo;
    }
    //  ********* Metodos  get/set para nif ********* /
   public void setNif(String nif){
        this.nif = nif;
    }
    public String getNif(){
        return this.nif;
    }
    //  ********* Metodos  get/set para Departamento ********* /
    public void setDepartamento(String departamento){
        this.departamento = departamento;
    }
    public String getDepartamento(){
        return this.departamento;
    }
    //  ********* Metodos  get/set para Cargo ********* /
    public void setCargo(String cargo){
        this.cargo = cargo;
    }
    public String getCargo(){
        return this.cargo;
    }
}