/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Bussiness.Facade;

/**
 *
 * @author André
 */
public class Main {
    public static void main(String[] args){
        Facade f = new Facade();
        IntroInterface ii = new IntroInterface(f);
        ii.setLocationRelativeTo(null);
        ii.setVisible(true);
    }
}
