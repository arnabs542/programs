package com.raj.stackqueues;

/**
 * @author <a href="mailto:rshekhar@walmartlabs.com">Shekhar Raj</a>
 */
public class MinStack {

    /**
     * Design a recursionStack that supports push, pop, top, and retrieving the minimum element in constant time.

     push(x) – Push element x onto recursionStack.
     pop() – Removes the element on top of the recursionStack.
     top() – Get the top element.
     getMin() – Retrieve the minimum element in the recursionStack.
     Note that all the operations have to be constant time operations.

     Algo:
     Maintain 2 queues. One which stored the actual recursionStack of element, and the other which stores
     the minimum of elements.So when pushing new element,
     min = min(top of minimum recursionStack, current value) which is pushed to minimum recursionStack.

     However, this uses 2N memory.
     */

    int[] stack = new int[1000];
    int stackSize = -1;
    int[] minStack = new int[100];
    int minStackSize = -1;

    public void push(int x) {
        int min = -999999;
        if (minStackSize >= 0) min = minStack[minStackSize];
        if (x < min || minStackSize == -1) minStack[++minStackSize] = x;
        stack[++stackSize] = x;
    }

    public void pop() {
        if (stackSize < 0) return;
        int x = stack[stackSize--];
        if (x == getMin()) minStackSize--;
    }

    public int top() {
        if (stackSize < 0) return -1;
        return stack[stackSize];
    }

    public int getMin() {
        if (minStackSize < 0) return -1;
        return minStack[minStackSize];
    }

    public static void main(String[] args) {
        MinStack m = new MinStack();
        String str = "P 10 P 9 g P 8 g P 7 g P 6 g p g p g p g p g p g";
        String[] arr = str.split(" ");
        for (int i=0; i<arr.length;i++) {

            String s = arr[i];
            if (s.equals("P")) m.push(Integer.valueOf(arr[++i]));
            if (s.equals("g")) System.out.println(m.getMin());
            if (s.equals("p")) m.pop();
        }
    }
}
