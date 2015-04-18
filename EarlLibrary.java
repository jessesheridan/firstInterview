// Author: Jesse Sheridan
// Email: jesse.sheridan@gmail.com
// Date: 04/13/2015
// Description: This is for an interview code review exercise
// Design Notes: 
// I will use ArrayDeque for the stack
// A stack for each genre (available books).  There are 4 genres, so there will be 4 stacks.
// Check in book takes a book and a rating.  This will create a new book with the book given with a new rating.  
// This will then add the book to the genre stack. 
// Rating will be based on searching through the stack.  Unfortunately, this is an O(n) operation.  
// However, this is a tradeoff I made to keep check in and check out on average O(1).  
// Searching will be performed rarely, so this is a good tradeoff.


package org.screening.problems.library;

import java.util.ArrayDeque;
import java.util.Iterator;


public class EarlLibrary implements Library {
	private ArrayDeque<Book> bookStack1; // NON_FICTION
	private ArrayDeque<Book> bookStack2; // GENERAL_FICTION
	private ArrayDeque<Book> bookStack3; // SCIENCE_FICTION
	private ArrayDeque<Book> bookStack4; // WESTERN

	/**
	 * Constructor for EarlLibrary.
	 */
	public EarlLibrary() {
		this.bookStack1 = new ArrayDeque<Book>();
		this.bookStack2 = new ArrayDeque<Book>();
		this.bookStack3 = new ArrayDeque<Book>();
		this.bookStack4 = new ArrayDeque<Book>();
	}

    /**
     * Returns the book most recently checked in in this genre, and removes it from the pool of available items.
     * 
     * This has average O(1) for time complexity due to the fact that it is made with an array and most times you do not need to move all the data over to a new smaller array.
     * This has worst case O(n) for time, due to the fact that you may need to copy all the data over to a smaller array.
     * Average and worst case for space is O(n) since you need an array element for each item
     * 
     * @param genre the genre to which the book belongs
     * @return the Book object that has just been checked out
     * @throws OutOfBooksException if there are no books in that genre available
     * @see Book
     * @see Genre
     */
	public Book checkOutBook(Genre genre) throws OutOfBooksException {
		// Pop the book off the stack for the genre selected
		switch (genre) {
		case NON_FICTION:
			if (bookStack1.isEmpty())
				throw new OutOfBooksException();
			return bookStack1.pop();
		case GENERAL_FICTION:
			if (bookStack2.isEmpty())
				throw new OutOfBooksException();
			return bookStack2.pop();
		case SCIENCE_FICTION:
			if (bookStack3.isEmpty())
				throw new OutOfBooksException();
			return bookStack3.pop();
		case WESTERN:
			if (bookStack4.isEmpty())
				throw new OutOfBooksException();
			return bookStack4.pop();
		} // switch

		return null;
	}

    /**
     * Returns the book to the library's availability pool, making it the last checked-in book and rating the book
     * in the process.
     * 
     * I assumed that each book added to the library is unique
     * This has average O(1) for time complexity due to the fact that it is made with an array and most times you do not need to move all the data over to a new larger array.
     * This has worst case O(n) for time, due to the fact that you may need to copy all the data over to a larger array.
     * Average and worst case for space is O(n) since you need an array element for each item
     *
     * @param returnedBook the Book that is being checked back in
     * @param rating       an integer from 1 to 100 (inclusive) specifying the rating. The last person to rate the
     *                     book overwrites any previous rating
     * @throws IllegalRatingException if a rating less than 1 or more than 100 is specified
     */
	public void checkInBook(Book returnedBook, int rating) throws IllegalRatingException {
		// Handle invalid ratings
		if (rating < 1 || rating > 100) {
			throw new IllegalRatingException();
		}
		
		// Create a new book and give it the new rating
		Book newBook = new Book(returnedBook.getGenre(),
				returnedBook.getTitle(), returnedBook.getAuthor(), rating);
		
		// For the genre selected, add the book to the stack
		switch (newBook.getGenre()) {
		case NON_FICTION:
			bookStack1.push(newBook);
			break;
		case GENERAL_FICTION:
			bookStack2.push(newBook);
			break;
		case SCIENCE_FICTION:
			bookStack3.push(newBook);
			break;
		case WESTERN:
			bookStack4.push(newBook);
			break;
		} // switch

	}

    /**
     * Returns the highest rated book in the specified genre, but does not remove it from availability
     *
     * This has average and worst case O(n) for time, due to the fact that you need to search through each item of the array every time
     * Average and worst case for space is O(1) since you are just referencing already created data
     *
     * @param genre the genre for which we'd like to retrieve the highest-rated book
     * @return a Book that is the highest-rated book currently available in the genre
     * @throws OutOfBooksException if there are no books in that genre available
     */
	public Book peekHighestRatedBook(Genre genre) throws OutOfBooksException {
		Book highestRatedBook = null;
		Iterator<Book> genreIterator = null;
		
		// Get an iterator for the stack of the genre selected
		switch (genre) {
		case NON_FICTION:
			genreIterator = bookStack1.iterator();
			break;
		case GENERAL_FICTION:
			genreIterator = bookStack2.iterator();
			break;
		case SCIENCE_FICTION:
			genreIterator = bookStack3.iterator();
			break;
		case WESTERN:
			genreIterator = bookStack4.iterator();
			break;
		} // switch

		// If no books in the stack, throw an exception
		if (!genreIterator.hasNext()) {
			throw new OutOfBooksException();
		}

		// Go through the entire stack looking for the highest rated book.  
		// This is an O(n) operation due to having to go through the entire stack each time
		while (genreIterator.hasNext()) {
			Book book = genreIterator.next();
			if (highestRatedBook == null || book.getRating() > highestRatedBook.getRating())
				highestRatedBook = book;
		}

		return highestRatedBook;
	}

    /**
     * Main function which acts as our unit test to verify correct functionality
     */
	public static void main(String[] args) {
		
		EarlLibrary library = new EarlLibrary();
		Book newBook = null;
		int counter = 0;
		
		// First check to make sure the methods throw exceptions when no books are in the library for each genre
		for (Genre genre : Genre.values()) {
			try {
				newBook = library.peekHighestRatedBook(genre);
			} catch (Library.OutOfBooksException e) {
				counter++;
			}
			try {
				newBook = library.checkOutBook(genre);
			} catch (Library.OutOfBooksException e) {
				counter++;
			}
		}
	
		// Check to make sure an exception is thrown if we give an invalid rating (less than 1 or greater than 100)
		for (int i = 0; i < 102; i += 101) {
			try {
				library.checkInBook(newBook, i);
			} catch (Library.IllegalRatingException e) {
				counter++;
			}
		}
		
		// We should have received 10 exceptions, if so our test has passed.
		if (counter == 10) {
			System.out.println("All exceptions are thrown properly.");
		}
		
		
		// Create three books in each genre and add them to the library with increasing rating
		for (Genre genre : Genre.values()) {
			for (int i = 1; i < 4; i++) {
				newBook = new Book(genre, "Book " + i, "Jesse Sheridan", 1);
				try {
					library.checkInBook(newBook, i);
				} catch (IllegalRatingException e) {

				}
			}
		}
		
		// Add one more book at the end for each genre and make sure it has a lower rating
		for (Genre genre : Genre.values()) {
			newBook = new Book(genre, "Book 4", "Jesse Sheridan", 1);
			try {
				library.checkInBook(newBook, 1);
			} catch (IllegalRatingException e) {
	
			}
		}
		
		// Check to make sure Book 3 (rating of 3) is the highest
		try {
			newBook = library.peekHighestRatedBook(Genre.GENERAL_FICTION);
		} catch (OutOfBooksException e) {

		}
		
		if (newBook.getTitle().equals("Book 3"))
			System.out.println("Book 3 is the highest rated book like it should be");
		
		
		// Check out a book in any genre and make sure it is Book 4, the last book added
		try {
			newBook = library.checkOutBook(Genre.NON_FICTION);
		} catch (OutOfBooksException e) {
			
		}
		
		if (newBook.getTitle().equals("Book 4")) {
			System.out.println("Book 4 is the last book added to the stack like it should be");
		}
		

	}

}
