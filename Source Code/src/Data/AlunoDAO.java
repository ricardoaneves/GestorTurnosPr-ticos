/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Bussiness.Aluno;
import java.util.Random;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author André
 */
public class AlunoDAO {
    
    private Random randomGenerator = new Random();
    ArrayList<String> arrayTurno = new ArrayList<>(); 
    String turnoEscolhido;
    
    public static Aluno get(String username, String password){
        Aluno al = null;
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Select * from Aluno where username=? and password=?;";
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
                    al = new Aluno(nome,user,email,pass);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        return al;
    }
 
    public static void put(String nome, String username, String email, String password) {
        Aluno al = null;
        Connection conn = null;
        try{
            conn = Connect.connect();
            String Sql = "Insert into aluno (nome,username,email,password, estatutoEspecial) values (?,?,?,?, 'Não tem');";
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
    
    public int estaInscrito(String uc, String username){
        Connection conn = null;
        int res = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "SELECT 1 FROM uc join aluno as a join aluno_has_turno as aht on aht.idAluno = a.idAluno join turno as t on t.idTurno = aht.idTurno and uc.idUC = t.idUC WHERE a.username = ? and uc.nome = ?;";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,username);
            pstTurno.setString(2, uc);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                res = rsTurno.getInt("1");
            }
            if (res == 1)
                return 1;
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return 1;
    }
    
    public int podeTrocar(String uc, String nome){
        Connection conn = null;
        int res = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "SELECT 1 FROM uc join aluno as a join aluno_has_turno as aht on aht.idAluno = a.idAluno join turno as t on t.idTurno = aht.idTurno and uc.idUC = t.idUC WHERE a.nome = ? and uc.nome = ?;";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,nome);
            pstTurno.setString(2, uc);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                res = rsTurno.getInt("1");
            }
            if (res == 1)
                return 1;
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return 0;
    }
    
    public void putUC(String username, String uc){
        Connection conn = null;
        int idAluno = 0;
        int idUC = 0;
        int idTurno = 0;
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
            //Busca de ID de Aluno
            String SqlIdAluno = "Select aluno.idaluno From aluno Where aluno.username = ?;";
            PreparedStatement pstIdAluno = conn.prepareStatement(SqlIdAluno);
            pstIdAluno.setString(1,username);
            ResultSet rsIdAluno = pstIdAluno.executeQuery();
            while(rsIdAluno.next()){
                idAluno = rsIdAluno.getInt("aluno.idaluno");
            }
            //Buscar Nomes dos Turnos de Uma UC
            String SqlIDTurno = "Select Turno.nome From Turno JOIN uc on uc.idUC = turno.iduc Where uc.iduc = ?;";
            PreparedStatement pstIDTurno = conn.prepareStatement(SqlIDTurno);
            pstIDTurno.setInt(1,idUC);
            ResultSet rsIDTurno = pstIDTurno.executeQuery();
            while(rsIDTurno.next()){
                arrayTurno.add(rsIDTurno.getString("Turno.nome"));
            }
            int index = randomGenerator.nextInt(arrayTurno.size());
            String turnoEscolhido = arrayTurno.get(index);
            
            //Buscar o ID do turno com um certo nome
            String SqlNomeParaIDTurno = "Select Turno.idTurno From Turno Where Turno.nome = ?;";
            PreparedStatement pstNomeParaIDTurno = conn.prepareStatement(SqlNomeParaIDTurno);
            pstNomeParaIDTurno.setString(1,turnoEscolhido);
            ResultSet rsNomeParaIDTurno = pstNomeParaIDTurno.executeQuery();
            while(rsNomeParaIDTurno.next()){
                idTurno = rsNomeParaIDTurno.getInt("Turno.idTurno");
            }
            //Inscrever o aluno num turno com certo id
            String SqlInserirAluno = "Insert Into aluno_has_turno Values (?,?, 0, 0);";
            PreparedStatement pstInserirAluno =  conn.prepareStatement(SqlInserirAluno);
            pstInserirAluno.setInt(1,idAluno);
            pstInserirAluno.setInt(2,idTurno);
            pstInserirAluno.executeUpdate();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    public String tiraTurno(String uc, String username){
        Connection conn = null;
        String Turno = "";
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select t.nome From Turno as t Join UC on t.idUC = uc.iduc Join aluno_has_turno as aht on aht.idTurno = t.idTurno Join aluno as a on a.idAluno = aht.idAluno Where uc.nome=? and a.username = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,uc);
            pstTurno.setString(2, username);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                Turno = rsTurno.getString("t.nome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Turno;
    }
    
    public String tiraDocente(String uc, String turno){
        Connection conn = null;
        String Docente = "";
        
        try{
            conn = Connect.connect();
            String SqlDocente = "Select d.nome From Docente as d Join Turno as t on d.idDocente = t.idDocente Join uc on t.idUC = uc.idUc Where uc.nome = ? and t.nome = ?";
            PreparedStatement pstDocente = conn.prepareStatement(SqlDocente);
            pstDocente.setString(1,uc);
            pstDocente.setString(2, turno);
            ResultSet rsDocente = pstDocente.executeQuery();
            while(rsDocente.next()){
                Docente = rsDocente.getString("d.nome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Docente;
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
    
    public ArrayList<String> todosAlunos(String uc, String username){
        Connection conn = null;
        ArrayList<String> alunos = new ArrayList<>();
        int idTurno;
        
        try{
//            idTurno = excluiTurno(uc, username);
//            conn = Connect.connect();
//            String SqlAlunos = "Select a.nome from aluno as a join alunos_has_turno as aht on a.idAluno = aht.idAluno join turno as t on t.idTurno != aht.idTurno join uc on uc.idUc = t.idUc where uc.nome = ? and t.idTurno = ?";
//            PreparedStatement pstAlunos = conn.prepareStatement(SqlAlunos);
//            pstAlunos.setInt(2, idTurno);
//            pstAlunos.setString(1, uc);
            conn = Connect.connect();
            String SqlAlunos = "Select a.nome from aluno as a where a.username != ?"; 
            PreparedStatement pstAlunos = conn.prepareStatement(SqlAlunos);
            pstAlunos.setString(1, username);
            ResultSet rsAlunos = pstAlunos.executeQuery();
            while(rsAlunos.next()){
                alunos.add(rsAlunos.getString("a.nome"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
        return alunos;
    }
    
//    public int excluiTurno(String uc, String username){
//        Connection conn = null;
//        int res = 0;
//        
//        try{
//            conn = Connect.connect();
//            String SqlTurno = "Select t.idTurno from turno as t join aluno_has_turno as aht on aht.idTurno = t.idTurno join aluno as a on aht.idAluno = a.idAluno join UC on uc.idUc = t.idUc where uc.nome = ? and a.username = ?";
//            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
//            pstTurno.setString(1,uc);
//            pstTurno.setString(2, username);
//            ResultSet rsTurno = pstTurno.executeQuery();
//            while(rsTurno.next()){
//                res = rsTurno.getInt("t.idTurno");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            Connect.close(conn);
//        }
//        
//        return res;
//    }
    
    public void trocaTurnos(String uc, String nomeTrocador, String username){
        Connection conn = null;
        
        int turnoUtilizador = tiraTurnoUtilizador(uc, username);
        int turnoTrocador = tiraTurnoTrocador(uc, nomeTrocador);
        int idUtilizador = tiraIDUtilizador(username);
        int idTrocador = tiraIDTrocador(nomeTrocador);
        
        try{
            conn = Connect.connect();
            String SqlTroca1 = "UPDATE aluno_has_turno SET idTurno = ? WHERE idTurno = ? and idAluno = ?";
            PreparedStatement pstTroca = conn.prepareStatement(SqlTroca1);
            pstTroca.setInt(1,turnoTrocador);
            pstTroca.setInt(2, turnoUtilizador);
            pstTroca.setInt(3, idUtilizador);
            pstTroca.executeUpdate();
            
            String SqlTroca2 = "UPDATE aluno_has_turno SET idTurno = ? WHERE idTurno = ? and idAluno = ?";
            PreparedStatement pstTroca2 = conn.prepareStatement(SqlTroca2);
            pstTroca2.setInt(1,turnoUtilizador);
            pstTroca2.setInt(2, turnoTrocador);
            pstTroca2.setInt(3, idTrocador);
            pstTroca2.executeUpdate();            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
        
    }
    
    public int tiraTurnoTrocador(String uc, String nomeTrocador){
        Connection conn = null;
        int Turno = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select t.idTurno From Turno as t Join UC on t.idUC = uc.iduc Join aluno_has_turno as aht on aht.idTurno = t.idTurno Join aluno as a on a.idAluno = aht.idAluno Where uc.nome=? and a.nome = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,uc);
            pstTurno.setString(2, nomeTrocador);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                Turno = rsTurno.getInt("t.idTurno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Turno;
    }
    
    public int tiraTurnoUtilizador(String uc, String username){
        Connection conn = null;
        int Turno = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select t.idTurno From Turno as t Join UC on t.idUC = uc.iduc Join aluno_has_turno as aht on aht.idTurno = t.idTurno Join aluno as a on a.idAluno = aht.idAluno Where uc.nome=? and a.username = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,uc);
            pstTurno.setString(2, username);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                Turno = rsTurno.getInt("t.idTurno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Turno;
    }
    
    public int tiraTurnoUtilizador2(String turno, String username){
        Connection conn = null;
        int Turno = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select t.idTurno From Turno as t Join aluno_has_turno as aht on aht.idTurno = t.idTurno Join aluno as a on a.idAluno = aht.idAluno Where t.nome=? and a.username = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,turno);
            pstTurno.setString(2, username);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                Turno = rsTurno.getInt("t.idTurno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return Turno;
    }
    
    public int tiraIDUtilizador(String username){
        Connection conn = null;
        int id = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select aluno.idAluno from aluno where aluno.username = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,username);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                id = rsTurno.getInt("aluno.idAluno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return id;
    }
    
    public int tiraIDTrocador(String nomeTrocador){
        Connection conn = null;
        int id = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select aluno.idAluno from aluno where aluno.nome = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,nomeTrocador);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                id = rsTurno.getInt("aluno.idAluno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return id;
    }
    
    public void anula(String turno, String username){
        Connection conn = null;
        int idTurno = tiraTurnoUtilizador2(turno, username);
        //System.out.println(idTurno);
        int idUtilizador = tiraIDUtilizador(username);
        //System.out.println(idUtilizador);
        
        try{
            conn = Connect.connect();
            String Sql = "Delete from aluno_has_turno where idAluno = ? and idTurno = ?;";
            PreparedStatement pst = conn.prepareStatement(Sql);
            pst.setInt(1, idUtilizador);
            pst.setInt(2, idTurno);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }
    }
    
    public int tiraIDAluno(String nome){
        Connection conn = null;
        int id = 0;
        
        try{
            conn = Connect.connect();
            String SqlTurno = "Select aluno.idAluno from aluno where aluno.nome = ?";
            PreparedStatement pstTurno = conn.prepareStatement(SqlTurno);
            pstTurno.setString(1,nome);
            ResultSet rsTurno = pstTurno.executeQuery();
            while(rsTurno.next()){
                id = rsTurno.getInt("aluno.idAluno");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Connect.close(conn);
        }  
        
        return id;
    }
}
