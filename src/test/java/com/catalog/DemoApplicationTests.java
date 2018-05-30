package com.catalog;

import com.catalog.business.assemblers.TitleJsonResponseAssembler;
import com.catalog.business.assemblers.TitleRatingsAssembler;
import com.catalog.business.jobs.JobType;
import com.catalog.business.titleProcessor.SingleTitleProcessor;
import com.catalog.conf.AppConfiguration;
import com.catalog.conf.RepositoryConfiguration;
import com.catalog.config.TestConfig;
import com.catalog.model.entities.*;
import com.catalog.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {AppConfiguration.class, RepositoryConfiguration.class, TestConfig.class })
public class DemoApplicationTests {

	RawNames rawName;

	@Autowired
	TypeService typeService;

	@Autowired
	GenreService genreService;

	@Autowired
	TitleService titleService;

	@Autowired
	private HttpSession httpSession;


	private static String apiKey = "8d51e39d";


	private static String apiUrl = "http://www.omdbapi.com/?t=";

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private TitleJsonResponseAssembler titleJsonResponseAssembler;

	@Autowired
	private TitleRatingsAssembler titleRatingsAssembler;

	@Autowired
	private TitleJsonResponseService titleJsonResponseService;

	@Autowired
	private TitleRatingService titleRatingService;

	@Autowired
	ScheduledJobService scheduledJobService;

	private String json = null;

	HashMap<String, String> typeMap = new HashMap<String, String>();
	HashMap<String, String> genreMap = new HashMap<String, String>();



	@Before
	public void setUp() {
		rawName = new RawNames();
		rawName.setIDname(2218);
		rawName.setName("The Breadwinner 2017 BRRip XviD AC3-EVO");
		rawName.setType("folder");
		rawName.setLocation("j:\\filmovi\\The Breadwinner.2017.BRRip.XviD.AC3-EVO");
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

		json = "{\"Metascore\":\"76\",\n" +
				"\"BoxOffice\":\"$228,056\",\n" +
				"\"Website\":\"N\\/A\",\n" +
				"\"imdbRating\":\"7.5\",\n" +
				"\"imdbVotes\":\"950\",\n" +
				"\"Ratings\":[\n" +
				"\t\t{\"Value\":\"7.5\\/10\",\"Source\":\"\n" +
				"\t\t\"},\n" +
				"\t\t{\"Value\":\"94%\",\"Source\":\"Rotten Tomatoes\"},\n" +
				"\t\t{\"Value\":\"76\\/100\",\"Source\":\"Metacritic\"}],\n" +
				"\"Runtime\":\"94 min\",\n" +
				"\"Language\":\"English\",\n" +
				"\"Rated\":\"PG-13\",\n" +
				"\"Production\":\"N\\/A\",\n" +
				"\"Released\":\"17 Nov 2017\",\n" +
				"\"imdbID\":\"tt3901826\",\n" +
				"\"Plot\":\"A headstrong young girl in Afghanistan disguises herself as a boy in order to provide for her family.\",\n" +
				"\"Director\":\"Nora Twomey\",\n" +
				"\"Title\":\"The Breadwinner\",\n" +
				"\"Actors\":\"Saara Chaudry, Soma Chhaya, Noorin Gulamgaus, Laara Sadiq\",\n" +
				"\"Response\":\"True\",\n" +
				"\"Type\":\"movie\",\n" +
				"\"Awards\":\"Nominated for 1 Oscar. Another 5 wins & 42 nominations.\",\n" +
				"\"DVD\":\"06 Mar 2018\",\n" +
				"\"Year\":\"2017\",\n" +
				"\"Poster\":\"https:\\/\\/images-na.ssl-images-amazon.com\\/images\\/M\\/MV5BMWM2MzQ4YTAtMTBkZS00ODA1LWFmNTEtMjEwNzk3ZGJiZDc3XkEyXkFqcGdeQXVyMjM4NTM5NDY@._V1_SX300.jpg\",\n" +
				"\"Country\":\"Ireland, Canada, Luxembourg\",\n" +
				"\"Genre\":\"Animation, Drama, Family\",\n" +
				"\"Writer\":\"Anita Doron, Deborah Ellis\"}";

	}

	//@Test
	public void testTitleJsonSave(){

		Title title = titleService.getTitle(2214);

		//creating title json response
		TitleJsonResponse jsonResponse =  titleJsonResponseAssembler.assembleTitleJsonResponseFromJson(json);

		//creating title ratings for title json response
		Set<TitleRating> ratings = titleRatingsAssembler.assembleRatingsForTitle(json);

		for( TitleRating rating : ratings ){
			rating.setTitleJsonResponse(jsonResponse);
		}
		jsonResponse.setRatings(ratings);
		jsonResponse.setTitleEntity(title);

		titleJsonResponseService.saveTitleJsonResponse(jsonResponse);
	}

	//@Test
	public void randomTest(){
		BigDecimal res= new BigDecimal(100);
		double result = BigDecimal.valueOf(( (double ) 100 )/ 1114).setScale(3, RoundingMode.HALF_UP).doubleValue();
		System.out.println("Result: "+result);
	}

	//@Test
	public void saveJobTest(){
		System.out.println(MessageFormat.format("Cron Task :: Execution Time - {0}", dateTimeFormatter.format(LocalDateTime.now())));

		ScheduledJob job = new ScheduledJob();
		job.setJobType(JobType.SYNCHRONIZATION_JOB);
		job.setStartTime(new Date());

		scheduledJobService.saveScheduledJob(job);
		ScheduledJob schJob = scheduledJobService.findLastScheduledJob(JobType.SYNCHRONIZATION_JOB);
		System.out.println(schJob.toString());

	}

	//@Test
	public void contextLoads() {

		Collection<Callable<Map<RawNames, Title>>> tasks = new ArrayList<>();
		tasks.add( new SingleTitleProcessor(rawName, typeMap,genreMap,apiKey,apiUrl ));
		ExecutorService executor = Executors.newFixedThreadPool(20);

		try {
			List<Future<Map<RawNames, Title>>> results = executor.invokeAll(tasks);

			for(Future<Map<RawNames, Title>> result : results) {
				Map<RawNames, Title> title = (HashMap) result.get();
				for( Map.Entry<RawNames, Title> set : title.entrySet() ){

					if( set.getValue() != null ){

						//insert new title
						set.getValue().toString();

						//save title
						Title response = set.getValue();
						titleService.createTitle(response);

						//creating title json response
						TitleJsonResponse jsonResponse =  titleJsonResponseAssembler.assembleTitleJsonResponseFromJson(response.getApiResponse());

						//creating title ratings for title json response
						Set<TitleRating> ratings = titleRatingsAssembler.assembleRatingsForTitle(json);

						for( TitleRating rating : ratings ){
							rating.setTitleJsonResponse(jsonResponse);
						}
						jsonResponse.setRatings(ratings);
						jsonResponse.setTitleEntity(response);

						titleJsonResponseService.saveTitleJsonResponse(jsonResponse);
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
