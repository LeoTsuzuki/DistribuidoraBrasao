package br.com.distribuidoraBrasao.ctr;

import java.sql.ResultSet;
import br.com.distribuidoraBrasao.dto.ProdutoDTO;
import br.com.distribuidoraBrasao.dao.ProdutoDAO;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;

public class ProdutoCTR {

    ProdutoDAO produtoDAO = new ProdutoDAO();

    public ProdutoCTR() {
    }

    public String inserirProduto(ProdutoDTO produtoDTO) {
        try {
            if (produtoDAO.inserirProduto(produtoDTO)) {
                return "Produto Cadastrado com Sucesso!!!";
            } else {
                return "Produto NÃO Cadastrado!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Cadastrado!!!";
        }
    }

    public String alterarProduto(ProdutoDTO produtoDTO) {
        try {
            if (produtoDAO.alterarProduto(produtoDTO)) {
                return "Produto Alterado com Sucesso!!!";
            } else {
                return "Produto NÃO Alterado!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Alterado!!!";
        }
    }

    public String excluirProduto(ProdutoDTO produtoDTO) {
        try {
            if (produtoDAO.excluirProduto(produtoDTO)) {
                return "Produto Excluído com Sucesso!!!";
            } else {
                return "Produto NÃO Excluído!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Excluído!!!";
        }
    }

    public ResultSet consultarProduto(ProdutoDTO produtoDTO, int opcao) {
        ResultSet rs = null;
        rs = produtoDAO.consultarProduto(produtoDTO, opcao);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

}