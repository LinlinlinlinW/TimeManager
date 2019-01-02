/**************this is the test before designing UI**************/

//package tests.testExceptions;
//
//import exceptions.ImproperInputException;
//import exceptions.NotLogicalInputException;
//import objects.User;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class testIIEAndNLIE {
//
//    @Test
//    void testNotThrowImproperInputForIdentity() {
//        try {
//            User user = new User();
//            user.setIdentity(1);
//            System.out.println("No ImproperInputForIdentityException is thrown. Great!");
//        } catch (ImproperInputException e) {
//            fail("Should not throw ImproperInputForIdentityException");
//        }
//    }
//
//    @Test
//    public void testImproperInputForIdentity() {
//        try {
//            User user = new User();
//            user.setIdentity(3);
//            fail("Did not throw ImproperInputForIdentityException");
//        } catch (ImproperInputException e) {
//            System.out.println("Successfully throw ImproperInputForIdentityException");
//        }
//    }
//
//    @Test
//    public void testNotThrowImproperInputForSetGetUpTime() {
//        try {
//            User user = new User();
//            user.setGetUpTime(6);
//            System.out.println("No ImproperInputForIdentityException is thrown. Great!");
//        } catch (ImproperInputException e) {
//            fail("Should not throw ImproperInputForIdentityException");
//        }
//    }
//
//    @Test
//    public void testImproperInputForSetGetUpTime1() {
//        try {
//            User user = new User();
//            user.setGetUpTime(-1);
//            fail("Did not throw ImproperInputForIdentityException");
//        } catch (ImproperInputException e) {
//            System.out.println("Successfully throw ImproperInputForIdentityException");
//        }
//    }
//
//    @Test
//    public void testImproperInputForSetGetUpTime2() {
//        try {
//            User user = new User();
//            user.setGetUpTime(24);
//            fail("Did not throw ImproperInputForIdentityException");
//        } catch (ImproperInputException e) {
//            System.out.println("Successfully throw ImproperInputForIdentityException");
//        }
//    }
//
//    @Test
//    public void testNotThrowImproperInputForSetSleepTime() {
//        try {
//            User user = new User();
//            user.setSleepTime(23);
//            System.out.println("No ImproperInputForIdentityException is thrown. Great!");
//        } catch (NotLogicalInputException e) {
//            fail("Should not throw ImproperInputForIdentityException");
//        } catch (ImproperInputException ee) {
//            fail("Should not throw ImproperInputForIdentityException");
//        }
//    }
//
//        @Test
//        public void testImproperInputForSetSleepTime1 () {
//            try {
//                User user = new User();
//                user.setGetUpTime(6);
//                user.setSleepTime(4);
//                fail("Did not throw NotLogicalInputException");
//            } catch (NotLogicalInputException ee) {
//                System.out.println("Successfully throw ImproperInputForIdentityException");
//            } catch (ImproperInputException e) {
//                fail("Should not throw ImproperInputException");
//            }
//        }
//
//        @Test
//        public void testImproperInputForSetSleepTime2 () {
//            try {
//                User user = new User();
//                user.setSleepTime(25);
//                fail("Did not throw ImproperInputForIdentityException");
//            } catch (NotLogicalInputException e) {
//                fail("Should not throw NotLogicalInputException");
//            } catch (ImproperInputException ee) {
//                System.out.println("Successfully throw ImproperInputForIdentityException");
//            }
//        }
//
////    @Test
////    public void test
//    }
//
//
