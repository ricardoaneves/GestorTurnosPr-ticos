/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Bussiness.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author André
 */
public class TurnoDAO {
    
    ArrayList<String> turnos = new ArrayList<>();
    
    public ArrayList<String> putUC(String username,String uc){
        Connection conn = null;
        int idUC = 0;
        int idAluno = 0;
        try{
            conn = Connect.connect();
            //Buscar ID da UC
            String SqlIdUC = "Select uc.iduc From uc Where uc.nome = ?;";
            PreparedStatement pstIdUC = conn.prepareStatement(SqlIdUC);
            pstIdUC.setString(1,uc);
            ResultSet rsIdUC = pstIdUC.executeQuery();
            while(rsIdUC.next()){
                idUC = rsIdUC.getInt("uc.iduc");
            }
            //Busca de ID de Aluno
            String SqlIdAluno = "Select aluno.idaluno From aluno Where aluno.username = ?;";
            PreparedStatement pstIdAluno = conn.prepareStatement(SqlIdAluno);
            pstIdAluno.setString(1,username);
            ResultSet rsIdAluno = pstIdAluno.executeQuery();
            while(rsIdAluno.next()){
                idAluno = rsIdAluno.getInt("aluno.idaluno");
            }
            //Buscar Nomes dos Turnos da UC
            String SqlNomeTurnos = "Select distinct t.nome From turno as t JOIN uc on uc.iduc = t.iduc Where uc.iduc = ?;";
            PreparedStatement pst = conn.prepareStatement(SqlNomeTurnos);
            pst.setInt(1,idUC);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                turnos.add(rs.getString("t.nome"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        return turnos;
    }
    
    public int verCapacidade(String turno){
        Connection conn = null;
        int capacidade = 0;
        try{
            conn = Connect.connect();
            String SqlCapacidade = "Select turno.capacidade from turno where turno.nome = ?";
            PreparedStatement pstCapacidade = conn.prepareStatement(SqlCapacidade);
            pstCapacidade.setString(1, turno);
            ResultSet rsCapacidade = pstCapacidade.executeQuery();
            while(rsCapacidade.next()){
                capacidade = rsCapacidade.getInt("turno.capacidade");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return capacidade;
    }
    
    public int verNrAlunos(int turno){
        Connection conn = null;
        int numero = 0;
        
        try{
            conn = Connect.connect();
            String Sql = "Select count(idAluno) from aluno_has_turno where idTurno = ?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setInt(1, turno);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                numero = rs.getInt("COUNT(idAluno)");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return numero;
    }
    
    public int verIDTurno(String turno){
        Connection conn = null;
        int Turno = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select turno.idTurno from turno where turno.nome = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,turno);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                Turno = rsTurno.getInt("turno.idTurno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Turno;
    }
    
    public ArrayList<String> poeTurnos(String username){
        Connection conn = null;
        ArrayList<String> turnos = new ArrayList<>();
        try{
            conn = Connect.connect();
            String Sql = "Select t.nome from turno as t order by t.nome";
            PreparedStatement pst = conn.prepareStatement(Sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("t.nome");
                turnos.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return turnos;
    }
    
    public ArrayList<String> getAlunosTurno(String turno){
        Connection conn = null;
        ArrayList<String> alunos = new ArrayList<>();
        try{
            conn = Connect.connect();
            String Sql = "Select a.nome from aluno as a join aluno_has_turno as aht on a.idAluno = aht.idAluno join turno as t on t.idTurno = aht.idTurno where t.nome = ?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1, turno);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("a.nome");
                alunos.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return alunos;
    }
    
    public int getNumeroFaltas(String nomeAluno, String turno){
        Connection conn = null;
        int nrFaltas = 0;
        
        try{
            conn = Connect.connect();
            String Sql = "Select aht.faltas from aluno_has_turno as aht join turno as t on t.idTurno = aht.idTurno join aluno as a on a.idAluno = aht.idAluno where t.nome = ? and a.nome = ?;";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setString(1, turno);
            pst.setString(2, nomeAluno);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                nrFaltas = rs.getInt("aht.faltas");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return nrFaltas;
    }
    
    public void marcaFalta(int faltas, int idAluno, int idTurno){
        Connection conn = null;
        
        try{
            conn = Connect.connect();
            String Sql = "Update aluno_has_turno set faltas = ? where idAluno = ? and idTurno = ?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setInt(1, faltas);
            pst.setInt(2, idAluno);
            pst.setInt(3, idTurno);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    public void registaAvaliacao(int nota, int idAluno, int idTurno){
        Connection conn = null;
        
        try{
            conn = Connect.connect();
            String Sql = "Update aluno_has_turno set avaliacao = ? where idAluno = ? and idTurno = ?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setInt(1, nota);
            pst.setInt(2, idAluno);
            pst.setInt(3, idTurno);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    public int getAvaliacao(String nome, int idTurno){
        Connection conn = null;
        int res = 0;
        
        try{
            conn = Connect.connect();
            String Sql = "Select aht.avaliacao from aluno_has_turno as aht join turno as t on t.idTurno = aht.idTurno join aluno as a on a.idAluno = aht.idAluno where t.idTurno = ? and a.nome = ?;";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setInt(1, idTurno);
            pst.setString(2, nome);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                res = rs.getInt("aht.avaliacao");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return res;
    }
    
    public void atualizaCapacidade(int novaCapacidade, int idTurno){
        Connection conn = null;
        
        try{
            conn = Connect.connect();
            String Sql = "Update turno set capacidade = ? where idTurno = ?";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setInt(1, novaCapacidade);
            pst.setInt(2, idTurno);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
}
