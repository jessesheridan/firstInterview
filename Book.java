/* Begin Book.java 
 * This file was given to me by the company I interviewed with, I created EarlLibrary.java to interact with this.
*/

package org.screening.problems.library;

public class Book {
    private final Genre genre;
    private final String title;
    private final String author;
    private final int rating;

    /**
     * Parameterized constructor for Books.
     *
     * @param genre  the genre to which this book belongs
     * @param title  the title of this book
     * @param author the author of this book
     * @param rating the rating of this book
     */
    public Book(Genre genre, String title, String author, int rating) {
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.rating = rating;
    }

    /**
     * Returns the genre to which the book belongs.
     *
     * @return a Genre representing the genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Returns the title of the book.
     *
     * @return a String containing the title of the book in English
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     *
     * @return a String containing the name of the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the rating of the book.
     *
     * @return an int containing that rating
     */
    public int getRating() {
        return rating;
    }

}

/* End Book.java */