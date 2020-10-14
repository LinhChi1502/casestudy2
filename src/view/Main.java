package view;

import controller.*;
import model.Book;
import model.BookReader;
import model.Reader;

import java.util.*;

public class Main {
    public static String readerFileName = "readers.dat";
    public static String bookFileName = "books.dat";
    public static String bookReaderFileName = "bookReader.dat";

    public static void menuForManager() {
        try {
            System.out.println("----------------BOOK MANAGEMENT----------------");
            System.out.println("1. Add new book");
            System.out.println("2. Display book list");
            System.out.println("3. Edit book");
            System.out.println("4. Delete book");
            System.out.println("5. Search book");
            System.out.println("6. Sort book list");
            System.out.println("----------------READER MANAGEMENT--------------");
            System.out.println("7. Add new reader");
            System.out.println("8. Display reader list");
            System.out.println("9. Edit reader");
            System.out.println("10. Delete reader");
            System.out.println("11. Search reader");
            System.out.println("12. Sort reader list by name");
            System.out.println("-------------------------------------");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());
            Set<Map.Entry<Integer, Book>> bookEntries = BookManager.bookMap.entrySet();
            Set<Map.Entry<Integer, Reader>> readerEntries = ReaderManager.readerMap.entrySet();
            switch (choice) {

                case 1 -> {
                    System.out.println("-------------------------");
                    System.out.println("Enter bookname: ");
                    String bookName = scanner.nextLine();

                    int bookID = 0;
                    for (Map.Entry<Integer, Book> bookEntry : bookEntries) {
                        Book book = bookEntry.getValue();
                        if (book.getBookName().equals(bookName)) {
                            bookID = book.getBookID();
                            break;
                        }
                    }

                    System.out.println("Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    boolean isIdCheck = false;
                    if (!isIdCheck) {
                        BookManager.changeBookId();
                        isIdCheck = true;
                    }

                    boolean isBookExist = BookManager.checkBookExist(bookID);
                    if (isBookExist) {
                        // nếu book đã tồn tại thì tăng quantity lên
                        for (Map.Entry<Integer, Book> bookEntry : bookEntries) {
                            Book book = bookEntry.getValue();
                            if (book.getBookName().equals(bookName)) {
                                quantity += book.getQuantity();
                                book.setQuantity(quantity);
                                System.out.println("Increased quantity of this book!");
                            }
                        }
                    } else {
                        System.out.println("Enter author: ");
                        String author = scanner.nextLine();

                        int specChoice;
                        String[] specList = {"Science", "Art", "Economics", "IT"};
                        do {
                            System.out.println("-----------SPECIALIZATION-----------");
                            System.out.println("1. Science\n2. Art\n3. Economics\n4. IT");
                            System.out.println("Enter specialization: ");
                            specChoice = Integer.parseInt(scanner.nextLine());
                        } while (specChoice < 1 || specChoice > 4);
                        String specialization = specList[specChoice - 1];

                        System.out.println("Enter price: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        System.out.println("Enter publisher: ");
                        String publisher = scanner.nextLine();

                        Book book = new Book(0, bookName, author, specialization, price, quantity, publisher);
                        BookManager.addBook(book);
                    }
                }
                case 2 -> BookManager.displayBooks();
                case 3 -> {
                    BookManager.displayBooks();
                    System.out.println("Enter book ID to edit: ");
                    int bookID = Integer.parseInt(scanner.nextLine());

                    boolean isBookExist = BookManager.checkBookExist(bookID);
                    if (isBookExist) {
                        System.out.println("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.println("Enter new author: ");
                        String newAuthor = scanner.nextLine();
                        int specChoice;
                        String[] specList = {"Science", "Art", "Economics", "IT"};
                        do {
                            System.out.println("-----------SPECIALIZATION-----------");
                            System.out.println("1. Science\n2. Art\n3. Economics\n4. IT");
                            System.out.println("Enter new specialization: ");
                            specChoice = Integer.parseInt(scanner.nextLine());
                        } while (specChoice < 1 || specChoice > 4);
                        String newSpec = specList[specChoice - 1];
                        System.out.println("Enter new price: ");
                        double newPrice = Double.parseDouble(scanner.nextLine());

                        System.out.println("Enter new quantity: ");
                        int newQuantity = Integer.parseInt(scanner.nextLine());

                        System.out.println("Enter new publisher: ");
                        String newPublisher = scanner.nextLine();
                        Set<Map.Entry<Integer, Book>> entries = BookManager.bookMap.entrySet();
                        for (Map.Entry<Integer, Book> bookEntry : entries) {
                            Book book = bookEntry.getValue();
                            if (book.getBookID() == bookID) {
                                BookManager.editBook(book, newName, newAuthor, newSpec, newPrice, newQuantity,
                                        newPublisher);
                                FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);
                            }
                        }
                    } else {
                        System.err.println("There is no such book!");
                    }
                }
                case 4 -> {
                    System.out.println("Enter book name to delete: ");
                    String bookName = scanner.nextLine();
                    ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(bookEntries);
                    boolean isSearchBookNameExist = false;
                    for (Map.Entry<Integer, Book> entry : arrayList) {
                        Book book = entry.getValue();
                        if (book.getBookName().equalsIgnoreCase(bookName)) {
                            isSearchBookNameExist = true;
                            int deleteBookID = book.getBookID();
                            BookManager.deleteBook(deleteBookID);
                            System.err.println("Book Deleted!");
                        }
                    }
                    if (isSearchBookNameExist == false) {
                        System.err.println("There is no such book!");
                    }
                }
                case 5 -> {
                    System.out.println("-------------SEARCH BY MENU-------------");
                    System.out.println("1. Search by name\n2. Search by author\n" +
                            "3. Search by specialization\n4. Search by publisher");
                    System.out.println("----------------------------------------");
                    System.out.println("Enter your choice: ");
                    int searchChoice = scanner.nextInt();
                    switch (searchChoice) {
                        case 1 -> {
                            System.out.println("Enter book name to search: ");
                            String bookName = new Scanner(System.in).nextLine();
                            String bookSearchName = ConvertVieToEng.removeAccent(bookName).toLowerCase();
                            BookManager.searchBookByName(bookSearchName);
                        }
                        case 2 -> {
                            System.out.println("Enter author name to search: ");
                            String author = new Scanner(System.in).nextLine();
                            String searchAuthor = ConvertVieToEng.removeAccent(author).toLowerCase();
                            BookManager.searchBookByAuthor(searchAuthor);
                        }
                        case 3 -> {
                            int specChoice;
                            String[] specList = {"Science", "Art", "Economics", "IT"};
                            do {
                                System.out.println("-----------SPECIALIZATION-----------");
                                System.out.println("1. Science\n2. Art\n3. Economics\n4. IT");
                                System.out.println("Enter specialization to search: ");
                                specChoice = Integer.parseInt(new Scanner(System.in).nextLine());
                            } while (specChoice < 1 || specChoice > 4);
                            String specialization = specList[specChoice - 1];
                            BookManager.searchBookBySpecialization(specialization);
                        }
                        case 4 -> {
                            System.out.println("Enter publisher to search: ");
                            String publisher = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
                            BookManager.searchBookByPublisher(publisher);
                        }
                    }
                }
                case 6 -> {
                    System.out.println("--------------SORT BY MENU--------------");
                    System.out.println("1. Sort by name\n2. Sort by price");
                    System.out.println("----------------------------------------");
                    System.out.println("Enter your choice: ");
                    int sortChoice = Integer.parseInt(scanner.nextLine());
                    switch (sortChoice) {
                        case 1 -> {
                            System.out.println("-----------------------------");
                            System.out.println("1. Ascending order");
                            System.out.println("2. Descending order");
                            System.out.println("-----------------------------");
                            System.out.println("Enter your choice: ");
                            int nameSortChoice = scanner.nextInt();
                            switch (nameSortChoice) {
                                case 1 -> BookManager.sortNameAscending();
                                case 2 -> BookManager.sortNameDescending();
                            }

                        }
                        case 2 -> {
                            System.out.println("-----------------------------");
                            System.out.println("1. Ascending order");
                            System.out.println("2. Descending order");
                            System.out.println("-----------------------------");
                            System.out.println("Enter your choice: ");
                            int priceSortChoice = scanner.nextInt();
                            switch (priceSortChoice) {
                                case 1 -> BookManager.sortPriceAscending();
                                case 2 -> BookManager.sortPriceDescending();
                            }
                        }
                    }
                }
                case 7 -> {
                    boolean isCheck = false;

                    if (!isCheck) {
                        ReaderManager.changeReaderId();
                        isCheck = true;
                    }

                    System.out.println("-------------------------");
                    Scanner input = new Scanner(System.in);
                    System.out.println("Enter reader name: ");
                    String readerName = input.nextLine();

                    String phoneNumber;
                    boolean isExist = false;
                    do {
                        System.out.println("Enter phone number (eg. 012.345.6789): ");
                        phoneNumber = input.nextLine();
                        for (Map.Entry<Integer, Reader> entry : readerEntries) {
                            if (entry.getValue().getPhoneNumber().equals(phoneNumber)) {
                                isExist = true;
                                System.err.println("This phone number has already existed. Enter again!");
                            } else {
                                isExist = false;
                            }
                        }
                    } while (!phoneNumber.matches("\\d{3}.\\d{3}.\\d{4}") || isExist);
                    String email;
                    boolean isEmailExist = false;
                    do {
                        System.out.println("Enter email: ");
                        email = input.nextLine();
                        for (Map.Entry<Integer, Reader> readerEntry : readerEntries) {
                            if (readerEntry.getValue().getEmail().equals(email)) {
                                isEmailExist = true;
                                System.err.println("This email has already existed. Enter again!");
                                break;
                            } else {
                                isEmailExist = false;
                            }
                        }
                    } while (isEmailExist || !email.matches("^(.*?)@gmail.com"));

                    System.out.println("Enter address: ");
                    String address = input.nextLine();

                    Reader reader = new Reader(0, readerName, phoneNumber, email, address);
                    ReaderManager.addReader(reader);
                }
                case 8 -> ReaderManager.displayReaders();
                case 9 -> {
                    ReaderManager.displayReaders();
                    System.out.println("Enter reader ID to edit: ");
                    int readerID = Integer.parseInt(scanner.nextLine());

                    boolean isReaderExist = ReaderManager.checkReaderExist(readerID);
                    if (isReaderExist) {
                        System.out.println("Enter new name: ");
                        String newReaderName = scanner.nextLine();

                        String newPhoneNumber;

                        do {
                            System.out.println("Enter new phone number (eg. 012.345.6789): ");
                            newPhoneNumber = scanner.nextLine();
                        } while (!newPhoneNumber.matches("\\d{3}.\\d{3}.\\d{4}"));

                        String newEmail;
                        System.out.println("Enter new email: ");
                        newEmail = scanner.nextLine();
                        System.out.println("Enter new address: ");
                        String newAddress = scanner.nextLine();

                        for (Map.Entry<Integer, Reader> readerEntry : readerEntries) {
                            Reader reader = readerEntry.getValue();
                            if (reader.getReaderID() == readerID) {
                                ReaderManager.editReader(reader, newReaderName, newPhoneNumber, newEmail, newAddress);
                            }
                        }
                    } else {
                        System.err.println("There is no such reader!");
                    }
                }
                case 10 -> {
                    ReaderManager.displayReaders();
                    System.out.println("Enter reader ID to delete: ");
                    int readerID = Integer.parseInt(scanner.nextLine());


                    ArrayList<Map.Entry<Integer, Reader>> arrayList = new ArrayList<>(readerEntries);
                    boolean isSearchReaderExist = false;
                    for (Map.Entry<Integer, Reader> entry : arrayList) {
                        Reader reader = entry.getValue();
                        if (reader.getReaderID() == readerID) {
                            isSearchReaderExist = true;
                            ReaderManager.deleteReader(readerID);
                            System.err.println("Reader Deleted");
                        }
                    }
                    if (!isSearchReaderExist) {
                        System.err.println("There is no such reader!");
                    }
                }
                case 11 -> {
                    System.out.println("-------------SEARCH BY MENU-------------");
                    System.out.println("1. Search by name\n2. Search by address");
                    System.out.println("----------------------------------------");
                    System.out.println("Enter your choice: ");
                    int searchChoice = scanner.nextInt();
                    switch (searchChoice) {
                        case 1 -> {
                            System.out.println("Enter reader name to search: ");
                            String readerName = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
                            ReaderManager.searchReaderByName(readerName);
                        }
                        case 2 -> {
                            System.out.println("Enter address to search: ");
                            String address = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
                            ReaderManager.searchReaderByAddress(address);
                        }
                    }
                }
                case 12 -> {
                    System.out.println("-----------------------------");
                    System.out.println("1. Ascending order");
                    System.out.println("2. Descending order");
                    System.out.println("-----------------------------");
                    System.out.println("Enter your choice: ");
                    int readerSortChoice = scanner.nextInt();
                    switch (readerSortChoice) {
                        case 1 -> ReaderManager.sortReaderNameAscending();
                        case 2 -> ReaderManager.sortReaderNameDescending();
                    }
                }

                case 0 -> {
                    System.err.println("Thank you!");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.err.println("Your input is wrong!Do again!");
        }
    }

    public static void menuforLibrarian() {
        try {
            System.out.println("----------------BORROWING MANAGEMENT--------------");
            System.out.println("1. Borrow new book");
            System.out.println("2. Return book");
            System.out.println("3. Show borrowing list");
            System.out.println("4. Search book borrowed by reader");
            System.out.println("5. Search reader borrowing by book");
            System.out.println("6. Sort books borrowed");
            System.out.println("-------------------------------------");
            System.out.println("0. Exit");
            System.out.println("-------------------------------------");

            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    boolean isReaderExist;
                    int readerID;
                    do {
                        ReaderManager.displayReaders();
                        System.out.println("Enter reader ID: ");
                        readerID = Integer.parseInt(scanner.nextLine());
                        isReaderExist = ReaderManager.checkReaderExist(readerID);
                        if (!isReaderExist) {
                            System.err.println("There is no such reader!");
                        }
                    } while (!isReaderExist);

                    if (isReaderExist) {
                        boolean isTotalExceed10 = BookReaderManager.checkIsTotalExceed10(readerID);
                        if (isTotalExceed10) {
                            System.err.println("This reader borrowed 10 books!");
                        } else {
                            int bookID;
                            boolean isBookExist;
                            boolean isBookExceed3;
                            do {
                                BookManager.displayBooks();
                                System.out.println("Enter book ID: ");
                                bookID = Integer.parseInt(scanner.nextLine());
                                isBookExist = BookManager.checkBookExist(bookID);
                                isBookExceed3 = BookReaderManager.checkIsBookExceed3(readerID, bookID);
                                if (!isBookExist) {
                                    System.err.println("There is no such book!");
                                }
                                if (isBookExceed3) {
                                    System.err.println("This reader borrowed 3 of this book!");
                                }
                            } while (!isBookExist || isBookExceed3);

                            Book currentBook = BookManager.bookMap.get(bookID);
                            int numOfBookLeft;
                            int numOfBookToBorrow;
                            int numOfBookBorrowed;
                            do {
                                numOfBookLeft = currentBook.getQuantity();
                                System.out.println("There are " + numOfBookLeft + " of this books in library");
                                System.out.println("Enter quantity to borrow (Reader can only borrow maximum of " +
                                        "3 each book): ");
                                numOfBookToBorrow = Integer.parseInt(scanner.nextLine());
                                numOfBookBorrowed = BookReaderManager.countBookBorrowed(readerID, bookID);
                            } while (numOfBookToBorrow == 0 || (numOfBookBorrowed + numOfBookToBorrow) > 3
                                    || numOfBookToBorrow > numOfBookLeft);

                            int stateChoice;
                            String[] stateSet = {"new", "old"};
                            do {
                                System.out.println("Enter state of book:\n 1. new 2. old");
                                stateChoice = Integer.parseInt(scanner.nextLine());
                            } while (stateChoice < 1 || stateChoice > 2);
                            String state = stateSet[stateChoice - 1];

                            double price = currentBook.getPrice();
                            double fee = BookReaderManager.calculateFee(price, numOfBookToBorrow, state);
                            System.out.println("bookName = " + currentBook.getBookName() + "\n"
                                    + "number of book to borrow = " + numOfBookToBorrow + "\n"
                                    + "Fee = " + fee);

                            BookReaderManager.borrowBook(readerID, bookID, numOfBookToBorrow, state);
                            BookManager.bookMap.get(bookID).setQuantity(numOfBookLeft - numOfBookToBorrow);
                            FileController.writeBookToFile(BookManager.bookMap, bookFileName);
                            FileController.writeBookReaderToFile(BookReaderManager.bookReaderList, bookReaderFileName);
                        }
                    }
                }
                case 2 -> {
                    boolean isReaderExist;
                    int readerID;
                    do {
                        ReaderManager.displayReaders();
                        System.out.println("Enter reader ID: ");
                        readerID = Integer.parseInt(scanner.nextLine());
                        isReaderExist = BookReaderManager.checkIsReaderExist(readerID);
                        if (isReaderExist == false) {
                            System.err.println("There is no such reader!");
                        }
                    } while (!isReaderExist);

                    boolean isBookExist;
                    int bookID;
                    do {
                        for (BookReader bookReader : BookReaderManager.bookReaderList) {
                            if (bookReader.getReader().getReaderID() == readerID) {
                                bookReader.outputInfo();
                            }
                        }
                        System.out.println("Enter book ID to take back: ");
                        bookID = Integer.parseInt(scanner.nextLine());
                        isBookExist = BookReaderManager.checkIsBookExist(bookID);

                        if (isBookExist == false) {
                            System.err.println("There is no such book in reader's borrowing list!");
                        }
                    } while (!isBookExist);

                    int numOfBookToReturn;
                    int totalBooksBorrowed = 0;
                    do {
                        System.out.println("Enter quantity to return: ");
                        numOfBookToReturn = Integer.parseInt(scanner.nextLine());
                        for (int i = 0; i < BookReaderManager.bookReaderList.size(); i++) {
                            Reader reader = BookReaderManager.bookReaderList.get(i).getReader();
                            Book book = BookReaderManager.bookReaderList.get(i).getBook();
                            if (reader.getReaderID() == readerID) {
                                if (book.getBookID() == bookID) {
                                    totalBooksBorrowed += BookReaderManager.bookReaderList.get(i).getBorrowNum();
                                }
                            }
                        }
                    } while (numOfBookToReturn <= 0 || numOfBookToReturn > totalBooksBorrowed);

                    BookReaderManager.returnBook(readerID, bookID, numOfBookToReturn);
                    System.err.println("Succeed!");
                    FileController.writeBookReaderToFile(BookReaderManager.bookReaderList, Main.bookReaderFileName);
                }
                case 3 -> BookReaderManager.displayBookReader();
                case 4 -> {
                    int readerID;
                    boolean isReaderExist;
                    do {
                        ReaderManager.displayReaders();
                        System.out.println("----------------------------------");
                        System.out.println("Enter reader ID to search: ");
                        readerID = Integer.parseInt(scanner.nextLine());
                        isReaderExist = BookReaderManager.checkIsReaderExist(readerID);
                        if (isReaderExist == false) {
                            System.err.println("There is no such reader in borrowing list");
                        }
                    } while (!isReaderExist);

                    BookReaderManager.searchBorrowBookByReader(readerID);
                }
                case 5 -> {
                    boolean isBookExist;
                    int bookID;
                    do {
                        BookManager.displayBooks();
                        System.out.println("Enter book ID to search: ");
                        bookID = Integer.parseInt(scanner.nextLine());
                        isBookExist = BookReaderManager.checkIsBookExist(bookID);
                        if (isBookExist == false) {
                            System.err.println("There is no such book in the list!");
                        }
                    } while (!isBookExist);

                    BookReaderManager.searchBorrowReaderByBook(bookID);
                }
                case 6 -> BookReaderManager.sortBooksBorrowed();
                case 0 -> {
                    System.err.println("Thank you!");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.err.println("Your input is wrong!Do again!");
        }
    }

    public static void main(String[] args) {
        ReaderManager.readerMap = FileController.readReaderFromFile(readerFileName);
        BookManager.bookMap = FileController.readBookFromFile(bookFileName);
        BookReaderManager.bookReaderList = FileController.readBookReaderFromFile(bookReaderFileName);
        userManager.setManagerList();
        Scanner scanner = new Scanner(System.in);
        boolean isDirector;
        boolean isLibrarian;
        do {
            System.out.println("Enter username: ");
            String userName = scanner.nextLine();
            isDirector = userManager.checkIsDirector(userName);
            isLibrarian = userManager.checkIsLibrarian(userName);
            if (isDirector || isLibrarian) {
                boolean checkIsPassword;
                do {
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    checkIsPassword = userManager.checkIsPassword(userName, password);
                } while (!checkIsPassword);

                if (isDirector) {
                    menuForManager();
                } else if (isLibrarian) {
                    menuforLibrarian();
                }
            } else {
                System.err.println("username is not found!");
            }
        } while (!isDirector && !isLibrarian);

    }
}




