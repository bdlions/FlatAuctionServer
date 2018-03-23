/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.auction.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alamgir
 */
public class URIBuilder {
    private final List<URIParam> parameters = new ArrayList<>();
    private final String url;

    public URIBuilder(String url) {
        this.url = url;
    }
    
    @Override
    public String toString(){
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(url);
            builder.append("?");
            int count = 0;
            for (URIParam parameter : parameters) {
                if(count > 0 ){
                    builder.append("&");
                }
                count ++;
                builder.append(parameter.key);
                builder.append("=");
                builder.append(URLEncoder.encode(parameter.value, "UTF-8"));
            }
            return builder.toString();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public URIBuilder addParameter(String key, String value){
        parameters.add(new URIParam(key, value));
        return this;
    }
    
    private class URIParam{
        private final String key;
        private final String value;

        public URIParam(String key, String value) {
            this.key = key;
            this.value = value;
        }
        
    }
}
