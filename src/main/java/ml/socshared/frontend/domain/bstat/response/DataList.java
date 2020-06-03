package ml.socshared.frontend.domain.bstat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataList <T>{
    Integer size;
    List<T> data;
}
