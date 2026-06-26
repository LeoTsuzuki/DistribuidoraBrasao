package br.com.distribuidoraBrasao.dao;

import java.sql.*;

public class ItemVendaDAO {

    public ItemVendaDAO() {}

    private ResultSet rs = null;
    private Statement stmt = null;

    public boolean inserirItemVenda(int id_vend, int id_prod, int qtd_item, double val_unit_item, double val_custo_item) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "INSERT INTO item_venda (id_vend, id_prod, qtd_item, val_unit_item, val_custo_item) VALUES ("
                    + id_vend + ", "
                    + id_prod + ", "
                    + qtd_item + ", "
                    + val_unit_item + ", "
                    + val_custo_item + ")";

            stmt.execute(comando);

            String comandoEstoque = "UPDATE produto SET estoque_prod = estoque_prod - " + qtd_item
                    + " WHERE id_prod = " + id_prod;
            stmt.execute(comandoEstoque);

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

    public boolean excluirItensPorVenda(int id_vend) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "DELETE FROM item_venda WHERE id_vend = " + id_vend;

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

    public ResultSet consultarItensPorVenda(int id_vend) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "SELECT i.*, p.nome_prod, p.p_venda_prod, p.p_custo_prod "
                    + "FROM item_venda i "
                    + "INNER JOIN produto p ON i.id_prod = p.id_prod "
                    + "WHERE i.id_vend = " + id_vend;

            rs = stmt.executeQuery(comando);
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }
}