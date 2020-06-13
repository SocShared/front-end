package ml.socshared.frontend.service.impl;

import groovyjarjarpicocli.CommandLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.BStatClient;
import ml.socshared.frontend.client.StorageClient;
import ml.socshared.frontend.domain.bstat.model.ChartPoint;
import ml.socshared.frontend.domain.bstat.response.*;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.model.LinearChart;
import ml.socshared.frontend.domain.model.SocialNetwork;
import ml.socshared.frontend.domain.storage.GroupPostStatus;
import ml.socshared.frontend.domain.storage.PostType;
import ml.socshared.frontend.domain.storage.response.PostStatus;
import ml.socshared.frontend.domain.storage.response.PublicationResponse;
import ml.socshared.frontend.service.BStatService;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
@Slf4j
@RequiredArgsConstructor
public class BStatServiceImpl implements BStatService {

    private final BStatClient bStatClient;
    private final StorageClient storageCLient;


    @Override
    public void getGroupStatPage(UUID systemGroupId, Model model, Pageable pageable, String accessToken) {
//        TimeSeries<Integer> groupOnline = new TimeSeries<>();
//        LocalDateTime beginDate = LocalDateTime.now().minusMinutes(50);
//        List<TimePoint<Integer>> online =Arrays.asList(
//                new TimePoint<Integer>(100, beginDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(115, beginDate.plusMinutes(10).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(90, beginDate.plusMinutes(20).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(95, beginDate.plusMinutes(30).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(120, beginDate.plusMinutes(40).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(118, beginDate.plusMinutes(50).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
//        );
//        List<TimePoint<Integer>> subscribers =Arrays.asList(
//                new TimePoint<Integer>(1000, beginDate.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(1000, beginDate.plusMinutes(10).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(1005, beginDate.plusMinutes(20).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(1005, beginDate.plusMinutes(30).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(1010, beginDate.plusMinutes(40).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint<Integer>(1008, beginDate.plusMinutes(50).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
//        );
//
//
//        Set<GroupPostStatus> status = new HashSet<>();
//        status.add(new GroupPostStatus(systemGroupId, PostStatus.PUBLISHED));
//        status.add(new GroupPostStatus(UUID.randomUUID(), PostStatus.AWAITING));
//        Page<PublicationResponse> postsPage = new PageImpl<>(Arrays.asList(
//                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "Это очень интересная публикация", new Date(),
//                        LocalDateTime.now(), status, PostType.IN_REAL_TIME),
//                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "Это другая интересная публикация", new Date(),
//                        LocalDateTime.now(), status, PostType.IN_REAL_TIME),
//                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "!! Это другая интересная публикация !!", new Date(),
//                LocalDateTime.now(), status, PostType.IN_REAL_TIME),
//                new PublicationResponse(UUID.randomUUID(), UUID.randomUUID(), "------------------------ssd-------------\n\n\nsadasdas", new Date(),
//                        LocalDateTime.now(), status, PostType.IN_REAL_TIME)
//        ));

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC);
        GroupInfoResponse groupInfo = bStatClient.getStatisticOfGroup(systemGroupId, SocialNetwork.VK, currentTime.minusDays(2).toEpochSecond(),
                currentTime.plusDays(2).toEpochSecond(), "Bearer "+accessToken);
        Page<PublicationResponse> postsPage =  storageCLient.getPostList(systemGroupId, pageable.getPageNumber(), pageable.getPageSize(), "Bearer "+accessToken);

        Pair<List<Integer>, List<String>> onlineChart = convertArrayTimePointToChartView(groupInfo.getOnline().getData());
        Pair<List<Integer>, List<String>> subscribersChart = convertArrayTimePointToChartView(groupInfo.getSubscribers().getData());

        List<PostStatus> statuses = new ArrayList<>(postsPage.getNumberOfElements());
        for(PublicationResponse post : postsPage) {
            boolean isFound = false;
            for(GroupPostStatus s : post.getPostStatus()) {

                if(systemGroupId.equals(s.getGroupId())) {
                    statuses.add(s.getPostStatus());
                    isFound = true;
                    break;
                }
            }
            if(!isFound) {
                log.error("Internal Error: invalid state data base of storage service. Storage service returned " +
                                "post (id: {}), which don't have postStatus current group (id: {})", post.getPublicationId(),
                        systemGroupId);
                statuses.add(PostStatus.NOT_SUCCESSFUL);
            }
        }


        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                        new BreadcrumbElement("social", "Социальные аккунты"),
                        new BreadcrumbElement("social", "Группы ВК")),
                "Статистика и публикации"));
        model.addAttribute("online_chart_data", onlineChart.getFirst());
        model.addAttribute("online_chart_labels", onlineChart.getSecond());
        model.addAttribute("subscribers_chart_data", subscribersChart.getFirst());
        model.addAttribute("subscribers_chart_labels", subscribersChart.getSecond());
        model.addAttribute("posts", postsPage);
        model.addAttribute("statuses", statuses);
    }

    @Override
    public void getPostStatPage(UUID systemGroupId, UUID systemPostId,SocialNetwork soc, Model model, String accessToken) {
//        PostInfoByTimeResponse response = new PostInfoByTimeResponse();
//        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(50);
//        List<TimePoint<Integer>> variability = Arrays.asList(
//                new TimePoint(150, dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint(160, dateTime.plusMinutes(10).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint(162, dateTime.plusMinutes(20).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint(165, dateTime.plusMinutes(30).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint(180, dateTime.plusMinutes(40).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()),
//                new TimePoint(215, dateTime.plusMinutes(50).atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
//        );
//        Double Coeff = 0.25;
//        response.setVariabilityNumberComments(new DataList<>(variability.size(), variability));
//        response.setVariabilityNumberShares(new DataList<>(variability.size(), variability));
//        response.setVariabilityNumberLikes(new DataList<>(variability.size(), variability));
//        response.setVariabilityNumberViews(new DataList<>(variability.size(), variability));

        ZonedDateTime current = ZonedDateTime.now(ZoneOffset.UTC);
        PostInfoByTimeResponse response = bStatClient.getStatisticOfPost(systemGroupId, systemPostId, soc,current.minusDays(2).toEpochSecond(),
                current.plusDays(2).toEpochSecond(), "Bearer " + accessToken);

        List<LinearChart<Integer, String>> charts = new ArrayList<>(4);
        Pair<List<Integer>, List<String>> commentsChart = convertArrayTimePointToChartView(
                response.getVariabilityNumberComments().getData());
        charts.add(new LinearChart<>(commentsChart.getFirst(), commentsChart.getSecond(), "Комментарии"));

        Pair<List<Integer>, List<String>> sharesChart = convertArrayTimePointToChartView(
                response.getVariabilityNumberShares().getData());
        charts.add(new LinearChart<>(sharesChart.getFirst(), sharesChart.getSecond(), "Распростарнение записи пользователями"));

        Pair<List<Integer>, List<String>> likesChart = convertArrayTimePointToChartView(
                response.getVariabilityNumberLikes().getData());
        charts.add(new LinearChart<>(likesChart.getFirst(), likesChart.getSecond(), "Отметки мне нравится"));

        Pair<List<Integer>, List<String>> viewsChart = convertArrayTimePointToChartView(
                response.getVariabilityNumberViews().getData());
        charts.add(new LinearChart<>(viewsChart.getFirst(), viewsChart.getSecond(), "Количество просмотров"));

        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("social", "Социальные аккунты"),
                new BreadcrumbElement("social", "Группы ВК"),
                new BreadcrumbElement("social", "Статистика и публикации")),
                "Статистика публикации"));

        model.addAttribute("charts", charts);

    }




    private Pair<List<Integer>, List<String>> convertArrayTimePointToChartView(List<TimePoint<Integer>> data) {
       List<Integer> dataList = new LinkedList<>();
       List<String> labelList = new LinkedList<>();
       for(TimePoint<Integer> el : data) {
           dataList.add(el.getValue());
           labelList.add(Instant.ofEpochMilli(el.getDateTime()).atZone(ZoneOffset.UTC).toString());
       }
       return Pair.of(dataList, labelList);
    }
}
