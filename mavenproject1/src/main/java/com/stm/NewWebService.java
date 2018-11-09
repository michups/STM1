/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author micha
 */
@WebService(serviceName = "NewWebService")
public class NewWebService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
        /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "test")
    public String test(@WebParam(name = "name") String txt) {
       String base64Image = "";
       
	ClassLoader classLoader = getClass().getClassLoader(); 
	File file=null;
        try {
            file = new File(classLoader.getResource("test.PNG").toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
	try (FileInputStream imageInFile = new FileInputStream(file)) {
		// Reading a Image file from file system
		byte imageData[] = new byte[(int) file.length()];
                System.out.println(file.length());
		imageInFile.read(imageData);
		base64Image = Base64.getEncoder().encodeToString(imageData);
	} catch (FileNotFoundException e) {
		System.out.println("Image not found" + e);
	} catch (IOException ioe) {
		System.out.println("Exception while reading the Image " + ioe);
	}
	return base64Image;
    }
}
