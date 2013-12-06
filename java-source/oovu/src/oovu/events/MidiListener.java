package oovu.events;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

import oovu.addresses.Environment;
import oovu.events.types.MidiEvent;

public class MidiListener {
    private class MidiReceiver implements Receiver {
        public final String name;

        public MidiReceiver(String name) {
            this.name = name;
        }

        @Override
        public void close() {
        }

        @Override
        public void send(MidiMessage message, long time_stamp) {
            ShortMessage short_message = (ShortMessage) message;
            MidiEvent midi_event =
                new MidiEvent(short_message.getChannel(),
                    short_message.getData1(), short_message.getData2());
            Environment.log(midi_event.toString());
            Environment.event_service.publish(midi_event);
        }

        @Override
        public String toString() {
            return "MidiReceiver ["
                + (this.name != null ? "name=" + this.name : "") + "]";
        }
    }

    public MidiListener() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                String device_info = device.getDeviceInfo().toString();
                Environment.log("Trying: " + info);
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
}
