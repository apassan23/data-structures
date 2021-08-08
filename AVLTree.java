public class AVLTree<T> extends BST<T> {
    @Override
    public Node<T> insert(int key, T value) {
        Node<T> node = super.insert(key, value);

        Node<T> x = node;
        Node<T> y = x.parent;

        if (y != null) {
            Node<T> z = y.parent;
            if (z != null) {
                // finding the unbalanced node (if any)
                while (z.parent != null && !isImbalanced(z)) {
                    x = y;
                    y = z;
                    z = z.parent;
                }

                // z can be imbalanced, the root of the tree or both
                if (isImbalanced(z)) {
                    boolean doubleRotation = false;
                    if (y.left == x) {
                        // LL Case
                        if (z.left == y)
                            rightRotate(z);
                        // RL Case
                        else {
                            rightRotate(y);
                            leftRotate(z);
                            doubleRotation = true;
                        }
                    }

                    else {
                        // LR Case
                        if (z.left == y) {
                            leftRotate(y);
                            rightRotate(z);
                            doubleRotation = true;
                        }
                        // RR Case
                        else
                            leftRotate(z);
                    }

                    // updating the root and updating height of the
                    // branch where the element was inserted
                    // if root was unbalanced, then on rotation it
                    // will change
                    if (doubleRotation) {
                        if (this.root == z)
                            this.root = x;
                        else
                            updateParentHeight(x.parent);
                    } else {
                        if (this.root == z)
                            this.root = y;
                        else
                            updateParentHeight(y.parent);
                    }
                }
            }

        }

        return node;
    }

    @Override
    public void remove(Node<T> node) {
        Node<T> parentNode = node.parent;
        super.remove(node);
        // rebalancing after deletion
        rebalanceOnRemove(parentNode);
    }

    private void rebalanceOnRemove(Node<T> node) {
        if (node == null)
            return;

        if (isImbalanced(node)) {
            // since y is the the child of node with higher height
            Node<T> y = height(node.left) >= height(node.right) ? node.left : node.right;
            // since x is the the child of y with higher height
            Node<T> x = height(y.left) >= height(y.right) ? y.left : y.right;
            boolean doubleRotation = false;

            if (y.left == x) {
                // LL Case
                if (node.left == y)
                    rightRotate(node);
                // RL Case
                else {
                    rightRotate(y);
                    leftRotate(node);
                    doubleRotation = true;
                }
            }

            else {
                // LR Case
                if (node.left == y) {
                    leftRotate(y);
                    rightRotate(node);
                    doubleRotation = true;
                }
                // RR Case
                else
                    leftRotate(node);
            }

            // updating the root and updating height of the
            // branch where the element was inserted
            // if root was unbalanced, then on rotation it
            // will change
            if (doubleRotation) {
                if (this.root == node)
                    this.root = x;
                else
                    updateParentHeight(x.parent);
            } else {
                if (this.root == node)
                    this.root = y;
                else
                    updateParentHeight(y.parent);
            }
        }

        rebalanceOnRemove(node.parent);
    }

    private void rightRotate(Node<T> node) {
        Node<T> leftChild = node.left;
        Node<T> parent = node.parent;

        // rotation
        node.left = leftChild.right;
        if (leftChild.right != null)
            leftChild.right.parent = node;
        leftChild.right = node;
        node.parent = leftChild;

        // updating the node links
        leftChild.parent = parent;
        if (parent != null) {
            if (parent.left == node)
                parent.left = node.parent;
            else
                parent.right = node.parent;
        }

        // updating heights
        node.height = max(height(node.left), height(node.right)) + 1;
        node.parent.height = max(height(node), height(node.parent.left)) + 1;
    }

    private void leftRotate(Node<T> node) {
        Node<T> rightChild = node.right;
        Node<T> parent = node.parent;

        // rotation
        node.right = rightChild.left;
        if (rightChild.left != null)
            rightChild.left.parent = node;
        rightChild.left = node;
        node.parent = rightChild;

        // updating the node links
        rightChild.parent = parent;
        if (parent != null) {
            if (parent.left == node)
                parent.left = node.parent;
            else
                parent.right = node.parent;
        }

        // updating heights
        node.height = max(height(node.left), height(node.right)) + 1;
        node.parent.height = max(height(node), height(node.parent.right)) + 1;
    }

    private boolean isImbalanced(Node<T> node) {
        if (node == null)
            return false;

        return Math.abs(height(node.left) - height(node.right)) > 1;
    }

}
