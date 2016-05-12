package moneycounter.dialogs;

import java.sql.Timestamp;
import java.util.function.Function;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.OperationType;

public class AddingDialog {
	private Shell shell;

	private static final int HEIGHT = 570;
	private static final int WIDTH = 243;
	
	private OperationData newOperationData;
	

	public AddingDialog(Shell parent) {
		shell = new Shell(parent);
		createGUI();
	}

	private void createGUI() {
		shell.setSize(WIDTH, HEIGHT);
		shell.setLocation(1230, 175);

		shell.setText("Добавление операции");

		GridLayout layoutForShell = new GridLayout(1, true);
		shell.setLayout(layoutForShell);
		
		Group groupForType = new Group(shell, SWT.SHADOW_ETCHED_IN);
		GridLayout glGroupForType = new GridLayout(2, false);
		GridData gdGroupForType = new GridData();
		gdGroupForType.horizontalAlignment = SWT.FILL;
		groupForType.setLayoutData(gdGroupForType);
		groupForType.setLayout(glGroupForType);
		Label lblType = new Label(groupForType, SWT.NONE);
		lblType.setText("Тип операции:       ");
		Combo comboOperationType = new Combo(groupForType, SWT.READ_ONLY);
		for (OperationType type : OperationType.values()) {
			comboOperationType.add(type.getDescription());
		}
		comboOperationType.select(1);
		
		Group groupForOperationDate = new Group(shell, SWT.SHADOW_ETCHED_IN);
		GridLayout glGroupForOperationType = new GridLayout(1, false);
		GridData gdGroupForOperationDate = new GridData();
		gdGroupForOperationDate.horizontalAlignment = SWT.FILL;
		groupForOperationDate.setLayout(glGroupForOperationType);
		groupForOperationDate.setLayoutData(gdGroupForOperationDate);
		Label lblDate = new Label(groupForOperationDate, SWT.NONE);
		lblDate.setText("Дата операции: ");
		Text dateText = new Text(groupForOperationDate, SWT.NONE);
		GridData gdForDateText = new GridData();
		gdForDateText.horizontalAlignment = SWT.FILL;
		dateText.setLayoutData(gdForDateText);
		dateText.setEditable(false);
		DateTime calendar = new DateTime(groupForOperationDate, SWT.CALENDAR);
		
		Function<DateTime, String> convertCalendarToString = (c) -> "" + (c.getDay() <= 9 ? "0" : "") + c.getDay() + "/"
				+ (c.getMonth() + 1 <= 9 ? "0" : "") + (calendar.getMonth() + 1) + "/" + calendar.getYear();
		
		GridData gdForCalendar = new GridData();
		gdForCalendar.horizontalAlignment = SWT.FILL;
		gdForCalendar.grabExcessHorizontalSpace = true;
		calendar.setLayoutData(gdForCalendar);
		
		calendar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dateText.setText(convertCalendarToString.apply(calendar));
			}
		});
		dateText.setText(convertCalendarToString.apply(calendar));

		Group groupForCategory = new Group(shell, SWT.SHADOW_ETCHED_IN);
		GridLayout glGroupForCategory = new GridLayout(2, false);
		GridData gdGroupForCategory = new GridData();
		gdGroupForCategory.horizontalAlignment = SWT.FILL;
		groupForCategory.setLayoutData(gdGroupForCategory);
		groupForCategory.setLayout(glGroupForCategory);
		Label lblCategory = new Label(groupForCategory, SWT.NONE);
		lblCategory.setText("Категория: ");
		Combo comboCategoryType = new Combo(groupForCategory, SWT.READ_ONLY);
		for (OperationCategory category : OperationCategory.values()) {
			comboCategoryType.add(category.getDescription());
		}
		comboCategoryType.select(0);
		
		Group groupForSum = new Group(shell, SWT.SHADOW_ETCHED_IN);
		GridLayout glGroupForSum = new GridLayout(2, false);
		GridData gdGroupForSum = new GridData();
		gdGroupForSum.horizontalAlignment = SWT.FILL;
		groupForSum.setLayoutData(gdGroupForSum);
		groupForSum.setLayout(glGroupForSum);
		Label lblSum = new Label(groupForSum, SWT.NONE);
		lblSum.setText("Введите сумму: ");
		Text txtSum = new Text(groupForSum, SWT.NONE);
		GridData gdForTxtSum = new GridData();
		gdForTxtSum.horizontalAlignment = SWT.FILL;
		gdForTxtSum.grabExcessHorizontalSpace = true;
		
		txtSum.setLayoutData(gdForTxtSum);
		txtSum.setText("200");
		
		
		Group groupForComment = new Group(shell, SWT.SHADOW_ETCHED_IN);
		GridLayout glGroupForComment = new GridLayout(1, true);
		GridData gdGroupForComment = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupForComment.setLayout(glGroupForComment);
		groupForComment.setLayoutData(gdGroupForComment);
		
		Label lblComment = new Label(groupForComment, SWT.NONE);
		lblComment.setText("Введите комментарий:");
		
		Text txtComment = new Text(groupForComment, SWT.WRAP);
		GridData gdForTxtComment = new GridData(SWT.FILL, SWT.FILL, true, true);

		txtComment.setLayoutData(gdForTxtComment);
	
		txtComment.setText(" ");
		txtComment.addMouseListener(new MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				txtComment.setSelection(0, txtComment.getText().length());
			};
		});
		
		Group groupForButton = new Group(shell, SWT.SHADOW_ETCHED_IN);
		RowLayout rlGroupForButton = new RowLayout();
		rlGroupForButton.marginLeft = 50;
		rlGroupForButton.justify = false;
		rlGroupForButton.pack = false;
		rlGroupForButton.wrap = false;
		rlGroupForButton.type = SWT.HORIZONTAL;
		GridData gdGroupForButton = new GridData();
		gdGroupForButton.horizontalAlignment = SWT.FILL;
		groupForButton.setLayout(rlGroupForButton);
		groupForButton.setLayoutData(gdGroupForButton);
		Button buttonOk = new Button(groupForButton, SWT.PUSH);
		buttonOk.setText("Добавить");
		buttonOk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OperationType operationType = OperationType.getByDescription(comboOperationType.getText());
				Timestamp date = new Timestamp(calendar.getYear() - 1900, calendar.getMonth(), calendar.getDay(), 0, 0, 0, 0);
				OperationCategory category = OperationCategory.getByDescription(comboCategoryType.getText());
				Double sum = Double.parseDouble(txtSum.getText());
				String comment = txtComment.getText();
				newOperationData = new OperationData(-1, operationType, null, date, category, comment, sum);
				shell.close();
			};
		});
		Button buttonCancel = new Button(groupForButton, SWT.PUSH);
		buttonCancel.setText("Отмена");
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			};
		});
		
		Listener listenerForAdding = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.type == SWT.KeyDown && (event.character == SWT.CR || event.character == SWT.LF)) {
					buttonOk.notifyListeners(SWT.Selection, new Event());
				}
			}
		};

		buttonOk.addListener(SWT.KeyDown, listenerForAdding);
		buttonCancel.addListener(SWT.KeyDown, listenerForAdding);
		txtSum.addListener(SWT.KeyDown, listenerForAdding);
		txtComment.addListener(SWT.KeyDown, listenerForAdding);
	}	

	public OperationData open() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
		return newOperationData;
	}
}
