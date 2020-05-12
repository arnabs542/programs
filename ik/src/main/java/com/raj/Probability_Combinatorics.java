package com.raj;

public class Probability_Combinatorics {
/**
 * ==== Mathematical Probability ====
 * https://www.geeksforgeeks.org/mathematics-probability/
 * OR means union U
 * AND means intersection ∩
 *
 * Theorems:
 * General – Let A, B, C are the events associated with a random experiment, then
 * P(A∪B) = P(A) + P(B) – P(A∩B)
 * P(A∪B) = P(A) + P(B) if A and B are mutually exclusive
 * P(A∪B∪C) = P(A) + P(B) + P(C) – P(A∩B) – P(B∩C)- P(C∩A) + P(A∩B∩C)
 * P(A∩B’) = P(A) – P(A∩B)
 * P(A’∩B) = P(B) – P(A∩B)
 * Extension of Multiplication Theorem – Let A1, A2, ….., An are n events associated with a random experiment, then
 * P(A1∩A2∩A3 ….. An) = P(A1)P(A2/A1)P(A3/A2∩A1) ….. P(An/A1∩A2∩A3∩ ….. ∩An-1)
 *
 * Total Law of Probability – Let S be the sample space associated with a random experiment and E1, E2, …, En be n mutually
 * exclusive and exhaustive events associated with the random experiment. If A is any event which occurs with E1 or E2 or … or En, then
 * P(A) = P(E1)P(A/E1) + P(E2)P(A/E2) + ... +  P(En)P(A/En)
 *
 * Examples:
 *
 * # A bag contains 10 oranges and 20 apples out of which 5 apples and 3 oranges are defective.
 * If a person takes out two at random, what is the probability that either both are good or both are apples?
 * [Simple]
 * P(both_good OR both_apple) = P(AUB) = P(A) + P(B) – P(A∩B)
 * both_good = 30-8/30 = 22/30 for 1st draw * 21/29 for 2nd draw = 22/30 * 21/29 = 0.53
 * both_apple= 20/30 for 1st draw * 19/29 for 2nd draw = 20/30 * 19/29 = 0.44
 * both_good AND both_apple = 15/30 * 14/29 = 0.24
 * Answer = 0.53 + 0.44 - 0.24 = 0.73
 *
 * [n choose k method]
 * There are 8 defective items and 22 are good, Out of 22 good items, two can be can drawn in 22 C 2 ways = P(B) = 22 C 2 / 30 C 2
 * There are 20 apples, out of which 2 can drawn in 20 C 2 ways = P(A) = 20 C 2 / 30 C 2
 * Since there are 15 items which are good apples, out of which 2 can be selected in 15C2 ways = P(A∩B) = 15 C 2 / 30 C 2
 * Substituting the values of P(A), P(B) and P(A∩B) in (i)
 * Required probability is = (20C2/30C2) + (22C2/30C2) – (15C2/30C2)
 *                         = 20*19/(30*29) ... same as in Simple + ........ = 0.73
 *
 *
 * # The probability that a person will get an electric contract is 2/5 and probability that he will not get plumbing
 * contract is 4/7. If the probability of getting at least one contact is 2/3, what is the probability of getting both ?
 * Consider the two events:
 * A = Person gets electric contract
 * B = Person gets plumbing contract
 * We have,
 * P(A) = 2/5
 * P(B’) = 4/7
 * P(A∪B) = 2/3
 * Now,
 * P(A∩B) = P(A) + P(B) – P(A∪B)
 * = (2/5) + (1 – 4/7) – (2/3) = 17/105
 *
 *
 * # A bag contains 3 black balls and 4 red balls .A second bag contains 4 black balls and 2 red balls .One bag is
 * selected at random. From the selected bag, one ball is drawn. Find the probability that the ball drawn is red.
 * Using total law of probability, we have
 * P(Red_any) = P(bag1).P(red from bag1) + P(bag2).P(red from bag2)
 * = (1/2)(4/7) + (1/2)(2/6) = 19/42
 *
 *
 * # In a bulb factory, three machines namely A, B, C produces 25%, 35% and 40% of the total bulbs respectively.
 * Of their output, 5, 4 and 2 percent are defective bulbs respectively. A bulb is drawn is drawn at random from products.
 * What is the probability that bulb drawn is defective?
 * Using total law of probability, we have
 * P(A) = P(E1)P(A/E1) + P(E2)P(A/E2) + P(E3)P(A/E3)
 * = (25/100)(5/100) + (35/100)(4/100) + (40/100)(2/100) = 0.0345
 *
 * ==== Conditional Probability ====
 * https://www.geeksforgeeks.org/conditional-probability/
 * Conditional probability P(A | B) indicates the probability of event ‘A’ happening given that event B happened.
 * P(A | B) = P(A ∩ B) / P(B)
 *
 * # In a batch, there are 80% C programmers, and 40% are Java and C programmers. What is the probability that a
 * C programmer is also Java programmer?
 * Let A --> Event that a student is Java programmer
 *     B --> Event that a student is C programmer
 *     P(A|B) = P(A ∩ B) / P(B)
 *            = (0.4) / (0.8) = 0.5
 *
 */
}
