package com.test;

/*
 * $Header: /cvsroot/pdfdoclet/pdfdoclet/example/test/com/test/MyCriteria.java,v 1.2 2005/09/17 07:08:58 marcelschoen Exp $
 * 
 * @Copyright: Marcel Schoen, Switzerland, 2005, All Rights Reserved.
 */

/**
 * This is just some strange class.
 * 
 * @author Marcel Schoen
 * @version $Revision: 1.2 $
 */
public interface MyCriteria {
    
    /** 
    * Method contains. This method returns if the track is in the 
    * geographic criteria. 
    * @param target The object to test. 
    * @return The boolean is true if the filter contains the object. 
    */ 
    public boolean contains(Target target); 
}
