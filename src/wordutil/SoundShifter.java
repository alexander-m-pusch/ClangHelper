package wordutil;

import phonemes.Phoneme;
import phonemes.PhonemeClass;
import phonemes.PhonemeWrapper;
import phonemes.SoundChange;

public class SoundShifter {
	public static Word shiftSound(Word input, PhonemeClass shiftFrom, Phoneme shiftTo, PhonemeClass shiftBefore, int beforeOffset, PhonemeClass shiftAfter, int afterOffset, boolean affectStressed, boolean affectUnstressed) {
		if(!affectStressed && !affectStressed) return input; //if it affects literally nothing, dont waste computer power on it!
		
		Word newWord = new Word();
		
		int phonemeCounter = 0;
		PhonemeWrapper[] wrappers = input.getPhonemes();
		for(PhonemeWrapper wrapper : wrappers) {
			Phoneme phoneme = wrapper.getWrappedPhoneme();
			
			boolean shifted = true;
			if(shiftFrom.isPhonemeOfThisClass(phoneme)) {
				if((wrapper.getStress() && affectStressed) || (!wrapper.getStress() && affectUnstressed)) {
					if(shiftBefore != null) {
						if(phonemeCounter + beforeOffset >= wrappers.length) {
							shifted = shifted & false;
						} else {
							PhonemeWrapper beforeWrapper = wrappers[phonemeCounter + beforeOffset];
							if(shiftBefore.isPhonemeOfThisClass(beforeWrapper.getWrappedPhoneme())) {
								shifted = shifted & true;
							}
						}
						if(shiftAfter != null) {
							if(phonemeCounter - afterOffset <= 0) {
								shifted = shifted & false;
							} else {
								PhonemeWrapper afterWrapper = wrappers[phonemeCounter - beforeOffset];
								if(shiftAfter.isPhonemeOfThisClass(afterWrapper.getWrappedPhoneme())) {
									shifted = shifted & true;
								}
							}
						}
					} else if(shiftAfter != null) {
						if(phonemeCounter - afterOffset < 0) {
							shifted = shifted & false;
						} else {
							PhonemeWrapper afterWrapper = wrappers[phonemeCounter - beforeOffset];
							if(shiftAfter.isPhonemeOfThisClass(afterWrapper.getWrappedPhoneme())) {
								shifted = shifted & true;
							} else {
								shifted = shifted & false;
							}
						}
					}
				} 
			} else {
				shifted = shifted & false;
			}
			if(!shifted) {
				newWord.addPhoneme(wrapper);
			} else {
				PhonemeWrapper newPh = new PhonemeWrapper(shiftTo);
				newPh.setStress(wrapper.getStress());
				newWord.addPhoneme(newPh);
			}
			phonemeCounter++;
		}
		
		return newWord;
	}
	
	public static Word shiftSound(Word input, SoundChange soundChange) {
		return SoundShifter.shiftSound(input, soundChange.getShiftedFrom(), soundChange.getShiftTo(), soundChange.getShiftedBefore(), soundChange.getBeforeOffset(), soundChange.getShiftedAfter(), soundChange.getAfterOffset(), soundChange.isAffectStressed(), soundChange.isAffectUnstressed());
	}
}
