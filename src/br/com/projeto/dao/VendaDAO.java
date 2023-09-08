/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.model.Clientes;
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
public class VendaDAO {

    private Connection con;

    public VendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void cadastrarVenda(Venda obj) {
        try {
            String sql = "insert into tb_vendas (cliente_id, data_venda, total_venda) values (?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
  

            stmt.execute();
            stmt.close();

       

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "erro ao cadastrar" + err);
        }
    }

    public int retornaUltimaVenda() {
        try {
            int idVenda = 0;
            String sql = "select max(id) id from tb_vendas ";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                Venda p = new Venda();
                p.setId(rs.getInt("id"));
                idVenda = p.getId();
            }
            
            return idVenda;
            
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }
    
     public List<Venda> listarVendaPeriodo( LocalDate data_inicio, LocalDate data_fim) {

        try {
            List lista = new ArrayList<>();

            String sql = "select d.id, date_format(d.data_venda, '%d/%m/%Y') as data_formatada, c.nome, d.total_venda from tb_vendas as d inner join tb_clientes as c on ( d.cliente_id = c.id) where d.data_venda BETWEEN? AND ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda obj = new Venda();
                Clientes c = new Clientes();
                
                obj.setId(rs.getInt("d.id"));
                obj.setData_venda(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
               obj.setCliente(c);
               obj.setTotal_venda(rs.getDouble("d.total_venda"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao listar" + e);

            return null;
        }
    }

}
