package dwz.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/passport")
public class PassportController{
	
//	@Autowired
//	private UserService userService;
    
	@RequestMapping(value = "")
	public String loginPage(){

		return "login";
	}
	
//	@RequestMapping(value = "/login")
//	public ModelAndView loginCheck(HttpServletRequest request,LoginCommand loginCommand){
//		boolean isValidUser = 
//			   userService.hasMatchUser(loginCommand.getUserName(),
//					                    loginCommand.getPassword());
//		
//		System.out.println(loginCommand.getLoginDate());
//		System.out.println(loginCommand.getSalary());
//		System.out.println(loginCommand.getUserStatus());
//		if (!isValidUser) {
//			return new ModelAndView("login", "error", "用户名或密码错误。");
//		} else {
//			User user = userService.findUserByUserName(loginCommand
//					.getUserName());
//			user.setLastIp(request.getLocalAddr());
//			user.setLastVisit(new Date());
//			userService.loginSuccess(user);
//			request.getSession().setAttribute("user", user);
//			return new ModelAndView("main");
//		}
//	}
}
