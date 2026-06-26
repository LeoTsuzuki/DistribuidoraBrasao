package br.com.distribuidoraBrasao.ctr;

import java.sql.*;
import br.com.distribuidoraBrasao.dao.ConexaoDAO;
import java.io.InputStream;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class RelatorioCTR {
    
    public RelatorioCTR(){}
    
    public JFrame abrirRelatorio(String relatorio, String titulo, Map parametros){
        
        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("br/com/avaliacao2_leonardo_tsuzuki/rels/" + relatorio);
            
            if (inputStream == null) {
                System.out.println("ARQUIVO NAO ENCONTRADO: br/com/avaliacao2_leonardo_tsuzuki/rels/" + relatorio);
                return null;
            }
            
            System.out.println("Arquivo encontrado, tentando preencher relatorio...");
            
            JasperPrint print = JasperFillManager.fillReport(inputStream, parametros,
                    getConexao() );
            
            CloseDB();
            
            JRViewer viewer = new JRViewer(print);
            
            JFrame frameRelatorio = new JFrame (titulo);
            
            frameRelatorio.add(viewer);
            
            frameRelatorio.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            frameRelatorio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            return frameRelatorio;
        }
        catch(Exception e){
            System.out.println("Entrou erro metodo abrirRelatorio");
            System.out.println(e.getMessage());
            e.printStackTrace();
            
            return null;
        }   
    }
    
    public Connection getConexao(){
        return ConexaoDAO.ConnectDBRels();
    }
    
    public void CloseDB(){
        ConexaoDAO.CloseDB();
    }
}