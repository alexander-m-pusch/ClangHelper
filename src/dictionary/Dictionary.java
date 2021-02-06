package dictionary;

import java.util.ArrayList;

public class Dictionary {
	private final ArrayList<DictionaryEntry> entries = new ArrayList<DictionaryEntry>();
	private final String name;
	private boolean hasChanged = false;
	
	public Dictionary(String name) {
		this.name = name;
	}
	
	public void clearEntries() {
		this.entries.clear();
		hasChanged = true;
	}
	
	public void addEntries(DictionaryEntry[] entries) {
		for(DictionaryEntry entry : entries) {
			this.entries.add(entry);
		}
		hasChanged = true;
	}
	
	public void addEntry(DictionaryEntry entry) {
		this.entries.add(entry);
		hasChanged = true;
	}
	
	public void removeEntry(DictionaryEntry entry) {
		this.entries.remove(entry);
		hasChanged = true;
	}
	
	public boolean isChanged() {
		boolean ch = hasChanged;
		hasChanged = false;
		return ch;
	}
	
	public DictionaryEntry getEntry(String translation) {
		for(DictionaryEntry entry : entries) {
			if(entry.getTranslation().equals(translation)) return entry;
		}
		return null;
	}
	
	public DictionaryEntry getEntryForWord(String word) {
		for(DictionaryEntry entry : entries) {
			if(entry.getWord().toString().equals(word)) return entry;
		}
		return null;
	}
	
	public DictionaryEntry[] getEntries() {
		return entries.toArray(new DictionaryEntry[this.entries.size()]);
	}
	
	public String name() {
		return this.name;
	}
	
	public int getSize() {
		return entries.size();
	}
}
