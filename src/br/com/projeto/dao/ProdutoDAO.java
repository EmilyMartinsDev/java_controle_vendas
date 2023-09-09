/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.model.Fornecedor;
import br.com.projeto.model.Produto;
import br.projeto.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ALEX
 */
public class ProdutoDAO {

    private Connection con;

    public ProdutoDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void cadastrarProduto(Produto obj) {
        try {
            String sql = "insert into tb_produtos (descricao, preco, qtd_estoque, for_id) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao cadastrar" + err);
        }
    }

    public List<Produto> listarproduto() {
        try {
            List lista = new ArrayList<>();
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id)";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto obj = new Produto();
                Fornecedor f = new Fornecedor();
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));

                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);

                lista.add(obj);
            }
            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao listar" + e);

            return null;
        }
    }

    public void alterarProduto(Produto obj) {
        try {
            String sql = "update tb_produtos set descricao=?, preco=?, qtd_estoque=?, for_id=? where id=?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setInt(5, obj.getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Editado com sucesso");

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao editar" + err);
        }
    }

    public void excluirProduto(Produto obj) {
        try {
            String sql = "delete from tb_produtos where id=?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Excluido com sucesso");

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao excluir" + err);
        }
    }

    public List<Produto> listaProdutosTela(String nome) {

        try {
            List lista = new ArrayList<>();

            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao like?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto obj = new Produto();
                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));

                Fornecedor f = new Fornecedor();
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);

                lista.add(obj);
            }

            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao listar" + e);

            return null;
        }
    }

    public Produto listarProdutoPorNomeTela(String nome) {
        try {

            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Produto obj = new Produto();
            Fornecedor f = new Fornecedor();
            while (rs.next()) {

                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));

                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);

            }

            return obj;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao listar" + e);

            return null;
        }

    }

    public Produto listarProdutoPorCodigoTela(int id) {
        try {

            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p inner join tb_fornecedores as f on (p.for_id = f.id) where p.id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Produto obj = new Produto();
            Fornecedor f = new Fornecedor();
            while (rs.next()) {

                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));

                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);

            }

            return obj;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao listar" + e);

            return null;
        }

    }

    public void baixaEstoque(int id, int qtdNova) {
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, qtdNova);
            stmt.setInt(2, id);

            stmt.execute();
            stmt.close();

            //  JOptionPane.showMessageDialog(null, "atualizado com sucesso");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao dar baixa" + err);
        }
    }

    public int retornaqtdEstoqueAtual(int id) {
        try {

            int qtd_estoque = 0;

            String sql = "select qtd_estoque from tb_produtos where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();

                qtd_estoque = (rs.getInt("qtd_estoque"));
            }
            return qtd_estoque;

            //  JOptionPane.showMessageDialog(null, "atualizado com sucesso");
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }
    
    
    
    public void adicionarNoEstoque(int id, int qtdNova) {
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, qtdNova);
            stmt.setInt(2, id);

            stmt.execute();
            stmt.close();

            //  JOptionPane.showMessageDialog(null, "atualizado com sucesso");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao dar adicionar" + err);
        }
    }
    
    

}
