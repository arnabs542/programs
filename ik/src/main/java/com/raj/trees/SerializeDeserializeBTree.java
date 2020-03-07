package com.raj.trees;

public class SerializeDeserializeBTree {

    /**
     * Serialize a Binary Tree to store in a file so that it can be later restored. The structure of tree must be
     * maintained. Deserialize to read tree back from file.
     * https://www.geeksforgeeks.org/serialize-deserialize-binary-tree/
     */
    public static void main(String[] args) {

    }

    /**
     * Approach 1:
     * Do PreOrder + InOrder Traversals, store in 2 arrays. Reconstruct it back going left to right in PreOrder sequence
     * & recursively building left & right after looking up it's left/right nodes in InOrder sequence.
     * Uses O(2*n) space
     *
     * Approach 2:
     * # Iterate tree in PreOrder fashion, indicating -1 for leaf's pointers which don't have childs. Store it in Q.
     * # Deserialize: Pop element, if -1 then return null base case & non -1s as new root nodes,
     *   recursively building left/right subtrees & returning new root node.
     *
     */
}
