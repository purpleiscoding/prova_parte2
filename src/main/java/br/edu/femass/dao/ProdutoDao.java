package br.edu.femass.dao;

import br.edu.femass.model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao extends DaoPostgres implements  Dao<Produto>{
    @Override
    public List<Produto> listar() throws Exception {
        String sql = "select * from produto order by nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Produto> produtos = new ArrayList<Produto>();
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setNome(rs.getString("nome"));
            produto.setId(rs.getLong("id"));
            produtos.add(produto);
        }

        return produtos;
    }

    @Override
    public void gravar(Produto value) throws Exception {
        String sql = "INSERT INTO produto (nome) VALUES (?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

    }

    @Override
    public void alterar(Produto value) throws Exception {
        String sql = "update produto set nome = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setLong(2, value.getId());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Produto value) throws Exception {
        String sql = "delete from produto where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
    }
}
