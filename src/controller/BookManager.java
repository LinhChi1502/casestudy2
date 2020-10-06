package controller;

import model.Book;

import java.util.*;

public class BookManager {
    public static LinkedHashMap<Integer, Book> bookMap = new LinkedHashMap<>();

    public static void addBook() {
        System.out.println("-------------------------");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter bookname: ");
        String bookName = input.nextLine();

        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(input.nextLine());

        boolean isBookExist = false;
        boolean isCheck = false;

        if (!isCheck) {
            changeBookId();
            isCheck = true;
        }
        Set<Map.Entry<Integer, Book>> bookEntries = bookMap.entrySet();
        for (Map.Entry<Integer, Book> bookEntry : bookEntries) {
            Book value = bookEntry.getValue();
            String name = value.getBookName();
            if (name.equals(bookName)) {
                isBookExist = true;
                quantity += value.getQuantity();
                value.setQuantity(quantity);
                System.out.println("This book has already existed");
                break;
            }
        }
        if (!isBookExist) {
            System.out.println("Enter author: ");
            String author = input.nextLine();

            int choice;
            String[] specList = {"Science", "Art", "Economics", "IT"};
            do {
                System.out.println("-----------SPECIALIZATION-----------");
                System.out.println("1. Science\n2. Art\n3. Economics\n4. IT");
                System.out.println("Enter specialization: ");
                choice = Integer.parseInt(input.nextLine());
            } while (choice < 1 || choice > 4);
            String specialization = specList[choice - 1];

            System.out.println("Enter price: ");
            double price = Double.parseDouble(input.nextLine());

            System.out.println("Enter publisher: ");
            String publisher = input.nextLine();

            Book book = new Book(0, bookName, author, specialization, price, quantity, publisher);
            bookMap.put(book.getBookID(), book);
        }
    }

    private static void changeBookId() {
        Set<Map.Entry<Integer, Book>> bookEntries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> bookEntryList = new ArrayList<>(bookEntries);
        if (bookEntryList.size() > 0) {
            Map.Entry<Integer, Book> bookEntry = bookEntryList.get(bookEntryList.size() - 1);
            int bookID = bookEntry.getValue().getBookID();
            Book.setId(bookID + 1);
        }
    }

    public static void displayBooks() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        for (Map.Entry<Integer, Book> entry : entries) {
            System.out.println(entry.getValue());
        }
    }

    public static void editBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter book name to edit: ");
        String bookName = sc.nextLine();
        Set<Map.Entry<Integer, Book>> bookEntry = bookMap.entrySet();
        boolean isBookExist = false;
        for (Map.Entry<Integer, Book> entry : bookEntry) {
            if (entry.getValue().getBookName().equalsIgnoreCase(bookName)) {
                isBookExist = true;
                System.out.println("Enter new name: ");
                String newName = sc.nextLine();
                System.out.println("Enter new author: ");
                String newAuthor = sc.nextLine();
                int choice;
                String[] specList = {"Science", "Art", "Economics", "IT"};
                do {
                    System.out.println("-----------SPECIALIZATION-----------");
                    System.out.println("1. Science\n2. Art\n3. Economics\n4. IT");
                    System.out.println("Enter new specialization: ");
                    choice = Integer.parseInt(sc.nextLine());
                } while (choice < 1 || choice > 4);
                String newSpec = specList[choice - 1];
                System.out.println("Enter new price: ");
                double newPrice = Double.parseDouble(sc.nextLine());

                System.out.println("Enter new quantity: ");
                int newQuantity = Integer.parseInt(sc.nextLine());

                System.out.println("Enter new publisher: ");
                String newPublisher = sc.nextLine();

                entry.getValue().setBookName(newName);
                entry.getValue().setAuthor(newAuthor);
                entry.getValue().setSpecialization(newSpec);
                entry.getValue().setPrice(newPrice);
                entry.getValue().setQuantity(newQuantity);
                entry.getValue().setPublisher(newPublisher);
            }
        }
        if (!isBookExist) {
            System.err.println("There is no such book!");
        }
    }

    public static void deleteBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter book name to delete: ");
        String bookName = sc.nextLine();
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        boolean isSearchBookName = false;
        for (Map.Entry<Integer, Book> entry : arrayList) {
            if (entry.getValue().getBookName().equalsIgnoreCase(bookName)) {
                isSearchBookName = true;
                bookMap.remove(entry.getKey());
            }
        }
        if (!isSearchBookName) {
            System.err.println("There is no such book!");
        }
    }

    public static void searchBookByName() {
        System.out.println("Enter book name to search: ");
        String bookName = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        boolean isSearchBookExist = false;
        for (Map.Entry<Integer, Book> entry : entries) {
            String searchName = ConvertVieToEng.removeAccent(entry.getValue().getBookName().toLowerCase());
            if (searchName.equalsIgnoreCase(bookName) || searchName.startsWith(bookName)) {
                isSearchBookExist = true;
                System.out.println(entry.getValue());
            }
        }
        if (!isSearchBookExist) {
            System.err.println("There is no such book");
        }
    }

    public static void searchBookByAuthor() {
        System.out.println("Enter author name to search: ");
        String author = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        boolean isSearchAuthorExist = false;
        for (Map.Entry<Integer, Book> entry : entries) {
            String searchAuthor = ConvertVieToEng.removeAccent(entry.getValue().getAuthor()).toLowerCase();
            if (searchAuthor.equalsIgnoreCase(author) || searchAuthor.startsWith(author)) {
                isSearchAuthorExist = true;
                System.out.println(entry.getValue());
            }
        }
        if (!isSearchAuthorExist) {
            System.err.println("There is no such author");
        }
    }

    public static void searchBookBySpecialization() {
        int choice;
        String[] specList = {"Science", "Art", "Economics", "IT"};
        do {
            System.out.println("-----------SPECIALIZATION-----------");
            System.out.println("1. Science\n2. Art\n3. Economics\n4. IT");
            System.out.println("Enter specialization to search: ");
            choice = Integer.parseInt(new Scanner(System.in).nextLine());
        } while (choice < 1 || choice > 4);
        String specialization = specList[choice - 1];
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        boolean isSearchSpecExist = false;
        for (Map.Entry<Integer, Book> entry : entries) {
            if (entry.getValue().getSpecialization().startsWith(specialization)) {
                isSearchSpecExist = true;
                System.out.println(entry.getValue());
            }
        }
        if (!isSearchSpecExist) {
            System.err.println("There is no such specialization");
        }
    }

    public static void searchBookByPublisher() {
        System.out.println("Enter publisher to search: ");
        String publisher = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        boolean isSearchPublisher = false;
        for (Map.Entry<Integer, Book> entry : entries) {
            String searchPublisher = ConvertVieToEng.removeAccent(entry.getValue().getPublisher()).toLowerCase();
            if (searchPublisher.equalsIgnoreCase(publisher) || searchPublisher.startsWith(publisher)) {
                isSearchPublisher = true;
                System.out.println(entry.getValue());
            }
        }
        if (!isSearchPublisher) {
            System.err.println("There is no such publisher");
        }
    }

    public static void sortBookByName() {
        System.out.println("-----------------------------");
        System.out.println("1. Ascending order");
        System.out.println("2. Descending order");
        System.out.println("-----------------------------");
        System.out.println("Enter your choice: ");
        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1 -> sortNameAscending();
            case 2 -> sortNameDescending();
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
        for (Map.Entry<Integer, Book> entry: arrayList) {
            System.out.println(entry.getValue());
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
        for (Map.Entry<Integer, Book> entry: arrayList) {
            System.out.println(entry);
        }
    }

    public static void sortBookPrice() {
        System.out.println("-----------------------------");
        System.out.println("1. Ascending order");
        System.out.println("2. Descending order");
        System.out.println("-----------------------------");
        System.out.println("Enter your choice: ");
        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1 -> sortPriceAscending();
            case 2 -> sortPriceDescending();
        }
    }
    private static void sortPriceAscending() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        arrayList.sort(Comparator.comparing(o -> o.getValue().getPrice()));
        for (Map.Entry<Integer, Book> entry: arrayList) {
            System.out.println(entry);
        }
    }
    private static void sortPriceDescending() {
        Set<Map.Entry<Integer, Book>> entries = bookMap.entrySet();
        ArrayList<Map.Entry<Integer, Book>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Book>>() {
            @Override
            public int compare(Map.Entry<Integer, Book> o1, Map.Entry<Integer, Book> o2) {
                return Double.compare(o2.getValue().getPrice(), o1.getValue().getPrice());
            }
        });
        for (Map.Entry<Integer, Book> entry: arrayList) {
            System.out.println(entry);
        }
    }
}

