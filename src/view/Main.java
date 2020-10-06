package view;

import controller.BookManager;
import controller.BookReaderManager;
import controller.FileController;
import controller.ReaderManager;

import java.util.Scanner;

public class Main {
    public static String readerFileName = "readers.dat";
    public static String bookFileName = "books.dat";
    public static String bookReaderFileName = "bookReader.dat";
    public static void menu() {


        System.out.println("----------------BOOK MANAGEMENT----------------");
        System.out.println("1. Add new book");
        System.out.println("2. Display book list");
        System.out.println("3. Edit book list");
        System.out.println("4. Delete book");
        System.out.println("5. Search book");
        System.out.println("6. Sort book list");
        System.out.println("----------------READER MANAGEMENT--------------");
        System.out.println("7. Add new reader");
        System.out.println("8. Display reader list");
        System.out.println("9. Edit reader list");
        System.out.println("10. Delete reader");
        System.out.println("11. Search reader");
        System.out.println("12. Sort reader list by name");
        System.out.println("----------------BORROWING MANAGEMENT--------------");
        System.out.println("13. Borrow new book");
        System.out.println("14. Take book back");
        System.out.println("15. Show borrowing list");
        System.out.println("16. Tim sach muon theo nguoi doc");
        System.out.println("-------------------------------------");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                BookManager.addBook();
                FileController.writeBookToFile(BookManager.bookMap, bookFileName);
            }
            case 2 -> BookManager.displayBooks();
            case 3 -> {
                BookManager.editBook();
                FileController.writeBookToFile(BookManager.bookMap, bookFileName);
            }
            case 4 -> {
                BookManager.deleteBook();
                FileController.writeBookToFile(BookManager.bookMap, bookFileName);
            }
            case 5 -> {
                System.out.println("-------------SEARCH BY MENU-------------");
                System.out.println("1. Search by name\n2. Search by author\n" +
                        "3. Search by specialization\n4. Search by publisher");
                System.out.println("----------------------------------------");
                System.out.println("Enter your choice: ");
                int searchChoice = scanner.nextInt();
                switch (searchChoice) {
                    case 1 -> BookManager.searchBookByName();
                    case 2 -> BookManager.searchBookByAuthor();
                    case 3 -> BookManager.searchBookBySpecialization();
                    case 4 -> BookManager.searchBookByPublisher();
                }
            }
            case 6 -> {
                System.out.println("--------------SORT BY MENU--------------");
                System.out.println("1. Sort by name\n2. Sort by price");
                System.out.println("----------------------------------------");
                System.out.println("Enter your choice: ");
                int sortChoice = Integer.parseInt(scanner.nextLine());
                switch (sortChoice) {
                    case 1 -> BookManager.sortBookByName();
                    case 2 -> BookManager.sortBookPrice();
                }
            }
            case 7 -> {
                ReaderManager.addReader();
                FileController.writeReaderToFile(ReaderManager.readerMap, readerFileName);
            }
            case 8 -> ReaderManager.displayReaders();
            case 9 -> {
                ReaderManager.editReader();
                FileController.writeReaderToFile(ReaderManager.readerMap, readerFileName);
            }
            case 10 -> {
                ReaderManager.deleteReader();
                FileController.writeReaderToFile(ReaderManager.readerMap, readerFileName);
            }
            case 11 -> {
                System.out.println("-------------SEARCH BY MENU-------------");
                System.out.println("1. Search by name\n2. Search by address");
                System.out.println("----------------------------------------");
                System.out.println("Enter your choice: ");
                int searchChoice = scanner.nextInt();
                switch (searchChoice) {
                    case 1 -> ReaderManager.searchReaderByName();
                    case 2 -> ReaderManager.searchReaderByAddress();
                }
            }
            case 12 -> ReaderManager.sortReaderByName();
            case 13 -> {
                BookReaderManager.borrowBook();
                FileController.writeBookReaderToFile(BookReaderManager.bookReaderList, bookReaderFileName);
            }
            case 14 -> {
                BookReaderManager.takeBookBack();
                FileController.writeBookReaderToFile(BookReaderManager.bookReaderList, bookReaderFileName);
            }
            case 15 -> BookReaderManager.displayBookReader();
            case 16 -> BookReaderManager.borrowBookManager();
            case 0 -> {
                System.err.println("Thank you!");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        ReaderManager.readerMap = FileController.readReaderFromFile(readerFileName);
        BookManager.bookMap = FileController.readBookFromFile(bookFileName);
        BookReaderManager.bookReaderList = FileController.readBookReaderFromFile(bookReaderFileName);
        while (true) {
            menu();
        }
    }

    // 1. gmail
    //2. phone number
    // 3. tìm kiếm số lượng sách mượn theo người đọc --> người này đang mượn n sách, danh sách là....
    // 4. tìm kiếm người đọc theo sách --> có n người đang mượn , danh sách là.....
    //5. sắp xếp số sách được mượn
    //6. thanh toán bổ sung vào chức năng mượn. nếu sách cũ, phí = số lượng * giá sách * 5%. nếu sách mới,
    // phí = số lượng * giá sách * 10%
// sửa lại search vừa start with vừa equals
    // sửa xóa nếu không có tên thì thông báo
}
