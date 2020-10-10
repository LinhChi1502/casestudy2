package controller;

import model.Book;
import model.BookReader;
import model.Reader;
import view.Main;

import java.util.*;

public class BookReaderManager {
    public static LinkedList<BookReader> bookReaderList = new LinkedList<>();

    public static void borrowBook(int readerID, int bookID, int numOfBookToBorrow, String state) {
        BookReader bookReader = new BookReader(ReaderManager.readerMap.get(readerID), BookManager.bookMap.get(bookID),
                numOfBookToBorrow, state);
        bookReaderList.add(bookReader);
    }

    public static void returnBook(int readerID, int bookID, int numOfBookToReturn) {
        //thêm sách trở lại thư viện
        Set<Map.Entry<Integer, Book>> entries = BookManager.bookMap.entrySet();
        for (Map.Entry<Integer, Book> bookEntry : entries) {
            Book book = bookEntry.getValue();
            if (book.getBookID() == bookID) {
                int currentQuantity = book.getQuantity();
                book.setQuantity(currentQuantity + numOfBookToReturn);
            }
        }
        FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);

        // xóa sách ra khỏi list mượn của bạn đọc
        for (int i = 0; i < bookReaderList.size(); i++) {
            Reader reader = BookReaderManager.bookReaderList.get(i).getReader();
            Book book = BookReaderManager.bookReaderList.get(i).getBook();
            if (reader.getReaderID() == readerID) {
                if (book.getBookID() == bookID) {
                    int totalBooksBorrowed = bookReaderList.get(i).getBorrowNum();
                    if (totalBooksBorrowed <= numOfBookToReturn) {
                        numOfBookToReturn -= totalBooksBorrowed;
                        bookReaderList.remove(bookReaderList.get(i));
                    } else {
                        totalBooksBorrowed = totalBooksBorrowed - numOfBookToReturn;
                        bookReaderList.get(i).setBorrowNum(totalBooksBorrowed);
                    }
                }
            }
        }


    }

    public static void displayBookReader() {
        for (BookReader bookReader : bookReaderList) {
            bookReader.outputInfo();
        }
    }

    public static void searchBorrowBookByReader(int readerID) {
        int borrowCount = 0;
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getReader().getReaderID() == readerID) {
                bookReader.outputInfo();
                borrowCount += bookReader.getBorrowNum();
            }
        }
        System.out.println();
        System.out.println("Total of books borrowed = " + borrowCount);
        System.out.println();
    }

    public static void searchBorrowReaderByBook(int bookID) {
        int borrowCount = 0;
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getBook().getBookID() == bookID) {
                bookReader.outputInfo();
                borrowCount += bookReader.getBorrowNum();
            }
        }
        System.out.println();
        System.out.println("Total of books borrowed = " + borrowCount);
        System.out.println();
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

    public static boolean checkIsTotalExceed10(int readerID) {
        int total = 0;
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getReader().getReaderID() == readerID) {
                total += bookReader.getBorrowNum();
            }
        }
        return total >= 10;
    }

    public static boolean checkIsBookExceed3(int readerID, int bookID) {
        int numOfBookBorrowed = countBookBorrowed(readerID, bookID);
        return numOfBookBorrowed >= 3;
    }

    public static int countBookBorrowed(int readerID, int bookID) {
        int numOfBookBorrowed = 0;
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getReader().getReaderID() == readerID &&
                    bookReader.getBook().getBookID() == bookID) {
                numOfBookBorrowed += bookReader.getBorrowNum();
            }
        }
        return numOfBookBorrowed;
    }

    public static double calculateFee(double price, int numOfBookToBorrow, String state) {
        double fee = 0;
        if (state.equals("new")) {
            fee = numOfBookToBorrow * price * 10 / 100;
        } else if (state.equals("old")) {
            fee = numOfBookToBorrow * price * 5 / 100;
        }
        return fee;
    }

    public static boolean checkIsReaderExist(int readerID) {
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getReader().getReaderID() == readerID) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIsBookExist(int bookID) {
        for (BookReader bookReader : bookReaderList) {
            if (bookReader.getBook().getBookID() == bookID) {
                return true;
            }
        }
        return false;
    }
}


