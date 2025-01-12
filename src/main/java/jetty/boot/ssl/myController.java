package jetty.boot.ssl;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController

public class myController {
	
	@RequestMapping("/home")
	public String getMethodName() {
		return "Hello there .. Hi from Lokesh !! ";
	}
	

}
