package br.edu.femass.model;

import lombok.Data;

import java.util.List;

@Data
public class Fornecedor {
    private Long id;
    private String nome;
    private String datafornecimento;
    private Integer anodefabricacao;
    private Integer quantidade;
    private List<Produto> Produtos;
    private Produto produto;

    @Override
    public String toString() {
        return this.nome;
    }

    public void setQuantidade(Produto produto) {
    }

    public void setClientes(List<Cliente> clientes) {
    }
}
