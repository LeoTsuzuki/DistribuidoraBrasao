
package br.com.distribuidoraBrasao.dto;


public class VendaDTO {
    private int id_vend;
    private String dat_vend;
    private double val_total_vend;
    private double val_custo_vend;
    private String forma_pag_vend;
    private String status_vend;
    private String obs_vend;
    private int id_cli;
    private String nome_cli;

    public int getId_vend() {
        return id_vend;
    }

    public void setId_vend(int id_vend) {
        this.id_vend = id_vend;
    }

    public String getDat_vend() {
        return dat_vend;
    }

    public void setDat_vend(String dat_vend) {
        this.dat_vend = dat_vend;
    }

    public double getVal_total_vend() {
        return val_total_vend;
    }

    public void setVal_total_vend(double val_total_vend) {
        this.val_total_vend = val_total_vend;
    }

    public double getVal_custo_vend() {
        return val_custo_vend;
    }

    public void setVal_custo_vend(double val_custo_vend) {
        this.val_custo_vend = val_custo_vend;
    }

    public String getForma_pag_vend() {
        return forma_pag_vend;
    }

    public void setForma_pag_vend(String forma_pag_vend) {
        this.forma_pag_vend = forma_pag_vend;
    }

    public String getStatus_vend() {
        return status_vend;
    }

    public void setStatus_vend(String status_vend) {
        this.status_vend = status_vend;
    }

    public String getObs_vend() {
        return obs_vend;
    }

    public void setObs_vend(String obs_vend) {
        this.obs_vend = obs_vend;
    }

    public int getId_cli() {
        return id_cli;
    }

    public void setId_cli(int id_cli) {
        this.id_cli = id_cli;
    }

    public String getNome_cli() {
        return nome_cli;
    }

    public void setNome_cli(String nome_cli) {
        this.nome_cli = nome_cli;
    }
}
