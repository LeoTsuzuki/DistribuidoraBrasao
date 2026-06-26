
package br.com.distribuidoraBrasao.dao;

import java.sql.*;
import br.com.distribuidoraBrasao.dto.DespesaDTO;

public class DespesaDAO {
    
    public DespesaDAO() {}

    private ResultSet rs = null;
    private Statement stmt = null;

    public boolean inserirDespesa(DespesaDTO despesaDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
 
            String idFor = despesaDTO.getId_for() == 0 ? "NULL" : String.valueOf(despesaDTO.getId_for());
 
            String comando = "INSERT INTO despesa (descricao_desp, categoria_desp, dat_lancamento_desp, "
                    + "dat_vencimento_desp, val_desp, status_desp, obs_desp, id_for) VALUES ("
                    + "'" + despesaDTO.getDescricao_desp() + "', "
                    + "'" + despesaDTO.getCategoria_desp() + "', "
                    + "'" + despesaDTO.getDat_lancamento_desp() + "', "
                    + "'" + despesaDTO.getDat_vencimento_desp() + "', "
                    + despesaDTO.getVal_desp() + ", "
                    + "'" + despesaDTO.getStatus_desp() + "', "
                    + "'" + despesaDTO.getObs_desp() + "', "
                    + idFor + ")";
 
            stmt.execute(comando.toUpperCase());
            ConexaoDAO.con.commit();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }
 
    public boolean alterarDespesa(DespesaDTO despesaDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
 
            String idFor = despesaDTO.getId_for() == 0 ? "NULL" : String.valueOf(despesaDTO.getId_for());
 
            String comando = "UPDATE despesa SET "
                    + "descricao_desp = '" + despesaDTO.getDescricao_desp() + "', "
                    + "categoria_desp = '" + despesaDTO.getCategoria_desp() + "', "
                    + "dat_lancamento_desp = '" + despesaDTO.getDat_lancamento_desp() + "', "
                    + "dat_vencimento_desp = '" + despesaDTO.getDat_vencimento_desp() + "', "
                    + "val_desp = " + despesaDTO.getVal_desp() + ", "
                    + "status_desp = '" + despesaDTO.getStatus_desp() + "', "
                    + "obs_desp = '" + despesaDTO.getObs_desp() + "', "
                    + "id_for = " + idFor + " "
                    + "WHERE id_desp = " + despesaDTO.getId_desp();
 
            stmt.execute(comando.toUpperCase());
            ConexaoDAO.con.commit();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }
 
    public boolean excluirDespesa(DespesaDTO despesaDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
 
            String comando = "DELETE FROM despesa WHERE id_desp = " + despesaDTO.getId_desp();
 
            stmt.execute(comando);
            ConexaoDAO.con.commit();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }
 
    public ResultSet consultarDespesa(DespesaDTO despesaDTO, int opcao) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
 
            String comando = "";
            switch (opcao) {
                case 1:
                    // Consulta por NOME (descricao_desp)
                    comando = "SELECT d.*, f.nome_for FROM despesa d "
                            + "LEFT JOIN fornecedor f ON d.id_for = f.id_for "
                            + "WHERE UPPER(d.descricao_desp) LIKE UPPER('" + despesaDTO.getDescricao_desp() + "%') "
                            + "ORDER BY d.descricao_desp";
                    break;
                case 2:
                    // Consulta por ID (para carregar dados na tela)
                    comando = "SELECT d.*, f.nome_for FROM despesa d "
                            + "LEFT JOIN fornecedor f ON d.id_for = f.id_for "
                            + "WHERE d.id_desp = " + despesaDTO.getId_desp();
                    break;
                case 3:
                    // Consulta por STATUS (EM ABERTO ou PAGO)
                    comando = "SELECT d.*, f.nome_for FROM despesa d "
                            + "LEFT JOIN fornecedor f ON d.id_for = f.id_for "
                            + "WHERE UPPER(d.status_desp) = UPPER('" + despesaDTO.getStatus_desp() + "') "
                            + "ORDER BY d.dat_vencimento_desp";
                    break;
                case 4:
                    String mesAno = despesaDTO.getDat_vencimento_desp();
                    String mes = mesAno.substring(0, 2);
                    String ano = mesAno.substring(3, 7);
                    comando = "SELECT d.*, f.nome_for FROM despesa d "
                            + "LEFT JOIN fornecedor f ON d.id_for = f.id_for "
                            + "WHERE EXTRACT(MONTH FROM d.dat_vencimento_desp) = " + mes + " "
                            + "AND EXTRACT(YEAR FROM d.dat_vencimento_desp) = " + ano + " "
                            + "ORDER BY d.dat_vencimento_desp";
                    break;
                case 5:
                    comando = "SELECT d.*, f.nome_for FROM despesa d "
                            + "LEFT JOIN fornecedor f ON d.id_for = f.id_for "
                            + "ORDER BY d.dat_vencimento_desp ASC";
                    break;
            }
 
            rs = stmt.executeQuery(comando);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }
}
