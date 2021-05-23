package io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private int age;
    private String name;
    private String surname;

}
