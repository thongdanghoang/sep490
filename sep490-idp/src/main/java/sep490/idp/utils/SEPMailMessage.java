package sep490.idp.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SEPMailMessage extends SimpleMailMessage {

    private String templateName;

    private Map<String, Object> templateModels = new HashMap<>();

    private Map<String, File> attachments = new HashMap<>();

}
