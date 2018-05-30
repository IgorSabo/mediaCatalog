package com.catalog.presentation.controllers;

//import com.example.dao.cntbygenre.CntByGenreDAO;
//import com.example.dao.cntbytype.CntByTypeDAO;
//import com.example.dao.notinserted.NotInsertedDAO;
//import com.example.dao.rawnames.RawNamesDAO;
import com.catalog.AppConstants;
import com.catalog.model.entities.*;
import com.catalog.service.*;
import com.catalog.business.utils.*;
import com.catalog.model.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.*;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
/*import com.example.dao.CntByGenreDAO;
import com.example.dao.CntByTypeDAO;
import com.example.dao.NotInsertedDAO;
import com.example.dao.RawNamesDAO;*/
/*import com.example.utils.FetchFromIMDB;
import com.example.utils.FileManipulator;*/


/**
 * Handles requests for the application home page.
 */
@Controller
public class TitlesController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private TitleService titleService;

	@Autowired
	private NotInsertedService noInsService;

	@Autowired
	private GenreService genreService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private RawNamesService rawNamesService;
	
	@Autowired
	private FillDatabase fillDatabase;
	
	@Autowired
	private CreateEntities createEntities;

	@Autowired
	private CntByGenreService cntByGenreService;

	@Autowired
	private CntByTypeService cntByTypeService;

	@Autowired
	private MediaFolderService mediaFolderService;

	@Autowired
	private FetchFromIMDB fetchFromIMDB;

	@Value( "${apikey:def}" )
	private String apiKey;

	@Value( "${apiUrl:def}" )
	private String apiUrl;

	@RequestMapping(value = "/titles")
	private ModelAndView homepage(Principal principal)
	{
		System.out.println("Ulazak u kontroler za /titles");
		//LOGGER.info("switching to home page");
		ModelAndView mv = new ModelAndView("index");
		Set<Title> list =  titleService.getResults("All", 0, 12, null, null);
		//LOGGER.info("Username is: {} ", principal.getName());
		//mv.addObject("userName", principal.getName());
		mv.addObject("titles",list);
		return mv;
	}


	@RequestMapping(value="/GetTitles", method = RequestMethod.GET)
	public String getTitles(Model model, HttpServletRequest request)
	{
		HttpSession session=request.getSession(true);
		System.out.println("Ulazak u kontroler za /GetTitles");
		
		//type
		String type= request.getParameter("type");
		if(type.equals("undefined") || type.equals("notSelected"))
        {
           type=(String)session.getAttribute("type");
        }
		else
		{
		   session.setAttribute("type", type);
		}
		
		String page= request.getParameter("page");
		String perPage=request.getParameter("perPage");
		
		//genre
		String genre=""; 
		if(request.getParameter("genre")!=null && !(request.getParameter("genre").equals("")) && !(request.getParameter("genre").equals("notSelected")) )
		{
			genre= request.getParameter("genre");
		}
		else
		{
			genre=null;
		}
		
		
		//year
		String year="";
		if(request.getParameter("year")!=null && !(request.getParameter("year").equals("")) && !(request.getParameter("year").equals("notSelected")) )
		{
			year= request.getParameter("year");
		}
		else
		{
			year=null;
		}
		
		Set<Title> list =  titleService.getResults(type, Integer.valueOf(page), Integer.valueOf(perPage), genre, year);
		Map<String,Set<Title>> map = new HashMap<>();

		map.put("titles", list);
		
		//System.out.println("Found next"+list.size()+" results");
		System.out.println("Nadjeni sledeci parametri: type="+type+", page="+page+", perPage="+perPage+", genre="+genre);
		model.addAttribute("titles",list);
		return "resultList :: resultsList";
	}
	
//	@RequestMapping(value="GetTotalNumber", method= RequestMethod.GET)
//	public @ResponseBody
//    Map<String, Number> GetTotalNumber()
//	{
//		Number num = titleDaoI.getTotalEntities();
//		HashMap<String, Number> map = new HashMap<String, Number>();
//		map.put("totalFound", num);
//		return map;
//	}
	
	@RequestMapping(value="/QuickSearchResults", method= RequestMethod.GET)
	public @ResponseBody
    ArrayList<Object[]> getQuickSearchResults( HttpServletRequest request)
	{
		String name=request.getParameter("name");

		HashSet<Object[]> set = (HashSet<Object[]>) titleService.getQuickSearchResults(name);
		ArrayList<Object[]> list = new ArrayList<>();
		list.addAll(set);
		return list;
	}
	
	@RequestMapping(value="/ShowInfo", method= RequestMethod.GET)
	public String showInfo(Model model,HttpServletRequest request)
	{

		int idFilm=Integer.valueOf(request.getParameter("IDtitle"));
		Title titl = titleService.showInfo(idFilm);
		model.addAttribute("titleInfo",titl);
		System.out.println("Entering ShowInfo, object found: "+titl.toString());
		return "resultInfo :: resultInfo";
	}


	@RequestMapping(value="/EditTitle", method= RequestMethod.GET)
	public String editTitle(Model model,HttpServletRequest request)
	{

		int idFilm=Integer.valueOf(request.getParameter("IDtitle"));
		Title titl = titleService.showInfo(idFilm);
		model.addAttribute("titleInfo",titl);
		System.out.println("Entering EditInfo, object found: "+titl.toString());
		return "editTitle :: editResultInfo";
	}
	
	@RequestMapping(value="/SaveChanges", method= RequestMethod.POST)
	public ModelAndView editTitle(@ModelAttribute Title wiredTitle)
	{
		titleService.updateTitle(wiredTitle);
		ModelAndView m = new ModelAndView("index");
		m.addObject("message", "poruka za korisnika");
		System.out.println("Updated method");
		return m;
	}
	
	@RequestMapping(value="FetchFromIMDB", method= RequestMethod.GET)
	public @ResponseBody JSONObject fetchFromIMDB(HttpServletRequest request)
	{
		String IMDBID =request.getParameter("IMDBID");
		System.out.println("apiKey = "+apiKey);
		JSONObject response=fetchFromIMDB.fetch(IMDBID, apiKey, apiUrl);
		System.out.println(response);

		return response;
	}
	
	@RequestMapping(value="/AddToFavorites", method= RequestMethod.POST)
	public ModelAndView addToFavorites(HttpServletRequest request)
	{
		titleService.addToFavorite(Integer.valueOf(request.getParameter("idTitle")));
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/AddToMustWatch", method= RequestMethod.POST)
	public ModelAndView addToMustWatch(HttpServletRequest request)
	{
		titleService.addToMustWatch(Integer.valueOf(request.getParameter("idTitle")));
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/AddToIncorrect", method= RequestMethod.POST)
	public ModelAndView addToIncorrect(HttpServletRequest request)
	{
		titleService.addToIncorrect(Integer.valueOf(request.getParameter("idTitle")));
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/Synchronize", method= RequestMethod.POST)
	public ModelAndView synchornizeTitles(HttpSession session)
	{
		//synchronizing titles
		//napuni bazu sa novim imenima
		/*fillDatabase.getNames();
		List<RawNames> list = rawNamesService.findByLastAdded(1);
		if(list.size()>0)
		{*/
			//createEntities.createTitlesAlt((ArrayList)list);
			titleService.processNewTitles(session);
		//}

		//removing status from session
		session.removeAttribute("status");

		System.out.println("Synchronization completed!");
		return new ModelAndView("index");
	}/**/

	@RequestMapping(value="/getResponseStatus", method= RequestMethod.POST)
	public @ResponseBody String getResponseStatus(HttpSession session){

		return titleService.returnProgress(session);
	}
	
	@RequestMapping(value="/DeleteTitle", method= RequestMethod.POST)
	public ModelAndView delete(HttpServletRequest request)
	{
		int titleId=Integer.valueOf(request.getParameter("idTitle"));

		Title title = titleService.getTitle(titleId);

		//delete from title
		titleService.deleteTitle(titleId);
		System.out.println("title deleted from Title table");

		//delete from rawNames
		rawNamesService.delete(titleId);
		System.out.println("title deleted from RawNames table");

		//delete from file system
		new FileManipulator().deleteFile(title.getLocation());
		System.out.println("title deleted from file system");

		return new ModelAndView("index");
	}

	@RequestMapping(value="/OpenFolder", method= RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void openFolder(HttpServletRequest request){

		int titleId=Integer.valueOf(request.getParameter("idTitle"));

		Title title = titleService.getTitle(titleId);

		DeterminePlayFile.performActionOnTitle(title.getLocation(), DeterminePlayFile.OperationType.OPEN_FOLDER);
	}

    @RequestMapping(value="/PlayFile", method= RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void playFile(HttpServletRequest request){

        int titleId=Integer.valueOf(request.getParameter("idTitle"));

        Title title = titleService.getTitle(titleId);

        DeterminePlayFile.performActionOnTitle(title.getLocation(), DeterminePlayFile.OperationType.PLAY_FILE);
    }
	
	
	@RequestMapping(value="/GetNotInserted", method = RequestMethod.GET)
    public String getNotInserted(Model model, HttpServletRequest request)
	{

		/*String page=(String) request.getParameter("page");
		String perPage=(String) request.getParameter("perPage");*/

		//ArrayList<NotInserted> list = (ArrayList<NotInserted>) notInsertedDAO.getNotInsertedResults(Integer.valueOf(page), Integer.valueOf(perPage));
		ArrayList<NotInserted> list = (ArrayList<NotInserted>) noInsService.getAllResults();

		model.addAttribute("noInsTitles",list);
		return "noInsResultList :: noInsResultsList";
	}
	
	@RequestMapping(value="/EditInfoNotInserted", method= RequestMethod.GET)
    public String EditInfoNotIserted(Model model,HttpServletRequest request)
	{
		int idFilm=Integer.valueOf(request.getParameter("IDtitle"));
		NotInserted title = noInsService.getOne(idFilm);
		ArrayList<Genre> genreList = (ArrayList<Genre>) genreService.findAll();
		ArrayList<Type> typeList = typeService.findAll();
		System.out.println("Entering EditInfoNotInserted, object found: "+title.toString());
		model.addAttribute("noInsTitle",title);
		model.addAttribute("genreList",genreList);
		model.addAttribute("typeList",typeList);
		//rawnamesDaoI.delete(idFilm);
		return "editNoInsTitle :: editNoInsTitle";
	}
	
	@RequestMapping(value="/SaveChangesForNotInserted", method= RequestMethod.POST)
	public ModelAndView saveChangesNotInserted(@ModelAttribute Title wiredTitle)
	{
		//due to nature of not inserted files we change the name of the file on hdd to file that is found on IMDB

		//rename file on hard drive and return Title object with updated location
		wiredTitle=FileManipulator.renameFile(wiredTitle);

		//delete entry from notInserted table
		noInsService.removeFromNotInserted(wiredTitle.getIDfilm());

		//update entry in rawNames table
		RawNames tmp = rawNamesService.find(wiredTitle.getIDfilm());
		tmp.setName(wiredTitle.getImdbTitle());
		tmp.setLocation(wiredTitle.getLocation());
		tmp.setLastAdded(1);
		rawNamesService.updateRawNames(tmp);

		//create entry in title table
		wiredTitle.setLastAdded(1);
		titleService.createTitle(wiredTitle);

		return new ModelAndView("index");
	} /**/
	
	@RequestMapping(value="/DeleteTitleFromNotInserted", method= RequestMethod.POST)
	public ModelAndView deleteTitleFromNotInserted(HttpServletRequest request)
	{
		int titleId=Integer.valueOf(request.getParameter("idTitle"));
		noInsService.removeFromNotInserted(titleId);
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/openAdvancedSearch", method= RequestMethod.GET)
	public ModelAndView openAdvancedSearch() throws Exception  {
		ModelAndView model=new ModelAndView("advancedSearch");
		model.addObject("message", "blablabla");
		return model;
	}

	@RequestMapping(value = "/openGeneralOptions", method= RequestMethod.GET)
	public ModelAndView openGeneralOptions() throws Exception  {

		List<MediaFolder> listOfFolders = mediaFolderService.getAllMediaFolders();

		Set<Duplicate> duplicates = titleService.getDuplicates();

		ModelAndView mv=new ModelAndView("generalOptions");
		mv.addObject("listOfFolders", listOfFolders);
		mv.addObject("duplicates", duplicates);

		return mv;
	}

	@RequestMapping(value = "/showDuplicateInfo/{idTitle}", method= RequestMethod.GET)
	public String showDuplicateInfo(Model model, @PathVariable int idTitle){

		List<Duplicate> duplicates = new ArrayList<Duplicate>();
		Title title = titleService.showInfo(idTitle);
		String imdbTitle = title.getImdbTitle();


		Set<Title> duplicateTitles = titleService.findByImdbTitle(imdbTitle);
		model.addAttribute("duplicates",duplicateTitles);
		return "duplicateInfo :: duplicateInfo";

	}

	
	@RequestMapping(value="/GetCountResults", method= RequestMethod.GET)
	public @ResponseBody
    HashMap<String, Number> getCountResults(HttpServletRequest request) {
		ArrayList<CntByType> listOfAllCountsByType = (ArrayList<CntByType>) cntByTypeService.getCntForAllTypes();
		ArrayList<CntByGenre> listOfAllCountsByGenre = (ArrayList<CntByGenre>) cntByGenreService.getCntForAllGenres();
		HashMap<String, Number> map = new HashMap<String, Number>();
		
		
		for(CntByType result : listOfAllCountsByType)
		{
			map.put(result.getType(), result.getTotal()); 
		}
		
		for(CntByGenre result : listOfAllCountsByGenre)
		{
			map.put(result.getGenre(), result.getTotal()); 
		}
		
		return map;
	}
	
	@RequestMapping(value="/GetSpecificCounts", method= RequestMethod.GET)
	public @ResponseBody
    HashMap<String, Number> GetSpecificCounts(HttpServletRequest request)
	{
		//TODO need to implement specific count retrival for the combination of selected type, genre and year
		
		
		HashMap<String, Number> map = new HashMap<String, Number>();
		
		/*String type=(String) request.getParameter("type");
		if(type.equals("undefined") || type.equals("NotSelected"))
        {
           type=null;
        }

		String genre=(String) request.getParameter("genre");
		if(type.equals("undefined") || type.equals("NotSelected"))
        {
			genre=null;
        }*/
		
		
		return map;
	}
	

	
}
