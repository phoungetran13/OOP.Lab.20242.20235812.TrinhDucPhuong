import java.util.Scanner;

public class Bai64DaysOfMonth {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year;
        int month = 0;
        boolean validInput = false;

        // Nhập năm hợp lệ
        do {
            System.out.print("Enter a valid year (non-negative integer): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid year.");
                scanner.next(); // Bỏ qua giá trị không hợp lệ
            }
            year = scanner.nextInt();
        } while (year < 0);

        // Nhập tháng hợp lệ
        scanner.nextLine(); // Xóa bộ đệm
        do {
            System.out.print("Enter a month (full name, abbreviation, 3-letter form, or number 1-12): ");
            String input = scanner.nextLine().trim();

            switch (input.toLowerCase()) {
                case "january", "jan.", "jan", "1" -> { month = 1; validInput = true; }
                case "february", "feb.", "feb", "2" -> { month = 2; validInput = true; }
                case "march", "mar.", "mar", "3" -> { month = 3; validInput = true; }
                case "april", "apr.", "apr", "4" -> { month = 4; validInput = true; }
                case "may", "5" -> { month = 5; validInput = true; }
                case "june", "jun.", "jun", "6" -> { month = 6; validInput = true; }
                case "july", "jul.", "jul", "7" -> { month = 7; validInput = true; }
                case "august", "aug.", "aug", "8" -> { month = 8; validInput = true; }
                case "september", "sept.", "sep", "9" -> { month = 9; validInput = true; }
                case "october", "oct.", "oct", "10" -> { month = 10; validInput = true; }
                case "november", "nov.", "nov", "11" -> { month = 11; validInput = true; }
                case "december", "dec.", "dec", "12" -> { month = 12; validInput = true; }
                default -> System.out.println("Invalid month. Please try again.");
            }
        } while (!validInput);

        // Xác định số ngày trong tháng
        int days;
        switch (month) {
            case 4, 6, 9, 11 -> days = 30;
            case 2 -> {
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    days = 29; // Năm nhuận
                } else {
                    days = 28;
                }
            }
            default -> days = 31;
        }

        // Hiển thị kết quả
        System.out.println("Number of days in the selected month and year: " + days);
        
        scanner.close();
    }
}
