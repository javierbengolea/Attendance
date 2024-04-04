/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author SistemasRC0011
 */
public class Utiles {

    public static void main(String[] args) throws Exception {

        File f = new File("E:\\SIJCOR_a_SICOSS\\ENTRADA\\2022_09.txt");

        BufferedReader br = new BufferedReader(new FileReader(f));

        String linea = br.readLine();

        while (linea != null) {
            System.out.println(linea.replaceAll("\\.", ","));
            linea = br.readLine();
        }

    }

}
