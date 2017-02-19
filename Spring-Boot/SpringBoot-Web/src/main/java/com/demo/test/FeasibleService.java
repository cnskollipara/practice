package com.demo.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class FeasibleService {
	
	public int findSolution(File file) {
		System.out.println("Inside controller");
		Scanner sc;
		int output = -1;
		try {
			sc = new Scanner(file);
			int T = sc.nextInt();
			int n = sc.nextInt();
			int[] satisfaction = new int[n];
			int[] time = new int[n];
			for(int i = 0; i < n; i++) {
				satisfaction[i] = sc.nextInt();
				time[i] = sc.nextInt();
			}
			System.out.println("-------------------------------Inputs-------------------------------\n");
			System.out.println("Total time in minutes : " + T);
			System.out.println("Items in the list : " + n);
			System.out.println("Satisfaction\tTime_Taken");
			for(int i = 0; i < n; i++) {
				System.out.println(satisfaction[i] + "\t\t" + time[i]);
			}
			output = maxSatisfaction(T, time, satisfaction, n);
			System.out.println("Feasible Solution : " + output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return output;
		
	}

	private int maxSatisfaction(int T, int[] time, int[] satisfaction, int n) {
		// Base Case
	    if (n == 0 || T == 0)
	        return 0;
	      
	    // If time for the nth item is more than total time T, then
	    // this item cannot be included in the optimal solution
	    if (time[n-1] > T)
	       return maxSatisfaction(T, time, satisfaction, n-1);
	      
	    // Return the maximum of two cases: 
	    // (1) nth item included 
	    // (2) not included
	    else return max( satisfaction[n-1] + maxSatisfaction(T-time[n-1], time, satisfaction, n-1),
	                     maxSatisfaction(T, time, satisfaction, n-1)
	                      );
	}

	private int max(int a, int b) {
		return (a > b) ? a : b;
	}

}
