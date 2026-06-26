package br.com.distribuidoraBrasao.ctr;

import java.sql.ResultSet;
import br.com.distribuidoraBrasao.dto.EntradaEstoqueDTO;
import br.com.distribuidoraBrasao.dao.EntradaEstoqueDAO;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;

public class EntradaEstoqueCTR {

    EntradaEstoqueDAO entradaEstoqueDAO = new EntradaEstoqueDAO();

    public EntradaEstoqueCTR() {
    }

    public String inserirEntradaEstoque(EntradaEstoqueDTO entradaEstoqueDTO) {
        try {
            if (entradaEstoqueDAO.inserirEntradaEstoque(entradaEstoqueDTO)) {
                return "Entrada de Estoque Cadastrada com Sucesso!!!";
            } else {
                return "Entrada de Estoque NÃO Cadastrada!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Entrada de Estoque NÃO Cadastrada!!!";
        }
    }

    public String excluirEntradaEstoque(EntradaEstoqueDTO entradaEstoqueDTO) {
        try {
            if (entradaEstoqueDAO.excluirEntradaEstoque(entradaEstoqueDTO)) {
                return "Entrada de Estoque Excluída com Sucesso!!!";
            } else {
                return "Entrada de Estoque NÃO Excluída!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Entrada de Estoque NÃO Excluída!!!";
        }
    }

    public ResultSet consultarEntradaEstoque(EntradaEstoqueDTO entradaEstoqueDTO, int opcao) {
        ResultSet rs = null;
        rs = entradaEstoqueDAO.consultarEntradaEstoque(entradaEstoqueDTO, opcao);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

}