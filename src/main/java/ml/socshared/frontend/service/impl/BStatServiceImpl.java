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
import ml.socshared.frontend.domain.model.form.DurationStat;
import ml.socshared.frontend.domain.storage.GroupPostStatus;
import ml.socshared.frontend.domain.storage.PostType;
import ml.socshared.frontend.domain.storage.response.PostStatus;
import ml.socshared.frontend.domain.storage.response.PublicationResponse;
import ml.socshared.frontend.exception.impl.HttpNotFoundException;
import ml.socshared.frontend.service.BStatService;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


@Service
@Slf4j
@RequiredArgsConstructor
public class BStatServiceImpl implements BStatService {

    private final BStatClient bStatClient;
    private final StorageClient storageCLient;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);


    @Override
    public void getGroupStatPage(UUID systemGroupId, SocialNetwork soc, Model model, DurationStat duration, Pageable pageable, String accessToken) {
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


        Pair<LocalDate, LocalDate> dt = convertDurationToTimes(duration);
        if(soc == SocialNetwork.VK) {
            getVkGroupStatPage(systemGroupId, model,dt.getFirst(), dt.getSecond(), pageable, accessToken);
        } else {
            getFbGroupStatPage(systemGroupId, model, dt.getFirst(), dt.getSecond(), pageable, accessToken);
        }

    }

    public void getVkGroupStatPage(UUID systemGroupId, Model model, LocalDate begin, LocalDate end, Pageable pageable, String accessToken) {
        GroupInfoResponse groupInfo = null;

       try {
           groupInfo = getGroupInfo(systemGroupId, SocialNetwork.VK, begin, end, accessToken);
       } catch (HttpNotFoundException exp) {
           log.warn("Info for group {} not found by period {} - {}", systemGroupId, begin, end);
           groupInfo = new GroupInfoResponse();
           groupInfo.setGroupId("");
           TimeSeries ts = new TimeSeries();
           ts.setSize(0);
           ts.setData(Collections.emptyList());
           ts.setBegin(begin);
           ts.setEnd(end);
           groupInfo.setOnline(ts);
           groupInfo.setSubscribers(ts);
           groupInfo.setSocialNetwork(SocialNetwork.VK);
           model.addAttribute("not_found_data_text", "Статистика по заданному периоду отстутсвует");
       }


        Page<PublicationResponse> postsPage =  storageCLient.getPostList(systemGroupId, pageable.getPageNumber(), pageable.getPageSize(), "Bearer "+accessToken);

        Pair<List<Integer>, List<String>> onlineChart = convertArrayTimePointToChartView(groupInfo.getOnline().getData());
        Pair<List<Integer>, List<String>> subscribersChart = convertArrayTimePointToChartView(groupInfo.getSubscribers().getData());

        List<PostStatus> statuses = getPostsStatus(postsPage, systemGroupId);



        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("social", "Социальные аккунты"),
                new BreadcrumbElement("social/vk/groups", "Группы ВК")),
                "Статистика и публикации"));
        model.addAttribute("charts", Arrays.asList(
                Pair.of("Онлайн", onlineChart),
                Pair.of("Количество одписчиков", subscribersChart)
        ));
        model.addAttribute("posts", postsPage);
        model.addAttribute("statuses", statuses);
        model.addAttribute("duration", new DurationStat(begin.format(formatter), end.format(formatter)));
    }

    public void getFbGroupStatPage(UUID systemGroupId, Model model, LocalDate begin, LocalDate end, Pageable pageable, String accessToken) {

        GroupInfoResponse groupInfo = null;

        try {
            groupInfo = getGroupInfo(systemGroupId, SocialNetwork.FACEBOOK, begin, end, accessToken);
        } catch (HttpNotFoundException exp) {
            log.warn("Info for group {} not found by period {} - {}", systemGroupId, begin, end);
            groupInfo = new GroupInfoResponse();
            groupInfo.setGroupId("");
            TimeSeries ts = new TimeSeries();
            ts.setSize(0);
            ts.setData(Collections.emptyList());
            ts.setBegin(begin);
            ts.setEnd(end);
            groupInfo.setOnline(ts);
            groupInfo.setSubscribers(ts);
            groupInfo.setSocialNetwork(SocialNetwork.VK);
            model.addAttribute("not_found_data_text", "Статистика по заданному периоду отстутсвует");
        }

        Page<PublicationResponse> postsPage =  storageCLient.getPostList(systemGroupId, pageable.getPageNumber(), pageable.getPageSize(), "Bearer "+accessToken);

        Pair<List<Integer>, List<String>> funChart = convertArrayTimePointToChartView(groupInfo.getSubscribers().getData());

        List<PostStatus> statuses = getPostsStatus(postsPage, systemGroupId);

        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("social", "Социальные аккунты"),
                new BreadcrumbElement("social/fb/groups", "Группы FB")),
                "Статистика и публикации"));
        model.addAttribute("charts", Collections.singletonList(Pair.of("Фанаты", funChart)));
        model.addAttribute("posts", postsPage);
        model.addAttribute("statuses", statuses);
        model.addAttribute("duration", new DurationStat(begin.format(formatter), end.format(formatter)));
    }

    @Override
    public void getPostStatPage(UUID systemGroupId, UUID systemPostId,SocialNetwork soc, Model model,DurationStat duration, String accessToken) {
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
        Pair<LocalDate, LocalDate> dt = convertDurationToTimes(duration);
        PostInfoByTimeResponse response = null;
        try{
            response = bStatClient.getStatisticOfPost(systemGroupId, systemPostId, soc,
                dt.getFirst().toEpochSecond(LocalTime.MIN,ZoneOffset.UTC),
                dt.getSecond().toEpochSecond(LocalTime.MIN,ZoneOffset.UTC), "Bearer " + accessToken);
        } catch (HttpNotFoundException exp) {
            log.warn("Info for post (GroupId: {}, PostId: {}) not found by period {} - {}", systemGroupId, dt.getFirst(), dt.getSecond());
            response = new PostInfoByTimeResponse();
            response.setGroupId("");
            TimeSeries ts = new TimeSeries();
            ts.setSize(0);
            ts.setData(Collections.emptyList());
            ts.setBegin( dt.getFirst());
            ts.setEnd(dt.getSecond());
            response.setVariabilityNumberLikes(ts);
            response.setVariabilityNumberViews(ts);
            response.setVariabilityNumberShares(ts);
            response.setVariabilityNumberComments(ts);
            model.addAttribute("not_found_data_text", "Статистика по заданному периоду отстутсвует");
    }



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

        String path = "social/vk/groups";
        if(soc == SocialNetwork.FACEBOOK) {
            path = "social/fb/groups";
        }
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("social", "Социальные аккунты"),
                new BreadcrumbElement(path, "Группы FB")),
                "Статистика и публикации"));

        model.addAttribute("charts", charts);
        model.addAttribute("stat_access", true);
        model.addAttribute("duration", new DurationStat(dt.getFirst().format(formatter), dt.getSecond().format(formatter)));
    }




    private Pair<List<Integer>, List<String>> convertArrayTimePointToChartView(List<TimePoint<Integer>> data) {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        List<Integer> dataList = new LinkedList<>();
       List<String> labelList = new LinkedList<>();
       for(TimePoint<Integer> el : data) {
           dataList.add(el.getValue());
           labelList.add(Instant.ofEpochMilli(el.getDateTime()).atZone(ZoneOffset.UTC).format(formatter));
       }
       return Pair.of(dataList, labelList);
    }

    private List<PostStatus> getPostsStatus(Page<PublicationResponse> postsPage, UUID systemGroupId) {
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
        return statuses;
    }

    private GroupInfoResponse getGroupInfo(UUID systemGroupId, SocialNetwork soc, LocalDate begin, LocalDate end,
                                           String accessToken) {
        try {
            return bStatClient.getStatisticOfGroup(systemGroupId, soc, begin.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC),
                    end.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC), "Bearer "+accessToken);
        } catch (Exception exp) {
            String msg = "";
            if(exp instanceof UndeclaredThrowableException) {
                msg = ((UndeclaredThrowableException) exp).getUndeclaredThrowable().getMessage();
            } else {
                msg = exp.getMessage();
            }
            log.error("BStat connection error: {}", msg);
            throw exp;
        }
    }

    Pair<LocalDate, LocalDate> convertDurationToTimes(DurationStat duration){
        LocalDate begin = null;
        LocalDate end = null;
        if(duration.getBegin() == null || duration.getEnd() == null ||
                "".equals(duration.getBegin()) || "".equals(duration.getEnd())) {
            begin = LocalDate.now(ZoneOffset.UTC);
            end = begin.plusDays(1);
        }  else {
            try{
                begin = LocalDate.parse(duration.getBegin(), formatter);
                end = LocalDate.parse(duration.getEnd(), formatter);
            }  catch(DateTimeParseException exp) {
                log.warn("error time parsing: {}", exp.getParsedString());
                begin = LocalDate.now(ZoneOffset.UTC);
                end = begin.plusDays(1);
            }

        }

        return Pair.of(begin, end);
    }
}
