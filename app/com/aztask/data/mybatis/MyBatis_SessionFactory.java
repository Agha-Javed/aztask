package com.aztask.data.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;

import com.aztask.util.Constants;

import play.Play;
import play.cache.Cache;

public class MyBatis_SessionFactory {

	private static SqlSessionFactory sqlSessionFactory;
	
	private MyBatis_SessionFactory() {}

	public static SqlSession openSession(){
		
		
		if (sqlSessionFactory==null || Cache.get(Constants.MYBATIS_SESSION_FACTORY)==null)
		{
			synchronized(MyBatis_SessionFactory.class)
			{
				if (sqlSessionFactory == null)
				{

					InputStream inputStream =MyBatis_SessionFactory.class.getResourceAsStream(Constants.MYBATIS_CONF_FILE_NAME);
					System.out.println("Resource:"+inputStream);
//					System.out.println("Resource:"+inputStream);

			//		InputStream inputStream =Play.application().resourceAsStream(Constants.MYBATIS_CONF_FILE_NAME);
					System.out.println("Resource:"+inputStream);


					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			    	Cache.set(Constants.MYBATIS_SESSION_FACTORY, sqlSessionFactory);
				}
			}            
		}

		return ((SqlSessionFactory)Cache.get(Constants.MYBATIS_SESSION_FACTORY)).openSession();
	}
	
}
