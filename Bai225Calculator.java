import java.util.Scanner;

public class Bai225Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập số thứ nhất
        System.out.print("Enter the first number: ");
        double num1 = Double.parseDouble(scanner.nextLine());

        // Nhập số thứ hai
        System.out.print("Enter the second number: ");
        double num2 = Double.parseDouble(scanner.nextLine());

        // Tính toán
        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;
        Double quotient = (num2 != 0) ? (num1 / num2) : null; // Kiểm tra chia cho 0

        // Hiển thị kết quả
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
        System.out.println("Product: " + product);
        if (quotient != null) {
            System.out.println("Quotient: " + quotient);
        } else {
            System.out.println("Error: Division by zero is not allowed.");
        }

        scanner.close();
    }
}
