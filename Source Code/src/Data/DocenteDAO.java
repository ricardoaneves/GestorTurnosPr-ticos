/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Bussiness.Docente;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author André
 */
public class DocenteDAO {
    
    ArrayList<String> arrayTurnoDocente = new ArrayList<>();
    private Random randomGenerator = new Random();
    
    public static Docente get(String username, String password){
        Docente d = null;
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Select * from Docente where username=? and password=?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String user = rs.getString("username");
                String pass = rs.getString("password");
                if(user.equals(username) && pass.equals(password)){
                    String email = rs.getString("email");
                    String nome = rs.getString("nome");
                    d = new Docente(nome,user,email,pass);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return d;
    }

    public static void put(String nome, String username, String email, String password) {
        Docente d = null;
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Insert into Docente (nome,username,email,password) values (?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1,nome);
            pst.setString(2,username);
            pst.setString(3,email);
            pst.setString(4,password);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    public void putUC(String username, String uc){
        Connection conn = null;
        int idDocente = 0;
        int flag = 1;
        int idUC = 0;
        int counter = 0;
        int idTurnoDocente = 0;
        String turnoEscolhido = "";
        try{ 
            conn = Connect.connect();
            //Busca de ID de UC
            String SqlIdUC = "Select uc.iduc From uc Where uc.nome = ?;";
            PreparedStatement pstIdUC = conn.prepareStatement(SqlIdUC);
            pstIdUC.setString(1,uc);
            ResultSet rsIdUC = pstIdUC.executeQuery();
            while(rsIdUC.next()){
                idUC = rsIdUC.getInt("uc.iduc");
            }
            //Busca de ID de Docente
            String SqlIdDocente = "Select docente.iddocente From docente Where docente.username = ?;";
            PreparedStatement pstIdDocente = conn.prepareStatement(SqlIdDocente);
            pstIdDocente.setString(1,username);
            ResultSet rsIdAluno = pstIdDocente.executeQuery();
            while(rsIdAluno.next()){
                idDocente = rsIdAluno.getInt("docente.iddocente");
            }
            //Buscar Nomes dos Turnos de Uma UC
            String SqlIDTurno = "Select Turno.nome From Turno JOIN uc on uc.idUC = turno.iduc Where uc.iduc = ?;";
            PreparedStatement pstIDTurno = conn.prepareStatement(SqlIDTurno);
            pstIDTurno.setInt(1,idUC);
            ResultSet rsIDTurno = pstIDTurno.executeQuery();
            while(rsIDTurno.next()){
                arrayTurnoDocente.add(rsIDTurno.getString("Turno.nome"));
            }

            while(flag == 1 && counter < 5){
                int index = randomGenerator.nextInt(arrayTurnoDocente.size());
                turnoEscolhido = arrayTurnoDocente.get(index);
                flag = turnoTemDocente(turnoEscolhido);
                counter++;
            }
            
            //Buscar o ID do turno com um certo nome
            String SqlNomeParaIDTurno = "Select Turno.idTurno From Turno Where Turno.nome = ?";
            PreparedStatement pstNomeParaIDTurno = conn.prepareStatement(SqlNomeParaIDTurno);
            pstNomeParaIDTurno.setString(1, turnoEscolhido);
            ResultSet rsNomeParaIDTurno = pstNomeParaIDTurno.executeQuery();
            while(rsNomeParaIDTurno.next()){
                idTurnoDocente = rsNomeParaIDTurno.getInt("Turno.idTurno");
            }
            //Inscrever o docente num turno com certo id
            String SqlInserirDocente = "UPDATE Turno SET idDocente = ? WHERE idTurno = ?";
            PreparedStatement pstInserirDocente =  conn.prepareStatement(SqlInserirDocente);
            pstInserirDocente.setInt(1,idDocente);
            pstInserirDocente.setInt(2,idTurnoDocente);
            pstInserirDocente.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    public int turnoTemDocente(String turnoEscolhido){
        Connection conn = null;
        int TemDocente = 0;
        
        try{
            conn = Connect.connect();
            String SqlTemDocente = "Select Turno.idDocente From Turno Where Turno.nome = ?;";
            PreparedStatement pstTemDocente = conn.prepareStatement(SqlTemDocente);        
            pstTemDocente.setString(1, turnoEscolhido);
            ResultSet rsTemDocente = pstTemDocente.executeQuery();
            while(rsTemDocente.next()){
                TemDocente = rsTemDocente.getInt("Turno.idDocente");
            }
            
            if(TemDocente == 1)
                return 0;
            
            return 1;
                
        } catch(Exception e){
            e.printStackTrace();
            return 0;
            
        } finally{
            Connect.close(conn);
        }
    }
    
    public String tiraTurno(String uc, String username){
        Connection conn = null;
        String Turno = "";
        
//        try{
//            conn = Connect.connect();
//            String SqlTurno = "Select t.nome from turno as t join docente as d on t.idDocente = d.idDocente join ";
//            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
//            pstTurno.setString(1,uc);
//            pstTurno.setString(2, username);
//            ResultSet rsTurno = pstTurno.executeQuery();
//            while(rsTurno.next()){
//                Turno = rsTurno.getString("t.nome");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            Connect.close(conn);
//        }  
        
        return Turno;
    }
    
    public String tiraDocenteResponsavel(String uc, String turno){
        Connection conn = null;
        String DocenteResponsavel = "";
        
        try{
            conn = Connect.connect();
            String SqlDocenteResponsavel = "Select d.nome from Docente as d join uc on uc.idDocenteResponsavel = d.idDocente Join Turno as t on t.idUC = uc.idUC where uc.nome = ? and t.nome = ?;";
            PreparedStatement pstDocenteResponsavel = conn.prepareStatement(SqlDocenteResponsavel);
            pstDocenteResponsavel.setString(1,uc);
            pstDocenteResponsavel.setString(2, turno);
            ResultSet rsDocenteResponsavel = pstDocenteResponsavel.executeQuery();
            while(rsDocenteResponsavel.next()){
                DocenteResponsavel = rsDocenteResponsavel.getString("d.nome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return DocenteResponsavel;
    }
    
    public String tiraSala(String uc, String turno){
        Connection conn = null;
        String Sala = "";
        
        try{
            conn = Connect.connect();
            String SqlSala = "Select s.nome from sala as s join turno as t on s.idSala = t.idSala join uc on uc.idUc = t.idUc where uc.nome = ? and t.nome = ?;";
            PreparedStatement pstSala = conn.prepareStatement(SqlSala);
            pstSala.setString(1,uc);
            pstSala.setString(2, turno);
            ResultSet rsSala = pstSala.executeQuery();
            while(rsSala.next()){
                Sala = rsSala.getString("s.nome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Sala;
    }
    
    public int tiraIDDocente(String username){
        Connection conn = null;
        int idDocente = 0;
        
        try{
            conn = Connect.connect();
            String SqlIdDocente = "Select idDocente From Docente Where username = ?";
            PreparedStatement pstIdDocente = conn.prepareStatement(SqlIdDocente);
            pstIdDocente.setString(1,username);
            ResultSet rsIdDocente = pstIdDocente.executeQuery();
            while(rsIdDocente.next()){
                idDocente = rsIdDocente.getInt("idDocente");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        return idDocente;
    }
    
    public void responsabilizaDocente(String uc, String username){
        Connection conn = null;
        int idDocente = tiraIDDocente(username);
        
        try{
            conn = Connect.connect();
            String SqlResponsabiliza = "UPDATE UC SET idDocenteResponsavel = ? WHERE nome = ?";
            PreparedStatement pstResponsabiliza =  conn.prepareStatement(SqlResponsabiliza);
            pstResponsabiliza.setInt(1, idDocente);
            pstResponsabiliza.setString(2, uc);
            pstResponsabiliza.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    
}
