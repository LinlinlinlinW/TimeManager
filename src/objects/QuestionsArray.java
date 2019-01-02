package objects;

public class QuestionsArray {
    private String[] questionArray;
    private int index;
    private int arraySize;

    //constructor
    public QuestionsArray(int listSize){
        questionArray = new String[listSize];
        index=0;
        this.arraySize = listSize;
    }

    //Modifies:this(index)
    //Effects:update current index of question array
    public void setQuestionArrayIndex(){
        index++;
        if(index >= arraySize)
            throw new ArrayIndexOutOfBoundsException();
    }

    //Modifies: this(questionArray)
    //Effects: add new question to the question array
    public void setQuestion(String s){
        try {
            questionArray[getIndex()] = s;
            setQuestionArrayIndex();
        } catch (ArrayIndexOutOfBoundsException e){}
    }

    //Effects: return questionArray
    public String[] getQuestionArray(){
        return this.questionArray;
    }

    //Effects: return current index
    public int getIndex(){
        return this.index;
    }

    //Effects: return the size of the array
    public int getArraySize(){
        return this.arraySize;
    }

    //Effects: return the question in the specified index of array
    public String getAPieceOfQuestion(int i){
        return questionArray[i];
    }
}
