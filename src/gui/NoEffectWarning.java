package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class NoEffectWarning extends Dialog {

	protected Object result;
	protected Shell shlWarning;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NoEffectWarning(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlWarning.open();
		shlWarning.layout();
		Display display = getParent().getDisplay();
		while (!shlWarning.isDisposed()) {
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
		shlWarning = new Shell(getParent(), getStyle());
		shlWarning.setSize(466, 224);
		shlWarning.setText("Warning");
		shlWarning.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(shlWarning, SWT.WRAP);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 2);
		gd_lblNewLabel.widthHint = 420;
		gd_lblNewLabel.heightHint = 97;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Your sound change effects neither stressed nor unstressed phonemes (so, no phonemes at all.) This is permissible, but your sound change would have no effect. Do you wish to continue (OK) or go ahead and edit the settings (Go back) ?");
		
		Button btnOk = new Button(shlWarning, SWT.NONE);
		GridData gd_btnOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOk.widthHint = 215;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = new Boolean(true);
				shlWarning.dispose();
			}
		});
		
		Button btnGoBack = new Button(shlWarning, SWT.NONE);
		GridData gd_btnGoBack = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGoBack.widthHint = 200;
		btnGoBack.setLayoutData(gd_btnGoBack);
		btnGoBack.setText("Go back");
		btnGoBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = new Boolean(false);
				shlWarning.dispose();
			}
		});
	}

}
