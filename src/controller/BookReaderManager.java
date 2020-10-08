package controller;

import model.Book;
import model.BookReader;
import model.Reader;
import view.Main;

import java.util.*;

public class BookReaderManager {
    public static LinkedList<BookReader> bookReaderList = new LinkedList<>();

    public static void borrowBook() {
        try {
            boolean isReaderExist = false;
            int readerID;
            Scanner scanner = new Scanner(System.in);
            do {
                ReaderManager.displayReaders();

                System.out.println("Enter reader ID: ");
                readerID = Integer.parseInt(scanner.nextLine());
                Set<Map.Entry<Integer, Reader>> entries = ReaderManager.readerMap.entrySet();
                for (Map.Entry<Integer, Reader> readerEntry : entries) {
                    if (readerEntry.getValue().getReaderID() == readerID) {
                        isReaderExist = true;
                    }
                }
                if (isReaderExist == false) {
                    System.err.println("There is no such reader!");
                }
            } while (!isReaderExist);


            if (isReaderExist) {
                // check xem reader có mượn quá 10 quyển không
                boolean isTotalTrue = true;
                int total = 0;

                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getReader().getReaderID() == readerID) {
                        total += bookReader.getBorrowNum();
                    }
                }

                if (total >= 10) {
                    System.err.println("This reader borrowed 10 books!");
                    isTotalTrue = false;
                }


                // nếu reader mượn ít hơn 10 quyển
                int bookID;


                if (isReaderExist && isTotalTrue) {
                    int bookCount;
                    boolean isBookBorrowAllow;
                    boolean isBookExist;
                    do {
                        isBookBorrowAllow = true;
                        BookManager.displayBooks();
                        System.out.println("Enter book ID: ");
                        bookID = Integer.parseInt(scanner.nextLine());

                        // check nếu reader có mượn sách này quá 3 quyển không
                        bookCount = 0;
                        isBookExist = false;
                        Set<Map.Entry<Integer, Book>> entries = BookManager.bookMap.entrySet();
                        for (Map.Entry<Integer, Book> bookEntry : entries) {
                            if (bookEntry.getValue().getBookID() == bookID) {
                                isBookExist = true;
                            }
                        }
                        if (isBookExist == false) {
                            System.err.println("There is no such book!");
                        }
                        for (BookReader bookReader : bookReaderList) {
                            if (bookReader.getReader().getReaderID() == readerID &&
                                    bookReader.getBook().getBookID() == bookID) {
                                bookCount += bookReader.getBorrowNum();
                            }
                        }
                        if (bookCount >= 3) {
                            System.err.println("This reader borrowed 3 of this books! Choose another book!");
                            isBookBorrowAllow = false;
                        }
                    } while (isBookBorrowAllow == false || isBookExist == false);

                    // nếu reader chưa mượn quyển này quá 3
                    Book currentBook = BookManager.bookMap.get(bookID);
                    int borrowNum;
                    if (isBookBorrowAllow) {
                        int bookLeft;
                        do {
                            bookLeft = currentBook.getQuantity();
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

                        // tính phí mượn
                        if (state.equals("new")) {
                            System.out.println("bookName = " + currentBook.getBookName() + "\n"
                                    + "number of borrow = " + borrowNum + "\n" +
                                    "Fee = " + borrowNum * currentBook.getPrice() * 10 / 100);
                        } else if (state.equals("old")) {
                            System.out.println("bookName = " + currentBook.getBookName() + "\n"
                                    + "number of borrow = " + borrowNum + "\n" +
                                    "Fee = " + borrowNum * currentBook.getPrice() * 5 / 100);
                        }

                        BookReader bookReader = new BookReader(ReaderManager.readerMap.get(readerID),
                                BookManager.bookMap.get(bookID),
                                borrowNum, state);
                        bookReaderList.add(bookReader);
                        BookManager.bookMap.get(bookID).setQuantity(bookLeft - borrowNum);
                        FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Your input is wrong! Do again!");
        }

    }

    public static void returnBook() {
        try {
            boolean isReaderExist;
            Scanner scanner = new Scanner(System.in);
            int readerID;
            do {
                ReaderManager.displayReaders();
                System.out.println("Enter reader ID: ");
                readerID = Integer.parseInt(scanner.nextLine());
                isReaderExist = false;
                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getReader().getReaderID() == readerID) {
                        isReaderExist = true;
                    }
                }
                if (isReaderExist == false) {
                    System.err.println("There is no such reader!");
                }
            } while (!isReaderExist);


            boolean isBookExist;
            int bookID;
            do {
                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getReader().getReaderID() == readerID) {
                        bookReader.outputInfo();
                    }
                }
                System.out.println("Enter book ID to take back: ");
                bookID = Integer.parseInt(scanner.nextLine());
                isBookExist = false;
                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getBook().getBookID() == bookID) {
                        isBookExist = true;
                    }
                }
                if (isBookExist == false) {
                    System.err.println("There is no such book in reader's borrowing info!");
                }
            } while (!isBookExist);


            int quantity;
            int borrowNum = 0;
            do {
                System.out.println("Enter the quantity to take back: ");
                quantity = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < bookReaderList.size(); i++) {
                    if (bookReaderList.get(i).getReader().getReaderID() == readerID) {
                        if (bookReaderList.get(i).getBook().getBookID() == bookID) {
                            borrowNum = bookReaderList.get(i).getBorrowNum();
                        }
                    }
                }
            } while (quantity <= 0 || quantity > borrowNum);


            // xóa sách ra khỏi list mượn của bạn đọc
            for (int i = 0; i < bookReaderList.size(); i++) {
                if (bookReaderList.get(i).getReader().getReaderID() == readerID) {
                    if (bookReaderList.get(i).getBook().getBookID() == bookID) {
                        borrowNum = bookReaderList.get(i).getBorrowNum();
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

            //thêm sách trở lại thư viện
            Set<Map.Entry<Integer, Book>> entries = BookManager.bookMap.entrySet();
            for (Map.Entry<Integer, Book> bookEntry : entries) {
                if (bookEntry.getValue().getBookID() == bookID) {
                    int currentQuantity = bookEntry.getValue().getQuantity();
                    bookEntry.getValue().setQuantity(currentQuantity + quantity);
                }
            }
            System.err.println("Succeed!");
            FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);
            FileController.writeBookReaderToFile(bookReaderList, Main.bookReaderFileName);
        } catch (Exception e) {
            System.err.println("Your input is wrong!Do again!");
        }
    }


    public static void displayBookReader() {
        for (BookReader bookReader : bookReaderList) {
            bookReader.outputInfo();
        }
    }

    public static void searchBorrowBookByReader() {
        try {
            boolean isReaderExist = false;
            int readerID;
            do {
                ReaderManager.displayReaders();
                System.out.println("----------------------------------");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter reader ID to search: ");
                readerID = Integer.parseInt(scanner.nextLine());
                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getReader().getReaderID() == readerID) {
                        isReaderExist = true;
                    }
                }
                if (isReaderExist == false) {
                    System.err.println("This reader is borrowing no book");
                }
            } while (!isReaderExist);


            int borrowCount = 0;
            for (BookReader bookReader : bookReaderList) {
                if (bookReader.getReader().getReaderID() == readerID) {
                    borrowCount += bookReader.getBorrowNum();
                    bookReader.outputInfo();
                }
            }
            System.out.println("------------------------------------");
            System.out.println("Total of books this reader is borrowing = " + borrowCount);
        } catch (Exception e) {
            System.err.println("Your input is wrong!Do again!");
        }

    }

    public static void searchBorrowReaderByBook() {
        try {
            Scanner scanner = new Scanner(System.in);
            BookManager.displayBooks();
            boolean isSearchBookExist;
            int bookCount = 0;
            do {
                System.out.println("Enter book ID to search: ");
                int bookID = Integer.parseInt(scanner.nextLine());
                isSearchBookExist = false;
                for (BookReader bookReader : bookReaderList) {
                    if (bookReader.getBook().getBookID() == bookID) {
                        isSearchBookExist = true;
                        bookReader.outputInfo();
                        bookCount += bookReader.getBorrowNum();
                    }
                }

                if (isSearchBookExist == false) {
                    System.err.println("There is no such book in the borrowing list!");
                }
            } while (!isSearchBookExist);
            System.out.println("Total of this book borrowed = " + bookCount);
        } catch (Exception e) {
            System.err.println("Your input is wrong!Do again!");
        }
    }

    public static void sortBooksBorrowed() {
        LinkedHashMap<Book, Integer> hashMap = new LinkedHashMap<>();
        ArrayList<Book> books = new ArrayList<>();
        Set<Map.Entry<Integer, Book>> entries = BookManager.bookMap.entrySet();
        for (Map.Entry<Integer, Book> bookEntry : entries) {
            for (BookReader bookReader : bookReaderList) {
                if (bookEntry.getValue().getBookID() == bookReader.getBook().getBookID()) {
                    books.add(bookEntry.getValue());
                }
            }
        }

        for (Book book : books) {
            int total = 0;
            for (BookReader bookReader : bookReaderList) {
                if (book.getBookID() == bookReader.getBook().getBookID()) {
                    total += bookReader.getBorrowNum();
                }
            }
            hashMap.put(book, total);
        }

        Set<Map.Entry<Book, Integer>> entries1 = hashMap.entrySet();
        ArrayList<Map.Entry<Book, Integer>> arrayList = new ArrayList<>(entries1);


        Collections.sort(arrayList, new Comparator<Map.Entry<Book, Integer>>() {
            @Override
            public int compare(Map.Entry<Book, Integer> o1, Map.Entry<Book, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (Map.Entry<Book, Integer> bookEntry : arrayList) {
            System.out.println("bookID = " + bookEntry.getKey().getBookID() + "|" + "bookName = "
                    + bookEntry.getKey().getBookName() + "|" + "total = " + bookEntry.getValue());
        }
    }
}


