import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTestMy {

    @BeforeAll
   static void setUp() {
        Configuration.browser = "firefox";
        Configuration.holdBrowserOpen = true;  }

    static Stream<Arguments> selenideLocaleDataProvider() {
        return Stream.of(
                Arguments.of(Reestr.Операторов, List.of("Активность подключения",
                        "Наименование оператора",
                        "Уникальный идентификатор оператора",
                        "Ответственная организация",
                        "ФИО ответственного лица",
                        "Рабочий телефон ответственного лица",
                        "Рабочая почта ответственного лица")),
                Arguments.of(Reestr.Участников, List.of("Активность подключения",
                        "Официальное наименование организации участника",
                        "Краткое наименование участника",
                        "Уникальный идентификатор участника",
                        "Уникальный идентификатор оператора",
                        "ФИО ответственного лица",
                        "ОГРН организации участника",
                        "Номер телефона ответственного лица",
                        "Адрес электронной почты ответственного лица",
                        "Подразделения"))
        );
    }


    @MethodSource("selenideLocaleDataProvider")
        @ParameterizedTest(name = "Для реестра {0} отображаются столбцы {1}")
    @Tag("BLOCKER")
    void selenideSiteShouldContainAllOfButtonsForGivenLocale(
            Reestr reestr,
            List<String> title
    ) {
        open("https://organizator.test.gas.gosedo.voskhod-11.ru/operators");
        $$("a.marker-title").find(text(reestr.name())).click();
        $$("div.header-cell-text")
                .filter(visible)
                .shouldHave(texts(title));
        open("https://organizator.test.gas.gosedo.voskhod-11.ru/participants");
        $$("a.marker-title").find(text(reestr.name())).click();
        $$("div.header-cell-text")
                .filter(visible)
                .shouldHave(texts(title));

    }

}
