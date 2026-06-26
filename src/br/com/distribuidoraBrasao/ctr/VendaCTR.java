package br.com.distribuidoraBrasao.ctr;

import java.sql.ResultSet;
import br.com.distribuidoraBrasao.dto.VendaDTO;
import br.com.distribuidoraBrasao.dao.VendaDAO;
import br.com.distribuidoraBrasao.dao.ItemVendaDAO;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;

public class VendaCTR {

    VendaDAO vendaDAO = new VendaDAO();
    ItemVendaDAO itemVendaDAO = new ItemVendaDAO();

    public VendaCTR() {
    }

    public String inserirVenda(VendaDTO vendaDTO) {
        try {
            if (vendaDAO.inserirVenda(vendaDTO)) {
                return "Venda Cadastrada com Sucesso!!!";
            } else {
                return "Venda NÃO Cadastrada!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Venda NÃO Cadastrada!!!";
        }
    }

    public String alterarVenda(VendaDTO vendaDTO) {
        try {
            if (vendaDAO.alterarVenda(vendaDTO)) {
                return "Venda Alterada com Sucesso!!!";
            } else {
                return "Venda NÃO Alterada!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Venda NÃO Alterada!!!";
        }
    }

    public String excluirVenda(VendaDTO vendaDTO) {
        try {
            if (vendaDAO.excluirVenda(vendaDTO)) {
                return "Venda Excluída com Sucesso!!!";
            } else {
                return "Venda NÃO Excluída!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Venda NÃO Excluída!!!";
        }
    }

    public ResultSet consultarVenda(VendaDTO vendaDTO, int opcao) {
        ResultSet rs = null;
        rs = vendaDAO.consultarVenda(vendaDTO, opcao);
        return rs;
    }

    public String excluirItensPorVenda(int id_vend) {
        try {
            if (itemVendaDAO.excluirItensPorVenda(id_vend)) {
                return "Itens Excluídos com Sucesso!!!";
            } else {
                return "Itens NÃO Excluídos!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Itens NÃO Excluídos!!!";
        }
    }

    public ResultSet consultarItensPorVenda(int id_vend) {
        return itemVendaDAO.consultarItensPorVenda(id_vend);
    }
    
    public int consultarUltimoId() {
    return vendaDAO.consultarUltimoId();
    }
    
    public String inserirItemVenda(int id_vend, int id_prod, int qtd_item, double val_unit_item, double val_custo_item) {
    try {
        if (itemVendaDAO.inserirItemVenda(id_vend, id_prod, qtd_item, val_unit_item, val_custo_item)) {
            return "Item Adicionado com Sucesso!!!";
        } else {
            return "Item NÃO Adicionado!!!";
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return "Item NÃO Adicionado!!!";
    }
}

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

}