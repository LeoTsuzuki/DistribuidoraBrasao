
package br.com.distribuidoraBrasao.dto;

public class EntradaEstoqueDTO {
    private int id_ent;
    private String dat_ent;
    private int qtd_ent;
    private int id_prod;
    private String nome_prod;

    public int getId_ent() {
        return id_ent;
    }

    public void setId_ent(int id_ent) {
        this.id_ent = id_ent;
    }

    public String getDat_ent() {
        return dat_ent;
    }

    public void setDat_ent(String dat_ent) {
        this.dat_ent = dat_ent;
    }

    public int getQtd_ent() {
        return qtd_ent;
    }

    public void setQtd_ent(int qtd_ent) {
        this.qtd_ent = qtd_ent;
    }


    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getNome_prod() {
        return nome_prod;
    }

    public void setNome_prod(String nome_prod) {
        this.nome_prod = nome_prod;
    }

    
}
