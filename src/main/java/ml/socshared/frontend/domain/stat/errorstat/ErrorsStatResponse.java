package ml.socshared.frontend.domain.stat.errorstat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorsStatResponse {

    private long allErrorsCount;
    private long authErrorsCount;
    private long bstatErrorsCount;
    private long textAnalyzerErrorsCount;
    private long vkAdapterErrorsCount;
    private long fbAdapterErrorsCount;
    private long techSupportErrorsCount;
    private long workerErrorsCount;
    private long mailSenderErrorsCount;
    private long gatewayErrorsCount;
    private long storageErrorsCount;

}
