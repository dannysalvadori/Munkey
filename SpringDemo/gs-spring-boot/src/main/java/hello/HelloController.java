package hello;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "<tr>"
			   + "<td>Don't have an account yet.</td>"
			   + "<td> <a href=\"index\">Register here</a>"
			   + "</td>"
		   + "</tr>";
    }
    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting ya nub";
    }
    
    @RequestMapping("/greeting")
    public String go(Model model) {
        model.addAttribute("Name", "Danny");
        return "registration";
    }
    
}
