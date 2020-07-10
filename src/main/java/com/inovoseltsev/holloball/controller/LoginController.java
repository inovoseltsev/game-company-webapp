package com.inovoseltsev.holloball.controller;

import com.inovoseltsev.holloball.message.ErrorMessage;
import com.inovoseltsev.holloball.model.entity.AppUser;
import com.inovoseltsev.holloball.model.service.AppUserService;
import com.inovoseltsev.holloball.model.state.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private AppUserService userService;

    @GetMapping("/sign-in")
    public String displayLoginPage(HttpServletRequest req, ModelMap model,
                                   Authentication authentication) {
        if (authentication != null) {
            return "redirect:/home";
        }
        if (req.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "sign-in";
    }

    @RequestMapping("/failed")
    public String setFailPage(@RequestParam Map<String, String> userLoginData,
                              Model model) {
        AppUser user = userService.findByLogin(userLoginData.get("login"));
        if (user == null || user.getState().equals(State.ACTIVE)) {
            model.addAttribute("errorMessage", ErrorMessage.getLoginError());
        } else {
            model.addAttribute("errorMessage", ErrorMessage.getBannedError());
        }
        return "sign-in";
    }
}
