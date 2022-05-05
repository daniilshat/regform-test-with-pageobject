package ru.daniilshat;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.daniilshat.pages.RegForm;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegFormTest {
    // Variables with java-faker
    Faker faker = new Faker();

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String mobile = "0123456789";
    String month = "January";
    String year = "1999";
    String subject = "Maths";
    String address = faker.address().fullAddress();
    String hobbie = "Reading";
    String imgPath = "img/1.jpg";
    String state = "NCR";
    String city = "Delhi";
    String gender = "Other";

    RegForm regFormPage = new RegForm();
    
    @BeforeAll
    static void setUP() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl =("https://demoqa.com");
    }

    @Test
    void fillRegForm() {
        regFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(email)
                .setGender(gender)
                .setNumber(mobile)
                .setBirthDate(month, year)
                .setSubject(subject)
                .setHobbies(hobbie)
                .uploadUserImage(imgPath)
                .setAddress(address)
                .stateAndCitySet(state, city);

        // Submit button
        $("#submit").click();

        // Some Checks
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".modal-body").shouldHave(
                text(firstName + " " + lastName),
                text(email),
                text("Other"),
                text(mobile),
                text("01 " + month + "," + year),
                text(subject),
                text("Reading"),
                text("1.jpg"),
                text(address),
                text("NCR Delhi")
        );

        // close the window
        $("#closeLargeModal").click();
    }
}
