/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * ------------------
 * RotationTests.java
 * ------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: RotationTests.java,v 1.2 2005/10/18 13:25:14 mungady Exp $
 *
 * Changes
 * -------
 * 08-Jan-2004 : Version 1 (DG);
 *
 */

package org.jfree.util.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.util.Rotation;

/**
 * Tests for the {@link Rotation} class.
 */
public class RotationTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RotationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RotationTests(final String name) {
        super(name);
    }

    /**
     * Tests the equals() method.
     */
    public void testEquals() {
        assertTrue(Rotation.CLOCKWISE.equals(Rotation.CLOCKWISE));
        assertTrue(Rotation.ANTICLOCKWISE.equals(Rotation.ANTICLOCKWISE));
    }
    
    /**
     * Serialize an instance, restore it, and check for identity.
     */
    public void testSerialization() {

        final Rotation r1 = Rotation.CLOCKWISE;
        Rotation r2 = null;

        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            final ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (Rotation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertTrue(r1 == r2); 

    }

}
