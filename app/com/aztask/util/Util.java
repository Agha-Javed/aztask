package com.aztask.util;

import java.util.List;
import com.aztask.vo.NearByDevice;

public class Util {
	
	
	public static String getWhereCluase(List<NearByDevice> nearByUsers){
		StringBuffer whereCluase=new StringBuffer("");
		for(NearByDevice nearByUser: nearByUsers){
			whereCluase.append("'"+nearByUser.getDevice_id()+"',");
		}
		if(whereCluase.length()>0)
		whereCluase.deleteCharAt(whereCluase.length()-1);
		
		return whereCluase.toString();
	}
	
	public static String getLikeClause(String columnName,String categories){
		
		StringBuffer likeCluase=new StringBuffer();
		for(String taskCategory : categories.split(";")){
			likeCluase.append(" "+columnName+" like '%"+taskCategory+"%' or");
		}
		likeCluase.delete(likeCluase.length()-2, likeCluase.length());
		return likeCluase.toString();
		
	}


}
