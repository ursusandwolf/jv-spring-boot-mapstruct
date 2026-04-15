package mate.academy.mapstruct.mapper;

import java.util.ArrayList;
import java.util.List;
import mate.academy.mapstruct.dto.student.CreateStudentRequestDto;
import mate.academy.mapstruct.dto.student.StudentDto;
import mate.academy.mapstruct.dto.student.StudentWithoutSubjectsDto;
import mate.academy.mapstruct.model.Student;
import mate.academy.mapstruct.model.Subject;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = GroupMapper.class)
public interface StudentMapper {
    @Mapping(source = "group.id", target = "groupId")
    StudentDto toDto(Student student);

    @Mapping(source = "group.id", target = "groupId")
    StudentWithoutSubjectsDto toStudentWithoutSubjectsDto(Student student);

    @Mapping(target = "group", source = "groupId", qualifiedByName = "groupById")
    Student toModel(CreateStudentRequestDto requestDto);

    default void setSubjectsId(StudentDto dto, Student student){
        List<Long> subjectsId = student.getSubjects().stream()
                .map(Subject::getId)
                .toList();
        dto.setSubjectIds(subjectsId);
    }

    @AfterMapping
    default void setSubjects(CreateStudentRequestDto requestDto, Student student){
        List<Subject> subjects = requestDto.subjects().stream()
                .map(Subject::new)
                .toList();
        student.setSubjects(subjects);
    }
}
