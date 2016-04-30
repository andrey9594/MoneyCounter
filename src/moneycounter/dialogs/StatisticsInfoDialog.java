package moneycounter.dialogs;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import moneycounter.model.OperationCategory;

public class StatisticsInfoDialog {
    private static final int HEIGHT = 215;
    private static final int WIDTH = 350; 
    
	private Shell shell;
	
	private int dateCount;
	
	private double totalSum;
	
	private Map <OperationCategory, Double> sumMap;

	public StatisticsInfoDialog(Shell parentShell, double totalSum, Map <OperationCategory, Double> sumMap, int dateCount) {
		shell = new Shell(parentShell);
        
		this.totalSum = totalSum;
		this.sumMap = sumMap;
		this.dateCount = dateCount;
		
		createGUI();
	}

	public void createGUI() {
		shell.setSize(WIDTH, HEIGHT);
		shell.setLocation(730, 375);

		shell.setText("Статистика для выбранных операций");
		
		GridLayout layoutForShell = new GridLayout(1, true);
		shell.setLayout(layoutForShell);
		
		String message = "Дней выбравно: " + dateCount + "\n";
		for (OperationCategory category : OperationCategory.values()) 
		{
			Double sum = sumMap.get(category);
			if (sum == null)
				sum = 0.;
			message += category.getDescription() + " = " + sum + " рублей или " + sum / dateCount + " рублей/день\n";
		}
		message += "Всего = " + totalSum + " рублей или " + totalSum / dateCount + " рублей/день\n";
		Label lblInfo = new Label(shell, SWT.NONE);
 		lblInfo.setText(message);
 		lblInfo.setVisible(true);
	}
	
	public void open() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
	}
	
}
