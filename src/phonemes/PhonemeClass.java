package phonemes;

import java.util.ArrayList;
import java.util.Random;

public class PhonemeClass {
	
	private final String className;
	private final ArrayList<Phoneme> containedPhonemes;
	
	public static PhonemeClass merge(String mergedName, PhonemeClass... classes) {
		ArrayList<Phoneme> phonemes = new ArrayList<Phoneme>();
		for(PhonemeClass p : classes) {
			for(Phoneme ph : p.getPhonemes()) {
				if(!phonemes.contains(ph)) phonemes.add(ph);
			}
		}
		return new PhonemeClass(mergedName, phonemes.toArray(new Phoneme[phonemes.size()]));
	}
	
	public PhonemeClass(String name, Phoneme... containedPhonemes) {
		this.className = name;
		this.containedPhonemes = new ArrayList<Phoneme>();
		for(Phoneme phoneme : containedPhonemes) {
			this.containedPhonemes.add(phoneme);
		}
	}
	
	public String name() {
		return this.className;
	}
	
	public void addPhoneme(Phoneme phoneme) {
		this.containedPhonemes.add(phoneme);
	}
	
	public void removePhoneme(Phoneme phoneme) {
		this.containedPhonemes.remove(phoneme);
	}
	
	public Phoneme getPhoneme(String key) {
		for(Phoneme phoneme : this.containedPhonemes) {
			if(phoneme.getRepresentation().equals(key)) return phoneme;
		}
		return null;
	}
	
	public Phoneme[] getPhonemes() {
		return this.containedPhonemes.toArray(new Phoneme[this.containedPhonemes.size()]);
	}
	
	public boolean isPhonemeOfThisClass(Phoneme phoneme) {
		for(Phoneme ph : this.containedPhonemes) {
			if(ph.equals(phoneme)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Phoneme getRandom() {
		Random random = new Random();
		int index = random.nextInt(this.containedPhonemes.size());
		return this.containedPhonemes.get(index);
	}
}
