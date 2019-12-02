package com.raj.sorting;

import java.util.ArrayList;
import java.util.List;

public class FourBillion {

    /**
     * You have been given an input file with four billion unsigned longs, provide an algorithm to generate an long which is not
     * contained in the file. We are considering long as 32 bit unsigned longs. Assume you have 1 GiB memory. Follow up
     * with what you would do if you have only 10 MiB of memory.
     */
    public static void main(String[] args) {
        List<Long> l = new ArrayList<>();
        for (long i=0;i<10_000_000;i++) if (i!=99_999) l.add(i);
        System.out.println(find_integer(l));
        System.out.println(find_integer_10MB(l));
    }

    /**
     * Storing each long wud take 8 bytes in java, hence 4B longs wud take 32 GB, if they were unique !!
     * But we know the range of longs(per problem) 32 bits. So they lie b/w 0 to 2^32 range. At max we have 2^32 longs.
     * Storing them wud need 2^32 * 8 bytes = 16 GB, but we have 1 GB only.
     * Idea is to squeeze numbers in least amount of space.
     * We have 1GB memory available which is equivalent to 10^9 bytes = 8*10^9 bits.
     * We can consider each of these bits to represent a long. And set them if present. Now each long takes just 1 bit !!
     * This way we can test presence of 4 Billion numbers !!
     * Finally, we return any number whose corresponding bit isn’t set.
     *
     * Consider the following input:
     * [0,2,3]
     * Creating a 2^32 bit array may be not possible, also even if we create it arr[index can only be int which is max ] so let's break it into chunks of 32 bit longs
     * Here, we create bins of size 0xffffffff/32 (or /8), where each bin will hold 32 bits, each bit representing an integer.
     * Here, since the integers are 0, 2 and 3, we will iterate through the integers and mark the corresponding bits in
     * the corresponding bin as set. So here, we set the zeroth, second and the third bit as set in bin 1. So Now the
     * first bin looks like (0...001101) in binary form. Here, we also note that none of the other bins get changed.
     * Now we iterate over the bins and return the integer corresponding to the first unset bit. Here, that integer is 1.
     */
    public static long find_integer(List<Long> arr) {
        long startM = Runtime.getRuntime().freeMemory();
        int bitsPerBin = 32;
        int bin_size = (int) (Math.pow(2,32)/bitsPerBin); // ALWAYS ENCLOSE BEFORE TYPECASTING, else it doesn't wrk
        int[] bins = new int[bin_size]; // int is 4 bytes = 32 bits, shard whole 2^32 range into 0-31,32-63,64-95... bit range

        System.out.println("bin_size = " + bin_size*1L);
        System.out.println((startM - Runtime.getRuntime().freeMemory())/(1024*1024) + " MB");

        // set longs
        for (long n : arr) {
            int bin = (int) (n / bitsPerBin);       // which bin to put this long?
            int bitToSet = (int) (n % bitsPerBin);  // what's the bit to set in this bin
            bins[bin] |= 1<<bitToSet;               // just set that 1 bit representing this long
        }

        // find missing long
        for (int i = 0; i < bin_size; i++) {  // bin 0....bin_size, each has 32 bits (11111....1101) => wud mean 1 is missing
            for (int j = 0; j < bitsPerBin; j++) {
                // find unset bit & return formed num
                if ((bins[i] & 1<<j) == 0) return (bitsPerBin * i) + j;
            }
        }
        return -1L;
    }

    /**
     * What if we need to do this with 10MB memory?
     * Our prev solution uses 1GB. 10MB is 100x smaller. So we can apply "paging technique" of external sort.
     * Basically, run the algo 100 times over after making the bins size smaller & check presence of missing numbers on just a small range
     */
    public static long find_integer_10MB(List<Long> arr) {

        // set longs for smaller range
        long range = (long)(Math.pow(2,32)/100);
        long start = 0L, end = start + range - 1;
        for (int window = 0; window < 100; window++) {
            long startM = Runtime.getRuntime().freeMemory();
            int bitsPerBin = 32;
            int bin_size = (int) (Math.pow(2,32)/(bitsPerBin*100)); // ALWAYS ENCLOSE BEFORE TYPECASTING, else it doesn't wrk
            int[] bins = new int[bin_size]; // int is 4 bytes = 32 bits, shard whole 2^32 range into 0-31,32-63,64-95... bit range

            System.out.println("bin_size = " + bin_size*1L);
            System.out.println((startM - Runtime.getRuntime().freeMemory())/(1024*1024) + " MB");

            for (long n : arr) {
                if (n < start || n > end) continue;
                long _n = n-start; // adjust offset
                int bin = (int) (_n / bitsPerBin);       // which bin to put this long?
                int bitToSet = (int) (_n % bitsPerBin);  // what's the bit to set in this bin
                bins[bin] |= 1 << bitToSet;             // just set that 1 bit representing this long
            }

            // find missing long
            for (int i = 0; i < bin_size; i++) {
                for (int j = 0; j < bitsPerBin; j++) {
                    // find unset bit & return formed num
                    if ((bins[i] & 1 << j) == 0) return (bitsPerBin * i * 1L) + j + start; // adjust for offset
                }
            }
            start = end+1;
            end += range;
        }
        return -1L;
    }

}
