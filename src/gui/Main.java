package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import configurationutil.type.Table;
import dictionary.Dictionary;
import dictionary.DictionaryEntry;
import dictionary.DictionaryManager;
import phonemes.Phoneme;
import phonemes.PhonemeClass;
import phonemes.PhonemeManager;
import phonemes.PhonemeWrapper;
import phonemes.Phonotactics;
import phonemes.SoundChange;
import wordutil.SoundShifter;
import wordutil.Word;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

public class Main {
	public static final String PROGRAM_VERSION = "0.9.0";
	
	private static Text text;
	private static Text text_1;
	private static Text text_2;
	private static Text text_3;
	private static Text text_4;
	private static Text text_5;
	private static Text text_6;
	
	static boolean needtoshow = false;
	private static Text text_7;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		PhonemeManager.setPhonotactics(new Phonotactics());
		
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(916, 711);
		shell.setText("Conlanging Helper");
		shell.setLayout(new GridLayout(1, true));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tabFolder.heightHint = 615;
		gd_tabFolder.widthHint = 872;
		tabFolder.setLayoutData(gd_tabFolder);
		
		TabItem tbtmWordGeneration = new TabItem(tabFolder, SWT.NONE);
		tbtmWordGeneration.setText("Word Generation");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmWordGeneration.setControl(composite);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblSelectDictionary = new Label(composite, SWT.NONE);
		lblSelectDictionary.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSelectDictionary.setText("Select dictionary:");
		
		Combo combo_2 = new Combo(composite, SWT.READ_ONLY);
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_2.addSelectionListener( new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if(combo_2.getSelectionIndex() != -1) {
						DictionaryManager.setSELECTED_DICTIONARY_WORDTAB(DictionaryManager.getDictionary(combo_2.getItem(combo_2.getSelectionIndex())));
						needtoshow = true;
					}
				}
		});
		
		Group wordSelectionGroup = new Group(composite, SWT.NONE);
		wordSelectionGroup.setText("Word selection");
		wordSelectionGroup.setLayout(new GridLayout(1, false));
		GridData gd_wordSelectionGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_wordSelectionGroup.widthHint = 843;
		wordSelectionGroup.setLayoutData(gd_wordSelectionGroup);
		
		Label lblEntriesArePresented = new Label(wordSelectionGroup, SWT.NONE);
		lblEntriesArePresented.setText("Entries are presented in your conlang.");
		
		List listWordSelection = new List(wordSelectionGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 95;
		data.widthHint = 464;
		listWordSelection.setLayoutData(data);
		listWordSelection.add("Create new..");
		listWordSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(listWordSelection.getSelection().length != 0 && DictionaryManager.getSELECTED_DICTIONARY_WORDTAB() != null) {
					if(DictionaryManager.getSELECTED_DICTIONARY_WORDTAB().getEntryForWord(listWordSelection.getSelection()[0]) != null) {
						PhonemeManager.resetSelectedPhonemes();
						text_5.setText(DictionaryManager.getSELECTED_DICTIONARY_WORDTAB().getEntryForWord(listWordSelection.getSelection()[0]).getTranslation());
						DictionaryEntry currentEntry = DictionaryManager.getSELECTED_DICTIONARY_WORDTAB().getEntryForWord(listWordSelection.getSelection()[0]);
						Word currWord = currentEntry.getWord();
						for(PhonemeWrapper wrapper : currWord.getPhonemes()) {
							PhonemeManager.addSelectedPhoneme(wrapper);
						}
					} else {
						//Selection is "Create new.."
						text_5.setText("");
						PhonemeManager.resetSelectedPhonemes();
					}
				}
			}
		});
		
		Label lblTranslationOfWord = new Label(composite, SWT.NONE);
		lblTranslationOfWord.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTranslationOfWord.setText("Translation of Word:");
		
		text_5 = new Text(composite, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnDeleteWord = new Button(composite, SWT.NONE);
		btnDeleteWord.setText("Delete Word");
		btnDeleteWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(listWordSelection.getSelection().length != 0) {
					if(!listWordSelection.getSelection()[0].equals("Create new..")) {
						Dictionary currdic = DictionaryManager.getSELECTED_DICTIONARY_WORDTAB();
						if(currdic != null) {
							currdic.removeEntry(currdic.getEntryForWord(listWordSelection.getSelection()[0]));
						}
					}
				}
			}
		});
		
		Button btnSaveWord = new Button(composite, SWT.NONE);
		btnSaveWord.setText("Create new Word / Save word.");
		btnSaveWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!text_5.getText().isEmpty() && DictionaryManager.getSELECTED_DICTIONARY_WORDTAB() != null) {
					if(listWordSelection.getSelection().length != 0) {
						if(listWordSelection.getSelection()[0].equals("Create new..")) {
							Dictionary dictionaryToAdd = DictionaryManager.getSELECTED_DICTIONARY_WORDTAB();
							Word word = new Word();
							for(PhonemeWrapper wrapper : PhonemeManager.getSelectedPhonemes()) {
								word.addPhoneme(wrapper);
							}
							DictionaryEntry entry = new DictionaryEntry(word, text_5.getText());
							dictionaryToAdd.addEntry(entry);
						} else {
							Dictionary dictionaryToAdd = DictionaryManager.getSELECTED_DICTIONARY_WORDTAB();
							Word word = new Word();
							for(PhonemeWrapper wrapper : PhonemeManager.getSelectedPhonemes()) {
								word.addPhoneme(wrapper);
							}
							DictionaryEntry entry = dictionaryToAdd.getEntryForWord(listWordSelection.getSelection()[0]);
							dictionaryToAdd.removeEntry(entry);
							
							DictionaryEntry newEntry = new DictionaryEntry(word, text_5.getText());
							dictionaryToAdd.addEntry(newEntry);
						}
					} else {
						new NotEnoughInfoError(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL).open();
					}
				} else {
					new NotEnoughInfoError(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL).open();
				}
			}
		});
		
		Label lblAvailablePhonemes = new Label(composite, SWT.NONE);
		lblAvailablePhonemes.setText("Available phonemes:");
		new Label(composite, SWT.NONE);
		
		List list_4 = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData data_4 = new GridData(GridData.FILL_BOTH);
		data_4.horizontalSpan = 2;
		data_4.heightHint = 52;
		data_4.widthHint = 464;		
		list_4.setLayoutData(data_4);
		for(Phoneme phoneme : PhonemeManager.getPhonemes()) {
			list_4.add(phoneme.getRepresentation());
		}
		
		Label lblPhonemesInWord = new Label(composite, SWT.NONE);
		lblPhonemesInWord.setText("Phonemes in word:");
		new Label(composite, SWT.NONE);
		
		List list_5 = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData data_5 = new GridData(GridData.FILL_BOTH);
		data_5.horizontalSpan = 2;
		data_5.heightHint = 60;
		data_5.widthHint = 464;		
		list_5.setLayoutData(data_5);
		list_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		Button btnRemoveSelectedPhoneme = new Button(composite, SWT.NONE);
		btnRemoveSelectedPhoneme.setText("Remove selected phoneme from word");
		btnRemoveSelectedPhoneme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_5.getSelection().length != 0) {
					int index = list_5.getSelectionIndex();
					list_5.remove(index);
					PhonemeManager.removeSelectedPhoneme(index);
				}
			}
		});
		
		Button btnAddPhonemeFrom = new Button(composite, SWT.NONE);
		btnAddPhonemeFrom.setText("Add phoneme from list of available phonemes to end of the word");
		btnAddPhonemeFrom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_4.getSelection().length != 0) {
					Phoneme phoneme = PhonemeManager.getPhoneme(list_4.getSelection()[0]);
					PhonemeWrapper wrapper = new PhonemeWrapper(phoneme);
					PhonemeManager.addSelectedPhoneme(wrapper);
					list_5.add(phoneme.getRepresentation());
				}
			}
		});
		
		Button btnMarkPhonemeAs = new Button(composite, SWT.CHECK);
		btnMarkPhonemeAs.setText("Mark phoneme as stressed");
		btnMarkPhonemeAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_5.getSelection().length != 0) {
					PhonemeManager.getSelectedPhoneme(list_5.getSelectionIndex()).setStress(btnMarkPhonemeAs.getSelection());
				}
			}
		});
		
		Button btnGenerateRandomWord = new Button(composite, SWT.NONE);
		btnGenerateRandomWord.setText("Generate random word from phonotactics (you still need to set a translation)");
		btnGenerateRandomWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Phonotactics p = PhonemeManager.getPhonotactics();
				
				for(int i = 0; i <= new Random().nextInt(p.getMaxPhonemes()); i++) {
					loop: for(ArrayList<PhonemeClass> ph : p.getSet().values()) {
						int rIndex = new Random().nextInt(ph.size());
												
						PhonemeClass plcla = ph.get(rIndex);
						
						if(plcla.name().equals("<may stay empty>")) continue loop;
						
						PhonemeManager.addSelectedPhoneme(new PhonemeWrapper(plcla.getRandom()));
					}
				}
			}
		});
		
		Label lblWordRomanization = new Label(composite, SWT.NONE);
		lblWordRomanization.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWordRomanization.setText("Word romanization:");
		
		text_6 = new Text(composite, SWT.BORDER);
		text_6.setEditable(false);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		TabItem tbtmSoundShifting = new TabItem(tabFolder, SWT.NONE);
		tbtmSoundShifting.setText("Sound Shifting");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		composite_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmSoundShifting.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		
		Label lblSelectInputDictionary = new Label(composite_1, SWT.NONE);
		GridData gd_lblSelectInputDictionary = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblSelectInputDictionary.heightHint = 29;
		gd_lblSelectInputDictionary.widthHint = 169;
		lblSelectInputDictionary.setLayoutData(gd_lblSelectInputDictionary);
		lblSelectInputDictionary.setText("Select dictionary to shift...");
		
		Combo combo = new Combo(composite_1, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DictionaryManager.setSELECTED_DICTIONARY_SOUNDSHIFTTAB(DictionaryManager.getDictionary(combo.getItem(combo.getSelectionIndex())));
				needtoshow = true;
			}
		});
		
		Group grpSoundChanges = new Group(composite_1, SWT.NONE);
		grpSoundChanges.setText("Sound Changes");
		grpSoundChanges.setLayout(new GridLayout(4, false));
		GridData gd_grpSoundChanges = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_grpSoundChanges.heightHint = 311;
		gd_grpSoundChanges.widthHint = 826;
		grpSoundChanges.setLayoutData(gd_grpSoundChanges);
		
		Label lblSelectSoundChange = new Label(grpSoundChanges, SWT.NONE);
		lblSelectSoundChange.setText("Available sound changes");
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		
		List list = new List(grpSoundChanges, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list = new GridData(GridData.FILL_BOTH);
		gd_list.heightHint = 151;
		gd_list.widthHint = 464;
		list.setLayoutData(gd_list);
		
		Button newSoundChangeButton = new Button(grpSoundChanges, SWT.NONE);
		newSoundChangeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SoundShiftWizard wizard = new SoundShiftWizard();
				wizard.open(null);
			}
		});
		newSoundChangeButton.setText("New..");
		
		Button editSoundChangeButton = new Button(grpSoundChanges, SWT.NONE);
		editSoundChangeButton.setText("Edit");
		editSoundChangeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SoundShiftWizard wizard = new SoundShiftWizard();
				wizard.open(list.getSelection()[0]);
			}
		});
		
		Button removeSoundChangeButton = new Button(grpSoundChanges, SWT.NONE);
		removeSoundChangeButton.setText("Remove");
		removeSoundChangeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list.getSelection().length != 0) {
					SoundChange sc = PhonemeManager.getSoundChange(list.getSelection()[0]);
					PhonemeManager.removeSoundChange(sc);
				}
			}
		});
		
		Label lblSelectedSoundChanges = new Label(grpSoundChanges, SWT.NONE);
		lblSelectedSoundChanges.setText("Selected sound changes (applied in order from top to bottom)");
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		
		List list_2 = new List(grpSoundChanges, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list_2 = new GridData(GridData.FILL_BOTH);
		gd_list_2.heightHint = 127;
		gd_list_2.widthHint = 808;
		list_2.setLayoutData(gd_list_2);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		
		Button btnAddSoundShift = new Button(grpSoundChanges, SWT.NONE);
		btnAddSoundShift.setText("Add Sound shift to selection");
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		btnAddSoundShift.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list.getSelection().length != 0) {
					if(!list.getSelection()[0].equals("Create new..")) {
						PhonemeManager.addSelectedSoundChange(PhonemeManager.getSoundChange(list.getSelection()[0]));
					}
				}
			}
		});
		
		Button btnRemoveSoundShift = new Button(grpSoundChanges, SWT.NONE);
		btnRemoveSoundShift.setText("Remove sound shift from selection");
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		
		Label lblNoteSoundChanges = new Label(grpSoundChanges, SWT.WRAP);
		lblNoteSoundChanges.setText("Sound changes are applied top to bottom. Sound changes may be applied multiple times.");
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		new Label(grpSoundChanges, SWT.NONE);
		btnRemoveSoundShift.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_2.getSelection().length != 0) {
					SoundChange ssc = PhonemeManager.getSelectedSoundChange(list_2.getSelection()[0]);
					PhonemeManager.removeSelectedSoundChange(ssc);
				}
			}
		});
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		Label lblOutputDictionaryName = new Label(composite_1, SWT.NONE);
		lblOutputDictionaryName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOutputDictionaryName.setText("Output dictionary name: ");
		
		text_1 = new Text(composite_1, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton_1 = new Button(composite_1, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton_1.widthHint = 852;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("Submit (creates a new dictionary with the sound-shifted words)");
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_2.getItemCount() != 0) {
					if(!text_1.getText().isEmpty() && DictionaryManager.getDictionary(text_1.getText()) == null && combo.getSelectionIndex() != -1) {
						Dictionary shiftedDictionary = new Dictionary(text_1.getText());
						
						//coppeh
						for(DictionaryEntry de : DictionaryManager.getSELECTED_DICTIONARY_SOUNDSHIFTTAB().getEntries()) {
							shiftedDictionary.addEntry(de);
						}
						
						for(SoundChange change : PhonemeManager.getSelectedSoundChanges()) {
							ArrayList<DictionaryEntry> newEntries = new ArrayList<DictionaryEntry>();
							for(DictionaryEntry dictionaryEntry : DictionaryManager.getSELECTED_DICTIONARY_SOUNDSHIFTTAB().getEntries()) {
								Word newWord = SoundShifter.shiftSound(dictionaryEntry.getWord(), change);
								DictionaryEntry newDictonaryEntry = new DictionaryEntry(newWord, dictionaryEntry.getTranslation());
								newEntries.add(newDictonaryEntry);
							}
							shiftedDictionary.clearEntries();
							shiftedDictionary.addEntries(newEntries.toArray(new DictionaryEntry[newEntries.size()]));
						}
						
						DictionaryManager.addDictionary(shiftedDictionary);
						
					} else {
						new NotEnoughInfoError(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
					}
				} else {
					new NotEnoughInfoError(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
				}
			}
		});
		
		TabItem tbtmPhonemes = new TabItem(tabFolder, SWT.NONE);
		tbtmPhonemes.setText("Phonemes");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmPhonemes.setControl(composite_2);
		composite_2.setLayout(new GridLayout(1, false));
		
		Label lblListOfPhonemes = new Label(composite_2, SWT.NONE);
		lblListOfPhonemes.setText("List of phonemes (select to modify)");
				
		List phonemeList = new List(composite_2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_phonemeList = new GridData(GridData.FILL_BOTH);
		gd_phonemeList.heightHint = 137;
		gd_phonemeList.widthHint = 735;
		phonemeList.setLayoutData(gd_phonemeList);
		phonemeList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					text_2.setText(phonemeList.getSelection()[0]);
				} catch (Exception ex) {
					//do nothing
				}
			}
		});
		phonemeList.add("Create new..");
		
		Group grpModiy = new Group(composite_2, SWT.NONE);
		GridData gd_grpModiy = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpModiy.widthHint = 735;
		grpModiy.setLayoutData(gd_grpModiy);
		grpModiy.setText("Modify");
		grpModiy.setLayout(new GridLayout(2, false));
		
		Label lblStringRepresentationOf = new Label(grpModiy, SWT.NONE);
		lblStringRepresentationOf.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStringRepresentationOf.setText("String representation of the phoneme: ");
		
		text_2 = new Text(grpModiy, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnRemovePhoneme = new Button(grpModiy, SWT.NONE);
		btnRemovePhoneme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(phonemeList.getSelection().length == 0) {
					
				} else {
					if(!phonemeList.getSelection()[0].equals("Create new..")) {
						String oldPhoneme = phonemeList.getSelection()[0];
						PhonemeManager.removePhoneme(PhonemeManager.getPhoneme(oldPhoneme));
					}
				}
			}
		});
		btnRemovePhoneme.setText("Remove Phoneme");
		
		Button btnSavePhoneme = new Button(grpModiy, SWT.NONE);
		btnSavePhoneme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(phonemeList.getSelection().length == 0) {
					//do nothing
				} else {
					if(phonemeList.getSelection()[0].equals("Create new..")) {
						if(!text_2.getText().equals("Create new..")) {
							if(PhonemeManager.getPhoneme(phonemeList.getSelection()[0]) == null) {
								Phoneme phoneme = new Phoneme(text_2.getText());
								PhonemeManager.addPhoneme(phoneme);
							}
						}
					} else {
						if(!text_2.getText().equals("Create new..")) {
							if(PhonemeManager.getPhoneme(phonemeList.getSelection()[0]) == null) {
								String oldPhoneme = phonemeList.getSelection()[0];
								PhonemeManager.removePhoneme(PhonemeManager.getPhoneme(oldPhoneme));
								PhonemeManager.addPhoneme(new Phoneme(text_2.getText()));
							}
						}
					}
				}
			}
		});
		btnSavePhoneme.setText("Save / create new phoneme");
		
		Group grpPhonemeClasses = new Group(composite_2, SWT.NONE);
		grpPhonemeClasses.setLayout(new GridLayout(2, false));
		GridData gd_grpPhonemeClasses = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpPhonemeClasses.widthHint = 737;
		grpPhonemeClasses.setLayoutData(gd_grpPhonemeClasses);
		grpPhonemeClasses.setText("Phoneme classes");
		
		text_4 = new Text(grpPhonemeClasses, SWT.BORDER);
		text_4.setEditable(false);
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		text_4.setText("Phonemes in selected phoneme class: <none selected>");
		
		List list_1 = new List(grpPhonemeClasses, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_list_1 = new GridData(GridData.FILL_BOTH);
		gd_list_1.heightHint = 68;
		gd_list_1.horizontalSpan = 2;
		gd_list_1.widthHint = 616;
		list_1.setLayoutData(gd_list_1);
		list_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					text_3.setText(list_1.getSelection()[0]);
					if(list_1.getSelection()[0].equals("Create new..")) {
						text_4.setText("Phonemes in selected phoneme class: <none selected>");
					} else {
						String selpho = "";
						for(Phoneme ph : PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).getPhonemes()) {
							selpho = selpho + ph.getRepresentation() + " ";
						}
						text_4.setText("Phonemes in selected phoneme class: " + selpho);
					}
				} catch (Exception ex) {
					//do nothing
				}
			}
		});
		list_1.add("Create new..");
		
		
		
		Button btnAddSelectedPhoneme = new Button(grpPhonemeClasses, SWT.NONE);
		GridData gd_btnAddSelectedPhoneme = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAddSelectedPhoneme.widthHint = 288;
		btnAddSelectedPhoneme.setLayoutData(gd_btnAddSelectedPhoneme);
		btnAddSelectedPhoneme.setText("Add selected phoneme to phoneme class");
		btnAddSelectedPhoneme.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_1.getSelection().length == 0 || phonemeList.getSelection().length == 0) {
					
				} else {
					if(!list_1.getSelection()[0].equals("Create new..")) {
						if(!phonemeList.getSelection()[0].equals("Create new..")) {
							if(PhonemeManager.getPhoneme(phonemeList.getSelection()[0]).equals(PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).getPhoneme(phonemeList.getSelection()[0]))){
								//do nothing
							} else {
								PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).addPhoneme(PhonemeManager.getPhoneme(phonemeList.getSelection()[0]));
								String strpho = "";
								for(Phoneme p : PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).getPhonemes()) {
									strpho = strpho + p.getRepresentation() + " ";
								}
								text_4.setText("Phonemes in selected phoneme class: " + strpho);
								
							}
						} else {
							//do nothing
						}
					} else {
						//do nothing
					}
				}
			}
		});
		

		
		Button btnNewButton = new Button(grpPhonemeClasses, SWT.NONE);
		btnNewButton.setText("Remove selected phoneme from phoneme class");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_1.getSelection().length == 0 || phonemeList.getSelection().length == 0) {
					
				} else {
					if(!list_1.getSelection()[0].equals("Create new..")) {
						if(!phonemeList.getSelection()[0].equals("Create new..")) {
							PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).removePhoneme(PhonemeManager.getPhoneme(phonemeList.getSelection()[0]));
							String strpho = "";
							for(Phoneme p : PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).getPhonemes()) {
								strpho = strpho + p.getRepresentation() + " ";
							}
							text_4.setText("Phonemes in selected phoneme class: " + strpho);

						} else {
							//do nothing
						}
					} else {
						//do nothing
					}
				}
			}
		});
		
		Label lblPhonemeClassName = new Label(grpPhonemeClasses, SWT.NONE);
		GridData gd_lblPhonemeClassName = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblPhonemeClassName.widthHint = 153;
		lblPhonemeClassName.setLayoutData(gd_lblPhonemeClassName);
		lblPhonemeClassName.setText("Phoneme class name:");
		
		text_3 = new Text(grpPhonemeClasses, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnRemovePhonemeClass = new Button(grpPhonemeClasses, SWT.NONE);
		GridData gd_btnRemovePhonemeClass = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnRemovePhonemeClass.widthHint = 333;
		btnRemovePhonemeClass.setLayoutData(gd_btnRemovePhonemeClass);
		btnRemovePhonemeClass.setText("Remove Phoneme class");
		btnRemovePhonemeClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_1.getSelection().length == 0) {
					
				} else {
					if(!list_1.getSelection()[0].equals("Create new..")) {
						String oldPhonemeClass = list_1.getSelection()[0];
						PhonemeManager.removePhonemeClass(PhonemeManager.getPhonemeClass(oldPhonemeClass));
					}
				}
			}
		});
		
		Button btnSaveCreate = new Button(grpPhonemeClasses, SWT.NONE);
		GridData gd_btnSaveCreate = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSaveCreate.widthHint = 329;
		btnSaveCreate.setLayoutData(gd_btnSaveCreate);
		btnSaveCreate.setText("Save / Create new phoneme class");
		
		Button btnOpenPhonotacticsWizard = new Button(grpPhonemeClasses, SWT.NONE);
		GridData gd_btnOpenPhonotacticsWizard = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnOpenPhonotacticsWizard.widthHint = 715;
		btnOpenPhonotacticsWizard.setLayoutData(gd_btnOpenPhonotacticsWizard);
		btnOpenPhonotacticsWizard.setText("Open phonotactics wizard (required for random word generation)");
		btnOpenPhonotacticsWizard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new PhonotactisWizard().open();
			}
		});
		
		btnSaveCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(list_1.getSelection().length == 0) {
					//do nothing
				} else {
					if(list_1.getSelection()[0].equals("Create new..")) {
						if(!text_3.getText().equals("Create new..")) {
							PhonemeClass phonemeClass = new PhonemeClass(text_3.getText());
							PhonemeManager.addPhonemeClass(phonemeClass);
						}
					} else {
						if(!text_3.getText().equals("Create new..")) {
							Phoneme[] phs = PhonemeManager.getPhonemeClass(list_1.getSelection()[0]).getPhonemes();
							PhonemeManager.removePhonemeClass(PhonemeManager.getPhonemeClass(list_1.getSelection()[0]));
							PhonemeManager.addPhonemeClass(new PhonemeClass(text_3.getText(), phs));
						}
					}
				}
			}
		});
		
		TabItem tbtmDictionary = new TabItem(tabFolder, SWT.NONE);
		tbtmDictionary.setText("Dictionary");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmDictionary.setControl(composite_3);
		composite_3.setLayout(new GridLayout(2, false));
		
		Label lblDictionarySelection = new Label(composite_3, SWT.NONE);
		lblDictionarySelection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDictionarySelection.setText("Dictionary Selection: ");
		
		Combo combo_1 = new Combo(composite_3, SWT.READ_ONLY);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_1.add("Create new...");
		combo_1.select(0);
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(combo_1.getSelectionIndex() != -1) {
					if(!combo_1.getItem(combo_1.getSelectionIndex()).equals("Create new...")) {
						DictionaryManager.setSELECTED_DICTIONARY_DICTIONARYTAB(DictionaryManager.getDictionary(combo_1.getItem(combo_1.getSelectionIndex())));
						needtoshow = true;
					}
				}
			}
		});
		
		Group grpModify = new Group(composite_3, SWT.NONE);
		grpModify.setText("Modify");
		grpModify.setLayout(new GridLayout(1, false));
		GridData gd_grpModify = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_grpModify.widthHint = 740;
		grpModify.setLayoutData(gd_grpModify);
		
		Label lblDictionaryName = new Label(grpModify, SWT.NONE);
		lblDictionaryName.setText("Dictionary name");
		
		text = new Text(grpModify, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 308;
		text.setLayoutData(gd_text);
		
		Button btnSaveNamecreates = new Button(grpModify, SWT.NONE);
		btnSaveNamecreates.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(combo_1.getSelectionIndex() != -1) {
					if(combo_1.getItem(combo_1.getSelectionIndex()).equals("Create new...")) {
						if(!text.getText().equals("") && DictionaryManager.getDictionary(text.getText()) == null) {
							Dictionary newDictionary = new Dictionary(text.getText());
							DictionaryManager.addDictionary(newDictionary);
						} else {
							NotEnoughInfoError err = new NotEnoughInfoError(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
							err.open();
						}
					} else {
						if(!text.getText().equals("") && DictionaryManager.getDictionary(text.getText()) == null) {
							Dictionary newDictionary = new Dictionary(text.getText());
							Dictionary oldDictionary = DictionaryManager.getDictionary(combo_1.getItem(combo_1.getSelectionIndex()));
							DictionaryEntry[] entries = oldDictionary.getEntries();
							for(DictionaryEntry entry : entries) {
								newDictionary.addEntry(entry);
							}
							DictionaryManager.removeDictionary(oldDictionary);
							DictionaryManager.addDictionary(newDictionary);
						} else {
							NotEnoughInfoError err = new NotEnoughInfoError(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
							err.open();
						}
					}
				}
			}
		});
		GridData gd_btnSaveNamecreates = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSaveNamecreates.widthHint = 729;
		btnSaveNamecreates.setLayoutData(gd_btnSaveNamecreates);
		btnSaveNamecreates.setText("Save name (creates a new dictionary if create new is selected)");
		
		Label lblClickingOnA = new Label(composite_3, SWT.NONE);
		lblClickingOnA.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblClickingOnA.setText("List of words: (to edit words, go to the Word Generation tab)");
		
		List list_3 = new List(composite_3, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_list_3 = new GridData(GridData.FILL_BOTH);
		gd_list_3.horizontalSpan = 2;
		gd_list_3.heightHint = 136;
		gd_list_3.widthHint = 845;
		list_3.setLayoutData(gd_list_3);
		
		TabItem tbtmSettings = new TabItem(tabFolder, SWT.NONE);
		tbtmSettings.setText("File");
		
		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmSettings.setControl(composite_4);
		composite_4.setLayout(new GridLayout(2, false));
		
		Label lblLanguageFile = new Label(composite_4, SWT.NONE);
		lblLanguageFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLanguageFile.setText("Language File: ");
		
		text_7 = new Text(composite_4, SWT.BORDER);
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSaveLanguageTo = new Button(composite_4, SWT.NONE);
		btnSaveLanguageTo.setText("Save language to file ");
		btnSaveLanguageTo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = new File(text_7.getText());
				Table table = new Table("conlang");
				
				Table dictionaries = new Table("dictionaries");
				
				int dictionaryWrapper = 0;
				for(Dictionary dictionary : DictionaryManager.getDictionaries()) {
					Table dict = new Table("dictionary_" + dictionaryWrapper);
					Table name = new Table("name");
					name.setValue(dictionary.name());
					dict.addSubTable(name);
					int entryWrapper = 0;
					for(DictionaryEntry entry : dictionary.getEntries()) {
						Table dictEntry = new Table("entry_" + entryWrapper);
						Table translation = new Table("translation");
						translation.setValue(entry.getTranslation());
						Table word = new Table("word");
						int indexWrapper = 0;
						for(PhonemeWrapper wrap : entry.getWord().getPhonemes()) {
							Table phoneme = new Table("phoneme_" + indexWrapper);
							Table rep = new Table("representation");
							Table stress = new Table("stress");
							rep.setValue(wrap.getWrappedPhoneme().getRepresentation());
							stress.setValue(Boolean.toString(wrap.getStress()));
							
							phoneme.addSubTable(rep);
							phoneme.addSubTable(stress);
							
							word.addSubTable(phoneme);
							
							indexWrapper++;
						}
						Table ephc = new Table("phonemeCount");
						ephc.setValue(Integer.toString(indexWrapper));
												
						word.addSubTable(ephc);
						
						dictEntry.addSubTable(translation);
						dictEntry.addSubTable(word);
						
						dict.addSubTable(dictEntry);
						
						entryWrapper++;
					}
					
					Table dictEntryCount = new Table("entriesCount");
					dictEntryCount.setValue(Integer.toString(entryWrapper));
					dict.addSubTable(dictEntryCount);
					dictionaries.addSubTable(dict);
					
					dictionaryWrapper++;
				}
				Table dictCount = new Table("dictionaryCount");
				dictCount.setValue(Integer.toString(dictionaryWrapper));
				
				dictionaries.addSubTable(dictCount);
				
				Table phonemes = new Table("phonemes");
				
				int indexPhoneme = 0;
				for(Phoneme phoneme : PhonemeManager.getPhonemes()) {
					Table ph = new Table("phoneme_" + indexPhoneme);
					ph.setValue(phoneme.getRepresentation());
					phonemes.addSubTable(ph);
					indexPhoneme++;
				}
				
				Table phonemeCount = new Table("phonemeCount");
				phonemeCount.setValue(Integer.toString(indexPhoneme));
				
				phonemes.addSubTable(phonemeCount);
				
				Table phonemeClasses = new Table("phonemeClasses");
				
				int indexPhonemeClass = 0;
				classLoop: for(PhonemeClass phonemeClass : PhonemeManager.getPhonemeClasses()) {
					if(phonemeClass.name().equals("<may stay empty>")) continue classLoop;
					Table phc = new Table("phonemeClass_" + indexPhonemeClass);
					Table name = new Table("name");
					name.setValue(phonemeClass.name());
					
					
					Table phos = new Table("phonemes");
					int cpind = 0;
					for(Phoneme contph : phonemeClass.getPhonemes()) {
						Table cp = new Table("phoneme_" + cpind);
						cp.setValue(contph.getRepresentation());
						phos.addSubTable(cp);
						cpind++;
					}
					
					Table phonemeInClassCount = new Table("phonemesCount");
					phonemeInClassCount.setValue(Integer.toString(cpind));
					
					phos.addSubTable(phonemeInClassCount);
					
					phc.addSubTable(phos);
					phc.addSubTable(name);
					
					phonemeClasses.addSubTable(phc);
					
					indexPhonemeClass++;
				}
				
				Table phonemeClassCount = new Table("phonemeClassCount");
				phonemeClassCount.setValue(Integer.toString(indexPhonemeClass));
				
				phonemeClasses.addSubTable(phonemeClassCount);
				
				Table soundChanges = new Table("soundChanges");
				int sci = 0;
				for(SoundChange change : PhonemeManager.getSoundChanges()) {
					Table soundChange = new Table("soundchange_" + sci);
					Table name = new Table("name");
					name.setValue(change.name());
					Table from = new Table("shiftfrom");
					from.setValue(change.getShiftedFrom().name());
					Table to = new Table("shiftto");
					to.setValue(change.getShiftTo().getRepresentation());
					Table before = new Table("shiftbefore");
					before.setValue(change.getShiftedBefore() == null ? "null" : change.getShiftedBefore().name());
					Table after = new Table("shiftafter");
					after.setValue(change.getShiftedAfter() == null ? "null" : change.getShiftedAfter().name());
					Table shiftStressed = new Table("shiftStressed");
					shiftStressed.setValue(Boolean.toString(change.isAffectStressed()));
					Table shiftUnstressed = new Table("shiftUnstressed");
					shiftUnstressed.setValue(Boolean.toString(change.isAffectUnstressed()));
					Table beforeOffset = new Table("beforeOffset");
					beforeOffset.setValue(Integer.toString(change.getBeforeOffset()));					
					Table afterOffset = new Table("afterOffset");
					afterOffset.setValue(Integer.toString(change.getAfterOffset()));
					
					soundChange.addSubTable(name);
					soundChange.addSubTable(from);
					soundChange.addSubTable(to);
					soundChange.addSubTable(before);
					soundChange.addSubTable(after);
					soundChange.addSubTable(shiftStressed);
					soundChange.addSubTable(shiftUnstressed);
					soundChange.addSubTable(beforeOffset);
					soundChange.addSubTable(afterOffset);
					
					soundChanges.addSubTable(soundChange);
					
					sci++;
				}
				
				Table soundChangesCount = new Table("soundChangesCount");
				soundChangesCount.setValue(Integer.toString(sci));
				
				Table phonotactics = new Table("phonotactics");
				int phonotacticsIndex = 0;
				
				Table maxPhonemes = new Table("maxSyllables");
				
				maxPhonemes.setValue(Integer.toString(PhonemeManager.getPhonotactics().getMaxPhonemes()));
				
				phonotactics.addSubTable(maxPhonemes);
				
				for(ArrayList<PhonemeClass> array : PhonemeManager.getPhonotactics().getSet().values()) {
					Table slot = new Table("slot_" + phonotacticsIndex);
					Table allowedClasses = new Table("allowedClasses");
					
					int allowedIndex = 0;
					for(PhonemeClass clazz : array) {
						Table clz = new Table("allowedClass_" + allowedIndex);
						clz.setValue(clazz.name());
						allowedClasses.addSubTable(clz);
						allowedIndex++;
					}
					
					Table classCount = new Table("allowedClassesCount");
					classCount.setValue(Integer.toString(allowedIndex));
					
					slot.addSubTable(classCount);
					slot.addSubTable(allowedClasses);
					phonotactics.addSubTable(slot);
					phonotacticsIndex++;
				}
				
				Table slotCount = new Table("slotCount");
				slotCount.setValue(Integer.toString(phonotacticsIndex));
				
				phonotactics.addSubTable(slotCount);
				
				table.addSubTable(phonotactics);
				
				soundChanges.addSubTable(soundChangesCount);
				
				table.addSubTable(phonemes);
				table.addSubTable(phonemeClasses);
				table.addSubTable(soundChanges);
				table.addSubTable(dictionaries);
				
				//System.out.println("Serialized Language table: \n" + table.serialize());
				FileWriter writer = null;
				try {
					writer = new FileWriter(file);
					
					writer.write(table.serialize());
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		Button btnLoadLanguageFrom = new Button(composite_4, SWT.NONE);
		btnLoadLanguageFrom.setText("Load language from file");
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		btnLoadLanguageFrom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PhonemeManager.clearPhonemeClasses();
				PhonemeManager.clearPhonemes();
				PhonemeManager.clearSelectedPhonemes();
				PhonemeManager.clearSelectedSoundChanges();
				PhonemeManager.clearSoundChanges();
				PhonemeManager.setPhonotactics(new Phonotactics());
				DictionaryManager.clearAll();
				//that oughtta do it
				
				File file = new File(text_7.getText());
				String read = "";
				try{
					java.util.List<String> lines = Files.readAllLines(file.toPath());
					
					for(String line : lines) {
						read = read + line + "\n";
					}
				} catch(IOException e1) {
					e1.printStackTrace();
				}
				if(read.isEmpty()) return;
				
				//System.out.println("read file: \n" + read);
				
				Table table = Table.fromString(read);
				
				System.out.println(table);
				
				PhonemeManager.addPhonemeClass(new PhonemeClass("<may stay empty>"));
				
				Table phonemes = table.getSubTable("phonemes");
				Table phonemeClasses = table.getSubTable("phonemeClasses");
				Table soundChanges = table.getSubTable("soundChanges");
				Table phonotactics = table.getSubTable("phonotactics");
				Table dictionaries = table.getSubTable("dictionaries");
				//System.out.println(phonemeClasses);
				int phonemeCount = Integer.parseInt(phonemes.getSubTable("phonemeCount").getValue());
				int phonemeClassesCount = Integer.parseInt(phonemeClasses.getSubTable("phonemeClassCount").getValue());
				int soundChangesCount = Integer.parseInt(soundChanges.getSubTable("soundChangesCount").getValue());
				int slotCount = Integer.parseInt(phonotactics.getSubTable("slotCount").getValue());
				int dictionaryCount = Integer.parseInt(dictionaries.getSubTable("dictionaryCount").getValue());
				
				
				for(int index = 0; index < phonemeCount; index++) {
					Table phoneme = phonemes.getSubTable("phoneme_" + index);
					PhonemeManager.addPhoneme(new Phoneme(phoneme.getValue()));
				}
				
				for(int index = 0; index < phonemeClassesCount; index++) {
					Table phonemeClass = phonemeClasses.getSubTable("phonemeClass_" + index);
					String name = phonemeClass.getSubTable("name").getValue();
					
					
					PhonemeClass phcl = new PhonemeClass(name);
					
					
					Table phos = phonemeClass.getSubTable("phonemes");
					
					
					for(int phIndex = 0; phIndex < Integer.parseInt(phos.getSubTable("phonemesCount").getValue()); phIndex++) {
						phcl.addPhoneme(PhonemeManager.getPhoneme(phos.getSubTable("phoneme_" + phIndex).getValue()));
					}
					
					if(!phcl.name().equals("<may stay empty>")) PhonemeManager.addPhonemeClass(phcl);
				}
				
				for(int index = 0; index < soundChangesCount; index++) {
					Table soundchange = soundChanges.getSubTable("soundchange_" + index);
					
					String name = soundchange.getSubTable("name").getValue();
					PhonemeClass shiftFrom = PhonemeManager.getPhonemeClass(soundchange.getSubTable("shiftfrom").getValue());
					PhonemeClass shiftBefore = PhonemeManager.getPhonemeClass(soundchange.getSubTable("shiftbefore").getValue());
					PhonemeClass shiftAfter = PhonemeManager.getPhonemeClass(soundchange.getSubTable("shiftafter").getValue());
					
					Phoneme shiftTo = PhonemeManager.getPhoneme(soundchange.getSubTable("shiftto").getValue());
					
					boolean shiftStressed = Boolean.parseBoolean(soundchange.getSubTable("shiftStressed").getValue());
					boolean shiftUnstressed = Boolean.parseBoolean(soundchange.getSubTable("shiftUnstressed").getValue());
					
					int beforeOffset = Integer.parseInt(soundchange.getSubTable("beforeOffset").getValue());
					int afterOffset = Integer.parseInt(soundchange.getSubTable("afterOffset").getValue());
					
					PhonemeManager.addSoundChange(new SoundChange(name, shiftBefore, shiftAfter, shiftFrom, shiftTo, beforeOffset, afterOffset, shiftUnstressed, shiftStressed));
				}
				
				PhonemeManager.getPhonotactics().setMaxPhonemes(Integer.parseInt(phonotactics.getSubTable("maxSyllables").getValue()));
				
				for(int index = 0; index < slotCount; index++) {
					Table slot = phonotactics.getSubTable("slot_" + index);
					
					PhonemeManager.getPhonotactics().addSlot(index);;
					
					Table allowedClasses = slot.getSubTable("allowedClasses");
					
					for(int alIndex = 0; alIndex < Integer.parseInt(slot.getSubTable("allowedClassesCount").getValue()); alIndex++) {
						PhonemeManager.getPhonotactics().addAllowedPhonemeClass(PhonemeManager.getPhonemeClass(allowedClasses.getSubTable("allowedClass_" + alIndex).getValue()), index);
					}
				}
				
				for(int index = 0; index < dictionaryCount; index++) {
					Table dictionary = dictionaries.getSubTable("dictionary_" + index);
					
					Dictionary dict = new Dictionary(dictionary.getSubTable("name").getValue());
					DictionaryManager.addDictionary(dict);
					
					for(int dIndex = 0; dIndex < Integer.parseInt(dictionary.getSubTable("entriesCount").getValue()); dIndex++) {
						Table entry = dictionary.getSubTable("entry_" + dIndex);
						
						System.out.println(entry);
						
						Word word = new Word();
						
						Table wordTable = entry.getSubTable("word");
						
						for(int wIndex = 0; wIndex < Integer.parseInt(wordTable.getSubTable("phonemeCount").getValue()); wIndex++) {
							Table phon = wordTable.getSubTable("phoneme_" + wIndex);
							PhonemeWrapper wrapper = new PhonemeWrapper(PhonemeManager.getPhoneme(phon.getSubTable("representation").getValue()));
							
							wrapper.setStress(Boolean.parseBoolean(phon.getSubTable("stress").getValue()));
							
							word.addPhoneme(wrapper);
						}
						
						DictionaryEntry dEntry = new DictionaryEntry(word, entry.getSubTable("translation").getValue());
						dict.addEntry(dEntry);
					}
				}
			}
		});
		
		Button btnCheckForUpdates = new Button(composite_4, SWT.NONE);
		GridData gd_btnCheckForUpdates = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCheckForUpdates.widthHint = 693;
		btnCheckForUpdates.setLayoutData(gd_btnCheckForUpdates);
		btnCheckForUpdates.setText("Check for updates (this may hang)");
		btnCheckForUpdates.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
		        URL url = null;
		        
		        System.out.println("Checking for updates...");
		        
		        try {
					url = new URL("https://raw.githubusercontent.com/MrObsidy/technical-data-inf/master/clangutil/update.tbl");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
		        
		        System.out.println("Connection established, downloading file...");
		        
		        BufferedReader read = null;
				try {
					read = new BufferedReader(new InputStreamReader(url.openStream()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				System.out.println("Done downloading, reading...");
				
		        String concatenate = "";
		        String readLine;
		        try {
					while ((readLine = read.readLine()) != null){ 
						concatenate = concatenate + readLine + "\n";
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        try {
					read.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        System.out.println("Done reading, parsing...");
		        
		        Table table = null;
		        
		        try{
		        	table = Table.fromString(concatenate);
		        } catch (Exception e2) {
		        	e2.printStackTrace();
		        }
		        
		        System.out.println("Parsing complete. Checking if new version is available...");
		        
		        if(table != null) {		        	
		        	System.out.println("Version online: " + table.getSubTable("version").getValue());
		        	
		        	String version[] = table.getSubTable("version").getValue().split(Pattern.quote("."));
		        	int major = Integer.parseInt(version[0]);
		        	int minor = Integer.parseInt(version[1]);
		        	int patch = Integer.parseInt(version[2]);
		        	
		        	String[] thisVersion = Main.PROGRAM_VERSION.split(Pattern.quote("."));
		        	int thisMajor = Integer.parseInt(thisVersion[0]);
		        	int thisMinor = Integer.parseInt(thisVersion[1]);
		        	int thisPatch = Integer.parseInt(thisVersion[2]);
		        	
		        	if(major == thisMajor) {
		        		if(minor == thisMinor) {
		        			if(patch == thisPatch) {
		        				new NoUpdate().open();
		        			} else if (patch > thisPatch) {
		        				new UpdateAvailable(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
		        			} else {
		        				new NewerThanCurr(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
		        			}
		        		} else if(minor > thisMinor) {
		        			new UpdateAvailable(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
		        		} else {
		        			new NewerThanCurr(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
		        		}
		        	} else if(major > thisMajor) {
	        			new UpdateAvailable(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
	        		} else {
	        			new NewerThanCurr(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM).open();
	        		}
		        }
		        
		        System.out.println("Done.");
		        
			}
		});
		
		shell.open();
		shell.layout();
		
		boolean changedThisIteration = false;
		
		PhonemeManager.addPhonemeClass(new PhonemeClass("<may stay empty>"));
		
		while (!shell.isDisposed()) {			
			if(DictionaryManager.getChanged()) {
				combo.removeAll();
				combo_1.removeAll();
				combo_2.removeAll();
				
				combo_1.add("Create new...");
				for(Dictionary dict : DictionaryManager.getDictionaries()) {
					combo.add(dict.name());
					combo_1.add(dict.name());
					combo_2.add(dict.name());
				}
				combo_1.select(0);
			}
			
			if(DictionaryManager.getSELECTED_DICTIONARY_WORDTAB() != null) {
				if(DictionaryManager.getSELECTED_DICTIONARY_WORDTAB().isChanged()) {
					listWordSelection.removeAll();
					listWordSelection.add("Create new..");
					for(DictionaryEntry entry : DictionaryManager.getSELECTED_DICTIONARY_WORDTAB().getEntries()) {
						listWordSelection.add(entry.getWord().toString());
					}
					changedThisIteration = true;
				}
				if(needtoshow) {
					listWordSelection.removeAll();
					listWordSelection.add("Create new..");
					for(DictionaryEntry entry : DictionaryManager.getSELECTED_DICTIONARY_WORDTAB().getEntries()) {
						listWordSelection.add(entry.getWord().toString());
					}
					
					needtoshow = false;
					
					changedThisIteration = true;
				}
			}
			
			if(DictionaryManager.getSELECTED_DICTIONARY_DICTIONARYTAB() != null) {
				if(DictionaryManager.getSELECTED_DICTIONARY_DICTIONARYTAB().isChanged() || changedThisIteration) {
					list_3.removeAll();
					for(DictionaryEntry entry : DictionaryManager.getSELECTED_DICTIONARY_DICTIONARYTAB().getEntries()) {
						list_3.add(entry.getTranslation() + " : " + entry.getWord().toString());
					}
				}
				if(needtoshow) {
					list_3.removeAll();
					for(DictionaryEntry entry : DictionaryManager.getSELECTED_DICTIONARY_DICTIONARYTAB().getEntries()) {
						list_3.add(entry.getTranslation() + " : " + entry.getWord().toString());
					}
					
					needtoshow = false;
				}
			}
			
			if(DictionaryManager.getSELECTED_DICTIONARY_SOUNDSHIFTTAB() != null) {
				if(DictionaryManager.getSELECTED_DICTIONARY_SOUNDSHIFTTAB().isChanged() || changedThisIteration) {
					//I added this check in while I was tired, it's not needed or anything, but i'll leave it here in case I should need it later
				}
			}
			
			if(PhonemeManager.isSoundChangesChanged()) {
				list.removeAll();
				for(SoundChange change : PhonemeManager.getSoundChanges()) {
					list.add(change.name());
				}
			}
			if(PhonemeManager.isPhonemesChanged()) {
				phonemeList.removeAll();
				list_4.removeAll();
				phonemeList.add("Create new..");
				for(Phoneme phoneme: PhonemeManager.getPhonemes()) {
					phonemeList.add(phoneme.getRepresentation());
					list_4.add(phoneme.getRepresentation());
				}
			}
			if(PhonemeManager.isPhonemeClassesChanged()) {
				list_1.removeAll();
				list_1.add("Create new..");
				loop: for(PhonemeClass pClass : PhonemeManager.getPhonemeClasses()) {
					if(pClass.name().equals("<may stay empty>")) continue loop;
					list_1.add(pClass.name());
				}
			}
			
			if(PhonemeManager.isSelectedSoundChangesChanged()) {
				list_2.removeAll();
				for(SoundChange sh : PhonemeManager.getSelectedSoundChanges()) {
					list_2.add(sh.name());
				}
			}
			
			if(PhonemeManager.isSelectedPhonemesChanged()) {
				list_5.removeAll();
				String word = "";
				for(PhonemeWrapper wrapper : PhonemeManager.getSelectedPhonemes()) {
					list_5.add(wrapper.getWrappedPhoneme().getRepresentation());
					word = word + wrapper.getWrappedPhoneme().getRepresentation();
				}
				
				text_6.setText(word);
			}
			
			if(list_5.getSelection().length != 0) {
				btnMarkPhonemeAs.setSelection(PhonemeManager.getSelectedPhoneme(list_5.getSelectionIndex()).getStress());
			}
			
			if(list.getSelectionCount() == 0) {
				editSoundChangeButton.setEnabled(false);
			} else {
				editSoundChangeButton.setEnabled(true);
			}
			
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
			changedThisIteration = false;
		}
	}
}
