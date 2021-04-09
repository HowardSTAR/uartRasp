import com.pi4j.io.serial.*;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformManager;
import com.pi4j.util.Console;

import java.io.IOException;

public class RaspUart {
    public static void main(String args[]) {
        try {
            PlatformManager.setPlatform(Platform.RASPBERRYPI);
            Console console = new Console();
            console.promptForExit();
            Serial serial = SerialFactory.createInstance();
            serial.addListener(new SerialDataEventListener() {
                @Override
                public void dataReceived(SerialDataEvent event) {
                    try {
                        console.println("[HEX DATA]   " + event.getHexByteString("0x", " ", ""));
                        console.println("[ASCII DATA] " + event.getAsciiString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            SerialConfig config = new SerialConfig();
            config.device(RaspberryPiSerial.DEFAULT_COM_PORT)
                    .baud(Baud._9600)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);
            serial.open(config);
            console.waitForExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
