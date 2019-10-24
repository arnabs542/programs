package com.raj.adhoc;

/**
 * @author rshekh1
 */
public class IsPrime {

    /**
     * For input N = 4 and a = [6, 2, 5, 8], output will be:
     * 0110
     * Input array can be huge & repetitive
     */
    public static void main(String[] args) {
        System.out.println(detect_primes(new int[]{6, 2, 5, 8}));
    }

    // O(N * log(log MAX_A))
    static String detect_primes(int[] arr) {
        boolean[] isPrime = new boolean[4000001];
        isPrime[1] = true;
        for(int i=2; i<Math.sqrt(4000001); i++){
            if(!isPrime[i]) {  //
                for(int j=i*i; j<4000001; j+=i){
                    isPrime[j]= true;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<arr.length; i++){
            if(isPrime[arr[i]]){
                sb.append(0);
            } else {
                sb.append(1);
            }
        }
        return sb.toString();
    }

}
