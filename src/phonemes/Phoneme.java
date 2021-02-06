package phonemes;


public class Phoneme {
	
	private final String stringrep;
	
	public Phoneme(String stringrep) {
		this.stringrep = stringrep;
	}
	
	public String getRepresentation() {
		return this.stringrep;
	}
	
	@Override
	public String toString() {
		return this.getRepresentation();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Phoneme) return this.stringrep.equals(((Phoneme) o).getRepresentation()) ? true : false;
		return false;
	}
}
