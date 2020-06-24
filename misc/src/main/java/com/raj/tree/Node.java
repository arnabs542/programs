package com.raj.tree;

/**
 * @author: sraj1
 * Date:    10/10/12
 */
public class Node {

    public int val;
    public Node left, right;
    public Node parent;
    public Node nextRight;
    public int depth;
    public boolean isVisited;

    public Node() {

    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node left, Node right, Node parent, boolean visited) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.parent = parent;
        isVisited = visited;
    }

    @Override
    public String toString() {
        return val + "";
    }
}
