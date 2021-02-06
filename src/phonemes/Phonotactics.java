package phonemes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Phonotactics {
	private final Map<Integer, ArrayList<PhonemeClass>> allowed;
	private int maxPhonemes = 0;
	private boolean isChanged = false;
	
	public Phonotactics() {
		this.allowed = new HashMap<Integer, ArrayList<PhonemeClass>>();
	}
	
	public Map<Integer, ArrayList<PhonemeClass>> getSet() {
		return this.allowed;
	}
	
	public void setMaxPhonemes(int i) {
		this.maxPhonemes = i;
		isChanged = true;
	}
	
	public int getMaxPhonemes() {
		return this.maxPhonemes;
	}
	
	public boolean isChanged() {
		boolean ch = isChanged;
		isChanged = false;
		return ch;
	}
	
	public boolean isSet() {
		return this.allowed.size() != 0 ? true : false;
	}
	
	public void addAllowedPhonemeClass(PhonemeClass clazz, int position) {
		if(allowed.get(position) == null) {
			allowed.put(position, new ArrayList<PhonemeClass>());
		}
		allowed.get(position).add(clazz);
		isChanged = true;
	}
	
	public boolean isContained(PhonemeClass clazz, int index) {
		for(PhonemeClass cl : this.allowed.get(index)) {
			if(cl.equals(clazz)) return true;
		}
		return false;
	}
	
	public void removeAllowedPhonemeClass(PhonemeClass clazz, int position) {
		if(allowed.get(position) != null) allowed.get(position).remove(clazz);
		isChanged = true;
	}
	
	public void addSlot(int index) {
		this.allowed.put(index, new ArrayList<PhonemeClass>());
		isChanged = true;
	}
	
	public void removeSlot(int index) {
		allowed.remove(index);
		isChanged = true;
	}

	public PhonemeClass[] getPhonemeClassesForSlot(int slot) {
		if(this.allowed.get(slot) != null) return this.allowed.get(slot).toArray(new PhonemeClass[this.allowed.get(slot).size()]);
		return new PhonemeClass[] { };
	}
}