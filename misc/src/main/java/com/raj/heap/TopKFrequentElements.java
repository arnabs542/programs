package com.raj.heap;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author rshekh1
 */
public class TopKFrequentElements {

    /**
     * Given a non-empty array of integers, return the k most frequent elements.
     *
     * Example 1:
     *
     * Input: nums = [5,4,1,6,1,1,2,8,2,3,5], k = 3
     * Output: [1,2,5]
     *
     * Algo:
     * # Convert array into map of int,freq => [(1,3), (2,2), (3,1)]
     * # Option1: sort by values => nlogn
     * # Option2: Use a Min Heap of size K to track the min item at top.
     *      - Iterate and for each element, if heap.top < element, insert into heap
     *      - Remove heap contents into stack, pop stack and print
     *
     * ==> Why min heap?
     * In a Heap we only have access to top element. We need to decide if the new element is of interest.
     * If Min is at the top, all elements below are larger than top(more frequent than top).
     * While comparison, we can always evaluate if we need to insert it or not as we are trying
     * to maintain k most frequent elements in heap. Insert if element greater than top.
     *
     */
    public static void main(String[] args) {
        System.out.println(findTopK(Lists.newArrayList(5,4,1,6,1,1,2,8,2,3,5), 3));
        System.out.println(topKFrequent(new int[]{5,4,1,6,1,1,2,8,2,3,5}, 3));
    }

    private static List<Integer> findTopK(List<Integer> arr, int K) {
        // break into frequency KV
        Map<Integer,Integer> map = new HashMap<>();
        arr.forEach(x -> {
            if (!map.containsKey(x)) map.put(x, 1);
            else map.put(x, map.get(x)+1);
        });

        PriorityQueue<Tuple> pq = new PriorityQueue(new Comparator<Tuple>() {
            @Override
            public int compare(Tuple o1, Tuple o2) {
                if (o1._2 < o2._2) return -1;
                if (o1._2 > o2._2) return +1;
                else return 0;
            }
        });
        map.forEach((k,v) -> {
            if (pq.size() < K) {
                pq.offer(new Tuple(k,v));
            } else if (v > pq.peek()._2) {
                pq.poll(); pq.offer(new Tuple(k,v));
            }
        });
        List<Integer> res = Lists.newArrayList();
        while (!pq.isEmpty()) res.add(pq.poll()._1);
        return Lists.reverse(res);
    }

    private static class Tuple {
        public int _1, _2;

        public Tuple(int _1, int _2) {
            this._1 = _1;
            this._2 = _2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return _2 == tuple._2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(_2);
        }

        @Override
        public String toString() {
            return _2+"";
        }
    }

    // using map & inverted map
    public static List<Integer> topKFrequent(int[] nums, int k) {
        // map of num->freq
        Map<Integer,Integer> num2Freq = new HashMap<>();
        for (int n:nums) {
            if (!num2Freq.containsKey(n)) num2Freq.put(n, 0);
            num2Freq.put(n, num2Freq.get(n)+1);
        }
        // invert map freq->num
        Map<Integer,List<Integer>> freq2Num = new HashMap<>();
        num2Freq.forEach((num,freq) -> {
            if (!freq2Num.containsKey(freq)) freq2Num.put(freq, new ArrayList<>());
            freq2Num.get(freq).add(num);
        });

        System.out.println(freq2Num);

        // min heap of size k
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.naturalOrder());
        int i = 0, j = 0;
        // init heap with size k
        Iterator<Map.Entry<Integer,List<Integer>>> it = freq2Num.entrySet().iterator();
        while (minHeap.size() < k && it.hasNext()) {
            Map.Entry<Integer,List<Integer>> me = it.next();
            List<Integer> l = me.getValue();
            while(j<l.size() && minHeap.size() < k)
                minHeap.add(me.getKey()); j++;
        }

        while(it.hasNext()) {
            Map.Entry<Integer,List<Integer>> me = it.next();
            if (me.getKey() > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(me.getKey());
            }
        }

        List<Integer> res = new ArrayList<>();
        Stack<Integer> s = new Stack<>();
        while (!minHeap.isEmpty()) s.push(minHeap.poll());
        while (!s.isEmpty() && res.size() < k) res.addAll(freq2Num.get(s.pop()));
        return res;
    }

}
