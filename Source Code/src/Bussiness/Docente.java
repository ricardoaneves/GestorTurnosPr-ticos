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
public class Docente {
    
    private String nome;
    private String username;
    private String email;
    private String password;
    
    public Docente(){
        this.nome = "";
        this.username = "";
        this.email = "";
        this.password = "";
    }
    
    public Docente(String nome, String username, String email, String password){
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    public String getUsername(){
        return this.username;
    }
}
