package moneycounter.views;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import moneycounter.Activator;
import moneycounter.dialogs.StatisticsInfoDialog;
import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.OperationType;
import moneycounter.model.TableModel;

public class TableView {
	@Inject
	EPartService partService;
	
	private TableViewer viewer;

	private MyViewerComparator comparator;

	private TableModel model = TableModel.getInstance();

	private final String[] titles = { "ID", "Дата и время добавления", "Тип операции", "Дата операции", "Категория",
			"Сумма", "Комментарий"};

	public TableView(Composite parent, int horizontalSpan) {
		viewer = new TableViewer(new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION));

		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

		GridData gdForTable = new GridData(GridData.FILL_BOTH);
		gdForTable.horizontalSpan = horizontalSpan;
		viewer.getTable().setLayoutData(gdForTable);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(model.getData());

		comparator = new MyViewerComparator();
		viewer.setComparator(comparator);

		TableViewerColumn viewerColumn = createTableViewerColumn(titles[0], 0, 42);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // ID
			{
				OperationData data = (OperationData) element;
				return "" + data.getId();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[1], 1, 210);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Дата и время добавления
			{
				OperationData data = (OperationData) element;
				//return data.getTimestamp().toString();
				return new Date(data.getTimestamp().getTime()).toString();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[2], 2, 128);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Тип операции
			{
				OperationData data = (OperationData) element;
				return data.getOperationType().getDescription().charAt(0) + data.getOperationType().getDescription().substring(1).toLowerCase();
			}
		});	
		
		viewerColumn = createTableViewerColumn(titles[3], 3, 133);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Дата операции
			{
				OperationData data = (OperationData) element;
				//return data.getDate().toString();
				return new Date(data.getDate().getTime()).toString();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[4], 4, 125);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Категория
			{
				OperationData data = (OperationData) element;
				return data.getCategory().getDescription().charAt(0) + data.getCategory().getDescription().substring(1).toLowerCase();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[5], 5, 85);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Сумма
			{
				OperationData data = (OperationData) element;
				return "" + data.getSum();
			}
			
			@Override
			public Color getBackground(Object element) {
				ArrayList list = (ArrayList) viewer.getInput();
				int index = list.indexOf(element);
				if (TableModel.getInstance().getData().get(index).getOperationType() == OperationType.ДОХОД)
					return new Color(viewer.getTable().getDisplay(), new RGB(168, 222, 113));
				else
					return new Color(viewer.getTable().getDisplay(), new RGB(210, 84, 84));
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[6], 6, 400);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Комментарий
			{
				OperationData data = (OperationData) element;
				return data.getComment();
			}
		});
		
		viewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					String bodyQuestion = "Вы действительно хотите удалить эту запись?";
					String bodyInformation = "Запись была удалена!";
					if (viewer.getTable().getSelectionIndices().length > 1) {
						bodyQuestion = "Вы действительно хотите удалить эти записи?";
						bodyInformation = "Записи были удалены";
					}
					if (MessageDialog.openQuestion(parent.getShell(), "Подтверждение удаления", bodyQuestion)) {
						for (int index : viewer.getTable().getSelectionIndices()) {
							int id = Integer.parseInt(viewer.getTable().getItem(index).getText(0));
							model.deleteById(id);
							Activator.getDao().removeOperation(id);
						}
						refreshViewer();
							
						MessageDialog.openInformation(parent.getShell(), "Успешно", bodyInformation);
					}
						
				}
				if ((e.character == SWT.CR || e.character == SWT.LF)) {
					Set <String> dates = new TreeSet<>();
					double totalSum = 0;
					Map <OperationCategory, Double> sumMap = new HashMap<>();
					 
					for (int index : viewer.getTable().getSelectionIndices()) {
						if (OperationType.getByDescription(viewer.getTable().getItem(index).getText(2)) == OperationType.ДОХОД)
							continue;
						OperationCategory category = OperationCategory.getByDescription(viewer.getTable().getItem(index).getText(4));
						Double sum = sumMap.get(category);
						if (sum == null)
							sum = 0.;
						sum += Double.parseDouble(viewer.getTable().getItem(index).getText(5));
						sumMap.put(category, sum);
						totalSum += Double.parseDouble(viewer.getTable().getItem(index).getText(5));
						dates.add(viewer.getTable().getItem(index).getText(3));
					}
					if (dates.size() != 0) {
						StatisticsInfoDialog dialog = new StatisticsInfoDialog(viewer.getTable().getShell(), totalSum,
								sumMap, dates.size());
						dialog.open();
					}
				}
			}
		});

        viewer.getTable().update();
        viewer.getTable().pack();

	}

	private TableViewerColumn createTableViewerColumn(String title, final int colNumber, int length) {
		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setAlignment(SWT.LEFT);
		column.setResizable(true);
		column.setWidth(length);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				refreshViewer();
			}
		};
		return selectionAdapter;
	}
	
    public void refreshViewer() {
        viewer.getTable().setVisible(false);
        viewer.refresh();
        viewer.getTable().setVisible(true);
    }
  
}
