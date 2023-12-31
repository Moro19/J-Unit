import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

//import java.lang.module.Configuration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class JUnitSimpleTest {
    @DisplayName("Демонстрационный тест")
    @Test
    void simpleTest() {
        Assertions.assertTrue(3 > 2);
    }

    @BeforeEach
    void setUp() {
        Configuration.browser = "firefox";
        Configuration.holdBrowserOpen = true;
        open("https://google.com");
    }

    @CsvSource({
            "Allure testops, https://qameta.io",
            "Selenide, https://selenide.org"
    })
    // OR!!!
    @CsvFileSource(resources = "/testData.csv")
    @ParameterizedTest(name = "Адрес {1} должен быть в выдаче гугла по запросу {0}")
    //
    @Tags({@Tag("BLOCKER"), @Tag("UI_TEST")})
    void productSiteUrlShouldBePresentInResultsOfSearchInGoggleByProductNameQuery(
            String productName,
            String productUrl
    ) {
        $("[name=q]").setValue(productName).pressEnter();
        $("[id=search]").shouldHave(text(productUrl));
    }

    @ValueSource(
            strings = {"Allure testops", "Selenide"}
    )
    @ParameterizedTest(name = "Адрес {1} должен быть в выдаче гугла по запросу {0}")
    //
    @Tags({@Tag("BLOCKER"), @Tag("UI_TEST")})
    void searchResultsCountTest(String productName) {
        $("[name=q]").setValue(productName).pressEnter();
        $$("div[class='g']").shouldHave(CollectionCondition.sizeGreaterThan(5));
    }


}
