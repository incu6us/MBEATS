package ua.in.mbeats.blacklist.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ua.in.mbeats.blacklist.utils.BlackList;

@Path("/blacklist")
public class BlackListRest {

	@GET
	@Path("/{method}")
	@Produces(MediaType.APPLICATION_JSON)
	public BlackList excuteGetDelete(@PathParam("method") String method){
		BlackList bl = new BlackList();
		
		if(method.equals("get")){
			bl.allMsisdns();
		}else if(method.equals("delete")){
			bl.deleteList();
			bl.allMsisdns();
		}
		
		return bl;
	}
	
	@GET
	@Path("/{method}/{list}")
	@Produces(MediaType.APPLICATION_JSON)
	public BlackList executeAddChange(@PathParam("method") String method,
			@PathParam("list") String list){
		BlackList bl = new BlackList();
		
		if(method.equals("add")){
			bl.addToList(list);
		}else if(method.equals("delete")){
			bl.deleteMSISDN(list);
		}
		
		bl.allMsisdns();
		
		return bl;
	}
}
