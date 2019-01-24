package com.raj.p_vs_np;

/**
 * @author rshekh1
 */
public class PvsNPvsNPComplete {

    /**
     * https://www.youtube.com/watch?v=YX40hbAHx3s
     * https://www.geeksforgeeks.org/np-completeness-set-1/
     *
     * NP complete problems are problems whose status is unknown. No polynomial time algorithm has yet been
     * discovered for any NP complete problem, nor has anybody yet been able to prove that no polynomial-time
     * algorithm exist for any of them. The interesting part is, if any one of the NP complete problems can
     * be solved in polynomial time, then all of them can be solved.
     *
     * What are NP, P, NP-complete and NP-Hard problems?
     * P is set of problems that can be solved by a deterministic Turing machine in Polynomial time.
     *
     * NP is set of decision problems that can be solved by a Non-deterministic Turing Machine in Polynomial time.
     * P is subset of NP (any problem that can be solved by deterministic machine in polynomial time can also be
     * solved by non-deterministic machine in polynomial time).
     * Informally, NP is set of decision problems which can be solved by a polynomial time via a “Lucky Algorithm”,
     * a magical algorithm that always makes a right guess among the given set of choices (Source Ref 1).
     *
     * NP-complete problems are the hardest problems in NP set.  A decision problem L is NP-complete if:
     * 1) L is in NP (Any given solution for NP-complete problems can be verified quickly, but there is no efficient known solution).
     * 2) Every problem in NP is reducible to L in polynomial time (Reduction is defined below).
     *
     * A problem is NP-Hard if it follows property 2 mentioned above, doesn’t need to follow property 1.
     * Therefore, NP-Complete set is also a subset of NP-Hard set.
     */
}
