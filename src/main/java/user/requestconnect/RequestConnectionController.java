package user.requestconnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@ComponentScan("main")
public class RequestConnectionController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IRequestConnectionInput requestConnectionInteractor;

    @Autowired
    public RequestConnectionController(IRequestConnectionInput requestConnectionInteractor,
                                       SimpMessagingTemplate simpMessagingTemplate) {
        this.requestConnectionInteractor = requestConnectionInteractor;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @PostMapping("/users/connections/request")
    public void requestConnection(
            Principal user,
            @Header("simpSessionId") String sessionId,
            @Payload RequestConnectionRequestModel requestModel){
        RequestConnectionResponseModel requestConnectionResponseModel = requestConnectionInteractor.requestConnection(requestModel);

        // send to target
        simpMessagingTemplate.convertAndSendToUser(
                requestModel.getTargetId(), "/queue/connections/response", requestConnectionResponseModel
        );

        // send to current user
        simpMessagingTemplate.convertAndSendToUser(
                requestModel.getUserId(), "/queue/connections/response", requestConnectionResponseModel
        );
    }
}