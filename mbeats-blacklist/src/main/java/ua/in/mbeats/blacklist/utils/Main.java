package ua.in.mbeats.blacklist.utils;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlackList bl = new BlackList();
		
		bl.changeList("3423;234245");
		
		bl.allMsisdns();
		
		System.out.println(bl.getList());
	}

}
