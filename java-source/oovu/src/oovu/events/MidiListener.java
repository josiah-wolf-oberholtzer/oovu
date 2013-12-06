package oovu.events;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

import oovu.addresses.Environment;

public class MidiListener {

    public MidiListener() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                String device_info = device.getDeviceInfo().toString();
                Environment.log("Trying: " + info);
//                List<Transmitter> transmitters = device.getTransmitters();
//                Environment.log("Transmitters: " + transmitters.size());
//                for (Transmitter transmitter : transmitters) {
//                    Receiver receiver = new MidiReceiver(device_info);
//                    transmitter.setReceiver(receiver);
//                    Environment.log("\tTransmitter: " + transmitter.toString());
//                }
                Transmitter transmitter = device.getTransmitter();
                Receiver receiver = new MidiReceiver(device_info);
                transmitter.setReceiver(receiver);
                device.open();
                Environment.log("Opened: " + device_info);
            } catch (MidiUnavailableException e) {
                Environment.log(e.toString());
            }
        }
    }
    
    private class MidiReceiver implements Receiver {
        public final String name;
        public MidiReceiver(String name) {
            this.name = name; 
        }
        public void send(MidiMessage message, long time_stamp) {
            ShortMessage short_message = (ShortMessage) message;
            Environment.log("Received [" + this.name + "]: "
                + short_message.getChannel() + " "
                + short_message.getCommand() + " "
                + short_message.getStatus() + " "
                + short_message.getData1() + " "
                + short_message.getData2() + " "
                );
            //Environment.log("Received [" + this.name + "]: " + message.toString());
        }
        public void close() { }
    }
    
}
