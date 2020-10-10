package controller;

import model.Book;
import view.Main;

import java.util.*;

public class BookManager {

    public static LinkedHashMap<Integer, Book> bookMap = new LinkedHashMap<>();

    public static void addBook(Book book) {
        bookMap.put(book.getBookID(), book);
        FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);
    }

    public static void changeBookId() {
        Set<Map.Entry<Integer, Book>> bookEntries = BookManager.bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> bookEntryList = new ArrayList<>(bookEntries);
        if (bookEntryList.size() > 0) {
            Map.Entry<Integer, Book> bookEntry = bookEntryList.get(bookEntryList.size() - 1);
            int bookID = bookEntry.getValue().getBookID();
            Book.setId(bookID + 1);
        }
    }

    public static boolean checkBookExist(int bookID) {
        Set<Map.Entry<Integer, Book>> bookEntries = BookManager.bookMap.entrySet();
        for (Map.Entry<Integer, Book> bookEntry : bookEntries) {
            Book value = bookEntry.getValue();
            int IDValue = value.getBookID();
            if (IDValue == bookID) {
                return true;
            }
        }
        return false;
    }


    public static void displayBooks() {
        bookMap = FileController.readBookFromFile(Main.bookFileName);
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        for (Map.Entry<Integer, Book> entry : entries) {
            entry.getValue().outputInfo();
        }
    }

    public static void editBook(Book book, String newName, String newAuthor, String newSpec, double newPrice,
                                int newQuantity, String newPublisher) {
        book.setBookName(newName);
        book.setAuthor(newAuthor);
        book.setSpecialization(newSpec);
        book.setPrice(newPrice);
        book.setQuantity(newQuantity);
        book.setPublisher(newPublisher);

    }

    public static void deleteBook(int bookID) {
        bookMap.remove(bookID);
        FileController.writeBookToFile(BookManager.bookMap, Main.bookFileName);
    }

    public static void searchBookByName(String bookSearchName) {
        Set<Map.Entry<Integer, Book>> bookEntries = BookManager.bookMap.entrySet();
        boolean isBookSearchExist = false;
        for (Map.Entry<Integer, Book> bookEntry : bookEntries) {
            Book book = bookEntry.getValue();
            String nameValue = book.getBookName();
            String toSearchName = ConvertVieToEng.removeAccent(nameValue).toLowerCase();
            if (toSearchName.equals(bookSearchName) || toSearchName.contains(bookSearchName)) {
                isBookSearchExist = true;
                book.outputInfo();
            }
        }
        if (isBookSearchExist == false) {
            System.err.println("There is no such book!");
        }
    }

    public static void searchBookByAuthor(String searchAuthor) {
        Set<Map.Entry<Integer, Book>> bookEntries = BookManager.bookMap.entrySet();
        boolean isSearchAuthorExist = false;
        for (Map.Entry<Integer, Book> bookEntry : bookEntries) {
            Book book = bookEntry.getValue();
            String authorName = ConvertVieToEng.removeAccent(book.getAuthor()).toLowerCase();
            if (authorName.equalsIgnoreCase(searchAuthor) || authorName.contains(searchAuthor)) {
                isSearchAuthorExist = true;
                book.outputInfo();
            }
        }
        if (isSearchAuthorExist == false) {
            System.err.println("There is no such author!");
        }

    }

    public static void searchBookBySpecialization(String specialization) {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        boolean isSearchSpecExist = false;
        for (Map.Entry<Integer, Book> entry : entries) {
            Book book = entry.getValue();
            if (book.getSpecialization().contains(specialization)) {
                isSearchSpecExist = true;
                book.outputInfo();
            }
        }
        if (isSearchSpecExist == false) {
            System.err.println("There is no such specialization");
        }

    }

    public static void searchBookByPublisher(String publisher) {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        boolean isSearchPublisher = false;
        for (Map.Entry<Integer, Book> entry : entries) {
            Book book = entry.getValue();
            String searchPublisher = ConvertVieToEng.removeAccent(book.getPublisher()).toLowerCase();
            if (searchPublisher.equalsIgnoreCase(publisher) || searchPublisher.contains(publisher)) {
                isSearchPublisher = true;
                book.outputInfo();
            }
        }
        if (isSearchPublisher == false) {
            System.err.println("There is no such publisher");
        }

    }

    public static void sortNameAscending() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Book>>() {
            @Override
            public int compare(Map.Entry<Integer, Book> o1, Map.Entry<Integer, Book> o2) {
                String o1String = ConvertVieToEng.removeAccent(o1.getValue().getBookName());
                String o2String = ConvertVieToEng.removeAccent(o2.getValue().getBookName());
                return o1String.compareTo(o2String);
            }
        });
        for (Map.Entry<Integer, Book> entry : arrayList) {
            entry.getValue().outputInfo();
        }
    }

    public static void sortNameDescending() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Book>>() {
            @Override
            public int compare(Map.Entry<Integer, Book> o1, Map.Entry<Integer, Book> o2) {
                String o1String = ConvertVieToEng.removeAccent(o1.getValue().getBookName());
                String o2String = ConvertVieToEng.removeAccent(o2.getValue().getBookName());
                return o2String.compareTo(o1String);
            }
        });
        for (Map.Entry<Integer, Book> entry : arrayList) {
            entry.getValue().outputInfo();
        }
    }

    public static void sortPriceAscending() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        arrayList.sort(Comparator.comparing(o -> o.getValue().getPrice()));
        for (Map.Entry<Integer, Book> entry : arrayList) {
            entry.getValue().outputInfo();
        }
    }

    public static void sortPriceDescending() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Book>>() {
            @Override
            public int compare(Map.Entry<Integer, Book> o1, Map.Entry<Integer, Book> o2) {
                return Double.compare(o2.getValue().getPrice(), o1.getValue().getPrice());
            }
        });
        for (Map.Entry<Integer, Book> entry : arrayList) {
            entry.getValue().outputInfo();
        }
    }
}

