package web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

import business.controllers.CourtController;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class Presenter {
	
	private static final List<String> THEMES = Arrays.asList("jsp");
	
	@Autowired
	private CourtController courtController;
	
	private String theme = THEMES.get(0);

    public Presenter() {
    }
    
    @ModelAttribute("now")
    public String now() {
        return new SimpleDateFormat("EEEE, d MMM yyyy HH:mm:ss").format(new Date());
    }
    
    @RequestMapping("/home")
    public String home(Model model) {
    	courtController.createCourt(1);
    	courtController.createCourt(2);
    	courtController.createCourt(3);
    	model.addAttribute("courts", courtController.showCourts());
        return theme + "/ListCourts";
    }

}
