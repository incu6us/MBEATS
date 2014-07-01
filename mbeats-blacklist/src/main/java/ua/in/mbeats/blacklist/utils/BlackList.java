package ua.in.mbeats.blacklist.utils;

import org.apache.log4j.Logger;

public class BlackList {
	private static Logger LOGGER = Logger.getLogger(BlackList.class);
	
	private String list;

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void addToList(String list){
		DBBlacklistDAO db = new DBBlacklistDAO("root", "666666");
		LOGGER.debug("List: "+list);
		
		db.connect();
		
		String[] newList = list.split(";");
		
		for(String msisdn:newList){
			db.addValueForList(msisdn);
			LOGGER.debug("MSISDN: "+msisdn);
		}
		db.disconnect();
		
	}
	
	public void changeList(String list){
		DBBlacklistDAO db = new DBBlacklistDAO("root", "666666");
		
		db.connect();
		
		String[] newList = list.split(";");
		
		db.deleteValueFromList();
		for(String msisdn:newList){
			db.addValueForList(msisdn);
		}
		
		db.disconnect();
	}
	
	public void deleteList(){
		DBBlacklistDAO db = new DBBlacklistDAO("root", "666666");
		
		db.connect();
		
		db.deleteValueFromList();
		
		db.disconnect();
	}
	
	public void deleteMSISDN(String msisdn){
		DBBlacklistDAO db = new DBBlacklistDAO("root", "666666");
		
		db.connect();
		
		db.deleteValueFromListWithID(msisdn);
		
		db.disconnect();
	}
	
	public void allMsisdns(){
		String list = "";

		DBBlacklistDAO db = new DBBlacklistDAO("root", "666666");
		db.connect();
		
		list = db.getList();
		
		db.disconnect();
		
		setList(list);
	}
}
