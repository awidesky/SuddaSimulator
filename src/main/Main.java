package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Main {

	
	public static long N = 40000000; 
	
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
	
	public static boolean playGame(Pair p) {
		
		if(special) {
			
		}
		
		
		return true;
	}
	
	public static void main(String[] args) {

		long t = System.currentTimeMillis();
		
		long win = Stream.generate(() -> {
			int[] arr = new Random().ints(0, 20).distinct().limit(2).toArray();
			return new Pair(deck.get(arr[0]), deck.get(arr[1]));
		}).limit(N).parallel().map(Main::playGame).filter(Boolean::booleanValue).count();

		t = System.currentTimeMillis() - t;
		
		System.out.println(win + " / " + N + "(" + (100.0 * win/N) +"%)\nIn" + t + "ms");
		
	}

}

class Card {
	
	public final int num;
	public final int type;
	
	public Card(int n, int t) { num = n; type = t; }
	public String getGenealogy() {
		return "";
	}
}

class Pair {
	public Card a;
	public Card b;
	public Pair(Card a, Card b) {
		this.a = a;
		this.b = b;
	}
}
