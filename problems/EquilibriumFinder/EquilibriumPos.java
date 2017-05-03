import java.io.*;
import java.util.*;

public class EquilibriumPos {
	public static void main(String[] arr) throws FileNotFoundException {
		int[] input;
		Scanner sc = new Scanner(new File(arr[0]));
		int len = sc.nextInt();
		input = new int[len];
		for (int i = 0; i < len; i++) {
			input[i] = sc.nextInt();
		}
		System.out.println(findEquilibrium(input));
	}

	public static int findEquilibrium(int[] input) {
		int sum = 0;
		for(int i = 0; i < input.length; i++) {
			sum += input[i];
		}
		int leftSum = 0, rightSum = 0;
		for(int i = 0; i < input.length; i++) {
			rightSum = sum - leftSum - input[i];
			if(leftSum == rightSum) {
				return i;
			}
			leftSum += input[i];
		}
		return -1;
	}
}
