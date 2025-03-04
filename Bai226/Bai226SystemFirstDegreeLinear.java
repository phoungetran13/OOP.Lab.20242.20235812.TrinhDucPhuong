package Bai226;

import java.util.Scanner;

public class Bai226SystemFirstDegreeLinear {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a: ");
        double a = scanner.nextDouble();
        System.out.print("Enter b: ");
        double b = scanner.nextDouble();
        System.out.print("Enter c: ");
        double c = scanner.nextDouble();

        if (a == 0) {
            System.out.println("This is not a quadratic equation.");
        } else {
            double delta = b * b - 4 * a * c;
            if (delta < 0) {
                System.out.println("No real solutions.");
            } else if (delta == 0) {
                double x = -b / (2 * a);
                System.out.println("Double root: x = " + x);
            } else {
                double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                System.out.println("Two distinct roots: x1 = " + x1 + ", x2 = " + x2);
            }
        }

        scanner.close();
    }
}
