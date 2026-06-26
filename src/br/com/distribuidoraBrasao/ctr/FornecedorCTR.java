package br.com.distribuidoraBrasao.ctr;

import java.sql.ResultSet;
import br.com.distribuidoraBrasao.dto.FornecedorDTO;
import br.com.distribuidoraBrasao.dao.FornecedorDAO;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;

public class FornecedorCTR {

    FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public FornecedorCTR() {
    }

    public String inserirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            if (fornecedorDAO.inserirFornecedor(fornecedorDTO)) {
                return "Fornecedor Cadastrado com Sucesso!!!";
            } else {
                return "Fornecedor NÃO Cadastrado!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO Cadastrado!!!";
        }
    }

    public String alterarFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            if (fornecedorDAO.alterarFornecedor(fornecedorDTO)) {
                return "Fornecedor Alterado com Sucesso!!!";
            } else {
                return "Fornecedor NÃO Alterado!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO Alterado!!!";
        }
    }

    public String excluirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            if (fornecedorDAO.excluirFornecedor(fornecedorDTO)) {
                return "Fornecedor Excluído com Sucesso!!!";
            } else {
                return "Fornecedor NÃO Excluído!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO Excluído!!!";
        }
    }

    public ResultSet consultarFornecedor(FornecedorDTO fornecedorDTO, int opcao) {
        ResultSet rs = null;
        rs = fornecedorDAO.consultarFornecedor(fornecedorDTO, opcao);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

}