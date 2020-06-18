/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import Data.AlunoDAO;
import Data.DocenteDAO;
import Data.TurnoDAO;
import Data.UCDAO;
import java.util.ArrayList;

/**
 *
 * @author André
 */

public class Facade {
    
    private Aluno alunoLog;
    private Docente docenteLog;
    public String auxTurno;
    
    public Facade(){
        this.alunoLog = new Aluno();
        this.docenteLog = new Docente();
    }
    
    public int login(String username, String password){
        int auth = -1;
        char c = username.charAt(0);
        if(c == 'a'){
            Aluno a = AlunoDAO.get(username, password);
            if(a != null){
                auth = 0;
                this.alunoLog = a;
            }
        }else if(c == 'd'){
            Docente d = DocenteDAO.get(username,password);
            if(d != null){
                auth = 1;
                this.docenteLog = d;
            }
        }
        return auth;
    }
    
    public void registaUtilizador(String nome, String username, String email, String password, String tipoUtilizador){
        if(tipoUtilizador.equals("Aluno")){
            AlunoDAO.put(nome,username,email,password);
        }else if(tipoUtilizador.equals("Docente")){
            DocenteDAO.put(nome,username,email,password);
        }
    }
    
    // Aluno
    
    public int estaInscrito(String uc){
        int res = 0;
        AlunoDAO adao = new AlunoDAO();
        res = adao.estaInscrito(uc, alunoLog.getUsername());
        return res;
    }
    
    public int podeTrocar(String uc, String trocador){
        int pode=0;
        AlunoDAO adao = new AlunoDAO();
        pode = adao.podeTrocar(uc, trocador);
        return pode;
    }
    
    public ArrayList<String> addUCAluno(){
        ArrayList<String> arrayUC = new ArrayList<>();
        UCDAO ucdao = new UCDAO();
        arrayUC = ucdao.alunoGetUC(alunoLog.getUsername());
        return arrayUC;
    }
    
    public ArrayList<String> addUCPorInscrever(){
        ArrayList<String> arrayUCPorInscrever = new ArrayList<>();
        UCDAO ucdao = new UCDAO();
        arrayUCPorInscrever = ucdao.ucPorInscrever();
        return arrayUCPorInscrever;
    }
    
    public void addTurnos(String uc){
        AlunoDAO adao = new AlunoDAO();
        adao.putUC(alunoLog.getUsername(),uc);
    }
    
//    public String UCSelecionada(String uc){
//        return uc;
//    }
    
    public String infoTurno(String uc){
        AlunoDAO adao = new AlunoDAO();
        String turno = adao.tiraTurno(uc, alunoLog.getUsername());
        return turno;
    }
    
    public String infoDocente(String uc, String turno){
        AlunoDAO adao = new AlunoDAO();
        String docente = adao.tiraDocente(uc, turno);
        return docente;
    }
    
    public String infoDocenteResponsavel(String uc, String turno){
        AlunoDAO adao = new AlunoDAO();
        String docenteResponsavel = adao.tiraDocenteResponsavel(uc, turno);
        return docenteResponsavel;
    }
    
    public String infoSala(String uc, String turno){
        AlunoDAO adao = new AlunoDAO();
        String sala = adao.tiraSala(uc, turno);
        return sala;
    }
    
    public ArrayList<String> addAlunosCB(String uc){
        ArrayList<String> res = new ArrayList<>();
        AlunoDAO adao = new AlunoDAO();
        String username = alunoLog.getUsername();
        res = adao.todosAlunos(uc, username);
        return res;
    }
    
    public void trocaTurnos(String uc, String nomeTrocador){
        AlunoDAO adao = new AlunoDAO();
        String username = alunoLog.getUsername();
        adao.trocaTurnos(uc, nomeTrocador, username);
    }
    
    public int verificaCapacidade(String turno){
        int capacidade;
        TurnoDAO tdao = new TurnoDAO();
        capacidade = tdao.verCapacidade(turno);
        return capacidade;
    }
    
    public int verificaNrAlunos(String turno){
        TurnoDAO tdao = new TurnoDAO();
        int idTurno = tdao.verIDTurno(turno);
        int numero = tdao.verNrAlunos(idTurno);
        return numero;
    }
    
    public ArrayList<String> addTurnosCB(){
        TurnoDAO tdao = new TurnoDAO();
        ArrayList<String> array = new ArrayList<String>();
        array = tdao.poeTurnos(alunoLog.getUsername());
        return array;
    }
    
    public void anula(String s){
        AlunoDAO adao = new AlunoDAO();
        String turno = adao.tiraTurno(s, alunoLog.getUsername());
        adao.anula(turno, alunoLog.getUsername());
    }
    
    //Docente
    
    public ArrayList<String> addUCDocente(){
        ArrayList<String> arrayUC = new ArrayList<>();
        UCDAO ucdao = new UCDAO();
        arrayUC = ucdao.docenteGetUC(docenteLog.getUsername());
        return arrayUC;
    }
    
    public ArrayList<String> addUCDocenteLogin(){
        ArrayList<String> arrayTurnos = new ArrayList<>();
        UCDAO ucdao = new UCDAO();
        arrayTurnos = ucdao.docenteGetUCLogin(docenteLog.getUsername());
        return arrayTurnos;
    }
    
    public ArrayList<String> addUCPorInscreverDocente(){
        ArrayList<String> arrayUCPorInscreverDocente = new ArrayList<>();
        UCDAO ucdao = new UCDAO();
        arrayUCPorInscreverDocente = ucdao.ucPorInscrever();
        return arrayUCPorInscreverDocente;
    }
    
    public void putRegistaDocente(String uc){
        DocenteDAO ddao = new DocenteDAO();
        ddao.putUC(docenteLog.getUsername(), uc);
    }
    
//    public String infoTurnoDocente(String uc){
//        AlunoDAO adao = new AlunoDAO();
//        String turno = adao.tiraTurno(uc, docenteLog.getUsername());
//        return turno;
//    }
//    
//    public String infoDocenteResponsavelDocente(String uc, String turno){
//        AlunoDAO adao = new AlunoDAO();
//        String docenteResponsavel = adao.tiraDocenteResponsavel(uc, turno);
//        return docenteResponsavel;
//    }
//    
//    public String infoSalaDocente(String uc, String turno){
//        AlunoDAO adao = new AlunoDAO();
//        String sala = adao.tiraSala(uc, turno);
//        return sala;
//    }
    
    public void docenteResponsavel(String uc){
        DocenteDAO ddao = new DocenteDAO();
        ddao.responsabilizaDocente(uc, docenteLog.getUsername());
    }
    
    public String infoUC(String turno){
        UCDAO ucdao = new UCDAO();
        String uc = ucdao.infoUC(turno);
        return uc;
    }
    
    public void atualizaAuxTurno(String turno){
        auxTurno = turno;
    }
    
    public String buscaAuxTurno(){
        return auxTurno;
    }
    
    public ArrayList<String> alunosTurno(String turno){
        ArrayList<String> alunos = new ArrayList<>();
        TurnoDAO tdao = new TurnoDAO();
        alunos = tdao.getAlunosTurno(turno);
        return alunos;
    }
    
    public void marcaFalta(String nomeAluno){
        TurnoDAO tdao = new TurnoDAO();
        AlunoDAO adao = new AlunoDAO();
        int numeroFaltas = tdao.getNumeroFaltas(nomeAluno, auxTurno);
        numeroFaltas++;
        int idAluno = adao.tiraIDAluno(nomeAluno);
        int idTurno = tdao.verIDTurno(auxTurno);
        tdao.marcaFalta(numeroFaltas, idAluno, idTurno);
    }
    
    public int getNrFaltas(String nomeAluno){
        TurnoDAO tdao = new TurnoDAO();
        AlunoDAO adao = new AlunoDAO();
        int numeroFaltas = tdao.getNumeroFaltas(nomeAluno, auxTurno);
        return numeroFaltas;
    }
    
    public void registarAvaliacao(String nomeAluno, int novaNota){
        TurnoDAO tdao = new TurnoDAO();
        AlunoDAO adao = new AlunoDAO();
        int idAluno = adao.tiraIDAluno(nomeAluno);
        int idTurno = tdao.verIDTurno(auxTurno);
        tdao.registaAvaliacao(novaNota, idAluno, idTurno);    
    }
    
    public int getAvaliacao(String nomeAluno){
        int nota;
        TurnoDAO tdao = new TurnoDAO();
        int idTurno = tdao.verIDTurno(auxTurno);
        nota = tdao.getAvaliacao(nomeAluno, idTurno);
        return nota;
    }
    
    public void atualizaCapacidade(int novaCapacidade){
        TurnoDAO tdao = new TurnoDAO();
        int idTurno = tdao.verIDTurno(auxTurno);
        tdao.atualizaCapacidade(novaCapacidade, idTurno);
    }
}
