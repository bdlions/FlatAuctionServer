/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions;

import org.bdlions.auction.db.HibernateUtil;
import org.bdlions.auction.util.ClientRequestHandler;
import org.bdlions.session.ISessionManager;
import org.bdlions.transport.channel.provider.ChannelProviderImpl;
import org.bdlions.util.handler.request.IClientRequestHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class JarRunner{

    private static ChannelProviderImpl channelProviderImpl = null;
    public static void main(String[] args){
        SpringApplication.run(JarRunner.class, args);
        try
        {
            IClientRequestHandler requestHandler = ClientRequestHandler.getInstance();
            ISessionManager sessionManager = ClientRequestHandler.getInstance().getSessionManager();
            channelProviderImpl = new ChannelProviderImpl(requestHandler, sessionManager);
            channelProviderImpl.start();
            HibernateUtil.getSession();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        } 
        
        System.out.println("Server started");
    }

}
