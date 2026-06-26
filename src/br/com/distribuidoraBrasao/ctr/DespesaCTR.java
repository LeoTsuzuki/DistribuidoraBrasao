package br.com.distribuidoraBrasao.ctr;

import java.sql.ResultSet;
import br.com.distribuidoraBrasao.dto.DespesaDTO;
import br.com.distribuidoraBrasao.dao.DespesaDAO;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;

public class DespesaCTR {
    
    DespesaDAO despesaDAO = new DespesaDAO();

    public DespesaCTR() {}

    public String inserirDespesa(DespesaDTO despesaDTO) {
        try {
            if (despesaDAO.inserirDespesa(despesaDTO)) {
                return "Despesa Cadastrada com Sucesso!!!";
            } else {
                return "Despesa NÃO Cadastrada!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Despesa NÃO Cadastrada!!!";
        }
    }

    public String alterarDespesa(DespesaDTO despesaDTO) {
        try {
            if (despesaDAO.alterarDespesa(despesaDTO)) {
                return "Despesa Alterada com Sucesso!!!";
            } else {
                return "Despesa NÃO Alterada!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Despesa NÃO Alterada!!!";
        }
    }

    public String excluirDespesa(DespesaDTO despesaDTO) {
        try {
            if (despesaDAO.excluirDespesa(despesaDTO)) {
                return "Despesa Excluída com Sucesso!!!";
            } else {
                return "Despesa NÃO Excluída!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Despesa NÃO Excluída!!!";
        }
    }

    public ResultSet consultarDespesa(DespesaDTO despesaDTO, int opcao) {
        ResultSet rs = null;
        rs = despesaDAO.consultarDespesa(despesaDTO, opcao);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}
