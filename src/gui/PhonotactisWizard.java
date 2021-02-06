package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import phonemes.PhonemeClass;
import phonemes.PhonemeManager;
import phonemes.Phonotactics;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class PhonotactisWizard {
	
	int slot = -1;
	boolean changed = false;
	private Text text;
	
	/**
	 * 
	 * @wbp.parser.entryPoint
	 * Open the window.
	 */
	public void open() {
		
		if(PhonemeManager.getPhonotactics() == null) {
			PhonemeManager.setPhonotactics(new Phonotactics());
		}
		
		Display display = Display.getDefault();
		Shell shlPhonotacticsWizard = new Shell();
		shlPhonotacticsWizard.setSize(894, 428);
		shlPhonotacticsWizard.setText("Phonotactics wizard");
		shlPhonotacticsWizard.setLayout(new GridLayout(2, false));
		
		Label lblPositionInSyllable = new Label(shlPhonotacticsWizard, SWT.NONE);
		lblPositionInSyllable.setText("Position in syllable");
		new Label(shlPhonotacticsWizard, SWT.NONE);
		
		List list = new List(shlPhonotacticsWizard, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_list = new GridData(GridData.FILL_BOTH);
		gd_list.horizontalSpan = 2;
		gd_list.widthHint = 117;
		list.setLayoutData(gd_list);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list.getSelection().length != 0) {
					slot = Integer.parseInt(list.getSelection()[0]);
					changed = true;
				}
			}
		});
		
		Button btnRemoveSlot = new Button(shlPhonotacticsWizard, SWT.NONE);
		btnRemoveSlot.setText("Remove slot");
		btnRemoveSlot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list.getSelection().length != 0) {
					PhonemeManager.getPhonotactics().removeSlot(Integer.parseInt(list.getSelection()[0]));;
				}
			}
		});
		
		Button btnNewButton = new Button(shlPhonotacticsWizard, SWT.NONE);
		btnNewButton.setText("Append new slot");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PhonemeManager.getPhonotactics().addSlot(list.getItemCount() + 1);;
			}
		});
		
		Label lblAvailablePhonemeClasses = new Label(shlPhonotacticsWizard, SWT.NONE);
		lblAvailablePhonemeClasses.setText("Available phoneme classes:");
		
		Label lblPhonemeClassesIn = new Label(shlPhonotacticsWizard, SWT.NONE);
		GridData gd_lblPhonemeClassesIn = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblPhonemeClassesIn.widthHint = 238;
		lblPhonemeClassesIn.setLayoutData(gd_lblPhonemeClassesIn);
		lblPhonemeClassesIn.setText("Phoneme classes in selected slot:");
		
		List list_2 = new List(shlPhonotacticsWizard, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		list_2.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		for(PhonemeClass clazz : PhonemeManager.getPhonemeClasses()) {
			list_2.add(clazz.name());
		}
		
		List list_1 = new List(shlPhonotacticsWizard, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		list_1.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Button btnAddSelectedPhoneme = new Button(shlPhonotacticsWizard, SWT.NONE);
		btnAddSelectedPhoneme.setText("Add selected phoneme class to selected slot");
		btnAddSelectedPhoneme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_2.getSelection().length != 0 && slot != -1) {
					boolean notContained = true;
					
					for(String str : list_1.getItems()) {
						if(str.equals(list_2.getSelection()[0])) notContained = false;
					}
					
					if(notContained) {
						PhonemeManager.getPhonotactics().addAllowedPhonemeClass(PhonemeManager.getPhonemeClass(list_2.getSelection()[0]), slot);
					}
				}
			}
		});
		
		Button btnRemoveSelectedPhoneme = new Button(shlPhonotacticsWizard, SWT.NONE);
		btnRemoveSelectedPhoneme.setText("Remove selected phoneme class from slot");
		btnRemoveSelectedPhoneme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_1.getSelection().length != 0 && slot != -1) {
					PhonemeManager.getPhonotactics().removeAllowedPhonemeClass(PhonemeManager.getPhonemeClass(list_1.getSelection()[0]), slot);
				}
			}
		});
		
		Label lblMaximumAmountOf = new Label(shlPhonotacticsWizard, SWT.NONE);
		lblMaximumAmountOf.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaximumAmountOf.setText("Maximum amount of syllables per randomly generated word:");
		
		text = new Text(shlPhonotacticsWizard, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton_1 = new Button(shlPhonotacticsWizard, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton_1.widthHint = 861;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("Save and close wizard");
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PhonemeManager.getPhonotactics().setMaxPhonemes(Integer.parseInt(text.getText()));
				shlPhonotacticsWizard.dispose();
			}
		});
		
		shlPhonotacticsWizard.open();
		shlPhonotacticsWizard.layout();
		while (!shlPhonotacticsWizard.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
			if(PhonemeManager.getPhonotactics().isChanged() || changed) {
				if(shlPhonotacticsWizard.isDisposed()) continue;
				int selected = list.getSelectionIndex();
				list_1.removeAll();
				list.removeAll();
				for(PhonemeClass clazz : PhonemeManager.getPhonotactics().getPhonemeClassesForSlot(slot)) {
					list_1.add(clazz.name());
				}
				
				for(int i : PhonemeManager.getPhonotactics().getSet().keySet()) {
					list.add(Integer.toString(i)); 
				}
				
				text.setText(Integer.toString(PhonemeManager.getPhonotactics().getMaxPhonemes()));
				
				list.setSelection(selected);
				
				changed = false;
			}
		}
	}

}
