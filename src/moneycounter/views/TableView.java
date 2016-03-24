package moneycounter.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import moneycounter.model.OperationData;
import moneycounter.model.TableModel;

public class TableView {
	private TableViewer viewer;

	private MyViewerComparator comparator;

	private TableModel model = TableModel.getInstance();

	private final String[] titles = { "ID", "Дата и время добавления", "Тип операции", "Дата операции", "Категория",
			"Комментарий", "Сумма" };

	public void packItems() {
		for (int i = 0; i < titles.length; i++) {
			viewer.getTable().getColumn(i).pack();
		}
	}

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

		TableViewerColumn viewerColumn = createTableViewerColumn(titles[0], 0);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // ID
			{
				OperationData data = (OperationData) element;
				return "" + data.getId();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[1], 1);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Дата и время добавления
			{
				OperationData data = (OperationData) element;
				return data.getTimestamp().toString();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[2], 2);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Тип операции
			{
				OperationData data = (OperationData) element;
				return data.getOperationType().name().charAt(0) + data.getOperationType().name().substring(1).toLowerCase();
			}
		});	
		
		viewerColumn = createTableViewerColumn(titles[3], 3);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Дата операции
			{
				OperationData data = (OperationData) element;
				return data.getDate().toString();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[4], 4);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Категория
			{
				OperationData data = (OperationData) element;
				return data.getCategory().name().charAt(0) + data.getCategory().name().substring(1).toLowerCase();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[5], 5);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Комментарий
			{
				OperationData data = (OperationData) element;
				return data.getComment();
			}
		});
		
		viewerColumn = createTableViewerColumn(titles[6], 6);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) // Сумма
			{
				OperationData data = (OperationData) element;
				return "" + data.getSum();
			}
		});

        viewer.getTable().update();
        viewer.getTable().pack();

	}

	private TableViewerColumn createTableViewerColumn(String title, final int colNumber) {
		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setAlignment(SWT.LEFT);
		column.setResizable(true);
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
	
    public void refreshViewer()
    {
        viewer.getTable().setVisible(false);
        viewer.refresh();
        packItems();
        viewer.getTable().setVisible(true);
    }
    
    public void setFocus()
    {
//        viewer.getControl().setFocus();
    }
}
