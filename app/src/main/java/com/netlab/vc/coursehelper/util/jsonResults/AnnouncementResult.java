package com.netlab.vc.coursehelper.util.jsonResults;

/**
 * Created by dingfeifei on 16/12/23.
 */

public class AnnouncementResult{
    private Boolean success;
    private Announcement[] notifications;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Announcement[] getAnnouncements() {
        return notifications;
    }

    public void setAnnouncements(Announcement[] notifications) {
        this.notifications = notifications;
    }

    /**
     * Created by dingfeifei on 16/12/23.
     */

    public static class ForumResult{
        private Boolean success;
        private Forum[] postings;
        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Forum[] getForums() {
            return postings;
        }

        public void setAnnouncements(Forum[] postings) {
            this.postings = postings;
        }
    }
}