package moneycounter.parts;

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.OperationType;
import moneycounter.model.TableModel;
import moneycounter.views.TableView;

public class SamplePart {
	private TableView view;
	
    private ArrayList<Button> typeRadioButtons;
    private ArrayList<Button> categoryCheckBoxes;

	private Text txtInput;

	@PostConstruct
	public void createComposite(Composite parent) {
		GridLayout gl = new GridLayout(2, false);
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

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("Введите запрос");
		
        GridData gdForTxt = new GridData();
        gdForTxt.horizontalSpan = 2;
        gdForTxt.horizontalAlignment = GridData.FILL;
        txtInput.setLayoutData(gdForTxt);
        
        view = new TableView(parent, 3);
        
        TableModel.getInstance().addOperationData(new OperationData(1, OperationType.РАСХОД, new Date(122222222232L), new Date(1223232322L), OperationCategory.ПРОДУКТЫ, "Без комментариев", 100));
        TableModel.getInstance().addOperationData(new OperationData(2, OperationType.ДОХОД, new Date(122231231231231266L), new Date(121223131231232L), OperationCategory.ЗАРПЛАТА, "Аванс", 9200));
        
        view.refreshViewer();
	}
	
	@Focus
	public void setFocus()
	{
		view.setFocus();
	}
}