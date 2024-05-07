package org.example.service;

import org.example.model.UserDetail;
import org.example.model.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    public List<UserDetail> filterUserDetails(UserDetail userDetail) {
        if (userDetail == null || userDetail.isEmpty()) {
            return userDetailRepository.findAll();
        }

        Specification<UserDetail> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (userDetail.getGender() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("gender"), userDetail.getGender()));
            }

            if (userDetail.getEmail() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("email"), userDetail.getEmail()));
            }

            if (userDetail.getPhone() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("phone"), userDetail.getPhone()));
            }

            if (userDetail.getCell() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cell"), userDetail.getCell()));
            }

            if (userDetail.getNat() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("nat"), userDetail.getNat()));
            }

            if (userDetail.getName_title() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("name_title"), userDetail.getName_title()));
            }

            if (userDetail.getName_first() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("name_first"), userDetail.getName_first()));
            }

            if (userDetail.getName_last() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("name_last"), userDetail.getName_last()));
            }

            if (userDetail.getLocation_city() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_city"), userDetail.getLocation_city()));
            }

            if (userDetail.getLocation_state() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_state"), userDetail.getLocation_state()));
            }

            if (userDetail.getLocation_country() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_country"), userDetail.getLocation_country()));
            }

            if (userDetail.getLocation_postcode() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_postcode"), userDetail.getLocation_postcode()));
            }

            if (userDetail.getLocation_street_number() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_street_number"), userDetail.getLocation_street_number()));
            }

            if (userDetail.getLocation_street_name() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_street_name"), userDetail.getLocation_street_name()));
            }

            if (userDetail.getLocation_coordinates_latitude() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_coordinates_latitude"), userDetail.getLocation_coordinates_latitude()));
            }

            if (userDetail.getLocation_coordinates_longitude() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_coordinates_longitude"), userDetail.getLocation_coordinates_longitude()));
            }

            if (userDetail.getLocation_timezone_offset() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_timezone_offset"), userDetail.getLocation_timezone_offset()));
            }

            if (userDetail.getLocation_timezone_description() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("location_timezone_description"), userDetail.getLocation_timezone_description()));
            }

            if (userDetail.getLogin_uuid() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_uuid"), userDetail.getLogin_uuid()));
            }

            if (userDetail.getLogin_username() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_username"), userDetail.getLogin_username()));
            }

            if (userDetail.getLogin_password() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_password"), userDetail.getLogin_password()));
            }

            if (userDetail.getLogin_salt() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_salt"), userDetail.getLogin_salt()));
            }

            if (userDetail.getLogin_md5() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_md5"), userDetail.getLogin_md5()));
            }

            if (userDetail.getLogin_sha1() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_sha1"), userDetail.getLogin_sha1()));
            }

            if (userDetail.getLogin_sha256() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("login_sha256"), userDetail.getLogin_sha256()));
            }

            if (userDetail.getDob_date() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("dob_date"), userDetail.getDob_date()));
            }

            if (userDetail.getDob_age() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("dob_age"), userDetail.getDob_age()));
            }

            if (userDetail.getRegistered_date() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("registered_date"), userDetail.getRegistered_date()));
            }

            if (userDetail.getRegistered_age() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("registered_age"), userDetail.getRegistered_age()));
            }

            if (userDetail.getId_name() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id_name"), userDetail.getId_name()));
            }

            if (userDetail.getId_value() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id_value"), userDetail.getId_value()));
            }

            if (userDetail.getPicture_large() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("picture_large"), userDetail.getPicture_large()));
            }

            if (userDetail.getPicture_medium() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("picture_medium"), userDetail.getPicture_medium()));
            }

            if (userDetail.getPicture_thumbnail() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("picture_thumbnail"), userDetail.getPicture_thumbnail()));
            }

            return predicate;
        };

        return userDetailRepository.findAll(spec);
    }

    public String getThumbnailUrl(Long id) {
        UserDetail userDetail = userDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserDetail not found"));
        return userDetail.getPicture_thumbnail();
    }
}
