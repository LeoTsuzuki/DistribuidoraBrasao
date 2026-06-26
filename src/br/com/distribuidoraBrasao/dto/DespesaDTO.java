package br.com.distribuidoraBrasao.dto;


public class DespesaDTO {
    
    private int id_desp;
    private String descricao_desp;
    private String categoria_desp;
    private String dat_lancamento_desp;
    private String dat_vencimento_desp;
    private double val_desp;
    private String status_desp;
    private String obs_desp;
    private int id_for;
    private String nome_for;

    public int getId_desp() {
        return id_desp;
    }

    public void setId_desp(int id_desp) {
        this.id_desp = id_desp;
    }

    public String getDescricao_desp() {
        return descricao_desp;
    }

    public void setDescricao_desp(String descricao_desp) {
        this.descricao_desp = descricao_desp;
    }

    public String getCategoria_desp() {
        return categoria_desp;
    }

    public void setCategoria_desp(String categoria_desp) {
        this.categoria_desp = categoria_desp;
    }

    public String getDat_lancamento_desp() {
        return dat_lancamento_desp;
    }

    public void setDat_lancamento_desp(String dat_lancamento_desp) {
        this.dat_lancamento_desp = dat_lancamento_desp;
    }

    public String getDat_vencimento_desp() {
        return dat_vencimento_desp;
    }

    public void setDat_vencimento_desp(String dat_vencimento_desp) {
        this.dat_vencimento_desp = dat_vencimento_desp;
    }

    public double getVal_desp() {
        return val_desp;
    }

    public void setVal_desp(double val_desp) {
        this.val_desp = val_desp;
    }

    public String getStatus_desp() {
        return status_desp;
    }

    public void setStatus_desp(String status_desp) {
        this.status_desp = status_desp;
    }

    public String getObs_desp() {
        return obs_desp;
    }

    public void setObs_desp(String obs_desp) {
        this.obs_desp = obs_desp;
    }

    public int getId_for() {
        return id_for;
    }

    public void setId_for(int id_for) {
        this.id_for = id_for;
    }

    public String getNome_for() {
        return nome_for;
    }

    public void setNome_for(String nome_for) {
        this.nome_for = nome_for;
    }

}
