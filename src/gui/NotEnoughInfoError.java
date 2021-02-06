package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class NotEnoughInfoError extends Dialog {

	protected Object result;
	protected Shell shlError;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NotEnoughInfoError(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlError.open();
		shlError.layout();
		Display display = getParent().getDisplay();
		while (!shlError.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlError = new Shell(getParent(), getStyle());
		shlError.setSize(642, 158);
		shlError.setText("Error");
		shlError.setLayout(new GridLayout(1, false));
		
		Label lblYouDidntGive = new Label(shlError, SWT.NONE);
		lblYouDidntGive.setText("You didn't edit all required fields or the name you are using is already in use. Check your input.");
		new Label(shlError, SWT.NONE);
		
		Button btnOk = new Button(shlError, SWT.NONE);
		GridData gd_btnOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOk.widthHint = 617;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlError.dispose();
			}
		});

	}

}
