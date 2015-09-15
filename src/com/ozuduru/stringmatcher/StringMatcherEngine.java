/*
*File: StringMatherEngine.java
*Author: Onur Ozuduru
*   e-mail: onur.ozuduru { at } gmail.com
*   github: github.com/onurozuduru
*   twitter: twitter.com/OnurOzuduru
*
*License: This work is licensed under the
*   Creative Commons Attribution-ShareAlike 4.0 International License.
*   To view a copy of this license,
*   visit http://creativecommons.org/licenses/by-sa/4.0/.
*/

package com.ozuduru.stringmatcher;

import java.util.ArrayList;

// It includes two methods for string matching which are Finite Automaton Matcher and Naive String Matcher.
public class StringMatcherEngine {
	
	// Helper method for Finite Automaton Matcher.
	// It takes 2 strings "a" and "b" then it controls that the shortest one is suffix of the other one. 
	private boolean isSuffix(String a, String b) {
		
		int lenA = a.length();
		int lenB = b.length();
		if(lenA == 0 || lenB == 0)
			return false;
		if(lenA >= lenB)
			return a.endsWith(b);
		if(lenB < lenA)
			return b.endsWith(a);
		return true;
	}

	// Helper method for Finite Automaton Matcher.
	// It is actually implementation of Compute-Transition-Function(P, Sigma).
	// It takes String P and Character array sigma.
	public StateTable<Character> generateTransitionTable(String P, Character[] sigma) {
		int m = P.length();
		
		// Create new empty StateTable to return.
		StateTable<Character> delta = new StateTable<Character>(m + 1);
		
		for (int q = 0; q <= m; ++q) {
			for (Character a : sigma) {// foreach character from sigma.
				int k = Math.min(m + 1, q + 2);
				//There is no until loop for Java that is why we used while loop.
				--k;
				// Decrement k until Pk is suffix of PqA.
				while (!a.equals(P.charAt(k - 1)) ||
						 !isSuffix(P.substring(0, k), P.substring(0, q) + a)) {
					--k;
					
					//Do not let k goes negative.
					if (k == 0)
						break;
				}
				
				// Assign k to delta[q, a]
				delta.set(q, a, k);
			}
		}
		return delta;
	}

	// Implementation of Finite-Automata-Matcher.
	// int m is length of the path.
	public ArrayList<Integer> finiteAutomatonMatcher(String T, StateTable<Character> delta, int m) {
		// Initialize new arraylist.
		ArrayList<Integer> result = new ArrayList<>();
		int n = T.length();
		int q = 0;
		// i starts from 0 instead 1, because array indexes starts from 0 at Java.
		for (int i = 0; i < n; ++i) {
			char c = T.charAt(i);
			q = delta.get(q, c);
			if (q == m)
				result.add(i - m + 1); // It adds 1 because indexes starts from 0.
		}
		return result;
	}

	// Implementation of Naive-String-Matching
	// T is Text
	// P is Path
	public ArrayList<Integer> naiveStringMatcher(String T, String P) {
		int n = T.length();
		int m = P.length();
		ArrayList<Integer> result = new ArrayList<>();

		for (int s = 0; s <= n - m; ++s) {
			// String's equals method is used to control two strings are same or not.
			// And String's substring method is used to get a string from sth index to (s+m-1)th index.
			// substring does not takes endIndex to return string.
			if (P.equals(T.substring(s, s + m)))
				result.add(s);
		}
		return result;
	}
}
