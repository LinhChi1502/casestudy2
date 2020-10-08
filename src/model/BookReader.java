package model;

import java.io.Serializable;

public class BookReader implements Serializable {
    private Reader reader;
    private Book book;
    private int borrowNum;
    private String state;

    public BookReader() {
    }

    public BookReader(Reader reader, Book book, int borrowNum, String state) {
        this.reader = reader;
        this.book = book;
        this.borrowNum = borrowNum;
        this.state = state;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getBorrowNum() {
        return borrowNum;
    }

    public void setBorrowNum(int borrowNum) {
        this.borrowNum = borrowNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public void outputInfo() {
        System.out.printf("%-20s%-30s%-17s%-33s%-15s%-10s", "readerID = " + reader.getReaderID(),
                "readerName = " + reader.getReaderName(), "bookID = " + book.getBookID(),
                "bookName = " + book.getBookName(), "borrowNum = " + borrowNum, "state = " + state);
        System.out.println("");
    }
}
