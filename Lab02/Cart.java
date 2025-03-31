public class Cart {
    public static final int MAX_NUMBERS_ORDERED = 20;
    private DigitalVideoDisc itemsOrdered[] = new DigitalVideoDisc[MAX_NUMBERS_ORDERED];
    private int qtyOrdered = 0;

    public void addDigitalVideoDisc(DigitalVideoDisc disc) {
        if (qtyOrdered < MAX_NUMBERS_ORDERED) {
            itemsOrdered[qtyOrdered] = disc;
            qtyOrdered++;
            System.out.println("Đĩa đã được thêm");
            if (qtyOrdered == MAX_NUMBERS_ORDERED) {
                System.out.println("Giỏ hàng gần đầy");
            }
        } else {
            System.out.println("Giỏ hàng đã đầy không thể thêm đĩa");
        }
    }

    public void addDigitalVideoDisc(DigitalVideoDisc[] dvdList) {
        for (DigitalVideoDisc dvd : dvdList) {
            addDigitalVideoDisc(dvd);
        }
    }

    public void addDigitalVideoDisc(DigitalVideoDisc dvd1, DigitalVideoDisc dvd2) {
        addDigitalVideoDisc(dvd1);
        addDigitalVideoDisc(dvd2);
    }

    public void removeDigitalVideoDisc(DigitalVideoDisc disc) {
        boolean found = false;
        for (int i = 0; i < qtyOrdered; i++) {
            if (itemsOrdered[i].equals(disc)) {
                for (int j = i; j < qtyOrdered - 1; j++) {
                    itemsOrdered[j] = itemsOrdered[j + 1];
                }
                itemsOrdered[qtyOrdered - 1] = null;
                qtyOrdered--;
                found = true;
                System.out.println("Đĩa đã được xóa");
                break;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy đĩa trong giỏ hàng");
        }
    }

    public float totalCost() {
        float total = 0;
        for (int i = 0; i < qtyOrdered; i++) {
            total += itemsOrdered[i].getCost();
        }
        return total;
    }

    public void displayCart() {
        System.out.println("Các mặt hàng trong giỏ hàng:");
        for (int i = 0; i < qtyOrdered; i++) {
            System.out.println((i + 1) + " - " + itemsOrdered[i].getTitle() + " - " + itemsOrdered[i].getCost());
        }
        System.out.println("Tổng chi phí: " + totalCost());
    }
}