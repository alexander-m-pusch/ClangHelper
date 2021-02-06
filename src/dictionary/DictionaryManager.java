package dictionary;

import java.util.ArrayList;

public class DictionaryManager {
	private static final ArrayList<Dictionary> DICTIONARIES = new ArrayList<Dictionary>();
	private static Dictionary SELECTED_DICTIONARY_SOUNDSHIFTTAB;
	private static Dictionary SELECTED_DICTIONARY_DICTIONARYTAB;
	private static Dictionary SELECTED_DICTIONARY_WORDTAB;
	private static String SELECTED_TRANSLATION;
	
	private static boolean hasChanged = false;
	
	public static void clearAll() {
		SELECTED_DICTIONARY_SOUNDSHIFTTAB = null;
		SELECTED_DICTIONARY_DICTIONARYTAB = null;
		SELECTED_DICTIONARY_WORDTAB = null;
		clearList();
	}
	
	public static void clearList() {
		DICTIONARIES.clear();
		
		hasChanged = true;
	}
	
	public static String getSELECTED_TRANSLATION() {
		return SELECTED_TRANSLATION;
	}

	public static void setSELECTED_TRANSLATION(String sELECTED_TRANSLATION) {
		SELECTED_TRANSLATION = sELECTED_TRANSLATION;
	}

	public static Dictionary getSELECTED_DICTIONARY_SOUNDSHIFTTAB() {
		return SELECTED_DICTIONARY_SOUNDSHIFTTAB;
	}

	public static void setSELECTED_DICTIONARY_SOUNDSHIFTTAB(Dictionary sELECTED_DICTIONARY_SOUNDSHIFTTAB) {
		SELECTED_DICTIONARY_SOUNDSHIFTTAB = sELECTED_DICTIONARY_SOUNDSHIFTTAB;
	}

	public static void setSELECTED_DICTIONARY_WORDTAB(Dictionary sELECTED_DICTIONARY_WORDTAB) {
		SELECTED_DICTIONARY_WORDTAB = sELECTED_DICTIONARY_WORDTAB;
	}
	
	public static Dictionary getSELECTED_DICTIONARY_WORDTAB() {
		return SELECTED_DICTIONARY_WORDTAB;
	}
	
	public static Dictionary getSELECTED_DICTIONARY_DICTIONARYTAB() {
		return SELECTED_DICTIONARY_DICTIONARYTAB;
	}

	public static void setSELECTED_DICTIONARY_DICTIONARYTAB(Dictionary sELECTED_DICTIONARY_DICTIONARYTAB) {
		SELECTED_DICTIONARY_DICTIONARYTAB = sELECTED_DICTIONARY_DICTIONARYTAB;
	}

	public static void addDictionary(Dictionary dict) {
		hasChanged = true;
		DICTIONARIES.add(dict);
	}
	
	public static void removeDictionary(Dictionary dict) {
		hasChanged = true;
		DICTIONARIES.remove(dict);
	}
	
	public static Dictionary[] getDictionaries() {
		return DICTIONARIES.toArray(new Dictionary[DICTIONARIES.size()]);
	}
	
	public static Dictionary getDictionary(String name) {
		Dictionary dict = null;
		for(Dictionary dictionary : DICTIONARIES) {
			if(dictionary.name().equals(name)) {
				dict = dictionary;
			}
		}
		return dict;
	}
	
	public static boolean getChanged() {
		boolean changed = hasChanged;
		hasChanged = false;
		return changed;
	}
}
