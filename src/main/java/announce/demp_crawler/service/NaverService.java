package announce.demp_crawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.openqa.selenium.By.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverService {

    private static final String DRIVER_ID = "webdriver.chrome.driver";
    private static final String DRIVER_DIR = "C:/chromedriver/chromedriver.exe";

    private static final String NAVER_RECRUIT_SITE = "https://recruit.navercorp.com/rcrt/list.do?subJobCdArr=1010001";

    private WebDriver webDriver;

    public void naverCrawl() {
        System.setProperty(DRIVER_ID, DRIVER_DIR);

        ChromeOptions chromeOptions = new ChromeOptions();
        webDriver = new ChromeDriver(chromeOptions);

        webDriver.get(NAVER_RECRUIT_SITE);

        // 채용 공고 분야별 목록
        List<WebElement> techList = webDriver
                .findElements(cssSelector("#naver > div > section > div > div > div.lnb > div > div > form > div.filter_box > div:nth-child(2) > ul > li:nth-child(1) > ul > li"));

        techListClick(techList);
        loadDetailAnnounceList();

        List<WebElement> cardList = webDriver.findElements(cssSelector("#naver > div > section > div > div > div.sub_container > div.card_wrap > ul > li"));
        for (WebElement webElement : cardList) {
            webElement.findElement(tagName("a")).click();
            webDriver.navigate().back();
        }
        webDriver.close();
    }

    private void techListClick(List<WebElement> techList) {
        for (int i = 0; i < techList.size(); i ++) {
            WebElement button = techList.get(i).findElement(className("btn_tree"));
            if (i != 0){
                button.click();
            }
            List<WebElement> btns = techList.get(i).findElements(className("btn_filter_select"));
            for (int j = 0; j < btns.size(); j ++) {
                if (i != 0 || j != 0) {
                    btns.get(j).click();
                }
            }
        }
    }

    private void loadDetailAnnounceList() {
        WebElement listBtn = webDriver.findElement(className("list_btn"));
        String totalPerCurrent = webDriver.findElement(cssSelector("#naver > div > section > div > div > div.sub_container > div.card_wrap > div > span"))
                .getText().replaceAll(" ","");
        log.info("totalPerCurrent={}",totalPerCurrent);
        int currentCount = Integer.parseInt(totalPerCurrent.split("/")[0]);
        int totalCount = Integer.parseInt(totalPerCurrent.split("/")[1]);
        if (currentCount < totalCount){
            int repeat = (totalCount - 1) / currentCount;
            for (int i = 0; i < repeat; i++) {
                listBtn.click();
            }
        }
    }
}
