package com.raj.arrays;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Read4K {
    /**
     * FB:
     * char[] read4k() API reads 4k bytes. You can't change it.
     * char[] read(int n) --> implement using read4k()
     *
     */
    public static void main(String[] args) {
        boolean isEOF = false;
        Read read = new Read();
        while (!isEOF) {
            byte[] res = read.read(100);
            if (res == null) {
                System.out.println("Reached EOF");
                break;
            }
            System.out.println(new String(res));
        }
    }

    static int MAX_SIZE = 4*1024;
    static byte[] blob;
    static int ptr_4k = 0;
    static File file = new File("/Users/rshekh1/myapps/workspace-intellij/programs-1.0/ik/src/main/java/com/raj/BEHAVIORAL/CAREER_SKILLS.txt");
    static {
        try {
            blob = Files.readAllBytes(file.toPath());
            System.out.println(blob.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static byte[] read4k() { // can return null, some or full 4k bytes
        byte[] tmp;
        if (ptr_4k >= blob.length) return null;  // already EOF
        else if (ptr_4k + MAX_SIZE < blob.length) {   // ptr_4k < EOF
            tmp = Arrays.copyOfRange(blob, ptr_4k, ptr_4k + MAX_SIZE);
            ptr_4k += MAX_SIZE;
        } else {  // only some bytes left
            tmp = Arrays.copyOfRange(blob, ptr_4k, blob.length);
            ptr_4k = blob.length; // reached EOF
        }
        return tmp;
    }

    static class Read {
        byte[] buffer;
        int bufferPtr;

        Read() {
            buffer = new byte[MAX_SIZE];
            bufferPtr = 0;
        }

        /**
         * buffer empty, read4k, need ptr to track where we are in buffer
         * loop until n>0, fill up result, keep calling 4k if buffer is empty
         */
        byte[] read(int n) {  // exclusive as in array indexes
            if (n <= 0) return null;
            byte[] res = new byte[n];
            int i = 0;

            while (i < n) {

                // buffer empty or at MAX_SIZE? read 4k
                if (bufferPtr == 0 || bufferPtr == buffer.length || bufferPtr == MAX_SIZE) {
                    System.out.println("bufferPtr = " + bufferPtr);
                    bufferPtr = 0;
                    buffer = read4k();
                }

                // buffer read was unsuccessful but there may have been some earlier read chars, return it
                if (buffer == null) {
                    return i > 0 ? Arrays.copyOfRange(res,0, i) : null;
                }

                // keep copying from buffer
                res[i++] = buffer[bufferPtr++];
            }
            return res;
        }

    }

    /**
     * Design:
     *
     * EOF = false
     * buff[]
     * buff_ptr
     *
     * read(n) ->
     * # n<=0 || EOF ret null
     * # create res arraylist
     * # n-= copy(buff,res,n)  --> copy until n bytes from buffer into res, return num bytes copied
     * # n > 0 ? buff = read4k()  --> maybe its EOF, or still less than n bytes
     *           buff == null ? EOF = true
     *           n-= copy(buff,res,n)
     *           ret res.toArray
     *
     * copy(buff,res,n):
     *   while(n-- > 0 && buff_ptr < buff.length) res.add(buff[buff_ptr++])
     *   ret n
     */

}
