public class CircularLinkedList {
    private static class Node{
        Object data;
        Node next;

        public Node(Object data, Node next){
            this.data = data;
            this.next = next;
        }
    }

    private Node front;
    private Node rear;

    public CircularLinkedList(){
        front = rear = null;
    }

    public void insert(Object data){
        if(this.isEmpty()){
            front = rear = new Node(data, null);
            rear.next = front;
        }
        else{
            Node newNode = new Node(data, front);
            rear.next = newNode;
            rear = newNode;
        }
    }

    public Object remove() throws Exception{

        if(isEmpty())
            throw new Exception("CircularListEmpty: Cannot Remove");

        Object element;
        if(front == rear){
            element = front.data;
            front = rear = null;
        }
        else{
            element = front.data;
            rear.next = front.next;
            front = front.next;
        }

        return element;
    }

    public boolean isEmpty(){
        return front == null;
    }

    public String toString(){

        if(this.isEmpty())
            return "";

        StringBuilder opString = new StringBuilder();
        Node current = front;
        do{
            // System.out.println(current.data);
            opString.append(current.data.toString() + " ");
            current = current.next;
        }while(current != front);

        return opString.toString();
    }
}
