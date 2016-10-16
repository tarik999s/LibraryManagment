/**
 * 
 */
package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import main.books.BooksServiceImpl;
import main.connection.JDBCHelper;
import main.manager.ConsoleManager;
import main.utils.Constants;
import main.utils.DbRecreation;

/**
 * @author Taras Savitskyy
 * 
 */
public class MainSystem {

	static Scanner scanner = new Scanner(System.in);

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Hello it's Library Managment console project!");

		consoleMenu();
	}

	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void consoleMenu() throws ClassNotFoundException, SQLException, IOException {

		BooksServiceImpl booksService = new BooksServiceImpl(
				new JDBCHelper(Constants.USER_NAME, Constants.PASSWORD, Constants.URL));

		ConsoleManager consoleManager = new ConsoleManager(booksService);

		System.out.println("-----------------------------------------------------------");
		System.out.println("See the List of commands:");
		System.out.println("1 - 'Print all books'");
		System.out.println("2 - 'Add new Book'");
		System.out.println("3 - 'Edit the Book'");
		System.out.println("4 - 'Removes the Book'");
		System.out.println("5 - 'Finds the Book'");
		System.out.println("6 - 'Clear the table'");
		System.out.println("7 - 'Creates a specified number of random books'");
		System.out.println("8 - 'Run sql dump file'");
		System.out.println("0 - 'Exit from project'");
		System.out.println("-----------------------------------------------------------");

		int userOperation = Integer.parseInt(inputValue("Program: Please input the number operation : "));
		if ((userOperation >= 0) && (userOperation <= 8)) {
			if (userOperation == 1)
				consoleManager.printAllBooks();
			else if (userOperation == 2)
				consoleManager.addNewBook();
			else if (userOperation == 6)
				booksService.clearTable();
			else if (userOperation == 7)
				consoleManager.generateBooks();
			else if (userOperation == 0)
				System.exit(0);
			if (consoleManager.getBooksCount() > 0) {
				if (userOperation == 3)
					consoleManager.editBook();
				else if (userOperation == 4)
					consoleManager.removeBook();
				else if (userOperation == 5)
					consoleManager.findBook();
			} else {
				if (userOperation > 1) {
					System.out.println("Program: Not found books in table! ");
					System.out.println("-----------------------------------------------------------");
					String key = inputValue("[Any key to continue]");
					System.out.println("\n");
					consoleMenu();
				}
			}
			if (userOperation == 8)
				DbRecreation.recreation();
		} else {
			System.out.println("Program: Error number !! ");
			System.out.println("-----------------------------------------------------------");
			String key = inputValue("[Any key to continue]");
			System.out.println("\n");
			consoleMenu();
		}
		String key = inputValue("[Any key to continue]");
		System.out.println("\n");
		consoleMenu();
	}

	/**
	 * Reading an input value
	 * 
	 * @param the
	 *            Output message
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String inputValue(String message) throws ClassNotFoundException, SQLException, IOException {
		System.out.println(message);
		String returnString;
		System.out.print("You: ");
		returnString = scanner.next();

		return returnString;
	}

}
