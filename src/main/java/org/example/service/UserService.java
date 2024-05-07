package org.example.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.example.model.User;
import org.example.model.UserDetail;
import org.example.model.UserDetailRepository;
import org.example.model.UserRepository;
import org.example.util.JFlat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.util.IteratorUtil.makeCollection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    public List<User> saveSampleUsers(String response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, Map.class);

        List<Map<String, Object>> users = (List<Map<String, Object>>) map.get("results");

        for (Map<String, Object> userMap : users) {
            User user = new User();
            user.setInfo(mapper.writeValueAsString(userMap));
            userRepository.save(user);
        }
        convertUserInfoToUserDetail();
        return makeCollection(userRepository.findAll());
    }

    public String getUserInfoAsCsv() throws Exception {
        StringBuilder csvBuilderJson = new StringBuilder();
        List<User> users = makeCollection(userRepository.findAll());

        csvBuilderJson.append("[");
        for (User user : users) {
            csvBuilderJson.append(user.getInfo());
            csvBuilderJson.append(",");
        }
        csvBuilderJson.replace(csvBuilderJson.length() - 1, csvBuilderJson.length(), "]");

        return convertJsonToCsv(csvBuilderJson.toString());
    }

    private void convertUserInfoToUserDetail() throws Exception {
        List<UserDetail> userDetailList = new ArrayList<>();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ObjectReader mapper = new CsvMapper().reader(UserDetail.class).with(schema);
        MappingIterator<UserDetail> it = mapper.readValues(getUserInfoAsCsv());
        while (it.hasNext()) {
            userDetailList.add(it.next());
        }
        userDetailRepository.saveAll(userDetailList);
    }

    private String convertJsonToCsv(String json) throws Exception {
        JFlat flatMe = new JFlat(json);
        return flatMe.json2Sheet().headerSeparator("_").write2csv(',');
    }


}