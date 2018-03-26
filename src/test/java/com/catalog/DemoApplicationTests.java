package com.catalog;

import com.catalog.business.titleProcessor.SingleTitleProcessor;
import com.catalog.model.entities.*;
import com.catalog.service.GenreService;
import com.catalog.service.TypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	RawNames rawName;

	@Autowired
	TypeService typeService;

	@Autowired
	GenreService genreService;

	@Autowired
	private HttpSession httpSession;


	@Value( "${apikey}" )
	private static String apiKey;

	@Value( "${apiUrl}" )
	private static String apiUrl;

	HashMap<String, String> typeMap = new HashMap<String, String>();
	HashMap<String, String> genreMap = new HashMap<String, String>();

	@Before
	public void setUp() {
		rawName = new RawNames();
		rawName.setIDname(1034);
		rawName.setName("X-Men Days Of Future Past 2014 1080p BDRip x264 AAC KiNGDOM");
		rawName.setType("folder");
		rawName.setLocation("d:\\filmovi\\x-men days of future past 2014 1080p bdrip x264 aac kingdom");
		rawName.setLastAdded(1);

		// initialize types map
		ArrayList<Type> types = typeService.findAll();
		for( Type type : types ){
			typeMap.put(String.valueOf(type.getIDtype()), type.getName());
		}

		// initialize genres map
		for(Genre genre : genreService.findAll()){
			genreMap.put(genre.getName(), String.valueOf(genre.getIDgenre()));
		}

	}

	@Test
	public void testIncrement(){

		//System.out.println(100/)


	}

	//@Test
	public void contextLoads() {

		Collection<Callable<Map<RawNames, Title>>> tasks = new ArrayList<>();
		tasks.add( new SingleTitleProcessor(rawName, typeMap,genreMap,apiKey,apiUrl, httpSession, 10 ));
		ExecutorService executor = Executors.newFixedThreadPool(20);

		try {
			List<Future<Map<RawNames, Title>>> results = executor.invokeAll(tasks);

			for(Future<Map<RawNames, Title>> result : results) {
				Map<RawNames, Title> title = (HashMap) result.get();
				for( Map.Entry<RawNames, Title> set : title.entrySet() ){

					if( set.getValue() != null ){

						//insert new title
						set.getValue().toString();
						//titleRepository.save(set.getValue());
					}
					else{
						//insert into not inserted
						System.out.println("No data found!");
						RawNames rawName = set.getKey();
						NotInserted noIns = new NotInserted();
						noIns.setIDfilm(Integer.valueOf(rawName.getIDname()));
						noIns.setLocation(rawName.getLocation());
						noIns.setName(rawName.getName());
						noIns.setType(rawName.getType());
						//notInsertedService.save(noIns);
					}
				}
			}

		}
	 	catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
		}

		executor.shutdown();
	}

}
