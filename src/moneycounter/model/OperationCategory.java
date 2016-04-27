package moneycounter.model;

public enum OperationCategory {
	ПРОДУКТЫ("Продукты"),
	СТОЛОВАЯ("Столовая"),
	ЗАРПЛАТА("Зарплата"),
	ПРОЕЗД("Проезд"),
	ЛЕКАРСТВА("Лекарства"),
	ОПЛАТА_УСЛУГ("Оплата услуг"),
	ОТДЫХ("Отдых"),
	ИНОЕ("Иное"),
	;
	
	private String description;
	
	private OperationCategory(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static OperationCategory getByDescription(String name) {
		for (OperationCategory type : OperationCategory.values()) {
			if (type.getDescription().toLowerCase().equals(name.toLowerCase()))
				return type;
		}
		return null;	
	}
}