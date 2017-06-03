package com.catalog.business.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Created by Gile on 5/24/2017.
 */
public class DownloadDump {

    public static void main(String[] args){

        try {
            String remoteServerUrl = "http://files.tmdb.org/";
            URL website = new URL("http://files.tmdb.org/");
            File index = new File("src/main/resources/dumps/index.txt");



            //fetching index
            downloadFileFromRemoteHost(remoteServerUrl, index);

            HashMap<String, String> dumpDates = (HashMap<String, String>) processIndex(index);

            File dumpFolder = new File("src/main/resources/dumps");
            File[] listOfFiles = dumpFolder.listFiles();
            boolean alreadyHaveLatestMoviesDump = false;
            boolean alreadyHaveLatestSeriesDump = false;

            for(File f : listOfFiles){
                if(f.getName().equals("movie_ids_"+dumpDates.get("lastMoviesIdDumpDate")+".json")){
                    alreadyHaveLatestMoviesDump = true;
                }

                if(f.getName().equals("tv_series_ids_"+dumpDates.get("lastSeriesIdDumpDate")+".json")){
                    alreadyHaveLatestSeriesDump = true;
                }
            }

            if(!alreadyHaveLatestMoviesDump){
                File moviesDBdumpCompressed = new File("src/main/resources/dumps/movie_ids_"+dumpDates.get("lastMoviesIdDumpDate")+".json.gz");
                File moviesDBdump = new File("src/main/resources/dumps/movie_ids_"+dumpDates.get("lastMoviesIdDumpDate")+".json");
                String remoteUrl = "http://files.tmdb.org/p/exports/movie_ids_"+dumpDates.get("lastMoviesIdDumpDate")+".json.gz";

                //fetching movies id
                downloadFileFromRemoteHost(remoteUrl, moviesDBdumpCompressed);

                if(remoteUrl.endsWith(".gz")){
                    gunzipIt(moviesDBdumpCompressed.getPath(), moviesDBdump.getPath());
                    System.out.println("Decompressed :"+moviesDBdumpCompressed.getName());
                    moviesDBdumpCompressed.delete();
                }
            }
            else{
                System.out.println("already have latest movies id dump file...");
            }

            if(!alreadyHaveLatestSeriesDump){
                File seriesDBdumpCompressed = new File("src/main/resources/dumps/tv_series_ids_"+dumpDates.get("lastSeriesIdDumpDate")+".json.gz");
                File seriesDBdump = new File("src/main/resources/dumps/tv_series_ids_"+dumpDates.get("lastSeriesIdDumpDate")+".json");
                String remoteUrl = "http://files.tmdb.org/p/exports/tv_series_ids_"+dumpDates.get("lastSeriesIdDumpDate")+".json.gz";

                //fetching movies id
                downloadFileFromRemoteHost(remoteUrl, seriesDBdumpCompressed);

                if(remoteUrl.endsWith(".gz")){
                    gunzipIt(seriesDBdumpCompressed.getPath(), seriesDBdump.getPath());
                    System.out.println("Decompressed :"+seriesDBdumpCompressed.getName());
                    seriesDBdumpCompressed.delete();
                }
            }
            else{
                System.out.println("already have latest tv shows id dump file...");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static Map<String, String> processIndex(File index){

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_yyyy");
        String formattedString = localDate.format(formatter);
        Map<String, String> dumpDates = new HashMap<String, String>();

        BufferedReader br;

        try{

            br = new BufferedReader(new FileReader(index));
            StringBuffer sb = new StringBuffer();
            String line;

            while((line = br.readLine()) != null){
                sb.append(line);
            }

            Pattern pat = Pattern.compile("<Key>p/exports/movie_ids_(.+?)\\.json\\.gz</Key>");
            Matcher m = pat.matcher(sb.toString());

            //determine the date of movies last dump
            List<String> allMovieMatches = new ArrayList<String>();
            while(m.find()){
               allMovieMatches.add(m.group(1));
            }

            int minusDays = 0;
            boolean running = true;
            while(running){
                for(String dumpDate : allMovieMatches){
                    if(dumpDate.equals(formattedString)){
                        dumpDates.put("lastMoviesIdDumpDate", dumpDate);
                        running = false;
                    }
                }
                minusDays++;
                formattedString = localDate.minusDays(minusDays).format(formatter);
            }

            //determine the date of tv shows last dump
            formattedString = localDate.format(formatter);
            pat = Pattern.compile("<Key>p/exports/tv_series_ids_(.+?)\\.json\\.gz</Key>");
            m = pat.matcher(sb.toString());

            List<String> allSerieseMatches = new ArrayList<String>();
            while(m.find()){
                allSerieseMatches.add(m.group(1));
            }
            minusDays = 0;
            running = true;
            while(running){
                for(String dumpDate : allSerieseMatches){
                    if(dumpDate.equals(formattedString)){
                        dumpDates.put("lastSeriesIdDumpDate", dumpDate);
                        running = false;
                    }
                }
                minusDays++;
                formattedString = localDate.minusDays(minusDays).format(formatter);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return dumpDates;
    }

    public static void downloadFileFromRemoteHost(String remoteUrl, File downloadToFile){
        try{
            URL website = new URL(remoteUrl);
            System.out.println("Downloading: "+downloadToFile.getName());
            FileUtils.copyURLToFile(website, downloadToFile);
            System.out.println("Download complete for "+downloadToFile.getName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void gunzipIt(String INPUT_GZIP_FILE, String OUTPUT_FILE){

        byte[] buffer = new byte[1024];
        GZIPInputStream gzis = null;
        FileOutputStream out = null;
        System.out.println("Decompressing :"+INPUT_GZIP_FILE);
        try{

            gzis = new GZIPInputStream(new FileInputStream(INPUT_GZIP_FILE));

            out = new FileOutputStream(OUTPUT_FILE);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

        }catch(IOException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                gzis.close();
                out.close();
            }
            catch(Exception e){}
        }
    }
}
