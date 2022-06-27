package br.edu.femass.dao;

import br.edu.femass.model.Cliente;
import br.edu.femass.model.Produto;
import br.edu.femass.model.Fornecedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao extends DaoPostgres implements  Dao<Fornecedor>{
    @Override
    public List<Fornecedor> listar() throws Exception {
        String sql = "select " +
                "fornecedor.id as fornecedor_id, " +
                "fornecedor.nome as fornecedor_nome, " +
                "fornecedor.datafornecimento as fornecedor_datafornecimento, " +
                "fornecedor.ano as fornecedor_anodefabricacao, " +
                "produto.id as produto_id, " +
                "produto.nome as produto_nome " +
                "from fornecedor, produto " +
                "where fornecedor.id_produto = produto.id " +
                "order by fornecedor_nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
        while (rs.next()) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(rs.getLong("fornecedor_id"));
            fornecedor.setNome(rs.getString("fornecedor_nome"));
            fornecedor.setAnodefabricacao(rs.getInt("fornecedor_Anodefabricacao"));
            fornecedor.setDatafornecimento(rs.getString("fornecedor_Datafornecimento"));

            Produto produto = new Produto();
            produto.setId(rs.getLong("produto_id"));
            produto.setNome(rs.getString("produto_nome"));
            fornecedor.setQuantidade(produto);

            sql = "select " +
                    "cliente.id, " +
                    "cliente.nome, " +
                    "cliente.sobrenome " +
                    "from cliente, fornecedor_cliente " +
                    "where fornecedor_cliente.id_fornecedor = ? and " +
                    "fornecedor_cliente.id_cliente = cliente.id";
            PreparedStatement ps2 = getPreparedStatement(sql, false);
            ps2.setLong(1, fornecedor.getId());
            ResultSet rsCliente = ps2.executeQuery();

            List<Cliente> Clientes = new ArrayList<Cliente>();
            while (rsCliente.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rsCliente.getLong("id"));
                cliente.setSobreNome(rsCliente.getString("sobrenome"));
                cliente.setNome(rsCliente.getString("nome"));
                Clientes.add(cliente);
            }

            fornecedor.setClientes(Clientes);

            fornecedores.add(fornecedor);

        }

        return fornecedores;
    }

    @Override
    public void gravar(Fornecedor value) throws Exception {
       String sql = "INSERT INTO fornecedor (nome, edicao, ano, id_produto) VALUES (?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getDatafornecimento());
        ps.setInt(3, value.getAnodefabricacao());
        ps.setLong(4, value.getQuantidade());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

        for (Produto produto: value.getProdutos()) {
            sql = "insert into fornecedor_cliente (id_fornecedor, id_autor) values (?,?)";
            PreparedStatement ps2 = getPreparedStatement(sql, false);
            ps2.setLong(1, value.getId());
            ps2.setLong(2, produto.getId());

            ps2.executeUpdate();

        }

    }

    @Override
    public void alterar(Fornecedor value) throws Exception {
        /*
        String sql = "update cliente set nome = ?, sobrenome = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getSobreNome());
        ps.setLong(3, value.getId());
        ps.executeUpdate();

         */

    }

    @Override
    public void excluir(Fornecedor value) throws Exception {
        /*
        String sql = "delete from cliente where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
        */
    }
}
