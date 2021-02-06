package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Link;

public class UpdateAvailable extends Dialog {

	protected Object result;
	protected Shell shlUpdate;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UpdateAvailable(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlUpdate.open();
		shlUpdate.layout();
		Display display = getParent().getDisplay();
		while (!shlUpdate.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	public void createContents() {
		shlUpdate = new Shell(getParent(), getStyle());
		shlUpdate.setSize(638, 131);
		shlUpdate.setText("Update");
		shlUpdate.setLayout(new GridLayout(1, false));
		
		Label lblUpdateAvailableDownload = new Label(shlUpdate, SWT.NONE);
		lblUpdateAvailableDownload.setText("Update available! Download link:");
		
		Link link = new Link(shlUpdate, SWT.NONE);
		link.setText("<a>https://github.com/mrobsidy/clanghelper/</a>");
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/mrobsidy/clanghelper/"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		Button btnDismiss = new Button(shlUpdate, SWT.NONE);
		GridData gd_btnDismiss = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDismiss.widthHint = 589;
		btnDismiss.setLayoutData(gd_btnDismiss);
		btnDismiss.setText("Ok");
		btnDismiss.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlUpdate.dispose();
			}
		});
		
	}

}
