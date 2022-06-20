package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public static long N = 40000000; 
	public static int taskNum = 10; //tasks that each thread will have. You can devide tasks so that if one thread is finished and another is busy, that thread can fork the task.  
	
	private static final ArrayList<Card> deck = new ArrayList<>(20);
	private static ArrayList<String> genealogy;
	private static boolean special = true;
	
	static {
		for(int i = 1; i < 21; i++) {
			deck.add(new Card(i, 0));
			deck.add(new Card(i, 1));
		}
		
		
		genealogy = new ArrayList<>(Arrays.asList("茄昌", "滴昌", "技昌", "匙昌", "促几昌", "咯几昌", "老蚌昌", "咯袋昌", "酒醛场", "癌坷", "技氟", "厘荤", "厘绘", "备绘", "刀荤", "舅府", "1动", "2动", "3动", "4动", "5动", "6动", "7动", "8动", "9动", "厘动", "13堡动", "18堡动", "38堡动")); special = true;
		//genealogy = new ArrayList<>(Arrays.asList("茄昌", "滴昌", "技昌", "匙昌", "促几昌", "咯几昌", "老蚌昌", "咯袋昌", "酒醛场", "癌坷", "1动", "2动", "3动", "4动", "5动", "6动", "7动", "8动", "9动", "厘动", "13堡动", "18堡动", "38堡动")); special = true;
		//genealogy = new ArrayList<>(Arrays.asList("茄昌", "滴昌", "技昌", "匙昌", "促几昌", "咯几昌", "老蚌昌", "咯袋昌", "酒醛场", "癌坷", "1动", "2动", "3动", "4动", "5动", "6动", "7动", "8动", "9动", "厘动", "13堡动", "18堡动", "38堡动"));
		//genealogy = new ArrayList<>(Arrays.asList("茄昌", "滴昌", "技昌", "匙昌", "促几昌", "咯几昌", "老蚌昌", "咯袋昌", "酒醛场", "癌坷", "1动", "2动", "3动", "4动", "5动", "6动", "7动", "8动", "9动", "厘动"));
		
	}
	
	public static void main(String[] args) {

		final long n = N / (Runtime.getRuntime().availableProcessors() * taskNum);
		for(int i = 0; i < Runtime.getRuntime().availableProcessors() * taskNum; i++) {
			pool.execute(() -> {
				for(int j = 0; j < n; j++) {
					
				}
			});
		}
		
	}

}

class Card {
	
	public final int num;
	public final int type;
	
	public Card(int n, int t) { num = n; type = t; }
	
}