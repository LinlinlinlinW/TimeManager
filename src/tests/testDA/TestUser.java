/**************this is the test before designing UI**************/

//package tests.testDA;
//
//import exceptions.ImproperInputException;
//import exceptions.NotLogicalInputException;
//import objects.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestUser {
//    User user;
//    List<String> events;
//    List<String> hobbies;
//
//    @BeforeEach
//    public void setUp() {
//        user = new User();
//
//        events = new ArrayList<>();
//        hobbies = new ArrayList<>();
//    }
//
//    @Test
//    public void testSetIdentity() {
//        try {
//            user.setIdentity(2);
//            assertTrue(user.getIdentity() == 2);
//            assertFalse(user.getIdentity() == 1);
//        } catch (ImproperInputException e) {
//        }
//    }
//
//    @Test
//    public void testSetGetUpTime() {
//        try {
//            user.setGetUpTime(6);
//            assertTrue(user.getGetUpTime() == 6);
//            assertFalse(user.getGetUpTime() == 7);
//        } catch (ImproperInputException e) {
//        }
//    }
//
//    @Test
//    public void testSetSleepTime() {
//        try {
//            user.setSleepTime(23);
//            assertTrue(user.getSleepTime() == 23);
//            assertFalse(user.getSleepTime() == 22);
//        } catch (ImproperInputException e) {
//        } catch (NotLogicalInputException ee) {
//        }
//    }
//
//    @Test
//    public void testJudgeEvent() {
//        try {
//            user.setJudgeEvent(1);
//            assertTrue(user.getJudgeE() == 1);
//            assertFalse(user.getJudgeE() == 2);
//        } catch (ImproperInputException e) {
//        }
//    }
//
//    @Test
//    public void testSetNumOfEvents() {
//        try {
//            user.setNumOfEvents(2);
//            assertTrue(user.getNumEvents() == 2);
//        } catch (ImproperInputException e) {}
//          catch (NotLogicalInputException ee) {}
//    }
//
//    @Test
//    public void testSetEvents(){
//        try{
//            user.setEvents("running");
//            user.setEvents("cooking");
//            assertTrue(user.getEvents().get(0)=="running");
//            assertTrue(user.getEvents().get(1)=="cooking");
//            assertFalse(user.getEvents().contains("playing"));
//        }catch(NumberFormatException e){}
//    }
//
//    @Test
//    public void testNoEvents(){
//        try{
//            user.setNumOfEvents(2);
//            assertTrue(user.getEvents().size()==0);
//        }catch(ImproperInputException e){}
//         catch(NotLogicalInputException ee){}
//    }
//
//}
