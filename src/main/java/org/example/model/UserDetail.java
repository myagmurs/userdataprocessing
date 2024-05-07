package org.example.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String gender;

    private String email;

    private String phone;

    private String cell;

    private String nat;

    private String name_title;

    private String name_first;

    private String name_last;

    private String location_city;

    private String location_state;

    private String location_country;

    private String location_postcode;

    private String location_street_number;

    private String location_street_name;

    private String location_coordinates_latitude;

    private String location_coordinates_longitude;

    private String location_timezone_offset;

    private String location_timezone_description;

    private String login_uuid;

    private String login_username;

    private String login_password;

    private String login_salt;

    private String login_md5;

    private String login_sha1;

    private String login_sha256;

    private String dob_date;

    private String dob_age;

    private String registered_date;

    private String registered_age;

    private String id_name;

    private String id_value;

    private String picture_large;

    private String picture_medium;

    private String picture_thumbnail;

    public boolean isEmpty() {
        return gender == null && email == null && phone == null && cell == null && nat == null
                && name_title == null && name_first == null && name_last == null
                && location_city == null && location_state == null && location_country == null
                && location_postcode == null && location_street_number == null && location_street_name == null
                && location_coordinates_latitude == null && location_coordinates_longitude == null
                && location_timezone_offset == null && location_timezone_description == null
                && login_uuid == null && login_username == null && login_password == null
                && login_salt == null && login_md5 == null && login_sha1 == null && login_sha256 == null
                && dob_date == null && dob_age == null && registered_date == null && registered_age == null
                && id_name == null && id_value == null && picture_large == null
                && picture_medium == null && picture_thumbnail == null;
    }
}
