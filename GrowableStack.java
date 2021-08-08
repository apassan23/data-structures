public class GrowableStack {
    private static int c = 4;
    private Object[] content;
    private int length = 0;
    private int top = -1;

    public GrowableStack(){

    }

    public void push(Object el){
        if(top == length - 1){
            Object[] newArray = new Object[length + GrowableStack.c];

            for(int i = 0; i < this.size(); i++)
            newArray[i] = content[i];

            length += GrowableStack.c;
            content = newArray;
        }

        content[++top] = el;
    }

    public Object pop() throws Exception{
        if(this.isEmpty())
        throw new Exception("Stack Underflow");

        return content[top--];
    }

    public Object top() throws Exception{
        if(this.isEmpty())
        throw new Exception("Stack Underflow");

        return content[top];
    }   

    public boolean isEmpty(){
        return top == -1;
    }

    public static void setBound(int c){
        GrowableStack.c = c;
    }

    public int size(){
        return top + 1;
    }

    public String toString(){
        StringBuilder opBuilder = new StringBuilder();

        for(int i = 0; i < this.size(); i++){
            opBuilder.append(content[i].toString() + " ");
        }

        return opBuilder.toString();
    }

}
