public class BST<T> {
    public static class Node<T> {
        private int key;
        private T value;
        protected Node<T> parent = null;
        protected Node<T> right = null;
        protected Node<T> left = null;
        protected int height = 1;

        public Node(int key, T value) {
            this.key = key;
            this.value = value;
        }

        public T getValue() {
            return this.value;
        }

        protected int getKey() {
            return this.key;
        }

        public String toString() {
            return this.value.toString();
        }

        public int getHeight() {
            return this.height;
        }
    }

    // root of the tree
    protected Node<T> root = null;

    public BST() {
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public Node<T> getMaximum(Node<T> node) {
        Node<T> current = node;

        // finding the rightmost node
        while (current.right != null) {
            current = current.right;
        }

        return current;
    }

    public Node<T> getMinimum(Node<T> node) {
        Node<T> current = node;

        // finding the leftmost node
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    protected int max(int n1, int n2) {
        return n1 >= n2 ? n1 : n2;
    }

    protected int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    public Node<T> insert(int key, T value) {
        Node<T> y = null;
        Node<T> x = this.root;

        // finding an empty link to insert
        while (x != null) {
            y = x;
            if (key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        Node<T> z = new Node<>(key, value);
        z.parent = y;

        // checking for an empty tree
        if (y == null)
            this.root = z;
        // updating the parent of node to be inserted
        else if (key < y.key)
            y.left = z;
        else
            y.right = z;

        updateParentHeight(z);

        return z;
    }

    public Node<T> search(int key) {
        Node<T> x = this.root;

        while (x != null && key != x.key) {
            if (key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        return x;
    }

    public Node<T> successor(Node<T> node) {
        // right subtree not null so return the leftmost node there
        if (node.right != null)
            return getMinimum(node.right);

        Node<T> y = node.parent;
        Node<T> x = node;
        // finding a node which does not have a right child as x
        // moving up the tree
        while (y != null && y.right == x) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    public Node<T> predecessor(Node<T> node) {
        // left subtree not null so return the leftmost node there
        if (node.left != null)
            return getMaximum(node.left);

        Node<T> y = node.parent;
        Node<T> x = node;
        // finding a node which does not have a left child as x
        // moving up the tree
        while (y != null && y.left == x) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    // helper function for replacing
    // a node 'x' with a node 'y'
    private void replace(Node<T> x, Node<T> y) {
        if (y != null)
            y.parent = x.parent;
        if (x.parent != null) {
            if (x.parent.left == x)
                x.parent.left = y;
            else
                x.parent.right = y;
            x.parent.height = max(height(x.parent.left), height(x.parent.right)) + 1;
        } else {
            // only comes where when there is only root in the tree
            // or root has only child and root is to be deleted
            this.root = y;
            if (this.root != null)
                this.root.parent = null;
        }
    }

    public void remove(Node<T> node) {
        // no left child
        if (node.left == null) {
            replace(node, node.right);
            updateParentHeight(node);
        }
        // no right child
        else if (node.right == null) {
            replace(node, node.left);
            updateParentHeight(node);
        }
        // both children
        else {
            // find successor
            Node<T> y = successor(node);
            // copy contents of successor
            node.value = y.value;
            node.key = y.key;
            // remove the successor
            remove(y);
        }

    }

    protected void updateParentHeight(Node<T> node) {
        if (node == null)
            return;
        node.height = max(height(node.left), height(node.right)) + 1;
        updateParentHeight(node.parent);
    }

    public void inorder(Node<T> node) {
        if (node == null)
            return;
        else {
            inorder(node.left);
            System.out.print(node + " ");
            inorder(node.right);
        }
    }

    public void preorder(Node<T> node) {
        if (node == null)
            return;
        else {
            System.out.print(node + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder(Node<T> node) {
        if (node == null)
            return;
        else {
            postorder(node);
            postorder(node);
            System.out.print(node + " ");
        }
    }

}
