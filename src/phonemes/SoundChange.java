package phonemes;

public class SoundChange {
	private final PhonemeClass shiftedBefore;
	private final PhonemeClass shiftedAfter;
	private final PhonemeClass shiftedFrom;
	private final Phoneme shiftTo;
	private final int beforeOffset;
	private final int afterOffset;
	private final boolean affectUnstressed;
	private final boolean affectStressed;
	
	private final String name;
	
	public SoundChange(String name, PhonemeClass shiftedBefore, PhonemeClass shiftedAfter, PhonemeClass shiftedFrom, Phoneme shiftTo, int beforeOffset, int afterOffset, boolean affectUnstressed, boolean affectStressed) {
		this.shiftedBefore = shiftedBefore;
		this.shiftedAfter = shiftedAfter;
		this.shiftedFrom = shiftedFrom;
		this.shiftTo = shiftTo;
		this.beforeOffset = beforeOffset;
		this.afterOffset = afterOffset;
		this.affectUnstressed = affectUnstressed;
		this.affectStressed = affectStressed;
		
		this.name = name;
	}
	
	public String name() {
		return this.name;
	}
	
	public PhonemeClass getShiftedBefore() {
		return shiftedBefore;
	}

	public PhonemeClass getShiftedAfter() {
		return shiftedAfter;
	}

	public PhonemeClass getShiftedFrom() {
		return shiftedFrom;
	}

	public Phoneme getShiftTo() {
		return shiftTo;
	}

	public int getBeforeOffset() {
		return beforeOffset;
	}

	public int getAfterOffset() {
		return afterOffset;
	}

	public boolean isAffectUnstressed() {
		return affectUnstressed;
	}

	public boolean isAffectStressed() {
		return affectStressed;
	}
}