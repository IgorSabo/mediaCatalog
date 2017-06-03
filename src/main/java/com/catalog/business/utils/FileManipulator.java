package com.catalog.business.utils;

import com.catalog.model.entities.Title;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManipulator {
	public FileManipulator() {
		// TODO Auto-generated constructor stub
	}
	public static Title renameFile(Title wiredTitle) {
		//file:   g:\filmovi\HDTV-Shows.net_2012_720p.mkv
		//folder: i:\filmovi i serije\Angel.s01-05.Complete.dvdrip.x264
		Pattern pat = Pattern.compile("(.+)\\\\(.+)");
		Matcher m = pat.matcher(wiredTitle.getLocation());
		String loc = "";
		String extension = "";
		String newName = ""; 
		if (m.find()) {
			System.out.println("location:"+m.group(1)+", old name:"+m.group(2));
			loc = m.group(1);
			String oldName = m.group(2);
			
			Pattern pat1=Pattern.compile("(.+)\\.(.+)"); 
			Matcher m1 = pat1.matcher(oldName);
			if(m1.find()){
				extension = m1.group(2);
			}
			newName = wiredTitle.getImdbTitle();
			File originalName = new File(wiredTitle.getLocation());
			
			// rename file on hard drive
			if(originalName.isFile())
			{
				//if current object is file
				originalName.renameTo(new File(loc +"\\"+ newName.replace(":", " ") + "." + extension));
				wiredTitle.setLocation(new File(loc +"\\"+ newName.replace(":", " ") + "." + extension).getAbsolutePath().replace("/", "\\"));
			}
			else
			{
				//if current object is folder
				originalName.renameTo(new File(loc +"\\"+ newName.replace(":", " ")));
				wiredTitle.setLocation(new File(loc +"\\"+ newName.replace(":", " ")).getAbsolutePath().replace("/", "\\"));
			}
			System.out.println("New name: "+ newName + "." + extension); 
			System.out.println("New location: "+ new File(loc +"\\"+ newName + "." + extension).getAbsolutePath().replace("/", "\\"));
			wiredTitle.setRawName(newName);
		}
		//wiredTitle.setLocation(new File(loc +"\\"+ newName.replace(":", " ") + "." + extension).getAbsolutePath().replace("/", "\\"));
		return wiredTitle;
	}


	public boolean deleteFile(String location){
		boolean result=true;

		try{
			File f = new File(location);

			//deleting all files inside this dir(if this is a dir)
			if(f.isDirectory()){
				for(File tmp : f.listFiles()){
					tmp.delete();
				}
			}

			f.delete();
		}
		catch(Exception e){
			e.printStackTrace();
			result=false;
		}

		return result;

	}
	
	
	
		
	
}
