package com.catalog.business.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FileLister {
	
	public static void main(String[] args)
	{
		Set<String> set = new HashSet<String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/Gile/Desktop/sts-bundle/sts-3.7.0.RELEASE/notFound.txt"));
			String line="";
			while((line=br.readLine())!=null)
			{
				//System.out.println(line);
				set.add(line);
			}
			Iterator it = set.iterator();
			int i=0;
			while(it.hasNext())
			{
				System.out.println(it.next());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
