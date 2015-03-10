package cn.cg.uestc.www;

import java.util.LinkedList;
import java.util.Queue;

public class TESTOne {

	public static void main(String[] args){
		
		Queue<String> test = new LinkedList<String>();
		test.offer("a");
		test.offer("b");
		test.offer("c");
		for(String i : test){
			System.out.println(i);
		}
		
		System.out.println("------我是分割线----");
		System.out.println(test);
	}
}
