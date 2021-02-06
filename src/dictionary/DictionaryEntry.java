package dictionary;

import wordutil.Word;

public class DictionaryEntry {
	private Word word;
	private String translation;
	 
	public DictionaryEntry(Word word, String translation) {
		this.word = word;
		 this.translation = translation;
	}
	 
	public void setWord(Word word) {
		this.word = word;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public Word getWord() {
		return word;
	}

	public String getTranslation() {
		return translation;
	}
}
