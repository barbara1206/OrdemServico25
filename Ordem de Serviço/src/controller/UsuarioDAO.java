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
    public void efetuaLogin(String email, String senha ) {
       
        try {

            //1 passo - SQL
            String sql = "select * from tbusuarios where usuario = ? and senha = ?";
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //Usuario logou
                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);
                
            } else {
                //Dados incorretos
                JOptionPane.showMessageDialog(null, "Dados incorretos!");
                new TelaLogin().setVisible(true);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro : " + erro);
        }

    }
    public void adicionarUsuario(Usuario obj){
        
        try{
            
            String sql = "Insert into tbusuarios(iduser, usuario, fone, login, senha, perfil) values(?,?,?,?,?)";
            
            conexao = ModuloConexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,  obj.getIdUser());
            stmt.setString(2, obj.getUsuario());
            stmt.setString(3, obj.getFone());
            stmt.setString(4, obj.getLogin());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getPerfil());
            
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            
        }
        catch(SQLIntegrityConstraintViolationException e1){
            JOptionPane.showMessageDialog(null, "Login em uso. \n escolha outro login.");
        }
        
        catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                conexao.close();
            }
            catch (SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
}
