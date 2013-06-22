package com.other;

/*
 * @(#)OtherClass.java
 */
import com.test.ISecondInterface;

/**
 * This is a sub-interface of another interface.
 *
 * @author  Marcel Schoen
 * @version $Id: IThirdInterface.java,v 1.1 2005/01/24 18:11:57 marcelschoen Exp $
 */
public interface IThirdInterface extends ISubInterface {

    /**
     * And a method again.
     *
     * @return true if it is annyoing
     */
    public boolean isAnnyoing();
}

