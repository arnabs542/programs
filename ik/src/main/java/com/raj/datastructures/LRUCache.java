package com.raj.datastructures;

import java.util.*;

/**
 * @author rshekh1
 */
public class LRUCache {

    /**
     * Design and implement a data structure for LRU (Least Recently Used) cache. It should support the following operations: get and set.
     *
     * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
     * set(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least recently used item before inserting the new item.
     * The LRU Cache will be initialized with an integer corresponding to its capacity. Capacity indicates the maximum number of unique keys it can hold at a time.
     *
     * Definition of “least recently used” : An access to an item is defined as a get or a set operation of the item. “Least recently used” item is the one with the oldest access time.
     *
     * Example :
     *
     * Input :
     *          capacity = 2
     *          set(1, 10)
     *          set(5, 12)
     *          get(5)        returns 12
     *          get(1)        returns 10
     *          get(10)       returns -1
     *          set(6, 14)    this pushes out key = 5 as LRU is full.
     *          get(5)        returns -1
     */

    /**
     * Algo:
     * Create a doubly linked list - O(1) deletion, of size 'n' which stores keys and maintains insert ordering
     * Create a hashmap with key and value
     * set operation - Add element to hashmap & to the front of the queue. If full delete the last one from list and map.
     * get operation - return corresponding value from queue via lookup from map
     * Edge cases : Adding an existing key, then remove the node, re-insert at front but exclude it from capacity check
     */
    private LinkedList<QNode> queue;    // Queue maintains access order + QNode stores the actual value
    private HashMap<Integer,QNode> map; // map stores the address of the QueueNode
    private int size;
    static class QNode {
        int key, val;  // need to store key as well for removing map entry when queue is full
        QNode(int k, int v) {key = k;val = v;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QNode qNode = (QNode) o;
            return key == qNode.key &&
                    val == qNode.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, val);
        }

        @Override
        public String toString() {
            return new StringJoiner("->", "" + "(", ")")
                    .add(key+"")
                    .add(val+"")
                    .toString();
        }
    }

    public LRUCache(int capacity) {
        queue = new LinkedList<>();
        map = new HashMap<>();
        size = capacity;
    }

    public int get(int key) {
        System.out.print("****Get " + key + " -> ");
        if (map.get(key) == null) return -1;
        else {
            int value = map.get(key).val;
            this.set(key, value);    // move node to the front of the queue
            return value;
        }
    }

    public void set(int key, int value) {
        try {
            if (map.containsKey(key)) {  // if the key already exists, just remove the node from both DS and let the insert happen again at first position
                queue.remove(map.get(key));
                map.remove(key);
            }
            if (map.size() >= size) {   // if capacity exceeded, remove least recently used from both DS
                QNode delQNode = queue.removeLast();
                map.remove(delQNode.key);
            }
            // now add to the front and set value
            QNode node = new QNode(key, value);
            queue.addFirst(node);
            map.put(key, node);
        } catch (Exception e) {
            System.out.println("Failed to insert: " + key + " -> " + value);
            e.printStackTrace();
            System.exit(-1);
        } finally {
            System.out.println();
            System.out.print("Set " + key + " -> " + value + " : ");
            System.out.println(queue);
        }

    }

    public static void main(String[] args) {
        /*LRUCache lruCache = new LRUCache(2);
        lruCache.set(1, 10);
        lruCache.set(5, 12);
        lruCache.get(5);        //returns 12
        lruCache.get(1);        //returns 10
        lruCache.get(10);       //returns -1
        lruCache.set(6, 14);    //this pushes out key = 5 as LRU is full.
        lruCache.get(5);        //returns -1*/

        LRUCache lruCache = new LRUCache(7);
        String input = "G 2 S 2 6 G 1 S 1 5 S 1 2 G 1 G 2";
        input = "S 2 1 S 1 1 S 2 3 S 4 1 G 1 G 2";
        input = "S 2 1 S 2 2 G 2 S 1 1 S 4 1 G 2";
        input = "S 2 1 S 1 10 S 8 13 G 12 S 2 8 G 11 G 7 S 14 7 S 12 9 S 7 10 G 11 S 9 3 S 14 15 G 15 G 9 S 4 13 G 3 S 13 7 G 2 S 5 9 G 6 G 13 S 4 5 S 3 2 S 4 12 G 13 G 7 S 9 7 G 3 G 6 G 2 S 8 4 S 8 9 S 1 4 S 2 9 S 8 8 G 13 G 3 G 13 G 6 S 3 8 G 14 G 4 S 5 6 S 10 15 G 12 S 13 5 S 10 9 S 3 12 S 14 15 G 4 S 10 5 G 5 G 15 S 7 6 G 1 S 5 12 S 1 6 S 11 8";
        String[] arr = input.split(" ");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("S")) {
                lruCache.set(Integer.valueOf(arr[i+1]), Integer.valueOf(arr[i+2]));
                i+=2;
            }
            if (arr[i].equals("G")) {
                System.out.print(lruCache.get(Integer.valueOf(arr[i+1])) + " ");
                i+=1;
            }
        }

    }



    /**
     * A simpler implementation using inbuilt java DS "LinkedHashMap" which is an ordered hashmap,
     * Note: Set accessOrder while creating LinkedHashMap for the ordering on access for simplicity
     * Else u'll need to remove and re-insert on each get to maintain access order
     */
    Map<Integer,Integer> cache;
    public LRUCache(int capacity, boolean use) {
        cache = new LinkedHashMap<Integer,Integer>(capacity,0.75f,true) {    // access order needs to be set for it to get to the front of the map
            public boolean removeEldestEntry(Map.Entry eldest) { // no default impl, hence need to impl when to remove eldest
                return size() > capacity;
            }
        };
    }

    public int get0(int key) {
        return cache.getOrDefault(key,-1);
    }

    public void set0(int key, int value) {
        cache.put(key,value);
    }



    /**
     * Impl using bare bones DS
     */
    Map<Integer, Node> map0;
    Node start;
    Node end;
    int capacity;

    public LRUCache(int capacity, int t) {
        map = new HashMap<>();
        start = new Node(0,0);
        end = new Node(0,0);
        this.capacity = capacity;
        start.next = end;
        end.prev = start;
    }

    public int get1(int key) {
        if (map.containsKey(key)) {
            Node node = map0.get(key);
            removeNode(node);
            addNode1(node);

            return node.val;
        }

        return -1;
    }

    private void addNode1(Node node) {
        node.next = start.next;
        start.next = node;
        node.prev = start;
        node.next.prev = node;
    }

    private void removeNode(Node node) {
        Node temp = node.next;
        node.prev.next = temp;
        temp.prev = node.prev;
    }

    public void set1(int key, int value) {
        Node node = new Node(key, value);

        if (map.containsKey(key)) {
            Node temp = map0.get(key);
            removeNode(temp);
            addNode1(node);
        }
        else {
            if (capacity == map.size()) {
                Node temp = end.prev;
                removeNode(temp);
                map.remove(temp.key);
            }

            addNode1(node);
        }
        map0.put(key, node);
    }

    class Node {
        public int key;
        public int val;
        public Node prev;
        public Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            prev = null;
            next = null;
        }
    }

}
