
package br.com.distribuidoraBrasao.dto;

public class ProdutoDTO {
    private int id_prod;
    private String nome_prod;
    private String tipo_prod;
    private String peso_prod;      // 5, 10, 15, 20
    private double p_custo_prod;
    private double p_venda_prod;
    private int estoque_prod;

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

    public String getTipo_prod() {
        return tipo_prod;
    }

    public void setTipo_prod(String tipo_prod) {
        this.tipo_prod = tipo_prod;
    }

    public String getPeso_prod() {
        return peso_prod;
    }

    public void setPeso_prod(String peso_prod) {
        this.peso_prod = peso_prod;
    }


    public double getP_custo_prod() {
        return p_custo_prod;
    }

    public void setP_custo_prod(double p_custo_prod) {
        this.p_custo_prod = p_custo_prod;
    }

    public double getP_venda_prod() {
        return p_venda_prod;
    }

    public void setP_venda_prod(double p_venda_prod) {
        this.p_venda_prod = p_venda_prod;
    }

    public int getEstoque_prod() {
        return estoque_prod;
    }

    public void setEstoque_prod(int estoque_prod) {
        this.estoque_prod = estoque_prod;
    }
}
