package com.masood;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    private static Double NETJ(int no, double[] x, double[][] w , double[] bias){

        double netj = 0.0;
        for (int i = 1; i <no ; i++) {
            for (int j = no;j<=no ;j++ ) {
                    netj = netj + x[i] * w[i][j];
            }
        }
        return netj+bias[no];
    }

    private static Double ERRORJ(int no,double[] Oj,double Tj,int n ,double[][] w,double[] Error){
            double error = 0.0;
            for (int i = no; i <=no ; i++) {
                if (i==n){
                    error = Oj[i] * (1-Oj[i]) * (Tj-Oj[i]);
                }else {
                    error = Oj[i] * (1-Oj[i])*Error[n]*w[i][n];
                }
        }
        return error;
    }

    private static Double O(double net){
        double O =0.0;
        O = 1 / (1+Math.exp(-net));
        return O;
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.####");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter No of Nodes : ");
        int n = scanner.nextInt();
        System.out.print("Enter No of Input Layer : ");
        int no = scanner.nextInt();
        System.out.println("\nEnter Values for Input Layer :");
        double[] x = new double[20];
        for (int i=1;i<=no;i++){
            System.out.print("Input Layer " +i +" : ");
            x[i] = scanner.nextInt();
        }
        System.out.println("\nEnter Bias Value:");
        double[] bias = new double[20];
        for (int i = no+1; i <=n ; i++) {
            System.out.print("Bias " +i +" : ");
            bias[i] = scanner.nextDouble();
        }

        System.out.print("\nEnter Learning Rate : ");
        double l = scanner.nextDouble();

        System.out.print("Enter Target Value : ");
        double Tj = scanner.nextDouble();

   /*
     int n=6,no=3;
     double[] x = new double[]{0,0,0,0,1,0,1};
     double[] bias = new double[]{0,0,0,0,-0.4,0.2,0.1};
*/
        System.out.println();

     System.out.println("Enter Weights For Each Traversing Node:");
        int hidden_layer = (n-no-1);
        int weight_count = hidden_layer*(no+1);
        double[][] w = new double[100][100];
        int counter = 0;
        for (int i=1;i<=no+1;i++){
            for (int j=no+1;j<n;j++) {
                if (i <= no) {
                    System.out.print("Weight For W" + i + j +" : ");
                    w[i][j] = scanner.nextDouble();
                }else {
                    if(counter==0){
                    System.out.print("Weight For W" + (i)  + (j+hidden_layer) +" : ");
                    w[i][j+hidden_layer] = scanner.nextDouble();
                    counter++;
                    }else if(counter==1) {
                        System.out.print("Weight For W" + (i+counter)  + (j+hidden_layer-1) +" : ");
                        w[i+counter][j+hidden_layer-1] = scanner.nextDouble();
                        counter++;
                    }else {
                        System.out.print("Weight For W" + (i+counter)  + (j+hidden_layer-2) +" : ");
                        w[i+counter][j+hidden_layer-2] = scanner.nextDouble();
                    }
                }
            }
        }

        System.out.println("\nW|B\t\tValue");

        counter=0;
        for (int i=1;i<=no+1;i++){
            for (int j=no+1;j<n;j++) {
                if (i <= no) {
                    System.out.println("W" + (i)  + (j) + "\t\t"+w[i][j]);
                }else {
                    if(counter==0){
                        System.out.println("W" + (i) + (j + hidden_layer) + "\t\t" +w[i][j+hidden_layer]);
                        counter++;
                    }else if(counter==1){
                        System.out.println("W" + (i + counter)  + (j +hidden_layer-1) + "\t\t" +w[i+counter][j+hidden_layer-1]);
                        counter++;
                    }else {
                        System.out.println("W" + (i + counter) + (j + hidden_layer-2) + "\t\t" +w[i+counter][j+hidden_layer-2]);
                    }
                }
            }
        }
        for (int i = no+1; i <=n ; i++) {
            System.out.println("B"+i+"\t\t"+df.format(bias[i]));
        }

        System.out.println("\nStep 1 : Calculate net(j) and O(j)");

        System.out.println();
        double[] net = new double[20];
        double[] Oj = new double[20];
        System.out.println("Unit(j)\t\tnet(j)\t\tO(j)");
        for (int i = no+1; i <=n ; i++) {
                if (i<n){
                    net[i] = NETJ(i,x,w,bias);
                    Oj[i] = O(net[i]);
                    System.out.print("\t"+i );
                    System.out.print("\t\t"+df.format(net[i]));
                    System.out.println("\t\t"+df.format(Oj[i]));
                }else {
                    net[i] = NETJ(i,Oj,w,bias);
                    Oj[i] = O(net[i]);
                    System.out.print("\t"+i);
                    System.out.print("\t\t"+df.format(net[i]));
                    System.out.println("\t\t"+df.format(Oj[i]));
                }
        }

        System.out.println();
        if (Oj[n] < 0.5) {
            System.out.println("Since the output of O(" + n + ") is less than 0.5 it is labelled as 0\nBut the target required is " + Tj + "\nTherefore we need to back propagate the error");
        } else {
            System.out.println("Since the output of O(" + n + ") is greater than 0.5 it is labelled as 1");
        }

        while (Oj[n]<0.5) {

            System.out.println("\nStep 2 : Calculate the Error");
            System.out.println("\nUnit(j)\t\tError(j)");
            double[] error = new double[20];
            for (int i = n; i > no; i--) {
                if (i == n) {
                    error[i] = ERRORJ(i, Oj, Tj, n, w, error);
                    System.out.print("\t" + i);
                    System.out.println("\t\t" + df.format(error[i]));
                } else {
                    error[i] = ERRORJ(i, Oj, Tj, n, w, error);
                    System.out.print("\t" + i);
                    System.out.println("\t\t" + df.format(error[i]));
                }
            }

            System.out.println("\nStep 3 : Update Weights and Bias");

            counter = 0;
            for (int i = 1; i <= no + 1; i++) {
                for (int j = no + 1; j < n; j++) {
                    if (i <= no) {
                        w[i][j] = w[i][j] + l * error[j] * x[i];
                    } else {
                        if (counter == 0) {
                            w[i][j + hidden_layer] = w[i][j + hidden_layer] + l * error[j + hidden_layer] * Oj[i];
                            counter++;
                        } else if (counter == 1) {
                            w[i + counter][j + hidden_layer - 1] = w[i + counter][j + hidden_layer - 1] + l * error[j + hidden_layer - 1] * Oj[i + counter];
                            counter++;
                        } else {
                            w[i + counter][j + hidden_layer - 2] = w[i + counter][j + hidden_layer - 2] + l * error[j + hidden_layer - 2] * Oj[i + counter];
                        }
                    }
                }
            }

            for (int i = no + 1; i <= n; i++) {
                bias[i] = bias[i] + l * error[i];
            }

            System.out.println("\nW|B\t\t\tNew Value");
            counter = 0;
            for (int i = 1; i <= no + 1; i++) {
                for (int j = no + 1; j < n; j++) {
                    if (i <= no) {
                        System.out.println("W" + (i) + (j) + "\t\t\t" + df.format(w[i][j]));
                    } else {
                        if (counter == 0) {
                            System.out.println("W" + (i) + (j + hidden_layer) + "\t\t\t" + df.format(w[i][j + hidden_layer]));
                            counter++;
                        } else if (counter == 1) {
                            System.out.println("W" + (i + counter) + (j + hidden_layer - 1) + "\t\t\t" + df.format(w[i + counter][j + hidden_layer - 1]));
                            counter++;
                        } else {
                            System.out.println("W" + (i + counter) + (j + hidden_layer - 2) + "\t\t\t" + df.format(w[i + counter][j + hidden_layer - 2]));
                        }
                    }
                }
            }
            for (int i = no + 1; i <= n; i++) {
                System.out.println("B" + i + "\t\t\t" + df.format(bias[i]));
            }

            System.out.println("\nStep 4 : Forward Pass");
            System.out.println("\nUnit(j)\t\tnet(j)\t\tO(j)");
            for (int i = no + 1; i <= n; i++) {
                if (i < n) {
                    net[i] = NETJ(i, x, w, bias);
                    Oj[i] = O(net[i]);
                    System.out.print("\t" + i);
                    System.out.print("\t\t" + df.format(net[i]));
                    System.out.println("\t\t" + df.format(Oj[i]));
                } else {
                    net[i] = NETJ(i, Oj, w, bias);
                    Oj[i] = O(net[i]);
                    System.out.print("\t" + i);
                    System.out.print("\t\t" + df.format(net[i]));
                    System.out.println("\t\t" + df.format(Oj[i]));
                }
            }

            System.out.println();
            if (Oj[n] < 0.5) {
                System.out.println("Since the output of O(" + n + ") is less than 0.5 it is labelled as 0\nBut the target required is " + Tj + "\nTherefore we need to back propagate the error");
            } else {
                System.out.println("Since the output of O(" + n + ") is greater than 0.5 it is labelled as 1");
            }

        }

    }
}
