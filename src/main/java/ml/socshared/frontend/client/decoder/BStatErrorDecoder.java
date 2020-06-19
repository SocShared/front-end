package ml.socshared.frontend.client.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import ml.socshared.frontend.exception.impl.HttpBadRequestException;
import ml.socshared.frontend.exception.impl.HttpNotFoundException;
import ml.socshared.frontend.exception.impl.HttpUnavailableRequestException;
import org.springframework.http.HttpInputMessage;

public class BStatErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status() == 500) {
            return new HttpNotFoundException("Статитсика по группе не доступна");
        }
        if(response.status() == 404) {
            return new HttpNotFoundException("Стаитистика по группе еще не собрана или группа в системе не существует");
        }
        return new HttpNotFoundException("Не предвиденная ошибка");
    }
}
