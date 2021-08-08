import java.util.LinkedList;
import java.util.ListIterator;

public abstract class Chaining<K, V> extends Dictionary<K, V> {
    protected int slots;
    private LinkedList<Entry<K, V>> hashTable[];
    public int lookups = 0;

    @SuppressWarnings("unchecked")
    public Chaining(int slots){
        this.slots = slots;
        this.hashTable = new LinkedList[this.slots];
        // HashTable intialization
        for(int i = 0; i < this.hashTable.length; i++)
            this.hashTable[i] = new LinkedList<>();
    }

    @Override
    public boolean delete_c(K key) {
        // getting the index with hash function
        int index = hash(getKeyHashCode(key));
        // iterator to iterate through the list
        ListIterator<Entry<K, V>> iterator = this.hashTable[index].listIterator();
        lookups++;
        while(iterator.hasNext()){
            // move the iterator one step ahead
            Entry<K, V> current = iterator.next();
            lookups++;
            if(current.getKey().equals(key))
               { 
                   iterator.remove();
                    return true;
                }
        }
        return false;
    }

    @Override
    public void insert_c(K key, V value) {
       Entry<K, V> entry = new Entry<>(key, value);
        int index = hash(getKeyHashCode(key));
        this.hashTable[index].add(entry);
    }

    @Override
    public V search_c(K key) {
        int index = hash(getKeyHashCode(key));
        ListIterator<Entry<K, V>> iterator = this.hashTable[index].listIterator();
        lookups++;
        while(iterator.hasNext()){
           Entry<K, V> current = iterator.next();
           lookups++;
           if(current.getKey().equals(key))
            return current.getValue();
        }
        return null;
    }
}
