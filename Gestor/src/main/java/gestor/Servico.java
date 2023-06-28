/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import java.io.Serializable;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @authors 28891 - Romilson Monteiro 28915 - Ruben Da Luz
 */
public class Servico implements Serializable {
    
    private int numUtilizacao;
    private int ID;
    private String nome;

    Pedidos getPrimereiroPedido() {
      if (!this.pedidos.isEmpty()){
          return this.pedidos.element();
      }else {
          return null;
      }
      
    }

    void apagarPedido() {
      if(!this.pedidos.isEmpty()){
          this.pedidos.remove();
      }  
    }

    public enum Estados {
        DISPONIVEL, OCUPADO, INDISPONIVEL, ERRO
    }

    public enum Tipos {
        BACKUP, IMPRESSAO
    }
    private Estados estado;
    private Tipos tipo;
    private final Queue<Pedidos> pedidos = new LinkedList<>();

    public Servico(String nome, int ID, Estados estado) {
        this.nome = nome;
        this.ID = ID;
        this.estado = estado;
        this.numUtilizacao = 0;
    }

    // *********  Metodos  get/set nome ********* /
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // *********  Metodos  get/set para ID ********* /
    public int getID() {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    
    // *********  Metodos  get/set para ID ********* /
    public int getNumUtilizacao() {
        return this.numUtilizacao;
    }
    public void setNumUtilizacao() {
        this.numUtilizacao++;
    }
    
    // *********  Metodos  get/set para Estados  ********* /
    public Estados getEstado() {
        return this.estado;
    }
    public void setEstados(Estados estado) {
        Estados estadoAnterior = this.estado;
        this.estado = estado;
        logEstadoServico(estadoAnterior);
    }

    // *********  Metodos  get/set para tipo ********* /
    public Tipos getTipo() {
        return this.tipo;
    }
    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    // *********  Metodos  get/set para Pedidos ********* /
    public Queue<Pedidos> getPedidos() {
        return this.pedidos;
    } 
    public void addPedido(Pedidos pedido) {
        this.pedidos.add(pedido);
    }

    
    
    // Metudos para inprimir os objectos criado apartir dessa classe
    public void ptint() {
        System.out.println("\t # " + this.nome + "[" + this.estado + "]");
        System.out.println("\t\tID : " + this.ID);
    }
    
    
    public void printQueue() {
        for (Pedidos pedido : pedidos) {
            pedido.print();
        }
    }
    
    // metudo para gravar a mudanca de estado num ficheiro de log
    public void logEstadoServico( Estados estadoAnterior) {
        LocalDateTime dataHora = LocalDateTime.now();
        String logLine = dataHora + " - Servico: " + this.nome + " - ID: " + this.ID + " - Estado anterior: " + estadoAnterior + " - Estado atual: " + this.estado;

        try {
            FileWriter fw = new FileWriter("servico_log.txt", true); //true para adicionar ao ficheiro em vez de sobrescrever o ficheiro
            fw.write(logLine + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no ficheiro de log: " + e);
        }
    }
}
