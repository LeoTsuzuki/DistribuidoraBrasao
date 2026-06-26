package br.com.distribuidoraBrasao.dao;

import br.com.distribuidoraBrasao.dto.EntradaEstoqueDTO;
import java.sql.*;

public class EntradaEstoqueDAO {

    public EntradaEstoqueDAO() {}

    private ResultSet rs = null;
    private Statement stmt = null;

    public boolean inserirEntradaEstoque(EntradaEstoqueDTO entradaEstoqueDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "INSERT INTO entrada_estoque (dat_ent, qtd_ent, id_prod) VALUES ("
                    + "'" + entradaEstoqueDTO.getDat_ent() + "', "
                    + entradaEstoqueDTO.getQtd_ent() + ", "
                    + entradaEstoqueDTO.getId_prod() + ")";

            stmt.execute(comando);

            String comandoEstoque = "UPDATE produto SET estoque_prod = estoque_prod + " + entradaEstoqueDTO.getQtd_ent()
                    + " WHERE id_prod = " + entradaEstoqueDTO.getId_prod();
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

    public boolean excluirEntradaEstoque(EntradaEstoqueDTO entradaEstoqueDTO) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comandoEstoque = "UPDATE produto SET estoque_prod = estoque_prod - " + entradaEstoqueDTO.getQtd_ent()
                    + " WHERE id_prod = " + entradaEstoqueDTO.getId_prod();
            stmt.execute(comandoEstoque);

            String comando = "DELETE FROM entrada_estoque WHERE id_ent = " + entradaEstoqueDTO.getId_ent();
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

    public ResultSet consultarEntradaEstoque(EntradaEstoqueDTO entradaEstoqueDTO, int opcao) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();

            String comando = "";
            switch (opcao) {
                case 1:
                    comando = "SELECT e.*, p.nome_prod FROM entrada_estoque e "
                            + "INNER JOIN produto p ON e.id_prod = p.id_prod "
                            + "WHERE p.nome_prod ILIKE '" + entradaEstoqueDTO.getNome_prod() + "%' "
                            + "ORDER BY e.dat_ent DESC";
                    break;
                case 2:
                    comando = "SELECT e.*, p.nome_prod FROM entrada_estoque e "
                            + "INNER JOIN produto p ON e.id_prod = p.id_prod "
                            + "WHERE e.id_ent = " + entradaEstoqueDTO.getId_ent();
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