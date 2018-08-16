package app.com.esenatenigeria.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.List;

import app.com.esenatenigeria.room.CommitteeTypeConverter;
import app.com.esenatenigeria.room.EducationTypeConverter;
import app.com.esenatenigeria.room.PerviousOfficeConverter;

/**
 * Created by dev on 10/5/18.
 */

public class SenatorDetailModel {
    /**
     * statusCode : 200
     * message : Success
     * data : {"committee":[],"user_id":213,"name":"fWukhD+3e6WTJ5yVZ2dvWOTJ3QrdKLKYbwurHY/VOI4=","phoneNo":"9kq6WbKMsfYBwhuaFE9yCg==","phoneNo2":"","email":"dATkBzRvcRgA2TdGeYZUmBBshl/5/9TUNmgkE3m4TOQ=","email2":"","password":"550e1bafe077ff0b0b67f4e32f29d751","accessToken":null,"deviceToken":null,"deviceType":null,"appVersion":null,"isBlocked":0,"isDeleted":0,"profilePicURL":null,"chamber":"FEVnh+78kJdEd0hVQJ8Uzw==","isLeadership":0,"address":"","constituency":"0cR9mx5AzFvJ3fx6hZS1gw==","age":"49zwLXxjcAG1yFXjRCIIJA==","party":"2kPw0s0LcbOaTTmIevEYMsPwPN7m75qr7tBmbcAOEw0=","bio":"X71VXF5dtxkyv5ND28XxwHmMoLvHcvQlL96Hlr3KZ2B3OwV4HVdEhvxZEh3xjXhM","state":"dnWiEL1P+pZqxc0l8u3kDw==","achievementAndAwards":{"interests":"T2sgPW3HqxvLjPo8/5AQDrzoOiGZfUVIgz/Zm36Y7+Y=","achievements":"SiMqAR3rHEfEyrzjwIGKUoNYUe65V0mmGzqY01oTJUIxQ5/wOPLzFzfSuMawSNsEwJbywirGLbUFVPvBZdNK0Q==","awards":"x1HRfnX6oSNN9C2z9EWs5A=="},"position":"iNvIHHbawlsCSDhJH9/53w==","education":[{"institution":"yH89DuI8TMWNJKvyfen2RlQ92LHqwJY3D5R4c3utiLc=","dateOfGraduation":"5ODwJSfpkeoHWSRK6JaFBg==","certificate":"wDyerM5i8VXmOhzsUcKYbw=="},{"institution":"qtRiL6dpW0weg093HamyRypQvMM82v+eI1kcaMcRq7M=","dateOfGraduation":"KD28YMNXrj9ed4AxCGgp0A==","certificate":"CaHstnqg6n8z0ZKQWogmmw=="},{"institution":"5wTpyUeOBm3YMS7Zhh2kftZnRoIUYC7SWJhkEA7zIyw=","dateOfGraduation":"+MVnzVTXP/XAN/JBOzyrCg==","certificate":"KD28YMNXrj9ed4AxCGgp0A=="},{"institution":"0NdR0HGup++v6pV95BtrxJ4iAeP15vMCBspYdfwTUZo=","dateOfGraduation":"/RCDE65Cl7/2yqTb9kqJLw==","certificate":"ExUu9JPKUdbIP0fx9eayRw=="},{"institution":"dj8j/Vrju1r/gos7zj3vjbmZUuVbSZFjMXBr5Bv43EA=","dateOfGraduation":"ilUv1xnOHdKjBcj3sHr9bQ==","certificate":"vB+px3JG1m+f7MKtXMoZvA=="},{"institution":"dj8j/Vrju1r/gos7zj3vjbmZUuVbSZFjMXBr5Bv43EA=","dateOfGraduation":"tO6gz8FxeEKPDCiSWuEDZw==","certificate":"ceYMOu19XJZ1R/crcQE/fw=="}],"previousOffice":[{"from":"w1TZ4qEPRC+WHOik9yrE+Q==","to":"qj9wJwe98TD2bKKNqMav9w==","rank":"jFe6TMX58/0DlGF/g86Xqg=="},{"from":"P9/Amp8QteLdxyt/Q5AVnA==","to":"w1TZ4qEPRC+WHOik9yrE+Q==","rank":"jFe6TMX58/0DlGF/g86Xqg=="},{"from":"H27WnWzC9iNf9Ju+tl7MEg==","to":"Ljps31KAT+3ZNYFN9S4pZQ==","rank":"n3vTp/whTFG1Dt+ezih0aGeZoO5NhopK2DmCv2iQ1o0="}],"codeUpdatedAt":"2018-05-08T06:43:07.000Z"}
     */

    private int statusCode;
    private String message;
    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBeanX {
        /**
         * count : 106
         * data : [{"user_id":213,"profilePicURL":null,"name":"fWukhD+3e6WTJ5yVZ2dvWOTJ3QrdKLKYbwurHY/VOI4="}]
         */

        private int count;
        private List<DataBean> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }
    }

    @Entity(tableName = "SenatorsDetail", indices = {@Index(value = {"user_id"},
            unique = true)})
    public static class DataBean {

        @PrimaryKey
        @NonNull
        private int user_id;

        @Embedded
        private ProfilePicURLBean profilePicURL;

        private String name;
        private String phoneNo;
        private String phoneNo2;
        private String email;
        private String email2;
        private String password;
//        private Object accessToken;
//        private Object deviceToken;
//        private Object deviceType;
//        private Object appVersion;
        private int isBlocked;
        private int isDeleted;
        private String chamber;
        private int isLeadership;
        private String address;
        private String constituency;
        private String age;
        private String party;
        private String bio;
        private String state;
        private String position;

        public int getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        private int isAdmin;
        private String codeUpdatedAt;

        @Embedded
        private AchievementAndAwardsBean achievementAndAwards;
        @TypeConverters(CommitteeTypeConverter.class)
        private List<Committee> committee;
        @TypeConverters(EducationTypeConverter.class)
        private List<EducationBean> education;
        @TypeConverters(PerviousOfficeConverter.class)
        private List<PreviousOfficeBean> previousOffice;

        public String getCodeUpdatedAt() {
            return codeUpdatedAt;
        }

        public void setCodeUpdatedAt(String codeUpdatedAt) {
            this.codeUpdatedAt = codeUpdatedAt;
        }

        public ProfilePicURLBean getProfilePicURL() {
            return profilePicURL;
        }

        public void setProfilePicURL(ProfilePicURLBean profilePicURL) {
            this.profilePicURL = profilePicURL;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPhoneNo2() {
            return phoneNo2;
        }

        public void setPhoneNo2(String phoneNo2) {
            this.phoneNo2 = phoneNo2;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail2() {
            return email2;
        }

        public void setEmail2(String email2) {
            this.email2 = email2;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getIsBlocked() {
            return isBlocked;
        }

        public void setIsBlocked(int isBlocked) {
            this.isBlocked = isBlocked;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getChamber() {
            return chamber;
        }

        public void setChamber(String chamber) {
            this.chamber = chamber;
        }

        public int getIsLeadership() {
            return isLeadership;
        }

        public void setIsLeadership(int isLeadership) {
            this.isLeadership = isLeadership;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConstituency() {
            if (constituency == null)
                return "";
            else
                return constituency;
        }

        public void setConstituency(String constituency) {
            this.constituency = constituency;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getParty() {
            return party;
        }

        public void setParty(String party) {
            this.party = party;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getState() {
            if (state == null)
                return "";
            else
                return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public AchievementAndAwardsBean getAchievementAndAwards() {
            return achievementAndAwards;
        }

        public void setAchievementAndAwards(AchievementAndAwardsBean achievementAndAwards) {
            this.achievementAndAwards = achievementAndAwards;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public List<Committee> getCommittee() {
            return committee;
        }

        public void setCommittee(List<Committee> committee) {
            this.committee = committee;
        }

        public List<EducationBean> getEducation() {
            return education;
        }

        public void setEducation(List<EducationBean> education) {
            this.education = education;
        }

        public List<PreviousOfficeBean> getPreviousOffice() {
            return previousOffice;
        }

        public void setPreviousOffice(List<PreviousOfficeBean> previousOffice) {
            this.previousOffice = previousOffice;
        }

        public static class AchievementAndAwardsBean {
            /**
             * interests : T2sgPW3HqxvLjPo8/5AQDrzoOiGZfUVIgz/Zm36Y7+Y=
             * achievements : SiMqAR3rHEfEyrzjwIGKUoNYUe65V0mmGzqY01oTJUIxQ5/wOPLzFzfSuMawSNsEwJbywirGLbUFVPvBZdNK0Q==
             * awards : x1HRfnX6oSNN9C2z9EWs5A==
             */
            private String interests;
            private String achievements;
            private String awards;

            public String getInterests() {
                return interests;
            }

            public void setInterests(String interests) {
                this.interests = interests;
            }

            public String getAchievements() {
                return achievements;
            }

            public void setAchievements(String achievements) {
                this.achievements = achievements;
            }

            public String getAwards() {
                return awards;
            }

            public void setAwards(String awards) {
                this.awards = awards;
            }
        }

        public static class EducationBean {
            /**
             * institution : yH89DuI8TMWNJKvyfen2RlQ92LHqwJY3D5R4c3utiLc=
             * dateOfGraduation : 5ODwJSfpkeoHWSRK6JaFBg==
             * certificate : wDyerM5i8VXmOhzsUcKYbw==
             */

            private String institution;
            private String dateOfGraduation;
            private String certificate;

            public String getInstitution() {
                return institution;
            }

            public void setInstitution(String institution) {
                this.institution = institution;
            }

            public String getDateOfGraduation() {
                return dateOfGraduation;
            }

            public void setDateOfGraduation(String dateOfGraduation) {
                this.dateOfGraduation = dateOfGraduation;
            }

            public String getCertificate() {
                return certificate;
            }

            public void setCertificate(String certificate) {
                this.certificate = certificate;
            }
        }

        public static class PreviousOfficeBean {
            /**
             * from : w1TZ4qEPRC+WHOik9yrE+Q==
             * to : qj9wJwe98TD2bKKNqMav9w==
             * rank : jFe6TMX58/0DlGF/g86Xqg==
             */

            private String from;
            private String to;
            private String rank;

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }
        }

        public static class Committee {


            /**
             * committee_id : 10
             * name : X66o8AApWwGiVg1RnWkVQLflA3Mb/po2CscebJuzpcY=
             * position : yk0MC51otkUbkkqdjBhyNA==
             */

            private int committee_id;
            private String name;
            private String position;

            public int getCommittee_id() {
                return committee_id;
            }

            public void setCommittee_id(int committee_id) {
                this.committee_id = committee_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }

        public static class ProfilePicURLBean {
            /**
             * original : https://s3.ap-south-1.amazonaws.com/kittydev/esenate/profilePic/profilePic_216_91.jpg
             * thumbnail : https://s3.ap-south-1.amazonaws.com/kittydev/esenate/profilePicThumb/profileThumb_216_91.jpg
             */
            private String original;
            private String thumbnail;

            public String getOriginal() {
                return original;
            }

            public void setOriginal(String original) {
                this.original = original;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
