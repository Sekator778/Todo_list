package test;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(exclude = "cars")
public class Engine {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int power;

    @Getter
    @Setter
    private Set<Car> cars;

}