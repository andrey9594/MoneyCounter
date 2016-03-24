package moneycounter.model;

import java.sql.Date;
import java.util.Objects;

/**
 * Класс для хранения операции (доход/расход, сумма, дата, прочее)
 *
 */
public class OperationData {
	/**
	 * Идентификатор операции
	 */
	private int id;
	
	/**
	 * Тип операции
	 */
	private OperationType operationType;
	
	/**
	 * Дата и время добавления записи
	 */
	private Date timestamp;
	
	/**
	 * Дата операции 
	 */ 
	private Date date;
	
	/**
	 * Категория, к которой относится операция
	 */
	private OperationCategory category;
	
	/**
	 * Комментарий к операции
	 */
	private String comment;
	
	/**
	 * Сумма операции
	 */
	private double sum;
	
	public OperationData(int id, OperationType operationType, Date timestamp, Date date, OperationCategory category, String comment, double sum)
	{
		Objects.requireNonNull(operationType, "Operation type cannot be null!");
		Objects.requireNonNull(category, "Category cannot be null!");
		
		this.id = id;
		this.operationType = operationType;
		this.timestamp = timestamp;
		this.date = date;
		this.category = category;
		this.comment = comment;
		this.sum = sum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OperationCategory getCategory() {
		return category;
	}

	public void setCategory(OperationCategory category) {
		this.category = category;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
}
