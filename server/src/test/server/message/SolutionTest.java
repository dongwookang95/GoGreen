package server.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    public void testBasicMac() {
        String message = "01aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbb000fffff";
        assertTrue(Solution.checkMac(Solution.addMac(message, "key",message.length()),"key", message.length()));
    }
//
//    @Test
//    public void testKeyedMac() {
//        String message = "01aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbb000fffff";
//        assertFalse(Solution.addMac(message,"key", message.length()).equals(message));
//        assertFalse(Solution.addMac(message,"key", message.length()).equals(message + Hash.hash(message)));
//    }
//
    @Test
    public void testBasicMac2() {
        String message = "randomMessagesAlsoWork";
        assertTrue(Solution.checkMac(Solution.addMac(message,"key", message.length()),"key", message.length()));
    }

    @Test
    public void testBasicReplay() {
        String message = "01aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbb000fffff";
        String messageWithMac = Solution.addMac(message, "key", message.length());
        assertTrue(Solution.checkMac(messageWithMac,"key", message.length()));
        assertFalse(Solution.checkMac(messageWithMac,"key", message.length()));
    }

    // @Test(timeout = 100000)
    // public void testTimingAttack() {
    //   String message = "Insert a message and crack the mac for 100/100!";
    //   assertTrue(MacLib.checkMac(message, Solution.forgeMac(message)));
    // }

}