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
		
		
		genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "����", "���", "���", "����", "����", "�˸�", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯", "13����", "18����", "38����")); special = true;
		//genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯", "13����", "18����", "38����")); special = true;
		//genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯", "13����", "18����", "38����"));
		//genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯"));
		
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
