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
		
		
		genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "����", "���", "���", "����", "����", "�˸�", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯", "13����", "18����", "38����")); special = true;
		//genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯", "13����", "18����", "38����")); special = true;
		//genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯", "13����", "18����", "38����"));
		//genealogy = new ArrayList<>(Arrays.asList("�Ѳ�", "�β�", "����", "�ײ�", "�ټ���", "������", "�ϰ���", "������", "��ȩ��", "����", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "�嶯"));
		
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