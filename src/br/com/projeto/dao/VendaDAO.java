/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.model.Venda;
import br.projeto.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String sql = "insert into tb_vendas (cliente_id, data_venda, total_venda, observacoes) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObs());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso");

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

}
