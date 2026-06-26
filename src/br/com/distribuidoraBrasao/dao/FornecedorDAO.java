package br.com.distribuidoraBrasao.dao;

import java.sql.*;
import br.com.distribuidoraBrasao.dto.FornecedorDTO;

public class FornecedorDAO {
    
    public FornecedorDAO(){}
    
    private ResultSet rs = null;
    private Statement stmt = null;
    
    public boolean inserirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
           
            ConexaoDAO.ConnectDB();
            
            stmt = ConexaoDAO.con.createStatement();
      
            String comando = "INSERT INTO fornecedor (nome_for, cnpj_for, tel_for, email_for, "
                    + "cidade_for, estado_for, rua_for, bairro_for, numero_for, cep_for, obs_for) VALUES ("
                    + "'" + fornecedorDTO.getNome_for() + "', "
                    + "'" + fornecedorDTO.getCnpj_for() + "', "
                    + "'" + fornecedorDTO.getTel_for() + "', "
                    + "'" + fornecedorDTO.getEmail_for() + "', "
                    + "'" + fornecedorDTO.getCidade_for() + "', "
                    + "'" + fornecedorDTO.getEstado_for() + "', "
                    + "'" + fornecedorDTO.getRua_for() + "', "
                    + "'" + fornecedorDTO.getBairro_for() + "', "
                    + "'" + fornecedorDTO.getNumero_for() + "', "
                    + "'" + fornecedorDTO.getCep_for() + "', "
                    + "'" + fornecedorDTO.getObs_for() + "')";
       

            stmt.execute(comando.toUpperCase());            
            ConexaoDAO.con.commit();         
            stmt.close();
            return true;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } 
        finally {  
            ConexaoDAO.CloseDB();
        }
    }
    public boolean alterarFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
            String comando = "UPDATE fornecedor SET "
                    + "nome_for = '" + fornecedorDTO.getNome_for() + "', "
                    + "cnpj_for = '" + fornecedorDTO.getCnpj_for() + "', "
                    + "tel_for = '" + fornecedorDTO.getTel_for() + "', "
                    + "numero_for = '" + fornecedorDTO.getNumero_for() + "', "
                    + "bairro_for = '" + fornecedorDTO.getBairro_for() + "', "
                    + "rua_for = '" + fornecedorDTO.getRua_for() + "', "
                    + "cep_for = '" + fornecedorDTO.getCep_for() + "', "
                    + "email_for = '" + fornecedorDTO.getEmail_for() + "', "
                    + "cidade_for = '" + fornecedorDTO.getCidade_for() + "', "
                    + "estado_for = '" + fornecedorDTO.getEstado_for() + "', "
                    + "obs_for = '" + fornecedorDTO.getObs_for() + "' "
                    + "WHERE id_for = " + fornecedorDTO.getId_for();
                       
            stmt.execute(comando.toUpperCase());
            ConexaoDAO.con.commit();
            stmt.close();
            return true;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } 
        finally {  
            ConexaoDAO.CloseDB();
        }
    }//Fecha o método alterarCliente
    
    
    
    public boolean excluirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
           
            ConexaoDAO.ConnectDB(); 
            stmt = ConexaoDAO.con.createStatement();
            String comando = "DELETE FROM fornecedor WHERE id_for = "
                             + fornecedorDTO.getId_for();

         
            stmt.execute(comando);
           
            ConexaoDAO.con.commit();
         
            stmt.close();
            return true;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        finally {         
            ConexaoDAO.CloseDB();
        }
    }//Fecha o método excluirCliente
    
        
    public ResultSet consultarFornecedor(FornecedorDTO fornecedorDTO, int opcao) {
        try {
            
            ConexaoDAO.ConnectDB();
            
            stmt = ConexaoDAO.con.createStatement();
            
            String comando = "";
            switch (opcao){
                case 1:
                    comando = "Select f.* "+
                              "from fornecedor f "+
                              "where nome_for ilike '" + fornecedorDTO.getNome_for() + "%' " +
                              "order by f.nome_for";    
                break;
                case 2:
                    comando = "Select f.* "+
                              "from fornecedor f " +
                              "where f.id_for = " + fornecedorDTO.getId_for();
                break;
                case 3:
                    comando = "Select f.id_for, f.nome_for "+
                              "from fornecedor f ";
                break;
                
            }
           
            rs = stmt.executeQuery(comando);
            return rs;
        } 
        catch (Exception e) {
            System.out.println("ERRO DETALHADO: " + e.getMessage());
            e.printStackTrace();
            return rs;
        }
    }//Fecha o método consultarFornecedor
}