public abstract class LinearProbing<K, V> extends Dictionary<K, V> {
    private Entry<K, V> hashTable[];
    protected int slots;
    private int size = 0;
    private int tombStones = 0;
    public int lookups = 0;

    @SuppressWarnings("unchecked")
    public LinearProbing(int slots){
        this.slots = slots;
        this.hashTable = new Entry[this.slots];
    }

    public boolean isFull(){
        return this.slots == this.size;
    }

    @Override
    public boolean delete_c(K key) {
        // rehashing when there are too many tombstones
        if((double)(tombStones + 1) / this.slots > 0.4)
            rehash();
        
        // getting the probe with the hash function
        int probe = hash(getKeyHashCode(key));

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

            probe = (probe + 1) % this.slots;
        }
        return false;
    }

    @Override
    public void insert_c(K key, V value) throws Exception {
        // hashtable full
        if(this.isFull()) throw new Exception("HashTable Full");

        Entry<K, V> entry = new Entry<K, V>(key, value);
        int probe = hash(getKeyHashCode(key));

        // searching for an empty location
        while(hashTable[probe] != null)
           { 
               lookups++;
               // found a tombstone, so overwrite it
               if(hashTable[probe].isTombStone()) break;
               probe = (probe + 1) % this.slots;
            }
        hashTable[probe] = entry;
        size++;
        
    }

    @Override
    public V search_c(K key) {
        int probe = hash(getKeyHashCode(key));

        while(hashTable[probe] != null){
            lookups++;
            if((!hashTable[probe].isTombStone()) && hashTable[probe].getKey().equals(key))
                return hashTable[probe].getValue();
            probe = (probe + 1) % this.slots;
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
                if(!hashTable[i].isTombStone()) 
                {
                    int probe = hash(getKeyHashCode(hashTable[i].getKey()));

                    while(rehashedTable[probe] != null)
                    {
                        lookups++;
                        probe = (probe + 1) % this.slots;
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
