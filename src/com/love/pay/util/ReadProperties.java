package com.love.pay.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	private InputStream in = null;
	
	public Properties ReadProps(String address){
	  Properties props = new Properties();
	  String url = "/"+this.getClass().getClassLoader().getResource(address).toString().substring(6);
	  url = url.replace("%20", " ");
	  in = null;
	  try {
	   in = new BufferedInputStream(new FileInputStream(url));
	   props.load(in);
	  } catch (FileNotFoundException e1) {
	   e1.printStackTrace();
	  } catch (IOException e1) {
	   e1.printStackTrace();
	  }
	  return props;
	}
	
	public void close(){
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
