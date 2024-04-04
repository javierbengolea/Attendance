/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

/**
 *
 * @author RRHH-SERVER
 */
public class ANDEquation {

    public ANDEquation() {
    }

    public int restoreY(int[] N) {
        int output = -1;

        int temp;

        for (int i = 0; i < N.length - 1; i++) {
            temp = N[i];

            for (int j = 0; j < N.length; j++) {
                if (j != i) {
                    temp &= N[j];
                    System.out.println(N[j]);
                }

            }
            //System.out.println(N[i]);
            if (temp == N[i]) {
                System.err.println(temp);
                //return temp;
            }
        }

        return output;
    }

    public static void main(String[] args) {
        int[] nros = {1, 2, 3, 4};
        new ANDEquation().restoreY(nros);
        //  System.out.println(new ANDEquation().restoreY(nros));

    }

}
