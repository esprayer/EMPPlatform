/**
 * TODO
 */
package com.put.util;

import java.util.*;
/**
 * @author aeolianer
 * @created  2007-3-14
 *
 */
public class AlertMessage {

	private List<String> messages;
	
	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		if(this.messages==null){
			messages = new LinkedList();
		}
		return messages;
	}
	
	public void addMessage(String message){		
//		log4.info("message="+message+" messages size="+messages.size());
		this.getMessages();
		
		messages.add(message);			
	}

	public void clear(){
		
//		log4.info("--clear--");
		this.messages=null;
	}
	
	
}
