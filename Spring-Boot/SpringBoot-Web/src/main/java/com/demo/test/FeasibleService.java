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
        int i, w;
        int K[][] = new int[n+1][T+1];
        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= T; w++) {
                if (i==0 || w==0)
                    K[i][w] = 0;
                else if (time[i-1] <= w)
                    K[i][w] = max(satisfaction[i-1] + K[i-1][w-time[i-1]],  K[i-1][w]);
                else
                    K[i][w] = K[i-1][w];                                                                                                     
               }
         }
         return K[n][T];
	}

	private int max(int a, int b) {
		return (a > b) ? a : b;
	}

}
