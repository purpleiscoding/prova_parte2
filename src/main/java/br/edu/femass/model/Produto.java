package br.edu.femass.model;

import lombok.Data;

@Data
public class Produto {
    private Long id;
    private String nome;
    private String sku;
    private Integer valor;
    private Integer quantidade;

    public String toString() {
        return this.nome;
    }
}
