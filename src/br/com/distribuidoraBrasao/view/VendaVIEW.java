package br.com.distribuidoraBrasao.view;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import br.com.distribuidoraBrasao.dto.VendaDTO;
import br.com.distribuidoraBrasao.dto.ClienteDTO;
import br.com.distribuidoraBrasao.dto.ProdutoDTO;
import br.com.distribuidoraBrasao.ctr.VendaCTR;
import br.com.distribuidoraBrasao.ctr.ClienteCTR;
import br.com.distribuidoraBrasao.ctr.ProdutoCTR;
import java.text.SimpleDateFormat;

public class VendaVIEW extends javax.swing.JInternalFrame {

    VendaDTO vendaDTO = new VendaDTO();
    VendaCTR vendaCTR = new VendaCTR();
    ClienteDTO clienteDTO = new ClienteDTO();
    ClienteCTR clienteCTR = new ClienteCTR();
    ProdutoDTO produtoDTO = new ProdutoDTO();
    ProdutoCTR produtoCTR = new ProdutoCTR();

    int gravar_alterar;
    ResultSet rs;
    DefaultTableModel modelo_jtl_itens_venda;
    DefaultTableModel modelo_jtl_consultar_venda;
    double val_total = 0.00;
    double val_custo = 0.00;
    
    public VendaVIEW() {
        initComponents();
        
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        modelo_jtl_itens_venda = (DefaultTableModel) jtl_itens_venda.getModel();
        modelo_jtl_consultar_venda = (DefaultTableModel) jtl_consultar_venda.getModel();
        preencheComboCliente();
        preencheComboProduto();
    }
    
    public void setPosicao() {
    Dimension d = this.getDesktopPane().getSize();
    this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void preencheComboCliente() {
        try {
            id_cli.removeAllItems();
            clienteDTO.setNome_cli("");
            rs = clienteCTR.consultarCliente(clienteDTO, 3);
            while (rs.next()) {
                id_cli.addItem(rs.getString("id_cli") + " - " + rs.getString("nome_cli"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao preencher combo cliente: " + e.getMessage());
        } finally {
            clienteCTR.CloseDB();
        }
    }

    private void preencheComboProduto() {
        try {
            id_prod_item.removeAllItems();
            produtoDTO.setNome_prod("");
            rs = produtoCTR.consultarProduto(produtoDTO, 3);
            while (rs.next()) {
                id_prod_item.addItem(rs.getString("id_prod") + " - " + rs.getString("nome_prod"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao preencher combo produto: " + e.getMessage());
        } finally {
            produtoCTR.CloseDB();
        }
    }

    private void gravar() {
        if (!validarDatas()) return;
        try {
            // verifica se tem itens
            if (modelo_jtl_itens_venda.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Adicione pelo menos um produto!");
                return;
            }

            // pega o id do cliente do combo
            String itemCliente = id_cli.getSelectedItem().toString();
            int id = Integer.parseInt(itemCliente.split(" - ")[0]);

            SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date data = formatoTela.parse(dat_vend.getText());

            vendaDTO.setId_cli(id);
            vendaDTO.setDat_vend(formatoBanco.format(data));
            vendaDTO.setForma_pag_vend(forma_pag_vend.getSelectedItem().toString());
            vendaDTO.setStatus_vend(status_vend.getSelectedItem().toString());
            vendaDTO.setObs_vend(obs_vend.getText());
            vendaDTO.setVal_total_vend(val_total);
            vendaDTO.setVal_custo_vend(val_custo);

            // insere a venda - CORRIGIDO: verifica retorno antes de continuar
            String resultadoInsercao = vendaCTR.inserirVenda(vendaDTO);
            if (!resultadoInsercao.contains("Sucesso")) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir venda: " + resultadoInsercao);
                return;
            }

            // pega o id da venda recém inserida
            int id_vend = vendaCTR.consultarUltimoId();

            // insere os itens
            for (int i = 0; i < modelo_jtl_itens_venda.getRowCount(); i++) {
                int id_prod = Integer.parseInt(modelo_jtl_itens_venda.getValueAt(i, 0).toString());
                int qtd = Integer.parseInt(modelo_jtl_itens_venda.getValueAt(i, 2).toString());
                double vlr_unit = Double.parseDouble(modelo_jtl_itens_venda.getValueAt(i, 3).toString());
                double vlr_custo = Double.parseDouble(modelo_jtl_itens_venda.getValueAt(i, 5).toString());

                vendaCTR.inserirItemVenda(id_vend, id_prod, qtd, vlr_unit, vlr_custo);
            }

            JOptionPane.showMessageDialog(null, "Venda Cadastrada com Sucesso!!!");

        } catch (Exception e) {
            System.out.println("Erro ao Gravar: " + e.getMessage());
        }
    }


    private void excluir() {
        if (JOptionPane.showConfirmDialog(null, "Deseja Realmente excluir a Venda?", "Aviso",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                    vendaCTR.excluirVenda(vendaDTO)
            );
        }
    }


    private void removerItem() {
        try {
            int linhaSelecionada = jtl_itens_venda.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um item para remover!");
                return;
            }

            double vlr_total_item = Double.parseDouble(modelo_jtl_itens_venda.getValueAt(linhaSelecionada, 4).toString());
            double vlr_custo_item = Double.parseDouble(modelo_jtl_itens_venda.getValueAt(linhaSelecionada, 5).toString());

            val_total -= vlr_total_item;
            val_custo -= vlr_custo_item;
            lbl_total.setText(String.format("%.2f", val_total));

            modelo_jtl_itens_venda.removeRow(linhaSelecionada);

        } catch (Exception e) {
            System.out.println("Erro ao remover item: " + e.getMessage());
        }
    }
    
    private void adicionarItem() {
    try {
        if (qtd_item.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe a quantidade!");
            return;
        }

        String itemProduto = id_prod_item.getSelectedItem().toString();
        int id_prod = Integer.parseInt(itemProduto.split(" - ")[0]);
        String nome_prod = itemProduto.split(" - ")[1];
        int qtd = Integer.parseInt(qtd_item.getText());

        produtoDTO.setId_prod(id_prod);
        rs = produtoCTR.consultarProduto(produtoDTO, 2);
        if (rs.next()) {
            int estoque_disponivel = rs.getInt("estoque_prod");

            if (qtd > estoque_disponivel) {
                JOptionPane.showMessageDialog(null, 
                    "Estoque insuficiente! Disponível: " + estoque_disponivel);
                return;
            }

            double vlr_unit = rs.getDouble("p_venda_prod");
            double vlr_custo = rs.getDouble("p_custo_prod");
            double vlr_total_item = vlr_unit * qtd;
            double vlr_custo_item = vlr_custo * qtd;

            modelo_jtl_itens_venda.addRow(new Object[]{
                id_prod,
                nome_prod,
                qtd,
                vlr_unit,
                vlr_total_item,
                vlr_custo_item
            });

            val_total += vlr_total_item;
            val_custo += vlr_custo_item;
            lbl_total.setText(String.format("%.2f", val_total));
        }

        qtd_item.setText("");

    } catch (Exception e) {
        System.out.println("Erro ao adicionar item: " + e.getMessage());
    } finally {
        produtoCTR.CloseDB();
    }
}

    private void liberaCampos(boolean a) {
        id_cli.setEnabled(a);
        dat_vend.setEnabled(a);
        status_vend.setEnabled(a);
        forma_pag_vend.setEnabled(a);
        obs_vend.setEnabled(a);
        id_prod_item.setEnabled(a);
        qtd_item.setEnabled(a);
        btnAdicionarItem.setEnabled(a);
        btnRemoverItem.setEnabled(a);
    }

    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnExcluir.setEnabled(d);
        btnSair.setEnabled(e);
    }

    private void limpaCampos() {
        id_cli.setSelectedIndex(0);
        dat_vend.setText("");
        status_vend.setSelectedIndex(0);
        forma_pag_vend.setSelectedIndex(0);
        obs_vend.setText("");
        qtd_item.setText("");
        modelo_jtl_itens_venda.setNumRows(0);
        val_total = 0.00;
        val_custo = 0.00;
        lbl_total.setText("0.00");
    }

    private void preencheTabela(String nome) {
        try {
            modelo_jtl_consultar_venda.setNumRows(0);
            vendaDTO.setNome_cli(nome);
            rs = vendaCTR.consultarVenda(vendaDTO, 1);
            SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                modelo_jtl_consultar_venda.addRow(new Object[]{
                    rs.getString("id_vend"),
                    formatoTela.format(rs.getDate("dat_vend")),
                    rs.getString("nome_cli"),
                    rs.getString("val_total_vend"),
                    rs.getString("status_vend")
                });
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        } finally {
            vendaCTR.CloseDB();
        }
    }

    private void preencheCampos(int id_vend) {
        try {
            vendaDTO.setId_vend(id_vend);
            rs = vendaCTR.consultarVenda(vendaDTO, 2);
            if (rs.next()) {
                limpaCampos();

                id_cli.setSelectedItem(rs.getString("id_cli") + " - " + rs.getString("nome_cli"));
                SimpleDateFormat formatoTela2 = new SimpleDateFormat("dd/MM/yyyy");
                dat_vend.setText(formatoTela2.format(rs.getDate("dat_vend")));
                status_vend.setSelectedItem(rs.getString("status_vend"));
                forma_pag_vend.setSelectedItem(rs.getString("forma_pag_vend"));
                obs_vend.setText(rs.getString("obs_vend"));
                val_total = rs.getDouble("val_total_vend");
                val_custo = rs.getDouble("val_custo_vend");
                lbl_total.setText(String.format("%.2f", val_total));

                vendaDTO.setId_vend(id_vend);

                // carrega os itens da venda na tabela
                ResultSet rsItens = vendaCTR.consultarItensPorVenda(id_vend);
                while (rsItens.next()) {
                    modelo_jtl_itens_venda.addRow(new Object[]{
                        rsItens.getString("id_prod"),
                        rsItens.getString("nome_prod"),
                        rsItens.getString("qtd_item"),
                        rsItens.getString("val_unit_item"),
                        rsItens.getString("val_unit_item"),
                        rsItens.getString("val_custo_item")
                    });
                }

                gravar_alterar = 2;
                liberaCampos(true);
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        } finally {
            vendaCTR.CloseDB();
        }
    }
    
    private boolean validarDatas() {
        try {
            String data = dat_vend.getText().trim();

            if (data.equals("  /  /    ")) {
                JOptionPane.showMessageDialog(null,"Informe a data da venda.");
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
            
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Digite uma data válida no formato DD/MM/AAAA.");
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dat_vend = new javax.swing.JFormattedTextField();
        id_cli = new javax.swing.JComboBox<>();
        status_vend = new javax.swing.JComboBox<>();
        forma_pag_vend = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        obs_vend = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_itens_venda = new javax.swing.JTable();
        id_prod_item = new javax.swing.JComboBox<>();
        qtd_item = new javax.swing.JTextField();
        btnAdicionarItem = new javax.swing.JButton();
        btnRemoverItem = new javax.swing.JButton();
        lbl_total = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtl_consultar_venda = new javax.swing.JTable();
        pesquisa_nome = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setTitle("GERENCIADOR DE VENDAS");

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setForeground(new java.awt.Color(153, 51, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1061, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setForeground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Cliente:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Data:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Forma de Pag:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Observações:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Status:");

        try {
            dat_vend.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        id_cli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        status_vend.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        status_vend.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pago", "Pendente", " " }));

        forma_pag_vend.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        forma_pag_vend.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Pix", "Cartão" }));

        obs_vend.setColumns(20);
        obs_vend.setRows(5);
        jScrollPane1.setViewportView(obs_vend);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_cli, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(forma_pag_vend, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dat_vend, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(status_vend, 0, 1, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(86, 86, 86))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(id_cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(dat_vend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(status_vend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(forma_pag_vend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Itens", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Produto:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Quantidade:");

        jtl_itens_venda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Produto", "Qtd", "Vlr_Unit", "Vlr_Total", "Vlr_Custo"
            }
        ));
        jScrollPane2.setViewportView(jtl_itens_venda);
        if (jtl_itens_venda.getColumnModel().getColumnCount() > 0) {
            jtl_itens_venda.getColumnModel().getColumn(0).setPreferredWidth(50);
            jtl_itens_venda.getColumnModel().getColumn(0).setMaxWidth(50);
            jtl_itens_venda.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtl_itens_venda.getColumnModel().getColumn(2).setMaxWidth(60);
            jtl_itens_venda.getColumnModel().getColumn(3).setPreferredWidth(70);
            jtl_itens_venda.getColumnModel().getColumn(3).setMaxWidth(70);
            jtl_itens_venda.getColumnModel().getColumn(4).setPreferredWidth(70);
            jtl_itens_venda.getColumnModel().getColumn(4).setMaxWidth(70);
            jtl_itens_venda.getColumnModel().getColumn(5).setMinWidth(0);
            jtl_itens_venda.getColumnModel().getColumn(5).setPreferredWidth(0);
            jtl_itens_venda.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnAdicionarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/adicionar-a-cesta.png"))); // NOI18N
        btnAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarItemActionPerformed(evt);
            }
        });

        btnRemoverItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/apagar-item.png"))); // NOI18N
        btnRemoverItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverItemActionPerformed(evt);
            }
        });

        lbl_total.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(51, 153, 0));
        lbl_total.setText("0.00");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Valor Total:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAdicionarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id_prod_item, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qtd_item, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(id_prod_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qtd_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemoverItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAdicionarItem)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consulta pelo Nome", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Cliente:");

        jtl_consultar_venda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data", "Cliente", "Total"
            }
        ));
        jtl_consultar_venda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consultar_vendaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtl_consultar_venda);
        if (jtl_consultar_venda.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_venda.getColumnModel().getColumn(0).setPreferredWidth(60);
            jtl_consultar_venda.getColumnModel().getColumn(0).setMaxWidth(60);
            jtl_consultar_venda.getColumnModel().getColumn(1).setPreferredWidth(80);
            jtl_consultar_venda.getColumnModel().getColumn(1).setMaxWidth(80);
            jtl_consultar_venda.getColumnModel().getColumn(3).setPreferredWidth(70);
            jtl_consultar_venda.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/pesquisa.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisa_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(pesquisa_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPesquisar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/novo.png"))); // NOI18N
        btnNovo.setText("Nova");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/excluir.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar)
                        .addGap(12, 12, 12)
                        .addComponent(btnCancelar)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSair))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        preencheTabela(pesquisa_nome.getText());
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        liberaCampos(true);
        liberaBotoes(false, true, true, false, true);
        gravar_alterar = 1;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (!validarDatas()) {
            return;
        }
        if (gravar_alterar == 1) {
            gravar();
            gravar_alterar = 0;
        } else {
            JOptionPane.showMessageDialog(null, "Erro no Sistema!!!");
        }
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        modelo_jtl_consultar_venda.setNumRows(0);
        liberaBotoes(true, false, false, false, true);
        gravar_alterar = 0;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        modelo_jtl_consultar_venda.setNumRows(0);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void jtl_consultar_vendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consultar_vendaMouseClicked
        preencheCampos(Integer.parseInt(String.valueOf(
        jtl_consultar_venda.getValueAt(
        jtl_consultar_venda.getSelectedRow(), 0))));
        liberaBotoes(false, true, true, true, true);
    }//GEN-LAST:event_jtl_consultar_vendaMouseClicked

    private void btnRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverItemActionPerformed
        removerItem();
    }//GEN-LAST:event_btnRemoverItemActionPerformed

    private void btnAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarItemActionPerformed
        adicionarItem();
    }//GEN-LAST:event_btnAdicionarItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarItem;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnRemoverItem;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JFormattedTextField dat_vend;
    private javax.swing.JComboBox<String> forma_pag_vend;
    private javax.swing.JComboBox<String> id_cli;
    private javax.swing.JComboBox<String> id_prod_item;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtl_consultar_venda;
    private javax.swing.JTable jtl_itens_venda;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JTextArea obs_vend;
    private javax.swing.JTextField pesquisa_nome;
    private javax.swing.JTextField qtd_item;
    private javax.swing.JComboBox<String> status_vend;
    // End of variables declaration//GEN-END:variables
}
