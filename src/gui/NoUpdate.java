package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class NoUpdate {

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		Shell shlUpdate = new Shell();
		shlUpdate.setSize(146, 120);
		shlUpdate.setText("Update");
		shlUpdate.setLayout(new GridLayout(1, false));
		
		Label lblYouAreUp = new Label(shlUpdate, SWT.NONE);
		lblYouAreUp.setText("You are up to date.");
		
		Button btnOk = new Button(shlUpdate, SWT.NONE);
		GridData gd_btnOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOk.widthHint = 126;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlUpdate.dispose();
			}
		});

		shlUpdate.open();
		shlUpdate.layout();
		while (!shlUpdate.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
