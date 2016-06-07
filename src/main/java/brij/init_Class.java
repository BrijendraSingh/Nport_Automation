package main.java.brij;

import java.io.IOException;

import frameDriver.FW_DriverScript;

public class init_Class {

	public static void main(String[] args) throws IOException{
		FW_DriverScript init = new FW_DriverScript();
		System.out.println("Selenium Test Started");
		init.init_FW();
		System.out.println("Selenium Test Finished");
	}
}
