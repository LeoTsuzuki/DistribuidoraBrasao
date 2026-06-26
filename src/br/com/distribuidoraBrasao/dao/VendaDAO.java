package br.com.distribuidoraBrasao.dao;

import br.com.distribuidoraBrasao.dto.VendaDTO;
import java.sql.*;

public class VendaDAO {
    
    public VendaDAO(){}
    
    private ResultSet rs = null;
    private Statement stmt = null;

    public boolean inserirVenda(VendaDTO vendaDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "INSERT INTO venda (dat_vend, val_total_vend, val_custo_vend, "
                    + "forma_pag_vend, status_vend, obs_vend, id_cli) VALUES ("
                    + "'" + vendaDTO.getDat_vend() + "', "
                    + vendaDTO.getVal_total_vend() + ", "
                    + vendaDTO.getVal_custo_vend() + ", "
                    + "'" + vendaDTO.getForma_pag_vend() + "', "
                    + "'" + vendaDTO.getStatus_vend() + "', "
                    + "'" + vendaDTO.getObs_vend() + "', "
                    + vendaDTO.getId_cli() + ")";

            stmt.execute(comando); // CORRIGIDO: removido .toUpperCase() que corrompía os valores
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

    public boolean alterarVenda(VendaDTO vendaDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "UPDATE venda SET "
                    + "dat_vend = '" + vendaDTO.getDat_vend() + "', "
                    + "val_total_vend = " + vendaDTO.getVal_total_vend() + ", "
                    + "val_custo_vend = " + vendaDTO.getVal_custo_vend() + ", "
                    + "forma_pag_vend = '" + vendaDTO.getForma_pag_vend() + "', "
                    + "status_vend = '" + vendaDTO.getStatus_vend() + "', "
                    + "obs_vend = '" + vendaDTO.getObs_vend() + "', "
                    + "id_cli = " + vendaDTO.getId_cli() + " "
                    + "WHERE id_vend = " + vendaDTO.getId_vend();

            stmt.execute(comando); // CORRIGIDO: removido .toUpperCase() que corrompía os valores
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

    public boolean excluirVenda(VendaDTO vendaDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "DELETE FROM item_venda WHERE id_vend = " + vendaDTO.getId_vend();
            stmt.execute(comando);

            comando = "DELETE FROM venda WHERE id_vend = " + vendaDTO.getId_vend();
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
    
    public ResultSet consultarVenda(VendaDTO vendaDTO, int opcao) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "";
            switch (opcao) {
                case 1: // Buscar vendas pelo nome do cliente
                    comando = "SELECT v.*, c.nome_cli FROM venda v "
                            + "INNER JOIN cliente c ON v.id_cli = c.id_cli "
                            + "WHERE c.nome_cli ILIKE '" + vendaDTO.getNome_cli() + "%' "
                            + "ORDER BY v.dat_vend DESC";
                    break;
                case 2: // Buscar venda pelo ID
                    comando = "SELECT v.*, c.nome_cli FROM venda v "
                            + "INNER JOIN cliente c ON v.id_cli = c.id_cli "
                            + "WHERE v.id_vend = " + vendaDTO.getId_vend();
                    break;
            }

            rs = stmt.executeQuery(comando);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }
    
    public int consultarUltimoId() {
        int id = 0;
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "SELECT MAX(id_vend) AS id_vend FROM venda";

            rs = stmt.executeQuery(comando);
            if (rs.next()) {
                id = rs.getInt("id_vend");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConexaoDAO.CloseDB();
        }
        return id;
    }
    
    
}
