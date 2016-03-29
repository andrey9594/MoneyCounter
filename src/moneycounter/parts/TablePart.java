package moneycounter.parts;

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import moneycounter.dialogs.AddingDialog;
import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.OperationType;
import moneycounter.model.TableModel;
import moneycounter.views.TableView;

public class TablePart {
	private TableView view;
	
	private Button addButton;
	private Button reportButton;
    private ArrayList<Button> typeRadioButtons;
    private ArrayList<Button> categoryCheckBoxes;

	private Text txtInput;

	@PostConstruct
	public void createComposite(Composite parent) {
		GridLayout gl = new GridLayout(3, false);
        parent.setLayout(gl);
        
        Group groupTypes = new Group(parent, SWT.SHADOW_ETCHED_IN);
        GridLayout glGroupType = new GridLayout(3, false);
        groupTypes.setLayout(glGroupType);
        groupTypes.setText("Баланс");
        typeRadioButtons = new ArrayList<>();
        String typeValues[] = {"Все", "Доход", "Расход"};
        int ind = 0;
        for (String type : typeValues)
        {
            Button newTypeRadioButton = new Button(groupTypes, SWT.RADIO);
            newTypeRadioButton.setText(type);
            ind += 1;
            if (ind == 1)
            {
            	newTypeRadioButton.setSelection(true);
            }
            typeRadioButtons.add(newTypeRadioButton);
        }
        
        Group groupCategory = new Group(parent, SWT.SHADOW_ETCHED_IN);
        GridLayout glGroupCategory = new GridLayout(8, false);
        groupCategory.setLayout(glGroupCategory);
        groupCategory.setText("Категории");
        categoryCheckBoxes = new ArrayList<>();
        String categoryValues[] = {"Продукты", "Столовая", "Зарплата", "Проезд", "Лекарства", "Оплата услуг", "Отдых", "Иное"};
        for (String category : categoryValues)
        {
            Button newCategoryCheckBox = new Button(groupCategory, SWT.CHECK);
            newCategoryCheckBox.setText(category);
            newCategoryCheckBox.setSelection(true);
            categoryCheckBoxes.add(newCategoryCheckBox);
        }
        
        Group groupCommands = new Group(parent, SWT.SHADOW_ETCHED_IN);
        GridLayout glGroupCommands = new GridLayout(2, false);
        groupCommands.setLayout(glGroupCommands);
        groupCommands.setText("Управление данными");
        //
        addButton = new Button(groupCommands, SWT.PUSH);
        addButton.setText("Добавить");
        addButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddingDialog dialog = new AddingDialog(parent.getShell());
				OperationData newData = dialog.open();
				if (newData != null) {
					// добавляем новые данные
				}
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        //
        reportButton = new Button(groupCommands, SWT.PUSH);
        reportButton.setText("Создать отчет");
        reportButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(parent.getShell(), "Hey", "Hello2!");
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("Введите запрос");
		
        GridData gdForTxt = new GridData();
        gdForTxt.horizontalSpan = 3;
        gdForTxt.horizontalAlignment = GridData.FILL;
        txtInput.setLayoutData(gdForTxt);
        
        view = new TableView(parent, 3);
        
        TableModel.getInstance().addOperationData(new OperationData(1, OperationType.РАСХОД, new Date(122222222232L), new Date(1223232322L), OperationCategory.ПРОДУКТЫ, "Без комментариев", 21100));
        TableModel.getInstance().addOperationData(new OperationData(2, OperationType.ДОХОД, new Date(12223123123123126L), new Date(12122313123122L), OperationCategory.ЗАРПЛАТА, "Аванс", 9200));
        TableModel.getInstance().addOperationData(new OperationData(3, OperationType.РАСХОД, new Date(123222222222232L), new Date(51223232322L), OperationCategory.ОПЛАТА_УСЛУГ, "Без комментариев", 500));
        
        view.refreshViewer();
	}
}