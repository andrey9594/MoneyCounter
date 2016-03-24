package moneycounter.model;

import java.util.ArrayList;
import java.util.List;

public class TableModel {
	private ArrayList<OperationData> data = new ArrayList<>();

	private static volatile TableModel instance;

	private TableModel() { }

	public static TableModel getInstance() {
		if (instance == null) {
			synchronized (TableModel.class) {
				if (instance == null)
					instance = new TableModel();
			}
		}
		return instance;
	}
	
	public void clear() {
		data.clear();
	}
	
	public void addOperationData(OperationData operationData) {
		data.add(operationData);
	}
	
	public List<OperationData> getData() {
		return data;
	}
}
