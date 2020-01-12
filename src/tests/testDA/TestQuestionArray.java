package tests.testDA;

import objects.QuestionsArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQuestionArray {
    QuestionsArray queArray;

    @BeforeEach
    public void setUp() {
        queArray = new QuestionsArray(3);
    }

    @Test
    public void testSetQuestions(){
        queArray.setQuestion("What is your name?");
        queArray.setQuestion("When do you get up?");
        queArray.setQuestion("Do you have special event?");
        assertTrue(queArray.getQuestionArray()[0]=="What is your name?");
        assertTrue(queArray.getQuestionArray()[1]=="When do you get up?");
        assertTrue(queArray.getQuestionArray()[2]=="Do you have special event?");
        assertEquals(queArray.getArraySize(),3);
        assertFalse(queArray.getArraySize()==1);
    }

    @Test
    public void testGetIndex(){
        queArray.setQuestion("What is your name?");
        queArray.setQuestion("When do you get up?");
        int x=queArray.getIndex();
        assertTrue(x==2);
        assertTrue(queArray.getIndex()==2);
        assertFalse(queArray.getIndex()==3);
    }

    @Test
    public void testGetAPieceOfQuestion(){
        queArray.setQuestion("What is your name?");
        queArray.setQuestion("When do you get up?");
        queArray.setQuestion("Do you have special event?");
        String s= queArray.getAPieceOfQuestion(1);
        assertEquals(s,"When do you get up?");
    }

}
