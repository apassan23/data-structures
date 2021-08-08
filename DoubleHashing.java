public abstract class DoubleHashing<K, V> extends Dictionary<K, V> {
    private Entry<K, V>[] hashTable;
    private int size;
    protected int slots;
    public int lookups = 0;
    private int tombStones = 0;

    @SuppressWarnings("unchecked")
    public DoubleHashing(int slots){
        this.slots = slots;
        this.hashTable = new Entry[this.slots];
    }

    private int hash2(int key){
        // taken the same as was in the video lecture
        return 8 - (key % 8);
    }

    public boolean isFull(){
        return this.size == this.slots;
    }

    @Override
    public boolean delete_c(K key) {
        // rehashing when there are too many tombstones
        if((double)(tombStones + 1) / this.slots > 0.4)
            rehash();

        // getting the probe with the hash function 1
        int probe = hash(getKeyHashCode(key));
        // getting the offset to move with hash function 2
        int offset = hash2(getKeyHashCode(key));

        while(hashTable[probe] != null){
            lookups++;
            if(hashTable[probe].getKey().equals(key)){
                // marking the entry as deleted
                // by making it a tombstone
                hashTable[probe].makeTombStone();
                tombStones++;
                size--;
                return true;
            }

            probe = (probe + offset) % this.slots;
        }

        return false;
    }

    @Override
    public void insert_c(K key, V value) throws Exception {
        // hashtable full
        if(this.isFull()) throw new Exception("HashTable Full!");

        int probe = hash(getKeyHashCode(key));
        int offset = hash2(getKeyHashCode(key));
        Entry<K, V> entry = new Entry<>(key, value);

        // searching for an empty location
        while(hashTable[probe] != null)
        { 
            lookups++;
            // found a tombstone, so overwrite it
            if(hashTable[probe].isTombStone()) break;
            probe = (probe + offset) % this.slots;
         }
        hashTable[probe] = entry;
        size++;
        
    }

    @Override
    public V search_c(K key) {
        int probe = hash(getKeyHashCode(key));
        int offset = hash2(getKeyHashCode(key));

        while(hashTable[probe] != null){
            lookups++;
            if((!hashTable[probe].isTombStone()) && hashTable[probe].getKey().equals(key))
                return hashTable[probe].getValue();
            probe = (probe + offset) % this.slots;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void rehash(){
        // creating an empty table
        Entry<K, V>[] rehashedTable = new Entry[hashTable.length];
        for(int i = 0; i < hashTable.length; i++){
            lookups++;
            if(hashTable[i] != null){
                if(!hashTable[i].isTombStone()){

                    int probe = hash(getKeyHashCode(hashTable[i].getKey()));
                    int offset = hash2(getKeyHashCode(hashTable[i].getKey()));
                    
                    while(rehashedTable[probe] != null)
                    {
                        lookups++;
                        probe = (probe + offset) % this.slots;
                    }
                    rehashedTable[probe] = hashTable[i];
                }
            }
                
        }
        // resetting the number of tombstones
        this.tombStones = 0;
        // changing the refernce of hashtable to the 
        // rehashed one
        this.hashTable = rehashedTable;
    }
    
}
