package com.zziri.contact.repository;

import com.zziri.contact.domain.Person;
import com.zziri.contact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();
        person.setName("john");

        personRepository.save(person);

        List<Person> result = personRepository.findByName("john");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("john");
    }

    @Test
    void findByBirthdayBetween() {
        List<Person> result = personRepository.findByMonthOfBirthday(8);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("sophia");
    }

    @Test
    void findByName() {
        List<Person> people = personRepository.findByName("tony");
        assertThat(people.size()).isEqualTo(1);

        Person person = people.get(0);
        assertAll(
                () -> assertThat(person.getName()).isEqualTo("tony"),
                () -> assertThat(person.getHobby()).isEqualTo("reading"),
                () -> assertThat(person.getAddress()).isEqualTo("Seoul"),
                () -> assertThat(person.getBirthday()).isEqualTo(Birthday.of(LocalDate.of(1991, 7, 10))),
                () -> assertThat(person.getJob()).isEqualTo("officer"),
                () -> assertThat(person.getPhoneNumber()).isEqualTo("010-2222-5555"),
                () -> assertThat(person.isDeleted()).isEqualTo(false)
        );
    }

    @Test
    void findByNameIfDeleted() {
        List<Person> people = personRepository.findByName("andrew");
        assertThat(people.size()).isEqualTo(0);
    }

    @Test
    void findByMonthOfBirthday() {
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        assertThat(people.size()).isEqualTo(2);
        assertAll(
                () -> assertThat(people.get(0).getName()).isEqualTo("david"),
                () -> assertThat(people.get(1).getName()).isEqualTo("tony")
        );
    }

    @Test
    void findPeopleDeleted() {
        List<Person> people = personRepository.findPeopleDeleted();

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("andrew");
    }
}