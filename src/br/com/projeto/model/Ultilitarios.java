/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.model;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ALEX
 */
public class Ultilitarios {
    public void LimpaTela(JPanel container){
        Component components[] = container.getComponents();
        for(Component componente: components){
            if(componente instanceof JTextField){
                ((JTextField) componente).setText(null);
            }
        }
    }
}
