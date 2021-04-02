import java.util.ArrayList;

/**
 * The following methods must be implemented
 * when creating a model of another data structure
 * for the GUI. The GUI is reliant on these methods.
 *
 * @author mathushanmathiyalagan
 * @version 24-03-21
 * @see GUI
 */
public interface ModelInterface {

    /**
     * A standard getter for an array of column names
     * that are queried by the GUI.
     *
     * @return Queried column names
     */
    String[] getQueriedColumns();

    /**
     * A standard getter for an array of values in all
     * columns that are queried by the GUI.
     *
     * @return Queried column values
     */
    String[][] getQueriedData();

    /**
     * A standard getter for an array of column names
     * that are searched by the GUI.
     *
     * @return Searched column names
     */
    String[] getSearchedColumns();

    /**
     * A standard getter for an array of values in all
     * columns that are searched by the GUI.
     *
     * @return Searched column values
     */
    String[][] getSearchedData();

    /**
     * A standard getter for an array of column names
     * that are ordered by the GUI.
     *
     * @return Ordered column names
     */
    String[] getOrderedColumns();

    /**
     * A standard getter for an array of values in all
     * columns that are ordered by the GUI.
     *
     * @return Ordered column values
     */
    String[][] getOrderedData();

    /**
     * A standard getter for an array of column names
     * that are to be shown as query buttons on the GUI.
     *
     * @return Queried column names
     */
    ArrayList<String> getQueryNames();

    /**
     * Adds values that belong to the queried column names
     * to a structure so that it can be returned by methods
     * getQueriedColumns and getQueriedData.
     *
     * @param query DataFrame.Column names to query
     */
    void queryTable(ArrayList<String> query);

    /**
     * Adds values that have a matching column name
     * and entry value to a structure so that it can
     * be returned by methods getSearchedColumns and
     * getSearchedData.
     *
     * @param field  DataFrame.Column name to search entry for
     * @param search Entry to search for in DataFrame.Column name
     */
    void searchEntry(String field, String search);

    /**
     * Adds maximum / minimum entry values of a DataFrame.Column
     * to a structure so that it can be returned by methods
     * getOrderedColumns and getOrderedData.
     *
     * @param field DataFrame.Column name to search maximum entries for
     * @param max   True - Maximum entry values
     *              False - Minimum entry values
     */
    void searchMax(String field, boolean max);

    /**
     * Writes current data to a given file in JSON
     * format.
     *
     * @param fileName Name of the file to write JSON data in
     */
    void writeToJson(String fileName);
}
