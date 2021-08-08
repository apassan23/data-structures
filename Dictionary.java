public abstract class Dictionary<K, V> {
    protected static class Entry<K, V>{
        private K key;
        private V value;
        private boolean tombStone = false;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        // getter for value
        public V getValue() {
            return value;
        }

        // Setter for value
        public void setValue(V value) {
            this.value = value;
        }

        // getter for key
        public K getKey(){
            return this.key;
        }

        // method to make an entry a tombstone (marker)
        public void makeTombStone(){
            this.tombStone = true;
        }

        // returns true if the entry is a tombstone
        public boolean isTombStone(){
            return this.tombStone;
        }
    }

    // to convert the key into an integer
    // with polynomial accumulation using
    // horner's rule
    protected int getKeyHashCode(K key) {
        int hash = 0;
        int x = 37;
        // to avoid overflow of integers
        int m = (int)1e9 + 9;
        String keyString = String.valueOf(key);
        for(int i = 0; i < keyString.length(); i++)
            hash = ((hash * x) + keyString.charAt(i)) % m;
        return hash;
    }

    public abstract void insert_c(K key, V value) throws Exception;
    public abstract V search_c(K key);
    public abstract boolean delete_c(K key);
    // hash function (implemented in the repective main class files)
    public abstract int hash(int key);
}
