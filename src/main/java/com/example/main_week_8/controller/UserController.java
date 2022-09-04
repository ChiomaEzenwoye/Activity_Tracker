package com.example.main_week_8.controller;

import com.example.main_week_8.dto.TaskDTO;
import com.example.main_week_8.dto.UserDTO;
import com.example.main_week_8.model.Status;
import com.example.main_week_8.model.Task;
import com.example.main_week_8.model.User;
import com.example.main_week_8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
//@RequestMapping(value = "/user")
public class UserController {

        private final UserService userService;
        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }



//    @GetMapping(value = "/")
//    public String displayLandingPage(Model model, HttpSession session){
//        model.addAttribute("userDetails" , new UserDTO());
//        session.setAttribute("userDetails", new UserDTO());
//
//        return "login";

        @GetMapping(value = "/login")
        public String displayLandingPage(Model model, HttpSession session){
        model.addAttribute("userDetails" , new UserDTO());
        session.setAttribute("userDetails", new UserDTO());

        return "login";
    }


        @GetMapping("/dashboard")
        public String index(Model model, HttpSession session){
            if(session.getAttribute("id") == null){
                return "redirect:/login";
            }else{
                List<Task> allTasks = userService.showTaskByUser((Integer)session.getAttribute("id"));
                model.addAttribute("taskList" , allTasks);
                session.setAttribute("taskList", allTasks);
                return "dashboard";
            }

        }


        @GetMapping(value = "/")
        public String index(Model model){
            model.addAttribute("userDetails" , new UserDTO());
            return "index";//changed login to index
        }


        @PostMapping(value = "/loginUser")
        public String loginUser(@RequestParam String email , @RequestParam String password , HttpSession session , Model model){
            String message =  userService.loginUser(email ,  password);
            if(message.equals("Success")){
                User user = userService.getUserByEmail(email);
                session.setAttribute("email" , user.getEmail());
                session.setAttribute("id" , user.getId());
                session.setAttribute("name" , user.getName());
                return "redirect:/dashboard";
            }else{
                model.addAttribute("errorMessage" , message);
                return  "redirect:/login";
            }
        }

    @GetMapping(value = "/signUpSuccess")
    public String showSignUpSuccess(){
        return "signUpSuccess";
    }




        @GetMapping(value = "/register")
        public  String showRegistrationForm(Model model){
            model.addAttribute("userRegistrationDetails" , new UserDTO());
            return  "register";
        }



        @PostMapping(value = "/userRegistration")
        public String registration(@ModelAttribute UserDTO userDTO){
            User registeredUser = userService.registerUser(userDTO);
            if (registeredUser != null){
                return "redirect:/login";
            }else {
                return "redirect:/register";
            }
        }


        @GetMapping(value = "/task/{status}")
        public String viewTaskByStatus(@PathVariable(name = "status") String status , Model model , HttpSession session){
            if (session.getAttribute("id") != null) {
                List<Task> task = userService.viewAllTaskByStatus(status, (Integer) session.getAttribute("id"));
                model.addAttribute("tasks", task);
                return "taskByStatus";
            } else {
                return "redirect:/login";
            }
        }

//    @GetMapping("/task/{status}")
//    public String taskByStatus(@PathVariable(name = "status") Task task) {
//
//        if(task.getStatus() == Status.PENDING) {
//            return "pendingTasks";
//        } else if (task.getStatus() == Status.IN_PROGRESS) {
//            return "tasksInProgress";
//        } else {
//            return "doneTasks";
//        }
//    }


        @GetMapping("/delete/{id}")
        public String deleteTask(@PathVariable(name = "id") Integer id){
            userService.deleteById(id);
            return "redirect:/dashboard";
        }


        @GetMapping(value = "/editPage/{id}")
        public String showEditPage(@PathVariable(name = "id") Integer id , Model model){
            Task task = userService.getTaskById(id);
            model.addAttribute("singleTask" , task);
            model.addAttribute("taskBody", new TaskDTO());
            return  "editTask";
        }


        @PostMapping(value = "/edit/{id}")
        public String editTask(@PathVariable( name = "id") String id , @ModelAttribute TaskDTO taskDTO){
            int taskId = Integer.parseInt(id);
            userService.updateTitleAndDescription(taskDTO , taskId);
            return "redirect:/dashboard";
        }


        @GetMapping(value = "/addNewTask")
        public String addTask(Model model){
            model.addAttribute("newTask" , new TaskDTO());
            return "addTask";
        }


        @PostMapping(value = "/addTask")
        public String CreateTask(@ModelAttribute TaskDTO taskDTO){
            userService.createTask(taskDTO);
            return "redirect:/dashboard";
        }



        @GetMapping("/logout")
        public String logoutUser(HttpSession session){
            session.invalidate();
            return "redirect:/login";
        }

    @GetMapping(value = "/arrow-right/{id}")
    public String moveStatusForward(@PathVariable(name = "id") int id){
        userService.moveForward(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/arrow-left/{id}")
    public String moveStatusBackward(@PathVariable(name = "id") int id){
        userService.moveBackward(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/viewTask/{id}")
    public String viewSingleTask(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
        Task task = userService.getTaskById(id);
        model.addAttribute("singleTask", task);
        session.setAttribute("singleTask", task);
        return "viewSingleTask";
    }


    }


