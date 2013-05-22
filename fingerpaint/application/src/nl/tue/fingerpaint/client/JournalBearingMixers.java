package nl.tue.fingerpaint.client;

/** 
 * Enum class that represents all mixers that belong to Geometry JOURNALBEARING
 * 
 * @author Group Fingerpaint
 */
public enum JournalBearingMixers implements Mixer{// new Mixers can be added here
	ExampleMixerName10, ExampleMixerName11, ExampleMixerName12;
	
	/** 
	 * Overrides the default toString method. Result starts with capital letter, and the remainder of the string is lowerCase.
	 */
		@Override
		public String toString() {
			String str = super.toString();
			return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
		}
}
