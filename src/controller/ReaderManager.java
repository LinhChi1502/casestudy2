package controller;

import model.Reader;

import java.util.*;

public class ReaderManager {
    public static LinkedHashMap<Integer, Reader> readerMap = new LinkedHashMap<>();

    public static void addReader() {
        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();

        boolean isCheck = false;

        if (!isCheck) {
            changeReaderId();
            isCheck = true;
        }

        System.out.println("-------------------------");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter reader name: ");
        String readerName = input.nextLine();

        String phoneNumber;
        boolean isExist = false;
        do {
            System.out.println("Enter phonenumber (10 numbers): ");
            phoneNumber = input.nextLine();
            for (Map.Entry<Integer, Reader> entry : entries) {
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
            for (Map.Entry<Integer, Reader> readerEntry : entries) {
                if (readerEntry.getValue().getEmail().equals(email)) {
                    isEmailExist = true;
                    System.err.println("This email has already existed. Enter again!");
                    break;
                } else {
                    isEmailExist = false;
                }
            }
        } while (isEmailExist || !email.matches("^(.*?)\\@gmail.com"));

        System.out.println("Enter address: ");
        String address = input.nextLine();

        Reader reader = new Reader(0, readerName, phoneNumber, email, address);
        readerMap.put(reader.getReaderID(), reader);
    }

    private static void changeReaderId() {
        Set<Map.Entry<Integer, Reader>> entrySet = readerMap.entrySet();
        ArrayList<Map.Entry<Integer, Reader>> readerArrayList = new ArrayList<>(entrySet);
        if (readerArrayList.size() > 0) {
            Map.Entry<Integer, Reader> readerEntry = readerArrayList.get(readerArrayList.size() - 1);
            int readerID = readerEntry.getValue().getReaderID();
            Reader.setId(readerID + 1);
        }
    }

    public static void displayReaders() {
        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();
        for (Map.Entry<Integer, Reader> readerEntry : entries) {
            System.out.println(readerEntry.getValue());
        }
    }

    public static void editReader() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter reader name to edit: ");
        String readerName = scanner.nextLine();
        Set<Map.Entry<Integer, Reader>> readerEntries = readerMap.entrySet();
        boolean isReaderExist = false;
        for (Map.Entry<Integer, Reader> readerEntry : readerEntries) {
            if (readerEntry.getValue().getReaderName().equalsIgnoreCase(readerName)) {
                isReaderExist = true;
                System.out.println("Enter new name: ");
                String newReaderName = scanner.nextLine();

                String newPhoneNumber;
                boolean isPhoneExist = false;
                do {
                    System.out.println("Enter new phone number (10 numbers): ");
                    newPhoneNumber = scanner.nextLine();
                    for (Map.Entry<Integer, Reader> entry : readerEntries) {
                        if (entry.getValue().getPhoneNumber().equals(newPhoneNumber)) {
                            isPhoneExist = true;
                            System.err.println("This phone number has already existed. Enter again!");
                        } else {
                            isPhoneExist = false;
                        }
                    }
                } while (!newPhoneNumber.matches("\\d{10}") || isPhoneExist);

                boolean isEmailExist = false;
                String newEmail;
                do {
                    System.out.println("Enter new email: ");
                    newEmail = scanner.nextLine();
                    for (Map.Entry<Integer, Reader> entry : readerEntries) {
                        if (entry.getValue().getEmail().equals(newEmail)) {
                            isEmailExist = true;
                            System.err.println("This email has already existed. Enter again!");
                        } else {
                            isEmailExist = false;
                        }
                    }
                } while (isEmailExist);

                System.out.println("Enter new address: ");
                String newAddress = scanner.nextLine();

                readerEntry.getValue().setReaderName(newReaderName);
                readerEntry.getValue().setPhoneNumber(newPhoneNumber);
                readerEntry.getValue().setEmail(newEmail);
                readerEntry.getValue().setAddress(newAddress);
            }
        }
        if (!isReaderExist) {
            System.err.println("There is no such reader!");
        }
    }

    public static void deleteReader() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter reader name to delete: ");
        String readerName = scanner.nextLine();
        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();
        ArrayList<Map.Entry<Integer, Reader>> arrayList = new ArrayList<>(entries);
        boolean isSearchReader = false;
        for (Map.Entry<Integer, Reader> entry : arrayList) {
            if (entry.getValue().getReaderName().equalsIgnoreCase(readerName)) {
                isSearchReader = true;
                readerMap.remove(entry.getKey());
                System.out.println("Reader Deleted");
            }
        }
        if (!isSearchReader) {
            System.err.println("There is no such reader!");
        }
    }

    public static void searchReaderByName() {
        System.out.println("Enter reader name to search: ");
        String readerName = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();
        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();
        boolean isSearchNameExist = false;
        for (Map.Entry<Integer, Reader> entry : entries) {
            String searchReaderName = ConvertVieToEng.removeAccent(entry.getValue().getReaderName()).toLowerCase();
            if (searchReaderName.contains(readerName)) {
                isSearchNameExist = true;
                System.out.println(entry.getValue());
            }
        }
        if (!isSearchNameExist) {
            System.err.println("There is no such reader");
        }
    }

    public static void searchReaderByAddress() {
        System.out.println("Enter address to search: ");
        String address = ConvertVieToEng.removeAccent(new Scanner(System.in).nextLine()).toLowerCase();

        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();
        boolean isSearchAddressExist = false;
        for (Map.Entry<Integer, Reader> entry : entries) {
            String searchAddress = ConvertVieToEng.removeAccent(entry.getValue().getAddress()).toLowerCase();
            if (searchAddress.equalsIgnoreCase(address)
                    || searchAddress.startsWith(address)) {
                isSearchAddressExist = true;
                System.out.println(entry.getValue());
            }
        }
        if (!isSearchAddressExist) {
            System.err.println("There is no such address");
        }
    }

    public static void sortReaderByName() {
        System.out.println("-----------------------------");
        System.out.println("1. Ascending order");
        System.out.println("2. Descending order");
        System.out.println("-----------------------------");
        System.out.println("Enter your choice: ");
        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1 -> sortReaderNameAscending();
            case 2 -> sortReaderNameDescending();
        }
    }

    private static void sortReaderNameAscending() {
        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();
        ArrayList<Map.Entry<Integer, Reader>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Reader>>() {
            @Override
            public int compare(Map.Entry<Integer, Reader> o1, Map.Entry<Integer, Reader> o2) {
                String o1String = ConvertVieToEng.removeAccent(o1.getValue().getReaderName());
                String o2String = ConvertVieToEng.removeAccent(o2.getValue().getReaderName());
                return o1String.compareTo(o2String);
            }
        });

        for (Map.Entry<Integer, Reader> entry : arrayList) {
            System.out.println(entry.getValue());
        }
    }

    private static void sortReaderNameDescending() {
        Set<Map.Entry<Integer, Reader>> entries = readerMap.entrySet();
        ArrayList<Map.Entry<Integer, Reader>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Reader>>() {
            @Override
            public int compare(Map.Entry<Integer, Reader> o1, Map.Entry<Integer, Reader> o2) {
                String o1String = ConvertVieToEng.removeAccent(o1.getValue().getReaderName());
                String o2String = ConvertVieToEng.removeAccent(o2.getValue().getReaderName());
                return o2String.compareTo(o1String);
            }
        });

        for (Map.Entry<Integer, Reader> entry : arrayList) {
            System.out.println(entry.getValue());
        }
    }
}
