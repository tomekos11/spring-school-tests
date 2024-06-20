package ts.myapp.groups;

import lombok.Data;
import ts.myapp.tests.Test;
import ts.myapp.tests.TestDTO;
import ts.myapp.users.UserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GroupTestDTO {

    private Long id;
    private TestDTO test;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;


    // optional: method to convert Group entity to GroupDTO
    public static GroupTestDTO from(GroupTest groupTest) {
        GroupTestDTO dto = new GroupTestDTO();
        dto.setId(groupTest.getId());
        dto.setTest(TestDTO.from(groupTest.getTest()));
        dto.setBeginDate(groupTest.getBeginDate());
        dto.setEndDate(groupTest.getEndDate());

        return dto;
    }

    // optional: method to convert List<Group> to List<GroupDTO>
    public static List<GroupTestDTO> from(List<GroupTest> groups) {
        return groups.stream()
                .map(GroupTestDTO::from)
                .collect(Collectors.toList());
    }
}
