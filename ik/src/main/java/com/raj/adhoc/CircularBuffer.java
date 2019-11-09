package com.raj.adhoc;

public class CircularBuffer {

    /**
     * Implement a circular buffer.
     *
     * Applications:
     * # Data (Audio/Video) Streaming - finite size buffer that purges older once it is full as we don't need too many frames
     * # Hash table - It's used to convert the value out of the hash function into an index into the array.
     * # Generally speaking if you want to keep cycling thru a set of contiguous indices without going out of bounds
     * # Use it for making sure a value stays within a range - (huge int value) % (max int value we want to go to)
     * # For arrays, there is a neat trick to count the occurrences provided
     *
     * The most common use I've found is for "wrapping round" your array indices.
     * For example, if you just want to cycle through an array repeatedly, you could use:
     * int a[10];
     * for (int i = 0; true; i = (i + 1) % 10) {
     *   // ... use a[i] ...
     * }
     * The modulo ensures that i stays in the [0, 10) range.
     *
     * Implementation:
     * Use a Fixed Length Array with following operations:
     * increment_index_one = (index + 1) % Length
     * decrement_index_one = (index + Length - 1) % Length  ... to avoid negative results eg. 0-1 % 10
     */
}
