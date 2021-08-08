import java.lang.Exception;

public class Stack{
    private int MAX = 10;
    private Object[] content;
    private int tos;

    public Stack(){
        content = new Object[MAX];
        tos = -1;
    }

    public void push(Object el){
        if(this.isFull())
        throw new StackOverflowError();
        content[++tos] = el;
    }

    public Object pop() throws Exception{
        if(this.isEmpty())
        throw new Exception("Stack Underflow");

        return content[tos--];
    }

    public Object top() throws Exception{
        if(this.isEmpty())
            throw new Exception("Stack Underflow");

        return content[tos];
    }

    public boolean isEmpty(){
        return tos == -1;
    }

    public boolean isFull(){
        return tos - 1 == MAX;
    }
}
