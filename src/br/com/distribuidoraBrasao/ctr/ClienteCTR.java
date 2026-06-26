package br.com.distribuidoraBrasao.ctr;

import java.sql.ResultSet;
import br.com.distribuidoraBrasao.dto.ClienteDTO;
import br.com.distribuidoraBrasao.dao.ClienteDAO;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;

public class ClienteCTR {

    ClienteDAO clienteDAO = new ClienteDAO();

    public ClienteCTR() {
    }

    public String inserirCliente(ClienteDTO clienteDTO) {
        try {
            if (clienteDAO.inserirCliente(clienteDTO)) {
                return "Cliente Cadastrado com Sucesso!!!";
            } else {
                return "Cliente NÃO Cadastrado!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente NÃO Cadastrado!!!";
        }
    }

    public String alterarCliente(ClienteDTO clienteDTO) {
        try {
            if (clienteDAO.alterarCliente(clienteDTO)) {
                return "Cliente Alterado com Sucesso!!!";
            } else {
                return "Cliente NÃO Alterado!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente NÃO Alterado!!!";
        }
    }

    public String excluirCliente(ClienteDTO clienteDTO) {
        try {
            if (clienteDAO.excluirCliente(clienteDTO)) {
                return "Cliente Excluído com Sucesso!!!";
            } else {
                return "Cliente NÃO Excluído!!!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente NÃO Excluído!!!";
        }
    }

    public ResultSet consultarCliente(ClienteDTO clienteDTO, int opcao) {
        ResultSet rs = null;
        rs = clienteDAO.consultarCliente(clienteDTO, opcao);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

}