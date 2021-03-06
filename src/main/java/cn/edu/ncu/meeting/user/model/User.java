package cn.edu.ncu.meeting.user.model;

import cn.edu.ncu.meeting.meeting.model.Meeting;
import cn.edu.ncu.meeting.meeting.model.MeetingJoinUser;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * The User model.
 * @author lorry
 * @author lin864464995@163.com
 * @see org.springframework.security.core.userdetails.UserDetails
 */
@Entity
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String name;

    @JsonIgnore
    private String password;

    private String IdCard;

    @Column(columnDefinition = "Boolean default false")
    private boolean gender = false;

    private String organization;

    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoleSet = new HashSet<>();

    @OneToMany(mappedBy = "holdUser")
    private Set<Meeting> myMeetings = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MeetingJoinUser> meetingJoinUserSet = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    public Set<MeetingJoinUser> getMeetingJoinUserSet() {
        return meetingJoinUserSet;
    }

    @JsonIgnore
    public Set<Meeting> getMyMeetings() {
        return myMeetings;
    }

    /**
     * Get User Join Meeting Info
     * @return [
     *      {
     *          "id": meeting id: long,
     *          "name": meeting name: String,
     *          "location": location: String,
     *          "time": meeting hold time: Timestamp,
     *          "hotel": meeting hold hotel: String,
     *          "comment": meeting comment: String,
     *          "checkIn": have user check in: Boolean
     *      },
     *      ...
     * ]
     */
    @JsonIgnore
    public List<JSONObject> getJoinMeetings() {
        List<JSONObject> result = new ArrayList<>(meetingJoinUserSet.size());
        for (MeetingJoinUser meetingJoinUser : meetingJoinUserSet) {
            JSONObject one = new JSONObject();
            final Meeting meeting = meetingJoinUser.getMeeting();

            one.put("id", meeting.getId());
            one.put("name", meeting.getName());
            one.put("location", meeting.getLocation());
            one.put("time", meeting.getTime());
            one.put("star", meeting.getStar());
            one.put("hotel", meeting.getHotel());
            one.put("comment", meeting.getComment());

            one.put("checkIn", meetingJoinUser.isCheckIn());

            result.add(one);
        }
        return result;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoleSet;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
