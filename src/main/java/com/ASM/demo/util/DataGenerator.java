package com.ASM.demo.util;

import com.github.javafaker.Faker;
import com.ASM.demo.model.*;
import com.ASM.demo.repository.AttendanceRepository;
import com.ASM.demo.repository.CourseRepository;
import com.ASM.demo.repository.LessonRepository;
import com.ASM.demo.repository.StudentRepository;
import com.ASM.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {

    private final Faker faker = new Faker();

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public DataGenerator(StudentRepository studentRepository,
                         TeacherRepository teacherRepository,
                         CourseRepository courseRepository,
                         LessonRepository lessonRepository,
                         AttendanceRepository attendanceRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public void generateData() {
        List<Student> students = generateStudents(1);
        List<Teacher> teachers = generateTeachers(2);
        List<Course> courses = generateCourses(2, teachers);
        List<Lesson> lessons = generateLessons(2, courses, teachers);
        List<Attendance> attendances = generateAttendances(2, students, lessons);

        studentRepository.saveAll(students);
        teacherRepository.saveAll(teachers);
        courseRepository.saveAll(courses);
        lessonRepository.saveAll(lessons);
        attendanceRepository.saveAll(attendances);
    }

    private List<Student> generateStudents(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            students.add(new Student(
                    faker.name().lastName(),        // lastName
                    faker.name().firstName(),       // firstName
                    faker.number().numberBetween(18, 25), // age
                    faker.internet().emailAddress(), // email
                    faker.address().fullAddress(),  // address
                    faker.phoneNumber().cellPhone() // phoneNumber
            ));
        }
        return students;
    }

    private List<Teacher> generateTeachers(int count) {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Teacher teacher = new Teacher(
                    faker.name().firstName(),       // firstName
                    faker.name().lastName(),        // lastName
                    faker.number().numberBetween(25, 65), // age
                    faker.internet().emailAddress(), // email
                    faker.address().fullAddress(),  // address
                    faker.phoneNumber().cellPhone(), // phoneNumber
                    faker.job().position(),         // position
                    faker.educator().university(),  // department
                    faker.address().city()          // officeLocation
            );
            teachers.add(teacher);
        }
        return (List<Teacher>) teacherRepository.saveAll(teachers);
    }

    private List<Course> generateCourses(int count, List<Teacher> teachers) {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Teacher teacher = teachers.get(faker.random().nextInt(teachers.size()));
            Course course = new Course(
                    faker.code().isbn13(),          // courseCode
                    faker.educator().course(),      // courseName
                    faker.educator().campus(),      // department
                    teacher,                        // instructor
                    faker.number().numberBetween(1, 4) // creditHours
            );
            courses.add(course);
        }
        return (List<Course>) courseRepository.saveAll(courses);
    }

    private List<Lesson> generateLessons(int count, List<Course> courses, List<Teacher> teachers) {
        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Course course = courses.get(faker.random().nextInt(courses.size()));
            Teacher teacher = teachers.get(faker.random().nextInt(teachers.size()));
            Lesson lesson = new Lesson(
                    course,                                 // course
                    LocalDateTime.now().plusDays(i),        // startTime
                    LocalDateTime.now().plusDays(i).plusHours(2), // endTime
                    teacher                                 // teacher
            );
            lessons.add(lesson);
        }
        return (List<Lesson>) lessonRepository.saveAll(lessons);
    }

    private List<Attendance> generateAttendances(int count, List<Student> students, List<Lesson> lessons) {
        List<Attendance> attendances = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Student student = students.get(faker.random().nextInt(students.size()));
            Lesson lesson = lessons.get(faker.random().nextInt(lessons.size()));
            Attendance attendance = new Attendance(
                    student,                                // student
                    lesson,                                 // lesson
                    LocalDate.now().minusDays(i),           // date
                    faker.bool().bool()                     // present
            );
            attendances.add(attendance);
        }
        return (List<Attendance>) attendanceRepository.saveAll(attendances);
    }
}
