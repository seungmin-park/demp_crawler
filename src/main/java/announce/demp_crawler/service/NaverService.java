package announce.demp_crawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
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

    private static final String NAVER_RECRUIT_SITE = "https://recruit.navercorp.com/cnts/tech";

    private WebDriver webDriver;

    public void naverCrawl() throws InterruptedException {
        System.setProperty(DRIVER_ID, DRIVER_DIR);

        ChromeOptions chromeOptions = new ChromeOptions();
        webDriver = new ChromeDriver(chromeOptions);

        webDriver.get(NAVER_RECRUIT_SITE);

        // 채용 공고 분야별 목록
        List<WebElement> positions = webDriver.findElements(cssSelector("#naver > div > section.section_info_detail > div > div:nth-child(1) > ul > li"));

        for (WebElement position : positions) {
            Thread.sleep(1000);
            WebElement linkByPosition = position.findElement(tagName("a"));
            String positionTitle = position.findElement(className("title")).getText();
            log.info("positionTitle={}", positionTitle);
            linkByPosition.click();
            List<WebElement> detailAnnounces = webDriver.findElements(cssSelector("#naver > div > section > div > div > div.sub_container > div.card_wrap > ul > li"));
            for (WebElement detailAnnounce : detailAnnounces) {
                WebElement linkByAnnounce = detailAnnounce.findElement(tagName("a"));
                linkByAnnounce.click();
                String announceTitle = webDriver.findElement(className("card_title")).getText();
                log.info("title={}", announceTitle);
                List<WebElement> detailBox = webDriver.findElements(className("detail_box"));
                for (WebElement box : detailBox) {
                }
            }
            log.info("detail count={}", detailAnnounces.size());
        }
        webDriver.close();
    }
}
