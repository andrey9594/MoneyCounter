package moneycounter.dao;

import java.util.List;
import java.util.Set;

import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.OperationType;

public interface OperationDAO {
	List<OperationData> getAllData();
	
	List<OperationData> getDataByTypeAndCategory(Set <OperationType> type, Set<OperationCategory> category);
	
	void close();
}
