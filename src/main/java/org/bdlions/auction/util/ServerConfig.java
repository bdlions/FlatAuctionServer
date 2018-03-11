package org.bdlions.auction.util;

import java.io.IOException;

/**
 *
 * @author nazmul hasan
 */
public class ServerConfig extends PropertyProvider{
    private static ServerConfig instance;
    public static final String SERVER_BASE_ABS_PATH      = "SERVER_BASE_ABS_PATH";
    
    private ServerConfig(String fileName) throws IOException {
        super(fileName);
    }
    
    public static ServerConfig getInstance(){
        try{
            if(instance == null){
                instance = new ServerConfig("server.properties");
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return instance;
    }
    
    @Override
    public String get(String key){
        return super.get(key);
    }
}
