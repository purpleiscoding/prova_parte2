package br.edu.femass;

import br.edu.femass.dao.ProdutoDao;
import br.edu.femass.dao.LojaDao;
import br.edu.femass.dao.FornecedorDao;
import br.edu.femass.model.*;

import java.util.List;

public class teste {

    public static void main(String[] args) {
        try {
            List<Fornecedor> fornecedores = new FornecedorDao().listar();

            for (Fornecedor l: fornecedores) {
                System.out.println("Nome do Produto: " + l.getNome());
                System.out.println("Data de Fornecimento: " + l.getDatafornecimento());
                System.out.println("Ano de Fabricação: " + l.getAnodefabricacao());
                System.out.println("Quantidade: " + l.getQuantidade());
                for (Produto a: l.getProdutos()) {
                    System.out.println("Marca do Produto: " + a.getId());
                    System.out.println("Valor do Produto: " + a.getValor());
                    System.out.println("Quantidade: " + a.getQuantidade());
                }
                System.out.println("===========================");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void gravarFornecedor() {
        try {
            List<Cliente> Clientes = new LojaDao().listar();

            List<Produto> produtos = new ProdutoDao().listar();

            Fornecedor l = new Fornecedor();
            l.setProdutos(produtos);
            l.setQuantidade(produtos.get(1));
            l.setNome("FORNECIMENTO SAMSUNG");
            l.setDatafornecimento("25/04/2022");
            l.setAnodefabricacao(2021);

            new FornecedorDao().gravar(l);
            System.out.println("produto gravado com o código " + l.getId().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
