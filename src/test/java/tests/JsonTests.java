package tests;

import model.House;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonTests {
    private ClassLoader cl = FilesTests.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonTest() {
        InputStream is = cl.getResourceAsStream("house.json");
        House house = objectMapper.readValue(is, House.class);
        Assertions.assertEquals("brick", house.getMaterial());
        Assertions.assertEquals(3, house.getFloors());
        Assertions.assertEquals("white", house.getColor());
        Assertions.assertEquals(1, house.getRooms().getLivingRoom());
        Assertions.assertEquals(4, house.getRooms().getBedroom());
        Assertions.assertEquals(2, house.getRooms().getBathroom());

    }
}
