package nl.tue.fingerpaint.client;

import java.io.Serializable;

/**
 * Abstract class to be implemented by mixers. 
 * 
 * This way enum mixers that belong
 * to a different Geometry still have the same typing. This is needed in class
 * userChoice
 * 
 * @author Group Fingerpaint
 */
public interface Mixer extends Serializable {

}
