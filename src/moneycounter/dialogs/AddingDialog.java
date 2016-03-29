package moneycounter.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import moneycounter.model.OperationData;

public class AddingDialog {
	// private Shell parent;
	private Shell shell;

	public AddingDialog(Shell parent) {
		// this.parent = parent;
		shell = new Shell(parent);
		GridLayout layoutForShell = new GridLayout(2, false);
		shell.setLayout(layoutForShell);

		Label lblInfo = new Label(shell, SWT.NONE);
		lblInfo.setText("Info!");
	}

	public OperationData open() {
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
		shell.getDisplay().dispose();
		return null;
	}
}
