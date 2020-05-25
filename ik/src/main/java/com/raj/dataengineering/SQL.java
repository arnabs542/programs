package com.raj.dataengineering;

public class SQL {
/**
 * # Given cust_id,cust_data,country write an SQL for getting number of customers in each country, sorted high to low:
 * SELECT COUNT(cust_id), country
 * FROM customers
 * GROUP BY country
 * ORDER BY COUNT(cust_id) DESC;
 *
 * # SQL statement lists the number of customers in each country, sorted high to low (Only include countries with more than 5 customers):
 * SELECT COUNT(cust_id), country
 * FROM customers
 * GROUP BY country
 * HAVING COUNT(cust_id) > 5
 * ORDER BY COUNT(cust_id) DESC;
 *
 * # Find Nth max salary
 * SELECT salary FROM Employee ORDER BY salary DESC LIMIT N-1,1   => pagination supported only in mysql
 * SQL Server Top N:
 * SELECT TOP 1 salary FROM (SELECT DISTINCT TOP N salary FROM #Employee ORDER BY salary DESC) AS temp ORDER BY salary
 *
 * # JOINS: https://www.w3schools.com/sql/sql_join.asp
 * (INNER) JOIN: Returns records that have matching values in both tables
 * LEFT (OUTER) JOIN: Returns all records from the left table, and the matched records from the right table
 * RIGHT (OUTER) JOIN: Returns all records from the right table, and the matched records from the left table
 * FULL (OUTER) JOIN: Returns all records when there is a match in either left or right table
 *
 */
}
