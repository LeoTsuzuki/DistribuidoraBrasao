package br.com.distribuidoraBrasao.view;


import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.util.Map;
import java.util.HashMap;
import br.com.distribuidoraBrasao.ctr.RelatorioCTR;


public class PrincipalVIEW extends javax.swing.JFrame {
    
    Map parametros = new HashMap(); 

    
    public PrincipalVIEW() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/carvao.png")));
        this.setLocationRelativeTo(null);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                desktopPane.repaint();
            }
        });
    }

    
  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        ImageIcon imageicon = new ImageIcon(getClass().getResource("imagens/fundo.png"));
        Image image = imageicon.getImage();

        desktopPane = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics graphics){
                graphics.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }

        ;
        menuBar = new javax.swing.JMenuBar();
        menuCadastro = new javax.swing.JMenu();
        itemMenuCliente = new javax.swing.JMenuItem();
        itemMenuFornecedor = new javax.swing.JMenuItem();
        itemMenuProduto = new javax.swing.JMenuItem();
        itemMenuDespesa = new javax.swing.JMenuItem();
        menuVenda = new javax.swing.JMenu();
        itemMenuVenda = new javax.swing.JMenuItem();
        ItemMenuEstoque = new javax.swing.JMenuItem();
        menuRelatorios = new javax.swing.JMenu();
        itemMenuRelatorioVendas = new javax.swing.JMenuItem();
        itemMenuRelatorioDespesas = new javax.swing.JMenuItem();
        menuSair = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem3.setText("jMenuItem3");

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuCadastro.setMnemonic('c');
        menuCadastro.setText("Cadastro");

        itemMenuCliente.setText("Cliente");
        itemMenuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuClienteActionPerformed(evt);
            }
        });
        menuCadastro.add(itemMenuCliente);

        itemMenuFornecedor.setMnemonic('f');
        itemMenuFornecedor.setText("Fornecedor");
        itemMenuFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuFornecedorActionPerformed(evt);
            }
        });
        menuCadastro.add(itemMenuFornecedor);

        itemMenuProduto.setMnemonic('p');
        itemMenuProduto.setText("Produto");
        itemMenuProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuProdutoActionPerformed(evt);
            }
        });
        menuCadastro.add(itemMenuProduto);

        itemMenuDespesa.setText("Despesa");
        itemMenuDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuDespesaActionPerformed(evt);
            }
        });
        menuCadastro.add(itemMenuDespesa);

        menuBar.add(menuCadastro);

        menuVenda.setText("Venda");

        itemMenuVenda.setText("Realizar Venda");
        itemMenuVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuVendaActionPerformed(evt);
            }
        });
        menuVenda.add(itemMenuVenda);

        ItemMenuEstoque.setText("Lançar Estoque");
        ItemMenuEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemMenuEstoqueActionPerformed(evt);
            }
        });
        menuVenda.add(ItemMenuEstoque);

        menuBar.add(menuVenda);

        menuRelatorios.setText("Relatórios");

        itemMenuRelatorioVendas.setText("Relatório de Vendas");
        itemMenuRelatorioVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuRelatorioVendasActionPerformed(evt);
            }
        });
        menuRelatorios.add(itemMenuRelatorioVendas);

        itemMenuRelatorioDespesas.setText("Relatório de Despesas");
        itemMenuRelatorioDespesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuRelatorioDespesasActionPerformed(evt);
            }
        });
        menuRelatorios.add(itemMenuRelatorioDespesas);

        menuBar.add(menuRelatorios);

        menuSair.setMnemonic('s');
        menuSair.setText("Sair");
        menuSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSairMouseClicked(evt);
            }
        });
        menuBar.add(menuSair);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1366, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSairMouseClicked
        sair();
    }//GEN-LAST:event_menuSairMouseClicked

    private void itemMenuFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuFornecedorActionPerformed
        abreFornecedorVIEW();
    }//GEN-LAST:event_itemMenuFornecedorActionPerformed

    private void itemMenuProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuProdutoActionPerformed
        abreProdutoVIEW();
    }//GEN-LAST:event_itemMenuProdutoActionPerformed

    private void itemMenuVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuVendaActionPerformed
        abreVendaVIEW();
    }//GEN-LAST:event_itemMenuVendaActionPerformed

    private void itemMenuClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuClienteActionPerformed
        abreClienteVIEW();
    }//GEN-LAST:event_itemMenuClienteActionPerformed

    private void itemMenuRelatorioVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuRelatorioVendasActionPerformed
        RelatorioCTR relatorioCTR = new RelatorioCTR();
        relatorioCTR.abrirRelatorio("Rel_vendas.jasper","Relatório de Vendas", parametros).setVisible(true);
    }//GEN-LAST:event_itemMenuRelatorioVendasActionPerformed

    private void ItemMenuEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemMenuEstoqueActionPerformed
        abreEntradaEstoqueVIEW();
    }//GEN-LAST:event_ItemMenuEstoqueActionPerformed

    private void itemMenuDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuDespesaActionPerformed
        abreDespesaVIEW();
    }//GEN-LAST:event_itemMenuDespesaActionPerformed

    private void itemMenuRelatorioDespesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuRelatorioDespesasActionPerformed
        RelatorioCTR relatorioCTR = new RelatorioCTR();
        relatorioCTR.abrirRelatorio("Rel_despesas.jasper","Relatório de Despesas", parametros).setVisible(true);
    }//GEN-LAST:event_itemMenuRelatorioDespesasActionPerformed

    private void sair(){
        Object[] options = { "Sair", "Cancelar" };
        if(JOptionPane.showOptionDialog(null, "Deseja Sair do Sistema", "Informação", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]) == 0){
            System.exit(0);
        } 
    } 
    
    
    private void abreClienteVIEW(){
        ClienteVIEW clienteVIEW = new ClienteVIEW();
        this.desktopPane.add(clienteVIEW);
        clienteVIEW.setVisible(true); 
        clienteVIEW.setPosicao();
    }
    
    private void abreDespesaVIEW(){
        DespesaVIEW despesaVIEW = new DespesaVIEW();
        this.desktopPane.add(despesaVIEW);
        despesaVIEW.setVisible(true); 
        despesaVIEW.setPosicao();
    }
    
    private void abreEntradaEstoqueVIEW() {
        EntradaEstoqueVIEW entradaEstoqueVIEW = new EntradaEstoqueVIEW();
        this.desktopPane.add(entradaEstoqueVIEW);
        entradaEstoqueVIEW.setVisible(true);
        entradaEstoqueVIEW.setPosicao();
    }
    
    private void abreFornecedorVIEW(){
        FornecedorVIEW fornecedorVIEW = new FornecedorVIEW();
        this.desktopPane.add(fornecedorVIEW);
        fornecedorVIEW.setVisible(true); 
        fornecedorVIEW.setPosicao();
    }
       
    private void abreProdutoVIEW(){
        ProdutoVIEW produtoVIEW = new ProdutoVIEW();
        this.desktopPane.add(produtoVIEW);
        produtoVIEW.setVisible(true); 
        produtoVIEW.setPosicao();
    }
    
    
    private void abreVendaVIEW(){
        VendaVIEW vendaVIEW = new VendaVIEW();
        this.desktopPane.add(vendaVIEW);
        vendaVIEW.setVisible(true); 
        vendaVIEW.setPosicao();
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalVIEW().setVisible(true);
            }
        });
    }
    
    
//    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemMenuEstoque;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem itemMenuCliente;
    private javax.swing.JMenuItem itemMenuDespesa;
    private javax.swing.JMenuItem itemMenuFornecedor;
    private javax.swing.JMenuItem itemMenuProduto;
    private javax.swing.JMenuItem itemMenuRelatorioDespesas;
    private javax.swing.JMenuItem itemMenuRelatorioVendas;
    private javax.swing.JMenuItem itemMenuVenda;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuCadastro;
    private javax.swing.JMenu menuRelatorios;
    private javax.swing.JMenu menuSair;
    private javax.swing.JMenu menuVenda;
    // End of variables declaration//GEN-END:variables

}
