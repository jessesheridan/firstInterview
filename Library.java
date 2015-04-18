/* Begin Library.Java */
package org.screening.problems.library;

public interface Library {

    /**
     * Returns the book most recently checked in in this genre, and removes it from the pool of available items.
     *
     * @param genre the genre to which the book belongs
     * @return the Book object that has just been checked out
     * @throws OutOfBooksException if there are no books in that genre available
     * @see Book
     * @see Genre
     */
    Book checkOutBook(Genre genre) throws OutOfBooksException;

    /**
     * Returns the book to the library's availability pool, making it the last checked-in book and rating the book
     * in the process.
     *
     * @param returnedBook the Book that is being checked back in
     * @param rating       an integer from 1 to 100 (inclusive) specifying the rating. The last person to rate the
     *                     book overwrites any previous rating
     * @throws IllegalRatingException if a rating less than 1 or more than 100 is specified
     */
    void checkInBook(Book returnedBook, int rating) throws IllegalRatingException;

    /**
     * Returns the highest rated book in the specified genre, but does not remove it from availability
     *
     * @param genre the genre for which we'd like to retrieve the highest-rated book
     * @return a Book that is the highest-rated book currently available in the genre
     * @throws OutOfBooksException if there are no books in that genre available
     */
    Book peekHighestRatedBook(Genre genre) throws OutOfBooksException;

    public static class OutOfBooksException extends Exception {
    }

    public static class IllegalRatingException extends Exception {
    }

}
/* End Library.java */
