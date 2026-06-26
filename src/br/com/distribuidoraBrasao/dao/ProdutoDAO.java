
package br.com.distribuidoraBrasao.dao;

import br.com.distribuidoraBrasao.dto.ProdutoDTO;
import java.sql.*;

public class ProdutoDAO {
    
    public ProdutoDAO(){}
    
    private ResultSet rs = null;
    private Statement stmt = null;
    
    public boolean inserirProduto(ProdutoDTO produtoDTO) {
        try {
           
            ConexaoDAO.ConnectDB();
            
            stmt = ConexaoDAO.con.createStatement();
      
            String comando = "INSERT INTO produto (nome_prod, tipo_prod, peso_prod, "
                    + "p_custo_prod, p_venda_prod, estoque_prod) VALUES ("
                    + "'" + produtoDTO.getNome_prod() + "', "
                    + "'" + produtoDTO.getTipo_prod() + "', "
                    + "'" + produtoDTO.getPeso_prod() + "', "
                    + produtoDTO.getP_custo_prod() + ", "
                    + produtoDTO.getP_venda_prod() + ", "
                    + produtoDTO.getEstoque_prod() + ")";
       

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
    public boolean alterarProduto(ProdutoDTO produtoDTO) {
        try {
            
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
            String comando = "UPDATE produto SET "
                    + "nome_prod = '" + produtoDTO.getNome_prod() + "', "
                    + "tipo_prod = '" + produtoDTO.getTipo_prod() + "', "
                    + "peso_prod = '" + produtoDTO.getPeso_prod() + "', "
                    + "p_custo_prod = " + produtoDTO.getP_custo_prod() + ", "
                    + "p_venda_prod = " + produtoDTO.getP_venda_prod() + ", "
                    + "estoque_prod = " + produtoDTO.getEstoque_prod() + " "
                    + "WHERE id_prod = " + produtoDTO.getId_prod();
                       
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
    }//Fecha o método alterarProduto
    
    
    
    public boolean excluirProduto(ProdutoDTO produtoDTO) {
        try {
           
            ConexaoDAO.ConnectDB(); 
            stmt = ConexaoDAO.con.createStatement();
            String comando = "DELETE FROM produto WHERE id_prod = " + produtoDTO.getId_prod();
      
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
    }//Fecha o método excluirProduto
    
        
    public ResultSet consultarProduto(ProdutoDTO produtoDTO, int opcao) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "";
            switch (opcao) {
                case 1:
                    comando = "SELECT * FROM produto "
                            + "WHERE nome_prod ILIKE '" + produtoDTO.getNome_prod() + "%' "
                            + "ORDER BY nome_prod";
                    break;
                case 2:
                    comando = "SELECT * FROM produto "
                            + "WHERE id_prod = " + produtoDTO.getId_prod();
                    break;
                case 3:
                    comando = "SELECT id_prod, nome_prod FROM produto "
                            + "ORDER BY nome_prod";
                    break;
            }

            rs = stmt.executeQuery(comando);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }//fecha metodo consultar produto
    
    public void atualizarEstoque(int id_prod, int qtd) {
        try {
            ConexaoDAO.ConnectDB();
            
            stmt = ConexaoDAO.con.createStatement();           
            
            String comando = "UPDATE produto SET estoque_prod = estoque_prod + " + qtd
                    + " WHERE id_prod = " + id_prod;

            
            stmt.execute(comando);
           
            ConexaoDAO.con.commit();
         
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar estoque: " + e.getMessage());
        }
    }
}
