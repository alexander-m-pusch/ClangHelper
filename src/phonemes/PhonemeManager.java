package phonemes;

import java.util.ArrayList;

public class PhonemeManager {
	private static final ArrayList<Phoneme> PHONEMES = new ArrayList<Phoneme>();
	private static final ArrayList<PhonemeClass> PHONEME_CLASSES = new ArrayList<PhonemeClass>();
	private static final ArrayList<SoundChange> SOUND_CHANGES = new ArrayList<SoundChange>();
	private static final ArrayList<SoundChange> SELECTED_SOUND_CHANGES = new ArrayList<SoundChange>();
	private static final ArrayList<PhonemeWrapper> SELECTED_PHONEMES = new ArrayList<PhonemeWrapper>();
	
	private static Phonotactics PHONOTACTICS;
	
	private static boolean PHONEMES_changed = false;
	private static boolean PHONEME_CLASSES_changed = false;
	private static boolean SOUND_CHANGES_changed = false;
	private static boolean SELECTED_SOUND_CHANGES_changed = false;
	private static boolean SELECTED_PHONEMES_changed = false;
	
	public static void setPhonotactics(Phonotactics phonotactics) {
		PHONOTACTICS = phonotactics;
	}
	
	public static Phonotactics getPhonotactics() {
		return PHONOTACTICS;
	}
	
	public static void clearPhonemes() {
		PHONEMES.clear();
		PHONEMES_changed = true;
	}
	
	public static void clearPhonemeClasses() {
		PHONEME_CLASSES.clear();
		PHONEME_CLASSES_changed = true;
	}
	
	public static void clearSoundChanges() {
		SOUND_CHANGES.clear();
		SOUND_CHANGES_changed = true;
	}
	
	public static void clearSelectedSoundChanges() {
		SELECTED_SOUND_CHANGES.clear();
		SELECTED_SOUND_CHANGES_changed = true;
	}
	
	public static void clearSelectedPhonemes() {
		SELECTED_PHONEMES.clear();
		SELECTED_PHONEMES_changed = true;
	}
	
	public static void resetSelectedPhonemes() {
		SELECTED_PHONEMES.clear();
		SELECTED_PHONEMES_changed = true;
	}
	
	public static PhonemeWrapper[] getSelectedPhonemes() {
		return SELECTED_PHONEMES.toArray(new PhonemeWrapper[SELECTED_PHONEMES.size()]);
	}
	
	public static Phoneme[] getPhonemes() {
		return PHONEMES.toArray(new Phoneme[PHONEMES.size()]);
	}
	
	public static PhonemeClass[] getPhonemeClasses() {
		return PHONEME_CLASSES.toArray(new PhonemeClass[PHONEME_CLASSES.size()]);
	}
	
	public static SoundChange[] getSoundChanges() {
		return SOUND_CHANGES.toArray(new SoundChange[SOUND_CHANGES.size()]);
	}
	
	public static SoundChange[] getSelectedSoundChanges() {
		return SELECTED_SOUND_CHANGES.toArray(new SoundChange[SELECTED_SOUND_CHANGES.size()]);
	}
	
	@Deprecated
	public static PhonemeWrapper getSelectedPhoneme(String key) {
		for(PhonemeWrapper phoneme : getSelectedPhonemes()) {
			if(phoneme.getWrappedPhoneme().getRepresentation().equals(key)) {
				return phoneme;
			}
		}
		
		return null;
	}
	
	public static PhonemeWrapper getSelectedPhoneme(int index) {
		return SELECTED_PHONEMES.get(index);
	}
	
	public static Phoneme getPhoneme(String key) {
		for(Phoneme phoneme : getPhonemes()) {
			if(phoneme.getRepresentation().equals(key)) {
				return phoneme;
			}
		}
		
		return null;
	}
	
	public static void addSelectedPhoneme(PhonemeWrapper wrapper) {
		SELECTED_PHONEMES.add(wrapper);
		SELECTED_PHONEMES_changed = true;
	}
	
	public static void addPhoneme(Phoneme phoneme) {
		PHONEMES.add(phoneme);
		PHONEMES_changed = true;
	}
	
	public static void addPhonemeClass(PhonemeClass pClass) {
		PHONEME_CLASSES.add(pClass);
		PHONEME_CLASSES_changed = true;
	}
	
	public static void addSoundChange(SoundChange soundChange) {
		SOUND_CHANGES.add(soundChange);
		SOUND_CHANGES_changed = true;
	}
	
	public static void addSelectedSoundChange(SoundChange soundChange) {
		SELECTED_SOUND_CHANGES.add(soundChange);
		SELECTED_SOUND_CHANGES_changed = true;
	}
	
	public static void removeSelectedPhoneme(PhonemeWrapper wrapper) {
		SELECTED_PHONEMES.remove(wrapper);
		SELECTED_PHONEMES_changed = true;
	}
	
	public static void removeSelectedPhoneme(int position) {
		SELECTED_PHONEMES.remove(position);
		SELECTED_PHONEMES_changed = true;
	}
	
	public static void removePhoneme(Phoneme phoneme) {
		PHONEMES.remove(phoneme);
		PHONEMES_changed = true;
		for(PhonemeClass pc : PHONEME_CLASSES) {
			if(pc.isPhonemeOfThisClass(phoneme)) pc.removePhoneme(phoneme);
		}
	}
	
	public static void removePhonemeClass(PhonemeClass pClass) {
		PHONEME_CLASSES.remove(pClass);
		PHONEME_CLASSES_changed = true;
	}
	
	public static void removeSoundChange(SoundChange soundChange) {
		SOUND_CHANGES.remove(soundChange);
		SOUND_CHANGES_changed = true;
	}
	
	public static void removeSelectedSoundChange(SoundChange soundChange) {
		SELECTED_SOUND_CHANGES.remove(soundChange);
		SELECTED_SOUND_CHANGES_changed = true;
	}
	
	public static boolean isSelectedPhonemesChanged() {
		boolean changed = SELECTED_PHONEMES_changed;
		SELECTED_PHONEMES_changed = false;
		return changed;
	}
	
	public static boolean isPhonemesChanged() {
		boolean changed = PHONEMES_changed;
		PHONEMES_changed = false;
		return changed;
	}
	
	public static boolean isPhonemeClassesChanged() {
		boolean changed = PHONEME_CLASSES_changed;
		PHONEME_CLASSES_changed = false;
		return changed;
	}
	
	public static boolean isSoundChangesChanged() {
		boolean changed = SOUND_CHANGES_changed;
		SOUND_CHANGES_changed = false;
		return changed;
	}
	
	public static boolean isSelectedSoundChangesChanged() {
		boolean changed = SELECTED_SOUND_CHANGES_changed;
		SELECTED_SOUND_CHANGES_changed = false;
		return changed;
	}
	
	public static PhonemeClass getPhonemeClass(String key) {
		for(PhonemeClass phoneme : getPhonemeClasses()) {
			if(phoneme.name().equals(key)) {
				return phoneme;
			}
		}
		
		return null;
	}
	
	public static SoundChange getSoundChange(String key) {
		for(SoundChange phoneme : getSoundChanges()) {
			if(phoneme.name().equals(key)) {
				return phoneme;
			}
		}
		
		return null;
	}
	
	public static SoundChange getSelectedSoundChange(String key) {
		for(SoundChange phoneme : getSelectedSoundChanges()) {
			if(phoneme.name().equals(key)) {
				return phoneme;
			}
		}
		
		return null;
	}
}