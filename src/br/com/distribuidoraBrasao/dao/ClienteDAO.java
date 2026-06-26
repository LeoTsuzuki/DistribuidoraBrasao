
package br.com.distribuidoraBrasao.dao;

import java.sql.*;
import br.com.distribuidoraBrasao.dto.ClienteDTO;

public class ClienteDAO {
    
    public ClienteDAO() {}
    
    private ResultSet rs = null;
    private Statement stmt = null;
    
    public boolean inserirCliente(ClienteDTO clienteDTO) {
        try {
           
            ConexaoDAO.ConnectDB();
            
            stmt = ConexaoDAO.con.createStatement();
      
            String comando = "INSERT INTO cliente (nome_cli, cpf_cli, cnpj_cli, responsavel_cli, "
                    + "tipo_cli, tel_cli, email_cli, cep_cli, rua_cli, numero_cli, "
                    + "bairro_cli, cidade_cli, estado_cli) VALUES ("
                    + "'" + clienteDTO.getNome_cli() + "', "
                    + "'" + clienteDTO.getCpf_cli() + "', "
                    + "'" + clienteDTO.getCnpj_cli() + "', "
                    + "'" + clienteDTO.getResponsavel_cli() + "', "
                    + "'" + clienteDTO.getTipo_cli() + "', "
                    + "'" + clienteDTO.getTel_cli() + "', "
                    + "'" + clienteDTO.getEmail_cli() + "', "
                    + "'" + clienteDTO.getCep_cli() + "', "
                    + "'" + clienteDTO.getRua_cli() + "', "
                    + "'" + clienteDTO.getNumero_cli() + "', "
                    + "'" + clienteDTO.getBairro_cli() + "', "
                    + "'" + clienteDTO.getCidade_cli() + "', "
                    + "'" + clienteDTO.getEstado_cli() + "')";
            
       

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
    public boolean alterarCliente(ClienteDTO clienteDTO) {
        try {
            
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
            String comando = "UPDATE cliente SET "
                    + "nome_cli = '" + clienteDTO.getNome_cli() + "', "
                    + "cpf_cli = '" + clienteDTO.getCpf_cli() + "', "
                    + "cnpj_cli = '" + clienteDTO.getCnpj_cli() + "', "
                    + "responsavel_cli = '" + clienteDTO.getResponsavel_cli() + "', "
                    + "tipo_cli = '" + clienteDTO.getTipo_cli() + "', "
                    + "tel_cli = '" + clienteDTO.getTel_cli() + "', "
                    + "email_cli = '" + clienteDTO.getEmail_cli() + "', "
                    + "cep_cli = '" + clienteDTO.getCep_cli() + "', "
                    + "rua_cli = '" + clienteDTO.getRua_cli() + "', "
                    + "numero_cli = '" + clienteDTO.getNumero_cli() + "', "
                    + "bairro_cli = '" + clienteDTO.getBairro_cli() + "', "
                    + "cidade_cli = '" + clienteDTO.getCidade_cli() + "', "
                    + "estado_cli = '" + clienteDTO.getEstado_cli() + "' "
                    + "WHERE id_cli = " + clienteDTO.getId_cli();
                       
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
    
    
    
    public boolean excluirCliente(ClienteDTO clienteDTO) {
        try {
           
            ConexaoDAO.ConnectDB(); 
            stmt = ConexaoDAO.con.createStatement();
            String comando = "DELETE FROM cliente WHERE id_cli = " + clienteDTO.getId_cli();     

         
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
    
        
    public ResultSet consultarCliente(ClienteDTO clienteDTO, int opcao) {
        try {
            
            ConexaoDAO.ConnectDB();
            
            stmt = ConexaoDAO.con.createStatement();
            
            String comando = "";
            switch (opcao){
                case 1:
                    comando = "Select c.* "+
                              "from cliente c "+
                              "where nome_cli like '" + clienteDTO.getNome_cli()+ "%' " +
                              "order by c.nome_cli";    
                break;
                case 2:
                    comando = "Select c.* "+
                              "from cliente c " +
                              "where c.id_cli = " + clienteDTO.getId_cli();
                break;
                case 3:
                    comando = "Select c.id_cli, c.nome_cli "+
                              "from cliente c ";
                break;
                
            }
           
            rs = stmt.executeQuery(comando.toUpperCase());
            return rs;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }//Fecha o método consultarCliente    
}
