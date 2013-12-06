package oovu.events;

import java.util.HashMap;

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
        public final MidiDevice device;

        public MidiReceiver(String name, MidiDevice device) {
            this.device = device;
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

    public final HashMap<String, MidiReceiver> receivers =
        new HashMap<String, MidiReceiver>();

    public MidiListener() {
        this.update();
    }

    public void update() {
        for (MidiReceiver midi_receiver : this.receivers.values()) {
            midi_receiver.device.close();
        }
        this.receivers.clear();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                String device_info = device.getDeviceInfo().toString();
                Transmitter transmitter = device.getTransmitter();
                MidiReceiver receiver = new MidiReceiver(device_info, device);
                this.receivers.put(device_info, receiver);
                transmitter.setReceiver(receiver);
                device.open();
            } catch (MidiUnavailableException e) {
                Environment.log(e.toString());
            }
        }
    }
}