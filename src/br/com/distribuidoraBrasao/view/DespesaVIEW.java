package br.com.distribuidoraBrasao.view;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import br.com.distribuidoraBrasao.dto.DespesaDTO;
import br.com.distribuidoraBrasao.dto.FornecedorDTO;
import br.com.distribuidoraBrasao.ctr.DespesaCTR;
import br.com.distribuidoraBrasao.ctr.FornecedorCTR;

public class DespesaVIEW extends javax.swing.JInternalFrame {

    FornecedorDTO fornecedorDTO = new FornecedorDTO(); 
    FornecedorCTR fornecedorCTR = new FornecedorCTR();
    DespesaDTO despesaDTO = new DespesaDTO();          
    DespesaCTR despesaCTR = new DespesaCTR();          
    int gravar_alterar; 
 
    ResultSet rs; // 
    DefaultTableModel modelo_jtl_consultar_despesa;    
    DefaultTableModel modelo_jtl_consultar_fornecedor; 
    
    public DespesaVIEW() {
        initComponents();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        modelo_jtl_consultar_despesa = (DefaultTableModel) jtl_consultar_despesa.getModel();
        modelo_jtl_consultar_fornecedor = (DefaultTableModel) jtl_consultar_fornecedor.getModel();
    }
    
    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }
    
    private void gravar() {
        try {
            SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
 
            despesaDTO.setDescricao_desp(descricao_desp.getText());
            despesaDTO.setCategoria_desp(categoria_desp.getSelectedItem().toString());
            despesaDTO.setDat_lancamento_desp(formatoBanco.format(formatoTela.parse(dat_lancamento_desp.getText())));
            despesaDTO.setDat_vencimento_desp(formatoBanco.format(formatoTela.parse(dat_vecimento_desp.getText())));
            despesaDTO.setVal_desp(Double.parseDouble(valor_desp.getText().replace(",", ".")));
            despesaDTO.setStatus_desp(status_desp.getSelectedItem().toString());
            despesaDTO.setObs_desp(jTextArea1.getText());
            int linhaSelecionada = jtl_consultar_fornecedor.getSelectedRow();
            if (linhaSelecionada >= 0) {
                despesaDTO.setId_for(Integer.parseInt(String.valueOf(
                        jtl_consultar_fornecedor.getValueAt(linhaSelecionada, 0))));
            } else {
                despesaDTO.setId_for(0); // sem fornecedor
            }
 
            JOptionPane.showMessageDialog(null,
                    despesaCTR.inserirDespesa(despesaDTO)
            );
        } catch (Exception e) {
            System.out.println("Erro ao Gravar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao gravar. Verifique se um fornecedor está selecionado e todos os campos estão corretos.");
        }
    }
    
    private void alterar() {
        try {
            SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
 
            despesaDTO.setDescricao_desp(descricao_desp.getText());
            despesaDTO.setCategoria_desp(categoria_desp.getSelectedItem().toString());
            despesaDTO.setDat_lancamento_desp(formatoBanco.format(formatoTela.parse(dat_lancamento_desp.getText())));
            despesaDTO.setDat_vencimento_desp(formatoBanco.format(formatoTela.parse(dat_vecimento_desp.getText())));
            despesaDTO.setVal_desp(Double.parseDouble(valor_desp.getText().replace(",", ".")));
            despesaDTO.setStatus_desp(status_desp.getSelectedItem().toString());
            despesaDTO.setObs_desp(jTextArea1.getText());
            fornecedorDTO.setId_for(Integer.parseInt(String.valueOf(
                    jtl_consultar_fornecedor.getValueAt(
                    jtl_consultar_fornecedor.getSelectedRow(), 0))));
            despesaDTO.setId_for(fornecedorDTO.getId_for());
 
            JOptionPane.showMessageDialog(null,
                    despesaCTR.alterarDespesa(despesaDTO)
            );
        } catch (Exception e) {
            System.out.println("Erro ao Alterar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao alterar. Verifique se um fornecedor está selecionado e se as datas estão corretas.");
        }
    }
    
    private void excluir() {
        if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a Despesa?", "Aviso",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                    despesaCTR.excluirDespesa(despesaDTO)
            );
        }
    } 
    
    private void liberaCampos(boolean a) {
        descricao_desp.setEnabled(a);
        dat_lancamento_desp.setEnabled(a);
        dat_vecimento_desp.setEnabled(a);
        categoria_desp.setEnabled(a);
        valor_desp.setEnabled(a);
        status_desp.setEnabled(a);
        jTextArea1.setEnabled(a);
        pesquisa_nome_fornecedor.setEnabled(a);
        btnCadastrarFornecedor.setEnabled(a);
        jtl_consultar_fornecedor.setEnabled(a);
        btnConsultarFor1.setEnabled(a);
    }
    
    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnExcluir.setEnabled(d);
        btnSair.setEnabled(e);
    }
    
    private void limpaCampos() {
        descricao_desp.setText("");
        dat_lancamento_desp.setText("");
        dat_vecimento_desp.setText("");
        categoria_desp.setSelectedIndex(0);
        valor_desp.setText("");
        status_desp.setSelectedIndex(0);
        jTextArea1.setText("");
        pesquisa_nome_fornecedor.setText("");
        modelo_jtl_consultar_fornecedor.setNumRows(0);
    }
    
    private void preencheTabelaDespesa(String valorPesquisa) { //Metodo que prenche a tabela de acordo com a ComboBox
        try {
            modelo_jtl_consultar_despesa.setNumRows(0);
            String ordenacao = tipoConsultaDespesa.getSelectedItem().toString();
            SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
            int opcao;
 
            switch (ordenacao) {
                case "NOME":
                    despesaDTO.setDescricao_desp(valorPesquisa);
                    opcao = 1;
                    break;
                case "STATUS":
                    // Usuário digita: PAGO ou EM ABERTO
                    despesaDTO.setStatus_desp(valorPesquisa);
                    opcao = 3;
                    break;
                case "VENCIMENTO":
                    // Usuário digita: MM/AAAA (ex: 06/2026)
                    despesaDTO.setDat_vencimento_desp(valorPesquisa);
                    opcao = 4;
                    break;
                case "TODAS":
                    opcao = 5;
                break;
                default:
                    despesaDTO.setDescricao_desp(valorPesquisa);
                    opcao = 1;
            }
 
            rs = despesaCTR.consultarDespesa(despesaDTO, opcao);
            while (rs.next()) {
                String dataVenc = "";
                try {
                    dataVenc = formatoTela.format(rs.getDate("dat_vencimento_desp"));
                } catch (Exception ex) {
                    dataVenc = rs.getString("dat_vencimento_desp");
                }
                modelo_jtl_consultar_despesa.addRow(new Object[]{
                    rs.getString("id_desp"),
                    rs.getString("descricao_desp"),
                    dataVenc,
                    String.format("%.2f", rs.getDouble("val_desp")),
                    rs.getString("status_desp")
                });
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL Despesa: " + erTab);
            JOptionPane.showMessageDialog(null,
                "Erro na consulta. Verifique o formato da pesquisa:\n"
                + "- NOME: texto livre\n"
                + "- STATUS: PAGO ou EM ABERTO\n"
                + "- VENCIMENTO: MM/AAAA (ex: 06/2026)");
        } finally {
            despesaCTR.CloseDB();
        }
    }
    
    private void preencheCamposDespesa(int id_desp) {
        try {
            despesaDTO.setId_desp(id_desp);
            rs = despesaCTR.consultarDespesa(despesaDTO, 2);
            if (rs.next()) {
                limpaCampos();
 
                SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
 
                descricao_desp.setText(rs.getString("descricao_desp"));
                categoria_desp.setSelectedItem(rs.getString("categoria_desp"));
                try {
                    dat_lancamento_desp.setText(formatoTela.format(rs.getDate("dat_lancamento_desp")));
                    dat_vecimento_desp.setText(formatoTela.format(rs.getDate("dat_vencimento_desp")));
                } catch (Exception ex) {
                    dat_lancamento_desp.setText(rs.getString("dat_lancamento_desp"));
                    dat_vecimento_desp.setText(rs.getString("dat_vencimento_desp"));
                }
                valor_desp.setText(String.valueOf(rs.getDouble("val_desp")));
                status_desp.setSelectedItem(rs.getString("status_desp"));
                jTextArea1.setText(rs.getString("obs_desp"));
 
                // Preenche a tabela de fornecedor e seleciona a linha, igual ao Projeto 5
                modelo_jtl_consultar_fornecedor.setNumRows(0);
                modelo_jtl_consultar_fornecedor.addRow(new Object[]{
                    rs.getInt("id_for"),
                    rs.getString("nome_for")
                });
                jtl_consultar_fornecedor.setRowSelectionInterval(0, 0);
 
                gravar_alterar = 2;
                liberaCampos(true);
            } // fecha if(rs.next)
        } catch (Exception erTab) {
            System.out.println("Erro SQL preencheCampos: " + erTab);
        } finally {
            despesaCTR.CloseDB();
        }
    }
    
    private void preencheTabelaFornecedor(String nome_for) {
        try {
            modelo_jtl_consultar_fornecedor.setNumRows(0);
            fornecedorDTO.setNome_for(nome_for);
            rs = fornecedorCTR.consultarFornecedor(fornecedorDTO, 1); // 1 = pesquisa por nome
            while (rs.next()) {
                modelo_jtl_consultar_fornecedor.addRow(new Object[]{
                    rs.getString("id_for"),
                    rs.getString("nome_for")
                });
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL Fornecedor: " + erTab);
        } finally {
            fornecedorCTR.CloseDB();
        }
    }
    
    private boolean validarDatas() {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            java.util.Date lancamento = sdf.parse(dat_lancamento_desp.getText());
            java.util.Date vencimento = sdf.parse(dat_vecimento_desp.getText());

            if (vencimento.before(lancamento)) {
                JOptionPane.showMessageDialog(null,"A data de vencimento não pode ser anterior à data de lançamento.");
                return false;
            }
            return true;
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Digite datas válidas no formato DD/MM/AAAA.");
            return false;
        }
    }
     
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        descricao_desp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        categoria_desp = new javax.swing.JComboBox<>();
        dat_vecimento_desp = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        dat_lancamento_desp = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        valor_desp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        status_desp = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        pesquisa_nome_fornecedor = new javax.swing.JTextField();
        btnCadastrarFornecedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtl_consultar_fornecedor = new javax.swing.JTable();
        btnConsultarFor1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consultar_despesa = new javax.swing.JTable();
        tipoConsultaDespesa = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        pesquisa_despesa = new javax.swing.JTextField();
        btnPesquisarDespesa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setTitle("GERENCIADOR DE DESPESAS");

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados gerais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Fornecedor:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Data de Lançamento:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Categoria:");

        categoria_desp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        categoria_desp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DESPESAS", "SERVIÇOS", "OUTROS" }));

        try {
            dat_vecimento_desp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Data de Vencimento:");

        try {
            dat_lancamento_desp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Valor(R$):");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Observações:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("Status:");

        status_desp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        status_desp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PAGO", "EM ABERTO" }));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Despesa:");

        btnCadastrarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/novo.png"))); // NOI18N
        btnCadastrarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarFornecedorActionPerformed(evt);
            }
        });

        jtl_consultar_fornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtl_consultar_fornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consultar_fornecedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtl_consultar_fornecedor);
        if (jtl_consultar_fornecedor.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_fornecedor.getColumnModel().getColumn(0).setPreferredWidth(60);
            jtl_consultar_fornecedor.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        btnConsultarFor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/pesquisa.png"))); // NOI18N
        btnConsultarFor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarFor1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(dat_lancamento_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(dat_vecimento_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(43, 43, 43)
                                                .addComponent(jLabel6)))))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(valor_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(status_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(categoria_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(descricao_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisa_nome_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultarFor1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCadastrarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descricao_desp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dat_lancamento_desp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(categoria_desp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dat_vecimento_desp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(valor_desp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(status_desp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(pesquisa_nome_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnCadastrarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultarFor1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consultar Despesas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(51, 51, 255));

        jtl_consultar_despesa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Vencimento", "Valor", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtl_consultar_despesa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consultar_despesaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtl_consultar_despesa);
        if (jtl_consultar_despesa.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_despesa.getColumnModel().getColumn(0).setPreferredWidth(60);
            jtl_consultar_despesa.getColumnModel().getColumn(0).setMaxWidth(60);
            jtl_consultar_despesa.getColumnModel().getColumn(2).setPreferredWidth(80);
            jtl_consultar_despesa.getColumnModel().getColumn(2).setMaxWidth(80);
            jtl_consultar_despesa.getColumnModel().getColumn(3).setPreferredWidth(80);
            jtl_consultar_despesa.getColumnModel().getColumn(3).setMaxWidth(80);
            jtl_consultar_despesa.getColumnModel().getColumn(4).setPreferredWidth(60);
            jtl_consultar_despesa.getColumnModel().getColumn(4).setMaxWidth(60);
        }

        tipoConsultaDespesa.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tipoConsultaDespesa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "NOME", "STATUS", "VENCIMENTO" }));
        tipoConsultaDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoConsultaDespesaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Odernar por:");

        btnPesquisarDespesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/pesquisa.png"))); // NOI18N
        btnPesquisarDespesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarDespesaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoConsultaDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pesquisa_despesa, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisarDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoConsultaDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pesquisa_despesa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarDespesa, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/distribuidoraBrasao/view/imagens/add_despesa.png"))); // NOI18N
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
            .addGroup(layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSair))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            if (gravar_alterar == 2) {
                alterar();
                gravar_alterar = 0;
            } else {
                JOptionPane.showMessageDialog(null, "Erro no Sistema!!!");
            }
        }
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        modelo_jtl_consultar_despesa.setNumRows(0);
        liberaBotoes(true, false, false, false, true);
        gravar_alterar = 0;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        modelo_jtl_consultar_despesa.setNumRows(0);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnCadastrarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarFornecedorActionPerformed
        Object[] options = {"Sim", "Não"};

        if (JOptionPane.showOptionDialog(this, "Você quer cadastrar um novo fornecedor?", "Informação",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]) == 0) {

            FornecedorVIEW fornecedorVIEW = new FornecedorVIEW();

            JDesktopPane desktop = this.getDesktopPane();

            if (desktop != null) {
                desktop.add(fornecedorVIEW);
                fornecedorVIEW.setPosicao();
                fornecedorVIEW.setVisible(true);

                try {
                    fornecedorVIEW.setSelected(true);
                }
                catch (Exception ex) {
                    ex.getMessage();
                }
            }
        }
    }//GEN-LAST:event_btnCadastrarFornecedorActionPerformed

    private void jtl_consultar_despesaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consultar_despesaMouseClicked
        preencheCamposDespesa(Integer.parseInt(String.valueOf(
      jtl_consultar_despesa.getValueAt(
      jtl_consultar_despesa.getSelectedRow(), 0))));
        liberaBotoes(false, true, true, true, true);
    }//GEN-LAST:event_jtl_consultar_despesaMouseClicked

    private void btnPesquisarDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarDespesaActionPerformed
        preencheTabelaDespesa(pesquisa_despesa.getText());
    }//GEN-LAST:event_btnPesquisarDespesaActionPerformed

    private void jtl_consultar_fornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consultar_fornecedorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtl_consultar_fornecedorMouseClicked

    private void btnConsultarFor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarFor1ActionPerformed
        preencheTabelaFornecedor(pesquisa_nome_fornecedor.getText());
    }//GEN-LAST:event_btnConsultarFor1ActionPerformed

    private void tipoConsultaDespesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoConsultaDespesaActionPerformed
        String opcao = tipoConsultaDespesa.getSelectedItem().toString();

        switch (opcao) {
            case "NOME":
                pesquisa_despesa.setText("Digite o nome da despesa...");
                break;

            case "STATUS":
                pesquisa_despesa.setText("Digite PAGO ou EM ABERTO...");
                break;

            case "VENCIMENTO":
                pesquisa_despesa.setText("Digite MM/AAAA (Ex: 06/2026)...");
                break;
            case "TODAS":
                pesquisa_despesa.setText("");
                break;
            
        }
    }//GEN-LAST:event_tipoConsultaDespesaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarFornecedor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsultarFor1;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarDespesa;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> categoria_desp;
    private javax.swing.JFormattedTextField dat_lancamento_desp;
    private javax.swing.JFormattedTextField dat_vecimento_desp;
    private javax.swing.JTextField descricao_desp;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable jtl_consultar_despesa;
    private javax.swing.JTable jtl_consultar_fornecedor;
    private javax.swing.JTextField pesquisa_despesa;
    private javax.swing.JTextField pesquisa_nome_fornecedor;
    private javax.swing.JComboBox<String> status_desp;
    private javax.swing.JComboBox<String> tipoConsultaDespesa;
    private javax.swing.JTextField valor_desp;
    // End of variables declaration//GEN-END:variables
}
