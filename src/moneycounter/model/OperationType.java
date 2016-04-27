package moneycounter.model;

public enum OperationType {
	ДОХОД("Доход"), 
	РАСХОД("Расход"),
	;

	private String description;

	private OperationType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public static OperationType getByDescription(String name) {
		for (OperationType type : OperationType.values()) {
			if (type.getDescription().toLowerCase().equals(name.toLowerCase()))
				return type;
		}
		return null;	
	}
}
