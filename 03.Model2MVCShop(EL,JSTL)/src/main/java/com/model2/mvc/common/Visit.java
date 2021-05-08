package com.model2.mvc.common;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Visit {

	public int getVisit(String fileName) throws Exception{
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String[] k = null;
		int i = 0;
		
		while(br.readLine() != null) {
			k = br.readLine().split(":");
			System.out.println("현재방문자수 : "+k);
			i = Integer.parseInt(k[1])+1;
		}
		br.close();
		return i;
	}
}
