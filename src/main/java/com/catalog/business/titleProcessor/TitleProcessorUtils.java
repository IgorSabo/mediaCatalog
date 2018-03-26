package com.catalog.business.titleProcessor;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gile on 2/11/2018.
 */

public class TitleProcessorUtils {


    public static void main(String[] args){

    }

    public static String getHtml(String url) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpClient httpClient = null;

        if (httpClient == null) {
            httpClient = new HttpClient();
        }
        BufferedReader br = null;

        BufferedWriter bw = null;

        StringBuffer html = new StringBuffer();
        String temp;
        GetMethod method;
        try {
            method = new GetMethod(url);
            method.setFollowRedirects(false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        HttpMethodParams params = new HttpMethodParams();

        params.setParameter(
                HttpMethodParams.USER_AGENT,
                "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.0.8) Gecko/2009032712 Ubuntu/8.10 (intrepid) Firefox/3.0.8");

        method.setParams(params);

        try {

            int statusCode = httpClient.executeMethod(method);

            if (statusCode == 200) {
                br = new BufferedReader(new InputStreamReader(
                        method.getResponseBodyAsStream(), "UTF8"));
                while ((temp = br.readLine()) != null) {
                    html.append(temp).append("\n");
                }

                return html.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public static String extractYear(String name) {
        String year = "";
        Pattern yearPattern = Pattern.compile(".+?(\\d{4}).+?");
        Matcher m = yearPattern.matcher(name);
        if (m.find()) {
            if (!m.group(1).equals("1080")) {
                year = m.group(1);
            }
        }

        return year;
    }

    public static String extractQuality(String name) {
        String quality = "";
        if (name.toLowerCase().contains("720p")) {
            quality = "720p";
        }
        if (name.toLowerCase().contains("1080p")) {
            quality = "1080p";
        }
        if (name.toLowerCase().contains("dvdrip") || name.toLowerCase().contains("dvd rip") || name.toLowerCase().contains("dvd-rip")) {
            quality = "DVD rip";
        }
        if (name.toLowerCase().contains("2160p")) {
            quality = "2160p";
        }

        return quality;
    }


}
