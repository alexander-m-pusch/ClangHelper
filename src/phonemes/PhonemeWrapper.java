package phonemes;

public class PhonemeWrapper {
	private final Phoneme phoneme;
	private boolean stressed;
	
	public PhonemeWrapper(Phoneme phoneme) {
		this.phoneme = phoneme;
		this.stressed = false;
	}
	
	public void setStress(boolean stressed) {
		this.stressed = stressed;
	}
	
	public Phoneme getWrappedPhoneme() {
		return this.phoneme;
	}
	
	public boolean getStress() {
		return this.stressed;
	}
}
