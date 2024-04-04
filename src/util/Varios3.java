/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

/**
 *
 * @author SistemasRC
 */
public class Varios3 {
    
    public static void main(String[] args) {
        Table table = Table.create("MyTable");

    
        // Step 2: Add columns to the table
        table.addColumns(
                StringColumn.create("Name", "John", "Doe", "Jane"),
                DoubleColumn.create("Age", 30, 25, 35),
                BooleanColumn.create("Married", true, false, true)
        );

        // Step 3: Display the table
        System.out.println("Table:");
        System.out.println(table);

        // Optionally, you can perform various operations on the table, such as filtering, sorting, etc.
        // For example, sorting the table by the 'Age' column
        Table sortedTable = table.sortAscendingOn("Age");

        // Display the sorted table
        System.out.println("Sorted Table:");
        System.out.println(sortedTable);
    }
    
}
