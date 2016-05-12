package moneycounter.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class StatisticsView {
	private TableViewer viewer;
	
	public StatisticsView(Composite parent) {
		viewer = new TableViewer(new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION));
		
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

		GridData gdForTable = new GridData(GridData.FILL_BOTH);
		viewer.getTable().setLayoutData(gdForTable);
		
		createTableViewerColumn("", 0, 113);
		createTableViewerColumn("", 1, 125);
		createTableViewerColumn("", 2, 125);
		createTableViewerColumn("", 3, 125);
		createTableViewerColumn("", 4, 125);
		createTableViewerColumn("", 5, 125);
		
        viewer.getTable().update();
        viewer.getTable().pack();
	}
	
	public void add(String name, String fst, String second, String thrd, String three, String all) {
		TableItem item = new TableItem(viewer.getTable(), SWT.NONE);
		item.setText(0, name);
		item.setText(1, fst);
		item.setText(2, second);
		item.setText(3, thrd);
		item.setText(4, three);
		item.setText(5, all);
	}
	
	private TableViewerColumn createTableViewerColumn(String title, final int colNumber, int length) {
		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setAlignment(SWT.LEFT);
		column.setResizable(true);
		column.setWidth(length);
		column.setMoveable(true);
		return viewerColumn;
	}
	
    public void clear() {
    	viewer.getTable().removeAll();
    }
}
