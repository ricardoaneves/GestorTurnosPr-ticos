/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author André
 */
public class UCDAO {
   
    ArrayList<String> ucsInscrito = new ArrayList<>();
    ArrayList<String> ucPorInscrever = new ArrayList<>();
    ArrayList<String> ucsInscritoDocente = new ArrayList<>();
    ArrayList<String> ucPorInscreverDocente = new ArrayList<>();
    
    
    //Aluno
    
    public ArrayList<String> alunoGetUC(String username){
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Select uc.nome from uc JOIN Turno as t on t.iduc = uc.iduc JOIN aluno_has_turno as aht on aht.idturno = t.idturno JOIN aluno as a on a.idaluno = aht.idaluno Where a.username = ?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("uc.nome");
                ucsInscrito.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ucsInscrito;
    }
    
    public ArrayList<String> ucPorInscrever(){
        Connection conn = null;
        try{
            conn= Connect.connect();
            String Sql = "SELECT distinct uc.nome FROM uc;";
            PreparedStatement pst = conn.prepareStatement(Sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("uc.nome");
                ucPorInscrever.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ucPorInscrever;
    }
    
    //Docente
    
    public ArrayList<String> docenteGetUC(String username){
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Select distinct uc.nome from uc JOIN Turno as t on t.iduc = uc.iduc JOIN docente as d on d.idDocente = t.idDocente Where d.username = ?;";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("uc.nome");
                ucsInscritoDocente.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ucsInscritoDocente;
    }
    
    public ArrayList<String> docenteGetUCLogin(String username){
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Select distinct t.nome from Turno as t join docente as d on d.idDocente = t.idDocente Where d.username = ?;";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("t.nome");
                ucsInscritoDocente.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ucsInscritoDocente;
    }
    
    public String infoUC(String turno){
        Connection conn = null;
        String res = "";
        try{
            conn = Connect.connect();
            String SqlUC = "Select uc.nome from uc join turno as t on t.idUC = uc.iduc where t.nome = ?";
            PreparedStatement pst = conn.prepareStatement(SqlUC);
            pst.setString(1, turno);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                res = rs.getString("uc.nome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return res;
            
    }
 
}
