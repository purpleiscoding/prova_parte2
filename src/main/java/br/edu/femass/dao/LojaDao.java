package br.edu.femass.dao;

import br.edu.femass.model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LojaDao extends DaoPostgres implements  Dao<Cliente>{
    @Override
    public List<Cliente> listar() throws Exception {
        String sql = "select * from cliente order by nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Cliente> Clientes = new ArrayList<Cliente>();
        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setNome(rs.getString("nome"));
            cliente.setSobreNome(rs.getString("sobrenome"));
            cliente.setId(rs.getLong("id"));
            Clientes.add(cliente);
        }

        return Clientes;
    }

    @Override
    public void gravar(Cliente value) throws Exception {
        String sql = "INSERT INTO cliente (nome, sobrenome) VALUES (?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getSobreNome());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

    }

    @Override
    public void alterar(Cliente value) throws Exception {
        String sql = "update cliente set nome = ?, sobrenome = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setString(2, value.getSobreNome());
        ps.setLong(3, value.getId());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Cliente value) throws Exception {
        String sql = "delete from cliente where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
    }
}
