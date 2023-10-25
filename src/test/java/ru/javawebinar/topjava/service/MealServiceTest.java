package ru.javawebinar.topjava.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
       Meal expected = service.get(MEAL_ID, USER_ID);
        assertThat(expected).usingRecursiveComparison().isEqualTo(meal);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getOther() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteOther() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        List<Meal> actual = Stream.of(meal, meal_2, meal_3)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertThat(expected).containsExactlyInAnyOrderElementsOf(actual);
    }

    @Test
    public void getAll() {
        List<Meal> expected = service.getAll(USER_ID);
        List<Meal> actual = Stream.of(meal, meal_2, meal_3, meal_4)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertThat(expected).containsExactlyInAnyOrderElementsOf(actual);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertThat(service.get(MEAL_ID, USER_ID)).usingRecursiveComparison().isEqualTo(getUpdated());
    }

    @Test
    public void updateOther() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertThat(created).isEqualTo(newMeal);
        assertThat(service.get(newId, USER_ID)).isEqualTo(newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(meal.getDateTime(), "Dublicate", 1000), USER_ID));
    }

}