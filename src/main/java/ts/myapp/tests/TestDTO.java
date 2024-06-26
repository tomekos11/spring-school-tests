package ts.myapp.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.Group;
import ts.myapp.users.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class TestDTO {
    private Long id;
    private String name;
    private String alias;

    // optional: method to convert Test entity to TestDTO
    public static TestDTO from(Test test) {
        return new TestDTO(test.getId(), test.getName(), test.getAlias());
    }

    // optional: method to convert List<Test> to List<TestDTO>
    public static List<TestDTO> from(List<Test> tests) {
        return tests.stream()
                .map(TestDTO::from)
                .collect(Collectors.toList());
    }
}
