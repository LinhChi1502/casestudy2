package controller;

import model.Reader;
import view.Main;

import java.util.*;

public class ReaderManager {
    public static LinkedHashMap<Integer, Reader> readerMap = new LinkedHashMap<>();

    public static void addReader(Reader reader) {
        readerMap.put(reader.getReaderID(), reader);
        FileController.writeReaderToFile(ReaderManager.readerMap, Main.readerFileName);
    }

    public static void changeReaderId() {
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
            readerEntry.getValue().outputInfo();
        }
    }
    public static void editReader(Reader reader, String newReaderName, String newPhoneNumber, String newEmail,
                                  String newAddress) {
        reader.setReaderName(newReaderName);
        reader.setPhoneNumber(newPhoneNumber);
        reader.setEmail(newEmail);
        reader.setAddress(newAddress);
        FileController.writeReaderToFile(ReaderManager.readerMap, Main.readerFileName);
    }

    public static void deleteReader(int readerID) {
        readerMap.remove(readerID);
        FileController.writeReaderToFile(ReaderManager.readerMap, Main.readerFileName);
    }

    public static void searchReaderByName(String readerName) {
        Set<Map.Entry<Integer, Reader>> readerEntries = ReaderManager.readerMap.entrySet();
        boolean isSearchNameExist = false;
        for (Map.Entry<Integer, Reader> entry : readerEntries) {
            Reader reader = entry.getValue();
            String searchReaderName = ConvertVieToEng.removeAccent(reader.getReaderName()).toLowerCase();
            if (searchReaderName.contains(readerName)) {
                isSearchNameExist = true;
                reader.outputInfo();
            }
        }
        if (isSearchNameExist == false) {
            System.err.println("There is no such reader");
        }

    }

    public static void searchReaderByAddress(String address) {
        Set<Map.Entry<Integer, Reader>> readerEntries = readerMap.entrySet();
        boolean isSearchAddressExist = false;
        for (Map.Entry<Integer, Reader> entry : readerEntries) {
            Reader reader = entry.getValue();
            String searchAddress = ConvertVieToEng.removeAccent(reader.getAddress()).toLowerCase();
            if (searchAddress.equalsIgnoreCase(address)
                    || searchAddress.contains(address)) {
                isSearchAddressExist = true;
                reader.outputInfo();
            }
        }
        if (isSearchAddressExist == false) {
            System.err.println("There is no such address");
        }
    }

    public static void sortReaderNameAscending() {
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
            entry.getValue().outputInfo();
        }
    }

    public static void sortReaderNameDescending() {
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
            entry.getValue().outputInfo();
        }
    }

    public static boolean checkReaderExist(int readerID) {
        Set<Map.Entry<Integer, Reader>> readerEntries = readerMap.entrySet();
        for (Map.Entry<Integer, Reader> readerEntry : readerEntries) {
            if (readerEntry.getValue().getReaderID() == readerID) {
                return true;
            }
        }
        return false;
    }
}
