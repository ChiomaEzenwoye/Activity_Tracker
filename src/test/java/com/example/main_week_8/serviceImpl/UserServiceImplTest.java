package com.example.main_week_8.serviceImpl;

import com.example.main_week_8.dto.TaskDTO;
import com.example.main_week_8.dto.UserDTO;
import com.example.main_week_8.model.Status;
import com.example.main_week_8.model.Task;
import com.example.main_week_8.model.User;
import com.example.main_week_8.repository.TaskRepository;
import com.example.main_week_8.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Calendar.AUGUST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    private User user;
    private Task task;
    private UserDTO userDTO;
    private TaskDTO taskDTO;
    List<Task> taskList;

    private  LocalDateTime time;


    @BeforeEach
    void setUp() {
        time = LocalDateTime.of(2022, AUGUST,3,6,30,40,50000);
        taskList = new ArrayList<>();
        taskList.add(task);
        this.user = new User(1,"Chioma", "vee@gmail.com","12345",taskList);
        this.task = new Task(1,"Cooking","cook egwusi in 30 mins", Status.PENDING, time, time, time, user);
        this.taskDTO = new TaskDTO("Washing", "Wash all you clothes ",1);
        this.userDTO = new UserDTO("Chioma", "vee@gmail.com","12345");

        when(taskRepository.save(task)).thenReturn(task);
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskRepository.listOfTasksByStatus("pending", 1)).thenReturn(taskList);
        when(taskRepository.findById(1)).thenReturn(Optional.ofNullable(task));
        when(taskRepository.updateTaskByIdAndStatus("ongoing" , 1)).thenReturn(true);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(userRepository.findUserByEmail("vee@gmail.com")).thenReturn(Optional.of(user));

    }

    @Test
    void registerUser() {
        when(userServiceImpl.registerUser(userDTO)).thenReturn(user);
        var actual = userServiceImpl.registerUser(userDTO);
        var expected = user;
        assertEquals( expected , actual );
    }

    @Test
    void loginUser_Successful() {
        String message = "Success";
        assertEquals(message , userServiceImpl.loginUser("vee@gmail.com" , "12345"));
    }

    @Test
    void loginUser_Unsuccessful() {
        String message = "incorrect password";
        assertEquals(message , userServiceImpl.loginUser("vee@gmail.com" , "1234"));
    }


    @Test
    void createTask() {
        when(userServiceImpl.createTask(taskDTO)).thenReturn(task);
        assertEquals(task , userServiceImpl.createTask(taskDTO));
    }

    @Test
    void updateTitleAndDescription() {
        assertEquals(task , userServiceImpl.updateTitleAndDescription(taskDTO , 1));
    }

    @Test
    void viewAllTasks() {
        assertEquals(1 , userServiceImpl.viewAllTasks().size());
    }

    @Test
    void viewAllTaskByStatus() {

        assertEquals(taskList , userServiceImpl.viewAllTaskByStatus("pending", 1));
    }

//    @Test
//    void deleteById() {
//        when(userServiceImpl.deleteById(1)).thenReturn(true);
//        assertTrue(userServiceImpl.deleteById(1));
//    }

    @Test
    void updateTaskStatus() {
        assertTrue(userServiceImpl.updateTaskStatus("ongoing" , 1));
    }

    @Test
    void getUserByEmail() {
        assertEquals(user , userServiceImpl.getUserByEmail("vee@gmail.com"));
    }

    @Test
    void getTaskById() {
        assertEquals(task, userServiceImpl.getTaskById(1));
    }


    }

