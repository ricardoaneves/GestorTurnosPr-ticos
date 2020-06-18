/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

/**
 *
 * @author André
 */
public class Turno {
    
    private String nome;
    private int capacidade;
    private int idSala;
    private int idUC;
    private int idDocente;
    
    public Turno(){
        this.nome = "";
        this.capacidade = 0;
        this.idSala = 0;
        this.idUC = 0;
        this.idDocente = 0;
    }
    
    public Turno(String nome, int capacidade, int idSala, int idUC, int idDocente){
        this.nome = nome;
        this.capacidade = capacidade;
        this.idSala = idSala;
        this.idUC = idUC;
        this.idDocente = idDocente;
    }
}
