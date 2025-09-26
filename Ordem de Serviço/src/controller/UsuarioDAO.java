/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.HeadlessException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import jdbc.ModuloConexao;
import model.Usuario;
import view.TelaLogin;
import view.TelaPrincipal;

/**
 *
 * @author clebe
 */
public class UsuarioDAO {
    
    private Connection conexao;
    
    public UsuarioDAO(){
        this.conexao = ModuloConexao.conectar();
    }
    
    //Metodo efetuaLogin
    public void efetuaLogin(String usuario, String senha ) {
       
        try {

            //1 passo - SQL
            String sql = "select * from tbusuarios where usuario = ? and senha = ?";
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //Usuario logou
                String perfil = rs.getString(6);
                if(perfil.equals("admin")){
                    TelaPrincipal tela = new TelaPrincipal();
                    tela.setVisible(true);
                    tela.jMnItmUsuario.setEnabled(true);
                    tela.jMnRelatorio.setEnabled(true);
                }else{
                    TelaPrincipal tela = new TelaPrincipal();
                    tela.setVisible(true);
                }
                
            } else {
                //Dados incorretos
                JOptionPane.showMessageDialog(null, "Dados incorretos!");
                new TelaLogin().setVisible(true);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro : " + erro);
        }
      
    }
    
  public void adicionarUsuario(Usuario obj) {
      
      try{
         String sql = "insert into Uusarios(iduser, usuario, fone, login, senha, perfil)  values (?,?,?,?,?)";
         
         conexao = ModuloConexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         stmt.setInt(1, obj.getIdUser());
         stmt.setString(2, obj.getUsuario());
        stmt.setString(3, obj.getFone());
        stmt.setString(4, obj.getLogin());
        stmt.setString(5, obj.getSenha());
        stmt.setString(6, obj.getPerfil());

        stmt.execute();
        stmt.clone();
        JOptionPane.showMessageDialog(null, "Usuario casdastrado com sucesso");
      
      }
      catch(SQLIntegrityConstrainViolationException el){
          JOptionPane.showmenssadeDialog(null, "login em uso, \nEscolha outro login.");
      }
      Catch (HeadLessException | SQLException e ) {
       JOptionPane.showmenssadeDialog(null, e);
      }
      finally {
            try{ 
              conexao.clone();
           }catch (SQLException ex){
              JOptionPane.showmenssadeDialog(null, ex);
            }     
       }
   }
 
}


         