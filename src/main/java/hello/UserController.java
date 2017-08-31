package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <code></code>
 *
 * @author masonhsieh
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;


    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/rest/create")
    @ResponseBody
    public Iterable<User> createUserRest(@RequestBody Iterable<User> users) {
        return userRepository.save(users);
    }

    // Create single user with JSON
//    @PostMapping("/rest/create")
//    @ResponseBody
//    public User createUserRest(@RequestBody User user) {
//        return userRepository.save(user);
//    }

    @GetMapping("/rest/all")
    @ResponseBody
    public Iterable<User> findAllUsersRest() {
        return userRepository.findAll();
    }

    @GetMapping("/form/id/{userId}")
    public String showUser(@PathVariable String userId, Model model) {
        System.out.println("Received id: " + userId);

        User user = userRepository.findOne(Long.parseLong(userId));

        model.addAttribute("user", user);

        return "show_user";
    }

    @GetMapping("/form/create")
    public String showUserForm(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "user_form";
    }

    @PostMapping("/form/create")
    public String submitUserForm(@ModelAttribute User user) {
        userRepository.save(user);

        return "show_user";
    }

    @GetMapping("/form/all")
    public String showAllUsers(Model model) {
        Iterable<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        return "show_all_users";
    }

    @GetMapping("/form/page/{page_index}/{size}")
    public String showUsersWithPageable(@PathVariable("page_index") Integer pageIndex, @PathVariable("size") Integer size, Model model) {
//        System.out.println(String.format("Received page: %d, size: %d", pageIndex, size));

        // The sorting property is the property of User object, instead of the column name in DB
        Sort sort = new Sort(Sort.Direction.ASC, "id");

        Pageable pageable = new PageRequest(pageIndex, size, sort);

        Page<User> page = userRepository.findAll(pageable);

        List<User> users = page.getContent();

        model.addAttribute("page", page);
        model.addAttribute("users", users);

        return "show_users_pageable";
    }

}
