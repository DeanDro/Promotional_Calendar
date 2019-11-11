package dataAnalysisAndStorage;

import java.util.*;

public class LLData <PromoData>{
	
	private Node head;
	
	private class Node<PromoData>{
		private PromoData data;
		private Node next;
		private Node prev;
		
		public Node(PromoData input) {
			this.data = input;
		}
	}
	
	
}
