package announce.demp_crawler.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaverServiceTest {

    private NaverService naverService = new NaverService();

    @Test
    @DisplayName("webDriverTest")
    void webDriverTest() throws Exception {
        //given
        naverService.naverCrawl();
        //when

        //then
    }
}