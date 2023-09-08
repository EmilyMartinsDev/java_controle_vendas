/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.model.Clientes;
import br.com.projeto.model.Fornecedor;
import br.com.projeto.model.ItemVenda;
import br.com.projeto.model.Produto;
import br.com.projeto.model.Venda;
import br.projeto.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ALEX
 */
public class ItemVendaDAO {
     private Connection con;

    public ItemVendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    public void cadastrarItem(ItemVenda obj){
        try {
            String sql = "insert into tb_itensvendas (venda_id, produto_id, qtd, subtotal) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());
  

            stmt.execute();
            stmt.close();

    

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao cadastrar" + err);
        } 
    }
    
     public List<ItemVenda> listaItensComprados( int venda_id) {

        try {
            List<ItemVenda> lista = new ArrayList<>();

            String sql = "select i.id , p.descricao, i.qtd, p.preco, i.subtotal from tb_itensvendas as i inner join tb_produtos as p on (i.produto_id = p.id) where i.venda_id =? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, venda_id);
      
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemVenda obj = new ItemVenda();
                Produto p = new Produto();
                p.setDescricao(rs.getString("p.descricao"));
                p.setPreco(rs.getDouble("p.preco"));
                obj.setProduto(p);
                obj.setId(rs.getInt("i.id"));
                obj.setQtd(rs.getInt("i.qtd"));
                obj.setSubtotal(rs.getDouble("i.subtotal"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao listar itens comprados" + e);

            return null;
        }
    }
    
}
