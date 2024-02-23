package com.fineroot1253.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApiTest {
    @Test
    @DisplayName("http get 요청")
    void get() throws IOException {
        //given
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        //when
        String result = Api.get(url);
        //then
        assertThat(result).hasToString("{  \"userId\": 1,  \"id\": 1,  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"}");
    }
}
