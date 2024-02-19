package majorfolio.backend.root.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.payments.entity.BuyInfo;
import majorfolio.backend.root.domain.payments.repository.BuyInfoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {
    private final BuyInfoRepository buyInfoRepository;

    //매일 0시마다 실행
    @Scheduled(cron = "0 0 0 ? * *")
    public void run(){
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);

        List<BuyInfo> buyInfoList;
        buyInfoList = buyInfoRepository.findByStatusAndIsPay("afterPay", true);

        for(BuyInfo buyInfo : buyInfoList){
            LocalDate buyInfoDate= buyInfo.getUpdatedAt().toLocalDate();
            if(buyInfoDate.isBefore(oneWeekAgo)){
                buyInfo.setStatus("buyComplete");
                buyInfoRepository.save(buyInfo);
            }
        }
    }
}
