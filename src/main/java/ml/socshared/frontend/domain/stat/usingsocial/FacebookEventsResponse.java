package ml.socshared.frontend.domain.stat.usingsocial;

import lombok.Data;

@Data
public class FacebookEventsResponse {

    private long allEventsCount;
    private long allFacebookAccountEventsCount;
    private long saveAccountEventsCount;
    private long removeAccountEventsCount;
    private long urlFormingEventsCount;
    private long userInfoEventsCount;
    private long commentsOfPostEventsCount;
    private long commentOfPostEventsCount;
    private long subCommentsEventsCount;
    private long subCommentByIdEventsCount;
    private long userGroupsEventsCount;
    private long userGroupEventsCount;
    private long postsEventsCount;
    private long postByIdEventsCount;
    private long addPostEventsCount;
    private long updatePostEventsCount;
    private long deletePostEventsCount;

}

