package ml.socshared.frontend.service.impl;

import groovyjarjarpicocli.CommandLine;
import ml.socshared.frontend.domain.bstat.model.ChartPoint;
import ml.socshared.frontend.domain.bstat.response.TimePoint;
import ml.socshared.frontend.domain.bstat.response.TimeSeries;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.storage.GroupPostStatus;
import ml.socshared.frontend.domain.storage.PostType;
import ml.socshared.frontend.domain.storage.response.PostStatus;
import ml.socshared.frontend.domain.storage.response.PublicationResponse;
import ml.socshared.frontend.service.BStatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;


@Service
public class BStatServiceImpl implements BStatService {
    @Override
    public void getGroupStatPage(UUID systemGroupId, Model model, String accessToken) {
        TimeSeries<Integer> groupOnline = new TimeSeries<>();
        LocalDateTime beginDate = LocalDateTime.now().minusMinutes(50);
        List<TimePoint> online =Arrays.asList(
                new TimePoint<Integer>(100, beginDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(115, beginDate.plusMinutes(10).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(90, beginDate.plusMinutes(20).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(95, beginDate.plusMinutes(30).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(120, beginDate.plusMinutes(40).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(118, beginDate.plusMinutes(50).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
        );
        List<TimePoint> subscribers =Arrays.asList(
                new TimePoint<Integer>(1000, beginDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(1000, beginDate.plusMinutes(10).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(1005, beginDate.plusMinutes(20).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(1005, beginDate.plusMinutes(30).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(1010, beginDate.plusMinutes(40).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new TimePoint<Integer>(1008, beginDate.plusMinutes(50).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
        );


        Set<GroupPostStatus> status = new HashSet<>();
        status.add(new GroupPostStatus(systemGroupId, PostStatus.PUBLISHED));
        status.add(new GroupPostStatus(UUID.randomUUID(), PostStatus.AWAITING));
        Page<PublicationResponse> postsPage = new PageImpl<>(Arrays.asList(
                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "Это очень интересная публикация", new Date(),
                        LocalDateTime.now(), status, PostType.IN_REAL_TIME),
                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "Это другая интересная публикация", new Date(),
                        LocalDateTime.now(), status, PostType.IN_REAL_TIME),
                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "!! Это другая интересная публикация !!", new Date(),
                LocalDateTime.now(), status, PostType.IN_REAL_TIME),
                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "------------------------ssd-------------\n\n\nsadasdas", new Date(),
                        LocalDateTime.now(), status, PostType.IN_REAL_TIME)
        ));
        List<String> onlineLabels = new LinkedList();
        List<Integer> onlinePoints = new LinkedList<>();
        List<String> subscribersLabels = new LinkedList();
        List<Integer> subscribersPoints = new LinkedList<>();
        for(TimePoint<Integer> point : online) {
            onlinePoints.add(point.getValue());
            onlineLabels.add(LocalDateTime.ofInstant(Instant.ofEpochMilli(point.getDateTime()), ZoneOffset.UTC).toString());

        }

        for(TimePoint<Integer> point : subscribers) {
            subscribersPoints.add(point.getValue());
            subscribersLabels.add(LocalDateTime.ofInstant(Instant.ofEpochMilli(point.getDateTime()),ZoneOffset.UTC ).toString());
        }



        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                        new BreadcrumbElement("social", "Социальные аккунты"),
                        new BreadcrumbElement("social", "Группы ВК")),
                "Статистика и публикации"));
        model.addAttribute("online_chart_data", onlinePoints);
        model.addAttribute("online_chart_labels", onlineLabels);
        model.addAttribute("subscribers_chart_data", subscribersPoints);
        model.addAttribute("subscribers_chart_labels", subscribersLabels);
        model.addAttribute("posts", postsPage);
    }
}
