package moneycounter.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import moneycounter.dao.OperationDAO;
import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.OperationType;
import moneycounter.model.TableModel;

public class OperationDAOImpl implements OperationDAO {
	private Connection connection;
	
	public OperationDAOImpl() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find postgresql Driver");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
		 	connection = DriverManager.getConnection(System.getProperty("dbUrl"), System.getProperty("dbUser"), System.getProperty("dbPassword"));
		} catch (SQLException e) {
			System.out.println("Cannot connect to DB");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public List<OperationData> getAllData() {
		List<OperationData> operationData = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			Statement stmt2 = connection.createStatement();
			String sql = "SELECT * FROM operationdata";
			ResultSet cursor = stmt.executeQuery(sql);
			while (cursor.next()) {
				int id = cursor.getInt(1);
				
				Timestamp timestamp = cursor.getTimestamp(2);
				Timestamp date = cursor.getTimestamp(4);
				
				String comment = cursor.getString(7);
				
				Double sum = cursor.getDouble(6);
				
				String getOperationTypeForIdSql = "SELECT OT.name FROM Operationtype OT JOIN OperationData "
						+ "OD ON (OT.id = OD.operation_id) "
						+ "WHERE OD.id = " + id;
				ResultSet cursorForOperationType = stmt2.executeQuery(getOperationTypeForIdSql);
				cursorForOperationType.next();
				OperationType operationType = OperationType.getByDescription(cursorForOperationType.getString(1));
				
				String getOperationCategoryForIdSql = "SELECT OC.name FROM Operationcategory OC JOIN OperationData "
						+ "OD ON (OC.id = OD.category_id) "
						+ "WHERE OD.id = " + id; 
				ResultSet cursorForCategory = stmt2.executeQuery(getOperationCategoryForIdSql);
				cursorForCategory.next();
				OperationCategory category = OperationCategory.getByDescription(cursorForCategory.getString(1));
				
				OperationData data = new OperationData(id, operationType, timestamp, date, category, comment, sum);
				operationData.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return operationData;
	}

	@Override
	public List<OperationData> getDataByTypeAndCategory(Set <OperationType> type, Set<OperationCategory> category) {
		List<OperationData> allData = getAllData();
		List<OperationData> dataWithCategory = new ArrayList<>();
		for (OperationData data : allData) {
			if (category.contains(data.getCategory()) && type.contains(data.getOperationType())) {
				dataWithCategory.add(data);
			}
		}
		return dataWithCategory;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Cannot close the connection");
			e.printStackTrace();
		}
	}

	@Override
	public void addOperation(OperationData data) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String getOperationTypeIdForTypeSql = "SELECT id FROM Operationtype WHERE name = '" + data.getOperationType().getDescription() + "'";
			ResultSet cursorForOperationType = stmt.executeQuery(getOperationTypeIdForTypeSql);
			cursorForOperationType.next();
			int operationId = cursorForOperationType.getInt(1);
			
			String getOperationCategoryIdForTypeSql = "SELECT id FROM Operationcategory WHERE name = '" + data.getCategory().getDescription() + "'";
			ResultSet cursorForOperationCategory = stmt.executeQuery(getOperationCategoryIdForTypeSql);
			cursorForOperationCategory.next();
			int categoryId = cursorForOperationCategory.getInt(1);
			
			Statement insertStmt = connection.createStatement();
			String insertScript = "INSERT INTO Operationdata(operation_id, operation_data, category_id, sum, comment) VALUES (" 
					+ operationId + ", '" + data.getDate() + "', " + categoryId + ", " + data.getSum() + ", '" + data.getComment() + "')";
			insertStmt.execute(insertScript);
			
			TableModel.getInstance().addOperationData(data);
		} catch (SQLException e) {
			System.out.println("Cannot insert the operation");
			e.printStackTrace();
		}
		
			}

	@Override
	public void removeOperation(int id) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM operationdata WHERE id = " + id);
		} catch (SQLException e) {
			System.out.println("Cant remove OperationData with id = " + id);
			e.printStackTrace();
		}
		
	}

	
}
