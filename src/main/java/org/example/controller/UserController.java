package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.User;
import org.example.model.UserDetail;
import org.example.service.UserDetailService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${file.path}")
    private String filePath;

    @Operation(summary = "'import users': Get <n> sample user info from external API")
    @GetMapping("/get-sample-users")
    public ResponseEntity<List<User>> getSampleUsers(@RequestParam int numberOfSamples) throws Exception {
        String url = apiUrl + numberOfSamples;
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(userService.saveSampleUsers(response));
    }

    @Operation(summary = "'export users': Get users from DB in CSV format")
    @GetMapping("/get-users-as-csv")
    public ResponseEntity<String> getUsersAsCSV() throws Exception {
        return ResponseEntity.ok(userService.getUserInfoAsCsv());
    }

    @Operation(summary = "'export users': Get users from DB in CSV format as a file")
    @GetMapping("/get-users-as-csv-file")
    public ResponseEntity<InputStreamResource> getUsersAsCSVFile() throws Exception {
        String csv = userService.getUserInfoAsCsv();

        ByteArrayInputStream bis = new ByteArrayInputStream(csv.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(bis));
    }

    @Operation(summary = "'export users': Get users from DB in CSV format as a file")
    @GetMapping("/get-users-as-csv-file-with-name")
    public ResponseEntity<String> getUsersAsCSVFile(@RequestParam String fileName) throws Exception {
        String csv = userService.getUserInfoAsCsv();

        byte[] csvBytes = csv.getBytes();

        Path path = Paths.get(filePath + fileName);

        Files.write(path, csvBytes);

        return ResponseEntity.ok("CSV data has been written to: " + path);
    }

    @Operation(summary = "'show users': Filter UserDetail entities based on provided fields")
    @GetMapping("/filter-user-details")
    public List<UserDetail> filterUserDetails(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String cell,
            @RequestParam(required = false) String nat,
            @RequestParam(required = false) String name_title,
            @RequestParam(required = false) String name_first,
            @RequestParam(required = false) String name_last,
            @RequestParam(required = false) String location_city,
            @RequestParam(required = false) String location_state,
            @RequestParam(required = false) String location_country,
            @RequestParam(required = false) String location_postcode,
            @RequestParam(required = false) String location_street_number,
            @RequestParam(required = false) String location_street_name,
            @RequestParam(required = false) String location_coordinates_latitude,
            @RequestParam(required = false) String location_coordinates_longitude,
            @RequestParam(required = false) String location_timezone_offset,
            @RequestParam(required = false) String location_timezone_description,
            @RequestParam(required = false) String login_uuid,
            @RequestParam(required = false) String login_username,
            @RequestParam(required = false) String login_password,
            @RequestParam(required = false) String login_salt,
            @RequestParam(required = false) String login_md5,
            @RequestParam(required = false) String login_sha1,
            @RequestParam(required = false) String login_sha256,
            @RequestParam(required = false) String dob_date,
            @RequestParam(required = false) String dob_age,
            @RequestParam(required = false) String registered_date,
            @RequestParam(required = false) String registered_age,
            @RequestParam(required = false) String id_name,
            @RequestParam(required = false) String id_value,
            @RequestParam(required = false) String picture_large,
            @RequestParam(required = false) String picture_medium,
            @RequestParam(required = false) String picture_thumbnail
    ) {
        UserDetail userDetail = new UserDetail();
        userDetail.setGender(gender);
        userDetail.setEmail(email);
        userDetail.setPhone(phone);
        userDetail.setCell(cell);
        userDetail.setNat(nat);
        userDetail.setName_title(name_title);
        userDetail.setName_first(name_first);
        userDetail.setName_last(name_last);
        userDetail.setLocation_city(location_city);
        userDetail.setLocation_state(location_state);
        userDetail.setLocation_country(location_country);
        userDetail.setLocation_postcode(location_postcode);
        userDetail.setLocation_street_number(location_street_number);
        userDetail.setLocation_street_name(location_street_name);
        userDetail.setLocation_coordinates_latitude(location_coordinates_latitude);
        userDetail.setLocation_coordinates_longitude(location_coordinates_longitude);
        userDetail.setLocation_timezone_offset(location_timezone_offset);
        userDetail.setLocation_timezone_description(location_timezone_description);
        userDetail.setLogin_uuid(login_uuid);
        userDetail.setLogin_username(login_username);
        userDetail.setLogin_password(login_password);
        userDetail.setLogin_salt(login_salt);
        userDetail.setLogin_md5(login_md5);
        userDetail.setLogin_sha1(login_sha1);
        userDetail.setLogin_sha256(login_sha256);
        userDetail.setDob_date(dob_date);
        userDetail.setDob_age(dob_age);
        userDetail.setRegistered_date(registered_date);
        userDetail.setRegistered_age(registered_age);
        userDetail.setId_name(id_name);
        userDetail.setId_value(id_value);
        userDetail.setPicture_large(picture_large);
        userDetail.setPicture_medium(picture_medium);
        userDetail.setPicture_thumbnail(picture_thumbnail);

        return userDetailService.filterUserDetails(userDetail);
    }

    @Operation(summary = "'download images': Filter UserDetail entities based on provided fields")
    @GetMapping("/user-detail/{id}/thumbnail")
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) {
        String url = userDetailService.getThumbnailUrl(id);

        RestTemplate restTemplate = new RestTemplate();
        byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.add("Content-Disposition", "attachment; filename=image.jpg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }
}
