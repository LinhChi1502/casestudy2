package controller;

import model.Book;
import model.BookReader;
import model.Reader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class FileController {
    public static void writeBookToFile(LinkedHashMap<Integer, Book> bookList, String fileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bookList);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<Integer, Book> readBookFromFile(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object bookList = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return (LinkedHashMap<Integer, Book>) bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeReaderToFile(LinkedHashMap<Integer, Reader> readerList, String fileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(readerList);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<Integer, Reader> readReaderFromFile(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object readerList = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return (LinkedHashMap<Integer, Reader>) readerList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeBookReaderToFile(LinkedList<BookReader> bookReaderList, String fileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(bookReaderList);
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<BookReader> readBookReaderFromFile(String bookReaderFileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(bookReaderFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object bookReaderList = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return (LinkedList<BookReader>) bookReaderList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
