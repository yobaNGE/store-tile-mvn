package org.chiches.storecherepitsamvn.controller;

import jakarta.validation.Valid;
import org.chiches.storecherepitsacontracs.controllers.AuthController;
import org.chiches.storecherepitsacontracs.dto.auth.LoginForm;
import org.chiches.storecherepitsacontracs.dto.user.UserForm;
import org.chiches.storecherepitsamvn.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String login(@ModelAttribute("form") LoginForm form,
                        BindingResult bindingResult,
                        Model model) {

        return "auth/login";
    }

    @Override
    public String registration(@ModelAttribute("form") UserForm form, BindingResult bindingResult, Model model) {
        return "auth/register";
    }

    @Override
    public String registerUser(@Valid @ModelAttribute("form") UserForm form,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR " + bindingResult.getErrorCount());
            return "auth/register";
        }

        try {
            System.out.println("trying to register");
            System.out.println(form.toString());
            authService.registerUser(form);
        } catch (IllegalArgumentException e) {
            System.out.println("exception " + e.getMessage());
            model.addAttribute("error","Такой пользователь уже существует");
            return "auth/register";
        }

        return "redirect:/auth/login";
    }

    @Override
    public String logoutConfirmation(Model model) {
        return "auth/logout-confirm";
    }

    @Override
    public String onFailedLogin(String username, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("badCredentials", true);
        return "redirect:/auth/login";
    }
}
