package model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SearchResult {

    String registration;
    String make;
    String model;
    String color;
    String year;

}
