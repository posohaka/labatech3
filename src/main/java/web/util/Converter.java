package web.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.*;

public class Converter {

    public static void toJSON(OutputStream outputStream,Messege messege) throws IOException {
//        MessagePackFactory messagePackFactory = new MessagePackFactory();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        mapper.writeValue(outputStream, messege);
    }

    public static Messege toMessege(InputStream inputStream) throws IOException {
//        MessagePackFactory messagePackFactory = new MessagePackFactory();
//        messagePackFactory.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
//        MessagePackFactory messagePackFactory = new MessagePackFactory();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        Messege messege = mapper.readValue(inputStream, Messege.class);
        return messege;
    }
}
