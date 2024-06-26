package ts.myapp.groups;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ts.myapp.services.UserService;
import ts.myapp.tests.Test;
import ts.myapp.tests.TestRepository;
import ts.myapp.users.User;
import ts.myapp.users.UserGroup;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private final UserService userService;

    private final ObjectMapper objectMapper;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TestRepository testRepository;

    public GroupController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/groups/manage")
    public ModelAndView showGroups(Model model) throws JsonProcessingException {
        User currentUser = userService.me();

        String serializedUser = objectMapper.writeValueAsString(currentUser);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        List<Group> groups = groupRepository.findAll();

        String serializedGroups = objectMapper.writeValueAsString(groups);
        List<Group> deserializedGroups = objectMapper.readValue(serializedGroups, new TypeReference<List<Group>>() {});

        ModelAndView modelAndView = new ModelAndView("groups-manage");


        List<GroupDTO> groupDTOs = groups.stream()
                .map(GroupDTO::from)
                    .toList();

        List<Test> tests = testRepository.findAll();

        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("groups", groupDTOs);
        modelAndView.addObject("tests", tests);

        return modelAndView;
    }

}
