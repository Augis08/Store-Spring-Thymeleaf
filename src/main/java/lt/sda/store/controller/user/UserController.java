package lt.sda.store.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String firstPage(ModelMap modelMap){
        modelMap.addAttribute("users", userService.getAllUsers());
        return "firstFile";
    }

    @GetMapping("/registration")
    public String registration(UserRegistrationDTO userRegistrationDTO, Model model){
        model.addAttribute("userRegistrationDTO", userRegistrationDTO);
        return "registration";
    }

    @PostMapping
    public String register (@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "registration";
        userService.register(userRegistrationDTO);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
