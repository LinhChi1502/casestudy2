package controller;

import model.Book;
import model.BookReader;
import view.Main;

import java.util.*;

public class BookReaderManager {
    public static LinkedList<BookReader> bookReaderList = new LinkedList<>();

    public static void borrowBook() {
        int bookID;
        int borrowNum;
        Scanner scanner = new Scanner(System.in);
        ReaderManager.displayReaders();
        System.out.println("Enter reader ID: ");
        int readerID = Integer.parseInt(scanner.nextLine());

        int total = 0;
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getReader().getReaderID() == readerID) {
                total += bookReader.getBorrowNum();
            }
        }
        boolean isTotalTrue = true;
        if (total >= 10) {
            System.err.println("This reader borrowed 10 books!");
            isTotalTrue = false;
        }


        if (isTotalTrue) {
            int bookCount;
            boolean isBookNumTrue;
            do {
                bookCount = 0;
                isBookNumTrue = true;
                BookManager.displayBooks();
                System.out.println("Enter book ID: ");
                bookID = Integer.parseInt(scanner.nextLine());
                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getReader().getReaderID() == readerID) {
                        if (bookReader.getBook().getBookID() == bookID) {
                            bookCount += bookReader.getBorrowNum();
                        }
                    }
                }
                if (bookCount >= 3) {
                    System.err.println("This reader borrowed 3 of this books! Choose another book!");
                    isBookNumTrue = false;
                }
            } while (!isBookNumTrue);

            if (isBookNumTrue) {
                int bookLeft;
                do {
                    bookLeft = BookManager.bookMap.get(bookID).getQuantity();
                    System.out.println("There are " + bookLeft + " of this books in library");
                    System.out.println("Enter quantity to borrow (Reader can only borrow maximum of 3 each book): ");
                    borrowNum = Integer.parseInt(scanner.nextLine());
                } while (borrowNum == 0 || (bookCount + borrowNum) > 3 || borrowNum > bookLeft);

                int stateChoice;
                String[] stateSet = {"new", "old"};
                do {
                    System.out.println("Enter state of book:\n 1. new 2. old");
                    stateChoice = Integer.parseInt(scanner.nextLine());
                } while (stateChoice < 1 || stateChoice > 2);
                String state = stateSet[stateChoice - 1];

                BookReader bookReader = new BookReader(ReaderManager.readerMap.get(readerID),
                        BookManager.bookMap.get(bookID),
                        borrowNum, state);
                bookReaderList.add(bookReader);
                BookManager.bookMap.get(bookID).setQuantity(bookLeft - borrowNum);
                FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);

                // viết thanh toán bao tiền?
            }
        }
    }

    public static void takeBookBack() {
        Scanner scanner = new Scanner(System.in);
        ReaderManager.displayReaders();
        System.out.println("Enter reader ID: ");
        int readerID = Integer.parseInt(scanner.nextLine());

        for (BookReader bookReader: bookReaderList) {
            if (bookReader.getReader().getReaderID() == readerID) {
                System.out.println(bookReader);
            }
        }

        System.out.println("Enter book ID to take back: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        int quantity;
        do {
            System.out.println("Enter the quantity to take back: ");
            quantity = Integer.parseInt(scanner.nextLine());
        } while (quantity <= 0);


        //thêm sách trở lại thư viện
        Set<Map.Entry<Integer, Book>> entries = BookManager.bookMap.entrySet();
        for (Map.Entry<Integer, Book> bookEntry: entries) {
            if (bookEntry.getValue().getBookID() == bookID) {
                int currentQuantity = bookEntry.getValue().getQuantity();
                bookEntry.getValue().setQuantity(currentQuantity + quantity);
            }
        }
        FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);

        // xóa sách ra khỏi list mượn của bạn đọc
        for (int i = 0; i < bookReaderList.size(); i++) {
            if (bookReaderList.get(i).getReader().getReaderID() == readerID) {
                if (bookReaderList.get(i).getBook().getBookID() == bookID) {
                    int borrowNum = bookReaderList.get(i).getBorrowNum();
                    if (borrowNum <= quantity) {
                        quantity -= borrowNum;
                        bookReaderList.remove(bookReaderList.get(i));
                    } else {
                        borrowNum = borrowNum - quantity;
                        bookReaderList.get(i).setBorrowNum(borrowNum);
                    }

                }
            }
        }
        FileController.writeBookReaderToFile(bookReaderList, Main.bookReaderFileName);
    }

    public static void displayBookReader() {
        for (BookReader bookReader: bookReaderList) {
            System.out.println(bookReader);
        }
    }

    public static void borrowBookManager() {
        LinkedList<BookReader> bookReadersListName = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter reader ID: ");
        int readerID = Integer.parseInt(scanner.nextLine());
        for (BookReader bookReader: bookReaderList) {
            if (bookReader.getReader().getReaderID() ==  readerID) {
                System.out.println(bookReader);
            }
        }

    }
}
//Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Reader>>() {
//            @Override
//            public int compare(Map.Entry<Integer, Reader> o1, Map.Entry<Integer, Reader> o2) {
//                String o1String = ConvertVieToEng.removeAccent(o1.getValue().getReaderName());
//                String o2String = ConvertVieToEng.removeAccent(o2.getValue().getReaderName());
//                return o1String.compareTo(o2String);
//            }
//        });
//
//        for (Map.Entry<Integer, Reader> entry : arrayList) {
//            System.out.println(entry.getValue());
//        }
