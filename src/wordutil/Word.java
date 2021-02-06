package wordutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import phonemes.PhonemeWrapper;

public class Word {
	
	private final List<PhonemeWrapper> phonemes;
	
	public Word(int stressedBegin, int stressedEnd, PhonemeWrapper... phonemes) {
		this.phonemes = Arrays.asList(phonemes);
		for(PhonemeWrapper s : this.phonemes) {
			s.setStress(false);
		}
		for(int i = stressedBegin; i <= stressedEnd; i++) {
			this.computeStress(i);
		}
	}
	
	@Override
	public String toString() {
		String concatenate = "";
		
		for(PhonemeWrapper phoneme : phonemes) {
			concatenate = concatenate + phoneme.getWrappedPhoneme().getRepresentation();
		}
		
		return concatenate;
	}
	
	public Word() {
		this.phonemes = new ArrayList<PhonemeWrapper>();
	}
	
	public void addPhoneme(PhonemeWrapper phoneme) {
		this.phonemes.add(phoneme);
	}
	
	public void setStressedSegment(int begin, int end) {
		for(int i = begin; i <= end; i++) {
			this.computeStress(i);
		}
	}
	
	public void computeStress(int stressedPhoneme) {
		if(stressedPhoneme < 0) {
			//count from the back
			if(stressedPhoneme + this.phonemes.size() > 0) {
				this.phonemes.get(0).setStress(true);
			} else {
				this.phonemes.get(stressedPhoneme + this.phonemes.size());
			}
		} else {
			//count from the front
			if(stressedPhoneme >= phonemes.size()) {
				this.phonemes.get(this.phonemes.size() - 1).setStress(true);
			} else {
				this.phonemes.get(stressedPhoneme);
			}
		}
	}
	
	public PhonemeWrapper[] getPhonemes() {
		return this.phonemes.toArray(new PhonemeWrapper[this.phonemes.size()]);
	}
}
