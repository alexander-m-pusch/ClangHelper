package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import phonemes.Phoneme;
import phonemes.PhonemeClass;
import phonemes.PhonemeManager;
import phonemes.SoundChange;

import org.eclipse.swt.widgets.Button;

public class SoundShiftWizard {

	protected Shell shell;
	private Text text;
	
	public boolean unstressed = false;
	public boolean stressed = false;
	public PhonemeClass shiftBefore = null;
	public PhonemeClass shiftAfter = null;
	public PhonemeClass shiftFrom = null;
	public Phoneme shiftTo = null;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void open(String openedSoundChange) {
		Display display = Display.getDefault();
		shell = new Shell();
		shell.setSize(592, 399);
		shell.setText("Sound Shift Editor");
		shell.setLayout(new GridLayout(2, false));
		
		SoundChange soch = PhonemeManager.getSoundChange(openedSoundChange);
		
		if(soch != null) {
			shiftFrom = soch.getShiftedFrom();
			shiftTo = soch.getShiftTo();
			shiftBefore = soch.getShiftedBefore();
			shiftAfter = soch.getShiftedAfter();
			unstressed = soch.isAffectUnstressed();
			stressed = soch.isAffectStressed();
		}
		
		Label lblSelectFrom = new Label(shell, SWT.NONE);
		lblSelectFrom.setText("Shift from... (required)");
		
		Label lblShiftTo = new Label(shell, SWT.NONE);
		lblShiftTo.setText("Shift to... (required)");
		
		List shiftFromList = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list = new GridData(GridData.FILL_BOTH);
		gd_list.widthHint = 263;
		shiftFromList.setLayoutData(gd_list);
		shiftFromList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(shiftFromList.getSelection().length != 0) shiftFrom = PhonemeManager.getPhonemeClass(shiftFromList.getSelection()[0]);
			}
		});
		for(PhonemeClass pc : PhonemeManager.getPhonemeClasses()) {
			shiftFromList.add(pc.name());
		}
		if(soch != null) {
			shiftFromList.setSelection(new String[]{ soch.getShiftedFrom().name() });
		}
		
		List shiftToList = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list_1 = new GridData(GridData.FILL_BOTH);
		gd_list_1.widthHint = 238;
		shiftToList.setLayoutData(gd_list_1);
		shiftToList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(shiftToList.getSelection().length != 0)  shiftTo = PhonemeManager.getPhoneme(shiftToList.getSelection()[0]);
			}
		});
		for(Phoneme pc : PhonemeManager.getPhonemes()) {
			shiftToList.add(pc.getRepresentation());
		}
		if(soch != null) {
			shiftToList.setSelection(new String[]{ soch.getShiftTo().getRepresentation() });
		}
		
		Label lblShiftBefore = new Label(shell, SWT.NONE);
		lblShiftBefore.setText("Shift before...");
		Label lblShiftAfter = new Label(shell, SWT.NONE);
		lblShiftAfter.setText("Shift after...");
		
		List shiftBeforeList = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list_2 = new GridData(GridData.FILL_BOTH);
		gd_list_2.widthHint = 263;
		shiftBeforeList.setLayoutData(gd_list_2);
		shiftBeforeList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(shiftBeforeList.getSelection().length != 0) {
					if(!shiftBeforeList.getSelection()[0].equals("<none>")) shiftBefore = PhonemeManager.getPhonemeClass(shiftBeforeList.getSelection()[0]);
				}
			}
		});
		shiftBeforeList.add("<none>");
		for(PhonemeClass pc : PhonemeManager.getPhonemeClasses()) {
			shiftBeforeList.add(pc.name());
		}
		if(soch != null) {
			if(soch.getShiftedBefore() != null) {
				shiftBeforeList.setSelection(new String[]{ soch.getShiftedBefore().name() });
			} else {
				shiftBeforeList.setSelection(new String[] { "<none>" });
			}
		} 
		
		
		List shiftAfterList = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list_3 = new GridData(GridData.FILL_BOTH);
		gd_list_3.widthHint = 236;
		shiftAfterList.setLayoutData(gd_list_3);
		shiftAfterList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) { 
				if(shiftAfterList.getSelection().length != 0) {
						if(!shiftAfterList.getSelection()[0].equals("<none>")) {
							shiftAfter = PhonemeManager.getPhonemeClass(shiftAfterList.getSelection()[0]);
						} else {
							shiftAfter = null;
						}
				}
			}
		});
		shiftAfterList.add("<none>");
		for(PhonemeClass pc : PhonemeManager.getPhonemeClasses()) {
			shiftAfterList.add(pc.name());
		}
		if(soch != null) {
			if(soch.getShiftedAfter() != null)	{
				shiftAfterList.setSelection(new String[]{ soch.getShiftedAfter().name() });
			} else {
				shiftAfterList.setSelection(new String[] { "<none>" });
			}
		} 
		
		
		Button btnShiftUnstressedPhonemes = new Button(shell, SWT.CHECK);
		btnShiftUnstressedPhonemes.setText("Shift unstressed phonemes");
		btnShiftUnstressedPhonemes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				unstressed = btnShiftUnstressedPhonemes.getSelection();
			}
		});
		if(soch != null) {
			if(soch.isAffectUnstressed()) btnShiftUnstressedPhonemes.setSelection(true);
		}
		
		Button btnShiftStressedPhonemes = new Button(shell, SWT.CHECK);
		btnShiftStressedPhonemes.setText("Shift stressed phonemes");
		btnShiftStressedPhonemes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stressed = btnShiftStressedPhonemes.getSelection();
			}
		});
		if(soch != null) {
			if(soch.isAffectStressed()) btnShiftStressedPhonemes.setSelection(true);
		}
		
		Label lblSoundshiftName = new Label(shell, SWT.NONE);
		GridData gd_lblSoundshiftName = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblSoundshiftName.widthHint = 276;
		lblSoundshiftName.setLayoutData(gd_lblSoundshiftName);
		lblSoundshiftName.setText("Name of sound shift (required)");
		
		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if(soch != null) {
			text.setText(soch.name());
		}
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton.widthHint = 563;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Save and close");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(shiftFrom != null && shiftTo != null && !text.getText().equals("") && PhonemeManager.getSoundChange(text.getText()) == null) {
					if(!stressed && !unstressed) {
						NoEffectWarning warning = new NoEffectWarning(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
						boolean result = ((Boolean) warning.open()).booleanValue();
						if(result) {
							PhonemeManager.addSoundChange(new SoundChange(text.getText(), shiftBefore, shiftAfter, shiftFrom, shiftTo, 1, 1, false, false));
							
							shell.dispose();
						} else {
							return;
						}
					} else {
						PhonemeManager.addSoundChange(new SoundChange(text.getText(), shiftBefore, shiftAfter, shiftFrom, shiftTo, 1, 1, unstressed, stressed));
						
						shell.dispose();
					}
				} else if(shiftFrom != null && shiftTo != null && soch != null) {
					PhonemeManager.removeSoundChange(soch);
					PhonemeManager.addSoundChange(new SoundChange(text.getText(), shiftBefore, shiftAfter, shiftFrom, shiftTo, 1, 1, false, false));
					shell.dispose();
				} else {
					NotEnoughInfoError err = new NotEnoughInfoError(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					err.open();
				}
			}
		});

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
