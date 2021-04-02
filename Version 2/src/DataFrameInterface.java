import java.util.ArrayList;

/**
 * The following methods must be implemented
 * when creating a dataFrame data structure.
 *
 * @author mathushanmathiyalagan
 * @version 25-03-21
 */
public interface DataFrameInterface {
    /**
     * Adds a given column to the dataFrame
     * data structure.
     *
     * @param column The column to add to the
     *               dataFrame
     */
    void addColumn(Column column);

    /**
     * Returns the names of the columns in the
     * dataFrame.
     *
     * @return Names of the columns in the
     * dataFrame
     */
    ArrayList<String> getColumnNames();

    /**
     * Returns the number of values in a given
     * column.
     *
     * @param columnName Name of the column to
     *                   count number of entries
     *                   for
     * @return Frequency of entries in column
     */
    int getRowCount(String columnName);

    /**
     * Returns the value stored in a given
     * column name at a given position.
     *
     * @param columnName Name of the column to
     *                   search an entry for
     * @param rowIndex   Position of the column to
     *                   search an entry for
     * @return Entry stored in given column name
     * and index
     */
    String getValue(String columnName, int rowIndex);

    /**
     * Stores provided value to a given column
     * name at a given position.
     *
     * @param columnName Name of the column to
     *                   put an entry for
     * @param rowIndex   Position of the column to
     *                   put an entry for
     * @param value      Value to store at given position
     */
    void putValue(String columnName, int rowIndex, String value);

    /**
     * Stores provided value to a given column
     * name at the end position.
     *
     * @param columnName Name of the column to
     *                   put an entry for
     * @param value      Value to store at given position
     */
    void addValue(String columnName, String value);
}
