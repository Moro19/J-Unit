import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class JUnitSimpleTestMy {


    @BeforeEach
    void setUp() {
        Configuration.browser = "firefox";
        Configuration.holdBrowserOpen = true;
        open("https://organizator.test.gas.gosedo.voskhod-11.ru/participants");
    }

    @CsvSource({
            "Алтайский, Администрация Губернатора И Правительства Алтайского Края",
            "Рязанская, Правительство Рязанской области"
    })

    @ParameterizedTest(name = "Участник {1} должен быть в выдаче по запросу {0}")
    //
    @Tags({@Tag("BLOCKER"), @Tag("UI_TEST")})
    void productSiteUrlShouldBePresentInResultsOfSearchInGoggleByProductNameQuery(
            String searchInput,
            String participantName
    ) {
        $("input.input").click();
        $("input.input").setValue(searchInput).pressEnter();
        $("div.vsk-table-cell.cell-min-275").shouldHave(text(participantName));
    }

    @ValueSource(
            strings = {"алтайский", "рязанская"}
    )
    @ParameterizedTest(name = "Участник должен быть один")
    //
    @Tags({@Tag("BLOCKER"), @Tag("UI_TEST")})
    void searchResultsCountTest(String productName) {
        $("input.input").click();
        $("input.input").setValue(productName).pressEnter();
        $$("div.vsk-table-data").shouldHave(CollectionCondition.size(1));
    }

}

